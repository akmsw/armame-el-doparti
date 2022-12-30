package armameeldoparti.utils.common;

import armameeldoparti.models.Error;
import java.awt.Color;
import java.awt.Image;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 * Commonly-used and useful constants class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 18/10/2022
 */
public final class Constants {

  // ---------------------------------------- Private constants ---------------------------------

  private static final int EXIT_CODE_ERROR_BROWSER = -1;
  private static final int EXIT_CODE_ERROR_FATAL_INTERNAL = -2;
  private static final int EXIT_CODE_ERROR_GUI = -3;
  private static final int EXIT_CODE_ERROR_INTERNAL_FILES = -4;

  /**
   * Size, in pixels, of the scaled program icon (height and width).
   */
  private static final int ICON_SCALE = 50;

  private static final String FILENAME_ICON = "icon.png";
  private static final String MSG_ERROR_BROWSER = "ERROR DE CONEXIÓN CON NAVEGADOR WEB";
  private static final String MSG_ERROR_FATAL_INTERNAL = "ERROR FATAL";
  private static final String MSG_ERROR_GUI = "ERROR DE INTERFAZ GRÁFICA";
  private static final String MSG_ERROR_INTERNAL_FILES = "ERROR DE LECTURA DE ARCHIVOS INTERNOS";

  // ---------------------------------------- Public constants ----------------------------------

  public static final int PLAYERS_PER_TEAM = 7;
  public static final int MAX_NAME_LEN = 10;
  public static final int MAX_PLAYERS_PER_ANCHORAGE = PLAYERS_PER_TEAM - 1;
  public static final int MAX_ANCHORED_PLAYERS = 2 * MAX_PLAYERS_PER_ANCHORAGE;
  public static final int MIX_BY_SKILLS = 1;
  public static final int MIX_RANDOM = 0;
  public static final int SKILL_INI = 1;
  public static final int SKILL_MAX = 5;
  public static final int SKILL_MIN = 1;
  public static final int SKILL_STEP = 1;

  public static final float FONT_SIZE = 18f;

  public static final String ERROR_MESSAGE_TITLE = "¡Error!";
  public static final String FILENAME_BG_IMG = "bg.png";
  public static final String FILENAME_FONT = "comfortaa.ttf";
  public static final String FILENAME_PDA = "dist.pda";
  public static final String MIX_OPTION_RANDOM = "Aleatoriamente";
  public static final String MIX_OPTION_BY_SKILL = "Por puntuaciones";
  public static final String PATH_DOCS = "docs/";
  public static final String PATH_HELP_DOCS = PATH_DOCS + "help/";
  public static final String PATH_IMG = "img/";
  public static final String PATH_TTF = "fonts/";
  public static final String POSITION_CENTRAL_DEFENDERS = "DEFENSORES CENTRALES";
  public static final String POSITION_FORWARDS = "DELANTEROS";
  public static final String POSITION_GOALKEEPERS = "ARQUEROS";
  public static final String POSITION_LATERAL_DEFENDERS = "DEFENSORES LATERALES";
  public static final String POSITION_MIDFIELDERS = "MEDIOCAMPISTAS";
  public static final String PROGRAM_TITLE = "Armame el doparti";
  public static final String PROGRAM_VERSION = "v3.0";
  public static final String REGEX_NAMES_VALIDATION = "[a-z A-ZÁÉÍÓÚáéíóúñÑ]+";
  public static final String REGEX_PDA_DATA_RETRIEVE = "[CLMFG].+>.+";
  public static final String REGEX_PLAYERS_AMOUNT = "(?!(?<=" + PLAYERS_PER_TEAM + ")\\d).";
  public static final String URL_CONTACT = "https://github.com/akmsw";
  public static final String URL_ISSUES = "https://github.com/akmsw/armame-el-doparti/issues";

  public static final Color GREEN_DARK = new Color(41, 71, 74);
  public static final Color GREEN_MEDIUM = new Color(109, 130, 118);
  public static final Color GREEN_LIGHT = new Color(176, 189, 162);
  public static final Color YELLOW_LIGHT = new Color(255, 238, 153);

  /**
   * Standard-size program icon.
   */
  public static final ImageIcon ICON = new ImageIcon(
      Constants.class
               .getClassLoader()
               .getResource(PATH_IMG + FILENAME_ICON)
  );

  /**
   * Scaled program icon.
   */
  public static final ImageIcon ICON_SCALED = new ImageIcon(
      ICON.getImage()
          .getScaledInstance(ICON_SCALE, ICON_SCALE, Image.SCALE_SMOOTH)
  );

  /**
   * Map of errors and their corresponding exit code.
   */
  public static final Map<Error, Integer> errorCodes = Map.of(
      Error.BROWSER_ERROR, EXIT_CODE_ERROR_BROWSER,
      Error.FATAL_INTERNAL_ERROR, EXIT_CODE_ERROR_FATAL_INTERNAL,
      Error.GUI_ERROR, EXIT_CODE_ERROR_GUI,
      Error.INTERNAL_FILES_ERROR, EXIT_CODE_ERROR_INTERNAL_FILES
  );

  /**
   * Map of errors and their corresponding error message to display.
   */
  public static final Map<Error, String> errorMessages = Map.of(
      Error.BROWSER_ERROR, MSG_ERROR_BROWSER,
      Error.FATAL_INTERNAL_ERROR, MSG_ERROR_FATAL_INTERNAL,
      Error.GUI_ERROR, MSG_ERROR_GUI,
      Error.INTERNAL_FILES_ERROR, MSG_ERROR_INTERNAL_FILES
  );

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Empty, private constructor. Not needed.
   */
  private Constants() {
    // No body needed.
  }
}