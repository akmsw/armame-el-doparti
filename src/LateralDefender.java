/**
 * @author  Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since   16/02/2021
 */

public class LateralDefender extends Player {

    //Campos privados.
    private static Position position = Position.LATERALDEFENDER;

    /**
     * Constructor.
     * 
     * @param   name    El nombre del defensor lateral.
     */
    protected LateralDefender(String name) {
        super(name, position);
    }
}