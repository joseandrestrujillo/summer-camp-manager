package business.exceptions.monitor;

/**
 * Esta excepción se lanza cuando no se puede encontrar una actividad en el sistema.
 * Puede ocurrir cuando se busca una actividad que no existe en la base de datos.
 */

public class MonitorAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
