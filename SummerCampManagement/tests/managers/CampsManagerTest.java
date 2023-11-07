package managers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.MonitorDTO;
import business.exceptions.activity.ActivityNotFoundException;
import business.exceptions.activity.MonitorIsNotInActivityException;
import business.exceptions.assistant.AssistantAlreadyRegisteredException;
import business.exceptions.camp.CampAlreadyRegisteredException;
import business.exceptions.camp.NotTheSameLevelException;
import business.exceptions.camp.SpecialMonitorAlreadyRegisterException;
import business.exceptions.repository.NotFoundException;
import business.managers.CampsManager;
import business.values.EducativeLevel;
import business.values.TimeSlot;
import data.memory.InMemoryActivityRepository;
import data.memory.InMemoryAssistantRepository;
import data.memory.InMemoryCampRepository;
import data.memory.InMemoryMontiorRepository;
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
		CampDTO camp = new CampDTO(
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
		
		CampDTO modifiedCamp = campManager.registerActivity(camp, activity);
		
		
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
		activity.registerMonitor(monitor);
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		CampDTO modifiedCamp = campManager.registerActivity(camp, activity);
				
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
		
		activity.registerMonitor(monitor);
		camp = campManager.registerActivity(camp, activity);
		CampDTO modifiedCamp = campManager.setPrincipalMonitor(camp, monitor);
		
		assertThrows(MonitorIsNotInActivityException.class, 
				() -> campManager.deleteActivity(modifiedCamp, activity)
		);
	}
}
