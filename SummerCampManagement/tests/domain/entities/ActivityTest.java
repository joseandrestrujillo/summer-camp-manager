package domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import domain.entities.Activity;
import domain.entities.Monitor;
import domain.exceptions.MaxMonitorsAddedException;
import domain.values.EducativeLevel;
import domain.values.TimeSlot;
import utilities.Utils;

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

        Activity activity = new Activity(
                activityName,
                educativeLevel,
                timeSlot,
                maxAssistants,
                neededMonitors
        );

        List<Monitor> monitors = new ArrayList<Monitor>();
        monitors.add(new Monitor(1, "juan", "perez", true));
        monitors.add(new Monitor(2, "juana", "perez", false));
        
        activity.setMonitorList(monitors);
        
        List<Assistant> assistants = new ArrayList<Assistant>();
        assistants.add(new Assistant(1, "juan", "perez", Utils.parseDate("26/01/2001"), true));
        assistants.add(new Assistant(2, "juana", "perez", Utils.parseDate("26/01/2001"), false));
        
        activity.setAssistants(assistants);
        
        String expectedToString = "{activityName: 'Activity', "
        		+ "educativeLevel: PRESCHOOL, "
        		+ "timeSlot: AFTERNOON, "
        		+ "maxAssistants: 10, "
        		+ "neededMonitors: 3, "
        		+ "assistants: ["
        		+ "{id: 1, firstName: 'juan', lastName: 'perez', birthDate: 26/01/2001, requireSpecialAttention: true}, "
        		+ "{id: 2, firstName: 'juana', lastName: 'perez', birthDate: 26/01/2001, requireSpecialAttention: false}"
        		+ "], "
        		+ "monitors: ["
        		+ "{id: 1, firstName: 'juan', lastName: 'perez', isSpecialEducator: true}, "
        		+ "{id: 2, firstName: 'juana', lastName: 'perez', isSpecialEducator: false}"
        		+ "]"
        		+ "}";
        assertEquals(expectedToString, activity.toString());
    }

	@Test
    public void testFromString() {
		String activityName = "Activity";
        EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
        TimeSlot timeSlot = TimeSlot.AFTERNOON;
        int maxAssistants = 10;
        int neededMonitors = 3;
        List<Monitor> monitors = new ArrayList<Monitor>();
        monitors.add(new Monitor(1, "juan", "perez", true));
        monitors.add(new Monitor(2, "juana", "perez", false));
        
        List<Assistant> assistants = new ArrayList<Assistant>();
        assistants.add(new Assistant(1, "juan", "perez", Utils.parseDate("26/01/2001"), true));
        assistants.add(new Assistant(2, "juana", "perez", Utils.parseDate("26/01/2001"), false));
		

        String inputString = "{activityName: 'Activity', "
        		+ "educativeLevel: PRESCHOOL, "
        		+ "timeSlot: AFTERNOON, "
        		+ "maxAssistants: 10, "
        		+ "neededMonitors: 3, "
        		+ "assistants: ["
        		+ "{id: 1, firstName: 'juan', lastName: 'perez', birthDate: 26/01/2001, requireSpecialAttention: true}, "
        		+ "{id: 2, firstName: 'juana', lastName: 'perez', birthDate: 26/01/2001, requireSpecialAttention: false}"
        		+ "], "
        		+ "monitors: ["
        		+ "{id: 1, firstName: 'juan', lastName: 'perez', isSpecialEducator: true}, "
        		+ "{id: 2, firstName: 'juana', lastName: 'perez', isSpecialEducator: false}"
        		+ "]"
        		+ "}";
        
        Activity activity = Activity.fromString(inputString);
        assertEquals(activityName, activity.getActivityName());
        assertEquals(educativeLevel, activity.getEducativeLevel());
        assertEquals(maxAssistants, activity.getMaxAssistants());
        assertEquals(neededMonitors, activity.getNeededMonitors());
        assertEquals(assistants.toString(), activity.getAssistants().toString());
        assertEquals(monitors.toString(), activity.getMonitorList().toString());
    }
	
	@Test
	public void testAddMonitor(){
		Monitor monitor = new Monitor(
				1,
				"Alberto",
				"Quesada",
				true
		);

		Monitor monitor2 = new Monitor(
				2,
				"Francisco",
				"Ruíz",
				false
		);

		String activityName = "Activity";
        EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
        TimeSlot timeSlot = TimeSlot.AFTERNOON;
        int maxAssistants = 10;
        int neededMonitors = 3;

        Activity activity = new Activity(
                activityName,
                educativeLevel,
                timeSlot,
                maxAssistants,
                neededMonitors
        );

		activity.registerMonitor(monitor);
		activity.registerMonitor(monitor2);


		assertEquals("Alberto", activity.getMonitorList().get(0).getFirstName());
		assertEquals("Francisco", activity.getMonitorList().get(1).getFirstName());

	}
	
	@Test
	public void testAddMonitor_throwAnException_WhenNumberOfNeededMonitorsIsExceded(){
		Monitor monitor = new Monitor(
				1,
				"Alberto",
				"Quesada",
				true
		);

		Monitor monitor2 = new Monitor(
				2,
				"Francisco",
				"Ruíz",
				false
		);

		
		Monitor monitor3 = new Monitor(
				3,
				"Jose",
				"Trujillo",
				true
		);
		
		String activityName = "Activity";
        EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
        TimeSlot timeSlot = TimeSlot.AFTERNOON;
        int maxAssistants = 10;
        int neededMonitors = 2;

        Activity activity = new Activity(
                activityName,
                educativeLevel,
                timeSlot,
                maxAssistants,
                neededMonitors
        );

		activity.registerMonitor(monitor);
		activity.registerMonitor(monitor2);


		assertThrows(MaxMonitorsAddedException.class, () -> activity.registerMonitor(monitor3));
 		
	}

	

}


