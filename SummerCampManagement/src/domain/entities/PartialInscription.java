package domain.entities;

import java.util.Date;

public class PartialInscription extends Inscription{

	public PartialInscription(int assistantId, int campId, Date inscriptionDate, float price, boolean canBeCanceled) {
		super(assistantId, campId, inscriptionDate, price, canBeCanceled);
	}

}
