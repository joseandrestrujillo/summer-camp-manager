package data.memory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.entities.Assistant;
import business.exceptions.repository.NotFoundException;
import business.interfaces.IDAO;

/**
 * La clase InMemoryAssistantRepository es una implementación en memoria de un repositorio de asistentes.
 
 */
public class InMemoryAssistantRepository implements IDAO<Assistant, Integer> {
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
        if (this.mapOfAssistants.get(identifier) == null) {
            throw new NotFoundException();
        }
        return this.mapOfAssistants.get(identifier);
    }

    /**
     * Guarda a un asistente en el repositorio.
     *
     * @param obj El asistente a guardar en el repositorio.
     */
    @Override
    public void save(Assistant obj) {
        this.mapOfAssistants.put(obj.getId(), obj);
    }

    /**
     * Obtiene una lista de todos los asistentes almacenados en el repositorio.
     *
     * @return Una lista de asistentes.
     */
    @Override
    public List<Assistant> getAll() {
        List<Assistant> allAssistants = new ArrayList<>(this.mapOfAssistants.values());
        return allAssistants;
    }

    /**
     * Elimina a un asistente del repositorio.
     *
     * @param obj El asistente a eliminar del repositorio.
     */
    @Override
    public void delete(Assistant obj) {
        this.mapOfAssistants.remove(obj.getId());
    }
}
