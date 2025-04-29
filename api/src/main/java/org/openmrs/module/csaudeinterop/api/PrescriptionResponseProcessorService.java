package org.openmrs.module.csaudeinterop.api;

import org.openmrs.module.csaudeinterop.camel.payload.PrescriptionResponsePayload;

public interface PrescriptionResponseProcessorService {
	
	void process(PrescriptionResponsePayload payload);
}
