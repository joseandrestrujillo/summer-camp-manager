package data.memory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.dtos.MonitorDTO;

public class MapsManager {
	private static MapsManager instance;
	private Map<String, ActivityDTO> mapOfActivity;
    private Map<Integer, List<ActivityDTO>> mapOfActivityCamp;
    private Map<Integer, AssistantDTO> mapOfAssistants;
    private Map<Integer, CampDTO> mapOfCamp;
    private Map<String, InscriptionDTO> mapOfInscription;
    private Map<Integer, MonitorDTO> mapOfMonitor;
    private Map<String, List<MonitorDTO>> mapOfMonitorActivity;
	
	
	public static MapsManager getInstance() {
		if (instance == null) {
			instance = new MapsManager();
		}
		return instance;
	}
	
	public static void resetInstance() {
		instance = new MapsManager();
	}
	
	private MapsManager() {
		this.mapOfActivity = new HashMap<String, ActivityDTO>();
        this.mapOfActivityCamp = new HashMap<Integer, List<ActivityDTO>>();
        this.mapOfAssistants = new HashMap<Integer, AssistantDTO>();
        this.mapOfCamp = new HashMap<Integer, CampDTO>();
        this.mapOfInscription = new HashMap<String, InscriptionDTO>();
        this.mapOfMonitor = new HashMap<Integer, MonitorDTO>();
        this.mapOfMonitorActivity = new HashMap<String, List<MonitorDTO>>();
	}
	
	public Map<String, ActivityDTO> getMapOfActivity() {
		return mapOfActivity;
	}

	public void setMapOfActivity(Map<String, ActivityDTO> mapOfActivity) {
		this.mapOfActivity = mapOfActivity;
	}

	public Map<Integer, List<ActivityDTO>> getMapOfActivityCamp() {
		return mapOfActivityCamp;
	}

	public void setMapOfActivityCamp(Map<Integer, List<ActivityDTO>> mapOfActivityCamp) {
		this.mapOfActivityCamp = mapOfActivityCamp;
	}

	public Map<Integer, AssistantDTO> getMapOfAssistants() {
		return mapOfAssistants;
	}

	public void setMapOfAssistants(Map<Integer, AssistantDTO> mapOfAssistants) {
		this.mapOfAssistants = mapOfAssistants;
	}

	public Map<Integer, CampDTO> getMapOfCamp() {
		return mapOfCamp;
	}

	public void setMapOfCamp(Map<Integer, CampDTO> mapOfCamp) {
		this.mapOfCamp = mapOfCamp;
	}

	public Map<String, InscriptionDTO> getMapOfInscription() {
		return mapOfInscription;
	}

	public void setMapOfInscription(Map<String, InscriptionDTO> mapOfInscription) {
		this.mapOfInscription = mapOfInscription;
	}

	public Map<Integer, MonitorDTO> getMapOfMonitor() {
		return mapOfMonitor;
	}

	public void setMapOfMonitor(Map<Integer, MonitorDTO> mapOfMonitor) {
		this.mapOfMonitor = mapOfMonitor;
	}

	public Map<String, List<MonitorDTO>> getMapOfMonitorActivity() {
		return mapOfMonitorActivity;
	}

	public void setMapOfMonitorActivity(Map<String, List<MonitorDTO>> mapOfMonitorActivity) {
		this.mapOfMonitorActivity = mapOfMonitorActivity;
	}
}
