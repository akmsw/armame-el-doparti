package armameeldoparti.utils;

import armameeldoparti.Main;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 * Common-use functions class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 18/10/2022
 */
public class CommonFunctions {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Empty private constructor. Not needed.
   */
  private CommonFunctions() {
    // Not needed.
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Exits the program with the corresponding error message
   * and error code according to the occurred exception.
   *
   * @param e The error that caused the program to end.
   */
  public static final void exitProgram(Error e) {
    showErrorMessage(Constants.errorMessages
                              .get(e));

    System.exit(Constants.errorCodes
                         .get(e));
  }

  /**
   * Builds an error window with a custom message.
   *
   * @param errorMessage Custom error message to show.
   */
  public static final void showErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(null, errorMessage, Constants.ERROR_MESSAGE_TITLE,
                                  JOptionPane.ERROR_MESSAGE, null);
  }

  /**
   * Gets a list containing the anchored players
   * grouped by their anchorage number.
   *
   * @return A list containing the anchored players
   *         grouped by their anchorage number.
  */
  public static final List<List<Player>> getAnchoredPlayers() {
    return Main.getPlayersSets()
               .values()
               .stream()
               .flatMap(List::stream)
               .filter(Player::isAnchored)
               .collect(Collectors.groupingBy(Player::getAnchorageNumber))
               .values()
               .stream()
               .collect(Collectors.toList());
  }

  /**
   * Gets the valueToSearch corresponding position in a generic map received.
   *
   * @param map    Generic map with positions as keys.
   * @param search Value to search in the map.
   *
   * @return The value-to-search corresponding position.
   */
  public static final <T> Position getCorrespondingPosition(Map<Position, T> map, T search) {
    return map.entrySet()
              .stream()
              .filter(e -> e.getValue()
                            .equals(search))
              .map(Map.Entry::getKey)
              .collect(Collectors.toList())
              .get(0);
  }
}