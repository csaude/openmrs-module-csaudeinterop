package org.openmrs.module.csaudeinterop.camel.payload;

public class PrescriptionResponsePayload {
	
	private String prescriptionUuid;
	
	private String remoteId;
	
	private String status; // "SUCCESS", "ERROR", etc.
	
	private String errorMessage;
	
	public String getPrescriptionUuid() {
		return prescriptionUuid;
	}
	
	public void setPrescriptionUuid(String prescriptionUuid) {
		this.prescriptionUuid = prescriptionUuid;
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
	
}
