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
import java.util.Optional;

import business.entities.Inscription;
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;


/**
 * La clase InDatabaseInscriptionDAO es una implementación en base de datos de un DAO de inscripciones.

 */

public class InDatabaseInscriptionDAO implements IDAO<Inscription, String> {
    /**
     * Constructor de la clase InDatabaseInscriptionDA0.
     * Inicializa un nuevo mapa para almacenar inscripciones en memoria.
     */

    public InDatabaseInscriptionDAO(String filePath) {
       
    }


    /**
     * Busca una inscripción por su nombre.
     *
     * @param identifier El nombre de la inscripción a buscar.
     * @return La inscripción encontrada.
     * @throws NotFoundException Si la inscripción no se encuentra en el DAO.
     */

    @Override
    public Inscription find(String identifier) {
        return null;
    }


    /**
     * Guarda una inscripción en el DAO.
     *
     * @param obj La inscripción a guardar en el DAO.
     */

    @Override
    public void save(Inscription obj) {
       
    }


    /**
     * Obtiene una lista de todas las inscripciones almacenadas en el DAO.
     *
     * @return Una lista de inscripciones.
     */

    @Override
    public List<Inscription> getAll(Optional<ICriteria> criteria) {
        return null;
    }

    /**
     * Elimina una inscripción del DAO.
     *
     * @param obj La inscripción a eliminar del DAO.
     */ 

    @Override
    public void delete(Inscription obj) {
       
    }
}
