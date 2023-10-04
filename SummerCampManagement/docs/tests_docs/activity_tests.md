# P1 EJ1:
## Clase Activity
### Constructor parametrizado
``` public Activity(String activityName, EducativeLevel educativeLevel, TimeSlot timeSlot, int maxAssistants,
			int neededMonitors) {
		this.activityName = activityName;
		this.educativeLevel = educativeLevel;
		this.timeSlot = timeSlot;
		this.maxAssistants = maxAssistants;
		this.neededMonitors = neededMonitors;
		this.monitorList = new ArrayList<Monitor>(this.neededMonitors);
	}
```
### Constructor vacio
```
	public Activity() {
		this.activityName = null;
		this.educativeLevel = null;
		this.timeSlot = null;
		this.maxAssistants = -1;
		this.neededMonitors = -1;
		this.monitorList = null;
	}
``` 