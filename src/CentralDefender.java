/**
 * @author  Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since   15/02/2021
 */

public class CentralDefender extends Player {

    //Campos privados.
    private static Position position = Position.CENTRALDEFENDER;

    /**
     * Constructor.
     * 
     * @param   name    El nombre del defensor central.
     */
    protected CentralDefender(String name) {
        super(name, position);
    }
}