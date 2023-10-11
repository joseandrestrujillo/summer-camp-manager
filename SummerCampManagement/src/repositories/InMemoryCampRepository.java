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

public class InMemoryCampRepository implements IRepository<Camp, Integer>{

	private Map<Integer, Camp> mapOfCamp;
	
	public InMemoryCampRepository() {
		this.mapOfCamp = new HashMap<Integer, Camp>();
	}
	
	@Override
	public Camp find(Integer identifier) {
		if (this.mapOfCamp.get(identifier) == null) {
			throw new NotFoundException();
		}
		return this.mapOfCamp.get(identifier);
	}

	@Override
	public void save(Camp obj) {
		this.mapOfCamp.put(obj.getCampID(), obj);
	}

	@Override
	public List<Camp> getAll() {
		return new ArrayList<Camp>(this.mapOfCamp.values());
	}

	@Override
	public void delete(Camp obj) {
		this.mapOfCamp.remove(obj.getCampID());
	}
}
