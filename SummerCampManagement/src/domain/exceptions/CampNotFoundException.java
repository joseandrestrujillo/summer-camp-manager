package domain.exceptions;

/**
 * La clase <code>CampNotFoundException</code> es una excepción que se lanza cuando no se puede encontrar
 * un campo (campamento) en el sistema.
 * Esta excepción extiende la clase <code>RuntimeException</code>, lo que significa que es una excepción no
 * comprobada y no requiere una declaración explícita o manejo en el código.
 */

public class CampNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
