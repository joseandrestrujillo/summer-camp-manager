package data.database;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.entities.Inscription;
import business.exceptions.repository.NotFoundException;
import business.interfaces.IRepository;


/**
 * La clase InDatabaseInscriptionRepository es una implementación en base de datos de un repositorio de inscripciones.

 */

public class InDatabaseInscriptionRepository implements IRepository<Inscription, String> {
    /**
     * Constructor de la clase InFileSystemInscriptionRepository.
     * Inicializa un nuevo mapa para almacenar inscripciones en memoria.
     */

    public InDatabaseInscriptionRepository(String filePath) {
       
    }


    /**
     * Busca una inscripción por su nombre.
     *
     * @param identifier El nombre de la inscripción a buscar.
     * @return La inscripción encontrada.
     * @throws NotFoundException Si la inscripción no se encuentra en el repositorio.
     */

    @Override
    public Inscription find(String identifier) {
        return null;
    }


    /**
     * Guarda una inscripción en el repositorio.
     *
     * @param obj La inscripción a guardar en el repositorio.
     */

    @Override
    public void save(Inscription obj) {
       
    }


    /**
     * Obtiene una lista de todas las inscripciones almacenadas en el repositorio.
     *
     * @return Una lista de inscripciones.
     */

    @Override
    public List<Inscription> getAll() {
        return null;
    }

    /**
     * Elimina una inscripción del repositorio.
     *
     * @param obj La inscripción a eliminar del repositorio.
     */ 

    @Override
    public void delete(Inscription obj) {
       
    }
}
