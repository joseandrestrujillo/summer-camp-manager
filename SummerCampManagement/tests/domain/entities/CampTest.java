package domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import business.entities.Activity;
import business.entities.Camp;
import business.entities.Monitor;
import business.exceptions.camp.NotTheSameLevelException;
import business.exceptions.camp.SpecialMonitorAlreadyRegisterException;
import business.exceptions.monitor.MonitorNotFoundException;
import business.values.EducativeLevel;
import business.values.TimeSlot;
import utilities.Utils;

class CampTest {
	
	@Test
	void testConstructorAndGetters() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;	
		

		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		assertEquals(campID, camp.getCampID());
		assertEquals(start, camp.getStart());
		assertEquals(end, camp.getEnd());
		assertEquals(educativeLevel, camp.getEducativeLevel());
		assertEquals(capacity, camp.getCapacity());
		assert(camp.getActivities().isEmpty());
	}
	
	@Test
	void testDefaultConstructor() {
		int campID = -1;
        Date start = null;
        Date end = null;
        EducativeLevel educativeLevel = null;
        int capacity = -1;

		Camp camp = new Camp();

		assertEquals(campID, camp.getCampID());
		assertEquals(start, camp.getStart());
		assertEquals(end, camp.getEnd());
		assertEquals(educativeLevel, camp.getEducativeLevel());
		assertEquals(capacity, camp.getCapacity());
		assertEquals(null, camp.getActivities());
	}

	@Test
	void testSetters() {
		Camp camp = new Camp();
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;
		List<Activity> activities = new ArrayList<>();


		camp.setCampID(campID);
		camp.setStart(start);
		camp.setEnd(end);
		camp.setEducativeLevel(educativeLevel);
		camp.setCapacity(capacity);
		camp.setActivities(activities);
		
		assertEquals(campID, camp.getCampID());
		assertEquals(start, camp.getStart());
		assertEquals(end, camp.getEnd());
		assertEquals(educativeLevel, camp.getEducativeLevel());
		assertEquals(capacity, camp.getCapacity());
		assertEquals(activities, camp.getActivities());

	}
	
	@Test
    public void testToString_notEmptyLists() {
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

        String expectedToString = ""
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
        assertEquals(expectedToString, camp.toString());
    }
	
	@Test
    public void testFromString_notEmptyLists() {
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
        Camp campcreated = Camp.fromString(inputString);
        
        assertEquals(campID, campcreated.getCampID());
        assertEquals(start, campcreated.getStart());
        assertEquals(end, campcreated.getEnd());
        assertEquals(educativeLevel, campcreated.getEducativeLevel());
        assertEquals(capacity, campcreated.getCapacity());
        assertEquals(activities.toString(), campcreated.getActivities().toString());
    }
	
	@Test
	public void fromString_nonEmptyMonitors() {
		
		Date start = Utils.parseDate("10/11/2024");
		Date end = Utils.parseDate("17/11/2024");
		EducativeLevel educativeLevel = EducativeLevel.TEENAGER;
		int capacity = 5;
		
		String inputString = "{campID: 1, start: 10/11/2024, end: 17/11/2024, educativeLevel: TEENAGER, capacity: 5, principalMonitor: null, specialMonitor: null, activities: [{activityName: 'Act1', educativeLevel: TEENAGER, timeSlot: MORNING, maxAssistants: 5, neededMonitors: 1, assistants: [], monitors: [{id: 5, firstName: 'Pepe', lastName: 'Meñique', isSpecialEducator: false}]}]}";
		String activities = "[{activityName: 'Act1', educativeLevel: TEENAGER, timeSlot: MORNING, maxAssistants: 5, neededMonitors: 1, assistants: [], monitors: [{id: 5, firstName: 'Pepe', lastName: 'Meñique', isSpecialEducator: false}]}]";
		Camp campcreated = Camp.fromString(inputString);
		
		assertEquals(1, campcreated.getCampID());
        assertEquals(start, campcreated.getStart());
        assertEquals(end, campcreated.getEnd());
        assertEquals(educativeLevel, campcreated.getEducativeLevel());
        assertEquals(capacity, campcreated.getCapacity());
        assertEquals(activities, campcreated.getActivities().toString());
	}
	
	@Test
	public void testToString_emptyLists() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		String expectedToString = ""
	        		+ "{campID: 1, "
	        		+ "start: 15/01/2024, "
	        		+ "end: 25/01/2024, "
	        		+ "educativeLevel: PRESCHOOL, "
	        		+ "capacity: 10, "
	        		+ "principalMonitor: null, "
					+ "specialMonitor: null, "
	        		+ "activities: []}";
		assertEquals(expectedToString, camp.toString());
    }

}
