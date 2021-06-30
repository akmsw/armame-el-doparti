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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ResultFrame extends JFrame {

    // Campos privados.
    private JPanel panel;
    private JFrame previousFrame;
    private JButton mainMenuButton;
    private BackButton backButton;
    private InputFrame inputFrame;

    /**
     * Creación de la ventana de resultados.
     */
    public ResultFrame(InputFrame inputFrame, JFrame previousFrame) {
        this.inputFrame = inputFrame;
        this.previousFrame = previousFrame;

        if (inputFrame.getDistribution() == 0)
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(MainFrame.iconBall.getImage());

        backButton = new BackButton(ResultFrame.this, previousFrame);

        backButton.setBounds(0, 0, 100, 30);

        mainMenuButton = new JButton("Volver al menú principal");

        mainMenuButton.addActionListener(new ActionListener() {
            /**
             * Este método devuelve al usuario al menú principal
             * de la aplicación, eliminando la ventana de resultados.
             * 
             * @param e Evento de click.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame mainFrame = new MainFrame();

                mainFrame.setVisible(true);
                
                ResultFrame.this.dispose();
            }
        });

        panel = new JPanel(new MigLayout("wrap"));

        panel.add(backButton, "growx");
        panel.add(mainMenuButton, "growx");
        
        add(panel);
    }

    /**
     * Este método se encarga de armar los equipos de manera completamente
     * aleatoria.
     */
    private void randomMix() {
        setTitle("MEZCLA ALEATORIA");
        setVisible(true);
        initializeComponents();
    }

    /**
     * Este método se encarga de armar los equipos de la manera más
     * equitativa en base a las puntuaciones seteadas a los jugadores.
     */
    private void ratingMix() {
        setTitle("MEZCLA POR PUNTAJES");
        setVisible(false);

        RatingFrame ratingFrame = new RatingFrame(inputFrame, previousFrame);

        ratingFrame.setVisible(true);
    }
}