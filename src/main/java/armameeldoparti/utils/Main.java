package armameeldoparti.utils;

import armameeldoparti.frames.MainFrame;
import java.awt.Color;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

  // ---------------------------------------- Constantes públicas -------------------------------

  public static final int RANDOM_MIX = 0;
  public static final int BY_SCORES_MIX = 1;
  public static final int PLAYERS_PER_TEAM = 7;

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

  public static final Color LIGHT_GREEN = new Color(176, 189, 162);
  public static final Color MEDIUM_GREEN = new Color(109, 130, 118);
  public static final Color DARK_GREEN = new Color(41, 71, 74);
  public static final Color LIGHT_ORANGE = new Color(255, 238, 153);

  // ---------------------------------------- Campos privados -----------------------------------

  private static int distribution;

  private static boolean anchorages;

  private static Map<Position, Integer> playersAmountMap;
  private static Map<Position, String> positions;
  private static Map<Position, List<Player>> playersSets;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Constructor vacío.
   */
  private Main() {
      // No utilizado
  }

  // ---------------------------------------- Punto de entrada principal ------------------------

  /**
   * Instancia y ejecuta todo el programa.
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

    MainFrame mainFrame = new MainFrame();
    mainFrame.setVisible(true);
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Retorna la distribución elegida para los jugadores.
   *
   * @return Distribución elegida.
   */
  public static int getDistribution() {
    return distribution;
  }

  /**
   * Retorna si la opción de anclajes fue elegida.
   *
   * @return Si hay o debe haber anclajes.
   */
  public static boolean thereAreAnchorages() {
    return anchorages;
  }

  /**
   * Retorna la cantidad de jugadores en total por cada posición.
   *
   * @return Mapa que asocia las posiciones con la cantidad de jugadores para cada una.
   */
  public static Map<Position, Integer> getPlayersAmountMap() {
    return playersAmountMap;
  }

  /**
   * Retorna la cantidad de jugadores por equipo por cada posición.
   *
   * @return Mapa que asocia las posiciones con los conjuntos de jugadores.
   */
  public static Map<Position, List<Player>> getPlayersSets() {
    return playersSets;
  }

  /**
   * Retorna las cadenas correspondientes a las posiciones de los jugadores.
   *
   * @return Mapa con los strings correspondientes a cada posición.
   */
  public static Map<Position, String> getPositionsMap() {
    return positions;
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
}