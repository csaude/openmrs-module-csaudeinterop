package org.openmrs.module.csaudeinterop.api;

import org.openmrs.module.csaudeinterop.camel.payload.DispensationPayload;

public interface DispensationProcessorService {
	
	void process(DispensationPayload payload);
}
