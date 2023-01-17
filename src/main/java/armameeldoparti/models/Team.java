package armameeldoparti.models;

import armameeldoparti.utils.common.CommonFields;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * Team class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 10/07/2022
 */
@Getter
@Setter
public class Team {

  // ---------------------------------------- Private fields ------------------------------------

  private int teamNumber;

  private Map<Position, List<Player>> teamPlayers;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds a basic team with empty positions.
   */
  public Team(int teamNumber) {
    teamPlayers = new EnumMap<>(Position.class);

    for (Position position : Position.values()) {
      teamPlayers.put(position, new ArrayList<>());
    }

    setTeamNumber(teamNumber);
  }

  // ---------------------------------------- Public methods -------------------------------------

  /**
   * Clears all players lists.
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
   * Checks if a particular position players list is full.
   *
   * @param position The position to check.
   *
   * @return Whether the position players list is full or not.
   */
  public boolean isPositionFull(@NotNull Position position) {
    return teamPlayers.get(position)
                      .size() == CommonFields.getPlayersAmountMap()
                                             .get(position);
  }
}