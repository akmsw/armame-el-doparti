/**
 * Clase representativa de los jugadores.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 15/02/2021
 */

package com.utils;

public class Player {

    /* ---------------------------------------- Campos privados  ---------------------------------- */

    private int rating;
    private int anchor;
    private int team;

    private String name;

    private Position position;

    /* ---------------------------------------- Constructor -------------------------------------- */

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
        setTeam(0);

        this.position = position;
    }

    /* ---------------------------------------- Métodos públicos --------------------------------- */

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

    /**
     * @param team Número de equipo al que pertenece el jugador.
     */
    public void setTeam(int team) {
        this.team = team;
    }

    /**
     * @return El puntaje asignado al jugador.
     */
    public int getRating() {
        return rating;
    }

    /**
     * @return Número de anclaje correspondiente.
     */
    public int getAnchor() {
        return anchor;
    }

    /**
     * @return El equipo al que pertenece el jugador.
     */
    public int getTeam() {
        return team;
    }

    /**
     * @return El nombre del jugador.
     */
    public String getName() {
        return name;
    }

    /**
     * @return La posición del jugador.
     */
    public Position getPosition() {
        return position;
    }
}