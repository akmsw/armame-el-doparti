/**
 * Clase correspondiente a la ventana de ingreso
 * de puntaje de jugadores.
 * 
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 06/03/2021
 */

// import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
// import javax.swing.JLabel;
import javax.swing.JPanel;
// import javax.swing.JSpinner;
// import javax.swing.SpinnerNumberModel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class RatingFrame extends JFrame {

    // Campos privados.
    private JPanel masterPanel, leftPanel, rightPanel, southPanel;
    private JButton finish, cancel;
    // private ArrayList<JSpinner> spinners; // Arreglo de spinners de los jugadores.

    /**
     * Creación de la ventana de ingreso de puntajes.
     */
    public RatingFrame() {
        initializeComponents();
    }

    // ----------------------------------------Métodos privados---------------------------------

    /**
     * Este método se encarga de inicializar los componentes de la ventana de
     * anclaje.
     */
    private void initializeComponents() {
        masterPanel = new JPanel(new MigLayout());
        leftPanel = new JPanel(new MigLayout("wrap 2"));
        rightPanel = new JPanel(new MigLayout("wrap 2"));
        southPanel = new JPanel(new MigLayout());

        finish = new JButton("Finalizar");
        cancel = new JButton("Cancelar");

        // spinners = new ArrayList<>();

        /*for (int i = 0; i < 14; i++)
            spinners.add(new JSpinner(new SpinnerNumberModel(1, 1, 5, 1)));

        for (int i = 0; i < (spinners.size() / 2); i++) {
            leftPanel.add(new JLabel("TEXTO " + (i + 1)));
            leftPanel.add(spinners.get(i), "wrap");
        }

        for (int i = (spinners.size() / 2); i < spinners.size(); i++) {
            rightPanel.add(new JLabel("TEXTO " + (i + 1)));
            rightPanel.add(spinners.get(i));
        }*/

        southPanel.add(finish, "growx");
        southPanel.add(cancel, "growx");

        masterPanel.add(southPanel, "south");
        masterPanel.add(leftPanel, "west");
        masterPanel.add(rightPanel, "center, span");

        add(masterPanel);
        setResizable(false);
        setTitle("Puntuaciones");
        setIconImage(MainFrame.iconBall.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
}