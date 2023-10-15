import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Monitor;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

/**
 * Esta clase implementa un repositorio de monitores en el sistema de archivos.
 * Permite realizar operaciones de carga, guardado, búsqueda y eliminación de monitores
 * almacenados en un archivo.
 */
public class InFileSystemMonitorRepository implements IRepository<Monitor, Integer> {
    private String filePath;
    private Map<Integer, Monitor> mapOfMonitors;

    /**
     * Constructor de la clase InFileSystemMonitorRepository.
     *
     * @param filePath La ruta del archivo en el que se almacenan los datos de los monitores.
     */
    public InFileSystemMonitorRepository(String filePath) {
        this.filePath = filePath;
        this.mapOfMonitors = new HashMap<>();
        loadFromFile();
    }

    /**
     * Busca un monitor por su identificador.
     *
     * @param identifier El identificador del monitor que se desea buscar.
     * @return El monitor con el identificador especificado.
     * @throws NotFoundException Si no se encuentra un monitor con el identificador especificado.
     */
    @Override
    public Monitor find(Integer identifier) {
        if (!mapOfMonitors.containsKey(identifier)) {
            throw new NotFoundException();
        }
        return mapOfMonitors.get(identifier);
    }

    /**
     * Guarda un monitor en el repositorio.
     *
     * @param obj El monitor que se desea guardar.
     */
    @Override
    public void save(Monitor obj) {
        mapOfMonitors.put(obj.getId(), obj);
        saveToFile();
    }

    /**
     * Obtiene una lista de todos los monitores en el repositorio.
     *
     * @return Una lista de monitores almacenados en el repositorio.
     */
    @Override
    public List<Monitor> getAll() {
        return new ArrayList<>(mapOfMonitors.values());
    }

    /**
     * Elimina un monitor del repositorio.
     *
     * @param obj El monitor que se desea eliminar.
     */
    @Override
    public void delete(Monitor obj) {
        mapOfMonitors.remove(obj.getId());
        saveToFile();
    }

    /**
     * Carga los datos de monitores desde un archivo en el sistema de archivos.
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
                mapOfMonitors = monitorMapFromString(fileContent);
            }
        } catch (IOException e) {
            System.err.println("No se han podido cargar los datos desde el sistema de archivos.");
        }
    }

    /**
     * Guarda los datos de monitores en un archivo en el sistema de archivos.
     */
    private void saveToFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            String fileContent = monitorMapToString(mapOfMonitors);
            writer.write(fileContent);
        } catch (IOException e) {
            System.err.println("No se han podido guardar los datos en el sistema de archivos.");
        }
    }

    /**
     * Convierte un mapa de monitores en una cadena de texto.
     *
     * @param assistantMap El mapa de monitores que se desea convertir.
     * @return Una cadena de texto que representa los monitores en el mapa.
     */
    private String monitorMapToString(Map<Integer, Monitor> assistantMap) {
        StringBuilder sb = new StringBuilder();
        for (Monitor assistant : assistantMap.values()) {
            sb.append(assistant.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Convierte una cadena de texto en un mapa de monitores.
     *
     * @param fileContent La cadena de texto que contiene la representación de los monitores.
     * @return Un mapa de monitores construido a partir de la cadena de texto.
     */
    private Map<Integer, Monitor> monitorMapFromString(String fileContent) {
        Map<Integer, Monitor> assistantMap = new HashMap<>();
        String[] lines = fileContent.split(System.lineSeparator());
        for (String line : lines) {
            Monitor assistant = Monitor.fromString(line);
            if (assistant != null) {
                assistantMap.put(assistant.getId(), assistant);
            }
        }
        return assistantMap;
    }
}