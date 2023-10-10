package domain.entities;

import java.util.Date;

import domain.values.InscriptionType;
import utilities.Utils;

public class Inscription {
	private int assistantId;
	private int campId;
	private Date inscriptionDate;
	private float price;
	private boolean canBeCanceled;

	public Inscription( int assistantId, int campId, Date inscriptionDate, float price, boolean canBeCanceled) {
		this.assistantId = assistantId;
		this.campId = campId;
		this.inscriptionDate = inscriptionDate;
		this.price = price;
		this.canBeCanceled = canBeCanceled;
	}
	
	public Inscription() {
		this.assistantId = -1;
		this.campId = -1;
		this.inscriptionDate = null;
		this.price = -1;
	}

	public Integer getAssistantId() {
		return this.assistantId;
	}

	public Integer getCampId() {
		return this.campId;
	}

	public Date getInscriptionDate() {
		return this.inscriptionDate;
	}

	public Float getPrice() {
		return this.price;
	}

	
	public void setAssistantId(int assistantId ){
		this.assistantId = assistantId;
	}
	
	public void setCampId(int campId) {
		this.campId = campId;
	}
	
	public void setInscriptionDate(Date inscriptionDate){
		this.inscriptionDate = inscriptionDate;
	}

	public void setPrice(float price){
		this.price = price;
	}
	
	public String toString() {
		return "{"
				+ "AssistantId: " + this.assistantId 
				+ ", CampId: '" + this.campId
				+ "', InscriptionDate: '" +Utils.getStringDate(this.inscriptionDate)
				+ ", Price: " + this.price 
				+ "}";
		
	}

	public boolean canBeCanceled() {
		return canBeCanceled;
	}


}
