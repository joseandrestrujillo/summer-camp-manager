package domain.factories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.Inscription;
import domain.exceptions.assistant.AssistantNotFoundException;
import domain.exceptions.camp.CampNotFoundException;
import domain.exceptions.inscription.AfterLateTimeException;
import domain.exceptions.inscription.AfterStartTimeException;
import domain.exceptions.inscription.BeforeLateTimeException;
import domain.factories.EarlyRegisterInscriptionFactory;
import domain.interfaces.IRepository;
import domain.values.EducativeLevel;
import domain.values.InscriptionType;
import repositories.memory.InMemoryAssistantRepository;
import repositories.memory.InMemoryCampRepository;
import utilities.Utils;

class LateRegisterInscriptionFactoryTest  {

	@Test
	void createPartial_whenTheDateIsInTime_createsAnInscriptionThatCanNotBeCancelled() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("10/1/2024");
		float price = 100;
		
		IRepository<Camp, Integer> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IRepository<Assistant, Integer> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		Inscription inscription = factory.createPartial(assistantId, campId, inscriptionDate, price);

		assertEquals(false, inscription.canBeCanceled());
	}

	
	@Test
	void createPartial_whenTheDateIsNotInTime_throwAfterStartTimeExceptionException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("18/01/2024");
		float price = 100;
		
		IRepository<Camp, Integer> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IRepository<Assistant, Integer> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(AfterStartTimeException.class, 
				() -> factory.createPartial(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createPartial_whenTheDateIsNotInTime_throwBeforeLateTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("24/12/2023");
		float price = 100;
		
		IRepository<Camp, Integer> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IRepository<Assistant, Integer> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(BeforeLateTimeException.class, 
				() -> factory.createPartial(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createPartial_whenTheDateIsNotInTime_throwAfterLateTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("13/01/2024");
		float price = 100;
		
		IRepository<Camp, Integer> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IRepository<Assistant, Integer> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(AfterLateTimeException.class, 
				() -> factory.createPartial(assistantId, campId, inscriptionDate, price)
		);
	}

	@Test
	void createPartial_whenTheAssistantNotExist_throwsAssistantNotFoundException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		IRepository<Camp, Integer> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, new InMemoryAssistantRepository());
		
		assertThrows(AssistantNotFoundException.class, 
				() -> factory.createPartial(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createPartial_whenTheCampNotExist_throwsCamptNotFoundException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		IRepository<Assistant, Integer> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(new InMemoryCampRepository(), assistantIRepository);
		
		assertThrows(CampNotFoundException.class, 
				() -> factory.createPartial(assistantId, campId, inscriptionDate, price)
		);
	}

	@Test
	void createComplete_whenTheDateIsInTime_createsAnInscriptionThatCanNotBeCancelled() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("10/1/2024");
		float price = 100;
		
		IRepository<Camp, Integer> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IRepository<Assistant, Integer> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		Inscription inscription = factory.createComplete(assistantId, campId, inscriptionDate, price);

		assertEquals(false, inscription.canBeCanceled());
	}

	
	@Test
	void createComplete_whenTheDateIsNotInTime_throwAfterStartTimeExceptionException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("18/01/2024");
		float price = 100;
		
		IRepository<Camp, Integer> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IRepository<Assistant, Integer> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(AfterStartTimeException.class, 
				() -> factory.createComplete(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createComplete_whenTheDateIsNotInTime_throwBeforeLateTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("24/12/2023");
		float price = 100;
		
		IRepository<Camp, Integer> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IRepository<Assistant, Integer> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(BeforeLateTimeException.class, 
				() -> factory.createComplete(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createComplete_whenTheDateIsNotInTime_throwAfterLateTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("13/01/2024");
		float price = 100;
		
		IRepository<Camp, Integer> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IRepository<Assistant, Integer> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(AfterLateTimeException.class, 
				() -> factory.createComplete(assistantId, campId, inscriptionDate, price)
		);
	}

	@Test
	void createComplete_whenTheAssistantNotExist_throwsAssistantNotFoundException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		IRepository<Camp, Integer> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, new InMemoryAssistantRepository());
		
		assertThrows(AssistantNotFoundException.class, 
				() -> factory.createComplete(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createComplete_whenTheCampNotExist_throwsCamptNotFoundException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		IRepository<Assistant, Integer> assistantIRepository = new InMemoryAssistantRepository();
		assistantIRepository.save(new Assistant(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(new InMemoryCampRepository(), assistantIRepository);
		
		assertThrows(CampNotFoundException.class, 
				() -> factory.createComplete(assistantId, campId, inscriptionDate, price)
		);
	}
}
