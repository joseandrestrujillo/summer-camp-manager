package repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Activity;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

/**
 * La clase InMemoryActivityRepository es una implementación en memoria de un repositorio de actividades.
 */
public class InMemoryActivityRepository implements IRepository<Activity, String> {
    private Map<String, Activity> mapOfActivity;

    /**
     * Constructor de la clase InMemoryActivityRepository.
     * Inicializa un nuevo mapa para almacenar actividades en memoria.
     */
    public InMemoryActivityRepository() {
        this.mapOfActivity = new HashMap<String, Activity>();
    }

    /**
     * Busca una actividad por su nombre.
     *
     * @param activityName El nombre de la actividad a buscar.
     * @return La actividad encontrada.
     * @throws NotFoundException Si la actividad no se encuentra en el repositorio.
     */
    @Override
    public Activity find(String activityName) {
        // Verificar si la actividad existe en el repositorio.
        if (this.mapOfActivity.get(activityName) == null) {
            // Lanzar una excepción si la actividad no se encuentra.
            throw new NotFoundException();
        }
        // Devolver la actividad encontrada.
        return this.mapOfActivity.get(activityName);
    }

    /**
     * Guarda una actividad en el repositorio.
     *
     * @param activity La actividad a guardar en el repositorio.
     */
    @Override
    public void save(Activity activity) {
        // Almacenar la actividad en el mapa, utilizando su nombre como clave.
        this.mapOfActivity.put(activity.getActivityName(), activity);
    }

    /**
     * Obtiene una lista de todas las actividades almacenadas en el repositorio.
     *
     * @return Una lista de actividades.
     */
    @Override
    public List<Activity> getAll() {
        // Crear una lista que contenga todas las actividades en el repositorio.
        List<Activity> allActivities = new ArrayList<>(this.mapOfActivity.values());
        // Devolver la lista de actividades.
        return allActivities;
    }

    /**
     * Elimina una actividad del repositorio.
     *
     * @param obj La actividad a eliminar del repositorio.
     */
    @Override
    public void delete(Activity obj) {
        // Eliminar la actividad del mapa utilizando su nombre como clave.
        this.mapOfActivity.remove(obj.getActivityName());
    }
}
