package repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Assistant;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

/**
 * La clase InMemoryAssistantRepository es una implementación en memoria de un repositorio de asistentes.
 *
 * @param <Assistant> El tipo de entidad que se almacena en el repositorio.
 * @param <Integer>   El tipo de la clave utilizada para identificar a los asistentes.
 */
public class InMemoryAssistantRepository implements IRepository<Assistant, Integer> {
    private Map<Integer, Assistant> mapOfAssistants;

    /**
     * Constructor de la clase InMemoryAssistantRepository.
     * Inicializa un nuevo mapa para almacenar asistentes en memoria.
     */
    public InMemoryAssistantRepository() {
        this.mapOfAssistants = new HashMap<Integer, Assistant>();
    }

    /**
     * Busca a un asistente por su identificador.
     *
     * @param identifier El identificador del asistente a buscar.
     * @return El asistente encontrado.
     * @throws NotFoundException Si el asistente no se encuentra en el repositorio.
     */
    @Override
    public Assistant find(Integer identifier) {
        // Verificar si el asistente existe en el repositorio.
        if (this.mapOfAssistants.get(identifier) == null) {
            // Lanzar una excepción si el asistente no se encuentra.
            throw new NotFoundException();
        }
        // Devolver el asistente encontrado.
        return this.mapOfAssistants.get(identifier);
    }

    /**
     * Guarda a un asistente en el repositorio.
     *
     * @param obj El asistente a guardar en el repositorio.
     */
    @Override
    public void save(Assistant obj) {
        // Almacenar al asistente en el mapa, utilizando su identificador como clave.
        this.mapOfAssistants.put(obj.getId(), obj);
    }

    /**
     * Obtiene una lista de todos los asistentes almacenados en el repositorio.
     *
     * @return Una lista de asistentes.
     */
    @Override
    public List<Assistant> getAll() {
        // Crear una lista que contenga a todos los asistentes en el repositorio.
        List<Assistant> allAssistants = new ArrayList<>(this.mapOfAssistants.values());
        // Devolver la lista de asistentes.
        return allAssistants;
    }

    /**
     * Elimina a un asistente del repositorio.
     *
     * @param obj El asistente a eliminar del repositorio.
     */
    @Override
    public void delete(Assistant obj) {
        // Eliminar al asistente del mapa utilizando su identificador como clave.
        this.mapOfAssistants.remove(obj.getId());
    }
}
