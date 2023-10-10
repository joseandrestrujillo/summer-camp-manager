package domain.factories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import domain.entities.Camp;
import domain.entities.Inscription;
import domain.exceptions.NotInTimeException;
import domain.factories.EarlyRegisterInscriptionFactory;
import domain.interfaces.IRepository;
import domain.values.EducativeLevel;
import domain.values.InscriptionType;
import repositories.InMemoryCampRepository;
import utilities.Utils;

class LateRegisterInscriptionFactoryTest  {

	@Test
	void LateRegisterInscriptionFactory_whenTheDateIsInTime_createsAnInscriptionThatCanNotBeCancelled() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("10/1/2024");
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
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository);
		Inscription inscription = factory.create(assistantId, campId, inscriptionDate, price, inscriptionType);

		assertEquals(false, inscription.canBeCanceled());
	}

	
	@Test
	void LateRegisterInscriptionFactory_whenTheDateIsNotInTime_throwNotInTimeExceptionLately() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("18/01/2024");
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
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository);
		
		assertThrows(NotInTimeException.class, 
				() -> factory.create(assistantId, campId, inscriptionDate, price, inscriptionType)
		);
	}
}
