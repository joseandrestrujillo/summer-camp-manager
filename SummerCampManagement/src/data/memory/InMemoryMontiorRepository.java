package data.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import business.dtos.ActivityDTO;
import business.dtos.MonitorDTO;
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import business.interfaces.IMonitorDAO;

/**
 * La clase InMemoryMontiorRepository es una implementación en memoria de un repositorio de monitores.
 
 */
public class InMemoryMontiorRepository implements IMonitorDAO {
    private Map<Integer, MonitorDTO> mapOfMonitor;
    private Map<String, List<MonitorDTO>> mapOfMonitorActivity;

    /**
     * Constructor de la clase InMemoryMontiorRepository.
     * Inicializa un nuevo mapa para almacenar monitores en memoria.
     */
    public InMemoryMontiorRepository() {
        this.mapOfMonitor = new HashMap<Integer, MonitorDTO>();
        this.mapOfMonitorActivity = new HashMap<String, List<MonitorDTO>>();
    }

    /**
     * Busca un monitor por su identificador.
     *
     * @param identifier El identificador del monitor a buscar.
     * @return El monitor encontrado.
     * @throws NotFoundException Si el monitor no se encuentra en el repositorio.
     */
    @Override
    public MonitorDTO find(Integer identifier) {
        if (this.mapOfMonitor.get(identifier) == null) {
            throw new NotFoundException();
        }
        return this.mapOfMonitor.get(identifier);
    }

    /**
     * Guarda un monitor en el repositorio.
     *
     * @param obj El monitor a guardar en el repositorio.
     */
    @Override
    public void save(MonitorDTO obj) {
        this.mapOfMonitor.put(obj.getId(), obj);
    }

    /**
     * Obtiene una lista de todos los monitores almacenados en el repositorio.
     *
     * @return Una lista de monitores.
     */
    @Override
    public List<MonitorDTO> getAll(Optional<ICriteria> criteria) {
        List<MonitorDTO> allMonitors = new ArrayList<>(this.mapOfMonitor.values());
        return allMonitors;
    }

    /**
     * Elimina un monitor del repositorio.
     *
     * @param obj El monitor a eliminar del repositorio.
     */
    @Override
    public void delete(MonitorDTO obj) {
        this.mapOfMonitor.remove(obj.getId());
    }

	@Override
	public List<MonitorDTO> getMonitorsInAnActivity(ActivityDTO activity) {
		if (this.mapOfMonitorActivity.get(activity.getActivityName()) == null) {
            this.mapOfMonitorActivity.put(activity.getActivityName(), new ArrayList<MonitorDTO>());
        }
		return this.mapOfMonitorActivity.get(activity.getActivityName());
	}

	@Override
	public void saveAndRelateWithAnActivity(MonitorDTO monitor, ActivityDTO activity) {
		if (this.mapOfMonitorActivity.get(activity.getActivityName()) == null) {
            this.mapOfMonitorActivity.put(activity.getActivityName(), new ArrayList<MonitorDTO>());
        }
		
		this.mapOfMonitorActivity.get(activity.getActivityName()).add(monitor);
	}
}
