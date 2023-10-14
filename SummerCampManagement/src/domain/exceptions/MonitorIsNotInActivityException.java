package domain.exceptions;

/**
 * This exception is thrown when a monitor is not in the expected activity state.
 * It typically indicates that an operation or action is being performed on a monitor
 * that should be in a specific activity but is not.
 */

public class MonitorIsNotInActivityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}