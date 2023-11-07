package business.interfaces;

import java.util.List;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.MonitorDTO;

public interface IMonitorDAO extends IDAO<MonitorDTO, Integer>{
	public List<MonitorDTO> getMonitorsInAnActivity(ActivityDTO activity);
	public void saveAndRelateWithAnActivity(MonitorDTO monitor, ActivityDTO activity);
}
