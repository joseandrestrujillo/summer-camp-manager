package domain.factories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import domain.entities.Camp;
import domain.entities.Inscription;
import domain.exceptions.NotInTimeException;
import domain.factories.EarlyRegisterInscriptionFactory;
import domain.interfaces.IRepository;
import domain.repositories.InMemoryCampRepository;
import domain.values.EducativeLevel;
import domain.values.InscriptionType;
import utilities.Utils;

class EarlyRegisterInscriptionFactoryTest  {

	@Test
	void earlyRegisterInscriptionFactory_whenTheDateIsInTime_createsAnInscriptionThatCanBeCancelled() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("24/12/2023");
		float price = 100;
		InscriptionType inscriptionType = InscriptionType.COMPLETE;
		
		IRepository<Camp> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(campRepository);
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
		
		IRepository<Camp> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(campRepository);
		
		assertThrows(NotInTimeException.class, 
				() -> factory.create(assistantId, campId, inscriptionDate, price, inscriptionType)
		);
	}
}
