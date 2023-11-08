package utilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.dtos.MonitorDTO;
import business.values.EducativeLevel;
import business.values.TimeSlot;

public class StringUtils {
	/**
     * Devuelve objeto Activity.
     *
     * @param inputString Una cadena que representa a la actividad en formato JSON.
     * @return Objeto de la clase
     */
    public static ActivityDTO activityFromString(String inputString) {

    	Pattern pattern = Pattern.compile("activityName: '(.+)',\\s+educativeLevel:\\s+(.+),\\s+timeSlot:\\s+(.+),\\s+maxAssistants:\\s+(\\d+),\\s+neededMonitors:\\s+(\\d+),\\s+assistants:\\s*(\\[[^\\]]*\\])?\\s*,\\s*monitors:\\s*(\\[[^\\]]*\\])?");
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

        List<AssistantDTO> assistantList = new ArrayList<>();
        String assistantString = matcher.group(6);
        if(assistantString != null && !assistantString.isEmpty()) {
        	if (!assistantString.isEmpty()) {
                Pattern assistantPattern = Pattern.compile("\\{(.*?)\\}");
                Matcher assistantMatcher = assistantPattern.matcher(assistantString);
                while (assistantMatcher.find()) {
                    String assistantData = assistantMatcher.group(1);
                    AssistantDTO assistant = StringUtils.assistantFromString(assistantData);
                    assistantList.add(assistant);
                }
            }
        }

        List<MonitorDTO> monitorList = new ArrayList<>();
        String monitorString = matcher.group(7);
        if(monitorString != null && !monitorString.isEmpty()) {
        	if (!monitorString.isEmpty()) {
                Pattern monitorPattern = Pattern.compile("\\{(.*?)\\}");
                Matcher monitorMatcher = monitorPattern.matcher(monitorString);
                while (monitorMatcher.find()) {
                    String monitorData = monitorMatcher.group(1);
                    MonitorDTO monitor = StringUtils.monitorFromString(monitorData);
                    monitorList.add(monitor);
                }
            }
        }

        ActivityDTO activity = new ActivityDTO(activityName, educativeLevel, timeSlot, maxAssistants, neededMonitors);


        return activity;

    }
    
    /**
     * Devuelve objeto Assistant.
     *
     * @param assistantString Una cadena que representa al asistente en formato JSON.
     * @return Objeto de la clase
     */
    public static AssistantDTO assistantFromString(String assistantString) {
        int id = 0;
        String firstName = "";
        String lastName = "";
        Date birthDate = null;
        boolean requireSpecialAttention = false;

        Pattern pattern = Pattern.compile("id: (\\d+), firstName: '(.+)', lastName: '(.+)', birthDate: (.+), requireSpecialAttention: (true|false)");
        Matcher matcher = pattern.matcher(assistantString);
        if (matcher.find()) {
            id = Integer.parseInt(matcher.group(1));
            firstName = matcher.group(2);
            lastName = matcher.group(3);
            birthDate = Utils.parseDate(matcher.group(4));
            requireSpecialAttention = Boolean.parseBoolean(matcher.group(5));
        }

        return new AssistantDTO(id, firstName, lastName, birthDate, requireSpecialAttention);
    }
    /**
     * Devuelve objeto Camp.
     *
     * @param inputString Una cadena que representa al campamento en formato JSON.
     * @return Objeto de la clase
     */
	public static CampDTO campFromString(String inputString) {

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
		  MonitorDTO principalMonitor = principalMonitorString.equals("null") ? null : StringUtils.monitorFromString(principalMonitorString);

		  String specialMonitorString = matcher.group(7);
		  MonitorDTO specialMonitor = specialMonitorString.equals("null") ? null : StringUtils.monitorFromString(specialMonitorString);

		  List<ActivityDTO> activities = new ArrayList<>();
		  
		  String activitiesString = matcher.group(8);
		  if (activitiesString != null && !activitiesString.isEmpty()) {
			  Pattern activityPattern = Pattern.compile("activityName: '(.+)',\\s+educativeLevel:\\s+(.+),\\s+timeSlot:\\s+(.+),\\s+maxAssistants:\\s+(\\d+),\\s+neededMonitors:\\s+(\\d+),\\s+assistants:\\s*(\\[[^\\]]*\\])?\\s*,\\s*monitors:\\s*(\\[[^\\]]*\\])?\\s*}");
			  Matcher activityMatcher = activityPattern.matcher(activitiesString);
			  while (activityMatcher.find()) {
				  String activityData = activityMatcher.group();
				  ActivityDTO activity = StringUtils.activityFromString(activityData);
				  activities.add(activity);
			  }
		  }

		  CampDTO camp = new CampDTO(campID, start, end, educativeLevel, capacity);
		  camp.setPrincipalMonitor(principalMonitor);
		  camp.setSpecialMonitor(specialMonitor);

		  return camp;
	}
	/**
     * Devuelve objeto Inscription.
     *
     * @param inputString Una cadena que representa la inscripcion en formato JSON.
     * @return Objeto de la clase
     */
	public static InscriptionDTO inscriptionFromString(String inputString) {
	    int assistantId = -1;
	    int campId = -1;
	    Date inscriptionDate = null;
	    float price = -1;
	    boolean canBeCanceled = false;

	    Pattern pattern = Pattern.compile("AssistantId: (\\d+), CampId: (.+), InscriptionDate: '(.+)', Price: (\\d+\\.\\d+), canBeCancelled: (true|false)");
	    Matcher matcher = pattern.matcher(inputString);
	    if (matcher.find()) {
	        assistantId = Integer.parseInt(matcher.group(1));
	        campId = Integer.parseInt(matcher.group(2));
	        inscriptionDate = Utils.parseDate(matcher.group(3));
	        price = Float.parseFloat(matcher.group(4));
	        canBeCanceled = Boolean.parseBoolean(matcher.group(5));
	    }

	    return new InscriptionDTO(assistantId, campId, inscriptionDate, price, canBeCanceled, false);
	}
	
	/**
     * Devuelve objeto Monitor.
     *
     * @param monitorString Una cadena que representa al monitor en formato JSON.
     * @return Objeto de la clase
     */
    public static MonitorDTO monitorFromString(String monitorString) {
        int id = 0;
        String firstName = "";
        String lastName = "";
        boolean specialEducator = false;

        Pattern pattern = Pattern.compile("id: (\\d+), firstName: '(.+)', lastName: '(.+)', isSpecialEducator: (true|false)");
        Matcher matcher = pattern.matcher(monitorString);
        if (matcher.find()) {
            id = Integer.parseInt(matcher.group(1));
            firstName = matcher.group(2);
            lastName = matcher.group(3);
            specialEducator = Boolean.parseBoolean(matcher.group(4));
        }

        return new MonitorDTO(id, firstName, lastName, specialEducator);
    }
	
}
