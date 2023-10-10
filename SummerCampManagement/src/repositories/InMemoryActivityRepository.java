package repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Activity;
import domain.entities.Assistant;
import domain.entities.Camp;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

public class InMemoryActivityRepository implements IRepository<Activity>{

	private Map<Integer, Activity> mapOfActivity;
	
	public InMemoryActivityRepository() {
		this.mapOfActivity = new HashMap<Integer, Activity>();
	}
	
	@Override
	public Activity find(int identifier) {
		if (this.mapOfActivity.get(identifier) == null) {
			throw new NotFoundException();
		}
		return this.mapOfActivity.get(identifier);
	}

	@Override
	public void save(Activity obj) {
		this.mapOfActivity.put(obj.getCampID(), obj);
	}

	@Override
	public List<Activity> getAll() {
		return new ArrayList<Activity>(this.mapOfActivity.values());
	}

	@Override
	public ArrayList<Activity> findActivity(String identifier) {
		// TODO Auto-generated method stub
		return new ArrayList<Activity>(this.mapOfActivity.values());
	}
}
