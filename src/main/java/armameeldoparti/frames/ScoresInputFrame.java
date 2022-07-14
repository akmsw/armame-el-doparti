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
public class ScoresInputFrame extends JFrame {

  // ---------------------------------------- Constantes privadas -------------------------------

  /**
   * Valor inicial de los campos de entrada para puntuaciones.
   */
  private static final int SCORE_INI = 1;

  /**
   * Valor mínimo de los campos de entrada para puntuaciones.
   */
  private static final int SCORE_MIN = 1;

  /**
   * Valor máximo de los campos de entrada para puntuaciones.
   */
  private static final int SCORE_MAX = 5;

  /**
   * Paso utilizado para el incremento y decremento del valor de los campos de entrada
   * para puntuaciones.
   */
  private static final int SCORE_STEP = 1;

  /**
   * Configuración utilizada frecuentemente.
   */
  private static final String GROW_SPAN = "grow, span";

  /**
   * Título de la ventana.
   */
  private static final String FRAME_TITLE = "Ingreso de puntuaciones";

  // ---------------------------------------- Campos privados -----------------------------------

  private JPanel masterPanel;

  private ResultsFrame resultsFrame;

  private transient Map<Player, JSpinner> spinnersMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye una ventana de ingreso de puntuaciones.
   *
   * @param previousFrame Ventana fuente que crea la ventana ScoreInputFrame.
   */
  public ScoresInputFrame(JFrame previousFrame) {
    initializeInterface(previousFrame);
  }

  /**
   * Inicializa la interfaz gráfica de esta ventana.
   *
   * @param previousFrame Ventana fuente que crea la ventana ScoreInputFrame.
   */
  private void initializeInterface(JFrame previousFrame) {
    masterPanel = new JPanel(new MigLayout());

    spinnersMap = new HashMap<>();

    addSpinners();
    addButtons(previousFrame);
    add(masterPanel);
    setTitle(FRAME_TITLE);
    setResizable(false);
    setIconImage(MainFrame.ICON.getImage());
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    pack();
    setLocationRelativeTo(null);
  }

  /**
   * Añade los botones al panel de la ventana.
   *
   * @param previousFrame Ventana fuente que crea la ventana ScoreInputFrame.
   */
  private void addButtons(JFrame previousFrame) {
    JButton finishButton = new JButton("Finalizar");
    JButton resetButton = new JButton("Reiniciar puntuaciones");

    finishButton.addActionListener(e -> {
      spinnersMap.forEach((k, v) -> k.setScore((int) v.getValue()));

      resultsFrame = new ResultsFrame(this);

      resultsFrame.setVisible(true);

      setVisible(false);
      setLocationRelativeTo(null);
    });

    resetButton.addActionListener(e -> spinnersMap.forEach((k, v) -> {
      v.setValue(1);
      k.setScore(0);
    }));

    BackButton backButton = new BackButton(this, previousFrame, null);

    masterPanel.add(finishButton, GROW_SPAN);
    masterPanel.add(resetButton, GROW_SPAN);
    masterPanel.add(backButton, GROW_SPAN);
  }

  /**
   * Añade los campos de puntuación al panel de la ventana.
   */
  private void addSpinners() {
    for (int i = 0; i < Main.getPlayersSets().size(); i++) {
      JLabel label = new JLabel(Main.getPositionsMap()
                                    .get(Position.values()[i]));

      label.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

      masterPanel.add(label, GROW_SPAN);

      for (int j = 0; j < Main.getPlayersSets().get(Position.values()[i]).size(); j++) {
        spinnersMap.put(Main.getPlayersSets()
                            .get(Position.values()[i])
                            .get(j),
                        new JSpinner(new SpinnerNumberModel(SCORE_INI, SCORE_MIN,
                                                            SCORE_MAX, SCORE_STEP)));

        masterPanel.add(new JLabel(Main.getPlayersSets()
                                       .get(Position.values()[i])
                                       .get(j)
                                       .getName()), "pushx");

        if (j % 2 != 0) {
          masterPanel.add(spinnersMap.get(Main.getPlayersSets()
                                     .get(Position.values()[i])
                                     .get(j)), "wrap");
        } else {
          masterPanel.add(spinnersMap.get(Main.getPlayersSets()
                                     .get(Position.values()[i])
                                     .get(j)));
        }
      }

      for (JSpinner js : spinnersMap.values()) {
        ((DefaultEditor) js.getEditor()).getTextField()
                                        .setEditable(false);
      }
    }
  }
}