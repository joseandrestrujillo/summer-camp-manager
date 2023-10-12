package managers;

import java.util.Date;

import domain.entities.Activity;
import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.CompleteInscription;
import domain.entities.Inscription;
import domain.entities.Monitor;
import domain.exceptions.AfterEarlyTimeException;
import domain.exceptions.AssistantAlreadyEnrolledException;
import domain.exceptions.NotFoundException;
import domain.factories.EarlyRegisterInscriptionFactory;
import domain.factories.LateRegisterInscriptionFactory;
import domain.interfaces.AbstractInscriptionFactory;
import domain.interfaces.IRepository;

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
	private Inscription create(
			AbstractInscriptionFactory factory,
			int assistantId, 
			int campId, 
			Date inscriptionDate, 
			boolean isPartial, 
			float price ) {
		Inscription inscription;
		if (! isPartial) {
			inscription = factory.createComplete(assistantId, campId, inscriptionDate, price);
		} else {
			inscription = factory.createPartial(assistantId, campId, inscriptionDate, price);
		}
		return inscription;
	}
	public Inscription enroll(
			int assistantId, 
			int campId, 
			Date inscriptionDate, 
			boolean isPartial, 
			boolean needSpecialAttention
	) {
		String inscriptionId = assistantId + "-" + campId;
		int nActivities = campRepository.find(campId).getActivities().size();
		float basePrice = isPartial ? 100 : 300;
		float price = basePrice + 20*nActivities;
		
		try {
			this.inscriptionRepository.find(inscriptionId);
			throw new AssistantAlreadyEnrolledException();
		} catch (NotFoundException e) {}
		
		Inscription inscription;
		
		try {
			EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(campRepository, assitantRepository);
			inscription = create(factory, assistantId, campId, inscriptionDate, isPartial, price);
		} catch (AfterEarlyTimeException e) {
			LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assitantRepository);
			inscription = create(factory, assistantId, campId, inscriptionDate, isPartial, price);
		}
		
		this.inscriptionRepository.save(inscription);
		return inscription;
	}
}