package armameeldoparti.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Players class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since v1.0
 */
@Getter
@Setter
public class Player {

  // ---------------------------------------- Private fields ------------------------------------

  private boolean isAnchored;

  private int anchorageNumber;
  private int skillPoints;
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
  public Player(@NonNull String name,
                @NonNull Position position) {
    setName(name);
    setPosition(position);
    setAnchored(false);
    setTeamNumber(0);
    setAnchorageNumber(0);
    setSkillPoints(0);
  }
}