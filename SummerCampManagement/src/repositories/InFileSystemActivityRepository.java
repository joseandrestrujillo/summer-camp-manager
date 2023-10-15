package repositories;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Activity;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

/**
 * InFileSystemActivityRepository es una implementación de IRepository
 * que almacena y gestiona actividades en un sistema de archivos.
 */
public class InFileSystemActivityRepository implements IRepository<Activity, String> {
    private String filePath;
    private Map<String, Activity> mapOfAssistants;

    /**
     * Constructor de la clase InFileSystemActivityRepository.
     *
     * @param filePath Ruta al archivo en el que se almacenan las actividades.
     */
    public InFileSystemActivityRepository(String filePath) {
        this.filePath = filePath;
        this.mapOfAssistants = new HashMap<>();
        loadFromFile();
    }

    /**
     * Busca una actividad por su identificador.
     *
     * @param identifier Identificador de la actividad.
     * @return La actividad correspondiente al identificador.
     * @throws NotFoundException Si la actividad no se encuentra.
     */
    @Override
    public Activity find(String identifier) {
        if (!mapOfAssistants.containsKey(identifier)) {
            throw new NotFoundException();
        }
        return mapOfAssistants.get(identifier);
    }

    /**
     * Guarda una actividad en el repositorio.
     *
     * @param obj La actividad que se va a guardar.
     */
    @Override
    public void save(Activity obj) {
        mapOfAssistants.put(obj.getActivityName(), obj);
        saveToFile();
    }

    /**
     * Obtiene todas las actividades almacenadas en el repositorio.
     *
     * @return Una lista de todas las actividades.
     */
    @Override
    public List<Activity> getAll() {
        return new ArrayList<>(mapOfAssistants.values());
    }

    /**
     * Elimina una actividad del repositorio.
     *
     * @param obj La actividad que se va a eliminar.
     */
    @Override
    public void delete(Activity obj) {
        mapOfAssistants.remove(obj.getActivityName());
        saveToFile();
    }

    /**
     * Carga las actividades almacenadas en el archivo en el sistema de archivos.
     */
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

    /**
     * Guarda las actividades en el archivo en el sistema de archivos.
     */
    private void saveToFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            String fileContent = AssistantMapToString(mapOfAssistants);
            writer.write(fileContent);
        } catch (IOException e) {
            System.err.println("No se han podido guardar los datos en el sistema de archivos.");
        }
    }

    /**
     * Convierte un mapa de actividades a una cadena de texto.
     *
     * @param assistantMap Mapa de actividades.
     * @return La representación en cadena de texto del mapa de actividades.
     */
    private String AssistantMapToString(Map<String, Activity> assistantMap) {
        StringBuilder sb = new StringBuilder();
        for (Activity assistant : assistantMap.values()) {
            sb.append(assistant.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
    /**
     * Convierte una cadena de texto en un mapa de actividades.
     *
     * @param fileContent Cadena de texto que representa las actividades.
     * @return Un mapa de actividades recuperado de la
