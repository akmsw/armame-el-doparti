package armameeldopartidesktop.utils.common;

import java.awt.Color;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import armameeldopartidesktop.models.enums.Error;
import armameeldopartidesktop.models.enums.Position;

/**
 * Common-use constants class.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class Constants {

  // ---------- Private constants -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private static final int      EXIT_CODE_ERROR_BROWSER                       = -1;
  private static final int      EXIT_CODE_ERROR_GUI                           = -2;
  private static final int      EXIT_CODE_ERROR_INTERNAL                      = -3;
  private static final int      EXIT_CODE_ERROR_FILES                         = -4;
  private static final int      SCALE_ICON_DIALOG                             = 32;
  private static final int      SCALE_ICON_MAIN                               = 60;

  private static final String   FILE_EXTENSION_HELP                           = ".hlp";
  private static final String   FILE_EXTENSION_PDA                            = ".pda";
  private static final String   FILE_EXTENSION_PNG                            = ".png";
  private static final String   FILE_EXTENSION_TTF                            = ".ttf";
  private static final String   FILE_EXTENSION_TXT                            = ".txt";
  private static final String   FILENAME_HELP_PAGE_1                          = "helpIntro"            + FILE_EXTENSION_HELP;
  private static final String   FILENAME_HELP_PAGE_2                          = "helpCriteria"         + FILE_EXTENSION_HELP;
  private static final String   FILENAME_HELP_PAGE_3                          = "helpNames"            + FILE_EXTENSION_HELP;
  private static final String   FILENAME_HELP_PAGE_4                          = "helpAnchorages"       + FILE_EXTENSION_HELP;
  private static final String   FILENAME_HELP_PAGE_5                          = "helpScores"           + FILE_EXTENSION_HELP;
  private static final String   FILENAME_HELP_PAGE_6                          = "helpRandomMix"        + FILE_EXTENSION_HELP;
  private static final String   FILENAME_HELP_PAGE_7                          = "helpBySkillPointsMix" + FILE_EXTENSION_HELP;
  private static final String   FILENAME_HELP_PAGE_8                          = "helpContact"          + FILE_EXTENSION_HELP;
  private static final String   FILENAME_ICON_CB_DISABLED_SELECTED            = "cb_d_s"               + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_CB_DISABLED_UNSELECTED          = "cb_d_us"              + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_CB_ENABLED_SELECTED_FOCUSED     = "cb_e_s_f"             + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_CB_ENABLED_SELECTED_PRESSED     = "cb_e_s_p"             + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_CB_ENABLED_SELECTED_UNFOCUSED   = "cb_e_s_uf"            + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_CB_ENABLED_UNSELECTED_FOCUSED   = "cb_e_us_f"            + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_CB_ENABLED_UNSELECTED_PRESSED   = "cb_e_us_p"            + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_CB_ENABLED_UNSELECTED_UNFOCUSED = "cb_e_us_uf"           + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_OP_ERROR                        = "op_e"                 + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_OP_INFORMATION                  = "op_i"                 + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_OP_QUESTION                     = "op_q"                 + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_OP_WARNING                      = "op_w"                 + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_RB_DISABLED_SELECTED            = "rb_d_s"               + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_RB_DISABLED_UNSELECTED          = "rb_d_us"              + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_RB_ENABLED_SELECTED_FOCUSED     = "rb_e_s_f"             + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_RB_ENABLED_SELECTED_PRESSED     = "rb_e_s_p"             + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_RB_ENABLED_SELECTED_UNFOCUSED   = "rb_e_s_uf"            + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_RB_ENABLED_UNSELECTED_FOCUSED   = "rb_e_us_f"            + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_RB_ENABLED_UNSELECTED_PRESSED   = "rb_e_us_p"            + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_RB_ENABLED_UNSELECTED_UNFOCUSED = "rb_e_us_uf"           + FILE_EXTENSION_PNG;
  private static final String   FILENAME_ICON_MAIN                            = "main_icon"            + FILE_EXTENSION_PNG;
  private static final String   FILENAME_IMAGE_BACKGROUND                     = "bg"                   + FILE_EXTENSION_PNG;
  private static final String   HEX_CODE_GREEN_DARK                           = "#29474a";
  private static final String   HEX_CODE_GREEN_DARK_MEDIUM                    = "#4b6560";
  private static final String   HEX_CODE_GREEN_MEDIUM                         = "#6d8276";
  private static final String   HEX_CODE_GREEN_MEDIUM_LIGHT                   = "#8fa08c";
  private static final String   HEX_CODE_GREEN_LIGHT                          = "#b0bda2";
  private static final String   HEX_CODE_GREEN_LIGHT_WHITE                    = "#d8ded1";
  private static final String   HEX_CODE_ANCHORAGE_COLOR_1                    = "#e5af8d";
  private static final String   HEX_CODE_ANCHORAGE_COLOR_2                    = "#74b1d1";
  private static final String   HEX_CODE_ANCHORAGE_COLOR_3                    = "#ffee99";
  private static final String   HEX_CODE_ANCHORAGE_COLOR_4                    = "#85d689";
  private static final String   HEX_CODE_ANCHORAGE_COLOR_5                    = "#a995c9";
  private static final String   HEX_CODE_ANCHORAGE_COLOR_6                    = "#f27c7c";
  private static final String   MIG_LAYOUT_ALIGN                              = "align";
  private static final String   MSG_ERROR_BROWSER                             = "ERROR DE CONEXIÓN CON NAVEGADOR WEB";
  private static final String   MSG_ERROR_FILES                               = "ERROR DE LECTURA DE ARCHIVOS INTERNOS";
  private static final String   MSG_ERROR_GUI                                 = "ERROR DE INTERFAZ GRÁFICA";
  private static final String   MSG_ERROR_INTERNAL                            = "ERROR FATAL INTERNO";
  private static final String   PROGRAM_AUTHOR_GITHUB_USERNAME                = "xinaras";
  private static final String   TITLE_HELP_PAGE_1                             = "Introducción";
  private static final String   TITLE_HELP_PAGE_2                             = "Criterios establecidos";
  private static final String   TITLE_HELP_PAGE_3                             = "Jugadores";
  private static final String   TITLE_HELP_PAGE_4                             = "Anclajes";
  private static final String   TITLE_HELP_PAGE_5                             = "Puntuaciones";
  private static final String   TITLE_HELP_PAGE_6                             = "Distribución aleatoria";
  private static final String   TITLE_HELP_PAGE_7                             = "Distribución por puntuaciones";
  private static final String   TITLE_HELP_PAGE_8                             = "Sugerencias, reportes y contacto";

  private static final Color    COLOR_ANCHORAGE_1                             = Color.decode(HEX_CODE_ANCHORAGE_COLOR_1);
  private static final Color    COLOR_ANCHORAGE_2                             = Color.decode(HEX_CODE_ANCHORAGE_COLOR_2);
  private static final Color    COLOR_ANCHORAGE_3                             = Color.decode(HEX_CODE_ANCHORAGE_COLOR_3);
  private static final Color    COLOR_ANCHORAGE_4                             = Color.decode(HEX_CODE_ANCHORAGE_COLOR_4);
  private static final Color    COLOR_ANCHORAGE_5                             = Color.decode(HEX_CODE_ANCHORAGE_COLOR_5);
  private static final Color    COLOR_ANCHORAGE_6                             = Color.decode(HEX_CODE_ANCHORAGE_COLOR_6);

  // ---------- Public constants --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public static final int       ERROR_CODE_NO_AVAILABLE_TEAM                  = -1;
  public static final int       INDEX_HELP_PAGE_FILENAME                      = 1;
  public static final int       INDEX_HELP_PAGE_TITLE                         = 0;
  public static final int       PLAYERS_PER_TEAM                              = 7;
  public static final int       PLAYERS_TOTAL                                 = PLAYERS_PER_TEAM * 2;
  public static final int       MAX_NAME_LEN                                  = 10;
  public static final int       MAX_ANCHORAGE_SIZE                            = PLAYERS_PER_TEAM - 1;
  public static final int       MAX_TOTAL_ANCHORED_PLAYERS                    = MAX_ANCHORAGE_SIZE * 2;
  public static final int       MIN_ANCHORAGE_SIZE                            = 2;
  public static final int       PLAYER_NO_ANCHORAGE_ASSIGNED                  = 0;
  public static final int       PLAYER_NO_SKILL_POINTS_ASSIGNED               = 0;
  public static final int       PLAYER_NO_TEAM_ASSIGNED                       = 0;
  public static final int       ROUNDED_BORDER_ARC_BUTTON_DIALOG              = 15;
  public static final int       ROUNDED_BORDER_ARC_COMBOBOX_SELECTOR          = 10;
  public static final int       ROUNDED_BORDER_ARC_GENERAL                    = 30;
  public static final int       ROUNDED_BORDER_ARC_SCROLLBAR                  = 20;
  public static final int       ROUNDED_BORDER_ARC_SEPARATOR                  = 4;
  public static final int       ROUNDED_BORDER_ARC_SPINNER                    = 10;
  public static final int       ROUNDED_BORDER_ARC_TABLE_CELLS                = 15;
  public static final int       ROUNDED_BORDER_ARC_TOOLTIP                    = 20;
  public static final int       ROUNDED_BORDER_INSETS_COMBOBOX                = 6;
  public static final int       ROUNDED_BORDER_INSETS_GENERAL                 = 8;
  public static final int       ROUNDED_BORDER_INSETS_TOOLTIP                 = 6;
  public static final int       SIZE_BUTTON_DIALOG_MIN_WIDTH                  = 28;
  public static final int       SIZE_BUTTON_DIALOG_MIN_HEIGHT                 = 36;
  public static final int       SIZE_FONT_TITLE_LABEL                         = 44;
  public static final int       SIZE_FONT_AUTHOR_LABEL                        = 30;
  public static final int       SIZE_FONT_VERSION_LABEL                       = 16;
  public static final int       SKILL_INI                                     = 1;
  public static final int       SKILL_MAX                                     = 5;
  public static final int       SKILL_MIN                                     = 1;
  public static final int       SKILL_STEP                                    = 1;
  public static final int       STROKE_BUTTON_ARROW                           = 5;
  public static final int       DELAY_TOOLTIP_DISMISS                         = 5000;
  public static final int       DELAY_TOOLTIP_INITIAL                         = 300;

  public static final float     SIZE_FONT_DEFAULT                             = 18f;

  public static final String    DATE_FORMAT                                   = "yyyy-MM-dd HH:mm:ss";
  public static final String    FILENAME_ERROR_REPORT                         = "errorReport" + FILE_EXTENSION_TXT;
  public static final String    FILENAME_FONT                                 = "comfortaa"   + FILE_EXTENSION_TTF;
  public static final String    FILENAME_PDA                                  = "dist"        + FILE_EXTENSION_PDA;
  public static final String    MIG_LAYOUT_CENTER                             = "center";
  public static final String    MIG_LAYOUT_EAST                               = "east";
  public static final String    MIG_LAYOUT_GROW                               = "grow";
  public static final String    MIG_LAYOUT_GROWX                              = MIG_LAYOUT_GROW  + "x";
  public static final String    MIG_LAYOUT_GROWY                              = MIG_LAYOUT_GROW  + "y";
  public static final String    MIG_LAYOUT_ALIGN_CENTER                       = MIG_LAYOUT_ALIGN + " center";
  public static final String    MIG_LAYOUT_ALIGN_LEFT                         = MIG_LAYOUT_ALIGN + " left";
  public static final String    MIG_LAYOUT_ALIGN_RIGHT                        = MIG_LAYOUT_ALIGN + " right";
  public static final String    MIG_LAYOUT_NORTH                              = "north";
  public static final String    MIG_LAYOUT_PUSH                               = "push";
  public static final String    MIG_LAYOUT_PUSHX                              = MIG_LAYOUT_PUSH + "x";
  public static final String    MIG_LAYOUT_PUSHY                              = MIG_LAYOUT_PUSH + "y";
  public static final String    MIG_LAYOUT_SOUTH                              = "south";
  public static final String    MIG_LAYOUT_SPAN                               = "span";
  public static final String    MIG_LAYOUT_SPAN2                              = MIG_LAYOUT_SPAN + "2";
  public static final String    MIG_LAYOUT_WEST                               = "west";
  public static final String    MIG_LAYOUT_WRAP                               = "wrap";
  public static final String    MIG_LAYOUT_WRAP_2                             = MIG_LAYOUT_WRAP + " 2";
  public static final String    MSG_ERROR_ILLEGAL_DIALOG_TYPE                 = "Invalid dialog message type received.";
  public static final String    MSG_ERROR_NAME_ALREADY_EXISTS                 = "El nombre del jugador no puede estar repetido";
  public static final String    MSG_ERROR_NAME_INVALID                        = "El nombre del jugador no puede tener caracteres especiales";
  public static final String    MSG_ERROR_NAME_LENGTH                         = "El nombre del jugador no puede" + System.lineSeparator() + "tener más de " + MAX_NAME_LEN + " caracteres";
  public static final String    MSG_ERROR_NO_AVAILABLE_TEAM                   = "No available team for current players configuration.";
  public static final String    MSG_ERROR_NO_OPTIONAL_CONTENT                 = "No available content to retrieve in Optional object.";
  public static final String    MSG_ERROR_NULL_GUI_RESOURCE                   = "Error en obtención de recursos gráficos";
  public static final String    MSG_ERROR_STRING_BLANK                        = "El nombre del jugador no puede estar vacío";
  public static final String    MSG_ERROR_STRING_NUMERIC                      = "El nombre del jugador debe tener" + System.lineSeparator() + "al menos una letra de la A a la Z";
  public static final String    MSG_INFO_ANCHORAGES_NO_SELECTION              = "No hay jugadores seleccionados para anclar";
  public static final String    MSG_WARNING_ANCHORAGE_LOWER_LIMIT             = "No puede haber menos de " + MIN_ANCHORAGE_SIZE + " jugadores en un anclaje";
  public static final String    MSG_WARNING_ANCHORAGE_UPPER_LIMIT             = "No puede haber más de " + MAX_ANCHORAGE_SIZE + " jugadores en un mismo anclaje";
  public static final String    MSG_WARNING_ANCHORAGES_CONFLICTS              = "Existen conflictos entre anclajes";
  public static final String    MSG_WARNING_ANCHORAGES_HALF_SET_LIMIT         = "No puede haber más de la mitad de jugadores" + System.lineSeparator() + "de una misma posición en un mismo anclaje";
  public static final String    MSG_WARNING_ANCHORAGES_TOTAL_LIMITS           = "No puede haber más de " + MAX_TOTAL_ANCHORED_PLAYERS + " jugadores anclados en total";
  public static final String    PATH_DOCS                                     = "docs/";
  public static final String    PATH_HELP_DOCS                                = PATH_DOCS + "help/";
  public static final String    PATH_ICO                                      = "icons/";
  public static final String    PATH_IMG                                      = "img/";
  public static final String    PATH_TTF                                      = "fonts/";
  public static final String    PLAYER_NO_NAME_ASSIGNED                       = "";
  public static final String    POSITION_CENTRAL_DEFENDERS                    = "DEFENSORES CENTRALES";
  public static final String    POSITION_FORWARDS                             = "DELANTEROS";
  public static final String    POSITION_GOALKEEPERS                          = "ARQUEROS";
  public static final String    POSITION_LATERAL_DEFENDERS                    = "DEFENSORES LATERALES";
  public static final String    POSITION_MIDFIELDERS                          = "MEDIOCAMPISTAS";
  public static final String    PROGRAM_TITLE                                 = "armame el doparti";
  public static final String    PROGRAM_VERSION                               = "v3.0.0";
  public static final String    PROGRAM_AUTHOR                                = "@" + PROGRAM_AUTHOR_GITHUB_USERNAME;
  public static final String    REGEX_NUMERIC_STRING                          = "\\d+$";
  public static final String    REGEX_PDA_DATA_RETRIEVE                       = "[CLMFG].+>.+";
  public static final String    REGEX_PLAYERS_COUNT                           = "(?!(?<=" + PLAYERS_PER_TEAM + ")\\d).";
  public static final String    REGEX_SPECIAL_CHARACTERS                      = ".*[^a-z\sA-ZÁÉÍÓÚáéíóúñÑ\\d]+.*";
  public static final String    REPOSITORY_NAME                               = (PROGRAM_TITLE + " desktop").replace(" ", "-");
  public static final String    TEXT_BUTTON_DIALOG_CANCEL                     = "Cancelar";
  public static final String    TEXT_BUTTON_DIALOG_NO                         = "No";
  public static final String    TEXT_BUTTON_DIALOG_OK                         = "Aceptar";
  public static final String    TEXT_BUTTON_DIALOG_YES                        = "Sí";
  public static final String    TITLE_MESSAGE_ERROR                           = "¡Error!";
  public static final String    TITLE_MESSAGE_INFORMATION                     = "Información";
  public static final String    TITLE_MESSAGE_QUESTION                        = "Seleccione una opción";
  public static final String    TITLE_MESSAGE_WARNING                         = "¡Advertencia!";
  public static final String    TITLE_VIEW_ANCHORAGES                         = "Anclaje de jugadores";
  public static final String    TITLE_VIEW_HELP                               = "Ayuda";
  public static final String    TITLE_VIEW_MAIN_MENU                          = CommonFunctions.capitalize(PROGRAM_TITLE) + " " + PROGRAM_VERSION;
  public static final String    TITLE_VIEW_NAMES_INPUT                        = "Ingreso de jugadores";
  public static final String    TITLE_VIEW_SKILL_POINTS_INPUT                 = "Ingreso de puntuaciones";
  public static final String    TOOLTIP_MSG_PROGRAM_VERSION                   = "Versión del programa";
  public static final String    URL_CONTACT                                   = "https://github.com/" + PROGRAM_AUTHOR_GITHUB_USERNAME;
  public static final String    URL_REPOSITORY                                = URL_CONTACT + "/" + REPOSITORY_NAME;
  public static final String    URL_ISSUES                                    = URL_REPOSITORY + "/issues";

  public static final Color     COLOR_GREEN_DARK                              = Color.decode(HEX_CODE_GREEN_DARK);
  public static final Color     COLOR_GREEN_DARK_MEDIUM                       = Color.decode(HEX_CODE_GREEN_DARK_MEDIUM);
  public static final Color     COLOR_GREEN_MEDIUM                            = Color.decode(HEX_CODE_GREEN_MEDIUM);
  public static final Color     COLOR_GREEN_MEDIUM_LIGHT                      = Color.decode(HEX_CODE_GREEN_MEDIUM_LIGHT);
  public static final Color     COLOR_GREEN_LIGHT                             = Color.decode(HEX_CODE_GREEN_LIGHT);
  public static final Color     COLOR_GREEN_LIGHT_WHITE                       = Color.decode(HEX_CODE_GREEN_LIGHT_WHITE);
  public static final Color     COLOR_TRANSPARENT                             = new Color(0, 0, 0, 0);

  public static final ImageIcon ICON_BACKGROUND                               = CommonFunctions.createImage(FILENAME_IMAGE_BACKGROUND);
  public static final ImageIcon ICON_CB_D_S                                   = CommonFunctions.createImageIcon(FILENAME_ICON_CB_DISABLED_SELECTED);
  public static final ImageIcon ICON_CB_D_US                                  = CommonFunctions.createImageIcon(FILENAME_ICON_CB_DISABLED_UNSELECTED);
  public static final ImageIcon ICON_CB_E_S_F                                 = CommonFunctions.createImageIcon(FILENAME_ICON_CB_ENABLED_SELECTED_FOCUSED);
  public static final ImageIcon ICON_CB_E_S_P                                 = CommonFunctions.createImageIcon(FILENAME_ICON_CB_ENABLED_SELECTED_PRESSED);
  public static final ImageIcon ICON_CB_E_S_UF                                = CommonFunctions.createImageIcon(FILENAME_ICON_CB_ENABLED_SELECTED_UNFOCUSED);
  public static final ImageIcon ICON_CB_E_US_F                                = CommonFunctions.createImageIcon(FILENAME_ICON_CB_ENABLED_UNSELECTED_FOCUSED);
  public static final ImageIcon ICON_CB_E_US_P                                = CommonFunctions.createImageIcon(FILENAME_ICON_CB_ENABLED_UNSELECTED_PRESSED);
  public static final ImageIcon ICON_CB_E_US_UF                               = CommonFunctions.createImageIcon(FILENAME_ICON_CB_ENABLED_UNSELECTED_UNFOCUSED);
  public static final ImageIcon ICON_OP_ERROR                                 = CommonFunctions.createImageIcon(FILENAME_ICON_OP_ERROR);
  public static final ImageIcon ICON_OP_INFORMATION                           = CommonFunctions.createImageIcon(FILENAME_ICON_OP_INFORMATION);
  public static final ImageIcon ICON_OP_QUESTION                              = CommonFunctions.createImageIcon(FILENAME_ICON_OP_QUESTION);
  public static final ImageIcon ICON_OP_WARNING                               = CommonFunctions.createImageIcon(FILENAME_ICON_OP_WARNING);
  public static final ImageIcon ICON_RB_D_S                                   = CommonFunctions.createImageIcon(FILENAME_ICON_RB_DISABLED_SELECTED);
  public static final ImageIcon ICON_RB_D_US                                  = CommonFunctions.createImageIcon(FILENAME_ICON_RB_DISABLED_UNSELECTED);
  public static final ImageIcon ICON_RB_E_S_F                                 = CommonFunctions.createImageIcon(FILENAME_ICON_RB_ENABLED_SELECTED_FOCUSED);
  public static final ImageIcon ICON_RB_E_S_P                                 = CommonFunctions.createImageIcon(FILENAME_ICON_RB_ENABLED_SELECTED_PRESSED);
  public static final ImageIcon ICON_RB_E_S_UF                                = CommonFunctions.createImageIcon(FILENAME_ICON_RB_ENABLED_SELECTED_UNFOCUSED);
  public static final ImageIcon ICON_RB_E_US_F                                = CommonFunctions.createImageIcon(FILENAME_ICON_RB_ENABLED_UNSELECTED_FOCUSED);
  public static final ImageIcon ICON_RB_E_US_P                                = CommonFunctions.createImageIcon(FILENAME_ICON_RB_ENABLED_UNSELECTED_PRESSED);
  public static final ImageIcon ICON_RB_E_US_UF                               = CommonFunctions.createImageIcon(FILENAME_ICON_RB_ENABLED_UNSELECTED_UNFOCUSED);
  public static final ImageIcon ICON_MAIN                                     = CommonFunctions.createImageIcon(FILENAME_ICON_MAIN);
  public static final ImageIcon ICON_MAIN_SCALED                              = CommonFunctions.scaleImageIcon(ICON_MAIN, SCALE_ICON_MAIN, SCALE_ICON_MAIN, Image.SCALE_SMOOTH);
  public static final ImageIcon ICON_DIALOG_ERROR                             = CommonFunctions.scaleImageIcon(ICON_OP_ERROR, SCALE_ICON_DIALOG, SCALE_ICON_DIALOG, Image.SCALE_SMOOTH);
  public static final ImageIcon ICON_DIALOG_INFORMATION                       = CommonFunctions.scaleImageIcon(ICON_OP_INFORMATION, SCALE_ICON_DIALOG, SCALE_ICON_DIALOG, Image.SCALE_SMOOTH);
  public static final ImageIcon ICON_DIALOG_QUESTION                          = CommonFunctions.scaleImageIcon(ICON_OP_QUESTION, SCALE_ICON_DIALOG, SCALE_ICON_DIALOG, Image.SCALE_SMOOTH);
  public static final ImageIcon ICON_DIALOG_WARNING                           = CommonFunctions.scaleImageIcon(ICON_OP_WARNING, SCALE_ICON_DIALOG, SCALE_ICON_DIALOG, Image.SCALE_SMOOTH);

  public static final Insets    INSETS_COMBOBOX                               = new Insets(ROUNDED_BORDER_INSETS_COMBOBOX, ROUNDED_BORDER_INSETS_COMBOBOX, ROUNDED_BORDER_INSETS_COMBOBOX, ROUNDED_BORDER_INSETS_COMBOBOX);
  public static final Insets    INSETS_GENERAL                                = new Insets(ROUNDED_BORDER_INSETS_GENERAL, ROUNDED_BORDER_INSETS_GENERAL, ROUNDED_BORDER_INSETS_GENERAL, ROUNDED_BORDER_INSETS_GENERAL);
  public static final Insets    INSETS_LABEL                                  = new Insets(ROUNDED_BORDER_INSETS_GENERAL, ROUNDED_BORDER_INSETS_GENERAL, ROUNDED_BORDER_INSETS_GENERAL, ROUNDED_BORDER_INSETS_GENERAL);
  public static final Insets    INSETS_TOOLTIP                                = new Insets(ROUNDED_BORDER_INSETS_TOOLTIP, ROUNDED_BORDER_INSETS_TOOLTIP, ROUNDED_BORDER_INSETS_TOOLTIP, ROUNDED_BORDER_INSETS_TOOLTIP);

  /**
   * Positions to show in the names input view combobox.
   */
  public static final List<String> OPTIONS_POSITIONS_COMBOBOX = Collections.unmodifiableList(
    Arrays.asList(CommonFunctions.capitalize(POSITION_CENTRAL_DEFENDERS),
                  CommonFunctions.capitalize(POSITION_LATERAL_DEFENDERS),
                  CommonFunctions.capitalize(POSITION_MIDFIELDERS),
                  CommonFunctions.capitalize(POSITION_FORWARDS),
                  CommonFunctions.capitalize(POSITION_GOALKEEPERS))
  );

  /**
   * Anchorages colors list used as background color for the results table cells.
   */
  public static final List<Color> COLORS_ANCHORAGES = Collections.unmodifiableList(
    Arrays.asList(COLOR_ANCHORAGE_1,
                  COLOR_ANCHORAGE_2,
                  COLOR_ANCHORAGE_3,
                  COLOR_ANCHORAGE_4,
                  COLOR_ANCHORAGE_5,
                  COLOR_ANCHORAGE_6)
  );

  public static final Map<Position, String> MAP_POSITIONS = Map.of(
    Position.CENTRAL_DEFENDER, POSITION_CENTRAL_DEFENDERS,
    Position.LATERAL_DEFENDER, POSITION_LATERAL_DEFENDERS,
    Position.MIDFIELDER      , POSITION_MIDFIELDERS,
    Position.FORWARD         , POSITION_FORWARDS,
    Position.GOALKEEPER      , POSITION_GOALKEEPERS
  );

  /**
   * Map of errors and their corresponding exit code.
   */
  public static final Map<Error, Integer> MAP_ERROR_CODE = Map.of(
    Error.ERROR_BROWSER , EXIT_CODE_ERROR_BROWSER,
    Error.ERROR_FILES   , EXIT_CODE_ERROR_FILES,
    Error.ERROR_GUI     , EXIT_CODE_ERROR_GUI,
    Error.ERROR_INTERNAL, EXIT_CODE_ERROR_INTERNAL
  );

  /**
   * Map of errors and their corresponding error message to display.
   */
  public static final Map<Error, String> MAP_ERROR_MESSAGE = Map.of(
    Error.ERROR_BROWSER , MSG_ERROR_BROWSER,
    Error.ERROR_FILES   , MSG_ERROR_FILES,
    Error.ERROR_GUI     , MSG_ERROR_GUI,
    Error.ERROR_INTERNAL, MSG_ERROR_INTERNAL
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
    RenderingHints.KEY_ANTIALIASING  , RenderingHints.VALUE_ANTIALIAS_ON,
    RenderingHints.KEY_RENDERING     , RenderingHints.VALUE_RENDER_QUALITY,
    RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE
  );

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Empty, private, unused constructor.
   */
  private Constants() {
    // Body not needed
  }
}