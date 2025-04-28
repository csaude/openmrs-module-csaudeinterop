package org.openmrs.module.csaudeinterop.camel.config;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.spring.SpringCamelContext;
import org.openmrs.module.csaudeinterop.camel.payload.PatientPayload;
import org.openmrs.module.csaudeinterop.camel.payload.PrescriptionPayload;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {
	
	@Bean
	public PropertiesComponent propertiesComponent() {
		PropertiesComponent pc = new PropertiesComponent();
		pc.setLocation("classpath:camel.properties");
		return pc;
	}
	
	@Bean
	public CamelContext camelContext(ApplicationContext applicationContext, PropertiesComponent propertiesComponent)
	        throws Exception {
		SpringCamelContext context = new SpringCamelContext(applicationContext);
		context.setPropertiesComponent(propertiesComponent);
		return context;
	}
	
	@Bean
	public ProducerTemplate producerTemplate(CamelContext context) {
		return context.createProducerTemplate();
	}
	
	@Bean
	public JacksonDataFormat prescriptionDataFormat() {
		return new JacksonDataFormat(PrescriptionPayload.class);
	}
	
	@Bean
	public JacksonDataFormat patientDataFormat() {
		return new JacksonDataFormat(PatientPayload.class);
	}
}
