package repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Activity;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

public class InMemoryActivityRepository implements IRepository<Activity>{

	private Map<String, Activity> mapOfActivity;
	
	public InMemoryActivityRepository() {
		this.mapOfActivity = new HashMap<String, Activity>();
	}
	
	@Override
	public Activity findActivity(String activityName) {
		if (this.mapOfActivity.get(activityName)==null) {
			throw new NotFoundException();
		}
		return this.mapOfActivity.get(activityName);
	}

	@Override
	public void saveActivity(String activityName) {
		this.mapOfActivity.put(activityName, null);
	}

	@Override
	public List<Activity> getAll() {
		return new ArrayList<Activity>(this.mapOfActivity.values());
	}
	//------ME OBLIGA LA class a ponerlas aunque no se usen----
	//QUIZAS HAY QUE CREAR OTRO IRepository para las operaciones activity???
	@Override
	public Activity find(int identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Activity obj) {
		// TODO Auto-generated method stub
		
	}


}
