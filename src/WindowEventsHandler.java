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

public class WindowEventsHandler extends WindowAdapter {

    // Campos privados.
    JFrame frame; // Ventana cuya visibilidad será toggleada.

    /**
     * Constructor del handler de eventos de ventana.
     * 
     * @param frame Ventana cuya visibilidad será toggleada.
     */
    public WindowEventsHandler(JFrame frame) {
        this.frame = frame;
    }

    // ----------------------------------------Métodos públicos---------------------------------

    /**
     * Este método se encarga de hacer invisible la ventana que debe quedar en
     * segundo plano.
     * 
     * @param e Evento de ventana.
     */
    @Override
    public void windowOpened(WindowEvent e) {
        frame.setVisible(false);
    }

    /**
     * Este método se encarga de hacer visible la ventana que debe quedar en primer
     * plano.
     * 
     * @param e Evento de ventana.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        frame.setVisible(true);
    }
}