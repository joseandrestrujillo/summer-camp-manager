package domain;

import java.util.Date;

import utilities.InscriptionType;
import utilities.Utils;

public class Inscription {
	private int assistantId;
	private int campId;
	private Date inscriptionDate;
	private float price;
	private InscriptionType inscriptionType;
	private boolean canBeCanceled;

	public Inscription( int assistantId, int campId, Date inscriptionDate, float price,
	 InscriptionType inscriptionType, boolean canBeCanceled) {
		this.assistantId = assistantId;
		this.campId = campId;
		this.inscriptionDate = inscriptionDate;
		this.price = price;
		this.inscriptionType = inscriptionType;
		this.canBeCanceled = canBeCanceled;
	}
	
	public Inscription() {
		this.assistantId = -1;
		this.campId = -1;
		this.inscriptionDate = null;
		this.price = -1;
		this.inscriptionType = null;
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

	public InscriptionType getInscriptionType() {
		return this.inscriptionType;
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
	
	public void setInscriptionType(InscriptionType inscriptionType){
		this.inscriptionType = inscriptionType;
	}
	public String toString() {
		return "{"
				+ "AssistantId: " + this.assistantId 
				+ ", CampId: '" + this.campId
				+ "', InscriptionDate: '" +Utils.getStringDate(this.inscriptionDate)
				+ ", Price: " + this.price 
				+ ", InscriptionType: " + this.inscriptionType 
				+ "}";
		
	}

	public boolean canBeCanceled() {
		return canBeCanceled;
	}


}
