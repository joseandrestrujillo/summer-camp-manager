package domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import business.entities.Inscription;
import business.values.EducativeLevel;
import business.values.InscriptionType;
import business.values.TimeSlot;
import utilities.Utils;

class InscriptionTest {

	@Test
	void testDefaultConstructor() {
		int assistantId = -1;
		int campId = -1;
		Date inscriptionDate = null;
		float price = -1;
		

		Inscription inscription = new Inscription();
		
		assertEquals(assistantId, inscription.getAssistantId());
        assertEquals(campId, inscription.getCampId());
        assertEquals(inscriptionDate, inscription.getInscriptionDate());
        assertEquals(price, inscription.getPrice());
	}

	@Test
	void testSetters() {
		Inscription inscription = new Inscription();
		int assistantId = 1;
		int campId = 4;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
				
		inscription.setAssistantId(assistantId);
		inscription.setCampId(campId);
		inscription.setInscriptionDate(inscriptionDate);
		inscription.setPrice(price);
		
		assertEquals(assistantId, inscription.getAssistantId());
		assertEquals(campId, inscription.getCampId());
		assertEquals(inscriptionDate, inscription.getInscriptionDate());
		assertEquals(price, inscription.getPrice());
		
	}

	@Test
	void testConstructorAndGetters() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		
		
		Inscription inscription = new Inscription(assistantId, campId, inscriptionDate, price, true);
		
		assertEquals(assistantId, inscription.getAssistantId());
		assertEquals(campId, inscription.getCampId());
		assertEquals(inscriptionDate, inscription.getInscriptionDate());
		assertEquals(price, inscription.getPrice());
		assertEquals(true, inscription.canBeCanceled());
	}


	@Test
    public void testToString() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		Inscription inscription = new Inscription(assistantId,campId,inscriptionDate,price, true);

        String expectedToString = "{AssistantId: 1, CampId: 1, InscriptionDate: '12/01/2024', Price: 100.0, canBeCancelled: true}";
        assertEquals(expectedToString, inscription.toString());
    }
	
	@Test
    public void testFromString() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		

        String inputString = "{AssistantId: 1, CampId: 1, InscriptionDate: '12/01/2024', Price: 100.0, canBeCancelled: true}";

        Inscription inscription = Inscription.fromString(inputString);
        
        assertEquals(assistantId, inscription.getAssistantId());
        assertEquals(campId, inscription.getCampId());
        assertEquals(inscriptionDate, inscription.getInscriptionDate());
        assertEquals(price, inscription.getPrice());
        assertEquals(true, inscription.canBeCanceled());
    }
	
}
