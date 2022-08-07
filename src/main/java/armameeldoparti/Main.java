package armameeldoparti;

import armameeldoparti.controllers.AnchoragesController;
import armameeldoparti.controllers.Controller;
import armameeldoparti.controllers.HelpController;
import armameeldoparti.controllers.MainMenuController;
import armameeldoparti.controllers.NamesInputController;
import armameeldoparti.controllers.ResultsController;
import armameeldoparti.controllers.SkillPointsInputController;
import armameeldoparti.models.Player;
import armameeldoparti.models.Positions;
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
import java.awt.Image;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;

/**
 * Main class, only for program initialization and useful fields declaration.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 15/02/2021
 */
public final class Main {

  // ---------------------------------------- Private constants ---------------------------------

  /**
   * Size, in pixels, of the scaled icon (height and width).
   */
  private static final int ICON_SCALE = 50;

  // ---------------------------------------- Public constants ----------------------------------

  public static final int RANDOM_MIX = 0;
  public static final int BY_SKILLS_MIX = 1;
  public static final int PLAYERS_PER_TEAM = 7;
  public static final int MAX_PLAYERS_PER_ANCHORAGE = PLAYERS_PER_TEAM - 1;
  public static final int MAX_ANCHORED_PLAYERS = 2 * MAX_PLAYERS_PER_ANCHORAGE;
  public static final int MAX_NAME_LEN = 10;

  /**
   * Skill points spinners initial value.
   */
  public static final int SKILL_INI = 1;

  /**
   * Skill points spinners minimum value.
   */
  public static final int SKILL_MIN = 1;

  /**
   * Skill points spinners maximum value.
   */
  public static final int SKILL_MAX = 5;

  /**
   * Increment and decrement step in the skill points spinners.
   */
  public static final int SKILL_STEP = 1;

  public static final float FONT_SIZE = 18f;

  public static final String FONT_NAME = "comfortaa.ttf";
  public static final String PROGRAM_TITLE = "Armame el doparti";
  public static final String PROGRAM_VERSION = "v3.0";
  public static final String ICON_FILENAME = "icon.png";
  public static final String BG_IMG_FILENAME = "bg.png";
  public static final String IMG_PATH = "img/";
  public static final String TTF_PATH = "fonts/";
  public static final String DOCS_PATH = "docs/";
  public static final String HELP_DOCS_PATH = "docs/help/";
  public static final String NAMES_VALIDATION_REGEX = "[a-z A-ZÁÉÍÓÚáéíóúñÑ]+";

  public static final Color LIGHT_GREEN = new Color(176, 189, 162);
  public static final Color MEDIUM_GREEN = new Color(109, 130, 118);
  public static final Color DARK_GREEN = new Color(41, 71, 74);
  public static final Color LIGHT_ORANGE = new Color(255, 238, 153);

  /**
   * Standard-size program icon.
   */
  public static final ImageIcon ICON = new ImageIcon(
      Main.class
          .getClassLoader()
          .getResource(Main.IMG_PATH + Main.ICON_FILENAME)
  );

  /**
   * Scaled program icon.
   */
  public static final ImageIcon SCALED_ICON = new ImageIcon(
      ICON.getImage()
          .getScaledInstance(ICON_SCALE, ICON_SCALE, Image.SCALE_SMOOTH)
  );

  // ---------------------------------------- Private fields ------------------------------------

  private static int distribution;

  private static boolean anchorages;

  private static Map<Positions, Integer> playersAmountMap;
  private static Map<Positions, String> positions;
  private static Map<Positions, List<Player>> playersSets;
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
    playersAmountMap = new EnumMap<>(Positions.class);
    positions = new EnumMap<>(Positions.class);
    controllersMap = new EnumMap<>(Views.class);

    playersSets = new TreeMap<>();

    positions.put(Positions.CENTRAL_DEFENDER, "DEFENSORES CENTRALES");
    positions.put(Positions.LATERAL_DEFENDER, "DEFENSORES LATERALES");
    positions.put(Positions.MIDFIELDER, "MEDIOCAMPISTAS");
    positions.put(Positions.FORWARD, "DELANTEROS");
    positions.put(Positions.GOALKEEPER, "ARQUEROS");

    setAnchorages(false);
    setGraphicalProperties();

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

    ((MainMenuController) mainMenuController).showView();
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Gets the valueToSearch corresponding position in a generic map received.
   *
   * @param map           Generic map with positions as keys.
   * @param valueToSearch Value to search in the map.
   *
   * @return The valueToSearch corresponding position.
   */
  public static <T> Positions getCorrespondingPosition(Map<Positions, T> map, T valueToSearch) {
    return (Positions) map.entrySet()
                         .stream()
                         .filter(e -> e.getValue()
                                       .equals(valueToSearch))
                         .map(Map.Entry::getKey)
                         .toArray()[0];
  }

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * The chosen players distribution.
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
  public static Map<Positions, Integer> getPlayersAmountMap() {
    return playersAmountMap;
  }

  /**
   * Gets the map that associates each players set with its corresponding position.
   *
   * @return The map that associates each players set with its corresponding position.
   */
  public static Map<Positions, List<Player>> getPlayersSets() {
    return playersSets;
  }

  /**
   * Gets the map that associates each position with its string representation.
   *
   * @return The map that associates each position with its string representation.
   */
  public static Map<Positions, String> getPositionsMap() {
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
   * Sets up the program's GUI properties.
   */
  private static void setGraphicalProperties() {
    UIManager.put("OptionPane.background", Main.LIGHT_GREEN);
    UIManager.put("Panel.background", Main.LIGHT_GREEN);
    UIManager.put("CheckBox.background", Main.LIGHT_GREEN);
    UIManager.put("Separator.background", Main.LIGHT_GREEN);
    UIManager.put("CheckBox.focus", Main.LIGHT_GREEN);
    UIManager.put("Button.background", Main.DARK_GREEN);
    UIManager.put("Button.focus", Main.DARK_GREEN);
    UIManager.put("ToggleButton.focus", Main.DARK_GREEN);
    UIManager.put("TitledBorder.border", new LineBorder(Main.DARK_GREEN));
    UIManager.put("Button.foreground", Color.WHITE);
    UIManager.put("ComboBox.focus", Color.WHITE);

    try {
      // In order to use the font, it must be created and registered first
      Font programFont = Font.createFont(Font.TRUETYPE_FONT,
                                         Main.class
                                             .getClassLoader()
                                             .getResourceAsStream(Main.TTF_PATH + Main.FONT_NAME))
                                             .deriveFont(Main.FONT_SIZE);

      GraphicsEnvironment.getLocalGraphicsEnvironment()
                         .registerFont(programFont);

      setProgramFont(programFont);
    } catch (IOException | FontFormatException ex) {
      ex.printStackTrace();
      System.exit(-1);
    }
  }

  /**
   * Sets the program font.
   *
   * @param font Font to use.
   */
  private static void setProgramFont(Font font) {
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