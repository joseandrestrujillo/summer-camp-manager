package managers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import domain.entities.Activity;
import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.Monitor;
import domain.exceptions.ActivityNotFoundException;
import domain.exceptions.AssistantAlreadyRegisteredException;
import domain.exceptions.CampAlreadyRegisteredException;
import domain.exceptions.MonitorIsNotInActivityException;
import domain.exceptions.NotFoundException;
import domain.exceptions.NotTheSameLevelException;
import domain.exceptions.SpecialMonitorAlreadyRegisterException;
import domain.values.EducativeLevel;
import domain.values.TimeSlot;
import repositories.InMemoryActivityRepository;
import repositories.InMemoryAssistantRepository;
import repositories.InMemoryCampRepository;
import repositories.InMemoryMontiorRepository;
import utilities.Utils;

class CampsManagerTest {
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
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		campsManager.registerCamp(camp);
		
		assertEquals(true, campsManager.isRegistered(camp));
	}
	
	@Test
	void registerCamp_whenCampIsRegistered_throwsAssitantisAlreadyRegisteredException(){
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
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		camp = campManager.registerActivity(camp, activity);
		
		assertEquals(true, camp.activityIsRegistered(activity));
		assertEquals(activity, activityRepository.find(activity.getActivityName()));
		assertEquals(true, campRepository.find(camp.getCampID()).activityIsRegistered(activity));
	}
	
	@Test
	public void registerActivity_whenIsNotTheSameEducativeLevel_throwsNotTheSameLevelException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

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
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		assertThrows(NotTheSameLevelException.class,
				() -> campManager.registerActivity(camp, activity)
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
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		camp = campManager.registerActivity(camp, activity);
		
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
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		Camp modifiedCamp = campManager.registerActivity(camp, activity);
		
		
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
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		Camp modifiedCamp = campManager.registerActivity(camp, activity);
				
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
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);

		camp = campManager.registerActivity(camp, activity);
		
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
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		camp = campManager.registerActivity(camp, activity);
		
		camp = campManager.deleteActivity(camp, activity);
		
		assertEquals(false, camp.activityIsRegistered(activity));
		assertThrows(NotFoundException.class, () -> activityRepository.find(activity.getActivityName()));
		assertEquals(false, campRepository.find(camp.getCampID()).activityIsRegistered(activity));
	}
	
	@Test
	void deleteActivity_whenActivityIsNotInTheCampAndThePrincipalMonitorIsInAnotherActivity_throwsActivityNotFoundException() {
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
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
				
		assertThrows(ActivityNotFoundException.class, 
				() -> campManager.deleteActivity(camp, activity)
		);
	}
	
	@Test
	void deleteActivity_whenActivityIsInTheCampAndThePrincipalMonitorIsNotInAnotherActivity_throwsMonitorIsNotInActivityException() {
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
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		activity.registerMonitor(monitor);
		camp = campManager.registerActivity(camp, activity);
		Camp modifiedCamp = campManager.setPrincipalMonitor(camp, monitor);
		
		assertThrows(MonitorIsNotInActivityException.class, 
				() -> campManager.deleteActivity(modifiedCamp, activity)
		);
	}
}
