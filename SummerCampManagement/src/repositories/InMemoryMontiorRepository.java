package repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Monitor;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

public class InMemoryMontiorRepository implements IRepository<Monitor, Integer>{

	private Map<Integer, Monitor> mapOfMonitor;
	
	public InMemoryMontiorRepository() {
		this.mapOfMonitor = new HashMap<Integer, Monitor>();
	}
	
	@Override
	public Monitor find(Integer identifier) {
		if (this.mapOfMonitor.get(identifier) == null) {
			throw new NotFoundException();
		}
		return this.mapOfMonitor.get(identifier);
	}

	@Override
	public void save(Monitor obj) {
		this.mapOfMonitor.put(obj.getId(), obj);
	}

	@Override
	public List<Monitor> getAll() {
		return new ArrayList<Monitor>(this.mapOfMonitor.values());
	}

}