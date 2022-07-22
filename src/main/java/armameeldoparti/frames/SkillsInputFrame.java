package armameeldoparti.frames;

import armameeldoparti.utils.BackButton;
import armameeldoparti.utils.Main;
import armameeldoparti.utils.Player;
import armameeldoparti.utils.Position;
import java.util.HashMap;
import java.util.List;
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
public class SkillsInputFrame extends JFrame {

  // ---------------------------------------- Constantes privadas -------------------------------

  private static final int SCORE_INI = 1;
  private static final int SCORE_MIN = 1;
  private static final int SCORE_MAX = 5;
  private static final int SCORE_STEP = 1;

  private static final String GROW_SPAN = "grow, span";
  private static final String FRAME_TITLE = "Ingreso de puntuaciones";

  // ---------------------------------------- Campos privados -----------------------------------

  private JPanel masterPanel;

  private ResultsFrame resultsFrame;

  private transient Map<Player, JSpinner> spinnersMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye una ventana de ingreso de puntuaciones.
   *
   * @param previousFrame Ventana fuente que crea la ventana SkillInputFrame.
   */
  public SkillsInputFrame(JFrame previousFrame) {
    initializeInterface(previousFrame);
  }

  /**
   * Inicializa la interfaz gráfica de esta ventana.
   *
   * @param previousFrame Ventana fuente que crea la ventana SkillInputFrame.
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
   * @param previousFrame Ventana fuente que crea la ventana SkillInputFrame.
   */
  private void addButtons(JFrame previousFrame) {
    JButton finishButton = new JButton("Finalizar");
    JButton resetButton = new JButton("Reiniciar puntuaciones");

    finishButton.addActionListener(e -> {
      spinnersMap.forEach((k, v) -> k.setSkill((int) v.getValue()));

      resultsFrame = new ResultsFrame(this);

      resultsFrame.setVisible(true);

      setVisible(false);
      setLocationRelativeTo(null);
    });

    resetButton.addActionListener(e -> spinnersMap.forEach((k, v) -> {
      v.setValue(1);
      k.setSkill(0);
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
    for (int i = 0; i < Main.getPlayersSets()
                            .size(); i++) {
      JLabel label = new JLabel(Main.getPositionsMap()
                                    .get(Position.values()[i]));

      label.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

      masterPanel.add(label, GROW_SPAN);

      List<Player> currentSet = Main.getPlayersSets()
                                    .get(Position.values()[i]);

      for (int j = 0; j < currentSet.size(); j++) {
        spinnersMap.put(currentSet.get(j),
                        new JSpinner(new SpinnerNumberModel(SCORE_INI, SCORE_MIN,
                                                            SCORE_MAX, SCORE_STEP)));

        masterPanel.add(new JLabel(currentSet.get(j)
                                             .getName()),
                                   "pushx");

        masterPanel.add(spinnersMap.get(currentSet.get(j)),
                        j % 2 != 0 ? "wrap" : null);
      }

      spinnersMap.values()
                 .forEach(s -> ((DefaultEditor) s.getEditor()).getTextField()
                                                              .setEditable(false));
    }
  }
}