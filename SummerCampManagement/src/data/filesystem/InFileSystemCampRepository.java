package data.filesystem;
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

import business.dtos.CampDTO;
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import utilities.StringUtils;
/**
 * La clase InFileSystemCampRepository es una implementación en sistema de ficheros de un repositorio de campamentos.
 
 */
public class InFileSystemCampRepository implements IDAO<CampDTO, Integer> {
    private Map<Integer, CampDTO> mapOfCamps;
    private String filePath;
     /**
     * Constructor de la clase InFileSystemCampRepository.
     * @param filePath La ruta del archivo
     */

    public InFileSystemCampRepository(String filePath) {
        this.filePath = filePath;
        this.mapOfCamps = new HashMap<Integer, CampDTO>();
        loadFromFile();
    }
     /**
     * Busca un campamento por su identificador.
     *
     * @param identifier El identificador del campamento a buscar.
     * @return El campamento encontrado.
     * @throws NotFoundException Si el campamento no se encuentra en el repositorio.
     */

    @Override
    public CampDTO find(Integer identifier) {
        if (!mapOfCamps.containsKey(identifier)) {
            throw new NotFoundException();
        }
        return mapOfCamps.get(identifier);
    }

    /**
     * Guarda un campamento en el repositorio.
     *
     * @param obj El campamento a guardar en el repositorio.
     */

    @Override
    public void save(CampDTO obj) {
        mapOfCamps.put(obj.getCampID(), obj);
        saveToFile();
    }
    /**
     * Obtiene una lista de todos los campamentos almacenados en el repositorio.
     *
     * @return Una lista de campamentos.
     */

    @Override
    public List<CampDTO> getAll(Optional<ICriteria> criteria) {
        return new ArrayList<>(mapOfCamps.values());
    }
        /**
     * Elimina un campamento del repositorio.
     *
     * @param obj El campamento a eliminar del repositorio.
     */

    @Override
    public void delete(CampDTO obj) {
        mapOfCamps.remove(obj.getCampID());
        saveToFile();
    }

    private void loadFromFile() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return;
            }

            List<String> lines = Files.readAllLines(Paths.get(filePath));
            String fileContent = String.join(System.lineSeparator(), lines);
            if (!fileContent.isEmpty()) {
                mapOfCamps = InscriptionMapFromString(fileContent);
            }
        } catch (IOException e) {
            System.err.println("No se han podido cargar los datos desde el sistema de archivos.");
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            String fileContent = InscriptionMapToString(mapOfCamps);
            writer.write(fileContent);
        } catch (IOException e) {
        	System.err.println("No se han podido guardar los datos en el sistema de archivos.");
        }
    }

    private String InscriptionMapToString(Map<Integer, CampDTO> InscriptionMap) {
        StringBuilder sb = new StringBuilder();
        for (CampDTO inscription : InscriptionMap.values()) {
            sb.append(inscription.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private Map<Integer, CampDTO> InscriptionMapFromString(String fileContent) {
        Map<Integer, CampDTO> CampMap = new HashMap<>();
        String[] lines = fileContent.split(System.lineSeparator());
        for (String line : lines) {
            CampDTO camp = StringUtils.campFromString(line);
            if (camp != null) {
                CampMap.put(camp.getCampID(), camp);
            }
        }
        return CampMap;
    }
}
