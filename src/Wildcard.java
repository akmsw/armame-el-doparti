/**
 * @author  Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since   16/02/2021
 */

public class Wildcard extends Player {

    //Campos privados.
    private static Position position = Position.WILDCARD;

    /**
     * Constructor.
     * 
     * @param   name    El nombre del comodín.
     */
    protected Wildcard(String name) {
        super(name, position);
    }   
}