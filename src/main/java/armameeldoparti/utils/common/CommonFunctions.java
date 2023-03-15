package armameeldoparti.utils.common;

import armameeldoparti.controllers.Controller;
import armameeldoparti.models.Error;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import armameeldoparti.views.View;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import lombok.NonNull;

/**
 * Common-use functions class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 18/10/2022
 */
public final class CommonFunctions {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Empty, private constructor. Not needed.
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
  public static void exitProgram(Error e) {
    showErrorMessage(Constants.errorMessages
                              .get(e),
                     null);

    System.exit(Constants.errorCodes
                         .get(e));
  }

  /**
   * Builds an error window with a custom message.
   *
   * @param errorMessage Custom error message to show.
   * @param parentComponent Graphical component where the dialogs associated
 *                          with the event should be displayed.
   */
  public static void showErrorMessage(@NonNull String errorMessage, Component parentComponent) {
    JOptionPane.showMessageDialog(
        parentComponent, errorMessage,
        Constants.ERROR_MESSAGE_TITLE,
        JOptionPane.ERROR_MESSAGE, null
    );
  }

  /**
   * Establishes the monitor where the given view is displayed, as the active monitor.
   *
   * @param view Reference view from which the active monitor will be retrieved.
   */
  public static void updateActiveMonitorFromView(@NonNull View view) {
    CommonFields.setActiveMonitor(
        Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment()
                                         .getScreenDevices())
              .filter(s -> s.getDefaultConfiguration()
                            .getBounds()
                            .contains(view.getLocation()))
              .collect(Collectors.toList())
              .get(0)
    );
  }

  /**
   * Given an action event, this method returns the graphical component associated with it.
   *
   * @param e The triggered action event.
   *
   * @return The graphical component associated to the action event.
   */
  public static Component getComponentFromEvent(ActionEvent e) {
    return e == null ? null : SwingUtilities.windowForComponent((Component) e.getSource());
  }

  /**
   * Gets a list containing the anchored players
   * grouped by their anchorage number.
   *
   * @return A list containing the anchored players
   *         grouped by their anchorage number.
  */
  @NonNull
  public static List<List<Player>> getAnchoredPlayers() {
    return new ArrayList<>(CommonFields.getPlayersSets()
                                       .values()
                                       .stream()
                                       .flatMap(List::stream)
                                       .filter(Player::isAnchored)
                                       .collect(Collectors.groupingBy(Player::getAnchorageNumber))
                                       .values());
  }

  /**
   * Gets the valueToSearch corresponding position in a generic map received.
   *
   * @param map    Generic map with positions as keys.
   * @param search Value to search in the map.
   *
   * @return The value-to-search corresponding position.
   */
  @NonNull
  public static <T> Position getCorrespondingPosition(@NonNull Map<Position, T> map,
                                                      @NonNull T search) {
    return map.entrySet()
              .stream()
              .filter(e -> e.getValue()
                            .equals(search))
              .map(Map.Entry::getKey)
              .collect(Collectors.toList())
              .get(0);
  }

  /**
   * Gets the corresponding controller to the requested view.
   *
   * @param view The view whose controller is needed.
   *
   * @return The requested view's controller.
  */
  @NonNull
  public static Controller getController(@NonNull ProgramView view) {
    return CommonFields.getControllersMap()
                       .get(view);
  }
}