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
	
	
	

}
