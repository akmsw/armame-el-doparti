package armameeldopartidesktop.utils.common;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import armameeldopartidesktop.controllers.Controller;
import armameeldopartidesktop.models.Player;
import armameeldopartidesktop.models.Team;
import armameeldopartidesktop.models.enums.Error;
import armameeldopartidesktop.models.enums.Position;
import armameeldopartidesktop.models.enums.ProgramView;
import armameeldopartidesktop.views.View;

/**
 * Common-use functions class.
 *
 * @since 3.0.0
 *
 * @version 1.0.1
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CommonFunctions {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Empty, private constructor.
   */
  private CommonFunctions() {
    // Body not needed
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Generates an error report with the current overall program context and stack trace.
   *
   * @param error     The error type that caused the program to end.
   * @param exception The thrown exception. If null, a new {@code IllegalStateException} is created with the current thread stack trace.
   */
  public static void generateErrorReport(Error error, Exception exception) {
    if (exception == null) {
      exception = new IllegalStateException();

      exception.setStackTrace(Thread.currentThread().getStackTrace());
    }

    try (FileWriter dumpFile = new FileWriter(Constants.FILENAME_ERROR_REPORT)) {
      int playersCount = 0;

      dumpFile.write("-------------- ERROR REPORT --------------" + System.lineSeparator().repeat(2));
      dumpFile.write("Report time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)) + System.lineSeparator());
      dumpFile.write("Error type: " + error + System.lineSeparator());
      dumpFile.write("Distribution type: " + CommonFields.getDistribution() + System.lineSeparator());
      dumpFile.write("Anchorages enabled: " + CommonFields.isAnchoragesEnabled() + System.lineSeparator().repeat(2));
      dumpFile.write("Player limit per position:" + System.lineSeparator());
      dumpFile.write("\t" + CommonFields.getPlayerLimitPerPosition().entrySet().toString() + System.lineSeparator().repeat(2));
      dumpFile.write("Positions map:" + System.lineSeparator());
      dumpFile.write("\t" + Constants.MAP_POSITIONS.entrySet().toString() + System.lineSeparator().repeat(2));
      dumpFile.write("Controllers map:" + System.lineSeparator());
      dumpFile.write("\t" + CommonFields.getControllersMap().entrySet().toString() + System.lineSeparator().repeat(2));
      dumpFile.write("Players:" + System.lineSeparator().repeat(2));

      for (List<Player> playersSet : CommonFields.getPlayersSets().values()) {
        for (Player player : playersSet) {
          dumpFile.write("\t" + (++playersCount) + ":" + System.lineSeparator());
          dumpFile.write("\t\t" + player.toString());
        }
      }

      if (exception.getMessage() != null) {
        dumpFile.write(System.lineSeparator() + "Error message:" + System.lineSeparator());
        dumpFile.write("\t" + exception.getMessage());
      }

      dumpFile.write(System.lineSeparator() + "Stack trace:" + System.lineSeparator());

      for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
        dumpFile.write("\t" + stackTraceElement + System.lineSeparator());
      }

      dumpFile.write(System.lineSeparator() + "----------- END OF ERROR REPORT ----------");
    } catch (IOException _) {
      System.exit(Constants.MAP_ERROR_CODE.get(Error.ERROR_INTERNAL));
    }
  }

  /**
   * Exits the program with the corresponding error message and error code according to the occurred exception.
   *
   * @param error     The error that caused the program to end.
   * @param exception The exception thrown.
   */
  public static void exitProgram(Error error, Exception exception) {
    generateErrorReport(error, exception);
    showMessageDialog(null, Constants.MAP_ERROR_MESSAGE.get(error), JOptionPane.ERROR_MESSAGE);

    System.exit(Constants.MAP_ERROR_CODE.get(error));
  }

  /**
   * Builds and displays a dialog window with a custom message.
   *
   * @param parentComponent   Graphical component where the dialog windows associated with the event should be displayed.
   * @param dialogMessage     Custom message to show.
   * @param dialogMessageType Message severity.
   */
  public static void showMessageDialog(Component parentComponent, String dialogMessage, int dialogMessageType) {
    String dialogTitle = null;

    Icon dialogIcon = null;

    switch (dialogMessageType) {
      case JOptionPane.INFORMATION_MESSAGE, JOptionPane.PLAIN_MESSAGE -> {
        dialogTitle = Constants.TITLE_MESSAGE_INFORMATION;
        dialogIcon  = Constants.ICON_DIALOG_INFORMATION;
      }
      case JOptionPane.WARNING_MESSAGE -> {
        dialogTitle = Constants.TITLE_MESSAGE_WARNING;
        dialogIcon  = Constants.ICON_DIALOG_WARNING;
      }
      case JOptionPane.ERROR_MESSAGE -> {
        dialogTitle = Constants.TITLE_MESSAGE_ERROR;
        dialogIcon  = Constants.ICON_DIALOG_ERROR;
      }
      case JOptionPane.QUESTION_MESSAGE -> {
        dialogTitle = Constants.TITLE_MESSAGE_QUESTION;
        dialogIcon  = Constants.ICON_DIALOG_QUESTION;
      }
      default -> CommonFunctions.exitProgram(Error.ERROR_GUI, new IllegalStateException(Constants.MSG_ERROR_DEBUG_INVALID_DIALOG_TYPE));
    }

    JOptionPane.showMessageDialog(parentComponent, dialogMessage, dialogTitle, dialogMessageType, dialogIcon);
  }

  /**
   * Builds and displays a dialog window with options for the user to choose.
   *
   * @param parentComponent Graphical component where the dialog windows associated with the event should be displayed.
   * @param dialogMessage   Custom message to show.
   * @param dialogOptions   Options for the user to choose.
   *
   * @return The integer indicating the option chosen by the user.
   *
   * @see JOptionPane#showOptionDialog(Component, Object, String, int, int, Icon, Object[], Object)
   */
  public static int showOptionDialog(Component parentComponent, String dialogMessage, Object[] dialogOptions) {
    return JOptionPane.showOptionDialog(
      parentComponent,
      dialogMessage,
      Constants.TITLE_MESSAGE_QUESTION,
      JOptionPane.OK_CANCEL_OPTION,
      JOptionPane.QUESTION_MESSAGE,
      Constants.ICON_DIALOG_QUESTION,
      dialogOptions,
      dialogOptions[0]
    );
  }

  /**
   * Calculates the difference between the skill points of a given set of teams.
   *
   * @param teams Teams to calculate the skill difference.
   *
   * @return The difference between the skill points of the given teams.
   */
  public static int getTeamsSkillDifference(List<Team> teams) {
    return Math.abs(teams.get(0).getTeamSkill() - teams.get(1).getTeamSkill());
  }

  /**
   * Checks if the skill points of the given teams are equal.
   *
   * @param teams Teams to check if their skill points are equal.
   *
   * @return Whether the skill points of the given teams are equal.
   */
  public static boolean teamsSkillPointsAreEqual(List<Team> teams) {
    return getTeamsSkillDifference(teams) == 0;
  }

  /**
   * Determines the monitor on which the majority of the given view is displayed and sets it as the active monitor.
   *
   * @param view Reference view from which the active monitor will be determined.
   */
  public static void updateActiveMonitorFromView(View view) {
    CommonFields.setActiveMonitor(
      retrieveOptional(
        Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices())
              .filter(screen -> !screen.getDefaultConfiguration()
                                       .getBounds()
                                       .intersection(view.getBounds())
                                       .isEmpty())
              .max(Comparator.comparingDouble(
                  screen -> {
                    Rectangle intersection = screen.getDefaultConfiguration()
                                                   .getBounds()
                                                   .intersection(view.getBounds());

                    return intersection.getWidth() * intersection.getHeight();
                  }
                )
              )
      )
    );
  }

  /**
   * Opens a new tab in the default web browser with the specified URL.
   *
   * @param url Destination URL.
   */
  public static void browserRedirect(String url) {
    try {
      Desktop.getDesktop().browse(new URI(url));
    } catch (IOException | URISyntaxException exception) {
      CommonFunctions.exitProgram(Error.ERROR_BROWSER, exception);
    }
  }

  /**
   * Given an action event, this method returns the graphical component associated with it.
   *
   * @param event The triggered action event.
   *
   * @return The graphical component associated to the action event.
   */
  public static Component getComponentFromEvent(ActionEvent event) {
    return event == null ? null : SwingUtilities.windowForComponent((Component) event.getSource());
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
   * Capitalizes the first letter of the given string.
   *
   * @param input The string to capitalize.
   *
   * @return The given string with the first letter uppercase and the rest lowercase.
   */
  public static String capitalize(String input) {
    return input.isBlank() ? input : (input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase());
  }

  /**
   * Given an image filename, creates an ImageIcon with it.
   *
   * @param imageFileName Name of the image file.
   *
   * @return The ImageIcon of the specified file.
   */
  public static ImageIcon createImage(String imageFileName) {
    return Objects.requireNonNull(new ImageIcon(Constants.class.getClassLoader().getResource(Constants.PATH_IMG + imageFileName)));
  }

  /**
   * Given an icon filename, appends the icons folder path to it and creates an ImageIcon.
   *
   * @param iconFileName Name of the icon file to use.
   *
   * @return The ImageIcon of the specified file.
   *
   * @see #createImage(String)
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
    return new ImageIcon(icon.getImage().getScaledInstance(width, height, hints));
  }

  /**
   * Gets the corresponding controller to the requested view.
   *
   * <p>The "java:S1452" warning is suppressed since the Java compiler can't know at runtime the type of the controlled view.
   *
   * @param view The view whose controller is needed.
   *
   * @return The requested view's controller.
   */
  @SuppressWarnings("java:S1452")
  public static Controller<? extends View> getController(ProgramView view) {
    return CommonFields.getControllersMap().get(view);
  }

  /**
   * Gets a list containing the anchored players grouped by their anchorage number.
   *
   * @return A list containing the anchored players grouped by their anchorage number.
  */
  public static List<List<Player>> getAnchorages() {
    return new ArrayList<>(CommonFields.getPlayersSets()
                                       .values()
                                       .stream()
                                       .flatMap(List::stream)
                                       .filter(Player::isAnchored)
                                       .collect(Collectors.groupingBy(Player::getAnchorageId))
                                       .values());
  }

  /**
   * Checks if an optional that should not be null has a value present. If so, that value is retrieved. If the optional has no value, then the program exits with a fatal internal error code.
   *
   * @param <T>      Generic optional type.
   * @param optional The optional to be checked.
   *
   * @return The optional value if present.
   */
  public static <T> T retrieveOptional(Optional<T> optional) {
    if (!optional.isPresent()) {
      exitProgram(Error.ERROR_INTERNAL, new IllegalArgumentException(Constants.MSG_ERROR_DEBUG_NO_OPTIONAL_CONTENT));
    }

    return optional.get();
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
                               .filter(entry -> entry.getValue().equals(search))
                               .map(Map.Entry::getKey)
                               .findFirst());
  }
}