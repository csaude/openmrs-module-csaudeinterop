package org.openmrs.module.csaudeinterop.api.impl;

import java.util.Objects;

import org.openmrs.Drug;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.Provider;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.FormService;
import org.openmrs.api.LocationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.ProviderService;
import org.openmrs.module.csaudeinterop.api.DispensationProcessorService;
import org.openmrs.module.csaudeinterop.camel.payload.DispensationPayload;
import org.openmrs.module.csaudeinterop.camel.payload.DispensationPayload.EncounterProvider;
import org.openmrs.module.csaudeinterop.camel.payload.DispensationPayload.Obs;
import org.openmrs.module.csaudeinterop.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DispensationProcessorServiceImpl implements DispensationProcessorService {
	
	private PatientService patientService;
	
	private EncounterService encounterService;
	
	private LocationService locationService;
	
	private ProviderService providerService;
	
	private FormService formService;
	
	private ConceptService conceptService;
	
	@Autowired
	public DispensationProcessorServiceImpl(PatientService patientService, EncounterService encounterService,
	    LocationService locationService, ProviderService providerService, FormService formService,
	    ConceptService conceptService) {
		this.patientService = patientService;
		this.encounterService = encounterService;
		this.locationService = locationService;
		this.providerService = providerService;
		this.formService = formService;
		this.conceptService = conceptService;
	}
	
	@Override
	public void process(DispensationPayload payload) {
		
		Patient patient = this.patientService.getPatientByUuid(payload.patient);
		if (patient == null) {
			throw new BusinessException(String.format("Paciente não encontrado: %s", payload.patient));
		}
		
		EncounterType encounterType = this.encounterService.getEncounterTypeByUuid(payload.encounterType);
		if (encounterType == null || encounterType.getRetired()) {
			throw new BusinessException(String.format("EncounterType inválido ou inativo. %s", payload.encounterType));
		}
		
		Location location = this.locationService.getLocationByUuid(payload.location);
		if (location == null || location.getRetired()) {
			throw new BusinessException(String.format("Location inválida ou inativa. %s", payload.location));
		}
		
		Form form = this.formService.getFormByUuid(payload.form);
		if (form == null || form.getRetired()) {
			throw new BusinessException(String.format("Formulário inválido ou inativo. %s", payload.form));
		}
		Encounter encounter = new Encounter();
		encounter.setEncounterDatetime(java.sql.Date.valueOf(payload.encounterDatetime));
		encounter.setPatient(patient);
		encounter.setEncounterType(encounterType);
		encounter.setLocation(location);
		encounter.setForm(form);
		
		for (EncounterProvider ep : payload.encounterProviders) {
			Provider provider = this.providerService.getProviderByUuid(ep.provider);
			if (provider == null || provider.getRetired()) {
				throw new BusinessException(String.format("Provider inválido ou inativo: %s", ep.provider));
			}
			encounter.addProvider(this.encounterService.getEncounterRoleByUuid(ep.encounterRole), provider);
		}
		for (Obs obsPayload : payload.obs) {
			org.openmrs.Obs obs = createObsRecursive(obsPayload, patient.getPerson(), location);
			encounter.addObs(obs);
		}
		this.encounterService.saveEncounter(encounter);
	}
	
	public org.openmrs.Obs createObsRecursive(Obs obsPayload, Person person, Location location) {
		org.openmrs.Obs obs = new org.openmrs.Obs();
		obs.setPerson(person);
		obs.setLocation(location);
		obs.setObsDatetime(java.sql.Date.valueOf(obsPayload.obsDatetime));
		obs.setConcept(this.conceptService.getConceptByUuid(obsPayload.concept));
		obs.setComment(obsPayload.comment);
		
		if (obsPayload.value != null) {
			try {
				org.openmrs.Concept concept = obs.getConcept();
				
				if (concept.isNumeric()) {
					
					obs.setValueNumeric(Double.parseDouble(obsPayload.value));
					
				} else if (concept.getDatatype().isText()) {
					
					obs.setValueText(obsPayload.value.toString());
					
				} else if (concept.getDatatype().isDate() || concept.getDatatype().isDateTime()) {
					
					obs.setValueDate(java.sql.Date.valueOf(obsPayload.value.toString()));
					
				} else if (concept.getDatatype().isBoolean()) {
					
					obs.setValueBoolean(Boolean.parseBoolean(obsPayload.value.toString()));
					
				} else if (concept.getDatatype().isCoded()) {
					
					if (isConceptCode(obsPayload.value.toString())) {
						
						org.openmrs.Concept coded = this.conceptService.getConceptByUuid(obsPayload.value.toString());
						if (coded == null) {
							throw new BusinessException("Valor codificado não encontrado: " + obsPayload.value);
						}
						obs.setValueCoded(coded);
						
					} else if (isDrug(obsPayload.value.toString())) {
						
						Drug codedDrug = this.conceptService.getDrugByUuid(obsPayload.value.toString());
						if (codedDrug == null) {
							throw new BusinessException("Valor codificado não encontrado: " + obsPayload.value);
						}
						obs.setValueDrug(codedDrug);
						obs.setValueCoded(codedDrug.getConcept());
						
					} else {
						throw new BusinessException(String.format(
						    "Valor codificado não encontrado para o conceito '%s', Nome: %s e valor '%s' ",
						    concept.getUuid(), concept.getName().getName(), obsPayload.value));
					}
				}
			}
			catch (Exception e) {
				throw new BusinessException(String.format("Erro ao definir valor '%s' para o conceito '%s'",
				    obsPayload.value, obsPayload.concept), e);
			}
		} else if (!("N/A".equals(obs.getConcept().getDatatype().getName()))) {
			throw new BusinessException(String.format("Conceito '%s' requer valor, mas nenhum foi fornecido.",
			    obsPayload.concept));
		}
		if (obsPayload.groupMembers != null && !obsPayload.groupMembers.isEmpty()) {
			for (Obs member : obsPayload.groupMembers) {
				org.openmrs.Obs childObs = createObsRecursive(member, person, location);
				obs.addGroupMember(childObs);
			}
		}
		return obs;
	}
	
	private boolean isConceptCode(String value) {
		try {
			return Objects.nonNull(this.conceptService.getConceptByUuid(value));
		}
		catch (Exception e) {
			return false;
		}
	}
	
	private boolean isDrug(String value) {
		try {
			return Objects.nonNull(this.conceptService.getDrugByUuid(value));
		}
		catch (Exception e) {
			return false;
		}
	}
	
}
