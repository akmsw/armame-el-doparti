package armameeldoparti.models;

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

  private int skill;
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
    setSkill(0);
    setAnchor(0);
    setTeam(0);

    this.position = position;
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Obtiene el puntaje asignado al jugador.
   *
   * @return El puntaje asignado al jugador.
   */
  public int getSkill() {
    return skill;
  }

  /**
   * Obtiene el número de anclaje al que corresponde el jugador.
   *
   * @return El número de anclaje correspondiente.
   */
  public int getAnchor() {
    return anchor;
  }

  /**
   * Obtiene el número de equipo al que pertenece el jugador.
   *
   * @return El número de equipo al que pertenece el jugador.
   */
  public int getTeam() {
    return team;
  }

  /**
   * Obtiene el nombre del jugador.
   *
   * @return El nombre del jugador.
   */
  public String getName() {
    return name;
  }

  /**
   * Obtiene la posición del jugador.
   *
   * @return La posición del jugador.
   */
  public Position getPosition() {
    return position;
  }

  // ---------------------------------------- Setters -------------------------------------------

  /**
   * Actualiza el nombre del jugador.
   *
   * @param name Nombre del jugador.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Actualiza la puntuación del jugador.
   *
   * @param skill Puntuación del jugador.
   */
  public void setSkill(int skill) {
    this.skill = skill;
  }

  /**
   * Actualiza el número de anclaje correspondiente.
   *
   * @param anchor Número de anclaje correspondiente.
   */
  public void setAnchor(int anchor) {
    this.anchor = anchor;
  }

  /**
   * Actualiza el número de equipo al que pertenece el jugador.
   *
   * @param team Número de equipo al que pertenece el jugador.
   */
  public void setTeam(int team) {
    this.team = team;
  }
}