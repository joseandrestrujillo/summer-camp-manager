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

import business.entities.Monitor;
import business.exceptions.repository.NotFoundException;
import business.interfaces.IDAO;
/**
 * La clase InDatabaseMonitorDAO es una implementación en base de datos de un DAO de la clase monitor.
 
 */
public class InDatabaseMonitorDAO implements IDAO<Monitor, Integer>{
    /**
     * Constructor de la clase InDatabaseMonitorDAO.
     * Inicializa un nuevo mapa para almacenar monitores en memoria y recibe la ruta de la tabla como parametro
     */
    public InDatabaseMonitorDAO(String filePath) {
        
    }
    /**
     * Busca un monitor por su identificador.
     *
     * @param identifier El identificador del monitor a buscar.
     * @return El monitor encontrado.
     * @throws NotFoundException Si el monitor no se encuentra en el DAO.
     */
    @Override
    public Monitor find(Integer identifier) {
        return null;
    }
    /**
     * Guarda un monitor en el DAO.
     *
     * @param obj El monitor a guardar en el  DAO.
     */
    @Override
    public void save(Monitor obj) {
        
    }
    /**
     * Obtiene una lista de todos los monitores almacenados en el DAO.
     *
     * @return Una lista de monitores.
     */
    @Override
    public List<Monitor> getAll() {
        return null;
    }

    /**
     * Elimina un monitor del DAO.
     *
     * @param obj El monitor a eliminar del DAO.
     */
    @Override
    public void delete(Monitor obj) {
        
    }
}