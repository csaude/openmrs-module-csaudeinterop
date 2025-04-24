package org.openmrs.module.csaudeinterop.camel.payload;

import java.util.List;

public class PrescriptionPayload {
	
	private String clinicalService;
	
	private String patientUuid;
	
	private String nid;
	
	private String prescriptionUuid;
	
	private String therapeuticRegimen;
	
	private String prescriptionDate;
	
	private String providerUuid;
	
	private String dispenseType;
	
	private String therapeuticLine;
	
	private String changeRegimenLine;
	
	private String regimenLineChangeReason;
	
	private String locationUuid;
	
	private String duration;
	
	private String notes;
	
	private List<Formulation> prescribedDrugs;
	
	public String getClinicalService() {
		return clinicalService;
	}
	
	public void setClinicalService(String clinicalService) {
		this.clinicalService = clinicalService;
	}
	
	public String getPatientUuid() {
		return patientUuid;
	}
	
	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
	}
	
	public String getNid() {
		return nid;
	}
	
	public void setNid(String nid) {
		this.nid = nid;
	}
	
	public String getPrescriptionUuid() {
		return prescriptionUuid;
	}
	
	public void setPrescriptionUuid(String prescriptionUuid) {
		this.prescriptionUuid = prescriptionUuid;
	}
	
	public String getTherapeuticRegimen() {
		return therapeuticRegimen;
	}
	
	public void setTherapeuticRegimen(String therapeuticRegimen) {
		this.therapeuticRegimen = therapeuticRegimen;
	}
	
	public String getPrescriptionDate() {
		return prescriptionDate;
	}
	
	public void setPrescriptionDate(String prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}
	
	public String getProviderUuid() {
		return providerUuid;
	}
	
	public void setProviderUuid(String providerUuid) {
		this.providerUuid = providerUuid;
	}
	
	public String getDispenseType() {
		return dispenseType;
	}
	
	public void setDispenseType(String dispenseType) {
		this.dispenseType = dispenseType;
	}
	
	public String getTherapeuticLine() {
		return therapeuticLine;
	}
	
	public void setTherapeuticLine(String therapeuticLine) {
		this.therapeuticLine = therapeuticLine;
	}
	
	public String getChangeRegimenLine() {
		return changeRegimenLine;
	}
	
	public void setChangeRegimenLine(String changeRegimenLine) {
		this.changeRegimenLine = changeRegimenLine;
	}
	
	public String getRegimenLineChangeReason() {
		return regimenLineChangeReason;
	}
	
	public void setRegimenLineChangeReason(String regimenLineChangeReason) {
		this.regimenLineChangeReason = regimenLineChangeReason;
	}
	
	public String getLocationUuid() {
		return locationUuid;
	}
	
	public void setLocationUuid(String locationUuid) {
		this.locationUuid = locationUuid;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public List<Formulation> getPrescribedDrugs() {
		return prescribedDrugs;
	}
	
	public void setPrescribedDrugs(List<Formulation> prescribedDrugs) {
		this.prescribedDrugs = prescribedDrugs;
	}
}
