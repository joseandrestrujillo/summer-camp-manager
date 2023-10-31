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

import business.entities.Camp;
import business.exceptions.repository.NotFoundException;
import business.interfaces.IRepository;
/**
 * La clase InDatabaseCampRepository es una implementación en base de datos de ficheros de un repositorio de campamentos.
 
 */
public class InDatabaseCampRepository implements IRepository<Camp, Integer> {
     /**
     * Constructor de la clase InFileSystemCampRepository.
     * @param filePath La ruta del archivo
     */

    public InDatabaseCampRepository(String filePath) {
       
    }
     /**
     * Busca un campamento por su identificador.
     *
     * @param identifier El identificador del campamento a buscar.
     * @return El campamento encontrado.
     * @throws NotFoundException Si el campamento no se encuentra en el repositorio.
     */

    @Override
    public Camp find(Integer identifier) {
        return null;
    }

    /**
     * Guarda un campamento en el repositorio.
     *
     * @param obj El campamento a guardar en el repositorio.
     */

    @Override
    public void save(Camp obj) {

    }
    /**
     * Obtiene una lista de todos los campamentos almacenados en el repositorio.
     *
     * @return Una lista de campamentos.
     */

    @Override
    public List<Camp> getAll() {
        return null;
    }
        /**
     * Elimina un campamento del repositorio.
     *
     * @param obj El campamento a eliminar del repositorio.
     */

    @Override
    public void delete(Camp obj) {
        
    }

}
