package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import business.entities.Activity;
import business.entities.Assistant;
import business.entities.Camp;
import business.entities.Inscription;
import business.entities.Monitor;
import business.values.EducativeLevel;
import business.values.TimeSlot;
import utilities.StringUtils;
import utilities.Utils;

public class StringUtilsTests {
	@Test
    public void testActivityFromString() {
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
        
        Activity activity = StringUtils.activityFromString(inputString);
        assertEquals(activityName, activity.getActivityName());
        assertEquals(educativeLevel, activity.getEducativeLevel());
        assertEquals(maxAssistants, activity.getMaxAssistants());
        assertEquals(neededMonitors, activity.getNeededMonitors());
        assertEquals(assistants.toString(), activity.getAssistants().toString());
        assertEquals(monitors.toString(), activity.getMonitorList().toString());
    }
	
	@Test
    public void testAssistantFromString() {
		int id = 1;
		String firstName = "José";
		String lastName = "Trujillo";
		Date birthDate = Utils.parseDate("26/01/2001");
		boolean requireSpecialAttention = true;
		

        String inputString = "{id: 1, firstName: 'José', lastName: 'Trujillo', birthDate: 26/01/2001, requireSpecialAttention: true}";
        Assistant assistant = StringUtils.assistantFromString(inputString);
        
        assertEquals(id, assistant.getId());
        assertEquals(firstName, assistant.getFirstName());
        assertEquals(lastName, assistant.getLastName());
        assertEquals(birthDate, assistant.getBirthDate());
        assertEquals(requireSpecialAttention, assistant.isRequireSpecialAttention());
    }
	

	@Test
    public void testCampFromString_notEmptyLists() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		List<Activity> activities = new ArrayList<Activity>();
		Activity activity = new Activity(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		
		Activity activity2 = new Activity(
				"Actividad 2",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		activities.add(activity);
		activities.add(activity2);

		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);

		camp.setActivities(activities);

        String inputString = ""
        		+ "{campID: 1, "
        		+ "start: 15/01/2024, "
        		+ "end: 25/01/2024, "
        		+ "educativeLevel: PRESCHOOL, "
        		+ "capacity: 10, "
				+ "principalMonitor: null, "
				+ "specialMonitor: null, "
        		+ "activities: ["
        		+ "{activityName: 'Actividad', educativeLevel: PRESCHOOL, timeSlot: AFTERNOON, maxAssistants: 10, neededMonitors: 3, assistants: [], monitors: []}, "
        		+ "{activityName: 'Actividad 2', educativeLevel: PRESCHOOL, timeSlot: AFTERNOON, maxAssistants: 10, neededMonitors: 3, assistants: [], monitors: []}"
        		+ "]"
        		+ "}";
        Camp campcreated = StringUtils.campFromString(inputString);
        
        assertEquals(campID, campcreated.getCampID());
        assertEquals(start, campcreated.getStart());
        assertEquals(end, campcreated.getEnd());
        assertEquals(educativeLevel, campcreated.getEducativeLevel());
        assertEquals(capacity, campcreated.getCapacity());
        assertEquals(activities.toString(), campcreated.getActivities().toString());
    }
	
	@Test
	public void campFromString_nonEmptyMonitors() {
		
		Date start = Utils.parseDate("10/11/2024");
		Date end = Utils.parseDate("17/11/2024");
		EducativeLevel educativeLevel = EducativeLevel.TEENAGER;
		int capacity = 5;
		
		String inputString = "{campID: 1, start: 10/11/2024, end: 17/11/2024, educativeLevel: TEENAGER, capacity: 5, principalMonitor: null, specialMonitor: null, activities: [{activityName: 'Act1', educativeLevel: TEENAGER, timeSlot: MORNING, maxAssistants: 5, neededMonitors: 1, assistants: [], monitors: [{id: 5, firstName: 'Pepe', lastName: 'Meñique', isSpecialEducator: false}]}]}";
		String activities = "[{activityName: 'Act1', educativeLevel: TEENAGER, timeSlot: MORNING, maxAssistants: 5, neededMonitors: 1, assistants: [], monitors: [{id: 5, firstName: 'Pepe', lastName: 'Meñique', isSpecialEducator: false}]}]";
		Camp campcreated = StringUtils.campFromString(inputString);
		
		assertEquals(1, campcreated.getCampID());
        assertEquals(start, campcreated.getStart());
        assertEquals(end, campcreated.getEnd());
        assertEquals(educativeLevel, campcreated.getEducativeLevel());
        assertEquals(capacity, campcreated.getCapacity());
        assertEquals(activities, campcreated.getActivities().toString());
	}
	

	@Test
    public void testInscriptionFromString() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		

        String inputString = "{AssistantId: 1, CampId: 1, InscriptionDate: '12/01/2024', Price: 100.0, canBeCancelled: true}";

        Inscription inscription = StringUtils.inscriptionFromString(inputString);
        
        assertEquals(assistantId, inscription.getAssistantId());
        assertEquals(campId, inscription.getCampId());
        assertEquals(inscriptionDate, inscription.getInscriptionDate());
        assertEquals(price, inscription.getPrice());
        assertEquals(true, inscription.canBeCanceled());
    }
	

	@Test
    public void testMonitorFromString() {
		int id = 1;
		String firstName = "José";
		String lastName = "Trujillo";
		boolean isSpecialEducator = true;
		

        String inputString = "{id: 1, firstName: 'José', lastName: 'Trujillo', isSpecialEducator: true}";
        Monitor monitor = StringUtils.monitorFromString(inputString);
        
        assertEquals(id, monitor.getId());
        assertEquals(firstName, monitor.getFirstName());
        assertEquals(lastName, monitor.getLastName());
        assertEquals(isSpecialEducator, monitor.isSpecialEducator());
    }
}
