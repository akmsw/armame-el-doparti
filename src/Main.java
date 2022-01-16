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

import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class Main {

    /* ---------------------------------------- Constantes privadas ------------------------------ */

    private static final Color SELECT_NOTIF_FRAME_BG_COLOR = new Color(176, 189, 162); // Color de fondo de las ventanas
                                                                                       // de selección/notificación.

    /* ---------------------------------------- Constantes públicas ------------------------------ */

    public static final String PROGRAM_TITLE = "Armame el doparti";
    public static final String PROGRAM_VERSION = "v3.0";

    // Fuente utilizada para el programa.
    public static final FontUIResource PROGRAM_FONT = new FontUIResource("Noto Sans", Font.PLAIN, 13);
    public static final String IMG_PATH = "src/graphics/"; // Carpeta donde buscar las imágenes.
    public static final Color FRAMES_BG_COLOR = new Color(176, 189, 162); // Color de fondo de las ventanas.
    public static final Color BUTTONS_BG_COLOR = new Color(41, 71, 74); // Color de fondo de los botones.
    public static final float CB_FONT_SIZE = 12; // Tamaño de fuente para los checkboxes necesarios.

    /* ---------------------------------------- Campos públicos ---------------------------------- */

    public static ArrayList<String> positions; // Arreglo con los strings de las posiciones de los jugadores.

    /**
     * Método principal.
     * 
     * Aquí se instancia y ejecuta todo el programa.
     * 
     * @param args Argumentos para ejecutar el programa.
     */
    public static void main(String[] args) {
        setGUIProperties();

        positions = new ArrayList<>();

        positions.add("DEFENSORES CENTRALES");
        positions.add("DEFENSORES LATERALES");
        positions.add("MEDIOCAMPISTAS");
        positions.add("DELANTEROS");
        positions.add("COMODINES");

        MainFrame mainFrame = new MainFrame();

        mainFrame.setVisible(true);
    }

    /* ---------------------------------------- Métodos privados --------------------------------- */

    /**
     * Este método se encarga de setear las propiedades de la interfaz
     * gráfica del programa.
     */
    private static void setGUIProperties() {
        UIManager.put("OptionPane.background", SELECT_NOTIF_FRAME_BG_COLOR);
        UIManager.put("Panel.background", SELECT_NOTIF_FRAME_BG_COLOR);
        UIManager.put("Button.background", BUTTONS_BG_COLOR);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focus", BUTTONS_BG_COLOR);
        UIManager.put("ToggleButton.focus", BUTTONS_BG_COLOR);
        UIManager.put("CheckBox.focus", FRAMES_BG_COLOR);
        UIManager.put("CheckBox.background", FRAMES_BG_COLOR);
        UIManager.put("ComboBox.focus", Color.WHITE);
        UIManager.put("Separator.background", FRAMES_BG_COLOR);

        setUIFont(PROGRAM_FONT);
    }

    /**
     * Este método se encarga de setear la fuente utilizada para el programa.
     * 
     * @param f Fuente a utilizar.
     */
    private static void setUIFont(FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();

        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);

            if (value instanceof FontUIResource)
                UIManager.put(key, f);
        }
    }
}