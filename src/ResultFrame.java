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

import net.miginfocom.swing.MigLayout;

public class ResultFrame extends JFrame {

    // Campos privados.
    private JPanel panel;

    /**
     * Creación de la ventana de resultados.
     */
    public ResultFrame() {
        initializeComponents();

        if (InputFrame.distribution == 0)
            randomMix();
        else
            ratingMix();
    }

    // ----------------------------------------Métodos privados---------------------------------

    /**
     * Este método inicializa los componentes de la ventana de resultados.
     */
    private void initializeComponents() {
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setIconImage(MainFrame.iconBall.getImage());

        panel = new JPanel(new MigLayout("wrap"));
        
        add(panel);
    }

    /**
     * Este método se encarga de armar los equipos de manera completamente
     * aleatoria.
     */
    private void randomMix() {
        setTitle("MEZCLA ALEATORIA");
        setVisible(true);
    }

    /**
     * Este método se encarga de armar los equipos de la manera más
     * equitativa en base a las puntuaciones seteadas a los jugadores.
     */
    private void ratingMix() {
        setTitle("MEZCLA POR PUNTAJES");

        RatingFrame ratingFrame = new RatingFrame();

        ratingFrame.addWindowListener(new WindowEventsHandler(ResultFrame.this));
        ratingFrame.setVisible(true);
    }
}