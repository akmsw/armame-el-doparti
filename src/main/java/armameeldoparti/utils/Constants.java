package armameeldoparti.utils;

import armameeldoparti.Main;
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

  /**
   * Size, in pixels, of the scaled program icon (height and width).
   */
  private static final int ICON_SCALE = 50;

  private static final String ICON_FILENAME = "icon.png";

  // ---------------------------------------- Public constants ----------------------------------

  public static final int PLAYERS_PER_TEAM = 7;
  public static final int MAX_NAME_LEN = 10;
  public static final int MAX_PLAYERS_PER_ANCHORAGE = PLAYERS_PER_TEAM - 1;
  public static final int MAX_ANCHORED_PLAYERS = 2 * MAX_PLAYERS_PER_ANCHORAGE;
  public static final int MIX_BY_SKILL = 1;
  public static final int MIX_RANDOM = 0;
  public static final int SKILL_INI = 1;
  public static final int SKILL_MAX = 5;
  public static final int SKILL_MIN = 1;
  public static final int SKILL_STEP = 1;

  public static final float FONT_SIZE = 18f;

  public static final String BG_IMG_FILENAME = "bg.png";
  public static final String DOCS_PATH = "docs/";
  public static final String ERROR_MESSAGE_TITLE = "¡Error!";
  public static final String FONT_NAME = "comfortaa.ttf";
  public static final String GROW = "grow";
  public static final String GROW_SPAN = "grow, span";
  public static final String GROWX = "growx";
  public static final String GROWX_SPAN = "growx, span";
  public static final String HELP_DOCS_PATH = DOCS_PATH + "help/";
  public static final String IMG_PATH = "img/";
  public static final String PDA_FILENAME = "dist.pda";
  public static final String PDA_DATA_RETRIEVE_REGEX = "[CLMFG].+>.+";
  public static final String PROGRAM_TITLE = "Armame el doparti";
  public static final String PROGRAM_VERSION = "v3.0";
  public static final String REGEX_NAMES_VALIDATION = "[a-z A-ZÁÉÍÓÚáéíóúñÑ]+";
  public static final String REGEX_PLAYERS_AMOUNT = "(?!(?<=" + PLAYERS_PER_TEAM + ")\\d).";
  public static final String TTF_PATH = "fonts/";

  public static final Color DARK_GREEN = new Color(41, 71, 74);
  public static final Color MEDIUM_GREEN = new Color(109, 130, 118);
  public static final Color LIGHT_GREEN = new Color(176, 189, 162);
  public static final Color LIGHT_YELLOW = new Color(255, 238, 153);

  /**
   * Standard-size program icon.
   */
  public static final ImageIcon ICON = new ImageIcon(
      Main.class
          .getClassLoader()
          .getResource(IMG_PATH + ICON_FILENAME)
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
      Error.GUI_ERROR, -1,
      Error.INTERNAL_FILES_ERROR, -2
  );

  /**
   * Map of errors and their corresponding error message to display.
   */
  public static final Map<Error, String> errorMessages = Map.of(
      Error.GUI_ERROR, "ERROR DE INTERFAZ GRÁFICA",
      Error.INTERNAL_FILES_ERROR, "ERROR DE LECTURA DE ARCHIVOS INTERNOS"
  );

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Empty, private constructor. Not needed.
   */
  private Constants() {
    // No body needed.
  }
}