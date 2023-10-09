package repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Assistant;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

public class InMemoryAssistantRepository implements IRepository<Assistant>{

	private Map<Integer, Assistant> mapOfAssistants;
	
	public InMemoryAssistantRepository() {
		this.mapOfAssistants = new HashMap<Integer, Assistant>();
	}
	
	@Override
	public Assistant find(int identifier) {
		if (this.mapOfAssistants.get(identifier) == null) {
			throw new NotFoundException();
		}
		return this.mapOfAssistants.get(identifier);
	}

	@Override
	public void save(Assistant obj) {
		this.mapOfAssistants.put(obj.getId(), obj);
	}

}
