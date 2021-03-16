/**
 * Clase representativa de los jugadores.
 * 
 * @author Bonino, Francisco Ignacio.s
 * 
 * @version 1.1.0
 * 
 * @since 15/02/2021
 */

public class Player {

    // Campos privados.
    private int rating, anchor;
    private String name;
    private Position position;

    /**
     * Se crea un jugador básico con los parámetros recibidos.
     * 
     * @param name     El nombre del jugador.
     * @param position La posición del jugador.
     */
    public Player(String name, Position position) {
        setName(name);
        setRating(0);
        setAnchor(0);

        this.position = position;
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

    /**
     * @return A qué equipo está anclado el jugador.
     */
    public int getAnchor() {
        return anchor;
    }

    // ----------------------------------------Setters------------------------------------------

    /**
     * @param name El nombre del jugador.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param rating El puntaje del jugador.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * @param anchor Número de anclaje correspondiente.
     */
    public void setAnchor(int anchor) {
        this.anchor = anchor;
    }
}