package domain.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import domain.exceptions.MonitorNotFoundException;
import domain.exceptions.NotTheSameLevelException;
import domain.exceptions.SpecialMonitorAlreadyRegisterException;
import domain.values.EducativeLevel;
import utilities.Utils;


public class Camp {
    private int campID;
    private Date start;
    private Date end;
    private EducativeLevel educativeLevel;
    private int capacity;
    private List<Activity> activities;
    private Monitor principalMonitor;
    private Monitor specialMonitor;

    public Camp(){
        this.campID = -1;
        this.start = null;
        this.end = null;
        this.educativeLevel = null;
        this.capacity = -1;
    }

    public Camp(int campID, Date start, Date end, EducativeLevel educativeLevel, int capacity){
        this.campID = campID;
        this.start = start;
        this.end = end;
        this.educativeLevel = educativeLevel;
        this.capacity = capacity;
        this.activities = new ArrayList<Activity>();
    }
    
    
	public boolean activityIsRegistered(Activity activity) {
		return this.activities.contains(activity);
	}
	
	public Monitor getPrincipalMonitor() {
		return this.principalMonitor;
	}
	
	public Monitor getSpecialMonitor() {
		return this.specialMonitor;
	}
	
    public int getCampID() {
        return campID;
    }

    public void setCampID(int campID) {
        this.campID = campID;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public EducativeLevel getEducativeLevel() {
        return educativeLevel;
    }

    public void setEducativeLevel(EducativeLevel educativeLevel) {
        this.educativeLevel = educativeLevel;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
    
		
    public String toString() {
        return 
        		"{campID: " + this.campID 
        		+ ", start: '" + Utils.getStringDate(start)
        		+ "', end: '" + Utils.getStringDate(end)
        		+ "', educativeLevel: " + this.educativeLevel 
        		+ ", capacity: " + this.capacity 
        		+ ", activities: " + this.activities.toString() 
        		+  "}";
    }

	public void setPrincipalMonitor(Monitor monitor) {
		this.principalMonitor = monitor;
	}

	public void setSpecialMonitor(Monitor monitor) {
		this.specialMonitor = monitor;
	}

	
}
