package armameeldoparti.utils.mixers;

import armameeldoparti.models.Error;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.Team;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

/**
 * Random distribution class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class RandomMixer implements PlayersMixer {

  // ---------------------------------------------------------------- Private fields -----------------------------------------------------------------

  private int randomTeam1;
  private int randomTeam2;

  private Random randomGenerator;

  // --------------------------------------------------------------- Constructor ---------------------------------------------------------------------

  /**
   * Builds the random distributor.
   */
  public RandomMixer() {
    randomGenerator = new Random();
  }

  // ---------------------------------------------------------------- Public methods -----------------------------------------------------------------

  /**
   * Distributes the players randomly without considering anchorages.
   *
   * <p>Half of the players of each players-set are randomly assigned a team number. The rest of the players are assigned to the opposing team number.
   *
   * @param teams Teams where to distribute the players.
   *
   * @return The updated teams with the players distributed randomly without considering anchorages.
   */
  @Override
  public List<Team> withoutAnchorages(List<Team> teams) {
    shuffleTeamNumbers(teams.size());

    for (Position position : Position.values()) {
      List<Player> playersAtPosition = new ArrayList<>(CommonFields.getPlayersSets()
                                                                   .get(position));

      Collections.shuffle(playersAtPosition);

      teams.get(randomTeam1)
           .getTeamPlayers()
           .get(position)
           .addAll(playersAtPosition.subList(0, playersAtPosition.size() / teams.size()));
    }

    teams.get(randomTeam1)
         .getTeamPlayers()
         .values()
         .stream()
         .flatMap(List::stream)
         .forEach(player -> player.setTeamNumber(randomTeam1 + 1));

    CommonFields.getPlayersSets()
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(player -> player.getTeamNumber() == 0)
                .forEach(player -> {
                  teams.get(randomTeam2)
                       .getTeamPlayers()
                       .get(player.getPosition())
                       .add(player);

                  player.setTeamNumber(randomTeam2 + 1);
                });

    return teams;
  }

  /**
   * Distributes the players randomly considering anchorages.
   *
   * <p>First, the anchored players are grouped in different lists by their anchorage number, and they are distributed randomly starting with the sets
   * with most anchored players in order to avoid inconsistencies. If a set of anchored players cannot be added to one team, it will be added to the
   * other. Then, the players that are not anchored are distributed randomly. They will be added to a team only if the players per position or the
   * players per team amounts are not exceeded.
   *
   * @param teams Teams where to distribute the players.
   *
   * @return The updated teams with the players distributed randomly considering anchorages.
   */
  @Override
  public List<Team> withAnchorages(List<Team> teams) {
    for (List<Player> anchorage : CommonFunctions.getAnchoredPlayers()) {
      int teamNumber = getAvailableTeam(teams, team -> anchorageCanBeAdded(team, anchorage));

      if (teamNumber == -1) {
        CommonFunctions.exitProgram(Error.ERROR_INTERNAL);
      }

      for (Player player : anchorage) {
        player.setTeamNumber(teamNumber + 1);

        teams.get(teamNumber)
             .getTeamPlayers()
             .get(player.getPosition())
             .add(player);
      }
    }

    // Remaining (not anchored) players without an assigned team
    CommonFields.getPlayersSets()
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(player -> player.getTeamNumber() == 0)
                .forEach(player -> {
                  int teamNumber = getAvailableTeam(teams, team -> playerCanBeAdded(team, player));

                  if (teamNumber == -1) {
                    CommonFunctions.exitProgram(Error.ERROR_INTERNAL);
                  }

                  player.setTeamNumber(teamNumber + 1);

                  teams.get(teamNumber)
                       .getTeamPlayers()
                       .get(player.getPosition())
                       .add(player);
                });

    return teams;
  }

  // ---------------------------------------------------------------- Private methods ----------------------------------------------------------------

  /**
   * Checks which team a given player can be added to.
   *
   * @param teams               The possible teams where to add the player.
   * @param validationPredicate The predicate that will validate if the player can be added to a team, or not.
   *
   * @return The only available team index, a random team index if the player can be added in every team, or -1 if there's no available team for the
   *         player.
   */
  private int getAvailableTeam(List<Team> teams, Predicate<Team> validationPredicate) {
    shuffleTeamNumbers(teams.size());

    boolean isRandomTeam1Available = validationPredicate.test(teams.get(randomTeam1));
    boolean isRandomTeam2Available = validationPredicate.test(teams.get(randomTeam2));

    if (isRandomTeam1Available && isRandomTeam2Available) {
      return randomGenerator.nextInt(teams.size());
    }

    if (isRandomTeam1Available) {
      return randomTeam1;
    }

    if (isRandomTeam2Available) {
      return randomTeam2;
    }

    return -1;
  }

  /**
   * Randomly shuffles the team numbers.
   *
   * @param range Upper limit (exclusive) for the random number generator.
   */
  private void shuffleTeamNumbers(int range) {
    randomTeam1 = randomGenerator.nextInt(range);
    randomTeam2 = 1 - randomTeam1;
  }

  /**
   * Checks if the given player position in the given team is already complete and, therefore, if the player can be added to it.
   *
   * @param team   Team where the player should be added.
   * @param player The players to add.
   *
   * @return Whether a player can be added to the specified team.
   */
  private boolean playerCanBeAdded(Team team, Player player) {
    return !team.isPositionFull(player.getPosition());
  }

  /**
   * Checks if a set of anchored players can be added to a team.
   *
   * <p>First, checks if any of the positions of the anchored players in the destination team is already complete. If not, checks if adding them does
   * not exceed the number of players allowed per position per team. This is done in order to avoid more than half of the registered players of the
   * same position remaining on the same team.
   *
   * @param team            Team where the anchored players should be added.
   * @param anchoredPlayers List containing the players with the same anchorage number.
   *
   * @return Whether a set of anchored players can be added to a team.
   */
  private boolean anchorageCanBeAdded(Team team, List<Player> anchoredPlayers) {
    return !(anchorageOverflowsTeamSize(team, anchoredPlayers) || anchorageOverflowsAnyPositionSet(team, anchoredPlayers));
  }

  /**
   * Checks if the amount of anchored players to be added to a team would exceed the maximum allowed amount of players per team.
   *
   * @param team            Team to check if the anchored players can be added.
   * @param anchoredPlayers Anchored players to check.
   *
   * @return Whether the amount of anchored players to be added to a team would exceed the maximum allowed amount of players per team.
   */
  private boolean anchorageOverflowsTeamSize(Team team, List<Player> anchoredPlayers) {
    return team.getPlayersCount() + anchoredPlayers.size() > Constants.PLAYERS_PER_TEAM;
  }

  /**
   * Checks if the amount of anchored players to be added to a team would exceed the maximum allowed amount of players per team in any position set.
   *
   * @param team            Team to check if the anchored players can be added.
   * @param anchoredPlayers Anchored players to check.
   *
   * @return Whether the amount of anchored players to be added to a team would exceed the maximum allowed amount of players per team in any position
   *         set.
   */
  private boolean anchorageOverflowsAnyPositionSet(Team team, List<Player> anchoredPlayers) {
    return anchoredPlayers.stream()
                          .anyMatch(player -> team.isPositionFull(player.getPosition())
                                              || anchorageOverflowsPositionSet(team, anchoredPlayers, player.getPosition()));
  }

  /**
   * Checks if the amount of anchored players to be added to a position set in a team would exceed the maximum allowed amount of players per team for
   * that particular position.
   *
   * @param team            Team to check if the anchored players can be added.
   * @param anchoredPlayers Anchored players to check.
   * @param position        Anchored players position.
   *
   * @return Whether the amount of anchored players to be added to a position set in a team would exceed the maximum allowed amount of players per
   *         team for that particular position.
   */
  private boolean anchorageOverflowsPositionSet(Team team, List<Player> anchoredPlayers, Position position) {
    return team.getTeamPlayers()
               .get(position)
               .size()
           + anchoredPlayers.stream()
                            .filter(player -> player.getPosition() == position)
                            .count()
           > CommonFields.getPlayersAmountMap()
                         .get(position);
  }
}