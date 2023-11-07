package domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.MonitorDTO;
import business.exceptions.activity.MaxMonitorsAddedException;
import business.values.EducativeLevel;
import business.values.TimeSlot;
import utilities.Utils;

class ActivityTest {
	
	@Test
	void testConstructorAndGetters() {
		String activityName = "Activity";
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		TimeSlot timeSlot = TimeSlot.AFTERNOON;
		int maxAssistants = 15;
		int neededMonitors = 2;
		
		ActivityDTO activity = new ActivityDTO(
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
		
		ActivityDTO activity = new ActivityDTO();
		
		assertEquals(activityName, activity.getActivityName());
        assertEquals(educativeLevel, activity.getEducativeLevel());
        assertEquals(timeSlot, activity.getTimeSlot());
        assertEquals(maxAssistants, activity.getMaxAssistants());
        assertEquals(neededMonitors, activity.getNeededMonitors());
        assertEquals(null, activity.getMonitorList());
	}
	
	@Test
	void testSetters() {
		ActivityDTO activity = new ActivityDTO();
		String activityName = "Activity";
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		TimeSlot timeSlot = TimeSlot.AFTERNOON;
		int maxAssistants = 15;
		int neededMonitors = 2;
		List<MonitorDTO> monitors = new ArrayList<MonitorDTO>(neededMonitors);
		
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

        ActivityDTO activity = new ActivityDTO(
                activityName,
                educativeLevel,
                timeSlot,
                maxAssistants,
                neededMonitors
        );

        List<MonitorDTO> monitors = new ArrayList<MonitorDTO>();
        monitors.add(new MonitorDTO(1, "juan", "perez", true));
        monitors.add(new MonitorDTO(2, "juana", "perez", false));
        
        activity.setMonitorList(monitors);
        
        List<AssistantDTO> assistants = new ArrayList<AssistantDTO>();
        assistants.add(new AssistantDTO(1, "juan", "perez", Utils.parseDate("26/01/2001"), true));
        assistants.add(new AssistantDTO(2, "juana", "perez", Utils.parseDate("26/01/2001"), false));
        
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
	public void testAddMonitor(){
		MonitorDTO monitor = new MonitorDTO(
				1,
				"Alberto",
				"Quesada",
				true
		);

		MonitorDTO monitor2 = new MonitorDTO(
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

        ActivityDTO activity = new ActivityDTO(
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
		MonitorDTO monitor = new MonitorDTO(
				1,
				"Alberto",
				"Quesada",
				true
		);

		MonitorDTO monitor2 = new MonitorDTO(
				2,
				"Francisco",
				"Ruíz",
				false
		);

		
		MonitorDTO monitor3 = new MonitorDTO(
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

        ActivityDTO activity = new ActivityDTO(
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


