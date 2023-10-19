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

import business.entities.Inscription;
import business.exceptions.repository.NotFoundException;
import business.interfaces.IRepository;


/**
 * La clase InFileSystemInscriptionRepository es una implementación en memoria de un repositorio de inscripciones.

 */

public class InFileSystemInscriptionRepository implements IRepository<Inscription, String> {
    private Map<String, Inscription> mapOfInscription;
    private String filePath;


    /**
     * Constructor de la clase InFileSystemInscriptionRepository.
     * Inicializa un nuevo mapa para almacenar inscripciones en memoria.
     */

    public InFileSystemInscriptionRepository(String filePath) {
        this.filePath = filePath;
        this.mapOfInscription = new HashMap<String, Inscription>();
        loadFromFile();
    }


    /**
     * Busca una inscripción por su nombre.
     *
     * @param identifier El nombre de la inscripción a buscar.
     * @return La inscripción encontrada.
     * @throws NotFoundException Si la inscripción no se encuentra en el repositorio.
     */

    @Override
    public Inscription find(String identifier) {
        if (!mapOfInscription.containsKey(identifier)) {
            throw new NotFoundException();
        }
        return mapOfInscription.get(identifier);
    }


    /**
     * Guarda una inscripción en el repositorio.
     *
     * @param obj La inscripción a guardar en el repositorio.
     */

    @Override
    public void save(Inscription obj) {
        mapOfInscription.put(obj.getInscriptionIdentifier(), obj);
        saveToFile();
    }


    /**
     * Obtiene una lista de todas las inscripciones almacenadas en el repositorio.
     *
     * @return Una lista de inscripciones.
     */

    @Override
    public List<Inscription> getAll() {
        return new ArrayList<>(mapOfInscription.values());
    }

    /**
     * Elimina una inscripción del repositorio.
     *
     * @param obj La inscripción a eliminar del repositorio.
     */ 

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
