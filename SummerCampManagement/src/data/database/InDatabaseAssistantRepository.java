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
 * La clase InDatabaseAssistantDAO es una implementación en base de datos de un DAO de asistentes.
 
 */
public class InDatabaseAssistantRepository implements IRepository<Assistant, Integer>{

    /**
     * Constructor de la clase InDatabaseAssistantDAO.
     * Inicializa un nuevo mapa para almacenar asistentes en memoria y carga los asistentes almacenados en el DAO
     * 
     * @param filePath path al DAO de asistentes
     */
    public InDatabaseAssistantRepository(String filePath) {

    }
    /**
     * Busca a un asistente por su identificador.
     *
     * @param identifier El identificador del asistente a buscar.
     * @return El asistente encontrado.
     * @throws NotFoundException Si el asistente no se encuentra en el DAO.
     */
    @Override
    public Assistant find(Integer identifier) {
        return null;
    }
    /**
     * Guarda a un asistente en el DAO.
     *
     * @param obj El asistente a guardar en el DAO.
     */
    @Override
    public void save(Assistant obj) {

    }
    /**
     * Obtiene una lista de todos los asistentes almacenados en el DAO.
     *
     * @return Una lista de asistentes.
     */
    @Override
    public List<Assistant> getAll() {
        return null;
    }
    /**
     * Elimina a un asistente del DAO.
     *
     * @param obj El asistente a eliminar del DAO.
     */
    @Override
    public void delete(Assistant obj) {

    }

}