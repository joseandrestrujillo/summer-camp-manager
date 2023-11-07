package data.memory;

public class MapsManager {
	private static MapsManager instance;
	
	public static MapsManager getInstance() {
		if (instance == null) {
			instance = new MapsManager();
		}
		return instance;
	}
	
	private MapsManager() {}
}
