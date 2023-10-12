package managers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import domain.entities.Activity;
import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.CompleteInscription;
import domain.entities.Inscription;
import domain.exceptions.AssistantAlreadyEnrolledException;
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
		EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
		int capacity = 10;

		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
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
				true, 
				false);
				
		
		
		assertEquals(200, inscription.getPrice());
	}
}
