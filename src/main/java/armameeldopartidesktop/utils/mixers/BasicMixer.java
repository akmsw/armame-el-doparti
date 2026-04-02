package armameeldopartidesktop.utils.mixers;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import armameeldopartidesktop.models.Anchorage;
import armameeldopartidesktop.models.Player;
import armameeldopartidesktop.models.Team;
import armameeldopartidesktop.models.enums.Position;
import armameeldopartidesktop.utils.common.CommonFields;
import armameeldopartidesktop.utils.common.Constants;

/**
 * Abstract players mixer that provides basic distribution utilities.
 *
 * @since 3.0.0
 *
 * @version 1.0.1
 *
 * @author Bonino, Francisco Ignacio.
 */
public abstract class BasicMixer implements Mixer {

  // ---------- Protected fields --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  protected int randomTeam1;
  protected int randomTeam2;

  protected Random randomGenerator;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds an abstract basic players distributor.
   */
  protected BasicMixer() {
    randomGenerator = new Random();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Randomly shuffles the team numbers.
   *
   * @param range Upper limit (exclusive) for the random number generator.
   */
  protected void shuffleTeamNumbers(int range) {
    randomTeam1 = randomGenerator.nextInt(range);
    randomTeam2 = 1 - randomTeam1;
  }

    /**
   * @param team   Team where the player should be added.
   * @param player The players to add.
   *
   * @return Whether a player can be added to the specified team.
   */
  protected boolean playerCanBeAdded(Team team, Player player) {
    return !team.isPositionFull(player.getPosition());
  }

  /**
   * Checks if a set of anchored players can be added to a team.
   *
   * <p>First, checks if any of the positions of the anchored players in the destination team is already complete. If not, checks if adding them does not exceed the number of players allowed per position per team.
   *
   * <p>This is done in order to avoid more than half of the registered players of the same position remaining on the same team.
   *
   * @param team      Team where the anchored players should be added.
   * @param anchorage List containing the players with the same anchorage number.
   *
   * @return Whether a set of anchored players can be added to a team.
   */
  protected boolean anchorageCanBeAdded(Team team, Anchorage anchorage) {
    return !(anchorageOverflowsTeamSize(team, anchorage) || anchorageOverflowsAnyPositionSet(team, anchorage));
  }

  /**
   * Checks which team a given player can be added to.
   *
   * @param teams               The possible teams where to add the player.
   * @param validationPredicate The predicate that will validate if the player can be added to a team, or not.
   *
   * @return The only available team ID, a random team ID if the player can be added in every team, or -1 if there's no available team for the player.
   */
  protected int getAvailableTeamId(List<Team> teams, Predicate<Team> validationPredicate) {
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

    return Constants.ERROR_CODE_NO_AVAILABLE_TEAM;
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @param team      Team to check if the anchored players can be added.
   * @param anchorage Anchored players to check.
   *
   * @return Whether the number of anchored players to be added to a team would exceed the limit of players per team.
   */
  private boolean anchorageOverflowsTeamSize(Team team, Anchorage anchorage) {
    return team.getPlayersCount() + anchorage.getPlayers().size() > Constants.PLAYERS_PER_TEAM;
  }

  /**
   * @param team      Team to check if the anchored players can be added.
   * @param anchorage Anchored players to check.
   *
   * @return Whether the number of anchored players to be added to a team would exceed the limit of players per team in any position set.
   */
  private boolean anchorageOverflowsAnyPositionSet(Team team, Anchorage anchorage) {
    return anchorage.getPlayers().stream().anyMatch(player -> team.isPositionFull(player.getPosition()) || anchorageOverflowsPositionSet(team, anchorage, player.getPosition()));
  }

  /**
   * @param team      Team to check if the anchored players can be added.
   * @param anchorage Anchored players to check.
   * @param position  Anchored players position.
   *
   * @return Whether the number of anchored players to be added to a position set in a team would exceed the limit of players per team for that
   *         particular position.
   */
  private boolean anchorageOverflowsPositionSet(Team team, Anchorage anchorage, Position position) {
    return (team.getPlayers().get(position).size() + anchorage.getPlayers().stream().filter(player -> player.getPosition() == position).count()) > CommonFields.getPlayerLimitPerPosition().get(position);
  }
}