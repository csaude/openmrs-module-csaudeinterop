package org.openmrs.module.csaudeinterop.camel.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.openmrs.api.AdministrationService;
import org.openmrs.module.csaudeinterop.camel.payload.DispensationPayload;
import org.openmrs.module.csaudeinterop.camel.payload.PrescriptionResponsePayload;
import org.openmrs.module.csaudeinterop.camel.service.CamelMessageService;
import org.openmrs.module.csaudeinterop.util.CSaudeCoreConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CamelRouteInitializer implements InitializingBean {
	
	private static final Logger log = LoggerFactory.getLogger(CamelRouteInitializer.class);
	
	@Autowired
	private CamelContext camelContext;
	
	@Autowired
	private CamelMessageService camelMessageService;
	
	@Autowired
	@Qualifier("adminService")
	private AdministrationService administrationService;
	
	@Override
	public void afterPropertiesSet() throws Exception {

		JmsComponent jmsComponent = JmsComponent.jmsComponentAutoAcknowledge(connectionFactory());

		jmsComponent.setUsername(this.administrationService.getGlobalProperty(CSaudeCoreConstants.ARTEMIS_USER_NAME));
		jmsComponent.setPassword(this.administrationService.getGlobalProperty(CSaudeCoreConstants.ARTEMIS_PASSWORD));

		this.camelContext.getRegistry().bind(CSaudeCoreConstants.JMS_COMPONENT, jmsComponent);

		this.camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {

				from("direct:sendPatient").marshal().json().to("jms:queue:patient.sync.queue");

				from("direct:sendPrescription").marshal().json().to("jms:queue:prescription.queue");

				from("jms:queue:prescription.response.queue").process(exchange -> {
					String json = exchange.getIn().getBody(String.class);
					ObjectMapper mapper = new ObjectMapper();

					// TODO: alterar o object para PrescriptionResponsePayload, representa a
					// resposta da prescricao
					Object response = mapper.readValue(json, Object.class);
					log.info(String.format("payload consumido (Prescription Response) '%s'", response));

					camelMessageService.processPrescriptionResponse(new PrescriptionResponsePayload());
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
		        this.administrationService.getGlobalProperty(CSaudeCoreConstants.URL_ACTIVEMQ_ARTEMIS));
	}
}
