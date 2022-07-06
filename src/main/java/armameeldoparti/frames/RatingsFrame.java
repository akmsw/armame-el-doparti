package armameeldoparti.frames;

import armameeldoparti.utils.BackButton;
import armameeldoparti.utils.Main;
import armameeldoparti.utils.Player;
import armameeldoparti.utils.Position;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import net.miginfocom.swing.MigLayout;

/**
 * Clase correspondiente a la ventana de ingreso de puntuación de jugadores.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 06/03/2021
 */
public class RatingsFrame extends JFrame {

    // ---------------------------------------- Constantes privadas ------------------------------

    /**
     * Valor inicial de los campos de entrada para puntuaciones.
     */
    private static final int RATINGS_INI = 1;

    /**
     * Valor mínimo de los campos de entrada para puntuaciones.
     */
    private static final int RATINGS_MIN = 1;

    /**
     * Valor máximo de los campos de entrada para puntuaciones.
     */
    private static final int RATINGS_MAX = 5;

    /**
     * Paso utilizado para el incremento y decremento del valor de los campos de entrada para puntuaciones.
     */
    private static final int RATINGS_STEP = 1;

    /**
     * Configuración utilizada frecuentemente.
     */
    private static final String GROW_SPAN = "grow, span";

    // ---------------------------------------- Campos privados ----------------------------------

    private ResultFrame resultFrame;

    // ---------------------------------------- Constructor --------------------------------------

    /**
     * Construye una ventana de ingreso de puntuaciones.
     *
     * @param previousFrame Ventana fuente que crea la ventana RatingFrame.
     */
    public RatingsFrame(JFrame previousFrame) {
        JPanel panel = new JPanel(new MigLayout());

        JButton finishButton = new JButton("Finalizar");
        JButton resetButton = new JButton("Reiniciar puntuaciones");

        Map<Player, JSpinner> spinnersMap = new HashMap<>();

        BackButton backButton = new BackButton(RatingsFrame.this, previousFrame, null);

        panel.setBackground(Main.LIGHT_GREEN);

        finishButton.addActionListener(e -> {
            spinnersMap.forEach((k, v) -> k.setRating((int) v.getValue()));

            resultFrame = new ResultFrame(RatingsFrame.this);

            resultFrame.setVisible(true);

            RatingsFrame.this.setVisible(false);
            RatingsFrame.this.setLocationRelativeTo(null);
        });

        resetButton.addActionListener(e ->
            spinnersMap.forEach((k, v) -> {
                v.setValue(1);
                k.setRating(0);
            }));

        for (int i = 0; i < Main.getPlayersSets().size(); i++) {
            JLabel label = new JLabel(Main.getPositionsMap()
                                          .get(Position.values()[i]));

            label.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

            panel.add(label, GROW_SPAN);

            for (int j = 0; j < Main.getPlayersSets().get(Position.values()[i]).size(); j++) {
                spinnersMap.put(Main.getPlayersSets()
                                    .get(Position.values()[i])
                                    .get(j),
                                new JSpinner(new SpinnerNumberModel(RATINGS_INI, RATINGS_MIN,
                                                                    RATINGS_MAX, RATINGS_STEP)));

                panel.add(new JLabel(Main.getPlayersSets()
                                         .get(Position.values()[i])
                                         .get(j)
                                         .getName()), "pushx");

                if ((j % 2) != 0) {
                    panel.add(spinnersMap.get(Main.getPlayersSets()
                                                  .get(Position.values()[i])
                                                  .get(j)), "wrap");
                } else {
                    panel.add(spinnersMap.get(Main.getPlayersSets()
                                                  .get(Position.values()[i])
                                                  .get(j)));
                }
            }

            for (JSpinner js : spinnersMap.values()) {
                ((DefaultEditor) js.getEditor()).getTextField().setEditable(false);
            }
        }

        panel.add(finishButton, GROW_SPAN);
        panel.add(resetButton, GROW_SPAN);
        panel.add(backButton, GROW_SPAN);

        add(panel);
        setTitle("Puntuaciones");
        setIconImage(MainFrame.ICON.getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
