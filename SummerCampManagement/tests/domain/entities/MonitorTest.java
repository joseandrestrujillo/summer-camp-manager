package domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import business.entities.Monitor;
import utilities.Utils;

class MonitorTest {
	
	@Test
	void testConstructorAndGetters() {
		int id = 1;
		String firstName = "Alberto";
		String lastName = "Quesada";
		boolean specialEducator = true;
		
		Monitor monitor = new Monitor(
				id,
				firstName,
				lastName,
				specialEducator
		);
		
		assertEquals(id, monitor.getId());
        assertEquals(firstName, monitor.getFirstName());
        assertEquals(lastName, monitor.getLastName());
        assertEquals(specialEducator, monitor.isSpecialEducator());
	}
	
	@Test
	void testDefaultConstructor() {
		int id = 0;
		String firstName = "";
		String lastName = "";
		boolean specialEducator = false;
		
		Monitor monitor = new Monitor();
		
		assertEquals(id, monitor.getId());
        assertEquals(firstName, monitor.getFirstName());
        assertEquals(lastName, monitor.getLastName());
        assertEquals(specialEducator, monitor.isSpecialEducator());
	}
	
	
	@Test
	void testSetters() {
		Monitor monitor = new Monitor();
		int id = 1;
		String firstName = "Alberto";
		String lastName = "Quesada";
		boolean specialEducator = true;
		
		monitor.setId(id);
		monitor.setFirstName(firstName);
		monitor.setLastName(lastName);
		monitor.setSpecialEducator(specialEducator);
		
		assertEquals(id, monitor.getId());
        assertEquals(firstName, monitor.getFirstName());
        assertEquals(lastName, monitor.getLastName());
        assertEquals(specialEducator, monitor.isSpecialEducator());
	}
	
	@Test
    public void testToString() {
		int id = 1;
		String firstName = "Alberto";
		String lastName = "Quesada";
		boolean specialEducator = true;
		
		Monitor monitor = new Monitor(
				id,
				firstName,
				lastName,
				specialEducator
		);

        String expectedToString = "{id: 1, firstName: 'Alberto', lastName: 'Quesada', isSpecialEducator: true}";
        assertEquals(expectedToString, monitor.toString());
    }
	
	@Test
    public void testFromString() {
		int id = 1;
		String firstName = "José";
		String lastName = "Trujillo";
		boolean isSpecialEducator = true;
		

        String inputString = "{id: 1, firstName: 'José', lastName: 'Trujillo', isSpecialEducator: true}";
        Monitor monitor = Monitor.fromString(inputString);
        
        assertEquals(id, monitor.getId());
        assertEquals(firstName, monitor.getFirstName());
        assertEquals(lastName, monitor.getLastName());
        assertEquals(isSpecialEducator, monitor.isSpecialEducator());
    }

}
