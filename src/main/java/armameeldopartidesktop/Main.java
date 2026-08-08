package armameeldopartidesktop;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.IntStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.FontUIResource;

import armameeldopartidesktop.controllers.AnchoragesController;
import armameeldopartidesktop.controllers.HelpController;
import armameeldopartidesktop.controllers.MainMenuController;
import armameeldopartidesktop.controllers.NamesInputController;
import armameeldopartidesktop.controllers.ResultsController;
import armameeldopartidesktop.controllers.SkillPointsInputController;
import armameeldopartidesktop.models.Player;
import armameeldopartidesktop.models.enums.Error;
import armameeldopartidesktop.models.enums.Position;
import armameeldopartidesktop.models.enums.ProgramView;
import armameeldopartidesktop.utils.common.CommonFields;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.utils.common.custom.graphical.ui.CustomButtonUI;
import armameeldopartidesktop.utils.common.custom.graphical.ui.CustomCheckBoxUI;
import armameeldopartidesktop.utils.common.custom.graphical.ui.CustomComboBoxUI;
import armameeldopartidesktop.utils.common.custom.graphical.ui.CustomOptionPaneUI;
import armameeldopartidesktop.utils.common.custom.graphical.ui.CustomRadioButtonUI;
import armameeldopartidesktop.utils.common.custom.graphical.ui.CustomScrollBarUI;
import armameeldopartidesktop.utils.common.custom.graphical.ui.CustomScrollPaneUI;
import armameeldopartidesktop.utils.common.custom.graphical.ui.CustomSeparatorUI;
import armameeldopartidesktop.utils.common.custom.graphical.ui.CustomSpinnerUI;
import armameeldopartidesktop.utils.common.custom.graphical.ui.CustomTableUI;
import armameeldopartidesktop.utils.common.custom.graphical.ui.CustomTextAreaUI;
import armameeldopartidesktop.utils.common.custom.graphical.ui.CustomTextFieldUI;
import armameeldopartidesktop.views.AnchoragesView;
import armameeldopartidesktop.views.HelpView;
import armameeldopartidesktop.views.MainMenuView;
import armameeldopartidesktop.views.NamesInputView;
import armameeldopartidesktop.views.ResultsView;
import armameeldopartidesktop.views.SkillPointsInputView;

/**
 * Main class, only for program start-up.
 *
 * @since 1.0.0
 *
 * @version 3.1.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class Main {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Empty, private constructor to prevent instantiation.
   */
  private Main() {
    // Body not needed
  }

  // ---------- Main entry point --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Starts the program by initializing the fields needed along with the program's graphical properties, and making the main menu view visible.
   *
   * @param args Program arguments (not used).
   */
  public static void main(String [] args) {
    CommonFields.setAnchorages(new ArrayList<>());
    CommonFields.setAnchoragesEnabled(false);
    CommonFields.setControllersMap(new EnumMap<>(ProgramView.class));
    CommonFields.setPlayerLimitPerPosition(new EnumMap<>(Position.class));
    CommonFields.setPlayersSets(new TreeMap<>());

    setUpGeneralGraphicalProperties();
    setPlayersDistribution();
    initializePlayersSetsMap();

    JFrame mainFrame = new JFrame(Constants.TITLE_VIEW_MAIN_MENU);

    JPanel mainPanel = new JPanel(new CardLayout());

    mainFrame.setContentPane(mainPanel);
    mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    mainFrame.setResizable(false);
    mainFrame.setIconImage(Constants.ICON_MAIN_SCALED.getImage());
    mainFrame.setMinimumSize(new Dimension(1, 1));
    mainFrame.setMaximumSize(null);
    mainFrame.setVisible(true);

    CommonFields.setMainFrame(mainFrame);
    CommonFields.setMainPanel(mainPanel);

    initializeControllersMap();

    SwingUtilities.invokeLater(((MainMenuController) CommonFunctions.getController(ProgramView.MAIN_MENU))::showView);
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Populates the players sets with empty players.
   */
  private static void initializePlayersSetsMap() {
    for (Position position : Position.values()) {
      CommonFields.getPlayersSets()
                  .put(position, IntStream.range(0, CommonFields.getPlayerLimitPerPosition().get(position) * 2)
                                          .mapToObj(_ -> new Player(Constants.PLAYER_NO_NAME_ASSIGNED, position))
                                          .toList());
    }
  }

  /**
   * Gets the number of players for each position per team using regular expressions.
   *
   * <p>{@code REGEX_PDA_DATA_RETRIEVE}: Retrieves the lines that start with C, L, M, F, or G, followed by at least one '>' character (these are the lines that matters in the .pda file).
   * <p>{@code REGEX_PLAYERS_COUNT}: Gets the part of the line that is not a number that we are interested in.
   *
   * <p>If the .pda file is modified in terms of the order of the important lines, it must be taken into account that {@code Position.values()[index]} trusts that what is found corresponds to the order in which the
   * values in the Position enum are declared. Idem, if the order of the Position enum values are changed, it should be noted that {@code Position.values()[index]} trusts the order in which the data will be
   * retrieved from the .pda file and, therefore, you should review the order of the important lines in the file.
   */
  private static void setPlayersDistribution() {
    try (BufferedReader buffer = new BufferedReader(new InputStreamReader(Objects.requireNonNull(CommonFunctions.class.getClassLoader().getResourceAsStream(Constants.PATH_DOCS + Constants.FILENAME_PDA))))) {
      List<String> filteredLines = buffer.lines()
                                         .filter(line -> line.matches(Constants.REGEX_PDA_DATA_RETRIEVE))
                                         .toList();

      for (int lineIndex = 0; lineIndex < filteredLines.size(); lineIndex++) {
        CommonFields.getPlayerLimitPerPosition().put(Position.values()[lineIndex], Integer.parseInt(filteredLines.get(lineIndex).replaceAll(Constants.REGEX_PLAYERS_COUNT, "")));
      }
    } catch (IOException exception) {
      CommonFunctions.exitProgram(Error.ERROR_FILES, exception);
    }
  }

  /**
   * Creates the controllers and assigns their corresponding view to control.
   */
  private static void initializeControllersMap() {
    CommonFields.getControllersMap()
                .putAll(
                  Map.of(
                    ProgramView.MAIN_MENU   , new MainMenuController(new MainMenuView()),
                    ProgramView.HELP        , new HelpController(new HelpView()),
                    ProgramView.NAMES_INPUT , new NamesInputController(new NamesInputView()),
                    ProgramView.ANCHORAGES  , new AnchoragesController(new AnchoragesView()),
                    ProgramView.SKILL_POINTS, new SkillPointsInputController(new SkillPointsInputView()),
                    ProgramView.RESULTS     , new ResultsController(new ResultsView())
                  )
                );
  }

  /**
   * Sets up the general graphical properties of the program.
   */
  private static void setUpGeneralGraphicalProperties() {
    UIManager.put("ButtonUI"                     , CustomButtonUI.class.getName());
    UIManager.put("CheckBoxUI"                   , CustomCheckBoxUI.class.getName());
    UIManager.put("ComboBoxUI"                   , CustomComboBoxUI.class.getName());
    UIManager.put("ComboBox.background"          , Constants.COLOR_GREEN_MEDIUM);
    UIManager.put("ComboBox.foreground"          , Color.WHITE);
    UIManager.put("ComboBox.selectionBackground" , Constants.COLOR_GREEN_MEDIUM);
    UIManager.put("ComboBox.selectionForeground" , Color.WHITE);
    UIManager.put("OptionPaneUI"                 , CustomOptionPaneUI.class.getName());
    UIManager.put("OptionPane.cancelButtonText"  , Constants.TEXT_BUTTON_DIALOG_CANCEL);
    UIManager.put("OptionPane.noButtonText"      , Constants.TEXT_BUTTON_DIALOG_NO);
    UIManager.put("OptionPane.okButtonText"      , Constants.TEXT_BUTTON_DIALOG_OK);
    UIManager.put("OptionPane.yesButtonText"     , Constants.TEXT_BUTTON_DIALOG_YES);
    UIManager.put("OptionPane.errorIcon"         , Constants.ICON_DIALOG_ERROR);
    UIManager.put("OptionPane.informationIcon"   , Constants.ICON_DIALOG_INFORMATION);
    UIManager.put("OptionPane.questionIcon"      , Constants.ICON_DIALOG_QUESTION);
    UIManager.put("OptionPane.warningIcon"       , Constants.ICON_DIALOG_WARNING);
    UIManager.put("Panel.background"             , Constants.COLOR_GREEN_LIGHT);
    UIManager.put("RadioButtonUI"                , CustomRadioButtonUI.class.getName());
    UIManager.put("ScrollBarUI"                  , CustomScrollBarUI.class.getName());
    UIManager.put("ScrollPaneUI"                 , CustomScrollPaneUI.class.getName());
    UIManager.put("SeparatorUI"                  , CustomSeparatorUI.class.getName());
    UIManager.put("SpinnerUI"                    , CustomSpinnerUI.class.getName());
    UIManager.put("FormattedTextField.background", Constants.COLOR_GREEN_LIGHT_WHITE);
    UIManager.put("TableUI"                      , CustomTableUI.class.getName());
    UIManager.put("TextAreaUI"                   , CustomTextAreaUI.class.getName());
    UIManager.put("TextFieldUI"                    , CustomTextFieldUI.class.getName());
    UIManager.put("TextField.selectionBackground", Constants.COLOR_GREEN_DARK_MEDIUM);
    UIManager.put("TextField.selectionForeground", Constants.COLOR_GREEN_LIGHT_WHITE);

    ToolTipManager.sharedInstance().setInitialDelay(Constants.DELAY_TOOLTIP_INITIAL);
    ToolTipManager.sharedInstance().setDismissDelay(Constants.DELAY_TOOLTIP_DISMISS);

    try {
      Font programFont = Font.createFont(Font.TRUETYPE_FONT,
                                         Objects.requireNonNull(CommonFunctions.class
                                                                               .getClassLoader()
                                                                               .getResourceAsStream(Constants.PATH_TTF + Constants.FILENAME_FONT),
                                                                Constants.MSG_ERROR_NULL_GUI_RESOURCE))
                             .deriveFont(Constants.SIZE_FONT_DEFAULT);

      GraphicsEnvironment.getLocalGraphicsEnvironment()
                         .registerFont(programFont);

      UIManager.getDefaults()
               .keySet()
               .stream()
               .filter(key -> UIManager.get(key) instanceof FontUIResource)
               .forEach(key -> UIManager.put(key, programFont));
    } catch (IOException | FontFormatException exception) {
      CommonFunctions.exitProgram(Error.ERROR_GUI, exception);
    }
  }
}