/**
 * @author  Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since   16/02/2021
 */

public class Forward extends Player {

    //Campos privados.
    private static Position position = Position.FORWARD;

    /**
     * Constructor.
     * 
     * @param   name    El nombre del delantero.
     */
    protected Forward(String name) {
        super(name, position);
    }
}