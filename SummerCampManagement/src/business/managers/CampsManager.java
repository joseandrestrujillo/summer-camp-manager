package business.managers;

import java.util.List;

import business.dtos.ActivityDTO;
import business.dtos.CampDTO;
import business.dtos.MonitorDTO;
import business.exceptions.activity.ActivityNotFoundException;
import business.exceptions.activity.MonitorIsNotInActivityException;
import business.exceptions.assistant.AssistantAlreadyRegisteredException;
import business.exceptions.camp.CampAlreadyRegisteredException;
import business.exceptions.camp.IsNotAnSpecialEducator;
import business.exceptions.camp.NotTheSameLevelException;
import business.exceptions.camp.SpecialMonitorAlreadyRegisterException;
import business.exceptions.repository.NotFoundException;
import business.interfaces.IDAO;

/**
 * La clase CampsManager se encarga de gestionar campamentos, actividades y monitores.
 */
public class CampsManager {
    private IDAO<CampDTO, Integer> campRepository;
    private IDAO<ActivityDTO, String> activityRepository;
    private IDAO<MonitorDTO, Integer> monitorRepository;

    /**
     * Constructor de la clase CampsManager.
     * 
     * @param campRepository    El repositorio de campamentos.
     * @param activityRepository El repositorio de actividades.
     * @param monitorRepository  El repositorio de monitores.
     */
    public CampsManager(IDAO<CampDTO, Integer> campRepository, IDAO<ActivityDTO, String> activityRepository,
            IDAO<MonitorDTO, Integer> monitorRepository) {
        this.campRepository = campRepository;
        this.activityRepository = activityRepository;
        this.monitorRepository = monitorRepository;
    }

    /**
     * Registra una actividad en un campamento y verifica que tengan el mismo nivel educativo.
     * 
     * @param camp     El campamento en el que se registrará la actividad.
     * @param activity La actividad a registrar.
     * @return El campamento con la actividad registrada.
     * @throws NotTheSameLevelException Si la actividad y el campamento no tienen el mismo nivel educativo.
     */
    public CampDTO registerActivity(CampDTO camp, ActivityDTO activity) {
        if (activity.getEducativeLevel() != camp.getEducativeLevel()) {
            throw new NotTheSameLevelException();
        }

        List<ActivityDTO> activities = camp.getActivities();
        activities.add(activity);

        camp.setActivities(activities);

        this.activityRepository.save(activity);
        this.campRepository.save(camp);
        return camp;
    }

    /**
     * Verifica si un monitor está asignado a alguna actividad en un campamento.
     * 
     * @param camp    El campamento a verificar.
     * @param monitor El monitor a buscar.
     * @return true si el monitor está asignado a alguna actividad, false en caso contrario.
     */
    public boolean isMonitorInSomeActivity(CampDTO camp, MonitorDTO monitor) {
        boolean founded = false;

        List<ActivityDTO> activities = camp.getActivities();
        for (int i = 0; i < activities.size(); i++) {
            ActivityDTO activity = activities.get(i);
            if (activity.monitorIsRegistered(monitor)) {
                founded = true;
            }
        }
        return founded;
    }

    /**
     * Establece un monitor como el monitor principal de un campamento.
     * 
     * @param camp    El campamento en el que se establecerá el monitor principal.
     * @param monitor El monitor a establecer como principal.
     * @return El campamento actualizado con el monitor principal.
     * @throws MonitorIsNotInActivityException Si el monitor no está asignado a ninguna actividad en el campamento.
     */
    public CampDTO setPrincipalMonitor(CampDTO camp, MonitorDTO monitor) {
        boolean founded = isMonitorInSomeActivity(camp, monitor);

        if (!founded) {
            throw new MonitorIsNotInActivityException();
        }
        camp.setPrincipalMonitor(monitor);

        this.monitorRepository.save(monitor);
        this.campRepository.save(camp);
        return camp;
    }

    /**
     * Establece un monitor como monitor especial de un campamento.
     * 
     * @param camp    El campamento en el que se establecerá el monitor especial.
     * @param monitor El monitor a establecer como especial.
     * @return El campamento actualizado con el monitor especial.
     * @throws SpecialMonitorAlreadyRegisterException Si el monitor ya está asignado a alguna actividad en el campamento.
     * @throws IsNotAnSpecialEducator Si el monitor no es un monitor especial.
	 *
     */
    public CampDTO setSpecialMonitor(CampDTO camp, MonitorDTO monitor) {
        boolean founded = isMonitorInSomeActivity(camp, monitor);

        if (founded) {
            throw new SpecialMonitorAlreadyRegisterException();
        }
        
        if (monitor.isSpecialEducator() == false) {
        	throw new IsNotAnSpecialEducator();
        }

        camp.setSpecialMonitor(monitor);

        this.monitorRepository.save(monitor);
        this.campRepository.save(camp);
        return camp;
    }

    /**
     * Elimina una actividad de un campamento.
     * 
     * @param camp     El campamento del que se eliminará la actividad.
     * @param activity La actividad a eliminar.
     * @return El campamento actualizado sin la actividad.
     * @throws ActivityNotFoundException         Si la actividad no se encuentra en el campamento.
     * @throws MonitorIsNotInActivityException Si el monitor principal no está asignado a alguna actividad en el campamento.
     */
    public CampDTO deleteActivity(CampDTO camp, ActivityDTO activity) {
        List<ActivityDTO> activities = camp.getActivities();
        if (!activities.contains(activity)) {
            throw new ActivityNotFoundException();
        }

        activities.remove(activity);

        CampDTO auxCamp = camp;
        auxCamp.setActivities(activities);

        MonitorDTO principalMonitor = auxCamp.getPrincipalMonitor();
        if (principalMonitor != null) {
            if (!isMonitorInSomeActivity(auxCamp, principalMonitor)) {
                throw new MonitorIsNotInActivityException();
            }
        }

        camp = auxCamp;

        this.activityRepository.delete(activity);
        this.campRepository.save(camp);
        return camp;
    }

    /**
     * Verifica si un campamento está registrado en el repositorio.
     * 
     * @param camp El campamento a verificar.
     * @return true si el campamento está registrado, false en caso contrario.
     */
    public boolean isRegistered(CampDTO camp) {
        try {
            this.campRepository.find(camp.getCampID());
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    /**
     * Registra un campamento en el repositorio y verifica si ya está registrado.
     * 
     * @param camp El campamento a registrar.
     * @throws CampAlreadyRegisteredException Si el campamento ya está registrado.
     */
    public void registerCamp(CampDTO camp) {
        if (isRegistered(camp)) {
            throw new CampAlreadyRegisteredException();
        }
        this.campRepository.save(camp);
    }
}
