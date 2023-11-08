package data.memory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.exceptions.repository.NotFoundException;
import business.interfaces.IAssistantDAO;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import business.values.TimeSlot;

/**
 * La clase InMemoryAssistantRepository es una implementación en memoria de un repositorio de asistentes.
 
 */
public class InMemoryAssistantRepository implements IAssistantDAO {
    private MapsManager mapsManager;

    /**
     * Constructor de la clase InMemoryAssistantRepository.
     * Inicializa un nuevo mapa para almacenar asistentes en memoria.
     */
    public InMemoryAssistantRepository() {
        this.mapsManager = MapsManager.getInstance();
    }

    /**
     * Busca a un asistente por su identificador.
     *
     * @param identifier El identificador del asistente a buscar.
     * @return El asistente encontrado.
     * @throws NotFoundException Si el asistente no se encuentra en el repositorio.
     */
    @Override
    public AssistantDTO find(Integer identifier) {
        if (this.mapsManager.getMapOfAssistants().get(identifier) == null) {
            throw new NotFoundException();
        }
        return this.mapsManager.getMapOfAssistants().get(identifier);
    }

    /**
     * Guarda a un asistente en el repositorio.
     *
     * @param obj El asistente a guardar en el repositorio.
     */
    @Override
    public void save(AssistantDTO obj) {
        this.mapsManager.getMapOfAssistants().put(obj.getId(), obj);
    }

    /**
     * Obtiene una lista de todos los asistentes almacenados en el repositorio.
     *
     * @return Una lista de asistentes.
     */
    @Override
    public List<AssistantDTO> getAll(Optional<ICriteria> criteria) {
        List<AssistantDTO> allAssistants = new ArrayList<>(this.mapsManager.getMapOfAssistants().values());
        return allAssistants;
    }

    /**
     * Elimina a un asistente del repositorio.
     *
     * @param obj El asistente a eliminar del repositorio.
     */
    @Override
    public void delete(AssistantDTO obj) {
        this.mapsManager.getMapOfAssistants().remove(obj.getId());
    }

	@Override
	public List<AssistantDTO> getAssistantsInACamp(CampDTO camp) {
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		List<InscriptionDTO> inscriptions = inscriptionRepository.getAll();
		
		List<AssistantDTO> assistants = new ArrayList<AssistantDTO>();
		for (InscriptionDTO inscriptionDTO : inscriptions) {
			if(inscriptionDTO.getCampId() == camp.getCampID()) {
				assistants.add(find(inscriptionDTO.getAssistantId()));
			}
		}
		return assistants;
	}

	@Override
	public List<AssistantDTO> getAssistantsInAnActivity(ActivityDTO activity) {
		InMemoryInscriptionRepository inscriptionRepository = new InMemoryInscriptionRepository();
		InMemoryActivityRepository activityRepository = new InMemoryActivityRepository();
		InMemoryCampRepository campRepository = new InMemoryCampRepository();
		List<InscriptionDTO> inscriptions = inscriptionRepository.getAll();
		
		List<AssistantDTO> assistants = new ArrayList<AssistantDTO>();
		for (InscriptionDTO inscriptionDTO : inscriptions) {
			CampDTO camp = campRepository.find(inscriptionDTO.getCampId());
			List<ActivityDTO> activities = activityRepository.getActivitiesInACamp(camp);
			for (ActivityDTO activityi: activities) {				
				if(activityi.getActivityName() == activity.getActivityName()) {
					if (inscriptionDTO.isPartial()) {
						if (activity.getTimeSlot() == TimeSlot.MORNING) {
							assistants.add(find(inscriptionDTO.getAssistantId()));
						}
					}else {
						assistants.add(find(inscriptionDTO.getAssistantId()));
					}
					
				}
			}
			
		}
		return assistants;
	}
}
