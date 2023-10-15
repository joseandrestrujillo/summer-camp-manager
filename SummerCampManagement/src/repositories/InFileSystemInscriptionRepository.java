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
public class InFileSystemInscriptionRepository implements IRepository<Inscription, String> {
    private Map<String, Inscription> mapOfInscription;

    /**
     * Constructor de la clase InMemoryInscriptionRepository.
     * Inicializa un nuevo mapa para almacenar inscripciones en memoria.
     */
     public InFileSystemInscriptionRepository(String filePath) {
        this.filePath = filePath;
        this.mapOfInscription = new HashMap<String, Inscription>();
        loadFromFile();
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
    public void save(Inscription activity) {
        this.mapOfInscription.put(activity.getInscriptionIdentifier(), activity);
    }

    /**
     * Obtiene una lista de todas las inscripciones almacenadas en el repositorio.
     *
     * @return Una lista de inscripciones.
     */
    @Override
    public List<Inscription> getAll() {
        List<Inscription> allInscriptions = new ArrayList<>(this.mapOfInscription.values());
        return allInscriptions;
    }

    /**
     * Elimina una inscripción del repositorio.
     *
     * @param obj La inscripción a eliminar del repositorio.
     */
    @Override
    public void delete(Inscription obj) {
        this.mapOfInscription.remove(obj.getInscriptionIdentifier());
    }
}
