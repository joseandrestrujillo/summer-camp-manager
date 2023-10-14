package domain.entities;

import java.util.Date;

/**
 * La clase CompleteInscription representa una inscripción completa para un asistente en un campamento.
 * Es una subclase de Inscription.
 */
public class CompleteInscription extends Inscription{
	/**
     * Constructor para crear un objeto CompleteInscription con la información proporcionada.
     *
     * @param assistantId     
     * @param campId           
     * @param inscriptionDate   
     * @param price            
     * @param canBeCanceled     
     */
	public CompleteInscription(int assistantId, int campId, Date inscriptionDate, float price, boolean canBeCanceled) {
		super(assistantId, campId, inscriptionDate, price, canBeCanceled);
	}
}
