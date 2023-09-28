package app;

import java.util.ArrayList;
import java.util.List;

public class Activity {
	private String activityName; 
	private EducativeLevel educativeLevel;
	private TimeSlot timeSlot;
	private int maxAssistants;
	private int neededMonitors;
	private List<Monitor> monitorList;

	public Activity(String activityName, EducativeLevel educativeLevel, TimeSlot timeSlot, int maxAssistants,
			int neededMonitors) {
		this.activityName = activityName;
		this.educativeLevel = educativeLevel;
		this.timeSlot = timeSlot;
		this.maxAssistants = maxAssistants;
		this.neededMonitors = neededMonitors;
		this.monitorList = new ArrayList<Monitor>(this.neededMonitors);
	}

	public Activity() {
		this.activityName = null;
		this.educativeLevel = null;
		this.timeSlot = null;
		this.maxAssistants = -1;
		this.neededMonitors = -1;
		this.monitorList = null;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public EducativeLevel getEducativeLevel() {
		return educativeLevel;
	}

	public void setEducativeLevel(EducativeLevel educativeLevel) {
		this.educativeLevel = educativeLevel;
	}

	public TimeSlot getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	public int getMaxAssistants() {
		return maxAssistants;
	}

	public void setMaxAssistants(int maxAssistants) {
		this.maxAssistants = maxAssistants;
	}
	
	public int getNeededMonitors() {
		return neededMonitors;
	}

	public void setNeededMonitors(int neededMonitors) {
		this.neededMonitors = neededMonitors;
	}

	public List<Monitor> getMonitorList() {
		return monitorList;
	}

	public void setMonitorList(List<Monitor> monitorList) {
		this.monitorList = monitorList;
	}

}