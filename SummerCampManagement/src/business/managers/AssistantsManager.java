package business.managers;

import java.util.List;

import business.dtos.AssistantDTO;
import business.exceptions.assistant.AssistantAlreadyRegisteredException;
import business.exceptions.assistant.AssistantNotFoundException;
import business.exceptions.repository.NotFoundException;
import business.interfaces.IDAO;

/**
 * La clase AssistantsManager se encarga de gestionar el registro, actualización y consulta de asistentes.
 */
public class AssistantsManager {
    private IDAO<AssistantDTO, Integer> assistantRepository;

    /**
     * Constructor de la clase AssistantsManager.
     * 
     * @param assistantRepository El repositorio de asistentes con el que se va a trabajar.
     */
    public AssistantsManager(IDAO<AssistantDTO, Integer> assistantRepository) {
        this.assistantRepository = assistantRepository;
    }

    /**
     * Registra un asistente en el repositorio.
     * 
     * @param assistant El asistente a registrar.
     * @throws AssistantAlreadyRegisteredException Si el asistente ya está registrado.
     */
    public void registerAssistant(AssistantDTO assistant) {
        if (isRegistered(assistant)) {
            throw new AssistantAlreadyRegisteredException();
        }
        this.assistantRepository.save(assistant);
    }

    /**
     * Actualiza la información de un asistente en el repositorio.
     * 
     * @param assistant El asistente cuya información se va a actualizar.
     * @throws AssistantNotFoundException Si el asistente no está registrado.
     */
    public void updateAssistant(AssistantDTO assistant) {
        if (!isRegistered(assistant)) {
            throw new AssistantNotFoundException();
        }
        this.assistantRepository.save(assistant);
    }

    /**
     * Verifica si un asistente está registrado en el repositorio.
     * 
     * @param assistant El asistente a verificar.
     * @return true si el asistente está registrado, false en caso contrario.
     */
    public boolean isRegistered(AssistantDTO assistant) {
        try {
            this.assistantRepository.find(assistant.getId());
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    /**
     * Obtiene una lista de todos los asistentes registrados en el repositorio.
     * 
     * @return Una lista de asistentes registrados.
     */
    public List<AssistantDTO> getListOfRegisteredAssistant() {
        return this.assistantRepository.getAll();
    }
}
