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

public class InscriptionManager {
	private IRepository<Camp, Integer> campRepository;
	private IRepository<Activity, String> activityRepository;
	private IRepository<Monitor, Integer> monitorRepository;
	private IRepository<Assistant, Integer> assitantRepository;
	private IRepository<Inscription, String> inscriptionRepository;

	
	public InscriptionManager(IRepository<Camp, Integer> campRepository, IRepository<Activity, String> activityRepository, IRepository<Monitor, Integer> monitorRepository, IRepository<Assistant, Integer> assitantRepository, IRepository<Inscription, String> inscriptionRepository) {
		this.campRepository = campRepository;
		this.activityRepository = activityRepository;
		this.monitorRepository = monitorRepository;
		this.assitantRepository = assitantRepository;
		this.inscriptionRepository = inscriptionRepository;
	}
	
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
	
	private void ensureIfThereAreAnSpecialMonitorIfIsNecessary(int campId, boolean needSpecialAttention) {
		Camp camp = campRepository.find(campId);
		if ((needSpecialAttention)&&(camp.getSpecialMonitor()== null)) {
			throw new NeedToAddAnSpecialMonitorException();
		}
	}
	
	private void ensureIfTheAssistantIsAlreadyEnrolled(int assistantId, int campId) {
		String inscriptionId = assistantId + "-" + campId;
		try {
			this.inscriptionRepository.find(inscriptionId);
			throw new AssistantAlreadyEnrolledException();
		} catch (NotFoundException e) {}
	}
	
	private float calculatePrice(int campId, boolean isPartial) {
		Camp camp = this.campRepository.find(campId);
		
		int nActivities =camp.getActivities().size();
		float basePrice = isPartial ? 100 : 300;
		
		return basePrice + 20*nActivities;
	}
	
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