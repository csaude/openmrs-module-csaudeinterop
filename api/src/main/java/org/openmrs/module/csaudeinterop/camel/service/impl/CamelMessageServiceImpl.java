package org.openmrs.module.csaudeinterop.camel.service.impl;

import org.apache.camel.ProducerTemplate;
import org.openmrs.api.context.Context;
import org.openmrs.module.csaudeinterop.api.DispensationProcessorService;
import org.openmrs.module.csaudeinterop.api.PatientSynchronizationResponseProcessorService;
import org.openmrs.module.csaudeinterop.api.PrescriptionResponseProcessorService;
import org.openmrs.module.csaudeinterop.camel.payload.DispensationPayload;
import org.openmrs.module.csaudeinterop.camel.payload.PatientPayload;
import org.openmrs.module.csaudeinterop.camel.payload.PatientSyncResponsePayload;
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
	
	@Autowired
	private PatientSynchronizationResponseProcessorService patientSynchronizationResponseProcessorService;
	
	@Autowired
	private PrescriptionResponseProcessorService prescriptionResponseProcessorService;
	
	@Override
	public void publishPrescription(PrescriptionPayload payload) {
		this.producerTemplate.sendBody("direct:sendPrescription", payload);
	}
	
	@Override
	public void publishPatient(PatientPayload payload) {
		this.producerTemplate.sendBody("direct:sendPatient", payload);
		System.out.println("sincronizado paciente com UUID " + payload.getPatientUuid());
	}
	
	@Override
	public void processPrescriptionResponse(PrescriptionResponsePayload payload) {
		
		// TODO: Logica Context.openSession(); sera removida apos migracao dos resources
		// para a camada omod
		Context.openSession();
		try {
			Context.authenticate("admin", "eSaude123");
			this.prescriptionResponseProcessorService.process(payload);
			
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
	
	@Override
	public void processPatientSyncResponse(PatientSyncResponsePayload payload) {
		
		try {
			Context.authenticate("admin", "eSaude123");
			
			this.patientSynchronizationResponseProcessorService.process(payload);
			
		}
		finally {
			Context.closeSession();
		}
	}
}
