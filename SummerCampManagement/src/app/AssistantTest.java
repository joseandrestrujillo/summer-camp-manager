package app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

class AssistantTest {

	@Test
	void testConstructorAndGetters() {
		int id = 1;
		String firstName = "José";
		String lastName = "Trujillo";
		Date birthDate = Utils.parseDate("26/01/2001");
		boolean requireSpecialAttention = true;
		
		Assistant assistant = new Assistant(
				id,
				firstName,
				lastName,
				"26/01/2001",
				requireSpecialAttention
		);
		
		assertEquals(id, assistant.getId());
        assertEquals(firstName, assistant.getFirstName());
        assertEquals(lastName, assistant.getLastName());
        assertEquals(birthDate, assistant.getBirthDate());
        assertEquals(requireSpecialAttention, assistant.isRequireSpecialAttention());
	}
	
	@Test
	void testDefaultConstructor() {
		int id = 0;
		String firstName = "";
		String lastName = "";
		Date birthDate = null;
		boolean requireSpecialAttention = false;
		
		Assistant assistant = new Assistant();
		
		assertEquals(id, assistant.getId());
        assertEquals(firstName, assistant.getFirstName());
        assertEquals(lastName, assistant.getLastName());
        assertEquals(birthDate, assistant.getBirthDate());
        assertEquals(requireSpecialAttention, assistant.isRequireSpecialAttention());
	}

}
