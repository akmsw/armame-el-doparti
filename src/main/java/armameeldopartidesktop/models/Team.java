package armameeldopartidesktop.models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import armameeldopartidesktop.models.enums.Position;
import armameeldopartidesktop.utils.common.CommonFields;
import armameeldopartidesktop.utils.common.Constants;

/**
 * Team class.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class Team {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private int teamNumber;

  private Map<Position, List<Player>> teamPlayers;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic team with empty position sets.
   *
   * @param teamNumber Integer identification for the team.
   */
  public Team(int teamNumber) {
    setTeamNumber(teamNumber);
    setTeamPlayers(new EnumMap<>(Position.class));

    for (Position position : Position.values()) {
      teamPlayers.put(position, new ArrayList<>());
    }
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Clears all players sets in the team.
   */
  public void clear() {
    teamPlayers.values()
               .stream()
               .flatMap(List::stream)
               .forEach(player -> player.setTeamNumber(Constants.PLAYER_NO_TEAM_ASSIGNED));

    teamPlayers.values()
               .forEach(List::clear);
  }

  /**
   * @param position The position of the set to check.
   *
   * @return Whether the specified position set in the team is full.
   */
  public boolean isPositionFull(Position position) {
    return teamPlayers.get(position).size() == CommonFields.getPlayerLimitPerPosition().get(position);
  }

  /**
   * @return The number of players in the team.
   */
  public int getPlayersCount() {
    return teamPlayers.values()
                      .stream()
                      .mapToInt(List::size)
                      .sum();
  }

  /**
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
   * @return The number of players per position in the team.
   */
  public Map<Position, Integer> getPlayersCountPerPosition() {
    return teamPlayers.values()
                      .stream()
                      .flatMap(List::stream)
                      .collect(Collectors.toMap(Player::getPosition, _ -> 1, Integer::sum, () -> new EnumMap<>(Position.class)));
  }

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public int getTeamNumber() {
    return teamNumber;
  }

  public Map<Position, List<Player>> getTeamPlayers() {
    return teamPlayers;
  }

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setTeamNumber(int teamNumber) {
    this.teamNumber = teamNumber;
  }

  public void setTeamPlayers(Map<Position, List<Player>> teamPlayers) {
    this.teamPlayers = teamPlayers;
  }
}