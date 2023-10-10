package managers;

import java.util.List;

import domain.entities.Activity;
import domain.entities.Camp;
import domain.entities.Monitor;
import domain.exceptions.CampAlreadyRegisteredException;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

public class CampsManager {
	private IRepository<Camp> campIRepository;
	//---------------ESTO LO HA HECHO MIGUEL, NOSE SI ESTARA BIEN XDDD
	private IRepository<Activity> activityRepository;
	private IRepository<Monitor> monitorIRepository;
	//-----------------------------------------------------
	
	public CampsManager(IRepository<Camp> campIRepository) {
		this.campIRepository = campIRepository;
	}
	public void registerCamp(Camp camp) {
		if (isRegistered(camp) == true) {
			throw new CampAlreadyRegisteredException();
		}
		this.campIRepository.save(camp);
	}
	public boolean isRegistered(Camp camp) {
		try {
			this.campIRepository.find(camp.getCampID());
			return true;
		} catch (NotFoundException e) {
			return false;
		}	
	}
	//---------------Y ESTO---------------
	public void registerActivity(Activity activity) {
		if (isRegistered(activity) == true) {
			throw new CampAlreadyRegisteredException();
		}
		this.activityRepository.save(activity);
	}
	public void registerMonitor(Monitor monitor) {
		if (isRegistered(monitor) == true) {
			throw new CampAlreadyRegisteredException();
		}
		this.monitorIRepository.save(monitor);
	}
	//-------------------------------------
	public boolean isRegistered(Activity activity) {
		try {
			this.activityRepository.findActivity(activity.getActivityName());
			return true;
		} catch (NotFoundException e) {
			return false;
		}	
	}
	public boolean isRegistered(Monitor monitor) {
		try {
			this.monitorIRepository.find(monitor.getId());
			return true;
		} catch (NotFoundException e) {
			return false;
		}	
	}
//	
//	public void updateAssistant(Assistant assistant) {
//		if (isRegistered(Camp) == false) {
//			throw new AssistantNotFoundException();
//		}
//		this.assistantRepository.save(assistant);
//	}
//	
//	
//	
//	public List<Assistant> getListOfRegisteredAssistant(){
//		return this.assistantRepository.getAll();
//	}

}
