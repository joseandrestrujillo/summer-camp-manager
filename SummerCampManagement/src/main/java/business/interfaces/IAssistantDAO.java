package business.interfaces;

import java.util.List;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;

/**
 * Esta interfaz representa el Objeto de Acceso a Datos (DAO) para la entidad Asistente.
 * Extiende la interfaz IDAO con AssistantDTO como la entidad y Integer como la clave.
 */
public interface IAssistantDAO extends IDAO<AssistantDTO, Integer>{

    /**
     * Este método se utiliza para obtener una lista de asistentes en un campamento específico.
     * @param camp El campamento para obtener las actividades
      * @return Una lista de asistentes DTOs.
     */
    public List<AssistantDTO> getAssistantsInACamp(CampDTO camp);

    /**
     * Este método se utiliza para obtener una lista de asistentes en una actividad específica.
      * @param activity La actividad del campamento
      * @return Una lista de asistentes DTOs.
     */
    public List<AssistantDTO> getAssistantsInAnActivity(ActivityDTO activity);
}