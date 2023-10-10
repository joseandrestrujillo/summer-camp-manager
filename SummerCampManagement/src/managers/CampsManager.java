package managers;

import java.util.List;

import domain.entities.Camp;
import domain.exceptions.CampAlreadyRegisteredException;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

public class CampsManager {
	private IRepository<Camp> campIRepository;
	
	public CampsManager(IRepository<Camp> campIRepository) {
		this.campIRepository = campIRepository;
	}
	
	public void registerCamp(Camp camp) {
		if (isRegistered(camp) == true) {
			throw new CampAlreadyRegisteredException();
		}
		this.campIRepository.save(camp);
	}
	public boolean isRegistered(Camp camp) {
		try {
			this.campIRepository.find(camp.getCampID());
			return true;
		} catch (NotFoundException e) {
			return false;
		}	}
	
	
//	
//	public void updateAssistant(Assistant assistant) {
//		if (isRegistered(Camp) == false) {
//			throw new AssistantNotFoundException();
//		}
//		this.assistantRepository.save(assistant);
//	}
//	
//	
//	
//	public List<Assistant> getListOfRegisteredAssistant(){
//		return this.assistantRepository.getAll();
//	}

}
