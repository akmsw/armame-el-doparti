package armameeldopartidesktop.utils.common;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import armameeldopartidesktop.controllers.Controller;
import armameeldopartidesktop.models.Player;
import armameeldopartidesktop.models.Team;
import armameeldopartidesktop.models.enums.Error;
import armameeldopartidesktop.models.enums.ProgramView;
import armameeldopartidesktop.views.View;

/**
 * Common-use functions class.
 *
 * @since 3.0.0
 *
 * @version 1.1.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CommonFunctions {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Empty, private constructor to prevent instantiation.
   */
  private CommonFunctions() {
    // Body not needed
  }

  // ---------- Public static methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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
      dumpFile.write("Report time: " + LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)) + System.lineSeparator());
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
      default -> exitProgram(Error.ERROR_GUI, new IllegalStateException(Constants.MSG_ERROR_DEBUG_INVALID_DIALOG_TYPE));
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
   * @see JOptionPane#showOptionDialog
   */
  public static int showOptionDialog(Component parentComponent, String dialogMessage, Object [] dialogOptions) {
    return JOptionPane.showOptionDialog(parentComponent,
                                        dialogMessage,
                                        Constants.TITLE_MESSAGE_QUESTION,
                                        JOptionPane.OK_CANCEL_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        Constants.ICON_DIALOG_QUESTION,
                                        dialogOptions,
                                        dialogOptions[0]);
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
   * @return The total count of anchored players.
   */
  public static int getPlayersAnchoredCount() {
    return CommonFields.getAnchorages()
                       .stream()
                       .mapToInt(anchorage -> anchorage.getPlayers().size())
                       .sum();
  }

  /**
   * @param teams Teams to check if their skill points are equal.
   *
   * @return Whether the skill points of the given teams are equal.
   */
  public static boolean teamsSkillPointsAreEqual(List<Team> teams) {
    return getTeamsSkillDifference(teams) == 0;
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
      exitProgram(Error.ERROR_BROWSER, exception);
    }
  }

  /**
   * Displays the requested view in the main frame.
   *
   * @param view View to display.
   */
  public static void showView(View view) {
    JFrame mainFrame = CommonFields.getMainFrame();

    JPanel mainPanel = CommonFields.getMainPanel();

    if (view.getParent() != null) {
      view.getParent().remove(view);
    }

    mainPanel.removeAll();
    mainPanel.add(view, view.getClass().getName());

    ((CardLayout) mainPanel.getLayout()).show(mainPanel, view.getClass().getName());

    view.setVisible(true);

    mainFrame.setTitle(view.getTitle());

    /*
      Resize the main frame to fit the current view dimensions, identify the currently active screen and center the main frame on it.

      The screen identification is done by checking which screen has the largest intersection with the main frame bounds.
    */
    SwingUtilities.invokeLater(
      () -> {
        view.revalidate();
        view.doLayout();

        Dimension viewDimension = view.getPreferredSize();

        Insets frameInsets = mainFrame.getInsets();

        if ((viewDimension.width <= 0) || (viewDimension.height <= 0)) {
          viewDimension = view.getPreferredSize();
        }

        view.setPreferredSize(viewDimension);

        mainPanel.setPreferredSize(viewDimension);
        mainPanel.setSize(viewDimension);
        mainPanel.revalidate();
        mainPanel.doLayout();

        Dimension frameDimension = new Dimension(viewDimension.width + frameInsets.left + frameInsets.right,
                                                 viewDimension.height + frameInsets.top + frameInsets.bottom);

        mainFrame.setPreferredSize(frameDimension);
        mainFrame.setSize(frameDimension);

        Rectangle activeScreenBounds = retrieveOptional(
                                         Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices())
                                               .filter(screen -> !screen.getDefaultConfiguration()
                                                                        .getBounds()
                                                                        .intersection(mainFrame.getBounds())
                                                                        .isEmpty())
                                               .max(
                                                 Comparator.comparingDouble(
                                                   screen -> {
                                                     Rectangle intersection = screen.getDefaultConfiguration()
                                                                                    .getBounds()
                                                                                    .intersection(mainFrame.getBounds());

                                                     return (intersection.getWidth() * intersection.getHeight());
                                                   }
                                                 )
                                               )
                                       ).getDefaultConfiguration().getBounds();

        mainFrame.setLocation((((activeScreenBounds.width - mainFrame.getWidth()) / 2) + activeScreenBounds.x),
                              (((activeScreenBounds.height - mainFrame.getHeight()) / 2) + activeScreenBounds.y));
        mainFrame.revalidate();
        mainFrame.repaint();
      }
    );

    mainPanel.revalidate();
    mainPanel.repaint();
  }

  /**
   * Given an action event, this method returns the graphical component associated with it.
   *
   * @param event The triggered action event.
   *
   * @return The graphical component associated to the action event.
   */
  public static Component getComponentFromEvent(ActionEvent event) {
    return ((event == null) ? null : SwingUtilities.windowForComponent((Component) event.getSource()));
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
    return (input.isBlank() ? input : (input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase()));
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
   * @see #createImage
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
   * Gets the anchorages as an array of strings to be used as options in a dialog window.
   *
   * @return The anchorages as an array of strings.
   */
  public static String [] getAnchoragesAsOptions() {
    return IntStream.rangeClosed(1, CommonFields.getAnchorages().size())
                    .mapToObj(Integer::toString)
                    .toArray(String[]::new);
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
}