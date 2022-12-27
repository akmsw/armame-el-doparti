package armameeldoparti.utils.mixers;

import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.Team;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * By-skill distribution class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 12/07/2022
 */
public class BySkillsMixer implements PlayersMixer {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the by-skill players distributor.
   */
  public BySkillsMixer() {
    // No body needed
  }

  // ---------------------------------------- Public methods ----------------------------------

  /**
   * Distributes the players by their skills without considering anchorages.
   *
   * <p>Positions are traversed in reverse order to achieve the fairer distribution.
   *
   * <p>The players of each position are ordered based on their score, from highest to lowest.
   * The teams are then ordered based on the sum of their players scores so far, from lowest
   * to highest.
   *
   * <p>If the number of players to distribute is 2, the team with less skill points is
   * assigned the player with the highest skill points, and the team with more skill points
   * is assigned the lowest skill points player.
   *
   * <p>If the number of players to distribute is 4, two subgroups are made with the players
   * at the list ends, from the outside to the inside.
   * These subsets are then ordered based on their skill points, from highest to lowest.
   * The team with less skill points is assigned the set of players with more skill points.
   * The team with more skill points is assigned the set of players with the lowest skill points.
   *
   * @param teams List that contains the two teams.
   *
   * @return The updated teams with the players distributed by their skill points, without
   *         considering anchorages.
   */
  @Override
  public List<Team> withoutAnchorages(List<Team> teams) {
    List<Position> reversedEnum = Arrays.asList(Position.values());

    Collections.reverse(reversedEnum);

    for (Position position : reversedEnum) {
      List<Player> currentSet = CommonFields.getPlayersSets()
                                            .get(position);

      currentSet.sort(Comparator.comparingInt(Player::getSkillPoints)
                                .reversed());

      teams.sort(Comparator.comparingInt(Team::getTeamSkill));

      if (currentSet.size() == 2) {
        for (int i = 0; i < 2; i++) {
          teams.get(i)
               .getTeamPlayers()
               .get(position)
               .add(currentSet.get(i));
        }
      } else {
        distributeSubsets(teams, currentSet, position);
      }
    }

    return teams;
  }

  /**
   * Distributes the players by their skills considering anchorages.
   *
   * <p>First, the anchored players are grouped in different lists by their
   * anchorage number, and they are distributed as fair as possible starting
   * with the sets with most anchored players in order to avoid inconsistencies.
   *
   * <p>Then, the players that are not anchored are distributed between the teams
   * as fair as possible based on their skill points.
   * They will be added to a team only if the players per position or the players
   * per team amounts are not exceeded.
   *
   * @param teams List that contains the two teams.
   *
   * @return The updated teams with the players distributed by their skill points,
   *         without considering anchorages.
   */
  @Override
  public List<Team> withAnchorages(List<Team> teams) {
    List<List<Player>> anchoredPlayers = CommonFunctions.getAnchoredPlayers();

    for (List<Player> aps : anchoredPlayers) {
      teams.sort(Comparator.comparingInt(Team::getTeamSkill));

      for (Player p : aps) {
        p.setTeamNumber(teams.get(0)
                             .getTeamNumber());

        teams.get(0)
             .getTeamPlayers()
             .get(p.getPosition())
             .add(p);
      }
    }

    List<List<Player>> remainingPlayers = CommonFields.getPlayersSets()
                                                      .values()
                                                      .stream()
                                                      .flatMap(List::stream)
                                                      .filter(p -> p.getTeamNumber() == 0)
                                                      .collect(Collectors.groupingBy(
                                                               Player::getPosition))
                                                      .values()
                                                      .stream()
                                                      .collect(Collectors.toList());

    remainingPlayers.sort(Comparator.comparingInt(List::size));

    for (List<Player> ps : remainingPlayers) {
      ps.sort(Comparator.comparingInt(Player::getSkillPoints)
                        .reversed());

      if (ps.size() == 4) {
        teams.sort(Comparator.comparingInt(Team::getTeamSkill));

        distributeSubsets(teams, ps, ps.get(0)
                                       .getPosition());
      } else {
        for (Player p : ps) {
          teams.sort(Comparator.comparingInt(Team::getTeamSkill));

          int teamNumber = 0;

          if (teams.get(teamNumber)
                   .isPositionFull(p.getPosition())
              || teams.get(teamNumber)
                      .getPlayersCount() + 1 > Constants.PLAYERS_PER_TEAM) {
            teamNumber = 1;
          }

          p.setTeamNumber(teamNumber + 1);

          teams.get(teamNumber)
               .getTeamPlayers()
               .get(p.getPosition())
               .add(p);
        }
      }
    }

    return teams;
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Performs the subsets distribution in sets with 4 players as explained
   * in {@link #withAnchorages(List)}.
   *
   * @param teams      List that contains the two teams.
   * @param playersSet Current working players set.
   * @param position   Current working players position.
   */
  private void distributeSubsets(List<Team> teams, List<Player> playersSet, Position position) {
    List<Player> outerSubset = new ArrayList<>();
    List<Player> innerSubset = new ArrayList<>();

    outerSubset.add(playersSet.get(0));
    outerSubset.add(playersSet.get(playersSet.size() - 1));

    innerSubset.add(playersSet.get(1));
    innerSubset.add(playersSet.get(playersSet.size() - 2));

    List<List<Player>> playersSubsets = new ArrayList<>();

    playersSubsets.add(outerSubset);
    playersSubsets.add(innerSubset);

    playersSubsets.sort(Comparator.comparingInt(ps -> ps.stream()
                                                        .mapToInt(Player::getSkillPoints)
                                                        .reduce(0, Math::addExact)));

    var wrapper = new Object() {
      int index;
    };

    for (wrapper.index = 0; wrapper.index < 2; wrapper.index++) {
      playersSubsets.get(wrapper.index)
                    .forEach(p -> p.setTeamNumber(teams.get(1 - wrapper.index)
                                                       .getTeamNumber()));

      teams.get(wrapper.index)
           .getTeamPlayers()
           .get(position)
           .addAll(playersSubsets.get(1 - wrapper.index));
    }
  }
}