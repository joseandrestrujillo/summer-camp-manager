package domain;

import java.util.Date;

import utilities.InscriptionType;

public abstract class AbstractInscriptionFactory {
	public abstract Inscription create(int assistantId, int campId, Date inscriptionDate, float price, InscriptionType inscriptionType);
}
