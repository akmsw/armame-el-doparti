package armameeldoparti.utils;

import armameeldoparti.Main;
import armameeldoparti.models.Player;
import armameeldoparti.models.Positions;
import armameeldoparti.models.Team;
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
    List<Positions> reversedEnum = Arrays.asList(Positions.values());

    Collections.reverse(reversedEnum);

    for (Positions position : reversedEnum) {
      List<Player> currentSet = Main.getPlayersSets()
                                    .get(position);

      currentSet.sort(Comparator.comparingInt(Player::getSkillPoints)
                                .reversed());

      teams.sort(Comparator.comparingInt(Team::getTeamSkill));

      if (currentSet.size() == 2) {
        teams.get(0)
             .getPlayers()
             .get(position)
             .add(currentSet.get(0));

        teams.get(1)
             .getPlayers()
             .get(position)
             .add(currentSet.get(1));
      } else {
        List<Player> outerSubset = new ArrayList<>();
        List<Player> innerSubset = new ArrayList<>();

        outerSubset.add(currentSet.get(0));
        outerSubset.add(currentSet.get(currentSet.size() - 1));

        innerSubset.add(currentSet.get(1));
        innerSubset.add(currentSet.get(currentSet.size() - 2));

        List<List<Player>> playersSubsets = new ArrayList<>();

        playersSubsets.add(outerSubset);
        playersSubsets.add(innerSubset);
        playersSubsets.sort(Comparator.comparingInt(ps -> ps.stream()
                                                            .mapToInt(Player::getSkillPoints)
                                                            .reduce(0, Math::addExact)));

        playersSubsets.get(0)
                      .forEach(p -> p.setTeamNumber(teams.get(1)
                                                         .getTeamNumber()));

        playersSubsets.get(1)
                      .forEach(p -> p.setTeamNumber(teams.get(0)
                                                         .getTeamNumber()));

        teams.get(0)
             .getPlayers()
             .get(position)
             .addAll(playersSubsets.get(1));

        teams.get(1)
             .getPlayers()
             .get(position)
             .addAll(playersSubsets.get(0));
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
    List<List<Player>> anchoredPlayers = PlayersMixer.getAnchoredPlayers();

    for (List<Player> aps : anchoredPlayers) {
      teams.sort(Comparator.comparingInt(Team::getTeamSkill));

      for (Player p : aps) {
        p.setTeamNumber(teams.get(0)
                             .getTeamNumber());

        teams.get(0)
             .getPlayers()
             .get(p.getPosition())
             .add(p);
      }
    }

    List<List<Player>> remainingPlayers = Main.getPlayersSets()
                                              .values()
                                              .stream()
                                              .flatMap(List::stream)
                                              .filter(p -> p.getTeamNumber() == 0)
                                              .collect(
                                                Collectors.groupingBy(Player::getPosition))
                                              .values()
                                              .stream()
                                              .collect(Collectors.toList());

    for (List<Player> ps : remainingPlayers) {
      ps.sort(Comparator.comparingInt(Player::getSkillPoints)
                        .reversed());

      if (ps.size() == 4) {
        teams.sort(Comparator.comparingInt(Team::getTeamSkill));

        List<Player> outerSubset = new ArrayList<>();
        List<Player> innerSubset = new ArrayList<>();

        outerSubset.add(ps.get(0));
        outerSubset.add(ps.get(ps.size() - 1));

        innerSubset.add(ps.get(1));
        innerSubset.add(ps.get(ps.size() - 2));

        List<List<Player>> playersSubsets = new ArrayList<>();

        playersSubsets.add(outerSubset);
        playersSubsets.add(innerSubset);
        playersSubsets.sort(Comparator.comparingInt(pss -> pss.stream()
                                                              .mapToInt(Player::getSkillPoints)
                                                              .reduce(0, Math::addExact)));

        playersSubsets.get(0)
                      .forEach(p -> p.setTeamNumber(teams.get(1)
                                                         .getTeamNumber()));

        playersSubsets.get(1)
                      .forEach(p -> p.setTeamNumber(teams.get(0)
                                                         .getTeamNumber()));

        Positions currentPosition = ps.get(0)
                                      .getPosition();

        teams.get(0)
             .getPlayers()
             .get(currentPosition)
             .addAll(playersSubsets.get(1));

        teams.get(1)
             .getPlayers()
             .get(currentPosition)
             .addAll(playersSubsets.get(0));
      } else {
        for (Player p : ps) {
          teams.sort(Comparator.comparingInt(Team::getTeamSkill));

          int teamNumber = 0;

          if (teams.get(teamNumber)
                   .isPositionFull(p.getPosition())
              || teams.get(teamNumber)
                      .getPlayersCount() + 1 > Main.PLAYERS_PER_TEAM) {
            teamNumber = 1;
          }

          p.setTeamNumber(teamNumber + 1);

          teams.get(teamNumber)
               .getPlayers()
               .get(p.getPosition())
               .add(p);
        }
      }
    }

    return teams;
  }
}