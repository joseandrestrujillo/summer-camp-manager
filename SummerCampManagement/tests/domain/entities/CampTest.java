package domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import business.dtos.ActivityDTO;
import business.dtos.CampDTO;
import business.dtos.MonitorDTO;
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
		

		CampDTO camp = new CampDTO(
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

		CampDTO camp = new CampDTO();

		assertEquals(campID, camp.getCampID());
		assertEquals(start, camp.getStart());
		assertEquals(end, camp.getEnd());
		assertEquals(educativeLevel, camp.getEducativeLevel());
		assertEquals(capacity, camp.getCapacity());
		assertEquals(null, camp.getActivities());
	}

	@Test
	void testSetters() {
		CampDTO camp = new CampDTO();
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;
		List<ActivityDTO> activities = new ArrayList<>();


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

		List<ActivityDTO> activities = new ArrayList<ActivityDTO>();
		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		
		ActivityDTO activity2 = new ActivityDTO(
				"Actividad 2",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		activities.add(activity);
		activities.add(activity2);

		CampDTO camp = new CampDTO(
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
	public void testToString_emptyLists() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;
		CampDTO camp = new CampDTO(
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
