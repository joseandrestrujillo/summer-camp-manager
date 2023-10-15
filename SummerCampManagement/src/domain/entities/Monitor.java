package domain.entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * La clase Monitor representa a un monitor en un sistema educativo.
 */
public class Monitor {
    private int id;             
    private String firstName;   
    private String lastName;    
    private boolean specialEducator; 

    /**
     * Constructor predeterminado de la clase Monitor.
     * Inicializa los campos con valores predeterminados.
     */
    public Monitor() {
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.specialEducator = false;
    }

    /**
     * Constructor de la clase Monitor que toma parámetros.
     *
     * @param id El identificador del monitor.
     * @param firstName El primer nombre del monitor.
     * @param lastName El apellido del monitor.
     * @param SpecialEducator Indica si el monitor es educador especial.
     */
    public Monitor(int id, String firstName, String lastName, boolean SpecialEducator) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialEducator = SpecialEducator;
    }

    /**
     * Obtiene el identificador del monitor.
     *
     * @return El identificador del monitor.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Obtiene el primer nombre del monitor.
     *
     * @return El primer nombre del monitor.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Obtiene el apellido del monitor.
     *
     * @return El apellido del monitor.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Comprueba si el monitor es un educador especial.
     *
     * @return `true` si el monitor es un educador especial, `false` en caso contrario.
     */
    public boolean isSpecialEducator() {
        return this.specialEducator;
    }

    /**
     * Establece el identificador del monitor.
     *
     * @param id2 El nuevo identificador del monitor.
     */
    public void setId(int id2) {
        this.id = id2;
    }

    /**
     * Establece el primer nombre del monitor.
     *
     * @param firstName2 El nuevo primer nombre del monitor.
     */
    public void setFirstName(String firstName2) {
        this.firstName = firstName2;
    }

    /**
     * Establece el apellido del monitor.
     *
     * @param lastName2 El nuevo apellido del monitor.
     */
    public void setLastName(String lastName2) {
        this.lastName = lastName2;
    }

    /**
     * Establece si el monitor es un educador especial.
     *
     * @param SpecialEducator2 `true` si el monitor es un educador especial, `false` en caso contrario.
     */
    public void setSpecialEducator(boolean SpecialEducator2) {
        this.specialEducator = SpecialEducator2;
    }

    /**
     * Devuelve una representación en cadena del monitor en formato JSON.
     *
     * @return Una cadena que representa al monitor en formato JSON.
     */
    public String toString() {
        return "{id: " + this.id + ", firstName: '" + this.firstName + "', lastName: '" + this.lastName
                + "', isSpecialEducator: " + this.specialEducator + "}";
    }
    
    public static Monitor fromString(String monitorString) {
        int id = 0;
        String firstName = "";
        String lastName = "";
        boolean specialEducator = false;

        Pattern pattern = Pattern.compile("id: (\\d+), firstName: '(.+)', lastName: '(.+)', isSpecialEducator: (true|false)");
        Matcher matcher = pattern.matcher(monitorString);
        if (matcher.find()) {
            id = Integer.parseInt(matcher.group(1));
            firstName = matcher.group(2);
            lastName = matcher.group(3);
            specialEducator = Boolean.parseBoolean(matcher.group(4));
        }

        return new Monitor(id, firstName, lastName, specialEducator);
    }
}

	

