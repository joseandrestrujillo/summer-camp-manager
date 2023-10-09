package domain;

import java.util.Date;

import utilities.InscriptionType;

public class LateRegisterInscriptionFactory extends AbstractInscriptionFactory{

	@Override
	public Inscription create(int assistantId, int campId, Date inscriptionDate, float price,
			InscriptionType inscriptionType) {
		return null;
	}

}
