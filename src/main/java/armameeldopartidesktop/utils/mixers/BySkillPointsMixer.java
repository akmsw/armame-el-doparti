package armameeldopartidesktop.utils.mixers;

import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import armameeldopartidesktop.models.Anchorage;
import armameeldopartidesktop.models.Player;
import armameeldopartidesktop.models.Team;
import armameeldopartidesktop.models.enums.Error;
import armameeldopartidesktop.models.enums.Position;
import armameeldopartidesktop.utils.common.CommonFields;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;

/**
 * By-skill-points distribution class.
 *
 * @since 3.0.0
 *
 * @version 1.1.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class BySkillPointsMixer extends BasicMixer {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the by-skill-points players distributor.
   */
  public BySkillPointsMixer() {
    // Body not needed
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Distributes the players by their skill points without considering anchorages.
   *
   * <p>The players of each position are ordered based on their score, from highest to lowest. The teams are then ordered based on the sum of their players scores so far, from lowest to highest.
   *
   * <p>If the number of players to distribute is 2, the team with less skill points is assigned the player with the highest skill points, and the team with more skill points is assigned the player with the lowest
   * skill points.
   *
   * <p>If the number of players to distribute is 4, two subgroups are made with the players at the list ends, from the outside to the inside. These subsets are then ordered based on their skill points, from highest
   * to lowest. The team with less skill points is assigned the set of players with more skill points. The team with more skill points is assigned the set of players with the lowest skill points.
   *
   * @param teams Teams where to distribute the players.
   *
   * @return The updated teams with the players distributed by their skill points, without considering anchorages.
   */
  @Override
  public List<Team> withoutAnchorages(List<Team> teams) {
    Map<Position, List<Player>> playersMap = CommonFields.getPlayersSets();

    for (Position position : Position.values()) {
      List<Player> playersSet = new ArrayList<>(playersMap.get(position));

      playersSet.sort(comparingInt(Player::getSkillPoints).reversed()); // Players sorted highest to lowest
      teams.sort(comparingInt(Team::getTeamSkill));                     // Teams sorted lowest to highest

      if (playersSet.size() == 2) {
        for (int teamIdx = 0; teamIdx < Constants.TEAMS_TOTAL; teamIdx++) {
          teams.get(teamIdx)
               .getPlayers()
               .get(position)
               .add(playersSet.get(teamIdx));
        }
      } else {
        distributeSubsets(teams, playersSet, position);
      }
    }

    if (!CommonFunctions.teamsSkillPointsAreEqual(teams)) {
      checkPlayersSwap(teams);
    }

    return teams;
  }

  /**
   * Distributes the players by their skill points considering anchorages.
   *
   * <p>First, the anchored players are grouped in different lists by their anchorage number, and they are distributed as fair as possible starting with the sets with most anchored players in order to avoid
   * inconsistencies.
   *
   * <p>Then, the players that are not anchored are distributed between the teams as fair as possible based on their skill points. They will be added to a team only if the players per position or the players per
   * team limits are not exceeded.
   *
   * @param teams Teams where to distribute the players.
   *
   * @return The updated teams with the players distributed by their skill points, without considering anchorages.
   */
  @Override
  public List<Team> withAnchorages(List<Team> teams) {
    for (Anchorage anchorage : CommonFields.getAnchorages()) {
      teams.sort(comparingInt(Team::getTeamSkill));

      int availableTeamNumber = getAvailableTeamIdx(teams, team -> anchorageCanBeAdded(team, anchorage));

      /*
       * At this point, the anchorages are guaranteed to be possible to distribute by {@link armameeldoparti.controllers.AnchoragesController}. Therefore, if at this point we can't find any available team to add the
       * current anchorage, then something went wrong.
       */
      if (availableTeamNumber == Constants.ERROR_CODE_NO_AVAILABLE_TEAM) {
        CommonFunctions.exitProgram(Error.ERROR_INTERNAL, new IllegalStateException(Constants.MSG_ERROR_DEBUG_NO_AVAILABLE_TEAM));
      }

      for (Player player : anchorage.getPlayers()) {
        teams.get(availableTeamNumber)
             .getPlayers()
             .get(player.getPosition())
             .add(player);
      }
    }

    List<List<Player>> remainingPlayers = new ArrayList<>(CommonFields.getPlayersSets()
                                                                      .values()
                                                                      .stream()
                                                                      .flatMap(List::stream)
                                                                      .filter(player -> !teams.stream()
                                                                                              .flatMap(team -> team.getPlayers().values().stream())
                                                                                              .flatMap(List::stream)
                                                                                              .collect(Collectors.toSet())
                                                                                              .contains(player))
                                                                      .collect(Collectors.groupingBy(Player::getPosition))
                                                                      .values());

    remainingPlayers.sort(comparingInt(List::size));

    for (List<Player> players : remainingPlayers) {
      players.sort(comparingInt(Player::getSkillPoints).reversed());

      if (players.size() == 4) {
        teams.sort(comparingInt(Team::getTeamSkill));

        distributeSubsets(teams, players, players.get(0).getPosition());

        continue;
      }

      for (Player player : players) {
        teams.sort(comparingInt(Team::getTeamSkill));

        int teamNumber = 0;

        if (teams.get(teamNumber).isPositionFull(player.getPosition()) || (teams.get(teamNumber).getPlayersCount() + 1) > Constants.PLAYERS_PER_TEAM) {
          teamNumber = 1;
        }

        teams.get(teamNumber)
              .getPlayers()
              .get(player.getPosition())
              .add(player);
      }
    }

    if (!CommonFunctions.teamsSkillPointsAreEqual(teams)) {
      checkPlayersSwap(teams);
    }

    return teams;
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Performs the subsets distribution in sets with 4+ players as explained in {@link #withoutAnchorages(List)}.
   *
   * @param teams      Teams where to distribute the players, already sorted lowest to highest.
   * @param playersSet Current working players set, already sorted highest to lowest.
   * @param position   Current working players position.
   */
  private void distributeSubsets(List<Team> teams, List<Player> playersSet, Position position) {
    List<List<Player>> playersSubsets = new ArrayList<>();

    int halfSetSize = playersSet.size() / Constants.TEAMS_TOTAL;

    for (int playerIndex = 0; playerIndex < halfSetSize; playerIndex++) {
      playersSubsets.add(Arrays.asList(playersSet.get(playerIndex), playersSet.get(playersSet.size() - playerIndex - 1)));
    }

    // Subsets sorted lowest to highest
    playersSubsets.sort(comparingInt(playersSubset -> playersSubset.stream()
                                                                   .mapToInt(Player::getSkillPoints)
                                                                   .reduce(0, Math::addExact)));

    for (int teamIdx = 0; teamIdx < Constants.TEAMS_TOTAL; teamIdx++) {
      teams.get(teamIdx)
           .getPlayers()
           .get(position)
           .addAll(playersSubsets.get(teamIdx));
    }
  }

  /**
   * Checks if the players can be swapped between the teams to reduce the skill difference.
   *
   * <p>Starts by calculating the current skill difference between the teams. Then, iterates over the non-anchored players of each position, swapping them between the teams and recalculating the skill difference.
   *
   * <p>If the new skill difference is 0, the method returns (the best distribution has been found).
   *
   * <p>If the new skill difference is greater than or equal to the current skill difference, the swap is reverted.
   *
   * <p>If the new skill difference is less than the current skill difference, the swap is kept and it continues trying to find a better distribution.
   *
   * <p>The "java:S3776" warning is suppressed since this method is already as simple as possible while keeping a good performance.
   *
   * @param teams Teams where to check the players swaps.
   */
    @SuppressWarnings("java:S3776")
  private void checkPlayersSwap(List<Team> teams) {
    List<Player> anchoredPlayers = CommonFields.getAnchorages()
                                               .stream()
                                               .flatMap(anchorage -> anchorage.getPlayers().stream())
                                               .toList();

    int currentSkillDifference = CommonFunctions.getTeamsSkillDifference(teams);

    for (Position position : Position.values()) {
      List<Player> team1PlayersInPosition            = teams.get(0).getPlayers().get(position);
      List<Player> team1PlayersNonAnchoredInPosition = team1PlayersInPosition.stream().filter(player -> !anchoredPlayers.contains(player)).toList();

      List<Player> team2PlayersInPosition            = teams.get(1).getPlayers().get(position);
      List<Player> team2PlayersNonAnchoredInPosition = team2PlayersInPosition.stream().filter(player -> !anchoredPlayers.contains(player)).toList();

      if (team1PlayersNonAnchoredInPosition.isEmpty() || team2PlayersNonAnchoredInPosition.isEmpty()) {
        continue;
      }

      for (Player playerTeam1 : team1PlayersNonAnchoredInPosition) {
        int playerTeam1Index = team1PlayersInPosition.indexOf(playerTeam1);

        for (Player playerTeam2 : team2PlayersNonAnchoredInPosition) {
          int playerTeam2Index = team2PlayersInPosition.indexOf(playerTeam2);

          team1PlayersInPosition.set(playerTeam1Index, playerTeam2);
          team2PlayersInPosition.set(playerTeam2Index, playerTeam1);

          int newSkillDifference = CommonFunctions.getTeamsSkillDifference(teams);

          if (newSkillDifference == 0) {
            return;
          }

          if (newSkillDifference >= currentSkillDifference) {
            team1PlayersInPosition.set(playerTeam1Index, playerTeam1);
            team2PlayersInPosition.set(playerTeam2Index, playerTeam2);

            continue;
          }

          currentSkillDifference = newSkillDifference;
        }
      }
    }
  }
}