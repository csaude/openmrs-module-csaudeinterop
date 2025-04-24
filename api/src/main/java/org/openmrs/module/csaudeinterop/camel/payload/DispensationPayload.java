package org.openmrs.module.csaudeinterop.camel.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DispensationPayload {
	
	@JsonProperty("encounterDatetime")
	public String encounterDatetime;
	
	@JsonProperty("patient")
	public String patient;
	
	@JsonProperty("encounterType")
	public String encounterType;
	
	@JsonProperty("location")
	public String location;
	
	@JsonProperty("form")
	public String form;
	
	@JsonProperty("encounterProviders")
	public List<EncounterProvider> encounterProviders;
	
	@JsonProperty("obs")
	public List<Obs> obs;
	
	public static class EncounterProvider {
		
		@JsonProperty("provider")
		public String provider;
		
		@JsonProperty("encounterRole")
		public String encounterRole;
	}
	
	public static class Obs {
		
		@JsonProperty("person")
		public String person;
		
		@JsonProperty("obsDatetime")
		public String obsDatetime;
		
		@JsonProperty("concept")
		public String concept;
		
		@JsonProperty("value")
		public String value;
		
		@JsonProperty("comment")
		public String comment;
		
		@JsonProperty("groupMembers")
		public List<Obs> groupMembers;
	}
}
