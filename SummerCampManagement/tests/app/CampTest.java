package app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import utilities.EducativeLevel;
import utilities.TimeSlot;
import utilities.Utils;

class CampTest {
	
	@Test
	void testConstructorAndGetters() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;
		List<Activity> activities = new ArrayList<>();
		List<Monitor> monitors = new ArrayList<>();		
		
		monitors.add(new Monitor (1, "Pepe", "Sanchez Rodriguez", false));

		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		camp.setActivities(activities);
		camp.setMonitors(monitors);
		assertEquals(campID, camp.getCampID());
		assertEquals(start, camp.getStart());
		assertEquals(end, camp.getEnd());
		assertEquals(educativeLevel, camp.getEducativeLevel());
		assertEquals(capacity, camp.getCapacity());
		assertEquals(activities, camp.getActivities());
		assertEquals(monitors, camp.getMonitors());
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
		// assertEquals(null, camp.getActivities());
		// assertEquals(null, camp.getMonitors());
		// assertEquals(null, camp.getAssistants());
	}

	@Test
	void testSetters() {
		Camp camp = new Camp();
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;
		// List<Activity> activities = new ArrayList<>();
		// List<Monitor> monitors = new ArrayList<>();
		// List<Assistant> assistants = new ArrayList<>(capacity);

		camp.setCampID(campID);
		camp.setStart(start);
		camp.setEnd(end);
		camp.setEducativeLevel(educativeLevel);
		camp.setCapacity(capacity);

		assertEquals(campID, camp.getCampID());
		assertEquals(start, camp.getStart());
		assertEquals(end, camp.getEnd());
		assertEquals(educativeLevel, camp.getEducativeLevel());
		assertEquals(capacity, camp.getCapacity());

	}
	
	@Test
    public void testToString_notEmptyLists() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		List<Activity> activities = new ArrayList<Activity>();
		List<Monitor> monitors = new ArrayList<Monitor>();
		Activity activity = new Activity(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		monitors.add(new Monitor (1, "Pepe", "Sanchez Rodriguez", false));
		activities.add(activity);

		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);

		camp.setActivities(activities);
		camp.setMonitors(monitors);		

        String expectedToString = "{campID: 1, start: '"+Utils.parseDate("15/01/2024")+"', end: '"+Utils.parseDate("25/01/2024")+"', educativeLevel: PRESCHOOL, capacity: 10, activities: " + activities.toString()+", monitors: "+monitors.toString()+"}";
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
		//TODO: List need be checked
        String expectedToString = "{campID: 1, start: '"+Utils.parseDate("15/01/2024")+"', end: '"+Utils.parseDate("25/01/2024")+"', educativeLevel: PRESCHOOL, capacity: 10, activities: [], monitors: []}";
        assertEquals(expectedToString, camp.toString());
    }

}
