/**
 * Clase correspondiente a la ventana de ingreso
 * de puntaje de jugadores.
 * 
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 3.0.0
 * 
 * @since 06/03/2021
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.miginfocom.swing.MigLayout;

public class RatingFrame extends JFrame {

    /* ---------------------------------------- Campos privados  ---------------------------------- */

    private JPanel panel;
    private JButton finishButton;
    private BackButton backButton;
    private HashMap<Player, JSpinner> spinnersMap; // Mapa que asocia a cada jugador un JSpinner.
    private InputFrame inputFrame;

    /**
     * Creación de la ventana de ingreso de puntajes.
     * 
     * @param inputFrame    La ventana de ingreso de datos, de la cual se obtendrá
     *                      información importante.
     * @param previousFrame La ventana fuente que crea la ventana RatingFrame.
     */
    public RatingFrame(InputFrame inputFrame, JFrame previousFrame) {
        this.inputFrame = inputFrame;

        panel = new JPanel(new MigLayout());

        panel.setBackground(Main.FRAMES_BG_COLOR);

        finishButton = new JButton("Finalizar");
        backButton = new BackButton(RatingFrame.this, previousFrame);

        finishButton.addActionListener(new ActionListener() {
            /**
             * Este método envía togglear la visibilidad de las ventanas.
             * 
             * @param e Evento de click.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                spinnersMap.forEach((k, v) -> k.setRating((int) v.getValue()));

                ratingsTest();
            }
        });

        spinnersMap = new HashMap<>();

        for (int i = 0; i < inputFrame.getPlayersSets().size(); i++) {
            JLabel label = new JLabel(Main.positions.get(i));

            label.setFont(Main.PROGRAM_FONT.deriveFont(Font.BOLD));

            panel.add(label, "span");
            panel.add(new JSeparator(JSeparator.HORIZONTAL), "growx, span");

            for (int j = 0; j < inputFrame.getPlayersSets().get(i).length; j++) {
                spinnersMap.put(inputFrame.getPlayersSets().get(i)[j],
                        new JSpinner(new SpinnerNumberModel(1, 1, 5, 1)));

                panel.add(new JLabel(inputFrame.getPlayersSets().get(i)[j].getName()), "pushx");

                if (j % 2 != 0)
                    panel.add(spinnersMap.get(inputFrame.getPlayersSets().get(i)[j]), "wrap");
                else
                    panel.add(spinnersMap.get(inputFrame.getPlayersSets().get(i)[j]));
            }
        }

        panel.add(finishButton, "growx, span, w 230::");
        panel.add(backButton, "growx, span");

        add(panel);

        setResizable(false);
        setTitle("Puntuaciones");
        setIconImage(MainFrame.iconBall.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();

        setLocationRelativeTo(null);
    }

    /**
     * Método de prueba para testear que los puntajes se hayan asignado
     * correctamente.
     */
    private void ratingsTest() {
        System.out.println("##############################################");

        for (int i = 0; i < inputFrame.getPlayersSets().size(); i++)
            for (int j = 0; j < inputFrame.getPlayersSets().get(i).length; j++)
                System.out.println("JUGADOR " + inputFrame.getPlayersSets().get(i)[j].getName() + " > RATING = "
                        + inputFrame.getPlayersSets().get(i)[j].getRating());
    }
}