/**
 * This class represents a repository for storing and managing activities in a file system.
 * It implements the IRepository interface for the Activity entity.
 */
public class InFileSystemActivityRepository implements IRepository<Activity, String> {

    /**
     * The file path where the activities are stored.
     */
    private String filePath;

    /**
     * A map that stores activities with their identifiers as keys.
     */
    private Map<String, Activity> mapOfAssistants;

    /**
     * Constructs an InFileSystemActivityRepository with the specified file path.
     *
     * @param filePath The path to the file where activities are stored.
     */
    public InFileSystemActivityRepository(String filePath) {
        this.filePath = filePath;
        this.mapOfAssistants = new HashMap<>();
        loadFromFile();
    }

    /**
     * Retrieves an activity by its identifier.
     *
     * @param identifier The identifier of the activity to find.
     * @return The found activity.
     * @throws NotFoundException if the activity is not found.
     */
    @Override
    public Activity find(String identifier) {
        if (!mapOfAssistants.containsKey(identifier)) {
            throw new NotFoundException();
        }
        return mapOfAssistants.get(identifier);
    }

    /**
     * Saves an activity to the repository.
     *
     * @param obj The activity to be saved.
     */
    @Override
    public void save(Activity obj) {
        mapOfAssistants.put(obj.getActivityName(), obj);
        saveToFile();
    }

    /**
     * Retrieves a list of all activities in the repository.
     *
     * @return A list of all activities.
     */
    @Override
    public List<Activity> getAll() {
        return new ArrayList<>(mapOfAssistants.values());
    }

    /**
     * Deletes an activity from the repository.
     *
     * @param obj The activity to be deleted.
     */
    @Override
    public void delete(Activity obj) {
        mapOfAssistants.remove(obj.getActivityName());
        saveToFile();
    }

    /**
     * Loads activities from the file system and populates the repository.
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
     * Saves activities to the file system.
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
     * Converts a map of activities to a string representation.
     *
     * @param assistantMap The map of activities to convert.
     * @return A string representation of the activities.
     */
    private String AssistantMapToString(Map<String, Activity> assistantMap) {
        StringBuilder sb = new StringBuilder();
        for (Activity assistant : assistantMap.values()) {
            sb.append(assistant.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Converts a string representation of activities to a map.
     *
     * @param fileContent The string representation of activities.
     * @return A map of activities.
     */
    private Map<String, Activity> AssistantMapFromString(String fileContent) {
        Map<String, Activity> assistantMap = new HashMap<>();
        String[] lines = fileContent.split(System.lineSeparator());
        for (String line : lines) {
            Activity assistant = Activity.fromString(line);
            if (assistant != null) {
                assistantMap.put(assistant.getActivityName(), assistant);
            }
        }
        return assistantMap;
    }
}
