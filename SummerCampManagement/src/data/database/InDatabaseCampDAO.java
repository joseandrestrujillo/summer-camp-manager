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

import business.entities.Camp;
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
/**
 * La clase InDatabaseCampDAO es una implementación en base de datos de un DAO de campamentos.
 
 */
public class InDatabaseCampDAO implements IDAO<Camp, Integer> {
     /**
     * Constructor de la clase InDatabaseCampDAO.
     * @param filePath La ruta de la tabla
     */

    public InDatabaseCampDAO(String filePath) {
       
    }
     /**
     * Busca un campamento por su identificador.
     *
     * @param identifier El identificador del campamento a buscar.
     * @return El campamento encontrado.
     * @throws NotFoundException Si el campamento no se encuentra en el DAO.
     */

    @Override
    public Camp find(Integer identifier) {
        return null;
    }

    /**
     * Guarda un campamento en el DAO.
     *
     * @param obj El campamento a guardar en el DAO.
     */

    @Override
    public void save(Camp obj) {

    }
    /**
     * Obtiene una lista de todos los campamentos almacenados en el DAO.
     *
     * @return Una lista de campamentos.
     */

    @Override
    public List<Camp> getAll(Optional<ICriteria> criteria) {
        return null;
    }
        /**
     * Elimina un campamento del DAO.
     *
     * @param obj El campamento a eliminar del DAO.
     */

    @Override
    public void delete(Camp obj) {
        
    }

}
