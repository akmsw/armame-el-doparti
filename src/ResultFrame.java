/**
 * Clase correspondiente a la ventana de resultados
 * de distribución de jugadores.
 * 
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 06/03/2021
 */

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ResultFrame extends JFrame {

    // Campos privados.
    private JPanel panel;

    /**
     * Creación de la ventana de resultados.
     */
    public ResultFrame() {
        initializeComponents();
    }

    // ----------------------------------------Métodos privados---------------------------------

    private void initializeComponents() {
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setIconImage(MainFrame.iconBall.getImage());

        panel = new JPanel();

        if (AnchorageFrame.distribution == 0)
            randomMix();
        else {
            RatingFrame ratingFrame = new RatingFrame();

            ratingFrame.addWindowListener(new WindowEventsHandler(this));
            ratingFrame.setVisible(true);
        }

        add(panel);
    }

    /**
     * Este método se encarga de armar los equipos de manera completamente
     * aleatoria.
     */
    private void randomMix() {
        setTitle("MEZCLA ALEATORIA");
    }
}