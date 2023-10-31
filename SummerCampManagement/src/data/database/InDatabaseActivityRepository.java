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
 * La clase InDatabaseActivityDAO es una implementación en base de datos de un DAO de actividades.
 */
public class InDatabaseActivityRepository implements IRepository<Activity, String>{

    /**
     * Constructor de la clase InDatabaseActivityDAO.
     * Inicializa un nuevo mapa para almacenar actividades en memoria y carga las actividades almacenadas en la base de datos
     * 
     * @param filePath path a la tabla de actividades
     */
    public InDatabaseActivityRepository(String filePath) {

    }
    
    /**
     * Busca una actividad por su nombre.
     *
     * @param identifier El nombre de la actividad a buscar.
     * @return La actividad encontrada.
     * @throws NotFoundException Si la actividad no se encuentra en el DAO.
     */
    @Override
    public Activity find(String identifier) {
        return null;
    }

    /**
     * Guarda una actividad en el DAO.
     *
     * @param obj La actividad a guardar en el DAO.
     */
    @Override
    public void save(Activity obj) {

    }
    
    /**
     * Obtiene una lista de todas las actividades almacenadas en el DAO.
     *
     * @return Una lista de actividades.
     */
    @Override
    public List<Activity> getAll() {
        return null;
    }

    /**
     * Elimina una actividad del DAO.
     *
     * @param obj La actividad a eliminar del DAO.
     */
    @Override
    public void delete(Activity obj) {

    }

}