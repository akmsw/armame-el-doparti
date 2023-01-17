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
import armameeldoparti.models.Views;
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
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
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
 * @since 15/02/2021
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
   * Starts the program by initializing the fields needed along with
   * the program's graphical properties, and making the main menu view visible.
   *
   * @param args Program arguments (not used yet).
   */
  public static void main(String[] args) {
    CommonFields.initializeMaps();
    CommonFields.setAnchorages(false);

    setGraphicalProperties();
    setPlayersDistribution();
    populatePlayersSets();
    setUpControllers();

    ((MainMenuController) CommonFunctions.getController(Views.MAIN_MENU)).showView();
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Populates the players sets with empty players.
   */
  private static void populatePlayersSets() {
    for (Position position : Position.values()) {
      List<Player> playersSet = new ArrayList<>();

      for (int i = 0; i < CommonFields.getPlayersAmountMap()
                                      .get(position) * 2; i++) {
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
  private static void setPlayersDistribution() {
    BufferedReader buff = new BufferedReader(
        new InputStreamReader(
          Objects.requireNonNull(
            CommonFunctions.class
                           .getClassLoader()
                           .getResourceAsStream(Constants.PATH_DOCS + Constants.FILENAME_PDA)
          )
        )
    );

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
  }

  /**
   * Creates the controllers and assigns their corresponding view to control.
   */
  private static void setUpControllers() {
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
    CommonFields.getControllersMap()
                .put(Views.RESULTS, resultsController);
  }

  /**
   * Sets up the program's GUI properties.
   */
  private static void setGraphicalProperties() {
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
    final Enumeration<Object> keys = UIManager.getDefaults()
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