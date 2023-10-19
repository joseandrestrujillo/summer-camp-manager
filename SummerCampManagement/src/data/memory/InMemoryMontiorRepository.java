package data.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.entities.Monitor;
import business.exceptions.repository.NotFoundException;
import business.interfaces.IRepository;

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
        if (this.mapOfMonitor.get(identifier) == null) {
            throw new NotFoundException();
        }
        return this.mapOfMonitor.get(identifier);
    }

    /**
     * Guarda un monitor en el repositorio.
     *
     * @param obj El monitor a guardar en el repositorio.
     */
    @Override
    public void save(Monitor obj) {
        this.mapOfMonitor.put(obj.getId(), obj);
    }

    /**
     * Obtiene una lista de todos los monitores almacenados en el repositorio.
     *
     * @return Una lista de monitores.
     */
    @Override
    public List<Monitor> getAll() {
        List<Monitor> allMonitors = new ArrayList<>(this.mapOfMonitor.values());
        return allMonitors;
    }

    /**
     * Elimina un monitor del repositorio.
     *
     * @param obj El monitor a eliminar del repositorio.
     */
    @Override
    public void delete(Monitor obj) {
        this.mapOfMonitor.remove(obj.getId());
    }
}
