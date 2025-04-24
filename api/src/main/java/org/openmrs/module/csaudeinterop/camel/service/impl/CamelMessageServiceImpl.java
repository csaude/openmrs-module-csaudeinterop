package org.openmrs.module.csaudeinterop.camel.service.impl;

import org.apache.camel.ProducerTemplate;
import org.openmrs.api.context.Context;
import org.openmrs.module.csaudeinterop.api.DispensationProcessorService;
import org.openmrs.module.csaudeinterop.camel.payload.DispensationPayload;
import org.openmrs.module.csaudeinterop.camel.payload.PatientPayload;
import org.openmrs.module.csaudeinterop.camel.payload.PrescriptionPayload;
import org.openmrs.module.csaudeinterop.camel.payload.PrescriptionResponsePayload;
import org.openmrs.module.csaudeinterop.camel.service.CamelMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CamelMessageServiceImpl implements CamelMessageService {
	
	@Autowired
	private ProducerTemplate producerTemplate;
	
	@Autowired
	private DispensationProcessorService dispensationProcessorService;
	
	@Override
	public void publishPrescription(PrescriptionPayload payload) {
		this.producerTemplate.sendBody("direct:sendPrescription", payload);
	}
	
	@Override
	public void publishPatient(PatientPayload payload) {
		this.producerTemplate.sendBody("direct:sendPatient", payload);
	}
	
	@Override
	public void processPrescriptionResponse(PrescriptionResponsePayload payload) {
		
		// TODO: Logica Context.openSession(); sera removida apos migracao dos resources
		// para a camada omod
		Context.openSession();
		try {
			Context.authenticate("admin", "eSaude123");
			
			// TODO: implementar logica de confirmacao da Prescricao
			
		}
		finally {
			Context.closeSession();
		}
	}
	
	@Override
	public void consumeAndPersistDispensation(DispensationPayload payload) {
		// TODO: Logica Context.openSession(); sera removida apos migracao dos resources
		// para a camada omod
		Context.openSession();
		try {
			Context.authenticate("admin", "eSaude123");
			dispensationProcessorService.process(payload);
		}
		finally {
			Context.closeSession();
		}
	}
	
}
