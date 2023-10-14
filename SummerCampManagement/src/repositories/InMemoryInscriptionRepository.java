package repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Inscription;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

/**
 * La clase InMemoryInscriptionRepository es una implementación en memoria de un repositorio de inscripciones.

 */
public class InMemoryInscriptionRepository implements IRepository<Inscription, String> {
    private Map<String, Inscription> mapOfInscription;

    /**
     * Constructor de la clase InMemoryInscriptionRepository.
     * Inicializa un nuevo mapa para almacenar inscripciones en memoria.
     */
    public InMemoryInscriptionRepository() {
        this.mapOfInscription = new HashMap<String, Inscription>();
    }

    /**
     * Busca una inscripción por su nombre.
     *
     * @param inscriptionName El nombre de la inscripción a buscar.
     * @return La inscripción encontrada.
     * @throws NotFoundException Si la inscripción no se encuentra en el repositorio.
     */
    @Override
    public Inscription find(String inscriptionName) {
        // Verificar si la inscripción existe en el repositorio.
        if (this.mapOfInscription.get(inscriptionName) == null) {
            // Lanzar una excepción si la inscripción no se encuentra.
            throw new NotFoundException();
        }
        // Devolver la inscripción encontrada.
        return this.mapOfInscription.get(inscriptionName);
    }

    /**
     * Guarda una inscripción en el repositorio.
     *
     * @param activity La inscripción a guardar en el repositorio.
     */
    @Override
    public void save(Inscription activity) {
        // Almacenar la inscripción en el mapa, utilizando su identificador como clave.
        this.mapOfInscription.put(activity.getInscriptionIdentifier(), activity);
    }

    /**
     * Obtiene una lista de todas las inscripciones almacenadas en el repositorio.
     *
     * @return Una lista de inscripciones.
     */
    @Override
    public List<Inscription> getAll() {
        // Crear una lista que contenga a todas las inscripciones en el repositorio.
        List<Inscription> allInscriptions = new ArrayList<>(this.mapOfInscription.values());
        // Devolver la lista de inscripciones.
        return allInscriptions;
    }

    /**
     * Elimina una inscripción del repositorio.
     *
     * @param obj La inscripción a eliminar del repositorio.
     */
    @Override
    public void delete(Inscription obj) {
        // Eliminar la inscripción del mapa utilizando su identificador como clave.
        this.mapOfInscription.remove(obj.getInscriptionIdentifier());
    }
}
