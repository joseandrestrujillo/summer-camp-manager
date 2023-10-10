package domain.interfaces;

import java.util.Date;

import domain.entities.CompleteInscription;
import domain.entities.Inscription;
import domain.entities.PartialInscription;
import domain.values.InscriptionType;

public interface AbstractInscriptionFactory {
	public PartialInscription createPartial(int assistantId, int campId, Date inscriptionDate, float price);
	public CompleteInscription createComplete(int assistantId, int campId, Date inscriptionDate, float price);
}
