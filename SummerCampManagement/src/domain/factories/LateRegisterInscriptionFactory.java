package domain.factories;

import java.util.Date;

import domain.entities.Inscription;
import domain.interfaces.AbstractInscriptionFactory;
import domain.values.InscriptionType;

public class LateRegisterInscriptionFactory implements AbstractInscriptionFactory{

	public Inscription create(int assistantId, int campId, Date inscriptionDate, float price,
			InscriptionType inscriptionType) {
		return null;
	}

}
