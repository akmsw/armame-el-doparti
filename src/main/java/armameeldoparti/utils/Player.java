package armameeldoparti.utils;

/**
 * Clase representativa de los jugadores.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 15/02/2021
 */
public class Player {

    // ---------------------------------------- Campos privados -----------------------------------

    private int score;
    private int anchor;
    private int team;

    private String name;

    private Position position;

    // ---------------------------------------- Constructor ---------------------------------------

    /**
     * Construye un jugador básico con los parámetros recibidos.
     *
     * @param name     Nombre del jugador.
     * @param position Posición del jugador.
     */
    public Player(String name, Position position) {
        setName(name);
        setScore(0);
        setAnchor(0);
        setTeam(0);

        this.position = position;
    }

    // ---------------------------------------- Métodos públicos ----------------------------------

    // ---------------------------------------- Getters -------------------------------------------

    /**
     * @return Puntaje asignado al jugador.
     */
    public int getScore() {
        return score;
    }

    /**
     * @return Número de anclaje correspondiente.
     */
    public int getAnchor() {
        return anchor;
    }

    /**
     * @return Equipo al que pertenece el jugador.
     */
    public int getTeam() {
        return team;
    }

    /**
     * @return Nombre del jugador.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Posición del jugador.
     */
    public Position getPosition() {
        return position;
    }

    // ---------------------------------------- Setters -------------------------------------------

    /**
     * @param name Nombre del jugador.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param score Puntuación del jugador.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @param anchor Número de anclaje correspondiente.
     */
    public void setAnchor(int anchor) {
        this.anchor = anchor;
    }

    /**
     * @param team Número de equipo al que pertenece el jugador.
     */
    public void setTeam(int team) {
        this.team = team;
    }
}
