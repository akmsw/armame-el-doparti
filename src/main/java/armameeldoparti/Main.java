package armameeldoparti;

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
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
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
import java.util.Objects;
import java.util.TreeMap;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;

/**
 * Main class, only for program start-up.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 1.0
 */
public final class Main {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Empty, private constructor. Not needed.
   */
  private Main() {
    // Body not needed
  }

  // ---------------------------------------- Main entry point ----------------------------------

  /**
   * Starts the program by initializing the fields needed along with the program's graphical
   * properties, and making the main menu view visible.
   *
   * @param args Program arguments (not used yet).
   */
  public static void main(String[] args) {
    // Establishes the main monitor as active monitor by default.
    CommonFields.setActiveMonitor(GraphicsEnvironment.getLocalGraphicsEnvironment()
                                                     .getDefaultScreenDevice());
    CommonFields.setAnchoragesEnabled(false);
    CommonFields.setControllersMap(new EnumMap<>(ProgramView.class));
    CommonFields.setPlayersAmountMap(new EnumMap<>(Position.class));
    CommonFields.setPositionsMap(new EnumMap<>(Position.class));
    CommonFields.setPlayersSets(new TreeMap<>());
    CommonFields.setPositionsMap(
        Map.of(
          Position.CENTRAL_DEFENDER, Constants.POSITION_CENTRAL_DEFENDERS,
          Position.LATERAL_DEFENDER, Constants.POSITION_LATERAL_DEFENDERS,
          Position.MIDFIELDER, Constants.POSITION_MIDFIELDERS,
          Position.FORWARD, Constants.POSITION_FORWARDS,
          Position.GOALKEEPER, Constants.POSITION_GOALKEEPERS
        )
    );

    setUpGeneralGraphicalProperties();
    setPlayersDistribution();
    populatePlayersSets();
    setUpControllers();

    ((MainMenuController) CommonFunctions.getController(ProgramView.MAIN_MENU)).showView();
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Populates the players sets with empty players.
   */
  private static void populatePlayersSets() {
    for (Position position : Position.values()) {
      List<Player> playersSet = new ArrayList<>();

      int totalPlayersInPosition = CommonFields.getPlayersAmountMap()
                                               .get(position) * 2;

      for (int i = 0; i < totalPlayersInPosition; i++) {
        playersSet.add(new Player("", position));
      }

      CommonFields.getPlayersSets()
                  .put(position, playersSet);
    }
  }

  /**
   * Gets the number of players for each position per team using regular expressions.
   *
   * <p>{@code [CLMFG].+>.+}: Retrieves the lines that start with C, L, M, F, or W, followed by at
   * least one '>' character (these are the lines that matters in the .pda file).
   *
   * <p>{@code (?!(?<=X)\\d).}: Gets the part of the line that is not a number that we are
   * interested in (the number would take the place of the X).
   *
   * <p>If the .pda file is modified in terms of the order of the important lines, it must be taken
   * into account that Position.values()[index] trusts that what is found corresponds to the order
   * in which the values in the Position enum are declared. Idem, if the order of the Position enum
   * values are changed, it should be noted that Position.values()[index] trusts the order in which
   * the data will be retrieved from the .pda file and, therefore, you should review the order of
   * the important lines in the file.
   */
  private static void setPlayersDistribution() {
    try (BufferedReader buff = new BufferedReader(
         new InputStreamReader(
           Objects.requireNonNull(
             CommonFunctions.class
                            .getClassLoader()
                            .getResourceAsStream(Constants.PATH_DOCS + Constants.FILENAME_PDA)
           )
         )
      )) {
      var wrapperIndex = new Object() {
        private int index = 0;
      };

      buff.lines()
          .filter(l -> l.matches(Constants.REGEX_PDA_DATA_RETRIEVE))
          .forEach(l -> {
            CommonFields.getPlayersAmountMap()
                        .put(Position.values()[wrapperIndex.index],
                             Integer.parseInt(l.replaceAll(Constants.REGEX_PLAYERS_AMOUNT, "")));

            wrapperIndex.index++;
          });
    } catch (IOException e) {
      CommonFunctions.exitProgram(Error.INTERNAL_FILES_ERROR);
    }
  }

  /**
   * Creates the controllers and assigns their corresponding view to control.
   */
  private static void setUpControllers() {
    Controller<MainMenuView> mainMenuController = new MainMenuController(
        new MainMenuView()
    );
    CommonFields.getControllersMap()
                .put(ProgramView.MAIN_MENU, mainMenuController);

    Controller<HelpView> helpController = new HelpController(
        new HelpView()
    );
    CommonFields.getControllersMap()
                .put(ProgramView.HELP, helpController);

    Controller<NamesInputView> namesInputController = new NamesInputController(
        new NamesInputView()
    );
    CommonFields.getControllersMap()
                .put(ProgramView.NAMES_INPUT, namesInputController);

    Controller<AnchoragesView> anchoragesController = new AnchoragesController(
        new AnchoragesView()
    );
    CommonFields.getControllersMap()
                .put(ProgramView.ANCHORAGES, anchoragesController);

    Controller<SkillPointsInputView> skillPointsInputController = new SkillPointsInputController(
        new SkillPointsInputView()
    );
    CommonFields.getControllersMap()
                .put(ProgramView.SKILL_POINTS, skillPointsInputController);

    Controller<ResultsView> resultsController = new ResultsController(
        new ResultsView()
    );
    CommonFields.getControllersMap()
                .put(ProgramView.RESULTS, resultsController);
  }

  /**
   * Sets up the program's GUI properties.
   */
  private static void setUpGeneralGraphicalProperties() {
    UIManager.put("Button.background", Constants.GREEN_DARK);
    UIManager.put("Button.foreground", Color.WHITE);
    UIManager.put("CheckBox.background", Constants.GREEN_LIGHT);
    UIManager.put("CheckBox.focus", Constants.GREEN_LIGHT);
    UIManager.put("ComboBox.background", Constants.GREEN_MEDIUM);
    UIManager.put("ComboBox.foreground", Color.WHITE);
    UIManager.put("ComboBox.selectionBackground", Constants.GREEN_MEDIUM);
    UIManager.put("ComboBox.selectionForeground", Color.WHITE);
    UIManager.put("Label.background", Constants.GREEN_MEDIUM_LIGHT);
    UIManager.put("OptionPane.background", Constants.GREEN_LIGHT);
    UIManager.put("Panel.background", Constants.GREEN_LIGHT);
    UIManager.put("Separator.background", Constants.GREEN_LIGHT);
    UIManager.put("TextArea.background", Constants.GREEN_LIGHT_WHITE);
    UIManager.put("TitledBorder.border", new LineBorder(Constants.GREEN_DARK));
    UIManager.put("ToggleButton.focus", Constants.GREEN_DARK);
    UIManager.put("ToolTip.background", Constants.GREEN_MEDIUM);
    UIManager.put("ToolTip.border", new LineBorder(Constants.GREEN_DARK));
    UIManager.put("ToolTip.foreground", Color.WHITE);

    try {
      // In order to use the font, it must be first created and registered
      Font programFont = Font.createFont(
          Font.TRUETYPE_FONT,
          Objects.requireNonNull(
            CommonFunctions.class
                           .getClassLoader()
                           .getResourceAsStream(Constants.PATH_TTF + Constants.FILENAME_FONT),
            Constants.MSG_ERROR_NULL_RESOURCE
          )
      ).deriveFont(Constants.FONT_SIZE);

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