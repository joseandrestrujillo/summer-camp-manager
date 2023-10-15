package repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entities.Inscription;
import domain.exceptions.NotFoundException;
import domain.interfaces.IRepository;

public class InFileSystemInscriptionRepository implements IRepository<Inscription, String> {
    private Map<String, Inscription> mapOfInscription;
    private String filePath;

    public InFileSystemInscriptionRepository(String filePath) {
        this.filePath = filePath;
        this.mapOfInscription = new HashMap<String, Inscription>();
        loadFromFile();
    }

    @Override
    public Inscription find(String identifier) {
        if (!mapOfInscription.containsKey(identifier)) {
            throw new NotFoundException();
        }
        return mapOfInscription.get(identifier);
    }

    @Override
    public void save(Inscription obj) {
        mapOfInscription.put(obj.getInscriptionIdentifier(), obj);
        saveToFile();
    }

    @Override
    public List<Inscription> getAll() {
        return new ArrayList<>(mapOfInscription.values());
    }

    @Override
    public void delete(Inscription obj) {
        mapOfInscription.remove(obj.getInscriptionIdentifier());
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
                mapOfInscription = InscriptionMapFromString(fileContent);
            }
        } catch (IOException e) {
            System.err.println("No se han podido cargar los datos desde el sistema de archivos.");
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            String fileContent = InscriptionMapToString(mapOfInscription);
            writer.write(fileContent);
        } catch (IOException e) {
        	System.err.println("No se han podido guardar los datos en el sistema de archivos.");
        }
    }

    private String InscriptionMapToString(Map<String, Inscription> InscriptionMap) {
        StringBuilder sb = new StringBuilder();
        for (Inscription inscription : InscriptionMap.values()) {
            sb.append(inscription.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private Map<String, Inscription> InscriptionMapFromString(String fileContent) {
        Map<String, Inscription> InscriptionMap = new HashMap<>();
        String[] lines = fileContent.split(System.lineSeparator());
        for (String line : lines) {
            Inscription inscription = Inscription.fromString(line);
            if (inscription != null) {
                InscriptionMap.put(inscription.getInscriptionIdentifier(), inscription);
            }
        }
        return InscriptionMap;
    }
}
