/**
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 28/02/2021
 */

import javax.swing.JFrame;

public class MixFrame extends JFrame {

    // Campos privados.

    /**
     * Constructor.
     */
    public MixFrame() {
        setSize(100, 100);
        setLocationRelativeTo(null);
        setTitle("MixFrame");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }
}