package managers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.dtos.ActivityDTO;
import business.dtos.CampDTO;
import business.dtos.MonitorDTO;
import business.exceptions.activity.ActivityNotFoundException;
import business.exceptions.activity.MonitorIsNotInActivityException;
import business.exceptions.camp.CampAlreadyRegisteredException;
import business.exceptions.camp.NotTheSameLevelException;
import business.exceptions.camp.SpecialMonitorAlreadyRegisterException;
import business.exceptions.repository.NotFoundException;
import business.managers.CampsManager;
import business.values.EducativeLevel;
import business.values.TimeSlot;
import data.memory.InMemoryActivityRepository;
import data.memory.InMemoryCampRepository;
import data.memory.InMemoryMontiorRepository;
import data.memory.MapsManager;
import utilities.Utils;

class CampsManagerTest {
	@BeforeEach
	void setUp() {
		MapsManager.resetInstance();
	}
	
	@Test
	void registerCamp_whenCampIsNotRegistered_thenRegisterTheCamp(){
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
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
		
		campsManager.registerCamp(camp);
		
		assertEquals(true, campsManager.isRegisteredCamp(camp));
	}
	
	@Test
	void registerCamp_whenCampIsRegistered_throwsAssitantisAlreadyRegisteredException(){
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
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		campRepository.save(camp);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
	
		
	
		assertThrows(CampAlreadyRegisteredException.class, 
				() ->campsManager.registerCamp(camp)
		);	
	}
	
	@Test
	public void registerActivity_whenIsTheSameEducativeLevel_thenRegisterTheActivity() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		CampDTO camp = new CampDTO(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		campManager.registerCamp(camp);
		camp = campManager.registerActivityInACamp(camp, activity);
		
		assertEquals(true, campManager.getActivitiesOfACamp(camp).contains(activity));
		assertEquals(activity, activityRepository.find(activity.getActivityName()));
		assertEquals(true, campManager.getActivitiesOfACamp(campRepository.find(camp.getCampID())).contains(activity));
	}
	
	@Test
	public void registerActivity_whenIsNotTheSameEducativeLevel_throwsNotTheSameLevelException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				EducativeLevel.PRESCHOOL,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		CampDTO camp = new CampDTO(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		assertThrows(NotTheSameLevelException.class,
				() -> campManager.registerActivityInACamp(camp, activity)
		);
	}
	
	@Test
	public void setPrincipalMonitor_whenBelongToOneActivity_thenSetThePrincipalMonitor() {
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
		
		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				EducativeLevel.PRESCHOOL,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		MonitorDTO monitor = new MonitorDTO(
				1,
				"Alberto",
				"Quesada",
				false
		);
		
		
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		campManager.registerMonitorInActivity(activity, monitor);
		
		camp = campManager.registerActivityInACamp(camp, activity);
		
		camp = campManager.setPrincipalMonitor(camp, monitor);
		
		assertEquals(monitor, camp.getPrincipalMonitor());
		assertEquals(monitor, monitorRepository.find(monitor.getId()));
		assertEquals(monitor, campRepository.find(camp.getCampID()).getPrincipalMonitor());
	}
	
	@Test
	public void setPrincipalMonitor_whenNotBelongToOneActivity_throwsMonitorIsNotInActivityException() {
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
		
		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				EducativeLevel.PRESCHOOL,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		MonitorDTO monitor = new MonitorDTO(
				1,
				"Alberto",
				"Quesada",
				false
		);
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		CampDTO modifiedCamp = campManager.registerActivityInACamp(camp, activity);
		
		
		assertThrows(MonitorIsNotInActivityException.class,
				() -> campManager.setPrincipalMonitor(modifiedCamp, monitor)
		);
		
	}
	
	@Test
	public void setSpecialMonitor_whenBelongToOneActivity_throwsSpecialMonitorAlreadyRegisterException() {
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
		
		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				EducativeLevel.PRESCHOOL,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		MonitorDTO monitor = new MonitorDTO(
				1,
				"Alberto",
				"Quesada",
				false
		);
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		campManager.registerMonitorInActivity(activity, monitor);
		CampDTO modifiedCamp = campManager.registerActivityInACamp(camp, activity);
				
		assertThrows(SpecialMonitorAlreadyRegisterException.class,
				() -> campManager.setSpecialMonitor(modifiedCamp, monitor)
		);
	}
	
	@Test
	public void setSpecialMonitor_whenNotBelongToOneActivity_thenSetThePrincipalMonitor () {
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
		
		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				EducativeLevel.PRESCHOOL,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		MonitorDTO monitor = new MonitorDTO(
				1,
				"Alberto",
				"Quesada",
				true
		);
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);

		camp = campManager.registerActivityInACamp(camp, activity);
		
		camp = campManager.setSpecialMonitor(camp, monitor);
		
		assertEquals(monitor, camp.getSpecialMonitor());
		assertEquals(monitor, monitorRepository.find(monitor.getId()));
		assertEquals(monitor, campRepository.find(camp.getCampID()).getSpecialMonitor());
	}
	
	@Test
	void deleteActivity_whenActivityIsInTheCampAndThePrincipalMonitorIsInAnotherActivity_thenDeletesTheActivity() {
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
		
		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				EducativeLevel.PRESCHOOL,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		campManager.registerCamp(camp);
		
		camp = campManager.registerActivityInACamp(camp, activity);
		
		camp = campManager.deleteAnActivityOfACamp(camp, activity);
		
		assertEquals(false, campManager.getActivitiesOfACamp(camp).contains(activity));
		assertThrows(NotFoundException.class, () -> activityRepository.find(activity.getActivityName()));
		assertEquals(false, campManager.getActivitiesOfACamp(campRepository.find(camp.getCampID())).contains(activity));
	}
	
	@Test
	void deleteActivity_whenActivityIsNotInTheCampAndThePrincipalMonitorIsInAnotherActivity_throwsActivityNotFoundException() {
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
		
		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				EducativeLevel.PRESCHOOL,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
				
		assertThrows(ActivityNotFoundException.class, 
				() -> campManager.deleteAnActivityOfACamp(camp, activity)
		);
	}
	
	@Test
	void deleteActivity_whenActivityIsInTheCampAndThePrincipalMonitorIsNotInAnotherActivity_throwsMonitorIsNotInActivityException() {
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
		
		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				EducativeLevel.PRESCHOOL,
				TimeSlot.AFTERNOON,
				10,
				3
		);

		MonitorDTO monitor = new MonitorDTO(
				1,
				"Alberto",
				"Quesada",
				false
		);
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		campManager.registerMonitorInActivity(activity, monitor);
		
		camp = campManager.registerActivityInACamp(camp, activity);
		CampDTO modifiedCamp = campManager.setPrincipalMonitor(camp, monitor);
		campManager.registerMonitorInActivity(activity, monitor);
		assertThrows(MonitorIsNotInActivityException.class, 
				() -> campManager.deleteAnActivityOfACamp(modifiedCamp, activity)
		);
	}
}
