package domain.factories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.Inscription;
import domain.exceptions.AfterLateTimeException;
import domain.exceptions.AfterStartTimeException;
import domain.exceptions.AssistantNotFoundException;
import domain.exceptions.BeforeLateTimeException;
import domain.exceptions.CampNotFoundException;
import domain.exceptions.NotInTimeException;
import domain.factories.EarlyRegisterInscriptionFactory;
import domain.interfaces.IRepository;
import domain.values.EducativeLevel;
import domain.values.InscriptionType;
import repositories.InMemoryAssistantRepository;
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
		
		IRepository<Assistant> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		Inscription inscription = factory.create(assistantId, campId, inscriptionDate, price, inscriptionType);

		assertEquals(false, inscription.canBeCanceled());
	}

	
	@Test
	void LateRegisterInscriptionFactory_whenTheDateIsNotInTime_throwAfterStartTimeExceptionException() {
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
		
		IRepository<Assistant> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(AfterStartTimeException.class, 
				() -> factory.create(assistantId, campId, inscriptionDate, price, inscriptionType)
		);
	}
	
	@Test
	void lateRegisterInscriptionFactory_whenTheDateIsNotInTime_throwBeforeLateTimeException() {
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
		
		IRepository<Assistant> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(BeforeLateTimeException.class, 
				() -> factory.create(assistantId, campId, inscriptionDate, price, inscriptionType)
		);
	}
	
	@Test
	void lateRegisterInscriptionFactory_whenTheDateIsNotInTime_throwAfterLateTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("13/01/2024");
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
		
		IRepository<Assistant> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(AfterLateTimeException.class, 
				() -> factory.create(assistantId, campId, inscriptionDate, price, inscriptionType)
		);
	}

	@Test
	void lateRegisterInscriptionFactory_whenTheAssistantNotExist_throwsAssistantNotFoundException() {
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
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, new InMemoryAssistantRepository());
		
		assertThrows(AssistantNotFoundException.class, 
				() -> factory.create(assistantId, campId, inscriptionDate, price, inscriptionType)
		);
	}
	
	@Test
	void lateRegisterInscriptionFactory_whenTheCampNotExist_throwsCamptNotFoundException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		InscriptionType inscriptionType = InscriptionType.COMPLETE;
		
		IRepository<Assistant> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(new InMemoryCampRepository(), assistantIRepository);
		
		assertThrows(CampNotFoundException.class, 
				() -> factory.create(assistantId, campId, inscriptionDate, price, inscriptionType)
		);
	}
}
