package repositories.memory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Activity;
import domain.entities.Assistant;
import domain.entities.Camp;
import domain.exceptions.repository.NotFoundException;
import domain.interfaces.IRepository;

/**
 * La clase InMemoryCampRepository es una implementación en memoria de un repositorio de campamentos.
 
 */
public class InMemoryCampRepository implements IRepository<Camp, Integer> {
    private Map<Integer, Camp> mapOfCamp;

    /**
     * Constructor de la clase InMemoryCampRepository.
     * Inicializa un nuevo mapa para almacenar campamentos en memoria.
     */
    public InMemoryCampRepository() {
        this.mapOfCamp = new HashMap<Integer, Camp>();
    }

    /**
     * Busca un campamento por su identificador.
     *
     * @param identifier El identificador del campamento a buscar.
     * @return El campamento encontrado.
     * @throws NotFoundException Si el campamento no se encuentra en el repositorio.
     */
    @Override
    public Camp find(Integer identifier) {
        if (this.mapOfCamp.get(identifier) == null) {
            throw new NotFoundException();
        }
        return this.mapOfCamp.get(identifier);
    }

    /**
     * Guarda un campamento en el repositorio.
     *
     * @param obj El campamento a guardar en el repositorio.
     */
    @Override
    public void save(Camp obj) {
        this.mapOfCamp.put(obj.getCampID(), obj);
    }

    /**
     * Obtiene una lista de todos los campamentos almacenados en el repositorio.
     *
     * @return Una lista de campamentos.
     */
    @Override
    public List<Camp> getAll() {
        List<Camp> allCamps = new ArrayList<>(this.mapOfCamp.values());
        return allCamps;
    }

    /**
     * Elimina un campamento del repositorio.
     *
     * @param obj El campamento a eliminar del repositorio.
     */
    @Override
    public void delete(Camp obj) {
        this.mapOfCamp.remove(obj.getCampID());
    }
}
