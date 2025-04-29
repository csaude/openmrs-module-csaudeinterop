package org.openmrs.module.csaudeinterop.api;

import org.openmrs.module.csaudeinterop.camel.payload.PatientSyncResponsePayload;

public interface PatientSynchronizationResponseProcessorService {
	
	void process(PatientSyncResponsePayload payload);
}
