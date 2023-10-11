package managers;

import java.util.Date;

import domain.entities.Activity;
import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.CompleteInscription;
import domain.entities.Inscription;
import domain.entities.Monitor;
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
	
	public Inscription enroll(
			int assistantId, 
			int campId, 
			Date inscriptionDate, 
			boolean isPartial, 
			boolean needSpecialAttention
	) {
		return new CompleteInscription(assistantId, campId, inscriptionDate, 300, true);
	}
}
