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

import business.entities.Activity;
import business.exceptions.repository.NotFoundException;
import business.interfaces.IRepository;

/**
 * La clase InDatabaseActivityRepository es una implementación en base de datos de ficheros de un repositorio de actividades.
 */
public class InDatabaseActivityRepository implements IRepository<Activity, String>{

    /**
     * Constructor de la clase InFileSystemActivityRepository.
     * Inicializa un nuevo mapa para almacenar actividades en memoria y carga las actividades almacenadas en la base de datos
     * 
     * @param filePath path al fichero de actividades
     */
    public InDatabaseActivityRepository(String filePath) {

    }
    
    /**
     * Busca una actividad por su nombre.
     *
     * @param identifier El nombre de la actividad a buscar.
     * @return La actividad encontrada.
     * @throws NotFoundException Si la actividad no se encuentra en el repositorio.
     */
    @Override
    public Activity find(String identifier) {
        return null;
    }

    /**
     * Guarda una actividad en el repositorio.
     *
     * @param obj La actividad a guardar en el repositorio.
     */
    @Override
    public void save(Activity obj) {

    }
    
    /**
     * Obtiene una lista de todas las actividades almacenadas en el repositorio.
     *
     * @return Una lista de actividades.
     */
    @Override
    public List<Activity> getAll() {
        return null;
    }

    /**
     * Elimina una actividad del repositorio.
     *
     * @param obj La actividad a eliminar del repositorio.
     */
    @Override
    public void delete(Activity obj) {

    }

}