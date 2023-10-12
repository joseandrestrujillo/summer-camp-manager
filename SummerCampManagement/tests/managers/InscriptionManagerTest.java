package managers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import domain.entities.Activity;
import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.CompleteInscription;
import domain.entities.Inscription;
import domain.exceptions.AssistantAlreadyEnrolledException;
import domain.exceptions.MaxAssistantExcededException;
import domain.exceptions.NeedToAddAnSpecialMonitorException;
import domain.exceptions.WrongEducativeLevelException;
import domain.factories.EarlyRegisterInscriptionFactory;
import domain.values.EducativeLevel;
import domain.values.TimeSlot;
import repositories.InMemoryActivityRepository;
import repositories.InMemoryAssistantRepository;
import repositories.InMemoryCampRepository;
import repositories.InMemoryInscriptionRepository;
import repositories.InMemoryMontiorRepository;
import utilities.Utils;

class InscriptionManagerTest {
	
	@Test
	void enroll_whenTheDateIsMoreThan15DaysBefore_thenReturnAnEarlyRegisterInscription() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date inscriptionDate = Utils.parseDate("25/12/2023");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		assertInstanceOf(CompleteInscription.class, inscription);
		assertEquals(1, inscription.getAssistantId());
		assertEquals(campID, inscription.getCampId());
		assertEquals(inscriptionDate, inscription.getInscriptionDate());
		assertEquals(true, inscription.canBeCanceled());
	}
	
	@Test
	void enroll_whenTheDateIsNotMoreThan15DaysBeforeAndMoreThan2DaysBefore_thenReturnAnLateRegisterInscription() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		assertInstanceOf(CompleteInscription.class, inscription);
		assertEquals(1, inscription.getAssistantId());
		assertEquals(campID, inscription.getCampId());
		assertEquals(inscriptionDate, inscription.getInscriptionDate());
		assertEquals(false, inscription.canBeCanceled());
	}
	
	@Test
	void enroll_whenTheAssistantIsAlreadyEnrolledInTheCamp_throwsAssistantAlreadyEnrolledException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		
		inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		assertThrows(AssistantAlreadyEnrolledException.class, 
				 () -> inscriptionManager.enroll(1, campID, inscriptionDate, false, false)
		);
	}
	
	
	@Test
	void enroll_whenISCompleteRegisteredAndNoActivities_thenPrices300_Early() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date inscriptionDate = Utils.parseDate("25/12/2023");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		assertEquals(300, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISPartialRegisteredAndNoActivities_thenPrices100_Early() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date inscriptionDate = Utils.parseDate("25/12/2023");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
				
		
		assertEquals(100, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISCompleteRegisteredAndOneActivity_thenPrices320_Early() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		Activity activity = new Activity(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		
		Date inscriptionDate = Utils.parseDate("25/12/2023");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		campsManager.registerActivity(camp, activity);
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		
		assertEquals(320, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISPartialRegisteredAndOneActivity_thenPrices120_Early() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		Activity activity = new Activity(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		
		Date inscriptionDate = Utils.parseDate("25/12/2023");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		campsManager.registerActivity(camp, activity);
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
				
		
		
		assertEquals(120, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISCompleteRegisteredAndOneActivity_thenPrices400_Early() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		Activity activity = new Activity(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Activity activity2 = new Activity(
				"Actividad2",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);Activity activity3 = new Activity(
				"Actividad3",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);Activity activity4 = new Activity(
				"Actividad4",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);Activity activity5 = new Activity(
				"Actividad5",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Date inscriptionDate = Utils.parseDate("25/12/2023");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		campsManager.registerActivity(camp, activity);
		campsManager.registerActivity(camp, activity2);
		campsManager.registerActivity(camp, activity3);
		campsManager.registerActivity(camp, activity4);
		campsManager.registerActivity(camp, activity5);
		
		
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		
		assertEquals(400, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISPartialRegisteredAndOneActivity_thenPrices200_Early() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		Activity activity = new Activity(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Activity activity2 = new Activity(
				"Actividad2",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);Activity activity3 = new Activity(
				"Actividad3",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);Activity activity4 = new Activity(
				"Actividad4",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);Activity activity5 = new Activity(
				"Actividad5",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Date inscriptionDate = Utils.parseDate("25/12/2023");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		campsManager.registerActivity(camp, activity);
		campsManager.registerActivity(camp, activity2);
		campsManager.registerActivity(camp, activity3);
		campsManager.registerActivity(camp, activity4);
		campsManager.registerActivity(camp, activity5);
		
		
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
				
		
		
		assertEquals(200, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISCompleteRegisteredAndNoActivities_thenPrices300_Late() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		assertEquals(300, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISPartialRegisteredAndNoActivities_thenPrices100_Late() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
				
		
		assertEquals(100, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISCompleteRegisteredAndOneActivity_thenPrices320_Late() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		Activity activity = new Activity(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		campsManager.registerActivity(camp, activity);
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		
		assertEquals(320, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISPartialRegisteredAndOneActivity_thenPrices120_Late() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		Activity activity = new Activity(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		campsManager.registerActivity(camp, activity);
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
				
		
		
		assertEquals(120, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISCompleteRegisteredAndOneActivity_thenPrices400_Late() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		Activity activity = new Activity(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Activity activity2 = new Activity(
				"Actividad2",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);Activity activity3 = new Activity(
				"Actividad3",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);Activity activity4 = new Activity(
				"Actividad4",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);Activity activity5 = new Activity(
				"Actividad5",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		campsManager.registerActivity(camp, activity);
		campsManager.registerActivity(camp, activity2);
		campsManager.registerActivity(camp, activity3);
		campsManager.registerActivity(camp, activity4);
		campsManager.registerActivity(camp, activity5);
		
		
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		
		assertEquals(400, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISPartialRegisteredAndOneActivity_thenPrices200_Late() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		Activity activity = new Activity(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Activity activity2 = new Activity(
				"Actividad2",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Activity activity3 = new Activity(
				"Actividad3",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Activity activity4 = new Activity(
				"Actividad4",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Activity activity5 = new Activity(
				"Actividad5",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		campsManager.registerActivity(camp, activity);
		campsManager.registerActivity(camp, activity2);
		campsManager.registerActivity(camp, activity3);
		campsManager.registerActivity(camp, activity4);
		campsManager.registerActivity(camp, activity5);
		
		
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
				
		
		
		assertEquals(200, inscription.getPrice());
	}
	
	@Test
	void enroll_whenTheAssistantNeedSpecialAttentioandthedontCampSpceialMonitor_throwsNeedToAddAnSpecialMonitorException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		
		
				
		
		assertThrows(NeedToAddAnSpecialMonitorException.class, 
				 () -> inscriptionManager.enroll(1, campID, inscriptionDate, false, true)
		);
	}
	
	@Test
	void avaliableCamps_whenAllCampamentsHaveNotStartAndHaveCapacity_thenReturnAllCampaments() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Camp camp1 = new Camp(
				2,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Camp camp2 = new Camp(
				3,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date actualDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		
		assistantsManager.registerAssistant(assistant);
		campManager.registerCamp(camp);
		campManager.registerCamp(camp1);
		campManager.registerCamp(camp2);
		
		List<Camp> expectedResult = new ArrayList<Camp>();
		expectedResult.add(camp);
		expectedResult.add(camp1);
		expectedResult.add(camp2);
		
		assertEquals(expectedResult, inscriptionManager.avaliableCamps(actualDate));
	}
	
	@Test
	void avaliableCamps_whenThereAreSomeCampThatAlreadyStart_thenReturnOnlyCampamentsThatHaveNotStartAndHaveCapacity() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Camp camp1 = new Camp(
				2,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Camp camp2 = new Camp(
				3,
				Utils.parseDate("09/01/2024"),
				end,
				educativeLevel,
				capacity				
		);
		
		Date actualDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		
		assistantsManager.registerAssistant(assistant);
		campManager.registerCamp(camp);
		campManager.registerCamp(camp1);
		campManager.registerCamp(camp2);
		
		List<Camp> expectedResult = new ArrayList<Camp>();
		expectedResult.add(camp);
		expectedResult.add(camp1);
		
		assertEquals(expectedResult, inscriptionManager.avaliableCamps(actualDate));
	}
	
	@Test
	void avaliableCamps_whenThereAreSomeCampThatIsFull_thenReturnOnlyCampamentsThatHaveNotStartAndHaveCapacity() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Camp camp1 = new Camp(
				2,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Camp camp2 = new Camp(
				3,
				start,
				end,
				educativeLevel,
				1				
		);
		
		Date inscriptionDate = Utils.parseDate("8/01/2024");

		Date actualDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		
		assistantsManager.registerAssistant(assistant);
		campManager.registerCamp(camp);
		campManager.registerCamp(camp1);
		campManager.registerCamp(camp2);
		inscriptionManager.enroll(assistant.getId(), 3, inscriptionDate, false, false);
		
		List<Camp> expectedResult = new ArrayList<Camp>();
		expectedResult.add(camp);
		expectedResult.add(camp1);
		
		assertEquals(expectedResult, inscriptionManager.avaliableCamps(actualDate));
	}
	
	@Test
	void enrollComplete_whenThereAreEnoughtSpace_thenAssistantIsAddedToAllActivitiesOfTheCamp() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		Activity activity = new Activity(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Activity activity2 = new Activity(
				"Actividad2",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		campsManager.registerActivity(camp, activity);
		campsManager.registerActivity(camp, activity2);
		
		
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		Activity activityPersisted = activityRepository.find(activity.getActivityName());
		Activity activity2Persisted = activityRepository.find(activity2.getActivityName());
				
		assertEquals(true, activityPersisted.getAssistants().contains(assistant));
		assertEquals(true, activity2Persisted.getAssistants().contains(assistant));
	}
	
	@Test
	void enrollComplete_whenThereAreNotEnoughtSpace_throwsMaxAssistantExcededException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		
		Assistant assistant2 = new Assistant(
				2,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		Activity activity = new Activity(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Activity activity2 = new Activity(
				"Actividad2",
				educativeLevel,
				TimeSlot.AFTERNOON,
				1,
				1
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		assistantRepository.save(assistant2);
		
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		campsManager.registerActivity(camp, activity);
		campsManager.registerActivity(camp, activity2);
		
		
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		assertThrows(MaxAssistantExcededException.class, 
				() -> inscriptionManager.enroll(2, campID, inscriptionDate, false, false)
		);
	}
	
	@Test
	void enrollPartial_whenThereAreEnoughtSpace_thenAssistantIsAddedToMorningActivitiesOfTheCamp() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		
		Assistant assistant2 = new Assistant(
				2,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Activity activity = new Activity(
				"Actividad",
				educativeLevel,
				TimeSlot.MORNING,
				10,
				3
		);
		
		Activity activity2 = new Activity(
				"Actividad2",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				1
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		assistantRepository.save(assistant2);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		campsManager.registerActivity(camp, activity);
		campsManager.registerActivity(camp, activity2);
		
		
		inscriptionManager.enroll(
				2, 
				campID, 
				inscriptionDate, 
				true, 
				false);
		
		inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
		
				
		
		Activity activityPersisted = activityRepository.find(activity.getActivityName());
		Activity activity2Persisted = activityRepository.find(activity2.getActivityName());
				
		assertEquals(true, activityPersisted.getAssistants().contains(assistant));
		assertEquals(false, activity2Persisted.getAssistants().contains(assistant));
	}
	
	@Test
	void enrollPartial_whenThereAreNotEnoughtSpaceInMorningActivities_throwsMaxAssistantExcededException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		
		Assistant assistant2 = new Assistant(
				2,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		Activity activity = new Activity(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Activity activity2 = new Activity(
				"Actividad2",
				educativeLevel,
				TimeSlot.MORNING,
				1,
				1
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		assistantRepository.save(assistant2);
		
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		campsManager.registerActivity(camp, activity);
		campsManager.registerActivity(camp, activity2);
		
		
		
		Inscription inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
				
		
		assertThrows(MaxAssistantExcededException.class, 
				() -> inscriptionManager.enroll(2, campID, inscriptionDate, true, false)
		);
	}
	
	@Test
	void enroll_whenTheAssistantIsElementaryAndTheActivityIsNot_throwsWrongEducativeLevelException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.TEENAGER;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		
				
		
		assertThrows(WrongEducativeLevelException.class, 
				 () -> inscriptionManager.enroll(1, campID, inscriptionDate, false, false)
		);
	}
	
	@Test
	void enroll_whenTheAssistantIsPreschoolAndTheActivityIsNot_throwsWrongEducativeLevelException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2015"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		
				
		
		assertThrows(WrongEducativeLevelException.class, 
				 () -> inscriptionManager.enroll(1, campID, inscriptionDate, false, false)
		);
	}
	
	@Test
	void enroll_whenTheAssistantIsTeenagerAndTheActivityIsNot_throwsWrongEducativeLevelException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2010"),
				true
				);
		Camp camp = new Camp(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryMontiorRepository monitorRepository = new InMemoryMontiorRepository();
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		
		
		campRepository.save(camp);
		assistantRepository.save(assistant);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository, assistantRepository, inscriptionRepository);
		
				
		
		assertThrows(WrongEducativeLevelException.class, 
				 () -> inscriptionManager.enroll(1, campID, inscriptionDate, false, false)
		);
	}
}
