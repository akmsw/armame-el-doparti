package armameeldoparti.models;

/**
 * Players class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 15/02/2021
 */
public class Player {

  // ---------------------------------------- Private fields ------------------------------------

  private int skill;
  private int anchorageNumber;
  private int teamNumber;

  private String name;

  private Position position;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds a basic player with the received parameters.
   *
   * @param name     Player name.
   * @param position Player position.
   */
  public Player(String name, Position position) {
    setName(name);
    setSkill(0);
    setAnchorageNumber(0);
    setTeam(0);

    this.position = position;
  }

  // ---------------------------------------- Public methods ------------------------------------

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Gets the player's skill.
   *
   * @return The player's skill.
   */
  public int getSkill() {
    return skill;
  }

  /**
   * Gets the player's anchorage number.
   *
   * @return The player's anchorage number.
   */
  public int getAnchorageNumber() {
    return anchorageNumber;
  }

  /**
   * Gets the player's team number.
   *
   * @return The player's team number.
   */
  public int getTeam() {
    return teamNumber;
  }

  /**
   * Gets the player's name.
   *
   * @return The player's name.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the player's position.
   *
   * @return The player's position.
   */
  public Position getPosition() {
    return position;
  }

  // ---------------------------------------- Setters -------------------------------------------

  /**
   * Updates the player's name.
   *
   * @param name The player's new name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Updates the player's skill.
   *
   * @param skill The player's new skill.
   */
  public void setSkill(int skill) {
    this.skill = skill;
  }

  /**
   * Updates the player's anchorage number.
   *
   * @param anchorageNumber The player's new anchorage number.
   */
  public void setAnchorageNumber(int anchorageNumber) {
    this.anchorageNumber = anchorageNumber;
  }

  /**
   * Updates the player's team number.
   *
   * @param teamNumber The player's new team number.
   */
  public void setTeam(int teamNumber) {
    this.teamNumber = teamNumber;
  }
}