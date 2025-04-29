package org.openmrs.module.csaudeinterop.api.impl;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.ObsService;
import org.openmrs.module.csaudeinterop.api.PatientSynchronizationResponseProcessorService;
import org.openmrs.module.csaudeinterop.camel.payload.PatientSyncResponsePayload;
import org.openmrs.module.csaudeinterop.util.CSaudeInteropConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PatientSynchronizationResponseProcessorServiceImpl implements PatientSynchronizationResponseProcessorService {
	
	private static final Logger log = LoggerFactory.getLogger(PatientSynchronizationResponseProcessorServiceImpl.class);
	
	private EncounterService encounterService;
	
	private ConceptService conceptService;
	
	private ObsService obsService;
	
	@Autowired
	public PatientSynchronizationResponseProcessorServiceImpl(EncounterService encounterService,
	    ConceptService conceptService, ObsService obsService) {
		this.encounterService = encounterService;
		this.conceptService = conceptService;
		this.obsService = obsService;
	}
	
	@Override
	public void process(PatientSyncResponsePayload payload) {
		
		Encounter encounter = encounterService.getEncounterByUuid(payload.getEncounterUuid());
		if (encounter == null) {
			log.warn("Encounter not found for UUID: " + payload.getEncounterUuid());
			return;
		}
		
		Concept syncStatusConcept = conceptService
		        .getConceptByUuid(CSaudeInteropConstants.CONCEPT_SYNCHRONIZATION_STATUS_UUID);
		if (syncStatusConcept == null) {
			log.error("Concept 'SYNCHRONIZATION STATUS' not found.");
			return;
		}
		
		Obs syncStatusObs = null;
		for (Obs obs : encounter.getObs()) {
			if (obs.getConcept().equals(syncStatusConcept)) {
				syncStatusObs = obs;
				break;
			}
		}
		
		if (syncStatusObs == null) {
			log.warn("No synchronization status Obs found for Encounter: " + encounter.getUuid());
			return;
		}
		
		if ("SUCCESS".equalsIgnoreCase(payload.getStatus())) {
			Concept successConcept = conceptService.getConceptByUuid(CSaudeInteropConstants.CONCEPT_SUCCESS_STATUS_UUID);
			syncStatusObs.setValueCoded(successConcept);
			syncStatusObs.setComment(null);
		} else if ("ERROR".equalsIgnoreCase(payload.getStatus())) {
			Concept errorConcept = conceptService.getConceptByUuid(CSaudeInteropConstants.CONCEPT_ERROR_STATUS_UUID);
			syncStatusObs.setValueCoded(errorConcept);
			syncStatusObs.setComment(payload.getErrorMessage());
		} else {
			log.warn("Unknown sync status received: " + payload.getStatus());
			return;
		}
		obsService.saveObs(syncStatusObs, "Sync response processed via Artemis");
	}
}
