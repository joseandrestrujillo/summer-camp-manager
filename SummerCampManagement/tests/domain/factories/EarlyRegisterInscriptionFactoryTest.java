package domain.factories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.Inscription;
import domain.exceptions.AfterEarlyTimeException;
import domain.exceptions.AfterStartTimeException;
import domain.exceptions.AssistantNotFoundException;
import domain.exceptions.CampNotFoundException;
import domain.interfaces.IRepository;
import domain.values.EducativeLevel;
import repositories.InMemoryAssistantRepository;
import repositories.InMemoryCampRepository;
import utilities.Utils;

class EarlyRegisterInscriptionFactoryTest  {

	@Test
	void createPartial_whenTheDateIsInTime_createsAnInscriptionThatCanBeCancelled() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("24/12/2023");
		float price = 100;
		
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
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(campRepository, assistantIRepository);
		Inscription inscription = factory.createPartial(assistantId, campId, inscriptionDate, price);

		assertEquals(true, inscription.canBeCanceled());
	}

	
	@Test
	void createPartial_whenTheDateIsNotInTime_throwAfterEarlyTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
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
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(AfterEarlyTimeException.class, 
				() -> factory.createPartial(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createPartial_whenTheDateAfterStartTimeOfTheCamp_throwAfterStartTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("16/01/2024");
		float price = 100;
		
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
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(AfterStartTimeException.class, 
				() -> factory.createPartial(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createPartial_whenTheAssistantNotExist_throwsAssistantNotFoundException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		IRepository<Camp> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(campRepository, new InMemoryAssistantRepository());
		
		assertThrows(AssistantNotFoundException.class, 
				() -> factory.createPartial(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createPartial_whenTheCampNotExist_throwsCampNotFoundException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		IRepository<Assistant> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(new InMemoryCampRepository(), assistantIRepository);
		
		assertThrows(CampNotFoundException.class, 
				() -> factory.createPartial(assistantId, campId, inscriptionDate, price)
		);
	}

	@Test
	void createComplete_whenTheDateIsInTime_createsAnInscriptionThatCanBeCancelled() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("24/12/2023");
		float price = 100;
		
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
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(campRepository, assistantIRepository);
		Inscription inscription = factory.createComplete(assistantId, campId, inscriptionDate, price);

		assertEquals(true, inscription.canBeCanceled());
	}

	
	@Test
	void createComplete_whenTheDateIsNotInTime_throwAfterEarlyTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
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
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(AfterEarlyTimeException.class, 
				() -> factory.createComplete(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createComplete_whenTheDateAfterStartTimeOfTheCamp_throwAfterStartTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("16/01/2024");
		float price = 100;
		
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
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(AfterStartTimeException.class, 
				() -> factory.createComplete(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createComplete_whenTheAssistantNotExist_throwsAssistantNotFoundException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		IRepository<Camp> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(campRepository, new InMemoryAssistantRepository());
		
		assertThrows(AssistantNotFoundException.class, 
				() -> factory.createComplete(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createComplete_whenTheCampNotExist_throwsCampNotFoundException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		IRepository<Assistant> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(new InMemoryCampRepository(), assistantIRepository);
		
		assertThrows(CampNotFoundException.class, 
				() -> factory.createComplete(assistantId, campId, inscriptionDate, price)
		);
	}
}
