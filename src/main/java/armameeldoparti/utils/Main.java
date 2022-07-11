package armameeldoparti.utils;

import armameeldoparti.frames.MainFrame;
import java.awt.Color;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Indicador de distribución aleatoria.
     */
    public static final int RANDOM_MIX = 0;

    /**
     * Indicador de distribución por puntuaciones.
     */
    public static final int RATINGS_MIX = 1;

    /**
     * Cantidad de jugadores por equipo.
     */
    public static final int PLAYERS_PER_TEAM = 7;

    /**
     * Tamaño de la fuente a utilizar.
     */
    public static final float FONT_SIZE = 18f;

    /**
     * Nombre de la fuente a utilizar.
     */
    public static final String FONT_NAME = "comfortaa.ttf";

    /**
     * Nombre de la aplicación.
     */
    public static final String PROGRAM_TITLE = "Armame el doparti";

    /**
     * Versión de la aplicación.
     */
    public static final String PROGRAM_VERSION = "v3.0";

    /**
     * Nombre del archivo de imagen de icono para las ventanas.
     */
    public static final String ICON_FILENAME = "icon.png";

    /**
     * Nombre del archivo de imagen de fondo para el menú principal.
     */
    public static final String BG_IMG_FILENAME = "bg.png";

    /**
     * Dirección relativa del directorio de imágenes.
     */
    public static final String IMG_PATH = "img/";

    /**
     * Dirección relativa del directorio de fuentes.
     */
    public static final String TTF_PATH = "fonts/";

    /**
     * Color verde claro en formato RGB.
     */
    public static final Color LIGHT_GREEN = new Color(176, 189, 162);

    /**
     * Color verde oscuro en formato RGB.
     */
    public static final Color DARK_GREEN = new Color(41, 71, 74);

    /**
     * Color amarillo claro en formato RGB.
     */
    public static final Color LIGHT_YELLOW = new Color(255, 255, 204);

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
     * @param args Argumentos para ejecutar el programa.
     */
    public static void main(String[] args) {
        positions = new EnumMap<>(Position.class);

        positions.put(Position.CENTRAL_DEFENDER, "DEFENSORES CENTRALES");
        positions.put(Position.LATERAL_DEFENDER, "DEFENSORES LATERALES");
        positions.put(Position.MIDFIELDER, "MEDIOCAMPISTAS");
        positions.put(Position.FORWARD, "DELANTEROS");
        positions.put(Position.GOALKEEPER, "ARQUEROS");

        MainFrame mainFrame = new MainFrame();

        mainFrame.setVisible(true);
    }

    // ---------------------------------------- Métodos públicos ----------------------------------

    // ---------------------------------------- Getters -------------------------------------------

    /**
     * @return Distribución elegida.
     */
    public static int getDistribution() {
        return distribution;
    }

    /**
     * @return Si hay o debe haber anclajes.
     */
    public static boolean thereAreAnchorages() {
        return anchorages;
    }

    /**
     * @return Mapa que asocia las posiciones con la cantidad de jugadores para cada una.
     */
    public static Map<Position, Integer> getPlayersAmountMap() {
        return playersAmountMap;
    }

    /**
     * @return Mapa que asocia las posiciones con los conjuntos de jugadores.
     */
    public static Map<Position, List<Player>> getPlayersSets() {
        return playersSets;
    }

    /**
     * @return Mapa con los strings correspondientes a cada posición.
     */
    public static Map<Position, String> getPositionsMap() {
        return positions;
    }

    // ---------------------------------------- Setters -------------------------------------------

    /**
     * @param d Distribución elegida.
     */
    public static void setDistribution(int d) {
        distribution = d;
    }

    /**
     * @param a Si hay o debe haber anclajes.
     */
    public static void setAnchorages(boolean a) {
        anchorages = a;
    }

    /**
     * @param pam Mapa que asocia las posiciones con la cantidad de jugadores para cada una.
     */
    public static void setPlayersAmountMap(Map<Position, Integer> pam) {
        playersAmountMap = pam;
    }

    /**
     * @param pss Mapa que asocia las posiciones con los conjuntos de jugadores.
     */
    public static void setPlayersSets(Map<Position, List<Player>> pss) {
        playersSets = pss;
    }
}
