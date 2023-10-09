package domain;

import java.util.Date;

import utilities.InscriptionType;

public class EarlyRegisterInscriptionFactory extends AbstractInscriptionFactory{

	@Override
	public Inscription create(int assistantId, int campId, Date inscriptionDate, float price,
			InscriptionType inscriptionType) {
		return new Inscription(assistantId, campId, inscriptionDate, price, inscriptionType, true);
	}

}
