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
import business.interfaces.IRepository;
/**
 * La clase InDatabaseMonitorRepository es una implementación en base de datos de un sistema de ficheros de la clase monitor.
 
 */
public class InDatabaseMonitorRepository implements IRepository<Monitor, Integer>{
    /**
     * Constructor de la clase InFileSystemMonitorRepository.
     * Inicializa un nuevo mapa para almacenar monitores en memoria y recibe la ruta de fichero como parametro
     */
    public InDatabaseMonitorRepository(String filePath) {
        
    }
    /**
     * Busca un monitor por su identificador.
     *
     * @param identifier El identificador del monitor a buscar.
     * @return El monitor encontrado.
     * @throws NotFoundException Si el monitor no se encuentra en el sistema de archivos.
     */
    @Override
    public Monitor find(Integer identifier) {
        return null;
    }
    /**
     * Guarda un monitor en el sistema de archivos.
     *
     * @param obj El monitor a guardar en el  sistema de archivos.
     */
    @Override
    public void save(Monitor obj) {
        
    }
    /**
     * Obtiene una lista de todos los monitores almacenados en el sistema de archivos.
     *
     * @return Una lista de monitores.
     */
    @Override
    public List<Monitor> getAll() {
        return null;
    }

    /**
     * Elimina un monitor del sistema de archivos.
     *
     * @param obj El monitor a eliminar del sistema de archivos.
     */
    @Override
    public void delete(Monitor obj) {
        
    }
}