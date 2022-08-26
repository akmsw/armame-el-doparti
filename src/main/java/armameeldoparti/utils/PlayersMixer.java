package armameeldoparti.utils;

import armameeldoparti.Main;
import armameeldoparti.models.Player;
import armameeldoparti.models.Team;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface that specifies the players distribution methods.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 13/07/2022
 */
public interface PlayersMixer {

  // ---------------------------------------- Public abstract methods ---------------------------

  /**
   * Distributes the players without considering anchorages.
   *
   * @param teams List that contains the two teams.
   *
   * @return The updated teams with the players distributed
   *         without considering anchorages.
   */
  List<Team> withoutAnchorages(List<Team> teams);

  /**
   * Distributes the players considering anchorages.
   *
   * @param teams List that contains the two teams.
   *
   * @return The updated teams with the players distributed
   *         considering anchorages.
   */
  List<Team> withAnchorages(List<Team> teams);

  // ---------------------------------------- Public static methods -----------------------------

  /**
   * Gets a list containing the anchored players
   * grouped by their anchorage number.
   *
   * @return A list containing the anchored players
   *         grouped by their anchorage number.
  */
  static List<List<Player>> getAnchoredPlayers() {
    return Main.getPlayersSets()
               .values()
               .stream()
               .flatMap(List::stream)
               .filter(Player::isAnchored)
               .collect(
                 Collectors.groupingBy(Player::getAnchorageNumber))
               .values()
               .stream()
               .collect(Collectors.toList());
  }
}