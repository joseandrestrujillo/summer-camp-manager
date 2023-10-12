package managers;

import java.util.List;

import domain.entities.Activity;
import domain.entities.Camp;
import domain.entities.Monitor;
import domain.exceptions.ActivityNotFoundException;
import domain.exceptions.AssistantAlreadyRegisteredException;
import domain.exceptions.CampAlreadyRegisteredException;
import domain.exceptions.MonitorIsNotInActivityException;
import domain.exceptions.NotFoundException;
import domain.exceptions.NotTheSameLevelException;
import domain.exceptions.SpecialMonitorAlreadyRegisterException;
import domain.interfaces.IRepository;

public class CampsManager {
	private IRepository<Camp, Integer> campRepository;
	private IRepository<Activity, String> activityRepository;
	private IRepository<Monitor, Integer> monitorRepository;
	
	public CampsManager(IRepository<Camp, Integer> campRepository, IRepository<Activity, String> activityRepository, IRepository<Monitor, Integer> monitorRepository) {
		this.campRepository = campRepository;
		this.activityRepository = activityRepository;
		this.monitorRepository = monitorRepository;
	}
	
	public Camp registerActivity(Camp camp, Activity activity) {
		if(activity.getEducativeLevel() != camp.getEducativeLevel()) {
    		throw new NotTheSameLevelException();
    	}
    	
    	List<Activity> activities = camp.getActivities();
		activities.add(activity);
		
		camp.setActivities(activities);
		
		this.activityRepository.save(activity);
		this.campRepository.save(camp);
		return camp;
	}
	
	public boolean isMonitorInSomeActivity(Camp camp, Monitor monitor) {
    	boolean founded = false;

		List<Activity> activities = camp.getActivities();
    	for (int i = 0; i < activities.size(); i++) {
    	    Activity activity = activities.get(i);
    	    if(activity.monitorIsRegistered(monitor)) {
    	    	founded = true;
    	    }
    	}
    	return founded;
	}
	
	public Camp setPrincipalMonitor(Camp camp, Monitor monitor) {
    	boolean founded = isMonitorInSomeActivity(camp, monitor);
    
    	if(founded == false) {
    		throw new MonitorIsNotInActivityException();
    	}
    	camp.setPrincipalMonitor(monitor);
    	
    	this.monitorRepository.save(monitor);
    	this.campRepository.save(camp);
    	return camp;
    }
    
	public Camp setSpecialMonitor(Camp camp, Monitor monitor) {
    	boolean founded = isMonitorInSomeActivity(camp, monitor);
        
    	if(founded == true) {
    		throw new SpecialMonitorAlreadyRegisterException();
    	}
    	
    	camp.setSpecialMonitor(monitor);
    	
    	this.monitorRepository.save(monitor);
    	this.campRepository.save(camp);
    	return camp;
    }

	public Camp deleteActivity(Camp camp, Activity activity) {
		
		List<Activity> activities = camp.getActivities();
		if(! activities.contains(activity)) {
			throw new ActivityNotFoundException();
		}
		
		activities.remove(activity);
		
		Camp auxCamp = camp;
		auxCamp.setActivities(activities);
		
		Monitor principalMonitor = auxCamp.getPrincipalMonitor();
		if (principalMonitor != null) {
			if (! isMonitorInSomeActivity(auxCamp, principalMonitor)) {
				throw new MonitorIsNotInActivityException();
			}
		}
		
		camp = auxCamp;
		
		this.activityRepository.delete(activity);
		this.campRepository.save(camp);
		return camp;
	}

	public boolean isRegistered(Camp camp) {
		try {
			this.campRepository.find(camp.getCampID());
			return true;
		} catch (NotFoundException e) {
			return false;
		}
	}

	public void registerCamp(Camp camp) {
		if (isRegistered(camp) == true) {
			throw new CampAlreadyRegisteredException();
		}
		this.campRepository.save(camp);
	}
}
