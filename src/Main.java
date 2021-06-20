/**
 * Clase principal, sólo para inicialización del programa.
 * 
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 15/02/2021
 */

public class Main {

    // Constantes públicas.
    public static final String VERSION = "v3.0";
    public static final String PROGRAM_TITLE = "Fiesta de fulbito";

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