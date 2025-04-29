package org.openmrs.module.csaudeinterop.camel.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PatientPayload {
	
	@JsonProperty("encounterUuid")
	private String encounterUuid;
	
	@JsonProperty("patientUuid")
	private String patientUuid;
	
	@JsonProperty("firstName")
	private String firstName;
	
	@JsonProperty("middleName")
	private String middleName;
	
	@JsonProperty("lastName")
	private String lastName;
	
	@JsonProperty("birthDate")
	private String birthDate;
	
	@JsonProperty("birthdateEstimated")
	private String birthdateEstimated;
	
	@JsonProperty("gender")
	private String gender;
	
	@JsonProperty("province")
	private String province;
	
	@JsonProperty("district")
	private String district;
	
	@JsonProperty("administrativePost")
	private String administrativePost;
	
	@JsonProperty("locality")
	private String locality;
	
	@JsonProperty("address")
	private String address;
	
	@JsonProperty("referencePoint")
	private String referencePoint;
	
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	
	@JsonProperty("alternativePhoneNumber")
	private String alternativePhoneNumber;
	
	@JsonProperty("locationUuid")
	private String locationUuid;
	
	@JsonProperty("locationName")
	private String locationName;
	
	@JsonProperty("clinicalHistory")
	private List<ClinicalHistory> clinicalHistory;
	
	public String getEncounterUuid() {
		return encounterUuid;
	}
	
	public void setEncounterUuid(String encounterUuid) {
		this.encounterUuid = encounterUuid;
	}
	
	public String getPatientUuid() {
		return patientUuid;
	}
	
	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getDistrict() {
		return district;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getAdministrativePost() {
		return administrativePost;
	}
	
	public void setAdministrativePost(String administrativePost) {
		this.administrativePost = administrativePost;
	}
	
	public String getLocality() {
		return locality;
	}
	
	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getReferencePoint() {
		return referencePoint;
	}
	
	public void setReferencePoint(String referencePoint) {
		this.referencePoint = referencePoint;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getAlternativePhoneNumber() {
		return alternativePhoneNumber;
	}
	
	public void setAlternativePhoneNumber(String alternativePhoneNumber) {
		this.alternativePhoneNumber = alternativePhoneNumber;
	}
	
	public String getLocationUuid() {
		return locationUuid;
	}
	
	public void setLocationUuid(String locationUuid) {
		this.locationUuid = locationUuid;
	}
	
	public String getLocationName() {
		return locationName;
	}
	
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public List<ClinicalHistory> getClinicalHistory() {
		return clinicalHistory;
	}
	
	public void setClinicalHistory(List<ClinicalHistory> clinicalHistory) {
		this.clinicalHistory = clinicalHistory;
	}
	
	public String getBirthdateEstimated() {
		return birthdateEstimated;
	}
	
	public void setBirthdateEstimated(String birthdateEstimated) {
		this.birthdateEstimated = birthdateEstimated;
	}
	
	public static class ClinicalHistory {
		
		@JsonProperty("serviceCode")
		private String serviceCode;
		
		@JsonProperty("nid")
		private String nid;
		
		@JsonProperty("admissionDate")
		private String admissionDate;
		
		@JsonProperty("programStatus")
		private String programStatus;
		
		@JsonProperty("clinicalSector")
		private String clinicalSector;
		
		@JsonProperty("systemDate")
		private String systemDate;
		
		public String getServiceCode() {
			return serviceCode;
		}
		
		public void setServiceCode(String serviceCode) {
			this.serviceCode = serviceCode;
		}
		
		public String getNid() {
			return nid;
		}
		
		public void setNid(String nid) {
			this.nid = nid;
		}
		
		public String getAdmissionDate() {
			return admissionDate;
		}
		
		public void setAdmissionDate(String admissionDate) {
			this.admissionDate = admissionDate;
		}
		
		public String getProgramStatus() {
			return programStatus;
		}
		
		public void setProgramStatus(String programStatus) {
			this.programStatus = programStatus;
		}
		
		public String getClinicalSector() {
			return clinicalSector;
		}
		
		public void setClinicalSector(String clinicalSector) {
			this.clinicalSector = clinicalSector;
		}
		
		public String getSystemDate() {
			return systemDate;
		}
		
		public void setSystemDate(String systemDate) {
			this.systemDate = systemDate;
		}
	}
}
