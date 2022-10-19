package armameeldoparti.utils;

import armameeldoparti.models.Positions;
import java.util.Map;
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
   * Builds an error window with a custom message.
   *
   * @param errorMessage Custom error message to show.
   */
  public static final void showErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(null, errorMessage, Constants.ERROR_MESSAGE_TITLE,
                                  JOptionPane.ERROR_MESSAGE, null);
  }

  /**
   * Gets the valueToSearch corresponding position in a generic map received.
   *
   * @param map    Generic map with positions as keys.
   * @param search Value to search in the map.
   *
   * @return The value-to-search corresponding position.
   */
  public static final <T> Positions getCorrespondingPosition(Map<Positions, T> map, T search) {
    return (Positions) map.entrySet()
                          .stream()
                          .filter(e -> e.getValue()
                                        .equals(search))
                          .map(Map.Entry::getKey)
                          .toArray()[0];
  }

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
}