/**
 * @author  Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since   16/02/2021
 */

public class Midfielder extends Player {

    //Campos privados.
    private static Position position = Position.MIDFIELDER;

    /**
     * Constructor.
     * 
     * @param   name    El nombre del mediocampista.
     */
    protected Midfielder(String name) {
        super(name, position);
    }
}