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

import business.dtos.AssistantDTO;
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import utilities.StringUtils;


/**
 * La clase InFileSystemAssistantRepository es una implementación en sistema de ficheros de un repositorio de asistentes.
 
 */
public class InFileSystemAssistantRepository implements IDAO<AssistantDTO, Integer>{
    private String filePath;
    private Map<Integer, AssistantDTO> mapOfAssistants;

    
    /**
     * Constructor de la clase InFileSystemAssistantRepository.
     * Inicializa un nuevo mapa para almacenar asistentes en memoria y carga los asistentes almacenados en el fichero
     * 
     * @param filePath path al fichero de asistentes
     */
    public InFileSystemAssistantRepository(String filePath) {
        this.filePath = filePath;
        this.mapOfAssistants = new HashMap<>();
        loadFromFile();
    }
    /**
     * Busca a un asistente por su identificador.
     *
     * @param identifier El identificador del asistente a buscar.
     * @return El asistente encontrado.
     * @throws NotFoundException Si el asistente no se encuentra en el repositorio.
     */
    @Override
    public AssistantDTO find(Integer identifier) {
        if (!mapOfAssistants.containsKey(identifier)) {
            throw new NotFoundException();
        }
        return mapOfAssistants.get(identifier);
    }
    /**
     * Guarda a un asistente en el repositorio.
     *
     * @param obj El asistente a guardar en el repositorio.
     */
    @Override
    public void save(AssistantDTO obj) {
        mapOfAssistants.put(obj.getId(), obj);
        saveToFile();
    }
    /**
     * Obtiene una lista de todos los asistentes almacenados en el repositorio.
     *
     * @return Una lista de asistentes.
     */
    @Override
    public List<AssistantDTO> getAll(Optional<ICriteria> criteria) {
        return new ArrayList<>(mapOfAssistants.values());
    }
    /**
     * Elimina a un asistente del repositorio.
     *
     * @param obj El asistente a eliminar del repositorio.
     */
    @Override
    public void delete(AssistantDTO obj) {
        mapOfAssistants.remove(obj.getId());
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

    private String AssistantMapToString(Map<Integer, AssistantDTO> assistantMap) {
        StringBuilder sb = new StringBuilder();
        for (AssistantDTO assistant : assistantMap.values()) {
            sb.append(assistant.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private Map<Integer, AssistantDTO> AssistantMapFromString(String fileContent) {
        Map<Integer, AssistantDTO> assistantMap = new HashMap<>();
        String[] lines = fileContent.split(System.lineSeparator());
        for (String line : lines) {
            AssistantDTO assistant = StringUtils.assistantFromString(line);
            if (assistant != null) {
                assistantMap.put(assistant.getId(), assistant);
            }
        }
        return assistantMap;
    }
}