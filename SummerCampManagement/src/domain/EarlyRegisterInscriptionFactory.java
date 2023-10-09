package domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import utilities.InscriptionType;
import utilities.NotInTimeException;
import utilities.Utils;

public class EarlyRegisterInscriptionFactory extends AbstractInscriptionFactory{
	private IRepository<Camp> campRepository;
	
	public EarlyRegisterInscriptionFactory(IRepository<Camp> campRepository) {
		this.campRepository = campRepository;
	}

	@Override
	public Inscription create(int assistantId, int campId, Date inscriptionDate, float price,
			InscriptionType inscriptionType) {
		Camp camp = this.campRepository.find(campId);
		
		long daysDifference = Utils.daysBetween(inscriptionDate, camp.getStart());
		if (daysDifference <= 15) {
			throw new NotInTimeException();
		}
		
		return new Inscription(assistantId, campId, inscriptionDate, price, inscriptionType, true);
	}
}
