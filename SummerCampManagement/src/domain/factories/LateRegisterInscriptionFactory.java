package domain.factories;

import java.util.Date;

import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.CompleteInscription;
import domain.entities.PartialInscription;
import domain.exceptions.AfterLateTimeException;
import domain.exceptions.AfterStartTimeException;
import domain.exceptions.AssistantNotFoundException;
import domain.exceptions.BeforeLateTimeException;
import domain.exceptions.CampNotFoundException;
import domain.exceptions.NotFoundException;
import domain.interfaces.AbstractInscriptionFactory;
import domain.interfaces.IRepository;
import utilities.Utils;

/**
 * La clase LateRegisterInscriptionFactory es una fábrica que crea inscripciones
 * tardías (late registration) para campamentos. Estas inscripciones se
 * caracterizan por reglas especiales relacionadas con el tiempo y el precio.
 */
public class LateRegisterInscriptionFactory implements AbstractInscriptionFactory {
    private IRepository<Camp, Integer> campRepository;
    private IRepository<Assistant, Integer> assistantRepository;

    /**
     * Constructor para LateRegisterInscriptionFactory.
     *
     * @param campRepository        Repositorio de campamentos.
     * @param assistantRepository   Repositorio de asistentes.
     */
    public LateRegisterInscriptionFactory(IRepository<Camp, Integer> campRepository, IRepository<Assistant, Integer> assistanRepository) {
        this.campRepository = campRepository;
        this.assistantRepository = assistanRepository;
    }

    /**
     * Crea una inscripción parcial tardía para un asistente en un campamento, siguiendo las reglas de late registration.
     *
     * @param assistantId      ID del asistente.
     * @param campId           ID del campamento.
     * @param inscriptionDate  Fecha de inscripción.
     * @param price            Precio de la inscripción.
     * @return Una inscripción parcial creada de acuerdo con las reglas de late registration.
     * @throws AssistantNotFoundException   Si no se encuentra al asistente en el repositorio.
     * @throws CampNotFoundException        Si no se encuentra el campamento en el repositorio.
     * @throws BeforeLateTimeException     Si la inscripción se realiza antes del tiempo permitido para late registration.
     * @throws AfterStartTimeException     Si la inscripción se realiza después del inicio del campamento.
     * @throws AfterLateTimeException      Si la inscripción se realiza después de un cierto tiempo de late registration.
     */
    @Override
    public PartialInscription createPartial(int assistantId, int campId, Date inscriptionDate, float price) {
        try {
            this.assistantRepository.find(assistantId);
        } catch (NotFoundException e) {
            throw new AssistantNotFoundException();
        }

        try {
            Camp camp = this.campRepository.find(campId);

            long daysDifference = Utils.daysBetween(camp.getStart(), inscriptionDate);

            if (daysDifference > 15) {
                throw new BeforeLateTimeException();
            } else if (daysDifference < 0) {
                throw new AfterStartTimeException();
            } else if (daysDifference <= 2) {
                throw new AfterLateTimeException();
            }

            return new PartialInscription(assistantId, campId, inscriptionDate, price, false);

        } catch (NotFoundException e) {
            throw new CampNotFoundException();
        }
    }

    /**
     * Crea una inscripción completa tardía para un asistente en un campamento, siguiendo las reglas de late registration.
     *
     * @param assistantId      ID del asistente.
     * @param campId           ID del campamento.
     * @param inscriptionDate  Fecha de inscripción.
     * @param price            Precio de la inscripción.
     * @return Una inscripción completa creada de acuerdo con las reglas de late registration.
     * @throws AssistantNotFoundException   Si no se encuentra al asistente en el repositorio.
     * @throws CampNotFoundException        Si no se encuentra el campamento en el repositorio.
     * @throws BeforeLateTimeException     Si la inscripción se realiza antes del tiempo permitido para late registration.
     * @throws AfterStartTimeException     Si la inscripción se realiza después del inicio del campamento.
     * @throws AfterLateTimeException      Si la inscripción se realiza después de un cierto tiempo de late registration.
     */
    @Override
    public CompleteInscription createComplete(int assistantId, int campId, Date inscriptionDate, float price) {
        try {
            this.assistantRepository.find(assistantId);
        } catch (NotFoundException e) {
            throw new AssistantNotFoundException();
        }

        try {
            Camp camp = this.campRepository.find(campId);

            long daysDifference = Utils.daysBetween(camp.getStart(), inscriptionDate);

            if (daysDifference > 15) {
                throw new BeforeLateTimeException();
            } else if (daysDifference < 0) {
                throw new AfterStartTimeException();
            } else if (daysDifference <= 2) {
                throw new AfterLateTimeException();
            }

            return new CompleteInscription(assistantId, campId, inscriptionDate, price, false);

        } catch (NotFoundException e) {
            throw new CampNotFoundException();
        }
    }
}
