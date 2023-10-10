package managers;

import domain.entities.Assistant;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;
import repositories.InMemoryAssistantRepository;

public class AssistantsManager {
	private IRepository<Assistant> assistantRepository;
	
	public AssistantsManager(IRepository<Assistant> assistantRepository) {
		this.assistantRepository = assistantRepository;
	}
	
	public void registerAssistant(Assistant assistant) {
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

}
