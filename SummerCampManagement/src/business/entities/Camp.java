package business.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import business.values.EducativeLevel;
import business.values.TimeSlot;
import utilities.Utils;

/**
 * La clase Camp representa un campamento educativo con su información básica y actividades asociadas.
 */
public class Camp {
    private int campID;
    private Date start;
    private Date end;
    private EducativeLevel educativeLevel;
    private int capacity;
    private List<Activity> activities;
    private Monitor principalMonitor;
    private Monitor specialMonitor;
    /**
     * Constructor predeterminado para crear un objeto Camp vacío.
     */
    public Camp(){
        this.campID = -1;
        this.start = null;
        this.end = null;
        this.educativeLevel = null;
        this.capacity = -1;
    }

    /**
     * Constructor para crear un nuevo objeto Camp con información proporcionada.
     *
     * @param campID        El identificador del campamento.
     * @param start         La fecha de inicio del campamento.
     * @param end           La fecha de finalización del campamento.
     * @param educativeLevel El nivel educativo del campamento.
     * @param capacity      La capacidad máxima de participantes del campamento.
     */
    public Camp(int campID, Date start, Date end, EducativeLevel educativeLevel, int capacity){
        this.campID = campID;
        this.start = start;
        this.end = end;
        this.educativeLevel = educativeLevel;
        this.capacity = capacity;
        this.activities = new ArrayList<Activity>();
    }

    /**
     * Verifica si una actividad está registrada en el campamento.
     *
     * @param activity La actividad que se quiere verificar.
     * @return true si la actividad está registrada en el campamento, false en caso contrario.
     */
	public boolean activityIsRegistered(Activity activity) {
		return this.activities.contains(activity);
	}

	/**
     * Obtiene el monitor principal del campamento.
     *
     * @return El monitor principal del campamento.
     */
	public Monitor getPrincipalMonitor() {
		return this.principalMonitor;
	}

	/**
     * Obtiene el monitor especial del campamento.
     *
     * @return El monitor especial del campamento.
     */
	public Monitor getSpecialMonitor() {
		return this.specialMonitor;
	}

    /**
     * Obtiene el identificador del campamento.
     *
     * @return El identificador del campamento.
     */
    public int getCampID() {
        return campID;
    }

    /**
     * Establece el identificador del campamento.
     *
     * @param campID El nuevo identificador del campamento.
     */
    public void setCampID(int campID) {
        this.campID = campID;
    }

    /**
     * Obtiene la fecha de inicio del campamento.
     *
     * @return La fecha de inicio del campamento.
     */
    public Date getStart() {
        return start;
    }

    /**
     * Establece la fecha de inicio del campamento.
     *
     * @param start La nueva fecha de inicio del campamento.
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * Obtiene la fecha de finalización del campamento.
     *
     * @return La fecha de finalización del campamento.
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Establece la fecha de finalización del campamento.
     *
     * @param end La nueva fecha de finalización del campamento.
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * Obtiene el nivel educativo del campamento.
     *
     * @return El nivel educativo del campamento.
     */
    public EducativeLevel getEducativeLevel() {
        return educativeLevel;
    }

    /**
     * Establece el nivel educativo del campamento.
     *
     * @param educativeLevel El nuevo nivel educativo del campamento.
     */
    public void setEducativeLevel(EducativeLevel educativeLevel) {
        this.educativeLevel = educativeLevel;
    }

    /**
     * Obtiene la capacidad máxima de participantes del campamento.
     *
     * @return La capacidad máxima de participantes del campamento.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Establece la capacidad máxima de participantes del campamento.
     *
     * @param capacity La nueva capacidad máxima de participantes del campamento.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Obtiene las actividades registradas en el campamento.
     *
     * @return Las actividades registradas en el campamento.
     */
    public List<Activity> getActivities() {
        return activities;
    }

    /**
     * Establece las actividades registradas en el campamento.
     *
     * @param activities Las nuevas actividades registradas en el campamento.
     */
    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
    
    /**
     * Devuelve una representación en formato de cadena del objeto Camp.
     *
     * @return Una cadena que representa el objeto Camp, incluyendo todos sus atributos.
     */
    public String toString() {
        return 
        		"{campID: " + this.campID 
        		+ ", start: " + Utils.getStringDate(start)
        		+ ", end: " + Utils.getStringDate(end)
        		+ ", educativeLevel: " + this.educativeLevel 
        		+ ", capacity: " + this.capacity 
        		+ ", principalMonitor: " + (this.principalMonitor == null ? "null" : this.principalMonitor.toString())
				+ ", specialMonitor: " + (this.principalMonitor == null ? "null" : this.specialMonitor.toString())
        		+ ", activities: " + this.activities.toString() 
        		+  "}";
    }

    /**
     * Establece el monitor principal del campamento.
     *
     * @param monitor El nuevo monitor principal del campamento.
     */
	public void setPrincipalMonitor(Monitor monitor) {
		this.principalMonitor = monitor;
	}

    /**
     * Establece el monitor especial del campamento.
     *
     * @param monitor El nuevo monitor especial del campamento.
     */
	public void setSpecialMonitor(Monitor monitor) {
		this.specialMonitor = monitor;
	}

	/**
     * Devuelve objeto Camp.
     *
     * @param inputString Una cadena que representa al campamento en formato JSON.
     * @return Objeto de la clase
     */
	public static Camp fromString(String inputString) {

		  Pattern pattern = Pattern.compile("campID: (\\d+), start: (.+), end: (.+), educativeLevel: (.+), capacity: (\\d+), principalMonitor: (.+), specialMonitor: (.+), activities: \\[(.+)\\]");
		  
		  Matcher matcher = pattern.matcher(inputString);

		  if(!matcher.find()) {
		    throw new IllegalStateException("No match found");
		  }

		  int campID = Integer.parseInt(matcher.group(1));

		  Date start = Utils.parseDate(matcher.group(2));
		  Date end = Utils.parseDate(matcher.group(3));

		  EducativeLevel educativeLevel = EducativeLevel.valueOf(matcher.group(4));

		  int capacity = Integer.parseInt(matcher.group(5));

		  String principalMonitorString = matcher.group(6);
		  Monitor principalMonitor = principalMonitorString.equals("null") ? null : Monitor.fromString(principalMonitorString);

		  String specialMonitorString = matcher.group(7);
		  Monitor specialMonitor = specialMonitorString.equals("null") ? null : Monitor.fromString(specialMonitorString);

		  List<Activity> activities = new ArrayList<>();
		  
		  String activitiesString = matcher.group(8);
		  if (activitiesString != null && !activitiesString.isEmpty()) {
			  Pattern activityPattern = Pattern.compile("activityName: '(.+)',\\s+educativeLevel:\\s+(.+),\\s+timeSlot:\\s+(.+),\\s+maxAssistants:\\s+(\\d+),\\s+neededMonitors:\\s+(\\d+),\\s+assistants:\\s*(\\[[^\\]]*\\])?\\s*,\\s*monitors:\\s*(\\[[^\\]]*\\])?\\s*}");
			  Matcher activityMatcher = activityPattern.matcher(activitiesString);
			  while (activityMatcher.find()) {
				  String activityData = activityMatcher.group();
				  Activity activity = Activity.fromString(activityData);
				  activities.add(activity);
			  }
		  }

		  Camp camp = new Camp(campID, start, end, educativeLevel, capacity);
		  camp.setPrincipalMonitor(principalMonitor);
		  camp.setSpecialMonitor(specialMonitor);
		  camp.setActivities(activities);

		  return camp;
		}
	
}
