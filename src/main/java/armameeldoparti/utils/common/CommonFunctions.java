package armameeldoparti.utils.common;

import armameeldoparti.controllers.Controller;
import armameeldoparti.models.Error;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import armameeldoparti.views.View;
import java.awt.Color;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

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
    // Body not needed
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Exits the program with the corresponding error message
   * and error code according to the occurred exception.
   *
   * @param error The error that caused the program to end.
   */
  public static void exitProgram(Error error) {
    showErrorMessage(Constants.MAP_ERROR_MESSAGE
                              .get(error),
                     null);

    System.exit(Constants.MAP_ERROR_CODE
                         .get(error));
  }

  /**
   * Builds an error window with a custom message.
   *
   * @param errorMessage    Custom error message to show.
   * @param parentComponent Graphical component where the dialogs associated with the event should
   *                        be displayed.
   */
  public static void showErrorMessage(String errorMessage, Component parentComponent) {
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
  public static void updateActiveMonitorFromView(View view) {
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
   * Capitalizes the first letter of the given string if it's not empty.
   *
   * @param input The string to capitalize.
   *
   * @return The given string with the first letter uppercase and the rest lowercase.
   */
  public static String capitalize(String input) {
    return input.isEmpty() ? input
                           : input.substring(0, 1)
                                  .toUpperCase()
                             + input.substring(1)
                                    .toLowerCase();
  }

  /**
   * Given an image filename, creates an ImageIcon with it.
   *
   * @param imageFileName Name of the image file.
   *
   * @return The ImageIcon of the specified file.
   */
  public static ImageIcon createImage(String imageFileName) {
    return Objects.requireNonNull(
      new ImageIcon(Constants.class
                             .getClassLoader()
                             .getResource(Constants.PATH_IMG + imageFileName))
    );
  }

  /**
   * Given an icon filename, appends the icons folder path to it and creates an ImageIcon.
   *
   * @see #createImage(String)
   *
   * @param iconFileName Name of the icon file to use.
   *
   * @return The ImageIcon of the specified file.
   */
  public static ImageIcon createImageIcon(String iconFileName) {
    return createImage(Constants.PATH_ICO + iconFileName);
  }

  /**
   * Scales an icon to the specified width and height.
   *
   * @param icon   Icon to scale.
   * @param width  New width.
   * @param height New height.
   * @param hints  Scaling method.
   *
   * @return The scaled icon.
   */
  public static ImageIcon scaleImageIcon(ImageIcon icon, int width, int height, int hints) {
    return new ImageIcon(icon.getImage()
                             .getScaledInstance(width, height, hints));
  }

  /**
   * Gets the corresponding controller to the requested view.
   *
   * <p>The "java:S1452" warning is suppressed since the Java compiler can't know at runtime
   * the type of the view controlled.
   *
   * <p>TODO: Avoid warning suppression.
   *
   * @param view The view whose controller is needed.
   *
   * @return The requested view's controller.
   */
  @SuppressWarnings("java:S1452")
  public static Controller<? extends View> getController(ProgramView view) {
    return CommonFields.getControllersMap()
                       .get(view);
  }

  /**
   * Gets a list containing the anchored players grouped by their anchorage number.
   *
   * @return A list containing the anchored players grouped by their anchorage number.
  */
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
   * @return The optional value if present.
   */
  public static <T> T retrieveOptional(Optional<T> optional) {
    if (!optional.isPresent()) {
      exitProgram(Error.FATAL_INTERNAL_ERROR);
    }

    return optional.get();
  }

  /**
   * Builds a custom arrow button that fits the program aesthetics based on the parent UI component.
   *
   * <p>The arrow will be light green, the shadows will be medium green and the button will have a
   * raised bevel border. If its parent UI component is a BasicComboBoxUI, the button background
   * will be dark green. Otherwise, it will be medium green.
   *
   *
   * @param <T>         Generic parent UI component type.
   * @param orientation The button arrow orientation.
   * @param parentComp  Parent UI component.
   *
   * @return The custom arrow button.
   */
  public static <T> JButton buildCustomArrowButton(int orientation, T parentComp) {
    Color backgroundColor = parentComp instanceof BasicComboBoxUI ? Constants.COLOR_GREEN_DARK
                                                                  : Constants.COLOR_GREEN_MEDIUM;

    JButton arrowButton = new BasicArrowButton(orientation,
                                               backgroundColor,
                                               Constants.COLOR_GREEN_MEDIUM,
                                               Constants.COLOR_GREEN_LIGHT,
                                               Constants.COLOR_GREEN_MEDIUM);

    arrowButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
                                                          Constants.COLOR_GREEN_MEDIUM,
                                                          Constants.COLOR_GREEN_DARK));

    return arrowButton;
  }

  /**
   * Gets the search-corresponding position in a generic map received.
   *
   * @param <T>    Generic value type.
   * @param map    Generic map with positions as keys.
   * @param search Value to search in the map.
   *
   * @return The search-corresponding position.
   */
  public static <T> Position getCorrespondingPosition(Map<Position, T> map, T search) {
    return retrieveOptional(map.entrySet()
                               .stream()
                               .filter(e -> e.getValue()
                                             .equals(search))
                               .map(Map.Entry::getKey)
                               .findFirst());
  }
}