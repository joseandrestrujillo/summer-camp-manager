package data.memory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import business.dtos.ActivityDTO;
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;

/**
 * La clase InMemoryActivityRepository es una implementación en memoria de un repositorio de actividades.
 */
public class InMemoryActivityRepository implements IDAO<ActivityDTO, String> {
    private Map<String, ActivityDTO> mapOfActivity;

    /**
     * Constructor de la clase InMemoryActivityRepository.
     * Inicializa un nuevo mapa para almacenar actividades en memoria.
     */
    public InMemoryActivityRepository() {
        this.mapOfActivity = new HashMap<String, ActivityDTO>();
    }

    /**
     * Busca una actividad por su nombre.
     *
     * @param activityName El nombre de la actividad a buscar.
     * @return La actividad encontrada.
     * @throws NotFoundException Si la actividad no se encuentra en el repositorio.
     */
    @Override
    public ActivityDTO find(String activityName) {
        if (this.mapOfActivity.get(activityName) == null) {
            throw new NotFoundException();
        }
        return this.mapOfActivity.get(activityName);
    }

    /**
     * Guarda una actividad en el repositorio.
     *
     * @param activity La actividad a guardar en el repositorio.
     */
    @Override
    public void save(ActivityDTO activity) {
        this.mapOfActivity.put(activity.getActivityName(), activity);
    }

    /**
     * Obtiene una lista de todas las actividades almacenadas en el repositorio.
     *
     * @return Una lista de actividades.
     */
    @Override
    public List<ActivityDTO> getAll(Optional<ICriteria> criteria) {
        List<ActivityDTO> allActivities = new ArrayList<>(this.mapOfActivity.values());
        return allActivities;
    }

    /**
     * Elimina una actividad del repositorio.
     *
     * @param obj La actividad a eliminar del repositorio.
     */
    @Override
    public void delete(ActivityDTO obj) {
        this.mapOfActivity.remove(obj.getActivityName());
    }
}
