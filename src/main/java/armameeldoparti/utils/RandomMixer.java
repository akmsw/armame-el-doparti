package armameeldoparti.utils;

import armameeldoparti.interfaces.PlayersMixer;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Random distribution class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 12/07/2022
 */
public class RandomMixer implements PlayersMixer {

  // ---------------------------------------- Private fields ------------------------------------

  private int index;
  private int team1;
  private int team2;

  private Random randomGenerator;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the random distributor.
   */
  public RandomMixer() {
    randomGenerator = new Random();
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Distributes the players randomly without considering anchorages.
   *
   * <p>Half of the players of each players set are randomly assigned a team number.
   *
   * <p>The rest of the players in the group without team (team == 0) are assigned
   * the oppsing team number.
   *
   * @param teams List that contains the two teams.
   *
   * @return The updated teams with the players distributed randomly
   *         without considering anchorages.
   */
  @Override
  public List<Team> withoutAnchorages(List<Team> teams) {
    updateChosenTeams(teams.size());

    List<Integer> alreadySetted = new ArrayList<>();

    for (Position position : Position.values()) {
      List<Player> playersSet = Main.getPlayersSets()
                                    .get(position);

      for (int i = 0; i < playersSet.size() / 2; i++) {
        updateIndex(playersSet.size(), alreadySetted);

        alreadySetted.add(index);

        Player chosenPlayer = playersSet.get(index);

        chosenPlayer.setTeam(team1 + 1);

        teams.get(team1)
             .getPlayers()
             .get(chosenPlayer.getPosition())
             .add(chosenPlayer);
      }

      playersSet.stream()
                .filter(p -> p.getTeam() == 0)
                .forEach(p -> {
                  p.setTeam(team2 + 1);

                  teams.get(team2)
                       .getPlayers()
                       .get(p.getPosition())
                       .add(p);
                });

      alreadySetted.clear();
    }

    return teams;
  }

  /**
   * Distributes the players randomly considering anchorages.
   *
   * <p>A random number between 0 and 1 is chosen to assign a team to a set of players,
   * and the rest will be assigned the opposite team.
   *
   * <p>It begins by going through each position. As long as the position being worked on
   * does not have the number of players specified for that position per team,
   * it will continue to iterate.
   *
   * <p>A player is chosen randomly from the set of players with the selected position,
   * and it is checked if it is available (team == 0) and if it is anchored (anchorageNumber != 0).
   *
   * <p>If the player is available and is anchored with other players, the players of all positions
   * with the same anchorage number are taken and it is validated if they can be added to the team
   * without exceeding the number of players allowed for each position.
   * If it is possible, they're added. If not, they are ignored and the iteration continues.
   *
   * <p>If the player does not have an anchorage number and can be added without going
   * over the player limit for their position, it is added.
   *
   * <p>When the first chosen team is full, the iteration stops and all remaining players are
   * assigned the opposite team number.
   *
   * @param teams List that contains the two teams.
   *
   * @return The updated teams with the players distributed randomly considering anchorages.
   */
  @Override
  public List<Team> withAnchorages(List<Team> teams) {
    updateChosenTeams(teams.size());

    Team currentWorkingTeam = teams.get(team1);

    List<Integer> indexesSet = new ArrayList<>();

    boolean teamFull = false;

    for (int i = 0; i < Position.values()
                                .length && !teamFull; i++) {
      List<Player> playersSet = Main.getPlayersSets()
                                    .get(Position.values()[i]);

      while (currentWorkingTeam.getPlayers()
                               .get(Position.values()[i])
                               .size() < Main.getPlayersAmountMap()
                                             .get(Position.values()[i])
             && !teamFull) {
        updateIndex(playersSet.size(), indexesSet);

        Player player = playersSet.get(index);

        if (player.getTeam() != 0) {
          continue;
        }

        if (player.getAnchorageNumber() == 0
            && currentWorkingTeam.getPlayersCount() + 1 <= Main.PLAYERS_PER_TEAM) {
          player.setTeam(team1 + 1);
          currentWorkingTeam.getPlayers()
                            .get(player.getPosition())
                            .add(player);
          indexesSet.add(index);
          /*
           * Here we should update the teamFull flag and continue,
           * but it conflicts with sonarlint java:S135.
           */
        }

        List<Player> anchoredPlayers = Main.getPlayersSets()
                                           .values()
                                           .stream()
                                           .flatMap(List::stream)
                                           .filter(p -> p.getAnchorageNumber()
                                                        == player.getAnchorageNumber())
                                           .collect(Collectors.toList());

        if (validateAnchorage(currentWorkingTeam, anchoredPlayers)) {
          anchoredPlayers.forEach(p -> {
            p.setTeam(team1 + 1);
            currentWorkingTeam.getPlayers()
                              .get(p.getPosition())
                              .add(p);
          });
          indexesSet.add(index);
        }

        teamFull = currentWorkingTeam.getPlayersCount() == Main.PLAYERS_PER_TEAM;
      }

      indexesSet.clear();
    }

    Main.getPlayersSets()
        .values()
        .stream()
        .flatMap(List::stream)
        .filter(p -> p.getTeam() == 0)
        .forEach(p -> {
          p.setTeam(team2 + 1);
          teams.get(team2)
               .getPlayers()
               .get(p.getPosition())
               .add(p);
        });

    return teams;
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Randomly updates the player selection index.
   *
   * @param range      Upper limit (exclusive) for the random number generator.
   * @param indexesSet Set where to check if the generated index is already present.
   */
  private void updateIndex(int range, List<Integer> indexesSet) {
    do {
      index = randomGenerator.nextInt(range);
    } while (indexesSet.contains(index));
  }

  /**
   * Randomly updates the team numbers.
   *
   * @param range Upper limit (exclusive) for the random number generator.
   */
  private void updateChosenTeams(int range) {
    team1 = randomGenerator.nextInt(range);
    team2 = 1 - team1;
  }

  /**
   * Checks if all anchored players can be added to a team.
   *
   * <p>It checks if any player already has an assigned team or
   * if the position of any of the anchored players in the destination
   * team is already complete.
   *
   * <p>Finally, it checks if adding them does not exceed the number
   * of players allowed per position per team. This is done in order to
   * avoid more than half of the registered players of the same position
   * remaining on the same team.
   *
   * @param team            Team where the anchored players should be added.
   * @param anchoredPlayers List containing the players with the same anchorage number.
   *
   * @return Whether all anchored players can be added to the team or not.
   */
  private boolean validateAnchorage(Team team, List<Player> anchoredPlayers) {
    return team.getPlayersCount() + anchoredPlayers.size() <= Main.PLAYERS_PER_TEAM
           && anchoredPlayers.stream()
                             .noneMatch(p -> p.getTeam() != 0
                                             || team.isPositionFull(p.getPosition())
                                             || team.getPlayers()
                                                    .get(p.getPosition())
                                                    .size()
                                                + anchoredPlayers.stream()
                                                                 .filter(ap ->
                                                                   ap.getPosition()
                                                                   == p.getPosition())
                                                                 .count()
                                                                 > Main.getPlayersAmountMap()
                                                                       .get(p.getPosition()));
  }
}