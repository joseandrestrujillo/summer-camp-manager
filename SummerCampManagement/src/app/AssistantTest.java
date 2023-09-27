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
				birthDate,
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

	@Test
	void testSetters() {
		Assistant assistant = new Assistant();
		int id = 1;
		String firstName = "José";
		String lastName = "Trujillo";
		Date birthDate = Utils.parseDate("26/01/2001");
		boolean requireSpecialAttention = true;
		
		assistant.setId(id);
		assistant.setFirstName(firstName);
		assistant.setLastName(lastName);
		assistant.setBirthDate(birthDate);
		assistant.setRequireSpecialAttention(requireSpecialAttention);
		
		assertEquals(id, assistant.getId());
        assertEquals(firstName, assistant.getFirstName());
        assertEquals(lastName, assistant.getLastName());
        assertEquals(birthDate, assistant.getBirthDate());
        assertEquals(requireSpecialAttention, assistant.isRequireSpecialAttention());
	}
	
	@Test
    public void testToString() {
		int id = 1;
		String firstName = "José";
		String lastName = "Trujillo";
		Date birthDate = Utils.parseDate("26/01/2001");
		boolean requireSpecialAttention = true;
		
		Assistant assistant = new Assistant(
				id,
				firstName,
				lastName,
				birthDate,
				requireSpecialAttention
		);

        String expectedToString = "{id: 1, firstName: 'José', lastName: 'Trujillo', birthDate: 26/01/2001, requireSpecialAttention: true}";
        assertEquals(expectedToString, assistant.toString());
    }
	
}
