package armameeldoparti.utils.common;

import armameeldoparti.models.Error;
import java.awt.Color;
import java.awt.Image;
import java.util.Map;
import java.util.Objects;
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

  private static final String MIG_LAYOUT_ALIGN = "align";
  private static final String MSG_ERROR_BROWSER = "ERROR DE CONEXIÓN CON NAVEGADOR WEB";
  private static final String MSG_ERROR_FATAL_INTERNAL = "ERROR FATAL INTERNO";
  private static final String MSG_ERROR_GUI = "ERROR DE INTERFAZ GRÁFICA";
  private static final String MSG_ERROR_INTERNAL_FILES = "ERROR DE LECTURA DE ARCHIVOS INTERNOS";

  // ---------------------------------------- Public constants ----------------------------------

  public static final int PLAYERS_PER_TEAM = 7;
  public static final int MAX_NAME_LEN = 10;
  public static final int MAX_PLAYERS_PER_ANCHORAGE = PLAYERS_PER_TEAM - 1;
  public static final int MAX_ANCHORED_PLAYERS = 2 * MAX_PLAYERS_PER_ANCHORAGE;
  public static final int MIX_BY_SKILLS = 1;
  public static final int MIX_RANDOM = 0;
  public static final int SIZE_FONT_TITLE_LABEL = 40;
  public static final int SIZE_FONT_AUTHOR_LABEL = SIZE_FONT_TITLE_LABEL - 10;
  public static final int SIZE_FONT_VERSION_LABEL = 16;
  public static final int SKILL_INI = 1;
  public static final int SKILL_MAX = 5;
  public static final int SKILL_MIN = 1;
  public static final int SKILL_STEP = 1;

  public static final float FONT_SIZE = 18f;

  public static final String ERROR_MESSAGE_TITLE = "¡Error!";
  public static final String FILENAME_ICON = "icon.png";
  public static final String FILENAME_FONT = "comfortaa.ttf";
  public static final String FILENAME_PDA = "dist.pda";
  public static final String MIX_OPTION_RANDOM = "Aleatoriamente";
  public static final String MIX_OPTION_BY_SKILL = "Por puntuaciones";
  public static final String MSG_ERROR_NULL_RESOURCE = "ERROR EN OBTENCIÓN DE RECURSOS GRÁFICOS";
  public static final String MSG_ERROR_INVALID_STRING = "El nombre del jugador debe estar formado"
                                                        + " por letras de la A a la Z";
  public static final String MSG_ERROR_INVALID_NAME = "El nombre del jugador no puede estar vacío,"
                                                      + " tener más de " + Constants.MAX_NAME_LEN
                                                      + " caracteres, o estar repetido";
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
  public static final String PROGRAM_AUTHOR = "@akmsw";
  public static final String REGEX_NAMES_VALIDATION = "[a-z A-ZÁÉÍÓÚáéíóúñÑ]+";
  public static final String REGEX_PDA_DATA_RETRIEVE = "[CLMFG].+>.+";
  public static final String REGEX_PLAYERS_AMOUNT = "(?!(?<=" + PLAYERS_PER_TEAM + ")\\d).";
  public static final String URL_CONTACT = "https://github.com/akmsw";
  public static final String URL_ISSUES = URL_CONTACT + "/armame-el-doparti/issues";
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
  public static final String MIG_LAYOUT_WRAP2 = MIG_LAYOUT_WRAP + " 2";

  public static final Color GREEN_DARK = new Color(41, 71, 74);
  public static final Color GREEN_MEDIUM = new Color(109, 130, 118);
  public static final Color GREEN_LIGHT = new Color(176, 189, 162);
  public static final Color YELLOW_LIGHT = new Color(255, 238, 153);

  /**
   * Standard-size program icon.
   */
  public static final ImageIcon ICON = new ImageIcon(
      Objects.requireNonNull(Constants.class
                                      .getClassLoader()
                                      .getResource(PATH_IMG + FILENAME_ICON),
                             Constants.MSG_ERROR_NULL_RESOURCE
      )
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

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Empty, private constructor. Not needed.
   */
  private Constants() {
    // Body not needed
  }
}