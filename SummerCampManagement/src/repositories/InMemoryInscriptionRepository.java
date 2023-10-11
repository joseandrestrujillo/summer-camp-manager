package repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Inscription;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

public class InMemoryInscriptionRepository implements IRepository<Inscription, String>{

	private Map<String, Inscription> mapOfInscription;
	
	public InMemoryInscriptionRepository() {
		this.mapOfInscription = new HashMap<String, Inscription>();
	}
	
	@Override
	public Inscription find(String inscriptionName) {
		if (this.mapOfInscription.get(inscriptionName)==null) {
			throw new NotFoundException();
		}
		return this.mapOfInscription.get(inscriptionName);
	}

	@Override
	public void save(Inscription activity) {
		this.mapOfInscription.put(activity.getInscriptionIdentifier(), activity);
	}

	@Override
	public List<Inscription> getAll() {
		return new ArrayList<Inscription>(this.mapOfInscription.values());
	}

	@Override
	public void delete(Inscription obj) {
		this.mapOfInscription.remove(obj.getInscriptionIdentifier());
	}


}
