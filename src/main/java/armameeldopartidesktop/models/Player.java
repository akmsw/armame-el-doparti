package armameeldopartidesktop.models;

import armameeldopartidesktop.models.enums.Position;
import armameeldopartidesktop.utils.common.Constants;

/**
 * Class that represents a player.
 *
 * @since 1.0.0
 *
 * @version 3.1.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class Player {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private int skillPoints;

  private String name;

  private Position position;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic player with the received parameters.
   *
   * @param name     Player name.
   * @param position Player position.
   */
  public Player(String name, Position position) {
    setName(name);
    setPosition(position);
    setSkillPoints(Constants.PLAYER_NO_SKILL_POINTS_ASSIGNED);
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * String representation of a Player object. Format used for error reporting.
   *
   * @return A string representation of a Player object.
   *
   * @see armameeldopartidesktop.utils.common.CommonFunctions#generateErrorReport(armameeldopartidesktop.models.enums.Error)
   */
  @Override
  public String toString() {
    return ("Position: " + getPosition().toString() + System.lineSeparator()
            + "\t\tName: " + getName() + System.lineSeparator()
            + "\t\tSkill points: " + getSkillPoints() + System.lineSeparator());
  }

  // ---------- Public getters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public int getSkillPoints() {
    return skillPoints;
  }

  public String getName() {
    return name;
  }

  public Position getPosition() {
    return position;
  }

  // ---------- Public setters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setSkillPoints(int skillPoints) {
    this.skillPoints = skillPoints;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPosition(Position position) {
    this.position = position;
  }
}