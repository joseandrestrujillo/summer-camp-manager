package managers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import domain.entities.Activity;
import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.Monitor;
import domain.exceptions.AssistantAlreadyRegisteredException;
import domain.exceptions.AssistantNotFoundException;
import domain.values.EducativeLevel;
import domain.values.TimeSlot;
import repositories.InMemoryAssistantRepository;
import repositories.InMemoryCampRepository;
import utilities.Utils;

class CampsManagerTest {

	@Test
	void registerCamp_whenCampIsNotRegistered_thenRegisterTheCamp(){
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		CampsManager campsManager = new CampsManager(campRepository);	
		//-------------ESTO LO HA HECHO MIGUEL----------------------
		List<Activity> activities = new ArrayList<Activity>();
		List<Monitor> monitors = new ArrayList<Monitor>();
		Activity activity = new Activity(
				"Actividad",
				EducativeLevel.ELEMENTARY,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		
		Activity activity2 = new Activity(
				"Actividad 2",
				EducativeLevel.PRESCHOOL,
				TimeSlot.AFTERNOON,
				10,
				3
		);
		Monitor monitor = new Monitor(
				1, 
				"Pepe", 
				"Sanchez Rodriguez", 
				false
		);
	    //--------------------------------------------
		Camp camp = new Camp(
				1,
				Utils.parseDate("15/01/2024"),
				Utils.parseDate("25/01/2024"),
				EducativeLevel.PRESCHOOL,
				10	
		);
		
		campsManager.registerCamp(camp);
		campsManager.registerMonitor(monitor);
		campsManager.registerActivity(activity);
		campsManager.registerActivity(activity2);
		
		assertEquals(true, campsManager.isRegistered(camp));
		assertEquals(true, campsManager.isRegistered(monitor));
		assertEquals(true, campsManager.isRegistered(activity));
		assertEquals(true, campsManager.isRegistered(activity2));



	}
	
//	@Test
//	void registerAssistant_whenAssistantIsRegistered_throwsAssitantisAlreadyRegisteredException(){
//		Assistant assistant = new Assistant(
//				1,
//				"José",
//				"Trujillo",
//				Utils.parseDate("26/01/2001"),
//				true
//				);
//		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
//		assistantRepository.save(assistant);
//		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);		
//	
//		
//	
//		assertThrows(AssistantAlreadyRegisteredException.class, 
//				() ->assistantsManager.registerAssistant(assistant)
//		);	
//	}
//	
//	@Test
//	void updateAssistant_whenAssistantIsRegistered_thenUpdateTheAssistant(){
//		Assistant assistant = new Assistant(
//				1,
//				"José",
//				"Trujillo",
//				Utils.parseDate("26/01/2001"),
//				true
//				);
//		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
//		assistantRepository.save(assistant);
//		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);
//		
//		
//		assistantsManager.updateAssistant(new Assistant(
//				1,
//				"José",
//				"Trujillo",
//				Utils.parseDate("26/01/2001"),
//				false
//			));
//		
//		assertEquals("José", assistantRepository.find(1).getFirstName());
//		assertEquals("Trujillo", assistantRepository.find(1).getLastName());
//		assertEquals(Utils.parseDate("26/01/2001").getTime(), assistantRepository.find(1).getBirthDate().getTime());
//		assertEquals(false, assistantRepository.find(1).isRequireSpecialAttention());
//	}
//	
//	@Test
//	void updateAssistant_whenAssistantIsNotRegistered_throwsAssitantisNotFoundException(){
//		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
//		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);		
//		
//		Assistant assistant = new Assistant(
//				1,
//				"José",
//				"Trujillo",
//				Utils.parseDate("26/01/2001"),
//				false
//			);
//		
//		assertThrows(AssistantNotFoundException.class, 
//				() -> assistantsManager.updateAssistant(assistant)
//		);
//	}
//	
//	@Test
//	void getListOfRegisteredAssistant_returnListRegisteredAssitant(){
//		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
//		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);		
//		Assistant assistant = new Assistant(
//				1,
//				"José",
//				"Trujillo",
//				Utils.parseDate("26/01/2001"),
//				true
//				);
//		
//		assistantsManager.registerAssistant(assistant);
//		List<Assistant> list = new ArrayList<Assistant>();
//		list.add(assistant);
//		assertEquals(list, assistantsManager.getListOfRegisteredAssistant());
//	}
}
