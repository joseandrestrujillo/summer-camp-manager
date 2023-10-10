package managers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.entities.Assistant;
import domain.exceptions.AssistantAlreadyRegisteredException;
import repositories.InMemoryAssistantRepository;
import utilities.Utils;

class AssistantsManagerTest {

	@Test
	void registerAssistant_whenAssistantIsNotRegistered_thenRegisterTheAssistant(){
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);		
		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				);
		
		assistantsManager.registerAssistant(assistant);
		
		assertEquals(true, assistantsManager.isRegistered(assistant));
	}
	


	@Test
	void registerAssistant_whenAssistantIsRegistered_throwsAssitantisAlreadyRegisteredException(){
		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				);
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		assistantRepository.save(assistant);
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);		
	
		
	
		assertThrows(AssistantAlreadyRegisteredException.class, 
				() ->assistantsManager.registerAssistant(assistant));		
		}
}
