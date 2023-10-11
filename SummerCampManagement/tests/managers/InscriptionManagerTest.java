package managers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import domain.entities.Activity;
import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.CompleteInscription;
import domain.entities.Inscription;
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
				
		assertEquals(new CompleteInscription(1, campID, inscriptionDate, 300, true),
				inscription
				);
		
	}
	
	
	
	
	
	
	
	
}
