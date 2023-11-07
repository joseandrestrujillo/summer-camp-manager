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

import business.dtos.ActivityDTO;
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import utilities.StringUtils;

/**
 * La clase InFileSystemActivityRepository es una implementación en sistema de ficheros de un repositorio de actividades.
 */
public class InFileSystemActivityRepository implements IDAO<ActivityDTO, String>{
    private String filePath;
    private Map<String, ActivityDTO> mapOfAssistants;

    
    /**
     * Constructor de la clase InFileSystemActivityRepository.
     * Inicializa un nuevo mapa para almacenar actividades en memoria y carga las actividades almacenadas en el fichero
     * 
     * @param filePath path al fichero de actividades
     */
    public InFileSystemActivityRepository(String filePath) {
        this.filePath = filePath;
        this.mapOfAssistants = new HashMap<>();
        loadFromFile();
    }
    
    /**
     * Busca una actividad por su nombre.
     *
     * @param identifier El nombre de la actividad a buscar.
     * @return La actividad encontrada.
     * @throws NotFoundException Si la actividad no se encuentra en el repositorio.
     */
    @Override
    public ActivityDTO find(String identifier) {
        if (!mapOfAssistants.containsKey(identifier)) {
            throw new NotFoundException();
        }
        return mapOfAssistants.get(identifier);
    }

    /**
     * Guarda una actividad en el repositorio.
     *
     * @param obj La actividad a guardar en el repositorio.
     */
    @Override
    public void save(ActivityDTO obj) {
        mapOfAssistants.put(obj.getActivityName(), obj);
        saveToFile();
    }
    
    /**
     * Obtiene una lista de todas las actividades almacenadas en el repositorio.
     *
     * @return Una lista de actividades.
     */
    @Override
    public List<ActivityDTO> getAll(Optional<ICriteria> criteria) {
        return new ArrayList<>(mapOfAssistants.values());
    }

    /**
     * Elimina una actividad del repositorio.
     *
     * @param obj La actividad a eliminar del repositorio.
     */
    @Override
    public void delete(ActivityDTO obj) {
        mapOfAssistants.remove(obj.getActivityName());
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
                mapOfAssistants = AssistantMapFromString(fileContent);
            }
        } catch (IOException e) {
            System.err.println("No se han podido cargar los datos desde el sistema de archivos.");
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            String fileContent = AssistantMapToString(mapOfAssistants);
            writer.write(fileContent);
        } catch (IOException e) {
        	System.err.println("No se han podido guardar los datos en el sistema de archivos.");
        }
    }

    private String AssistantMapToString(Map<String, ActivityDTO> assistantMap) {
        StringBuilder sb = new StringBuilder();
        for (ActivityDTO assistant : assistantMap.values()) {
            sb.append(assistant.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private Map<String, ActivityDTO> AssistantMapFromString(String fileContent) {
        Map<String, ActivityDTO> assistantMap = new HashMap<>();
        String[] lines = fileContent.split(System.lineSeparator());
        for (String line : lines) {
            ActivityDTO assistant = StringUtils.activityFromString(line);
            if (assistant != null) {
                assistantMap.put(assistant.getActivityName(), assistant);
            }
        }
        return assistantMap;
    }
}