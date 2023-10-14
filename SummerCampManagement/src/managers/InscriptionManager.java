package managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import domain.entities.Activity;
import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.Inscription;
import domain.entities.Monitor;
import domain.exceptions.AfterEarlyTimeException;
import domain.exceptions.AssistantAlreadyEnrolledException;
import domain.exceptions.MaxAssistantExcededException;
import domain.exceptions.NeedToAddAnSpecialMonitorException;
import domain.exceptions.NotFoundException;
import domain.exceptions.WrongEducativeLevelException;
import domain.factories.EarlyRegisterInscriptionFactory;
import domain.factories.LateRegisterInscriptionFactory;
import domain.interfaces.AbstractInscriptionFactory;
import domain.interfaces.IRepository;
import domain.values.EducativeLevel;
import domain.values.TimeSlot;
import utilities.Utils;

/**
 * Esta clase gestiona las inscripciones en los campamentos y actividades.
 */

public class InscriptionManager {
	private IRepository<Camp, Integer> campRepository;
	private IRepository<Activity, String> activityRepository;
	private IRepository<Monitor, Integer> monitorRepository;
	private IRepository<Assistant, Integer> assitantRepository;
	private IRepository<Inscription, String> inscriptionRepository;

	  /**
     * Constructor de InscriptionManager.
     *
     * @param campRepository        Repositorio de campamentos.
     * @param activityRepository    Repositorio de actividades.
     * @param monitorRepository     Repositorio de monitores.
     * @param assistantRepository   Repositorio de asistentes.
     * @param inscriptionRepository Repositorio de inscripciones.
	 */

	public InscriptionManager(IRepository<Camp, Integer> campRepository, IRepository<Activity, String> activityRepository, IRepository<Monitor, Integer> monitorRepository, IRepository<Assistant, Integer> assitantRepository, IRepository<Inscription, String> inscriptionRepository) {
		this.campRepository = campRepository;
		this.activityRepository = activityRepository;
		this.monitorRepository = monitorRepository;
		this.assitantRepository = assitantRepository;
		this.inscriptionRepository = inscriptionRepository;
	}
	
	/**
     * Realiza la inscripción de un asistente en un campamento.
     *
     * @param assistantId           ID del asistente.
     * @param campId                ID del campamento.
     * @param inscriptionDate       Fecha de inscripción.
     * @param isPartial             Indica si la inscripción es parcial.
     * @param needSpecialAttention  Indica si se necesita atención especial.
     * @return La inscripción creada.
     */

	public Inscription enroll(int assistantId, int campId, Date inscriptionDate, 
			boolean isPartial, boolean needSpecialAttention) 
	{
		this.ensureEducativeLevelIsCorrect(assistantId, campId, inscriptionDate);
		this.ensureIfThereAreAnSpecialMonitorIfIsNecessary(campId, needSpecialAttention);
		this.ensureIfTheAssistantIsAlreadyEnrolled(assistantId, campId);
		this.ensureAnyActivityIsFully(campId, isPartial);
		
		float price = this.calculatePrice(campId, isPartial);
		
		Inscription inscription;
		try {
			EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(campRepository, assitantRepository);
			inscription = create(factory, assistantId, campId, inscriptionDate, isPartial, price);
		} catch (AfterEarlyTimeException e) {
			LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assitantRepository);
			inscription = create(factory, assistantId, campId, inscriptionDate, isPartial, price);
		}
		
		
		this.addAssistantToActivities(assistantId, campId, isPartial);
		this.inscriptionRepository.save(inscription);
		return inscription;
	}
	
	/**
	 * Crea una inscripción utilizando una fábrica de inscripciones.
	 *
	 * @param factory       Fábrica de inscripciones a utilizar.
	 * @param assistantId   ID del asistente.
	 * @param campId        ID del campamento.
	 * @param inscriptionDate Fecha de inscripción.
	 * @param isPartial     Indica si la inscripción es parcial.
	 * @param price         Precio de la inscripción.
	 * @return La inscripción creada.
	 */

	private Inscription create(AbstractInscriptionFactory factory, int assistantId, int campId, 
			Date inscriptionDate, boolean isPartial, float price ) {
		Inscription inscription;
		if (! isPartial) {
			inscription = factory.createComplete(assistantId, campId, inscriptionDate, price);
		} else {
			inscription = factory.createPartial(assistantId, campId, inscriptionDate, price);
		}
		return inscription;
	}
	
	/**
	 * Agrega al asistente a las actividades del campamento.
	 *
	 * @param assistantId   ID del asistente.
	 * @param campId        ID del campamento.
	 * @param isPartial     Indica si la inscripción es parcial.
	 */

	private void addAssistantToActivities(int assistantId, int campId, boolean isPartial) {
		Camp camp = this.campRepository.find(campId);
		Assistant assistant = this.assitantRepository.find(assistantId);
		
		List<Activity> activities = camp.getActivities();
		for (int i = 0; i < activities.size(); i++) {
			Activity activity = activities.get(i);
			TimeSlot timeSlot = activity.getTimeSlot();
			List<Assistant> assistants = activity.getAssistants();
			
			if (isPartial) {
				if ((! assistants.contains(assistant)) && (timeSlot == TimeSlot.MORNING)) {
					assistants.add(assistant);
				}
			} else {			
				if (! assistants.contains(assistant)) {
					assistants.add(assistant);
				}
			}
			
			this.activityRepository.save(activity);
		}
	}
	
	/**
	 * Verifica si el nivel educativo del asistente es correcto para el campamento.
	 *
	 * @param assistantId   ID del asistente.
	 * @param campId        ID del campamento.
	 * @param inscriptionDate Fecha de inscripción.
	 * @throws WrongEducativeLevelException Si el nivel educativo no es adecuado.
	 */

	private void ensureEducativeLevelIsCorrect(int assistantId, int campId, Date inscriptionDate) {
		Assistant assistant = this.assitantRepository.find(assistantId);
		Camp camp = this.campRepository.find(campId);
		
		EducativeLevel campLevel = camp.getEducativeLevel();
		
		EducativeLevel assistantLevel;
		long assistantAge = Utils.yearsBetween(inscriptionDate, assistant.getBirthDate());
		if ((assistantAge >= 4) && (assistantAge <= 6)) {
			assistantLevel = EducativeLevel.ELEMENTARY;
		} else if ((assistantAge >= 7) && (assistantAge <= 12)) {
			assistantLevel = EducativeLevel.PRESCHOOL;
		} else if ((assistantAge >= 13) && (assistantAge <= 17)) {
			assistantLevel = EducativeLevel.TEENAGER;
		} else {
			throw new WrongEducativeLevelException();
		}
		
		if (campLevel != assistantLevel) {
			throw new WrongEducativeLevelException();
		}
	}
	
	/**
	 * Verifica si se requiere un monitor especial para el campamento.
	 *
	 * @param campId                ID del campamento.
	 * @param needSpecialAttention  Indica si se necesita atención especial.
	 * @throws NeedToAddAnSpecialMonitorException Si no hay un monitor especial y se necesita atención especial.
	 */

	private void ensureIfThereAreAnSpecialMonitorIfIsNecessary(int campId, boolean needSpecialAttention) {
		Camp camp = campRepository.find(campId);
		if ((needSpecialAttention)&&(camp.getSpecialMonitor()== null)) {
			throw new NeedToAddAnSpecialMonitorException();
		}
	}
	
	/**
	 * Verifica si el asistente ya está inscrito en el campamento.
	 *
	 * @param assistantId   ID del asistente.
	 * @param campId        ID del campamento.
	 * @throws AssistantAlreadyEnrolledException Si el asistente ya está inscrito.
	 */

	private void ensureIfTheAssistantIsAlreadyEnrolled(int assistantId, int campId) {
		String inscriptionId = assistantId + "-" + campId;
		try {
			this.inscriptionRepository.find(inscriptionId);
			throw new AssistantAlreadyEnrolledException();
		} catch (NotFoundException e) {}
	}
	
	/**
	 * Calcula el precio de la inscripción en función del campamento y si es parcial.
	 *
	 * @param campId        ID del campamento.
	 * @param isPartial     Indica si la inscripción es parcial.
	 * @return El precio de la inscripción.
	 */

	private float calculatePrice(int campId, boolean isPartial) {
		Camp camp = this.campRepository.find(campId);
		
		int nActivities =camp.getActivities().size();
		float basePrice = isPartial ? 100 : 300;
		
		return basePrice + 20*nActivities;
	}
	
	/**
	 * Verifica si alguna actividad del campamento está completamente llena.
	 *
	 * @param campId        ID del campamento.
	 * @param isPartial     Indica si la inscripción es parcial.
	 * @throws MaxAssistantExcededException Si alguna actividad está completamente llena.
	 */

	private void ensureAnyActivityIsFully(int campId, boolean isPartial) {
		Camp camp = this.campRepository.find(campId);
		List<Activity> activities = camp.getActivities();
		for (int i = 0; i < activities.size(); i++) {
			Activity activity = activities.get(i);
			List<Assistant> assistants = activity.getAssistants();
			TimeSlot timeSlot = activity.getTimeSlot();
			if (isPartial) {
				if ((assistants.size() == activity.getMaxAssistants()) && (timeSlot == TimeSlot.MORNING)) {
					throw new MaxAssistantExcededException();
				}
			} else {			
				if (assistants.size() == activity.getMaxAssistants()) {
					throw new MaxAssistantExcededException();
				}
			}
		}
	}
	
	/**
 * Obtiene la lista de asistentes de un campamento a partir de las inscripciones.
 *
 * @param camp El campamento del que se desea obtener la lista de asistentes.
 * @return La lista de asistentes del campamento.
 */

	private List<Assistant> getAssistantsOfACamp(Camp camp) {
		List<Inscription> allInscriptions = this.inscriptionRepository.getAll();
		List<Assistant> assistantsOfCamp = new ArrayList<Assistant>();
		
		for (int i = 0; i < allInscriptions.size(); i++) {
			Inscription inscription = allInscriptions.get(i);
			if (inscription.getCampId() == camp.getCampID()) {
				Assistant assistant = this.assitantRepository.find(inscription.getAssistantId());
				assistantsOfCamp.add(assistant);
			}
			
		}
		
		return assistantsOfCamp;
	}

	/**
	 * Obtiene la lista de campamentos disponibles en una fecha dada.
	 *
	 * @param actualDate Fecha actual para la comparación.
	 * @return La lista de campamentos disponibles.
	 */

	public List<Camp> avaliableCamps(Date actualDate){
		List<Camp> allCamps = this.campRepository.getAll();
		List<Camp> avaliableCamps = new ArrayList<Camp>();
		
		for (int i = 0; i < allCamps.size(); i++) {
			Camp camp = allCamps.get(i);
			List<Assistant> assistants = getAssistantsOfACamp(camp);
			
			if (camp.getStart().after(actualDate) && (assistants.size() < camp.getCapacity())) {
				avaliableCamps.add(camp);
			}
		}
    	
		
		return avaliableCamps;
	}
}