package business.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.dtos.MonitorDTO;
import business.exceptions.inscription.AfterEarlyTimeException;
import business.exceptions.inscription.AssistantAlreadyEnrolledException;
import business.exceptions.inscription.MaxAssistantExcededException;
import business.exceptions.inscription.NeedToAddAnSpecialMonitorException;
import business.exceptions.inscription.WrongEducativeLevelException;
import business.exceptions.repository.NotFoundException;
import business.factories.EarlyRegisterInscriptionFactory;
import business.factories.LateRegisterInscriptionFactory;
import business.interfaces.AbstractInscriptionFactory;
import business.interfaces.IDAO;
import business.values.EducativeLevel;
import business.values.TimeSlot;
import utilities.Utils;

/**
 * Esta clase gestiona las inscripciones en los campamentos y actividades.
 */

public class InscriptionManager {
	private IDAO<CampDTO, Integer> campRepository;
	private IDAO<ActivityDTO, String> activityRepository;
	private IDAO<MonitorDTO, Integer> monitorRepository;
	private IDAO<AssistantDTO, Integer> assitantRepository;
	private IDAO<InscriptionDTO, String> inscriptionRepository;

	  /**
     * Constructor de InscriptionManager.
     *
     * @param campRepository        Repositorio de campamentos.
     * @param activityRepository    Repositorio de actividades.
     * @param monitorRepository     Repositorio de monitores.
     * @param assitantRepository   Repositorio de asistentes.
     * @param inscriptionRepository Repositorio de inscripciones.
	 */

	public InscriptionManager(IDAO<CampDTO, Integer> campRepository, IDAO<ActivityDTO, String> activityRepository, IDAO<MonitorDTO, Integer> monitorRepository, IDAO<AssistantDTO, Integer> assitantRepository, IDAO<InscriptionDTO, String> inscriptionRepository) {
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

	public InscriptionDTO enroll(int assistantId, int campId, Date inscriptionDate, 
			boolean isPartial, boolean needSpecialAttention) 
	{
		this.ensureEducativeLevelIsCorrect(assistantId, campId, inscriptionDate);
		this.ensureIfThereAreAnSpecialMonitorIfIsNecessary(campId, needSpecialAttention);
		this.ensureIfTheAssistantIsAlreadyEnrolled(assistantId, campId);
		this.ensureAnyActivityIsFully(campId, isPartial);
		
		float price = this.calculatePrice(campId, isPartial);
		
		InscriptionDTO inscription;
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

	private InscriptionDTO create(AbstractInscriptionFactory factory, int assistantId, int campId, 
			Date inscriptionDate, boolean isPartial, float price ) {
		InscriptionDTO inscription;
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
		CampDTO camp = this.campRepository.find(campId);
		AssistantDTO assistant = this.assitantRepository.find(assistantId);
		
		List<ActivityDTO> activities = camp.getActivities();
		for (int i = 0; i < activities.size(); i++) {
			ActivityDTO activity = activities.get(i);
			TimeSlot timeSlot = activity.getTimeSlot();
			List<AssistantDTO> assistants = activity.getAssistants();
			
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
		AssistantDTO assistant = this.assitantRepository.find(assistantId);
		CampDTO camp = this.campRepository.find(campId);
		
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
		CampDTO camp = campRepository.find(campId);
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
		CampDTO camp = this.campRepository.find(campId);
		
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
		CampDTO camp = this.campRepository.find(campId);
		List<ActivityDTO> activities = camp.getActivities();
		for (int i = 0; i < activities.size(); i++) {
			ActivityDTO activity = activities.get(i);
			List<AssistantDTO> assistants = activity.getAssistants();
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

	private List<AssistantDTO> getAssistantsOfACamp(CampDTO camp) {
		List<InscriptionDTO> allInscriptions = this.inscriptionRepository.getAll();
		List<AssistantDTO> assistantsOfCamp = new ArrayList<AssistantDTO>();
		
		for (int i = 0; i < allInscriptions.size(); i++) {
			InscriptionDTO inscription = allInscriptions.get(i);
			if (inscription.getCampId() == camp.getCampID()) {
				AssistantDTO assistant = this.assitantRepository.find(inscription.getAssistantId());
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

	public List<CampDTO> avaliableCamps(Date actualDate){
		List<CampDTO> allCamps = this.campRepository.getAll();
		List<CampDTO> avaliableCamps = new ArrayList<CampDTO>();
		
		for (int i = 0; i < allCamps.size(); i++) {
			CampDTO camp = allCamps.get(i);
			List<AssistantDTO> assistants = getAssistantsOfACamp(camp);
			
			if (camp.getStart().after(actualDate) && (assistants.size() < camp.getCapacity())) {
				avaliableCamps.add(camp);
			}
		}
    	
		
		return avaliableCamps;
	}
}