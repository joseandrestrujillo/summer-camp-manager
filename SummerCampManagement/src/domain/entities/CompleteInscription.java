package domain.entities;

import java.util.Date;

public class CompleteInscription extends Inscription{
	public CompleteInscription(int assistantId, int campId, Date inscriptionDate, float price, boolean canBeCanceled) {
		super(assistantId, campId, inscriptionDate, price, canBeCanceled);
	}
}
