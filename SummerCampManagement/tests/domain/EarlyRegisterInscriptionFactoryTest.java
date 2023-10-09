package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import utilities.InscriptionType;
import utilities.Utils;

class EarlyRegisterInscriptionFactoryTest {

	@Test
	void earlyRegisterInscriptionFactory_whenTheDateIsInTime_createsAnInscriptionThatCanBeCancelled() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		InscriptionType inscriptionType = InscriptionType.COMPLETE;
		
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory();
		Inscription inscription = factory.create(assistantId, campId, inscriptionDate, price, inscriptionType);

		assertEquals(true, inscription.canBeCanceled());
	}

	
	@Test
	void earlyRegisterInscriptionFactory_whenTheDateIsNotInTime_throwNotInTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		InscriptionType inscriptionType = InscriptionType.COMPLETE;
		
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory();
		Inscription inscription = factory.create(assistantId, campId, inscriptionDate, price, inscriptionType);
	}
}
