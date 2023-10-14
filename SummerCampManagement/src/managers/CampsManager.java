package managers;

import java.util.List;

import domain.entities.Activity;
import domain.entities.Camp;
import domain.entities.Monitor;
import domain.exceptions.ActivityNotFoundException;
import domain.exceptions.AssistantAlreadyRegisteredException;
import domain.exceptions.CampAlreadyRegisteredException;
import domain.exceptions.MonitorIsNotInActivityException;
import domain.exceptions.NotFoundException;
import domain.exceptions.NotTheSameLevelException;
import domain.exceptions.SpecialMonitorAlreadyRegisterException;
import domain.interfaces.IRepository;

/**
 * La clase CampsManager se encarga de gestionar campamentos, actividades y monitores.
 */
public class CampsManager {
    private IRepository<Camp, Integer> campRepository;
    private IRepository<Activity, String> activityRepository;
    private IRepository<Monitor, Integer> monitorRepository;

    /**
     * Constructor de la clase CampsManager.
     * 
     * @param campRepository    El repositorio de campamentos.
     * @param activityRepository El repositorio de actividades.
     * @param monitorRepository  El repositorio de monitores.
     */
    public CampsManager(IRepository<Camp, Integer> campRepository, IRepository<Activity, String> activityRepository,
            IRepository<Monitor, Integer> monitorRepository) {
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
    public Camp registerActivity(Camp camp, Activity activity) {
        if (activity.getEducativeLevel() != camp.getEducativeLevel()) {
            throw new NotTheSameLevelException();
        }

        List<Activity> activities = camp.getActivities();
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
    public boolean isMonitorInSomeActivity(Camp camp, Monitor monitor) {
        boolean founded = false;

        List<Activity> activities = camp.getActivities();
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
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
    public Camp setPrincipalMonitor(Camp camp, Monitor monitor) {
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
     */
    public Camp setSpecialMonitor(Camp camp, Monitor monitor) {
        boolean founded = isMonitorInSomeActivity(camp, monitor);

        if (founded) {
            throw new SpecialMonitorAlreadyRegisterException();
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
    public Camp deleteActivity(Camp camp, Activity activity) {
        List<Activity> activities = camp.getActivities();
        if (!activities.contains(activity)) {
            throw new ActivityNotFoundException();
        }

        activities.remove(activity);

        Camp auxCamp = camp;
        auxCamp.setActivities(activities);

        Monitor principalMonitor = auxCamp.getPrincipalMonitor();
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
    public boolean isRegistered(Camp camp) {
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
    public void registerCamp(Camp camp) {
        if (isRegistered(camp)) {
            throw new CampAlreadyRegisteredException();
        }
        this.campRepository.save(camp);
    }
}
