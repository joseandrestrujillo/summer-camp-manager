package domain.interfaces;

import java.util.Date;

import domain.entities.CompleteInscription;
import domain.entities.Inscription;
import domain.entities.PartialInscription;
import domain.values.InscriptionType;
/**
 * La interfaz AbstractInscriptionFactory define métodos para crear instancias de inscripciones parciales y completas.
 */
public interface AbstractInscriptionFactory {
	  /**
     * Crea una nueva instancia de PartialInscription con la información proporcionada.
     *
     * @param assistantId     El ID del asistente inscrito.
     * @param campId          El ID del campamento al que se ha inscrito el asistente.
     * @param inscriptionDate La fecha en que se realizó la inscripción.
     * @param price           El precio total de la inscripción.
     * @return Una nueva instancia de PartialInscription.
     */
	public PartialInscription createPartial(int assistantId, int campId, Date inscriptionDate, float price);

	/**
     * Crea una nueva instancia de CompleteInscription con la información proporcionada.
     *
     * @param assistantId     El ID del asistente inscrito.
     * @param campId          El ID del campamento al que se ha inscrito el asistente.
     * @param inscriptionDate La fecha en que se realizó la inscripción.
     * @param price           El precio total de la inscripción.
     * @return Una nueva instancia de CompleteInscription.
     */
	public CompleteInscription createComplete(int assistantId, int campId, Date inscriptionDate, float price);
}
