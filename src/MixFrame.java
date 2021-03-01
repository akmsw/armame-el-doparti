/**
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 28/02/2021
 */

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MixFrame extends JFrame {

    // Campos privados.

    /**
     * Constructor.
     * Aquí se crea la ventana de mezcla.
     */
    public MixFrame(int playersAmount) {
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("MixFrame");
        setVisible(true);
        setResizable(false);
    }
}