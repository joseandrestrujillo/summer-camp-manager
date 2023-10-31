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

import business.entities.Assistant;
import business.exceptions.repository.NotFoundException;
import business.interfaces.IRepository;


/**
 * La clase InDatabaseAssistantRepository es una implementación en base de datos de ficheros de un repositorio de asistentes.
 
 */
public class InDatabaseAssistantRepository implements IRepository<Assistant, Integer>{

    /**
     * Constructor de la clase InFileSystemAssistantRepository.
     * Inicializa un nuevo mapa para almacenar asistentes en memoria y carga los asistentes almacenados en el fichero
     * 
     * @param filePath path al fichero de asistentes
     */
    public InDatabaseAssistantRepository(String filePath) {

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
        return null;
    }
    /**
     * Guarda a un asistente en el repositorio.
     *
     * @param obj El asistente a guardar en el repositorio.
     */
    @Override
    public void save(Assistant obj) {

    }
    /**
     * Obtiene una lista de todos los asistentes almacenados en el repositorio.
     *
     * @return Una lista de asistentes.
     */
    @Override
    public List<Assistant> getAll() {
        return null;
    }
    /**
     * Elimina a un asistente del repositorio.
     *
     * @param obj El asistente a eliminar del repositorio.
     */
    @Override
    public void delete(Assistant obj) {

    }

}