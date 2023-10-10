package managers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.entities.Assistant;
import repositories.InMemoryAssistantRepository;
import utilities.Utils;

class AssistantsManagerTest {

	@Test
	void registerAssistant_whenAssistantIsNotRegistered_thenRegisterTheAssistant(){
		InMemoryAssistantRepository assistantRepository = new InMemoryAssistantRepository();
		Assistant assistant = new Assistant(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				);
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);
		
		assistantsManager.registerAssistant(assistant);
		
		assertEquals(true, assistantsManager.isRegistered(assistant));
	}
	
	
}
