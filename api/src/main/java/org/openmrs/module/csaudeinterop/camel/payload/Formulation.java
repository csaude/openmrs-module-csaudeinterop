package org.openmrs.module.csaudeinterop.camel.payload;

public class Formulation {
	
	private String orderUuid;
	
	private String drug;
	
	private String drugName;
	
	private int prescribedQty;
	
	private String form;
	
	private int duration;
	
	private String durationUnit;
	
	private int amtPerTime;
	
	private int timesPerDay;
	
	public String getOrderUuid() {
		return orderUuid;
	}
	
	public void setOrderUuid(String orderUuid) {
		this.orderUuid = orderUuid;
	}
	
	public String getDrug() {
		return drug;
	}
	
	public void setDrug(String drug) {
		this.drug = drug;
	}
	
	public String getDrugName() {
		return drugName;
	}
	
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	
	public int getPrescribedQty() {
		return prescribedQty;
	}
	
	public void setPrescribedQty(int prescribedQty) {
		this.prescribedQty = prescribedQty;
	}
	
	public String getForm() {
		return form;
	}
	
	public void setForm(String form) {
		this.form = form;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String getDurationUnit() {
		return durationUnit;
	}
	
	public void setDurationUnit(String durationUnit) {
		this.durationUnit = durationUnit;
	}
	
	public int getAmtPerTime() {
		return amtPerTime;
	}
	
	public void setAmtPerTime(int amtPerTime) {
		this.amtPerTime = amtPerTime;
	}
	
	public int getTimesPerDay() {
		return timesPerDay;
	}
	
	public void setTimesPerDay(int timesPerDay) {
		this.timesPerDay = timesPerDay;
	}
}
