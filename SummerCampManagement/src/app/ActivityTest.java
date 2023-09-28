package app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ActivityTest {
	
	@Test
	void testConstructorAndGetters() {
		String activityName = "Activity";
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		TimeSlot timeSlot = TimeSlot.AFTERNOON;
		int maxAssistants = 15;
		int neededMonitors = 2;
		
		Activity activity = new Activity(
				activityName,
				educativeLevel,
				timeSlot,
				maxAssistants,
				neededMonitors
		);
		
		assertEquals(activityName, activity.getActivityName());
        assertEquals(educativeLevel, activity.getEducativeLevel());
        assertEquals(timeSlot, activity.getTimeSlot());
        assertEquals(maxAssistants, activity.getMaxAssistants());
        assertEquals(neededMonitors, activity.getNeededMonitors());
        assert(activity.getMonitorList().isEmpty());
	}
	
	@Test
	void testDefaultConstructor() {
		String activityName = null;
		EducativeLevel educativeLevel = null;
		TimeSlot timeSlot = null;
		int maxAssistants = -1;
		int neededMonitors = -1;
		
		Activity activity = new Activity();
		
		assertEquals(activityName, activity.getActivityName());
        assertEquals(educativeLevel, activity.getEducativeLevel());
        assertEquals(timeSlot, activity.getTimeSlot());
        assertEquals(maxAssistants, activity.getMaxAssistants());
        assertEquals(neededMonitors, activity.getNeededMonitors());
        assertEquals(null, activity.getMonitorList());
	}
	
	@Test
	void testSetters() {
		Activity activity = new Activity();
		String activityName = "Activity";
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		TimeSlot timeSlot = TimeSlot.AFTERNOON;
		int maxAssistants = 15;
		int neededMonitors = 2;
		List<Monitor> monitors = new ArrayList<Monitor>(neededMonitors);
		
		activity.setActivityName(activityName);
		activity.setEducativeLevel(educativeLevel);
		activity.setTimeSlot(timeSlot);
		activity.setMaxAssistants(maxAssistants);
		activity.setNeededMonitors(neededMonitors);
		activity.setMonitorList(monitors);
		
		assertEquals(activityName, activity.getActivityName());
        assertEquals(educativeLevel, activity.getEducativeLevel());
        assertEquals(timeSlot, activity.getTimeSlot());
        assertEquals(maxAssistants, activity.getMaxAssistants());
        assertEquals(neededMonitors, activity.getNeededMonitors());
        assertEquals(monitors, activity.getMonitorList());
        
	}
	@Test
	public void testToString() {
		
        String activityName = "Activity";
        EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
        TimeSlot timeSlot = TimeSlot.AFTERNOON;
        int maxAssistants = 10;
        int neededMonitors = 3;
        List<Monitor> monitorList = new ArrayList<Monitor>(neededMonitors);

        Activity activity = new Activity(
                activityName,
                educativeLevel,
                timeSlot,
                maxAssistants,
                neededMonitors
        );

        String expectedToString = "{activityName: 'Activity', educativeLevel: PRESCHOOL, timeSlot: AFTERNOON, maxAssistants: 10, neededMonitors: 3}";
        assertEquals(expectedToString, activity.toString());
    }
}
