package business.interfaces;

import java.util.Date;

import business.dtos.CompleteInscriptionDTO;
import business.dtos.InscriptionDTO;
import business.dtos.PartialInscriptionDTO;
import business.values.InscriptionType;
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
	public PartialInscriptionDTO createPartial(int assistantId, int campId, Date inscriptionDate, float price);

	/**
     * Crea una nueva instancia de CompleteInscription con la información proporcionada.
     *
     * @param assistantId     El ID del asistente inscrito.
     * @param campId          El ID del campamento al que se ha inscrito el asistente.
     * @param inscriptionDate La fecha en que se realizó la inscripción.
     * @param price           El precio total de la inscripción.
     * @return Una nueva instancia de CompleteInscription.
     */
	public CompleteInscriptionDTO createComplete(int assistantId, int campId, Date inscriptionDate, float price);
}
