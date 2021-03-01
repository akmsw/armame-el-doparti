/**
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 28/02/2021
 */

import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MixFrame extends JFrame {

    // Campos privados.
    private int playersAmount;
    private ImageIcon icon;

    /**
     * Constructor.
     * Aquí se crea la ventana de mezcla.
     */
    public MixFrame(int playersAmount, ImageIcon icon) {
        this.playersAmount = playersAmount;
        this.icon = icon;

        initializeComponents("Ingreso de jugadores - Fútbol " + playersAmount);

        setVisible(true);
    }

    // ----------------------------------------Métodos privados---------------------------------

    /**
     * Este método se encarga de inicializar los componentes de la ventana de comienzo.
     * 
     * @param   frameTitle  Título de la ventana.
     */
    private void initializeComponents(String frameTitle) {
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(frameTitle);
        setResizable(false);
        setIconImage(icon.getImage());
    }
}