package armameeldopartidesktop.models;

import armameeldopartidesktop.models.enums.Error;
import armameeldopartidesktop.models.enums.Position;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;

/**
 * Player class.
 *
 * @since 1.0.0
 *
 * @version 3.0.1
 *
 * @author Bonino, Francisco Ignacio.
 */
public class Player {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private int skillPoints;
  private int teamId;
  private int anchorageId;

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
    setTeamId(Constants.PLAYER_NO_TEAM_ASSIGNED);
    setAnchorageId(Constants.PLAYER_NO_ANCHORAGE_ASSIGNED);
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
    return "Position: " + getPosition().toString() + System.lineSeparator()
           + "\t\tName: " + getName() + System.lineSeparator()
           + "\t\tAnchorage number: " + getAnchorageId() + System.lineSeparator()
           + "\t\tSkill points: " + getSkillPoints() + System.lineSeparator()
           + "\t\tTeam number: " + getTeamId() + System.lineSeparator();
  }

  /**
   * @return Whether the player has an anchoraged assigned or not.
   */
  public boolean isAnchored() {
    return anchorageId != Constants.PLAYER_NO_ANCHORAGE_ASSIGNED;
  }

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  public int getAnchorageId() {
    return anchorageId;
  }

  public int getSkillPoints() {
    return skillPoints;
  }

  public int getTeamId() {
    return teamId;
  }

  public String getName() {
    return name;
  }

  public Position getPosition() {
    return position;
  }

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setAnchorageId(int anchorageId) {
    if (anchorageId < 0) {
      CommonFunctions.exitProgram(Error.ERROR_INTERNAL, new IllegalArgumentException(Constants.MSG_ERROR_DEBUG_INVALID_ANCHORAGE_ID));
    }

    this.anchorageId = anchorageId;
  }

  public void setSkillPoints(int skillPoints) {
    this.skillPoints = skillPoints;
  }

  public void setTeamId(int teamId) {
    this.teamId = teamId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPosition(Position position) {
    this.position = position;
  }
}