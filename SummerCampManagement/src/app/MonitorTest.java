package app;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

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
	
	

}
