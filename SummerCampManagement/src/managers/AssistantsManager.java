package managers;

import java.util.List;

import domain.entities.Assistant;
import domain.exceptions.AssistantAlreadyRegisteredException;
import domain.exceptions.AssistantNotFoundException;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

public class AssistantsManager {
	private IRepository<Assistant, Integer> assistantRepository;
	
	public AssistantsManager(IRepository<Assistant, Integer> assistantRepository) {
		this.assistantRepository = assistantRepository;
	}
	
	public void registerAssistant(Assistant assistant) {
		if (isRegistered(assistant) == true) {
			throw new AssistantAlreadyRegisteredException();
		}
		this.assistantRepository.save(assistant);
	}
	
	public void updateAssistant(Assistant assistant) {
		if (isRegistered(assistant) == false) {
			throw new AssistantNotFoundException();
		}
		this.assistantRepository.save(assistant);
	}
	
	public boolean isRegistered(Assistant assistant) {
		try {
			this.assistantRepository.find(assistant.getId());
			return true;
		} catch (NotFoundException e) {
			return false;
		}
	}
	
	public List<Assistant> getListOfRegisteredAssistant(){
		return this.assistantRepository.getAll();
	}

}
