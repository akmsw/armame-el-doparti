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

public class Main {

    // Constantes públicas.
    public static final String PROGRAM_TITLE = "Armame el doparti";
    public static final String PROGRAM_VERSION = "v3.0";
    public static final String IMG_PATH = "src/graphics/"; // Carpeta donde buscar las imágenes.
    public static final Color FRAMES_BG_COLOR = new Color(200, 200, 200); // Color de fondo de la ventana.
    
    /**
     * Método principal.
     * 
     * Aquí se instancia y ejecuta todo el programa.
     */
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();

        mainFrame.setVisible(true);
    }
}