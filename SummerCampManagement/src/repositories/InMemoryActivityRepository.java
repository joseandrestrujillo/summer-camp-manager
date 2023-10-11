package repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Activity;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

public class InMemoryActivityRepository implements IRepository<Activity, String>{

	private Map<String, Activity> mapOfActivity;
	
	public InMemoryActivityRepository() {
		this.mapOfActivity = new HashMap<String, Activity>();
	}
	
	@Override
	public Activity find(String activityName) {
		if (this.mapOfActivity.get(activityName)==null) {
			throw new NotFoundException();
		}
		return this.mapOfActivity.get(activityName);
	}

	@Override
	public void save(Activity activity) {
		this.mapOfActivity.put(activity.getActivityName(), activity);
	}

	@Override
	public List<Activity> getAll() {
		return new ArrayList<Activity>(this.mapOfActivity.values());
	}


}
