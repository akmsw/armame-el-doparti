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

package com.frames;

import com.utils.Position;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import java.io.File;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.Map;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class Main {

    /* ---------------------------------------- Constantes públicas ------------------------------ */

    public static final float FONT_SIZE = 18f;

    public static final String PROGRAM_TITLE = "Armame el doparti";
    public static final String PROGRAM_VERSION = "v3.0";
    public static final String RES_PATH = "res/";
    public static final String IMG_PATH = RES_PATH + "img/";
    public static final String TTF_PATH = RES_PATH + "fonts/";

    public static final Color FRAMES_BG_COLOR = new Color(176, 189, 162);
    public static final Color BUTTONS_BG_COLOR = new Color(41, 71, 74);

    /* ---------------------------------------- Campos privados ---------------------------------- */

    private static Font programFont;

    private static Map<Position, String> positions;

    /**
     * Método principal.
     *
     * Aquí se instancia y ejecuta todo el programa.
     *
     * @param args Argumentos para ejecutar el programa.
     */
    public static void main(String[] args) {
        setGUIProperties();

        positions = new EnumMap<>(Position.class);

        positions.put(Position.CENTRAL_DEFENDER, "DEFENSORES CENTRALES");
        positions.put(Position.LATERAL_DEFENDER, "DEFENSORES LATERALES");
        positions.put(Position.MIDFIELDER, "MEDIOCAMPISTAS");
        positions.put(Position.FORWARD, "DELANTEROS");
        positions.put(Position.GOALKEEPER, "ARQUEROS");

        MainFrame mainFrame = new MainFrame();

        mainFrame.setVisible(true);
    }

    /* ---------------------------------------- Métodos privados --------------------------------- */

    /**
     * Este método se encarga de configurar las propiedades
     * de la interfaz gráfica del programa.
     */
    private static void setGUIProperties() {
        UIManager.put("OptionPane.background", FRAMES_BG_COLOR);
        UIManager.put("Panel.background", FRAMES_BG_COLOR);
        UIManager.put("CheckBox.background", FRAMES_BG_COLOR);
        UIManager.put("Separator.background", FRAMES_BG_COLOR);
        UIManager.put("Button.background", BUTTONS_BG_COLOR);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("CheckBox.focus", FRAMES_BG_COLOR);
        UIManager.put("Button.focus", BUTTONS_BG_COLOR);
        UIManager.put("ToggleButton.focus", BUTTONS_BG_COLOR);
        UIManager.put("ComboBox.focus", Color.WHITE);

        try {
            // Se registra la fuente para poder utilizarla
            programFont = Font.createFont(Font.TRUETYPE_FONT, new File(TTF_PATH + "Comfortaa.ttf"))
                              .deriveFont(FONT_SIZE);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(programFont);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        setUIFont(programFont);
    }

    /**
     * Este método se encarga de setear la fuente utilizada para el programa.
     *
     * @param f Fuente a utilizar.
     */
    private static void setUIFont(Font f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();

        while (keys.hasMoreElements()) {
            Object k = keys.nextElement();
            Object value = UIManager.get(k);

            if (value instanceof FontUIResource)
                UIManager.put(k, f);
        }
    }

    /* ---------------------------------------- Métodos públicos --------------------------------- */

    /**
     * @return Mapa con los strings correspondientes a cada posición.
     */
    public static Map<Position, String> getPositionsMap() {
        return positions;
    }
}