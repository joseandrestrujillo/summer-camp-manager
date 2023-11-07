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
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;

/**
 * La clase InMemoryCampRepository es una implementación en memoria de un repositorio de campamentos.
 
 */
public class InMemoryCampRepository implements IDAO<CampDTO, Integer> {
    private Map<Integer, CampDTO> mapOfCamp;

    /**
     * Constructor de la clase InMemoryCampRepository.
     * Inicializa un nuevo mapa para almacenar campamentos en memoria.
     */
    public InMemoryCampRepository() {
        this.mapOfCamp = new HashMap<Integer, CampDTO>();
    }

    /**
     * Busca un campamento por su identificador.
     *
     * @param identifier El identificador del campamento a buscar.
     * @return El campamento encontrado.
     * @throws NotFoundException Si el campamento no se encuentra en el repositorio.
     */
    @Override
    public CampDTO find(Integer identifier) {
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
    public void save(CampDTO obj) {
        this.mapOfCamp.put(obj.getCampID(), obj);
    }

    /**
     * Obtiene una lista de todos los campamentos almacenados en el repositorio.
     *
     * @return Una lista de campamentos.
     */
    @Override
    public List<CampDTO> getAll(Optional<ICriteria> criteria) {
        List<CampDTO> allCamps = new ArrayList<>(this.mapOfCamp.values());
        return allCamps;
    }

    /**
     * Elimina un campamento del repositorio.
     *
     * @param obj El campamento a eliminar del repositorio.
     */
    @Override
    public void delete(CampDTO obj) {
        this.mapOfCamp.remove(obj.getCampID());
    }
}
