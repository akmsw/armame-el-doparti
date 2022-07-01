package armameeldoparti.frames;

import armameeldoparti.utils.Position;
import java.awt.Color;
import java.util.EnumMap;
import java.util.Map;

/**
 * Clase principal, sólo para inicialización del programa
 * y declaración de campos útiles.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 15/02/2021
 */
public final class Main {

    // ---------------------------------------- Constantes públicas ------------------------------

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
     * Dirección relativa del directorio de imágenes.
     */
    public static final String IMG_PATH = "/img/";

    /**
     * Dirección relativa del directorio de fuentes.
     */
    public static final String TTF_PATH = "fonts/";

    /**
     * Color de fondo de las ventanas.
     */
    public static final Color FRAMES_BG_COLOR = new Color(176, 189, 162);

    /**
     * Color de fondo de los botones.
     */
    public static final Color BUTTONS_BG_COLOR = new Color(41, 71, 74);

    // ---------------------------------------- Campos privados ----------------------------------

    private static Map<Position, String> positions;

    // ----------------------------------------- Constructor -------------------------------------

    private Main() {
        // No utilizado.
    }

    // --------------------------------- Punto de entrada principal ------------------------------

    /**
     * Se instancia y ejecuta todo el programa.
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

    // --------------------------------------- Métodos públicos ---------------------------------

    /**
     * @return Mapa con los strings correspondientes a cada posición.
     */
    public static Map<Position, String> getPositionsMap() {
        return positions;
    }
}
