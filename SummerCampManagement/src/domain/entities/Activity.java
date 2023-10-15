

package domain.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.exceptions.MaxMonitorsAddedException;
import domain.values.EducativeLevel;
import domain.values.TimeSlot;
import utilities.Utils;
/**
 * La clase Activity representa una actividad educativa que se lleva a cabo en un horario específico
 * y se asocia con un nivel educativo, un nombre y un límite de asistentes y monitores.
 */
public class Activity {
    private String activityName;
    private EducativeLevel educativeLevel;
    private TimeSlot timeSlot;
    private int maxAssistants;
    private int neededMonitors;
    private List<Assistant> assistantList;
    private List<Monitor> monitorList;

    /**
     * Constructor de la clase Activity.
     * 
     * @param activityName   El nombre de la actividad.
     * @param educativeLevel El nivel educativo al que se dirige la actividad.
     * @param timeSlot       El horario en el que se lleva a cabo la actividad.
     * @param maxAssistants  El número máximo de asistentes permitidos.
     * @param neededMonitors El número de monitores necesarios para la actividad.
     */
    public Activity(String activityName, EducativeLevel educativeLevel, TimeSlot timeSlot, int maxAssistants,
            int neededMonitors) {
        this.activityName = activityName;
        this.educativeLevel = educativeLevel;
        this.timeSlot = timeSlot;
        this.maxAssistants = maxAssistants;
        this.neededMonitors = neededMonitors;
        this.monitorList = new ArrayList<Monitor>(this.neededMonitors);
        this.assistantList = new ArrayList<Assistant>(this.maxAssistants);
    }

    /**
     * Constructor vacío de la clase Activity.
     */
    public Activity() {
        this.activityName = null;
        this.educativeLevel = null;
        this.timeSlot = null;
        this.maxAssistants = -1;
        this.neededMonitors = -1;
        this.monitorList = null;
        this.assistantList = null;
    }

    /**
     * Obtiene la lista de asistentes registrados en la actividad.
     * 
     * @return La lista de asistentes.
     */
    public List<Assistant> getAssistants() {
        return assistantList;
    }

    /**
     * Establece la lista de asistentes de la actividad.
     * 
     * @param assistants La lista de asistentes a establecer.
     */
    public void setAssistants(List<Assistant> assistants) {
        this.assistantList = assistants;
    }

    /**
     * Obtiene el nombre de la actividad.
     * 
     * @return El nombre de la actividad.
     */
    public String getActivityName() {
        return activityName;
    }

    /**
     * Establece el nombre de la actividad.
     * 
     * @param activityName El nombre de la actividad a establecer.
     */
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    /**
     * Obtiene el nivel educativo al que se dirige la actividad.
     * 
     * @return El nivel educativo de la actividad.
     */
    public EducativeLevel getEducativeLevel() {
        return educativeLevel;
    }

    /**
     * Establece el nivel educativo de la actividad.
     * 
     * @param educativeLevel El nivel educativo a establecer.
     */
    public void setEducativeLevel(EducativeLevel educativeLevel) {
        this.educativeLevel = educativeLevel;
    }

    /**
     * Obtiene el horario en el que se lleva a cabo la actividad.
     * 
     * @return El horario de la actividad.
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Establece el horario de la actividad.
     * 
     * @param timeSlot El horario a establecer.
     */
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    /**
     * Obtiene el número máximo de asistentes permitidos en la actividad.
     * 
     * @return El número máximo de asistentes.
     */
    public int getMaxAssistants() {
        return maxAssistants;
    }

    /**
     * Establece el número máximo de asistentes permitidos en la actividad.
     * 
     * @param maxAssistants El número máximo de asistentes a establecer.
     */
    public void setMaxAssistants(int maxAssistants) {
        this.maxAssistants = maxAssistants;
    }

    /**
     * Obtiene el número de monitores necesarios para la actividad.
     * 
     * @return El número de monitores necesarios.
     */
    public int getNeededMonitors() {
        return neededMonitors;
    }

    /**
     * Establece el número de monitores necesarios para la actividad.
     * 
     * @param neededMonitors El número de monitores necesarios a establecer.
     */
    public void setNeededMonitors(int neededMonitors) {
        this.neededMonitors = neededMonitors;
    }

    /**
     * Obtiene la lista de monitores registrados en la actividad.
     * 
     * @return La lista de monitores.
     */
    public List<Monitor> getMonitorList() {
        return monitorList;
    }

    /**
     * Establece la lista de monitores de la actividad.
     * 
     * @param monitorList La lista de monitores a establecer.
     */
    public void setMonitorList(List<Monitor> monitorList) {
        this.monitorList = monitorList;
    }

    /**
     * Representa la actividad como una cadena de texto en formato JSON.
     * 
     * @return Una cadena de texto que representa la actividad en formato JSON.
     */
    public String toString() {
        return "{activityName: '" + this.activityName + "', "
        		+ "educativeLevel: " + this.educativeLevel + ", "
				+ "timeSlot: " + this.timeSlot + ", "
				+ "maxAssistants: " + this.maxAssistants + ", "
				+ "neededMonitors: " + this.neededMonitors + ", "
				+ "assistants: " + this.assistantList.toString() + ", "
				+ "monitors: " + this.monitorList.toString() + "}";
    }

    /**
     * Registra un monitor en la actividad.
     * 
     * @param monitor El monitor a registrar.
     * @throws MaxMonitorsAddedException Si se alcanza el límite de monitores registrados.
     */
    public void registerMonitor(Monitor monitor) {
        if (monitorList.size() == this.neededMonitors) {
            throw new MaxMonitorsAddedException();
        }
        monitorList.add(monitor);
    }

    /**
     * Comprueba si un monitor está registrado en la actividad.
     * 
     * @param monitor El monitor a comprobar.
     * @return true si el monitor está registrado, false en caso contrario.
     */
    public boolean monitorIsRegistered(Monitor monitor){
		return this.monitorList.contains(monitor);	
	}
    
    public static Activity fromString(String inputString) {

        Pattern pattern = Pattern.compile("activityName: '(.+)',\\s+educativeLevel:\\s+(.+),\\s+timeSlot:\\s+(.+),\\s+maxAssistants:\\s+(\\d+),\\s+neededMonitors:\\s+(\\d+),\\s+assistants:\\s+\\[(.+)\\],\\s+monitors:\\s+\\[(.+)\\]");

        Matcher matcher = pattern.matcher(inputString);

        if(!matcher.find()) {
          throw new IllegalStateException("No match found");
        }

        String activityName = matcher.group(1);
        if(activityName == null) {
          throw new IllegalArgumentException("Activity name is null"); 
        }

        String educativeLevelStr = matcher.group(2);
        if(educativeLevelStr == null) {
          throw new IllegalArgumentException("Educative level is null");
        }
        EducativeLevel educativeLevel = EducativeLevel.valueOf(educativeLevelStr);

        String timeSlotStr = matcher.group(3);
        if(timeSlotStr == null) {
          throw new IllegalArgumentException("Time slot is null");
        }
        TimeSlot timeSlot = TimeSlot.valueOf(timeSlotStr);

        String maxAssistantsStr = matcher.group(4);
        if(maxAssistantsStr == null) {
          throw new IllegalArgumentException("Max assistants is null");
        }
        int maxAssistants = Integer.parseInt(maxAssistantsStr);

        String neededMonitorsStr = matcher.group(5);
        if(neededMonitorsStr == null) {
          throw new IllegalArgumentException("Needed monitors is null");
        }
        int neededMonitors = Integer.parseInt(neededMonitorsStr);

        List<Assistant> assistantList = new ArrayList<>();
        String assistantString = matcher.group(6);
        if(assistantString != null && !assistantString.isEmpty()) {
        	if (!assistantString.isEmpty()) {
                Pattern assistantPattern = Pattern.compile("\\{([^\\{\\}]+)\\}");
                Matcher assistantMatcher = assistantPattern.matcher(assistantString);
                while (assistantMatcher.find()) {
                    String assistantData = assistantMatcher.group(1);
                    Assistant assistant = Assistant.fromString(assistantData);
                    assistantList.add(assistant);
                }
            }
        }

        List<Monitor> monitorList = new ArrayList<>();
        String monitorString = matcher.group(7);
        if(monitorString != null && !monitorString.isEmpty()) {
        	if (!monitorString.isEmpty()) {
                Pattern monitorPattern = Pattern.compile("\\{([^\\{\\}]+)\\}");
                Matcher monitorMatcher = monitorPattern.matcher(monitorString);
                while (monitorMatcher.find()) {
                    String monitorData = monitorMatcher.group(1);
                    Monitor monitor = Monitor.fromString(monitorData);
                    monitorList.add(monitor);
                }
            }
        }

        Activity activity = new Activity(activityName, educativeLevel, timeSlot, maxAssistants, neededMonitors);
        activity.setAssistants(assistantList);
        activity.setMonitorList(monitorList);

        return activity;

    }
}
