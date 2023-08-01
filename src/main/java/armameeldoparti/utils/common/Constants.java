package armameeldoparti.utils.common;

import armameeldoparti.models.Error;
import java.awt.Color;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 * Commonly-used and useful constants class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public final class Constants {

  // ---------------------------------------- Private constants ---------------------------------

  private static final int EXIT_CODE_ERROR_BROWSER = -1;
  private static final int EXIT_CODE_ERROR_GUI = -2;
  private static final int EXIT_CODE_ERROR_INTERNAL_FATAL = -3;
  private static final int EXIT_CODE_ERROR_INTERNAL_FILES = -4;

  /**
   * Size, in pixels, of the scaled program icon (height and width).
   */
  private static final int ICON_SCALE = 50;

  private static final String FILENAME_HELP_PAGE_1 = "helpIntro.hlp";
  private static final String FILENAME_HELP_PAGE_2 = "helpCriteria.hlp";
  private static final String FILENAME_HELP_PAGE_3 = "helpNames.hlp";
  private static final String FILENAME_HELP_PAGE_4 = "helpAnchorages.hlp";
  private static final String FILENAME_HELP_PAGE_5 = "helpScores.hlp";
  private static final String FILENAME_HELP_PAGE_6 = "helpRandomMix.hlp";
  private static final String FILENAME_HELP_PAGE_7 = "helpBySkillsMix.hlp";
  private static final String FILENAME_HELP_PAGE_8 = "helpContact.hlp";
  private static final String FILENAME_ICON_CB_DISABLED_SELECTED = "cb_d_s.png";
  private static final String FILENAME_ICON_CB_DISABLED_UNSELECTED = "cb_d_us.png";
  private static final String FILENAME_ICON_CB_ENABLED_SELECTED_FOCUSED = "cb_e_s_f.png";
  private static final String FILENAME_ICON_CB_ENABLED_SELECTED_PRESSED = "cb_e_s_p.png";
  private static final String FILENAME_ICON_CB_ENABLED_SELECTED_UNFOCUSED = "cb_e_s_uf.png";
  private static final String FILENAME_ICON_CB_ENABLED_UNSELECTED_FOCUSED = "cb_e_us_f.png";
  private static final String FILENAME_ICON_CB_ENABLED_UNSELECTED_PRESSED = "cb_e_us_p.png";
  private static final String FILENAME_ICON_CB_ENABLED_UNSELECTED_UNFOCUSED = "cb_e_us_uf.png";
  private static final String FILENAME_ICON_MAIN = "main_icon.png";
  private static final String FILENAME_IMAGE_BACKGROUND = "bg.png";
  private static final String HEX_CODE_GREEN_DARK = "#29474a";
  private static final String HEX_CODE_GREEN_DARK_MEDIUM = "#4b6560";
  private static final String HEX_CODE_GREEN_MEDIUM = "#6d8276";
  private static final String HEX_CODE_GREEN_MEDIUM_LIGHT = "#8fa08c";
  private static final String HEX_CODE_GREEN_LIGHT = "#b0bda2";
  private static final String HEX_CODE_GREEN_LIGHT_WHITE = "#d8ded1";
  private static final String HEX_CODE_ANCHORAGE_COLOR_1 = "#e5af8d";
  private static final String HEX_CODE_ANCHORAGE_COLOR_2 = "#ffee99";
  private static final String HEX_CODE_ANCHORAGE_COLOR_3 = "#85d689";
  private static final String HEX_CODE_ANCHORAGE_COLOR_4 = "#74b1d1";
  private static final String HEX_CODE_ANCHORAGE_COLOR_5 = "#a995c9";
  private static final String HEX_CODE_ANCHORAGE_COLOR_6 = "#f27c7c";
  private static final String MIG_LAYOUT_ALIGN = "align";
  private static final String MSG_ERROR_BROWSER = "ERROR DE CONEXIÓN CON NAVEGADOR WEB";
  private static final String MSG_ERROR_FATAL_INTERNAL = "ERROR FATAL INTERNO";
  private static final String MSG_ERROR_GUI = "ERROR DE INTERFAZ GRÁFICA";
  private static final String MSG_ERROR_INTERNAL_FILES = "ERROR DE LECTURA DE ARCHIVOS INTERNOS";
  private static final String POSITION_COMBOBOX_1 = "Defensores centrales";
  private static final String POSITION_COMBOBOX_2 = "Defensores laterales";
  private static final String POSITION_COMBOBOX_3 = "Mediocampistas";
  private static final String POSITION_COMBOBOX_4 = "Delanteros";
  private static final String POSITION_COMBOBOX_5 = "Arqueros";
  private static final String PROGRAM_AUTHOR_GITHUB_USERNAME = "akmsw";
  private static final String TITLE_HELP_PAGE_1 = "Introducción";
  private static final String TITLE_HELP_PAGE_2 = "Criterios establecidos";
  private static final String TITLE_HELP_PAGE_3 = "Ingreso de jugadores";
  private static final String TITLE_HELP_PAGE_4 = "Anclajes";
  private static final String TITLE_HELP_PAGE_5 = "Puntuaciones";
  private static final String TITLE_HELP_PAGE_6 = "Distribución aleatoria";
  private static final String TITLE_HELP_PAGE_7 = "Distribución por puntuaciones";
  private static final String TITLE_HELP_PAGE_8 = "Sugerencias, reportes y contacto";

  // ---------------------------------------- Public constants ----------------------------------

  public static final int INDEX_HELP_PAGE_FILENAME = 1;
  public static final int INDEX_HELP_PAGE_TITLE = 0;
  public static final int PLAYERS_PER_TEAM = 7;
  public static final int MAX_NAME_LEN = 10;
  public static final int MAX_PLAYERS_PER_ANCHORAGE = PLAYERS_PER_TEAM - 1;
  public static final int MAX_ANCHORED_PLAYERS = 2 * MAX_PLAYERS_PER_ANCHORAGE;
  public static final int MIN_PLAYERS_PER_ANCHORAGE = 2;
  public static final int MIX_BY_SKILLS = 1;
  public static final int MIX_RANDOM = 0;
  public static final int ROUNDED_BORDER_ARC_GENERAL = 30;
  public static final int ROUNDED_BORDER_ARC_SCROLLBAR = 20;
  public static final int ROUNDED_BORDER_ARC_SPINNER = 10;
  public static final int ROUNDED_BORDER_ARC_TABLE_CELLS = 15;
  public static final int ROUNDED_BORDER_INSETS_GENERAL = 8;
  public static final int ROUNDED_BORDER_INSETS_SCROLLPANE = 18;
  public static final int SIZE_FONT_TITLE_LABEL = 40;
  public static final int SIZE_FONT_AUTHOR_LABEL = 30;
  public static final int SIZE_FONT_VERSION_LABEL = 16;
  public static final int SKILL_INI = 1;
  public static final int SKILL_MAX = 5;
  public static final int SKILL_MIN = 1;
  public static final int SKILL_STEP = 1;
  public static final int STROKE_BUTTON_ARROW = 5;

  public static final float FONT_SIZE = 18f;

  public static final String ERROR_MESSAGE_TITLE = "¡Error!";
  public static final String FILENAME_FONT = "comfortaa.ttf";
  public static final String FILENAME_PDA = "dist.pda";
  public static final String MIG_LAYOUT_CENTER = "center";
  public static final String MIG_LAYOUT_EAST = "east";
  public static final String MIG_LAYOUT_GROW = "grow";
  public static final String MIG_LAYOUT_GROWX = MIG_LAYOUT_GROW + "x";
  public static final String MIG_LAYOUT_ALIGN_CENTER = MIG_LAYOUT_ALIGN + " center";
  public static final String MIG_LAYOUT_ALIGN_LEFT = MIG_LAYOUT_ALIGN + " left";
  public static final String MIG_LAYOUT_ALIGN_RIGHT = MIG_LAYOUT_ALIGN + " right";
  public static final String MIG_LAYOUT_PUSH = "push";
  public static final String MIG_LAYOUT_PUSHX = MIG_LAYOUT_PUSH + "x";
  public static final String MIG_LAYOUT_SPAN = "span";
  public static final String MIG_LAYOUT_SPAN2 = MIG_LAYOUT_SPAN + "2";
  public static final String MIG_LAYOUT_WEST = "west";
  public static final String MIG_LAYOUT_WRAP = "wrap";
  public static final String MIG_LAYOUT_WRAP_2 = MIG_LAYOUT_WRAP + " 2";
  public static final String MIX_OPTION_RANDOM = "Aleatoriamente";
  public static final String MIX_OPTION_BY_SKILL = "Por puntuaciones";
  public static final String MSG_ERROR_NULL_RESOURCE = "ERROR EN OBTENCIÓN DE RECURSOS GRÁFICOS";
  public static final String MSG_ERROR_INVALID_STRING = "El nombre del jugador debe estar formado"
                                                        + " por letras de la A a la Z";
  public static final String MSG_ERROR_INVALID_NAME = "El nombre del jugador no puede estar vacío,"
                                                      + " tener más de " + MAX_NAME_LEN
                                                      + " caracteres, o estar repetido";
  public static final String PATH_DOCS = "docs/";
  public static final String PATH_HELP_DOCS = PATH_DOCS + "help/";
  public static final String PATH_ICO = "icons/";
  public static final String PATH_IMG = "img/";
  public static final String PATH_TTF = "fonts/";
  public static final String POSITION_CENTRAL_DEFENDERS = "DEFENSORES CENTRALES";
  public static final String POSITION_FORWARDS = "DELANTEROS";
  public static final String POSITION_GOALKEEPERS = "ARQUEROS";
  public static final String POSITION_LATERAL_DEFENDERS = "DEFENSORES LATERALES";
  public static final String POSITION_MIDFIELDERS = "MEDIOCAMPISTAS";
  public static final String PROGRAM_TITLE = "armame el doparti"; // Must be on lowercase
  public static final String PROGRAM_VERSION = "v3.0";
  public static final String PROGRAM_AUTHOR = "@" + PROGRAM_AUTHOR_GITHUB_USERNAME;
  public static final String REGEX_NAMES_VALIDATION = "[a-z A-ZÁÉÍÓÚáéíóúñÑ]+";
  public static final String REGEX_PDA_DATA_RETRIEVE = "[CLMFG].+>.+";
  public static final String REGEX_PLAYERS_AMOUNT = "(?!(?<=" + PLAYERS_PER_TEAM + ")\\d).";
  public static final String URL_CONTACT = "https://github.com/" + PROGRAM_AUTHOR_GITHUB_USERNAME;
  public static final String URL_ISSUES = URL_CONTACT + "/" + PROGRAM_TITLE.replace(" ", "-")
                                          + "/issues";

  public static final Color COLOR_GREEN_DARK = Color.decode(HEX_CODE_GREEN_DARK);
  public static final Color COLOR_GREEN_DARK_MEDIUM = Color.decode(HEX_CODE_GREEN_DARK_MEDIUM);
  public static final Color COLOR_GREEN_MEDIUM = Color.decode(HEX_CODE_GREEN_MEDIUM);
  public static final Color COLOR_GREEN_MEDIUM_LIGHT = Color.decode(HEX_CODE_GREEN_MEDIUM_LIGHT);
  public static final Color COLOR_GREEN_LIGHT = Color.decode(HEX_CODE_GREEN_LIGHT);
  public static final Color COLOR_GREEN_LIGHT_WHITE = Color.decode(HEX_CODE_GREEN_LIGHT_WHITE);

  public static final ImageIcon ICON_BACKGROUND = CommonFunctions.createImage(
      FILENAME_IMAGE_BACKGROUND
  );
  public static final ImageIcon ICON_CB_D_S = CommonFunctions.createImageIcon(
      FILENAME_ICON_CB_DISABLED_SELECTED
  );
  public static final ImageIcon ICON_CB_D_US = CommonFunctions.createImageIcon(
      FILENAME_ICON_CB_DISABLED_UNSELECTED
  );
  public static final ImageIcon ICON_CB_E_S_F = CommonFunctions.createImageIcon(
      FILENAME_ICON_CB_ENABLED_SELECTED_FOCUSED
  );
  public static final ImageIcon ICON_CB_E_S_P = CommonFunctions.createImageIcon(
      FILENAME_ICON_CB_ENABLED_SELECTED_PRESSED
  );
  public static final ImageIcon ICON_CB_E_S_UF = CommonFunctions.createImageIcon(
      FILENAME_ICON_CB_ENABLED_SELECTED_UNFOCUSED
  );
  public static final ImageIcon ICON_CB_E_US_F = CommonFunctions.createImageIcon(
      FILENAME_ICON_CB_ENABLED_UNSELECTED_FOCUSED
  );
  public static final ImageIcon ICON_CB_E_US_P = CommonFunctions.createImageIcon(
      FILENAME_ICON_CB_ENABLED_UNSELECTED_PRESSED
  );
  public static final ImageIcon ICON_CB_E_US_UF = CommonFunctions.createImageIcon(
      FILENAME_ICON_CB_ENABLED_UNSELECTED_UNFOCUSED
  );
  public static final ImageIcon ICON_MAIN = CommonFunctions.createImageIcon(
      FILENAME_ICON_MAIN
  );
  public static final ImageIcon ICON_DIALOG = CommonFunctions.scaleImageIcon(
      ICON_MAIN, ICON_SCALE, ICON_SCALE, Image.SCALE_SMOOTH
  );

  /**
   * Possible players distribution methods.
   */
  public static final List<String> OPTIONS_MIX = Collections.unmodifiableList(
      Arrays.asList(
        MIX_OPTION_RANDOM,
        MIX_OPTION_BY_SKILL
      )
  );

  /**
   * Positions to show in the names input view combobox.
   */
  public static final List<String> OPTIONS_POSITIONS_COMBOBOX = Collections.unmodifiableList(
      Arrays.asList(
        POSITION_COMBOBOX_1,
        POSITION_COMBOBOX_2,
        POSITION_COMBOBOX_3,
        POSITION_COMBOBOX_4,
        POSITION_COMBOBOX_5
      )
  );

  /**
   * Anchorages colors list used as background color for the results table cells.
   */
  public static final List<Color> COLORS_ANCHORAGES = Collections.unmodifiableList(
      Arrays.asList(
          Color.decode(HEX_CODE_ANCHORAGE_COLOR_1),
          Color.decode(HEX_CODE_ANCHORAGE_COLOR_2),
          Color.decode(HEX_CODE_ANCHORAGE_COLOR_3),
          Color.decode(HEX_CODE_ANCHORAGE_COLOR_4),
          Color.decode(HEX_CODE_ANCHORAGE_COLOR_5),
          Color.decode(HEX_CODE_ANCHORAGE_COLOR_6)
      )
  );

  /**
   * Map of errors and their corresponding exit code.
   */
  public static final Map<Error, Integer> MAP_ERROR_CODE = Map.of(
      Error.BROWSER_ERROR, EXIT_CODE_ERROR_BROWSER,
      Error.GUI_ERROR, EXIT_CODE_ERROR_GUI,
      Error.FATAL_INTERNAL_ERROR, EXIT_CODE_ERROR_INTERNAL_FATAL,
      Error.INTERNAL_FILES_ERROR, EXIT_CODE_ERROR_INTERNAL_FILES
  );

  /**
   * Map of errors and their corresponding error message to display.
   */
  public static final Map<Error, String> MAP_ERROR_MESSAGE = Map.of(
      Error.BROWSER_ERROR, MSG_ERROR_BROWSER,
      Error.FATAL_INTERNAL_ERROR, MSG_ERROR_FATAL_INTERNAL,
      Error.GUI_ERROR, MSG_ERROR_GUI,
      Error.INTERNAL_FILES_ERROR, MSG_ERROR_INTERNAL_FILES
  );

  /**
   * Map of help pages titles and filenames.
   */
  public static final Map<Integer, List<String>> MAP_HELP_PAGES_FILES = Map.of(
      0, Arrays.asList(TITLE_HELP_PAGE_1, FILENAME_HELP_PAGE_1),
      1, Arrays.asList(TITLE_HELP_PAGE_2, FILENAME_HELP_PAGE_2),
      2, Arrays.asList(TITLE_HELP_PAGE_3, FILENAME_HELP_PAGE_3),
      3, Arrays.asList(TITLE_HELP_PAGE_4, FILENAME_HELP_PAGE_4),
      4, Arrays.asList(TITLE_HELP_PAGE_5, FILENAME_HELP_PAGE_5),
      5, Arrays.asList(TITLE_HELP_PAGE_6, FILENAME_HELP_PAGE_6),
      6, Arrays.asList(TITLE_HELP_PAGE_7, FILENAME_HELP_PAGE_7),
      7, Arrays.asList(TITLE_HELP_PAGE_8, FILENAME_HELP_PAGE_8)
  );

  /**
   * Map of rendering hints used to paint custom components.
   */
  public static final Map<RenderingHints.Key, Object> MAP_RENDERING_HINTS = Map.of(
      RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
      RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY,
      RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE
  );

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Empty, private constructor. Not needed.
   */
  private Constants() {
    // Body not needed
  }
}