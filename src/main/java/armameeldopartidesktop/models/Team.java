package armameeldopartidesktop.models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import armameeldopartidesktop.models.enums.Position;
import armameeldopartidesktop.utils.common.CommonFields;

/**
 * Team class.
 *
 * @since 3.0.0
 *
 * @version 1.1.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class Team {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private Map<Position, List<Player>> players;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic team with empty position sets.
   */
  public Team() {
    setPlayers(new EnumMap<>(Position.class));

    for (Position position : Position.values()) {
      players.put(position, new ArrayList<>());
    }
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Clears all players sets in the team.
   */
  public void clear() {
    for (List<Player> playersList : players.values()) {
      playersList.clear();
    }
  }

  /**
   * @param position The position of the set to check.
   *
   * @return Whether the specified position set in the team is full.
   */
  public boolean isPositionFull(Position position) {
    return players.get(position).size() == CommonFields.getPlayerLimitPerPosition().get(position);
  }

  /**
   * @return The number of players in the team.
   */
  public int getPlayersCount() {
    return players.values()
                  .stream()
                  .mapToInt(List::size)
                  .sum();
  }

  /**
   * @return The team skill points accumulated so far.
   */
  public int getTeamSkill() {
    return players.values()
                  .stream()
                  .flatMap(List::stream)
                  .mapToInt(Player::getSkillPoints)
                  .sum();
  }

  /**
   * @return The number of players per position in the team.
   */
  public Map<Position, Integer> getPlayersCountPerPosition() {
    return players.values()
                  .stream()
                  .flatMap(List::stream)
                  .collect(Collectors.toMap(Player::getPosition, _ -> 1, Integer::sum, () -> new EnumMap<>(Position.class)));
  }

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public Map<Position, List<Player>> getPlayers() {
    return players;
  }

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setPlayers(Map<Position, List<Player>> teamPlayers) {
    this.players = teamPlayers;
  }
}