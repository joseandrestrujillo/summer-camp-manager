package data.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import business.dtos.InscriptionDTO;
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;

/**
 * La clase InMemoryInscriptionRepository es una implementación en memoria de un repositorio de inscripciones.

 */
public class InMemoryInscriptionRepository implements IDAO<InscriptionDTO, String> {
    private Map<String, InscriptionDTO> mapOfInscription;

    /**
     * Constructor de la clase InMemoryInscriptionRepository.
     * Inicializa un nuevo mapa para almacenar inscripciones en memoria.
     */
    public InMemoryInscriptionRepository() {
        this.mapOfInscription = new HashMap<String, InscriptionDTO>();
    }

    /**
     * Busca una inscripción por su nombre.
     *
     * @param inscriptionName El nombre de la inscripción a buscar.
     * @return La inscripción encontrada.
     * @throws NotFoundException Si la inscripción no se encuentra en el repositorio.
     */
    @Override
    public InscriptionDTO find(String inscriptionName) {
        if (this.mapOfInscription.get(inscriptionName) == null) {
            throw new NotFoundException();
        }
        return this.mapOfInscription.get(inscriptionName);
    }

    /**
     * Guarda una inscripción en el repositorio.
     *
     * @param activity La inscripción a guardar en el repositorio.
     */
    @Override
    public void save(InscriptionDTO activity) {
        this.mapOfInscription.put(activity.getInscriptionIdentifier(), activity);
    }

    /**
     * Obtiene una lista de todas las inscripciones almacenadas en el repositorio.
     *
     * @return Una lista de inscripciones.
     */
    @Override
    public List<InscriptionDTO> getAll(Optional<ICriteria> criteria) {
        List<InscriptionDTO> allInscriptions = new ArrayList<>(this.mapOfInscription.values());
        return allInscriptions;
    }

    /**
     * Elimina una inscripción del repositorio.
     *
     * @param obj La inscripción a eliminar del repositorio.
     */
    @Override
    public void delete(InscriptionDTO obj) {
        this.mapOfInscription.remove(obj.getInscriptionIdentifier());
    }
}
