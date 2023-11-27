package display.web;

import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.dtos.UserDTO;
import business.interfaces.IActivityDAO;
import business.interfaces.IAssistantDAO;
import business.interfaces.IDAO;
import business.interfaces.IMonitorDAO;
import business.managers.AssistantsManager;
import business.managers.CampsManager;
import business.managers.InscriptionManager;
import data.database.daos.*;
import data.memory.daos.*;

public class Container {
	private static Container _instance;
	private IDAO<CampDTO, Integer> campRepository;
	private IActivityDAO activityRepository;
	private IMonitorDAO monitorRepository;
	private IAssistantDAO assistantRepository;
	private IDAO<InscriptionDTO, String> inscriptionRepository;
	private IDAO<UserDTO, String> userRepository;
	
	private AssistantsManager assistantsManager;
	private CampsManager campsManager;
	private InscriptionManager inscriptionManager;
	
	
	public static Container getInstance() {
		if (_instance == null) {
			_instance = new Container();
		}
		return _instance;
	}
	
	private Container() {
		this.configureDatabasePersistence();
		this.setUpManagers();
	}
	
	@SuppressWarnings("unused")
	private void configureDatabasePersistence() {
		this.campRepository = new InDatabaseCampDAO();
		this.activityRepository = new InDatabaseActivityDAO();
		this.monitorRepository = new InDatabaseMonitorDAO();
		this.assistantRepository = new InDatabaseAssistantDAO();
		this.inscriptionRepository = new InDatabaseInscriptionDAO();
		this.userRepository = new InDatabaseUserDAO();
	}
	
	@SuppressWarnings("unused")
	private void configureMemoryPersistence() {
		this.campRepository = new InMemoryCampDAO();
		this.activityRepository = new InMemoryActivityDAO();
		this.monitorRepository = new InMemoryMontiorDAO();
		this.assistantRepository = new InMemoryAssistantDAO();
		this.inscriptionRepository = new InMemoryInscriptionDAO();
		this.userRepository = new InMemoryUserDAO();
	}
	
	private void setUpManagers() {		
		this.assistantsManager = new AssistantsManager(this.assistantRepository);
		this.campsManager = new CampsManager(this.campRepository, this.activityRepository, this.monitorRepository);
		this.inscriptionManager = new InscriptionManager(this.campRepository, this.activityRepository, this.assistantRepository, this.inscriptionRepository);
	}

	public AssistantsManager getAssistantsManager() {
		return assistantsManager;
	}

	public CampsManager getCampsManager() {
		return campsManager;
	}

	public InscriptionManager getInscriptionManager() {
		return inscriptionManager;
	}
}
