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
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import lombok.NonNull;

/**
 * Common-use functions class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public final class CommonFunctions {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Empty, private constructor. Not needed.
   */
  private CommonFunctions() {
    // Not needed
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Exits the program with the corresponding error message
   * and error code according to the occurred exception.
   *
   * @param e The error that caused the program to end.
   */
  public static void exitProgram(Error e) {
    showErrorMessage(Constants.MAP_ERROR_MESSAGE
                              .get(e),
                     null);

    System.exit(Constants.MAP_ERROR_CODE
                         .get(e));
  }

  /**
   * Builds an error window with a custom message.
   *
   * @param errorMessage    Custom error message to show.
   * @param parentComponent Graphical component where the dialogs associated with the event should
   *                        be displayed.
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
        retrieveOptional(Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment()
                                                          .getScreenDevices())
                               .filter(s -> s.getDefaultConfiguration()
                                             .getBounds()
                                             .contains(view.getLocation()))
                               .findFirst())
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
   * Builds a custom arrow button that fits the program aesthetics.
   *
   * <p>The button and the shadows will be medium green. The arrow will be light green.
   *
   * @param orientation The button arrow orientation.
   *
   * @return The custom arrow button.
   */
  public static JButton buildArrowButton(int orientation) {
    JButton arrowButton = new BasicArrowButton(orientation,
                                               Constants.GREEN_MEDIUM,
                                               Constants.GREEN_MEDIUM,
                                               Constants.GREEN_LIGHT,
                                               Constants.GREEN_MEDIUM);

    arrowButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
                                                          Constants.GREEN_MEDIUM,
                                                          Constants.GREEN_DARK));

    return arrowButton;
  }

  /**
   * Builds a string that represents the MiG layout constraints for the graphical component.
   *
   * @param constraints MiG Layout constraints for the component.
   *
   * @return The fully built component constraints.
   */
  public static String buildMigLayoutConstraints(String... constraints) {
    return String.join(", ", constraints);
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

  /**
   * Gets a list containing the anchored players grouped by their anchorage number.
   *
   * @return A list containing the anchored players grouped by their anchorage number.
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
   * Checks if an optional that should not be null has a value present. If so, that value is
   * retrieved. If the optional has no value, then the program exits with a fatal internal error
   * code.
   *
   * @param <T>      Generic optional type.
   * @param optional The optional to be checked.
   *
   * @return The optional value if it's present.
   */
  @NonNull
  public static <T> T retrieveOptional(Optional<T> optional) {
    if (!optional.isPresent()) {
      exitProgram(Error.FATAL_INTERNAL_ERROR);
    }

    return optional.get();
  }

  /**
   * Gets the search-corresponding position in a generic map received.
   *
   * @param map    Generic map with positions as keys.
   * @param search Value to search in the map.
   *
   * @return The search-corresponding position.
   */
  @NonNull
  public static <T> Position getCorrespondingPosition(@NonNull Map<Position, T> map,
                                                      @NonNull T search) {
    return retrieveOptional(map.entrySet()
                               .stream()
                               .filter(e -> e.getValue()
                                             .equals(search))
                               .map(Map.Entry::getKey)
                               .findFirst());
  }
}