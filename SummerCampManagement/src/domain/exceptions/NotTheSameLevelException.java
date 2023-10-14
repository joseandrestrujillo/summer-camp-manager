package domain.exceptions;

/**
 * This exception is thrown when two objects are not at the same level.
 * For example, if you have a hierarchy of objects and you expect them to be at the same level,
 * but they are not, this exception can be thrown to indicate the inconsistency.
 */


public class NotTheSameLevelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
