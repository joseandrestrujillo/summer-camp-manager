package managers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import domain.entities.Assistant;
import domain.entities.Camp;
import domain.exceptions.AssistantAlreadyRegisteredException;
import domain.exceptions.AssistantNotFoundException;
import domain.values.EducativeLevel;
import repositories.InMemoryAssistantRepository;
import repositories.InMemoryCampRepository;
import utilities.Utils;

class CampsManagerTest {

	@Test
	void registerCamp_whenCampIsNotRegistered_thenRegisterTheCamp(){
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		CampsManager campsManager = new CampsManager(campRepository);	
		
		Camp camp = new Camp(
				1,
				Utils.parseDate("15/01/2024"),
				Utils.parseDate("25/01/2024"),
				EducativeLevel.PRESCHOOL,
				10	
		);
		
		campsManager.registerCamp(camp);
		
		assertEquals(true, campsManager.isRegistered(camp));
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