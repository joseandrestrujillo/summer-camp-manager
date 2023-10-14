package domain.exceptions;

/**
 * This exception is thrown when an operation attempts to use a time
 * that is considered to be after an early time constraint.
 * This is a runtime exception, so it does not need to be explicitly caught.
 */

public class AfterEarlyTimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

}