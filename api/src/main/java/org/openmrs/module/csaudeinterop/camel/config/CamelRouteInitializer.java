package org.openmrs.module.csaudeinterop.camel.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jms.JmsComponent;
import org.openmrs.api.AdministrationService;
import org.openmrs.module.csaudeinterop.camel.payload.DispensationPayload;
import org.openmrs.module.csaudeinterop.camel.payload.PatientSyncResponsePayload;
import org.openmrs.module.csaudeinterop.camel.payload.PrescriptionResponsePayload;
import org.openmrs.module.csaudeinterop.camel.service.CamelMessageService;
import org.openmrs.module.csaudeinterop.util.CSaudeInteropConstants;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CamelRouteInitializer implements InitializingBean {
	
	@Autowired
	private CamelContext camelContext;
	
	@Autowired
	private CamelMessageService camelMessageService;
	
	@Autowired
	@Qualifier("adminService")
	private AdministrationService administrationService;
	
	@Autowired
	private JacksonDataFormat prescriptionDataFormat;
	
	@Autowired
	private JacksonDataFormat patientDataFormat;
	
	@Override
	public void afterPropertiesSet() throws Exception {

		JmsComponent jmsComponent = JmsComponent.jmsComponentAutoAcknowledge(connectionFactory());

		jmsComponent
				.setUsername(this.administrationService.getGlobalProperty(CSaudeInteropConstants.ARTEMIS_USER_NAME));
		jmsComponent.setPassword(this.administrationService.getGlobalProperty(CSaudeInteropConstants.ARTEMIS_PASSWORD));

		this.camelContext.getRegistry().bind(CSaudeInteropConstants.JMS_COMPONENT, jmsComponent);

		this.camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {

				from("direct:sendPatient").marshal(patientDataFormat)
						.setHeader("Content-Type", constant("application/json"))
						.to("jms:queue:patient.sync.queue?jmsMessageType=Text");

				from("direct:sendPrescription").marshal(prescriptionDataFormat)
						.setHeader("Content-Type", constant("application/json"))
						.to("jms:queue:prescription.queue?jmsMessageType=Text");

				from("jms:queue:prescription.response.queue").process(exchange -> {
					String json = exchange.getIn().getBody(String.class);
					ObjectMapper mapper = new ObjectMapper();

					PrescriptionResponsePayload response = mapper.readValue(json, PrescriptionResponsePayload.class);
					log.info(String.format("payload consumido (Prescription Response) '%s'", response));
					camelMessageService.processPrescriptionResponse(response);
				});

				from("jms:queue:patient.sync.response.queue").process(exchange -> {
					String json = exchange.getIn().getBody(String.class);
					ObjectMapper mapper = new ObjectMapper();

					PatientSyncResponsePayload response = mapper.readValue(json, PatientSyncResponsePayload.class);
					log.info(String.format("payload consumido (Patient Sync Response) '%s'", response));
					camelMessageService.processPatientSyncResponse(response);
				});

				from("jms:queue:dispensation.queue").process(exchange -> {
					String json = exchange.getIn().getBody(String.class);
					ObjectMapper mapper = new ObjectMapper();
					DispensationPayload response = mapper.readValue(json, DispensationPayload.class);
					log.info(String.format("payload consumido (Dispensation) '%s'", response));
					camelMessageService.consumeAndPersistDispensation(response);
				});
			}
		});
	}
	
	private ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory(
		        this.administrationService.getGlobalProperty(CSaudeInteropConstants.URL_ACTIVEMQ_ARTEMIS));
	}
}
