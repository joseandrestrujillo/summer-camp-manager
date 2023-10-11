package domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import domain.entities.Activity;
import domain.entities.Camp;
import domain.entities.Monitor;
import domain.exceptions.MonitorNotFoundException;
import domain.exceptions.NotTheSameLevelException;
import domain.exceptions.SpecialMonitorAlreadyRegisterException;
import domain.values.EducativeLevel;
import domain.values.TimeSlot;
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
        		+ "start: '15/01/2024', "
        		+ "end: '25/01/2024', "
        		+ "educativeLevel: PRESCHOOL, "
        		+ "capacity: 10, "
        		+ "activities: ["
        		+ "{activityName: 'Actividad', educativeLevel: PRESCHOOL, timeSlot: AFTERNOON, maxAssistants: 10, neededMonitors: 3}, "
        		+ "{activityName: 'Actividad 2', educativeLevel: PRESCHOOL, timeSlot: AFTERNOON, maxAssistants: 10, neededMonitors: 3}"
        		+ "]"
        		+ "}";
        assertEquals(expectedToString, camp.toString());
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
	        		+ "start: '15/01/2024', "
	        		+ "end: '25/01/2024', "
	        		+ "educativeLevel: PRESCHOOL, "
	        		+ "capacity: 10, "
	        		+ "activities: []}";
		assertEquals(expectedToString, camp.toString());
    }
	
	@Test
	public void registerActivity_whenIsTheSameEducativeLevel_thenRegisterTheActivity() {
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

		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		camp.registerActivity(activity);
		
		assertEquals(true, camp.activityIsRegistered(activity));
	}
	
	@Test
	public void registerActivity_whenIsNotTheSameEducativeLevel_throwsNotTheSameLevelException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		List<Activity> activities = new ArrayList<Activity>();
		Activity activity = new Activity(
				"Actividad",
				EducativeLevel.PRESCHOOL,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
				
		assertThrows(NotTheSameLevelException.class,
				() -> camp.registerActivity(activity)
		);
	}
	
	@Test
	public void setPrincipalMonitor_whenBelongToOneActivity_thenSetThePrincipalMonitor() {
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
		
		Activity activity = new Activity(
				"Actividad",
				EducativeLevel.PRESCHOOL,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		Monitor monitor = new Monitor(
				1,
				"Alberto",
				"Quesada",
				false
		);
		activity.registerMonitor(monitor);
		camp.registerActivity(activity);
		
		camp.setPrincipalMonitor(monitor);
		
		assertEquals(monitor, camp.getPrincipalMonitor());
	}
	
	@Test
	public void setPrincipalMonitor_whenNotBelongToOneActivity_throwsMonitorNotFoundException() {
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
		
		Activity activity = new Activity(
				"Actividad",
				EducativeLevel.PRESCHOOL,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		Monitor monitor = new Monitor(
				1,
				"Alberto",
				"Quesada",
				false
		);
		camp.registerActivity(activity);
		
		
		assertThrows(MonitorNotFoundException.class,
				() -> camp.setPrincipalMonitor(monitor)
		);
		
	}
	
	@Test
	public void setSpecialMonitor_whenBelongToOneActivity_throwsSpecialMonitorAlreadyRegisterException() {
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
		
		Activity activity = new Activity(
				"Actividad",
				EducativeLevel.PRESCHOOL,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		Monitor monitor = new Monitor(
				1,
				"Alberto",
				"Quesada",
				false
		);
		activity.registerMonitor(monitor);
		camp.registerActivity(activity);
				
		assertThrows(SpecialMonitorAlreadyRegisterException.class,
				() -> camp.setSpecialMonitor(monitor)
		);
	}
	
	@Test
	public void setSpecialMonitor_whenNotBelongToOneActivity_thenSetThePrincipalMonitor () {
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
		
		Activity activity = new Activity(
				"Actividad",
				EducativeLevel.PRESCHOOL,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		Monitor monitor = new Monitor(
				1,
				"Alberto",
				"Quesada",
				false
		);
		camp.registerActivity(activity);
		
		camp.setSpecialMonitor(monitor);
		
		assertEquals(monitor, camp.getSpecialMonitor());
		
	}

}
