package armameeldoparti.utils;

import armameeldoparti.controllers.AnchoragesController;
import armameeldoparti.controllers.HelpController;
import armameeldoparti.controllers.MainMenuController;
import armameeldoparti.controllers.NamesInputController;
import armameeldoparti.controllers.ResultsController;
import armameeldoparti.controllers.SkillsInputController;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.views.AnchoragesView;
import armameeldoparti.views.HelpView;
import armameeldoparti.views.MainMenuView;
import armameeldoparti.views.NamesInputView;
import armameeldoparti.views.ResultsView;
import armameeldoparti.views.SkillsInputView;
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
 * Clase principal, sólo para inicialización del programa y declaración de campos útiles.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 15/02/2021
 */
public final class Main {

  // ---------------------------------------- Constantes privadas -------------------------------

  private static final int ICON_SCALE = 50;

  // ---------------------------------------- Constantes públicas -------------------------------

  public static final int RANDOM_MIX = 0;
  public static final int BY_SKILLS_MIX = 1;
  public static final int PLAYERS_PER_TEAM = 7;
  public static final int MAX_PLAYERS_PER_ANCHORAGE = PLAYERS_PER_TEAM - 1;
  public static final int MAX_NAME_LEN = 10;

  /**
   * Valor inicial del campo de ingreso de puntuaciones.
   */
  public static final int SCORE_INI = 1;

  /**
   * Valor mínimo del campo de ingreso de puntuaciones.
   */
  public static final int SCORE_MIN = 1;

  /**
   * Valor máximo del campo de ingreso de puntuaciones.
   */
  public static final int SCORE_MAX = 5;

  /**
   * Paso utilizado para incremento y decremento
   * en los campos de ingreso de puntuaciones.
   */
  public static final int SCORE_STEP = 1;

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
   * Imagen estándar del icono de la aplicación.
   */
  public static final ImageIcon ICON = new ImageIcon(
      Main.class
          .getClassLoader()
          .getResource(Main.IMG_PATH + Main.ICON_FILENAME)
  );

  /**
   * Imagen escalada del icono de la aplicación.
   */
  public static final ImageIcon SCALED_ICON = new ImageIcon(
      ICON.getImage()
          .getScaledInstance(ICON_SCALE, ICON_SCALE, Image.SCALE_SMOOTH)
  );

  // ---------------------------------------- Campos privados -----------------------------------

  private static int distribution;

  private static boolean anchorages;

  private static Map<Position, Integer> playersAmountMap;
  private static Map<Position, String> positions;
  private static Map<Position, List<Player>> playersSets;

  private static MainMenuController mainMenuController;
  private static HelpController helpController;
  private static NamesInputController namesInputController;
  private static AnchoragesController anchoragesController;
  private static SkillsInputController skillsInputController;
  private static ResultsController resultsController;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Constructor vacío.
   */
  private Main() {
    // No necesita cuerpo
  }

  // ---------------------------------------- Punto de entrada principal ------------------------

  /**
   * Instancia y ejecuta el programa.
   *
   * @param args Argumentos para ejecutar el programa (no implementado).
   */
  public static void main(String[] args) {
    playersAmountMap = new EnumMap<>(Position.class);
    positions = new EnumMap<>(Position.class);

    playersSets = new TreeMap<>();

    positions.put(Position.CENTRAL_DEFENDER, "DEFENSORES CENTRALES");
    positions.put(Position.LATERAL_DEFENDER, "DEFENSORES LATERALES");
    positions.put(Position.MIDFIELDER, "MEDIOCAMPISTAS");
    positions.put(Position.FORWARD, "DELANTEROS");
    positions.put(Position.GOALKEEPER, "ARQUEROS");

    setAnchorages(false);
    setGraphicalProperties();

    mainMenuController = new MainMenuController(new MainMenuView());
    helpController = new HelpController(new HelpView());
    namesInputController = new NamesInputController(new NamesInputView());
    anchoragesController = new AnchoragesController(new AnchoragesView());
    skillsInputController = new SkillsInputController(new SkillsInputView());
    resultsController = new ResultsController(new ResultsView());

    mainMenuController.showView();
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Obtiene la distribución elegida para los jugadores.
   *
   * @return Distribución elegida.
   */
  public static int getDistribution() {
    return distribution;
  }

  /**
   * Indica si la opción de anclajes fue elegida.
   *
   * @return Si hay o debe haber anclajes.
   */
  public static boolean thereAreAnchorages() {
    return anchorages;
  }

  /**
   * Obtiene el mapa que asocia las posiciones con la cantidad de jugadores para cada una.
   *
   * @return Mapa que asocia las posiciones con la cantidad de jugadores para cada una.
   */
  public static Map<Position, Integer> getPlayersAmountMap() {
    return playersAmountMap;
  }

  /**
   * Obtiene el mapa que asocia las posiciones con los conjuntos de jugadores.
   *
   * @return Mapa que asocia las posiciones con los conjuntos de jugadores.
   */
  public static Map<Position, List<Player>> getPlayersSets() {
    return playersSets;
  }

  /**
   * Obtiene el mapa con las posiciones y su representación en cadenas de caracteres.
   *
   * @return Mapa con las posiciones y su representación en cadenas de caracteres.
   */
  public static Map<Position, String> getPositionsMap() {
    return positions;
  }

  /**
   * Obtiene el controlador de la ventana del menú principal.
   *
   * @return El controlador de la ventana del menú principal.
   */
  public static MainMenuController getMainMenuController() {
    return mainMenuController;
  }


  /**
   * Obtiene el controlador de la ventana de ayuda.
   *
   * @return El controlador de la ventana de ayuda.
   */
  public static HelpController getHelpController() {
    return helpController;
  }


  /**
   * Obtiene el controlador de la ventana de ingreso de nombres.
   *
   * @return El controlador de la ventana de ingreso de nombres.
   */
  public static NamesInputController getNamesInputController() {
    return namesInputController;
  }


  /**
   * Obtiene el controlador de la ventana de anclajes.
   *
   * @return El controlador de la ventana de anclajes.
   */
  public static AnchoragesController getAnchoragesController() {
    return anchoragesController;
  }


  /**
   * Obtiene el controlador de la ventana de puntuaciones.
   *
   * @return El controlador de la ventana de puntuaciones.
   */
  public static SkillsInputController getSkillsInputController() {
    return skillsInputController;
  }


  /**
   * Obtiene el controlador de la ventana de resultados.
   *
   * @return El controlador de la ventana de resultados.
   */
  public static ResultsController getResultsController() {
    return resultsController;
  }

  // ---------------------------------------- Setters -------------------------------------------

  /**
   * Actualiza la distribución de jugadores elegida.
   *
   * @param d Distribución elegida.
   */
  public static void setDistribution(int d) {
    distribution = d;
  }

  /**
   * Actualiza el estado de la opción de anclajes.
   *
   * @param a Si hay o debe haber anclajes.
   */
  public static void setAnchorages(boolean a) {
    anchorages = a;
  }

  /**
   * Obtiene la posición correspondiente del parámetro a buscar
   * entre los valores del mapa genérico de posiciones.
   *
   * @param map Mapa genérico con posiciones como claves.
   * @param valueToSearch Valor a buscar en el mapa.
   *
   * @return La posición correspondiente al parámetro recibido
   *         a buscar en el mapa genérico de posiciones.
   */
  public static <T> Position getCorrespondingPosition(Map<Position, T> map, T valueToSearch) {
    return (Position) map.entrySet()
                         .stream()
                         .filter(e -> e.getValue()
                                       .equals(valueToSearch))
                         .map(Map.Entry::getKey)
                         .toArray()[0];
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Configura las propiedades de la interfaz gráfica del programa.
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
      // Se crea y registra la fuente para poder utilizarla
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
   * Aplica la fuente para el programa.
   *
   * @param font Fuente a utilizar.
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