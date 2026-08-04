package armameeldopartidesktop.utils.mixers;

import java.util.ArrayList;
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

  protected Random randomGenerator;

  protected List<Integer> randomTeamNumbers;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds an abstract basic players distributor.
   */
  protected BasicMixer() {
    randomGenerator = new Random();
    randomTeamNumbers = new ArrayList<>();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Randomly shuffles the team indexes.
   */
  protected void shuffleTeamNumbers() {
    randomTeamNumbers.clear();
    randomTeamNumbers.add(randomGenerator.nextInt(Constants.TEAMS_TOTAL));
    randomTeamNumbers.add(1 - randomTeamNumbers.get(0));
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
   * @param validationPredicate The predicate that will validate whether the player can be added to a team.
   *
   * @return If the player can be added in both teams, a random team number. If not, the only available team number.
   *         If there's no available team, then {@code Constants.ERROR_CODE_NO_AVAILABLE_TEAM} is returned.
   */
  protected int getAvailableTeamNumber(List<Team> teams, Predicate<Team> validationPredicate) {
    shuffleTeamNumbers();

    boolean firstCandidateAvailable  = validationPredicate.test(teams.get(randomTeamNumbers.get(0)));
    boolean secondCandidateAvailable = validationPredicate.test(teams.get(randomTeamNumbers.get(1)));

    if (firstCandidateAvailable && secondCandidateAvailable) {
      return randomGenerator.nextInt(randomTeamNumbers.size());
    }

    if (firstCandidateAvailable) {
      return randomTeamNumbers.get(0);
    }

    if (secondCandidateAvailable) {
      return randomTeamNumbers.get(1);
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
    return ((team.getPlayersCount() + anchorage.getPlayers().size()) > Constants.PLAYERS_PER_TEAM);
  }

  /**
   * @param team      Team to check if the anchored players can be added.
   * @param anchorage Anchored players to check.
   *
   * @return Whether the number of anchored players to be added to a team would exceed the limit of players per team in any position set.
   */
  private boolean anchorageOverflowsAnyPositionSet(Team team, Anchorage anchorage) {
    return (anchorage.getPlayers().stream().anyMatch(player -> team.isPositionFull(player.getPosition()) || anchorageOverflowsPositionSet(team, anchorage, player.getPosition())));
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
    return ((team.getPlayers().get(position).size() + anchorage.getPlayers().stream().filter(player -> player.getPosition() == position).count()) > CommonFields.getPlayerLimitPerPosition().get(position));
  }
}