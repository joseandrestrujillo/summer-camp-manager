package data_access;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Assistant;
import domain.Camp;
import domain.IRepository;
import utilities.NotFoundException;

public class InMemoryCampRepository implements IRepository<Camp>{

	private Map<Integer, Camp> mapOfCamp;
	
	public InMemoryCampRepository() {
		this.mapOfCamp = new HashMap<Integer, Camp>();
	}
	
	@Override
	public Camp find(int identifier) {
		if (this.mapOfCamp.get(identifier) == null) {
			throw new NotFoundException();
		}
		return this.mapOfCamp.get(identifier);
	}

	@Override
	public void save(Camp obj) {
		this.mapOfCamp.put(obj.getCampID(), obj);
	}
}
