package armameeldopartidesktop.utils.mixers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
 * Random distribution class.
 *
 * @since 3.0.0
 *
 * @version 1.1.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class RandomMixer extends BasicMixer {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the random distributor.
   */
  public RandomMixer() {
    // Body not needed
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Distributes the players randomly without considering anchorages.
   *
   * <p>Half of the players of each players-set are randomly assigned to a team. The rest of the players are assigned to the opposing team.
   *
   * @param teams Teams where to distribute the players.
   *
   * @return The updated teams with the players distributed randomly without considering anchorages.
   */
  @Override
  public List<Team> withoutAnchorages(List<Team> teams) {
    shuffleTeamNumbers();

    for (Position position : Position.values()) {
      List<Player> playersAtPosition = new ArrayList<>(CommonFields.getPlayersSets().get(position));

      int halfSetSize = playersAtPosition.size() / Constants.TEAMS_TOTAL;

      Collections.shuffle(playersAtPosition);

      teams.get(randomTeamNumbers.get(0))
           .getPlayers()
           .get(position)
           .addAll(playersAtPosition.subList(0, halfSetSize));

      teams.get(randomTeamNumbers.get(1))
           .getPlayers()
           .get(position)
           .addAll(playersAtPosition.subList(halfSetSize, playersAtPosition.size()));
    }

    return teams;
  }

  /**
   * Distributes the players randomly considering anchorages.
   *
   * <p>First, the anchored players are grouped in different lists by their anchorage number, and they are distributed randomly. If a set of anchored players cannot be added to one team, it will be added to the
   * other. Then, the players that are not anchored are distributed randomly. They will be added to a team only if the players per position or the players per team limits are not exceeded.
   *
   * <p>At this point, the anchorages are guaranteed to be possible to distribute by {@link armameeldopartidesktop.controllers.AnchoragesController}, though there are cases where the order in which the anchorages are
   * distributed may affect the availability of teams for the following anchorages. To consider this, a boolean variable is used: if there's no room in any team for certain anchorage, then this variable is used to
   * stop the anchorages distribution, shuffle them and start the distribution again.
   *
   * @param teams Teams where to distribute the players.
   *
   * @return The updated teams with the players distributed randomly considering anchorages.
   */
  @Override
  public List<Team> withAnchorages(List<Team> teams) {
    boolean successfulDistribution = false;

    List<Anchorage> anchorages = CommonFields.getAnchorages();

    while (!successfulDistribution) {
      Collections.shuffle(anchorages);

      for (Anchorage anchorage : anchorages) {
        int availableTeamNumber = getAvailableTeamNumber(teams, team -> anchorageCanBeAdded(team, anchorage));

        if (availableTeamNumber == Constants.ERROR_CODE_NO_AVAILABLE_TEAM) {
          teams.forEach(Team::clear);

          successfulDistribution = false;

          break;
        }

        for (Player player : anchorage.getPlayers()) {
          teams.get(availableTeamNumber)
               .getPlayers()
               .get(player.getPosition())
               .add(player);
        }

        successfulDistribution = true;
      }
    }

    // Remaining (not anchored) players without an assigned team
    CommonFields.getPlayersSets()
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(player -> !teams.stream()
                                        .flatMap(team -> team.getPlayers().values().stream())
                                        .flatMap(List::stream)
                                        .collect(Collectors.toSet())
                                        .contains(player))
                .forEach(
                  player -> {
                    int availableTeamNumber = getAvailableTeamNumber(teams, team -> playerCanBeAdded(team, player));

                    // If there's no available team at this point, something went wrong
                    if (availableTeamNumber == Constants.ERROR_CODE_NO_AVAILABLE_TEAM) {
                      CommonFunctions.exitProgram(Error.ERROR_INTERNAL, new IllegalStateException(Constants.MSG_ERROR_DEBUG_NO_AVAILABLE_TEAM));
                    }

                    teams.get(availableTeamNumber)
                         .getPlayers()
                         .get(player.getPosition())
                         .add(player);
                  }
                );

    return teams;
  }
}