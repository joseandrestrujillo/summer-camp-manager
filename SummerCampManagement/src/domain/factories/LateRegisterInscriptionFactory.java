package domain.factories;

import java.util.Date;

import domain.entities.Camp;
import domain.entities.Inscription;
import domain.exceptions.NotInTimeException;
import domain.interfaces.AbstractInscriptionFactory;
import domain.interfaces.IRepository;
import domain.values.InscriptionType;
import utilities.Utils;

public class LateRegisterInscriptionFactory implements AbstractInscriptionFactory{
private IRepository<Camp> campRepository;
	
	public LateRegisterInscriptionFactory(IRepository<Camp> campRepository) {
		this.campRepository = campRepository;
	}
	public Inscription create(int assistantId, int campId, Date inscriptionDate, float price,
			InscriptionType inscriptionType) {
Camp camp = this.campRepository.find(campId);
		
		long daysDifference = Utils.daysBetween(inscriptionDate, camp.getStart());
		if (daysDifference <= 2 || daysDifference > 15) {
			throw new NotInTimeException();
		}
		
		return new Inscription(assistantId, campId, inscriptionDate, price, inscriptionType, false);
	}

}
