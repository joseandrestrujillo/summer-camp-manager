package business.interfaces;

import java.util.List;

import business.dtos.ActivityDTO;
import business.dtos.CampDTO;

public interface IActivityDAO extends IDAO<ActivityDTO, String>{
	public List<ActivityDTO> getActivitiesInACamp(CampDTO camp);
	public void saveAndRelateWithACamp(ActivityDTO activity, CampDTO camp);
}
