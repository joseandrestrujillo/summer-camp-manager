package domain.interfaces;

import java.util.Date;

import domain.entities.Inscription;
import domain.values.InscriptionType;

public interface AbstractInscriptionFactory {
	public Inscription create(int assistantId, int campId, Date inscriptionDate, float price, InscriptionType inscriptionType);
}
