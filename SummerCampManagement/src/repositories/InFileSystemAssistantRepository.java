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

import domain.entities.Assistant;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

public class InFileSystemAssistantRepository implements IRepository<Assistant, Integer>{
    private String filePath;
    private Map<Integer, Assistant> mapOfAssistants;

    public InFileSystemAssistantRepository(String filePath) {
        this.filePath = filePath;
        this.mapOfAssistants = new HashMap<>();
        loadFromFile();
    }

    @Override
    public Assistant find(Integer identifier) {
        if (!mapOfAssistants.containsKey(identifier)) {
            throw new NotFoundException();
        }
        return mapOfAssistants.get(identifier);
    }

    @Override
    public void save(Assistant obj) {
        mapOfAssistants.put(obj.getId(), obj);
        saveToFile();
    }

    @Override
    public List<Assistant> getAll() {
        return new ArrayList<>(mapOfAssistants.values());
    }

    @Override
    public void delete(Assistant obj) {
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

    private String AssistantMapToString(Map<Integer, Assistant> assistantMap) {
        StringBuilder sb = new StringBuilder();
        for (Assistant assistant : assistantMap.values()) {
            sb.append(assistant.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private Map<Integer, Assistant> AssistantMapFromString(String fileContent) {
        Map<Integer, Assistant> assistantMap = new HashMap<>();
        String[] lines = fileContent.split(System.lineSeparator());
        for (String line : lines) {
            Assistant assistant = Assistant.fromString(line);
            if (assistant != null) {
                assistantMap.put(assistant.getId(), assistant);
            }
        }
        return assistantMap;
    }
}