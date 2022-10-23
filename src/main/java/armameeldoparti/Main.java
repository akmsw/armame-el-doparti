package armameeldoparti;

import armameeldoparti.controllers.AnchoragesController;
import armameeldoparti.controllers.Controller;
import armameeldoparti.controllers.HelpController;
import armameeldoparti.controllers.MainMenuController;
import armameeldoparti.controllers.NamesInputController;
import armameeldoparti.controllers.ResultsController;
import armameeldoparti.controllers.SkillPointsInputController;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.Views;
import armameeldoparti.utils.CommonFunctions;
import armameeldoparti.utils.Constants;
import armameeldoparti.utils.Error;
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
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;

/**
 * Main class, only for program and useful fields initialization.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 15/02/2021
 */
public final class Main {

  // ---------------------------------------- Private fields ------------------------------------

  private static int distribution;

  private static boolean anchorages;

  private static Map<Position, Integer> playersAmountMap;
  private static Map<Position, List<Player>> playersSets;
  private static Map<Position, String> positions;
  private static Map<Views, Controller> controllersMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Empty constructor.
   */
  private Main() {
    // No body needed
  }

  // ---------------------------------------- Main entry point ----------------------------------

  /**
   * Runs the program.
   *
   * @param args Program arguments (not used yet).
   */
  public static void main(String[] args) {
    controllersMap = new EnumMap<>(Views.class);
    playersAmountMap = new EnumMap<>(Position.class);
    positions = new EnumMap<>(Position.class);

    playersSets = new TreeMap<>();

    positions.put(Position.CENTRAL_DEFENDER, "DEFENSORES CENTRALES");
    positions.put(Position.LATERAL_DEFENDER, "DEFENSORES LATERALES");
    positions.put(Position.MIDFIELDER, "MEDIOCAMPISTAS");
    positions.put(Position.FORWARD, "DELANTEROS");
    positions.put(Position.GOALKEEPER, "ARQUEROS");

    setGraphicalProperties();
    getPlayersDistributionData();
    populatePlayersSets();
    setUpControllers();
    setAnchorages(false);

    ((MainMenuController) getController(Views.MAIN_MENU)).showView();
  }

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Gets the chosen players distribution.
   *
   * @return The chosen players distribution.
   */
  public static int getDistribution() {
    return distribution;
  }

  /**
   * Indicates if the anchorages checkbox is checked or not.
   *
   * @return Whether the anchorages checkbox is checked or not.
   */
  public static boolean thereAreAnchorages() {
    return anchorages;
  }

  /**
   * Gets the map that contains the total amount of players for each position.
   *
   * @return The map that contains the total amount of players for each position.
   */
  public static Map<Position, Integer> getPlayersAmountMap() {
    return playersAmountMap;
  }

  /**
   * Gets the map that associates each players set with its corresponding position.
   *
   * @return The map that associates each players set with its corresponding position.
   */
  public static Map<Position, List<Player>> getPlayersSets() {
    return playersSets;
  }

  /**
   * Gets the map that associates each position with its string representation.
   *
   * @return The map that associates each position with its string representation.
   */
  public static Map<Position, String> getPositionsMap() {
    return positions;
  }

  /**
   * Gets the corresponding controller to the requested view.
   *
   * @param view The view whose controller is needed.
   *
   * @return The requested view's controller.
  */
  public static Controller getController(Views view) {
    return controllersMap.get(view);
  }

  // ---------------------------------------- Setters -------------------------------------------

  /**
   * Updates the chosen players distribution.
   *
   * @param newDistribution The new chosen players distribution.
   */
  public static void setDistribution(int newDistribution) {
    distribution = newDistribution;
  }

  /**
   * Updates the anchorages option state.
   *
   * @param newAnchoragesState The new anchorages option state.
   */
  public static void setAnchorages(boolean newAnchoragesState) {
    anchorages = newAnchoragesState;
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Populates the players sets with empty players.
   */
  private static final void populatePlayersSets() {
    for (Position position : Position.values()) {
      List<Player> playersSet = new ArrayList<>();

      for (int i = 0; i < playersAmountMap.get(position) * 2; i++) {
        playersSet.add(new Player("", position));
      }

      playersSets.put(position, playersSet);
    }
  }

  /**
   * Creates the controllers and assigns their corresponding view to control.
   */
  private static final void setUpControllers() {
    Controller mainMenuController = new MainMenuController(new MainMenuView());
    controllersMap.put(Views.MAIN_MENU, mainMenuController);

    Controller helpController = new HelpController(new HelpView());
    controllersMap.put(Views.HELP, helpController);

    Controller namesInputController = new NamesInputController(new NamesInputView());
    controllersMap.put(Views.NAMES_INPUT, namesInputController);

    Controller anchoragesController = new AnchoragesController(new AnchoragesView());
    controllersMap.put(Views.ANCHORAGES, anchoragesController);

    Controller skillPointsInputController = new SkillPointsInputController(
        new SkillPointsInputView()
    );
    controllersMap.put(Views.SKILL_POINTS, skillPointsInputController);

    Controller resultsController = new ResultsController(new ResultsView());
    controllersMap.put(Views.RESULTS, resultsController);
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
  private static final void getPlayersDistributionData() {
    BufferedReader buff = new BufferedReader(
        new InputStreamReader(Main.class
                                  .getClassLoader()
                                  .getResourceAsStream(Constants.DOCS_PATH
                                                       + Constants.PDA_FILENAME))
    );

    var wrapperIndex = new Object() {
      private int index;
    };

    buff.lines()
        .forEach(l -> {
          if (l.matches(Constants.PDA_DATA_RETRIEVE_REGEX)) {
            getPlayersAmountMap().put(Position.values()[wrapperIndex.index],
                                      Integer.parseInt(l.replaceAll(Constants.REGEX_PLAYERS_AMOUNT,
                                                                    "")));

            wrapperIndex.index++;
          }
        });
  }

  /**
   * Sets up the program's GUI properties.
   */
  private static final void setGraphicalProperties() {
    UIManager.put("OptionPane.background", Constants.LIGHT_GREEN);
    UIManager.put("Panel.background", Constants.LIGHT_GREEN);
    UIManager.put("CheckBox.background", Constants.LIGHT_GREEN);
    UIManager.put("Separator.background", Constants.LIGHT_GREEN);
    UIManager.put("CheckBox.focus", Constants.LIGHT_GREEN);
    UIManager.put("Button.background", Constants.DARK_GREEN);
    UIManager.put("Button.focus", Constants.DARK_GREEN);
    UIManager.put("ToggleButton.focus", Constants.DARK_GREEN);
    UIManager.put("TitledBorder.border", new LineBorder(Constants.DARK_GREEN));
    UIManager.put("Button.foreground", Color.WHITE);
    UIManager.put("ComboBox.focus", Color.WHITE);

    try {
      // In order to use the font, it must be first created and registered
      Font programFont = Font.createFont(Font.TRUETYPE_FONT,
                                         Main.class
                                             .getClassLoader()
                                             .getResourceAsStream(Constants.TTF_PATH
                                                                  + Constants.FONT_NAME))
                                             .deriveFont(Constants.FONT_SIZE);

      GraphicsEnvironment.getLocalGraphicsEnvironment()
                         .registerFont(programFont);

      setProgramFont(programFont);
    } catch (IOException | FontFormatException e) {
      e.printStackTrace();

      CommonFunctions.exitProgram(Error.GUI_ERROR);
    }
  }

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