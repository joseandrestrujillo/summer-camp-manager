package business.interfaces;

import java.util.List;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;

public interface IAssistantDAO extends IDAO<AssistantDTO, Integer>{
	public List<AssistantDTO> getAssistantsInACamp(CampDTO camp);
	public List<AssistantDTO> getAssistantsInAnActivity(ActivityDTO activity);
}
