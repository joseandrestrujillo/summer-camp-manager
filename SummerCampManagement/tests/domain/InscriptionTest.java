package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import utilities.InscriptionType;

class InscriptionTest {

	@Test
	void testDefaultConstructor() {
		int assistantId = -1;
		int campId = -1;
		Date inscriptionDate = null;
		float price = -1;
		InscriptionType inscriptionType = null;
		

		Inscription inscription = new Inscription();
		
		assertEquals(assistantId, inscription.getAssistantId());
        assertEquals(campId, inscription.getCampId());
        assertEquals(inscriptionDate, inscription.getInscriptionDate());
        assertEquals(price, inscription.getPrice());
        assertEquals(inscriptionType, inscription.getInscriptionType());
	}
}
