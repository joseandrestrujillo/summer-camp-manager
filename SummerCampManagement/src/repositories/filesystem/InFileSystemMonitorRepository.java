package repositories.filesystem;

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
import domain.exceptions.repository.NotFoundException;
import domain.interfaces.IRepository;

public class InFileSystemMonitorRepository implements IRepository<Monitor, Integer>{
    private String filePath;
    private Map<Integer, Monitor> mapOfMonitors;

    public InFileSystemMonitorRepository(String filePath) {
        this.filePath = filePath;
        this.mapOfMonitors = new HashMap<>();
        loadFromFile();
    }

    @Override
    public Monitor find(Integer identifier) {
        if (!mapOfMonitors.containsKey(identifier)) {
            throw new NotFoundException();
        }
        return mapOfMonitors.get(identifier);
    }

    @Override
    public void save(Monitor obj) {
        mapOfMonitors.put(obj.getId(), obj);
        saveToFile();
    }

    @Override
    public List<Monitor> getAll() {
        return new ArrayList<>(mapOfMonitors.values());
    }

    @Override
    public void delete(Monitor obj) {
        mapOfMonitors.remove(obj.getId());
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
                mapOfMonitors = monitorMapFromString(fileContent);
            }
        } catch (IOException e) {
            System.err.println("No se han podido cargar los datos desde el sistema de archivos.");
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            String fileContent = monitorMapToString(mapOfMonitors);
            writer.write(fileContent);
        } catch (IOException e) {
        	System.err.println("No se han podido guardar los datos en el sistema de archivos.");
        }
    }

    private String monitorMapToString(Map<Integer, Monitor> assistantMap) {
        StringBuilder sb = new StringBuilder();
        for (Monitor assistant : assistantMap.values()) {
            sb.append(assistant.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

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