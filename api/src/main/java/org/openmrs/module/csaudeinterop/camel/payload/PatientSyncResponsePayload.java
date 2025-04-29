package org.openmrs.module.csaudeinterop.camel.payload;

public class PatientSyncResponsePayload {
	
	private String encounterUuid;
	
	private String remoteId;
	
	private String status; // "SUCCESS", "ERROR"
	
	private String errorMessage;
	
	public String getEncounterUuid() {
		return encounterUuid;
	}
	
	public void setEncounterUuid(String encounterUuid) {
		this.encounterUuid = encounterUuid;
	}
	
	public String getRemoteId() {
		return remoteId;
	}
	
	public void setRemoteId(String remoteId) {
		this.remoteId = remoteId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
