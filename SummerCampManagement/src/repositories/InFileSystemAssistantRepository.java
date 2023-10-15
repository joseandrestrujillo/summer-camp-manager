/**
 * This class represents a repository that stores Assistant objects in the file system.
 * It implements the IRepository interface for Assistant objects with Integer identifiers.
 */
public class InFileSystemAssistantRepository implements IRepository<Assistant, Integer> {

    /**
     * The file path where the data is stored.
     */
    private String filePath;

    /**
     * A map that holds Assistant objects with their Integer identifiers.
     */
    private Map<Integer, Assistant> mapOfAssistants;

    /**
     * Constructs an InFileSystemAssistantRepository with the specified file path.
     *
     * @param filePath The file path where the data is stored.
     */
    public InFileSystemAssistantRepository(String filePath) {
        this.filePath = filePath;
        this.mapOfAssistants = new HashMap<>();
        loadFromFile();
    }

    /**
     * Retrieves an Assistant object by its identifier.
     *
     * @param identifier The identifier of the Assistant to find.
     * @return The Assistant object if found.
     * @throws NotFoundException If the Assistant with the given identifier is not found.
     */
    @Override
    public Assistant find(Integer identifier) {
        if (!mapOfAssistants.containsKey(identifier)) {
            throw new NotFoundException();
        }
        return mapOfAssistants.get(identifier);
    }

    /**
     * Saves an Assistant object to the repository.
     *
     * @param obj The Assistant object to be saved.
     */
    @Override
    public void save(Assistant obj) {
        mapOfAssistants.put(obj.getId(), obj);
        saveToFile();
    }

    /**
     * Retrieves all Assistant objects stored in the repository.
     *
     * @return A list of all Assistant objects.
     */
    @Override
    public List<Assistant> getAll() {
        return new ArrayList<>(mapOfAssistants.values());
    }

    /**
     * Deletes an Assistant object from the repository.
     *
     * @param obj The Assistant object to be deleted.
     */
    @Override
    public void delete(Assistant obj) {
        mapOfAssistants.remove(obj.getId());
        saveToFile();
    }

    /**
     * Loads Assistant data from the file specified by filePath.
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
     * Saves Assistant data to the file specified by filePath.
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
     * Converts a map of Assistant objects to a string representation.
     *
     * @param assistantMap The map of Assistant objects to be converted.
     * @return A string representing the Assistant objects.
     */
    private String AssistantMapToString(Map<Integer, Assistant> assistantMap) {
        StringBuilder sb = new StringBuilder();
        for (Assistant assistant : assistantMap.values()) {
            sb.append(assistant.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Converts a string representation to a map of Assistant objects.
     *
     * @param fileContent The string representation of Assistant data.
     * @return A map of Assistant objects.
     */
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
