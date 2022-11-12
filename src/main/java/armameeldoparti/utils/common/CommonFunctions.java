package armameeldoparti.utils.common;

import armameeldoparti.controllers.AnchoragesController;
import armameeldoparti.controllers.Controller;
import armameeldoparti.controllers.HelpController;
import armameeldoparti.controllers.MainMenuController;
import armameeldoparti.controllers.NamesInputController;
import armameeldoparti.controllers.ResultsController;
import armameeldoparti.controllers.SkillPointsInputController;
import armameeldoparti.models.Error;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.Views;
import armameeldoparti.views.AnchoragesView;
import armameeldoparti.views.HelpView;
import armameeldoparti.views.MainMenuView;
import armameeldoparti.views.NamesInputView;
import armameeldoparti.views.ResultsView;
import armameeldoparti.views.SkillPointsInputView;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;

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
   * Starts the program by initializing the fields needed along with
   * the program's graphical properties, and making the main menu view visible.
   */
  public static final void start() {
    CommonFields.initializeMaps();
    CommonFields.setAnchorages(false);

    setGraphicalProperties();
    getPlayersDistributionData();
    populatePlayersSets();
    setUpControllers();

    ((MainMenuController) CommonFunctions.getController(Views.MAIN_MENU)).showView();
  }

  /**
   * Gets a list containing the anchored players
   * grouped by their anchorage number.
   *
   * @return A list containing the anchored players
   *         grouped by their anchorage number.
  */
  public static final List<List<Player>> getAnchoredPlayers() {
    return CommonFields.getPlayersSets()
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

  /**
   * Gets the corresponding controller to the requested view.
   *
   * @param view The view whose controller is needed.
   *
   * @return The requested view's controller.
  */
  public static final Controller getController(Views view) {
    return CommonFields.getControllersMap()
                       .get(view);
  }

  /**
   * Populates the players sets with empty players.
   */
  public static final void populatePlayersSets() {
    for (Position position : Position.values()) {
      List<Player> playersSet = new ArrayList<>();

      for (int i = 0; i < CommonFields.getPlayersAmountMap().get(position) * 2; i++) {
        playersSet.add(new Player("", position));
      }

      CommonFields.getPlayersSets()
                  .put(position, playersSet);
    }
  }

  /**
   * Gets the number of players for each position per team using regular expressions.
   *
   * <p>{@code [CLMFG].+>.+}: Retrieves the lines that start with C, L, M, F, or W,
   * followed by at least one '>' character (these are the lines that matters in the
   * .pda file).
   *
   * <p>{@code (?!(?<=X)\\d).}: Gets the part of the line that is not a number that we are
   * interested in (the number would take the place of the X).
   *
   * <p>If the .pda file is modified in terms of the order of the important lines,
   * it must be taken into account that Position.values()[index] trusts that what is found
   * corresponds to the order in which the values in the Position enum are declared.
   * Idem, if the order of the Position enum values are changed, it should be noted that
   * Position.values()[index] trusts the order in which the data will be retrieved from the
   * .pda file and, therefore, you should review the order of the important lines in the file.
   */
  public static final void getPlayersDistributionData() {
    BufferedReader buff = new BufferedReader(
        new InputStreamReader(CommonFunctions.class
                                             .getClassLoader()
                                             .getResourceAsStream(Constants.PATH_DOCS
                                                                 + Constants.FILENAME_PDA))
    );

    var wrapperIndex = new Object() {
      private int index;
    };

    buff.lines()
        .forEach(l -> {
          if (l.matches(Constants.REGEX_PDA_DATA_RETRIEVE)) {
            CommonFields.getPlayersAmountMap()
                        .put(Position.values()[wrapperIndex.index],
                             Integer.parseInt(l.replaceAll(Constants.REGEX_PLAYERS_AMOUNT,
                             "")));

            wrapperIndex.index++;
          }
        });
  }

  /**
   * Creates the controllers and assigns their corresponding view to control.
   */
  public static final void setUpControllers() {
    Controller mainMenuController = new MainMenuController(new MainMenuView());
    CommonFields.getControllersMap()
                .put(Views.MAIN_MENU, mainMenuController);

    Controller helpController = new HelpController(new HelpView());
    CommonFields.getControllersMap()
                .put(Views.HELP, helpController);

    Controller namesInputController = new NamesInputController(new NamesInputView());
    CommonFields.getControllersMap()
                .put(Views.NAMES_INPUT, namesInputController);

    Controller anchoragesController = new AnchoragesController(new AnchoragesView());
    CommonFields.getControllersMap()
                .put(Views.ANCHORAGES, anchoragesController);

    Controller skillPointsInputController = new SkillPointsInputController(
        new SkillPointsInputView()
    );
    CommonFields.getControllersMap()
                .put(Views.SKILL_POINTS, skillPointsInputController);

    Controller resultsController = new ResultsController(new ResultsView());
    CommonFields.getControllersMap().put(Views.RESULTS, resultsController);
  }

  /**
   * Sets up the program's GUI properties.
   */
  public static final void setGraphicalProperties() {
    UIManager.put("OptionPane.background", Constants.GREEN_LIGHT);
    UIManager.put("Panel.background", Constants.GREEN_LIGHT);
    UIManager.put("CheckBox.background", Constants.GREEN_LIGHT);
    UIManager.put("Separator.background", Constants.GREEN_LIGHT);
    UIManager.put("CheckBox.focus", Constants.GREEN_LIGHT);
    UIManager.put("Button.background", Constants.GREEN_DARK);
    UIManager.put("Button.focus", Constants.GREEN_DARK);
    UIManager.put("ToggleButton.focus", Constants.GREEN_DARK);
    UIManager.put("TitledBorder.border", new LineBorder(Constants.GREEN_DARK));
    UIManager.put("Button.foreground", Color.WHITE);
    UIManager.put("ComboBox.focus", Color.WHITE);

    try {
      // In order to use the font, it must be first created and registered
      Font programFont = Font.createFont(Font.TRUETYPE_FONT,
                                         CommonFunctions.class
                                                        .getClassLoader()
                                                        .getResourceAsStream(
                                                          Constants.PATH_TTF
                                                          + Constants.FILENAME_FONT))
                                                        .deriveFont(Constants.FONT_SIZE);

      GraphicsEnvironment.getLocalGraphicsEnvironment()
                         .registerFont(programFont);

      setProgramFont(programFont);
    } catch (IOException | FontFormatException e) {
      e.printStackTrace();

      CommonFunctions.exitProgram(Error.GUI_ERROR);
    }
  }


  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Sets the program font.
   *
   * @param font Font to use.
   */
  private static final void setProgramFont(Font font) {
    Enumeration<Object> keys = UIManager.getDefaults()
                                        .keys();

    while (keys.hasMoreElements()) {
      Object key = keys.nextElement();
      Object value = UIManager.get(key);

      if (value instanceof FontUIResource) {
        UIManager.put(key, font);
      }
    }
  }
}