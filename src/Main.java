/**
 * Clase principal, sólo para inicialización del programa.
 * 
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 15/02/2021
 */

import java.awt.Color;

import java.util.ArrayList;

public class Main {

    // Constantes públicas.
    public static final String PROGRAM_TITLE = "Armame el doparti";
    public static final String PROGRAM_VERSION = "v3.0";
    public static final String IMG_PATH = "src/graphics/"; // Carpeta donde buscar las imágenes.
    public static final Color FRAMES_BG_COLOR = new Color(176, 189, 162); // Color de fondo de las ventanas.
    public static ArrayList<String> positions; // Arreglo con los strings de las posiciones de los jugadores.
    
    /**
     * Método principal.
     * 
     * Aquí se instancia y ejecuta todo el programa.
     */
    public static void main(String[] args) {
        positions = new ArrayList<>();

        positions.add("DEFENSORES CENTRALES");
        positions.add("DEFENSORES LATERALES");
        positions.add("MEDIOCAMPISTAS");
        positions.add("DELANTEROS");
        positions.add("COMODINES");

        MainFrame mainFrame = new MainFrame();

        mainFrame.setVisible(true);
    }
}