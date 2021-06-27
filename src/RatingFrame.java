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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

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

    // Campos privados.
    private JPanel masterPanel, centerPanel, southPanel;
    private JButton finishButton;
    private HashMap<Player, JSpinner> spinnersMap; // Mapa que asocia a cada jugador un JSpinner.

    /**
     * Creación de la ventana de ingreso de puntajes.
     */
    public RatingFrame() {
        centerPanel = new JPanel(new MigLayout("wrap 4"));
        southPanel = new JPanel(new MigLayout("wrap"));
        masterPanel = new JPanel(new MigLayout());

        finishButton = new JButton("Finalizar");

        finishButton.addActionListener(new ActionListener() {
            /**
             * Este método envía un evento de cierre de ventana para togglear
             * la visibilidad de las ventanas mediante el WindowEventsHandler.
             * 
             * @param e Evento de click.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                RatingFrame.this.dispatchEvent(new WindowEvent(RatingFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });

        spinnersMap = new HashMap<>();

        int index = 0;

        for (Player[] playersSet : InputFrame.playersSets) {
            centerPanel.add(new JLabel(Main.positions.get(index)), "span");
            centerPanel.add(new JSeparator(JSeparator.HORIZONTAL), "growx, span");

            for (Player player : playersSet) {
                spinnersMap.put(player, new JSpinner(new SpinnerNumberModel(1, 1, 5, 1)));

                centerPanel.add(new JLabel(player.getName()));
                centerPanel.add(spinnersMap.get(player));
            }

            index++;
        }

        centerPanel.setBackground(Main.FRAMES_BG_COLOR);

        southPanel.add(finishButton, "w 147:214:214, growx, wrap");
        southPanel.setBackground(Main.FRAMES_BG_COLOR);

        masterPanel.add(southPanel, "south");
        masterPanel.add(centerPanel, "center, growx, span");

        add(masterPanel);
        setResizable(false);
        setTitle("Puntuaciones");
        setIconImage(MainFrame.iconBall.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}