package domain.factories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.CompleteInscription;
import domain.entities.PartialInscription;
import domain.exceptions.AfterEarlyTimeException;
import domain.exceptions.AfterStartTimeException;
import domain.exceptions.AssistantNotFoundException;
import domain.exceptions.CampNotFoundException;
import domain.exceptions.NotFoundException;
import domain.interfaces.AbstractInscriptionFactory;
import domain.interfaces.IRepository;
import utilities.Utils;

public class EarlyRegisterInscriptionFactory implements AbstractInscriptionFactory{
	private IRepository<Camp> campRepository;
	private IRepository<Assistant> assistantRepository;
	
	public EarlyRegisterInscriptionFactory(IRepository<Camp> campRepository, IRepository<Assistant> assistantRepository) {
		this.campRepository = campRepository;
		this.assistantRepository = assistantRepository;
	}

	@Override
	public PartialInscription createPartial(int assistantId, int campId, Date inscriptionDate, float price) {
		try {
			this.assistantRepository.find(assistantId);			
		} catch (NotFoundException e) {
			throw new AssistantNotFoundException();
		} 
		
		try {
			Camp camp = this.campRepository.find(campId);	
			
			long daysDifference = Utils.daysBetween(camp.getStart(), inscriptionDate);
			
			if ((daysDifference <= 15) && (daysDifference > 0)) {
				throw new AfterEarlyTimeException();
			} else if (daysDifference < 0) {
				throw new AfterStartTimeException();
			}
			
			return new PartialInscription(assistantId, campId, inscriptionDate, price, true);
			
		} catch (NotFoundException e) {
			throw new CampNotFoundException();
		}
	}

	@Override
	public CompleteInscription createComplete(int assistantId, int campId, Date inscriptionDate, float price) {
		try {
			this.assistantRepository.find(assistantId);			
		} catch (NotFoundException e) {
			throw new AssistantNotFoundException();
		} 
		
		try {
			Camp camp = this.campRepository.find(campId);	
			
			long daysDifference = Utils.daysBetween(camp.getStart(), inscriptionDate);
			
			if ((daysDifference <= 15) && (daysDifference > 0)) {
				throw new AfterEarlyTimeException();
			} else if (daysDifference < 0) {
				throw new AfterStartTimeException();
			}
			
			return new CompleteInscription(assistantId, campId, inscriptionDate, price, true);
			
		} catch (NotFoundException e) {
			throw new CampNotFoundException();
		}
	}
}
