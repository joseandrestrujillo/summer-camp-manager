package domain;

import java.util.Date;

import utilities.InscriptionType;

public class Inscription {
	private int assistantId;
	private int campId;
	private Date inscriptionDate;
	private float price;
	private InscriptionType inscriptionType;
	
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


}
