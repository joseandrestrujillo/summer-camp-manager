package repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Monitor;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

/**
 * La clase InMemoryMontiorRepository es una implementación en memoria de un repositorio de monitores.
 
 */
public class InMemoryMontiorRepository implements IRepository<Monitor, Integer> {
    private Map<Integer, Monitor> mapOfMonitor;

    /**
     * Constructor de la clase InMemoryMontiorRepository.
     * Inicializa un nuevo mapa para almacenar monitores en memoria.
     */
    public InMemoryMontiorRepository() {
        this.mapOfMonitor = new HashMap<Integer, Monitor>();
    }

    /**
     * Busca un monitor por su identificador.
     *
     * @param identifier El identificador del monitor a buscar.
     * @return El monitor encontrado.
     * @throws NotFoundException Si el monitor no se encuentra en el repositorio.
     */
    @Override
    public Monitor find(Integer identifier) {
        // Verificar si el monitor existe en el repositorio.
        if (this.mapOfMonitor.get(identifier) == null) {
            // Lanzar una excepción si el monitor no se encuentra.
            throw new NotFoundException();
        }
        // Devolver el monitor encontrado.
        return this.mapOfMonitor.get(identifier);
    }

    /**
     * Guarda un monitor en el repositorio.
     *
     * @param obj El monitor a guardar en el repositorio.
     */
    @Override
    public void save(Monitor obj) {
        // Almacenar el monitor en el mapa, utilizando su identificador como clave.
        this.mapOfMonitor.put(obj.getId(), obj);
    }

    /**
     * Obtiene una lista de todos los monitores almacenados en el repositorio.
     *
     * @return Una lista de monitores.
     */
    @Override
    public List<Monitor> getAll() {
        // Crear una lista que contenga a todos los monitores en el repositorio.
        List<Monitor> allMonitors = new ArrayList<>(this.mapOfMonitor.values());
        // Devolver la lista de monitores.
        return allMonitors;
    }

    /**
     * Elimina un monitor del repositorio.
     *
     * @param obj El monitor a eliminar del repositorio.
     */
    @Override
    public void delete(Monitor obj) {
        // Eliminar el monitor del mapa utilizando su identificador como clave.
        this.mapOfMonitor.remove(obj.getId());
    }
}
