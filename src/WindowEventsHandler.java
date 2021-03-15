/**
 * Handler para eventos de ventana.
 * 
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 15/03/2021
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Clase privada para lidiar con los eventos de las ventanas.
 */
public class WindowEventsHandler extends WindowAdapter {

    // Campos privados.
    JFrame frame;
    
    public WindowEventsHandler(JFrame frame) {
        this.frame = frame;
    }
    /**
     * Este método se encarga de hacer invisible
     * la ventana de mezcla.
     * 
     * @param e Evento de ventana.
     */
    @Override
    public void windowOpened(WindowEvent e) {
        frame.setVisible(false); // La ventana de inputs se hace invisible si se abre la ventana de resultados.
    }

    /**
     * Este método se encarga de hacer visible
     * la ventana de mezcla.
     * 
     * @param e Evento de ventana.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        frame.setVisible(true); // La ventana de inputs se hace visible si se cierra la ventana de resultados.
    }
}