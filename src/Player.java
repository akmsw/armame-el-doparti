/**
 * Clase representativa de los jugadores.
 * 
 * @author Bonino, Francisco Ignacio.s
 * 
 * @version 1.0.0
 * 
 * @since 15/02/2021
 */

public class Player {

    // Campos privados.
    private int rating;
    private String name;
    private Position position;

    /**
     * Constructor.
     * 
     * @param name     El nombre del jugador.
     * @param position La posición del jugador.
     */
    public Player(String name, Position position) {
        setName(name);
        setPosition(position);
    }

    // ----------------------------------------Métodos públicos---------------------------------

    // ----------------------------------------Getters------------------------------------------

    /**
     * @return El nombre del jugador.
     */
    public String getName() {
        return name;
    }

    /**
     * @return El puntaje asignado al jugador.
     */
    public int getRating() {
        return rating;
    }

    /**
     * @return La posición del jugador.
     */
    public Position getPosition() {
        return position;
    }

    // ----------------------------------------Setters------------------------------------------

    /**
     * En este método se setea el nombre del jugador.
     * 
     * @param name El nombre del jugador.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * En este método se setea el puntaje del jugador.
     * 
     * @param rating El puntaje del jugador.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * En este método se setea la posición del jugador.
     * 
     * @param position La posición del jugador.
     */
    public void setPosition(Position position) {
        this.position = position;
    }
}