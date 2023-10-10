package domain.factories;

import java.util.Date;

import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.CompleteInscription;
import domain.entities.Inscription;
import domain.entities.PartialInscription;
import domain.exceptions.AfterEarlyTimeException;
import domain.exceptions.AfterLateTime;
import domain.exceptions.AfterLateTimeException;
import domain.exceptions.AfterStartTime;
import domain.exceptions.AfterStartTimeException;
import domain.exceptions.AssistantNotFoundException;
import domain.exceptions.BeforeLateTimeException;
import domain.exceptions.CampNotFoundException;
import domain.exceptions.NotFoundException;
import domain.exceptions.NotInTimeException;
import domain.interfaces.AbstractInscriptionFactory;
import domain.interfaces.IRepository;
import domain.values.InscriptionType;
import repositories.InMemoryAssistantRepository;
import utilities.Utils;

public class LateRegisterInscriptionFactory implements AbstractInscriptionFactory{
	private IRepository<Camp> campRepository;
	private IRepository<Assistant> assistantRepository;
	
	public LateRegisterInscriptionFactory(IRepository<Camp> campRepository, IRepository<Assistant> assistanRepository) {
		this.campRepository = campRepository;
		this.assistantRepository = assistanRepository;
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
			
			if (daysDifference > 15) {
				throw new BeforeLateTimeException();
			} else if (daysDifference < 0) {
				throw new AfterStartTimeException();
			} else if (daysDifference <= 2) {
				throw new AfterLateTimeException();
			}
			
			return new PartialInscription(assistantId, campId, inscriptionDate, price, false);
			
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
			
			if (daysDifference > 15) {
				throw new BeforeLateTimeException();
			} else if (daysDifference < 0) {
				throw new AfterStartTimeException();
			} else if (daysDifference <= 2) {
				throw new AfterLateTimeException();
			}
			
			return new CompleteInscription(assistantId, campId, inscriptionDate, price, false);
			
		} catch (NotFoundException e) {
			throw new CampNotFoundException();
		}
	}

}
