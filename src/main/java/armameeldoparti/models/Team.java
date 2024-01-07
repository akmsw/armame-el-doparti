package armameeldoparti.models;

import armameeldoparti.utils.common.CommonFields;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Team class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class Team {

  // ---------------------------------------------------------------- Private fields -----------------------------------------------------------------

  private int teamNumber;

  private Map<Position, List<Player>> teamPlayers;

  // --------------------------------------------------------------- Constructor ---------------------------------------------------------------------

  /**
   * Builds a basic team with empty position sets.
   */
  public Team(int teamNumber) {
    setTeamPlayers(new EnumMap<>(Position.class));

    for (Position position : Position.values()) {
      teamPlayers.put(position, new ArrayList<>());
    }

    setTeamNumber(teamNumber);
  }

  // ---------------------------------------------------------------- Public methods -----------------------------------------------------------------

  /**
   * Clears all players sets in the team.
   */
  public void clear() {
    teamPlayers.values()
               .forEach(List::clear);
  }

  /**
   * Gets the amount of players in this team.
   *
   * @return The amount of players in this team.
   */
  public int getPlayersCount() {
    return teamPlayers.values()
                      .stream()
                      .mapToInt(List::size)
                      .sum();
  }

  /**
   * Gets the team skill points accumulated so far.
   *
   * @return The team skill points accumulated so far.
   */
  public int getTeamSkill() {
    return teamPlayers.values()
                      .stream()
                      .flatMap(List::stream)
                      .mapToInt(Player::getSkillPoints)
                      .sum();
  }

  /**
   * Checks if a particular position set in the team is full.
   *
   * @param position The position of the set to check.
   *
   * @return Whether the position set in the team is full.
   */
  public boolean isPositionFull(Position position) {
    return teamPlayers.get(position)
                      .size() == CommonFields.getPlayersAmountMap()
                                             .get(position);
  }

  // -------------------------------------------------------------------- Getters --------------------------------------------------------------------

  public int getTeamNumber() {
    return teamNumber;
  }

  public Map<Position, List<Player>> getTeamPlayers() {
    return teamPlayers;
  }

  // -------------------------------------------------------------------- Setters --------------------------------------------------------------------

  public void setTeamNumber(int teamNumber) {
    this.teamNumber = teamNumber;
  }

  public void setTeamPlayers(Map<Position, List<Player>> teamPlayers) {
    this.teamPlayers = teamPlayers;
  }
}