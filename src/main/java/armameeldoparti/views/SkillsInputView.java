package armameeldoparti.views;

import armameeldoparti.controllers.SkillsInputController;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.utils.Main;
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
public class SkillsInputView extends JFrame {

  // ---------------------------------------- Constantes privadas -------------------------------

  private static final String GROW_SPAN = "grow, span";
  private static final String FRAME_TITLE = "Ingreso de puntuaciones";

  // ---------------------------------------- Campos privados -----------------------------------

  private JPanel masterPanel;

  private transient Map<Player, JSpinner> spinnersMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye una ventana de ingreso de puntuaciones.
   */
  public SkillsInputView() {
    initializeInterface();
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Obtiene el mapa que asocia cada jugador con su respectivo campo de puntuación.
   *
   * @return El mapa que asocia cada jugador con su respectivo campo de puntuación.
   */
  public Map<Player, JSpinner> getSpinnersMap() {
    return spinnersMap;
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Inicializa la interfaz gráfica de esta ventana.
   */
  private void initializeInterface() {
    masterPanel = new JPanel(new MigLayout());

    spinnersMap = new HashMap<>();

    setTitle(FRAME_TITLE);
    setResizable(false);
    setIconImage(MainMenuView.ICON.getImage());
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    addSpinners();
    addButtons();
    add(masterPanel);
    pack();
    setLocationRelativeTo(null);
  }

  /**
   * Añade los botones al panel de la ventana.
   */
  private void addButtons() {
    JButton finishButton = new JButton("Finalizar");
    JButton resetSkillsButton = new JButton("Reiniciar puntuaciones");
    JButton backButton = new JButton("Atrás");

    finishButton.addActionListener(e -> SkillsInputController.finishButtonEvent());
    resetSkillsButton.addActionListener(e -> SkillsInputController.resetSkillsButtonEvent());
    backButton.addActionListener(e -> SkillsInputController.backButtonEvent());

    masterPanel.add(finishButton, GROW_SPAN);
    masterPanel.add(resetSkillsButton, GROW_SPAN);
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
                        new JSpinner(new SpinnerNumberModel(SkillsInputController.SCORE_INI,
                                                            SkillsInputController.SCORE_MIN,
                                                            SkillsInputController.SCORE_MAX,
                                                            SkillsInputController.SCORE_STEP)));

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