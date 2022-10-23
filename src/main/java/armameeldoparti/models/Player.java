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

  private int skillPoints;
  private int anchorageNumber;
  private int teamNumber;

  private boolean isAnchored;

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
    setSkillPoints(0);
    setAnchorageNumber(0);
    setTeamNumber(0);
    setAnchored(false);
    setPosition(position);
  }

  // ---------------------------------------- Public methods ------------------------------------

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Gets the player's skill points.
   *
   * @return The player's skill points.
   */
  public int getSkillPoints() {
    return skillPoints;
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
  public int getTeamNumber() {
    return teamNumber;
  }

  /**
   * Gets the player's anchorage state.
   *
   * @return The player's anchorage state.
   */
  public boolean isAnchored() {
    return isAnchored;
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
   * Updates the player's skill points.
   *
   * @param skillPoints The player's new skill points.
   */
  public void setSkillPoints(int skillPoints) {
    this.skillPoints = skillPoints;
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
  public void setTeamNumber(int teamNumber) {
    this.teamNumber = teamNumber;
  }

  /**
   * Updates the player's anchorage state.
   *
   * @param isAnchored The player's new anchorage state.
   */
  public void setAnchored(boolean isAnchored) {
    this.isAnchored = isAnchored;
  }

  /**
   * Updates the player's position.
   *
   * @param position The player's new position.
   */
  public void setPosition(Position position) {
    this.position = position;
  }
}