package domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import domain.entities.Inscription;
import domain.values.EducativeLevel;
import domain.values.InscriptionType;
import domain.values.TimeSlot;
import utilities.Utils;

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

	@Test
	void testSetters() {
		Inscription inscription = new Inscription();
		int assistantId = 1;
		int campId = 4;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		InscriptionType inscriptionType =  InscriptionType.COMPLETE;
				
		inscription.setAssistantId(assistantId);
		inscription.setCampId(campId);
		inscription.setInscriptionDate(inscriptionDate);
		inscription.setPrice(price);
		inscription.setInscriptionType(inscriptionType);
		
		assertEquals(assistantId, inscription.getAssistantId());
		assertEquals(campId, inscription.getCampId());
		assertEquals(inscriptionDate, inscription.getInscriptionDate());
		assertEquals(price, inscription.getPrice());
		assertEquals(inscriptionType, inscription.getInscriptionType());
		
	}

	@Test
	void testConstructorAndGetters() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		InscriptionType inscriptionType = InscriptionType.COMPLETE;
		
		
		
		Inscription inscription = new Inscription(assistantId, campId, inscriptionDate, price, inscriptionType, true);
		
		assertEquals(assistantId, inscription.getAssistantId());
		assertEquals(campId, inscription.getCampId());
		assertEquals(inscriptionDate, inscription.getInscriptionDate());
		assertEquals(price, inscription.getPrice());
		assertEquals(inscriptionType, inscription.getInscriptionType());
		assertEquals(true, inscription.canBeCanceled());
	}


	@Test
    public void testToString() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		InscriptionType inscriptionType = InscriptionType.COMPLETE;
		
		Inscription inscription = new Inscription(assistantId,campId,inscriptionDate,price,inscriptionType, true);

        String expectedToString = "{AssistantId: 1, CampId: '1', InscriptionDate: '12/01/2024, Price: 100.0, InscriptionType: COMPLETE}";
        assertEquals(expectedToString, inscription.toString());
    }
	
}
