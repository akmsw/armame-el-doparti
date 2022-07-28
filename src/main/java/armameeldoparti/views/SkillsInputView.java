package armameeldoparti.views;

import armameeldoparti.abstracts.View;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.utils.Main;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
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
public class SkillsInputView extends View {

  // ---------------------------------------- Constantes privadas -------------------------------

  private static final String GROW_SPAN = "grow, span";
  private static final String FRAME_TITLE = "Ingreso de puntuaciones";

  // ---------------------------------------- Campos privados -----------------------------------

  private JPanel masterPanel;

  private transient Map<Player, JSpinner> spinnersMap;
  private transient Map<JSpinner, JLabel> labelsMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye una ventana de ingreso de puntuaciones.
   */
  public SkillsInputView() {
    initializeInterface();
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  /**
   * Inicializa la interfaz gráfica de esta ventana.
   */
  @Override
  public void initializeInterface() {
    masterPanel = new JPanel(new MigLayout());

    spinnersMap = new HashMap<>();
    labelsMap = new HashMap<>();

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
   * Actualiza las etiquetas con los nombres de los jugadores.
   */
  public void updateNameLabels() {
    for (Position position : Position.values()) {
      for (Player player : Main.getPlayersSets()
                               .get(position)) {
        labelsMap.get(spinnersMap.get(player))
                 .setText(player.getName());
      }
    }
  }

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Obtiene el mapa que asocia cada jugador con su respectivo campo de puntuación.
   *
   * @return El mapa que asocia cada jugador con su respectivo campo de puntuación.
   */
  public Map<Player, JSpinner> getSpinnersMap() {
    return spinnersMap;
  }

  // ---------------------------------------- Métodos protegidos --------------------------------

  /**
   * Añade los botones al panel de la ventana.
   */
  @Override
  protected void addButtons() {
    JButton finishButton = new JButton("Finalizar");
    JButton resetSkillsButton = new JButton("Reiniciar puntuaciones");
    JButton backButton = new JButton("Atrás");

    finishButton.addActionListener(e ->
        Main.getSkillsInputController()
            .finishButtonEvent()
    );

    resetSkillsButton.addActionListener(e ->
        Main.getSkillsInputController()
            .resetSkillsButtonEvent()
    );

    backButton.addActionListener(e ->
        Main.getSkillsInputController()
            .backButtonEvent()
    );

    masterPanel.add(finishButton, GROW_SPAN);
    masterPanel.add(resetSkillsButton, GROW_SPAN);
    masterPanel.add(backButton, GROW_SPAN);
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Añade los campos de puntuación al panel de la ventana.
   */
  private void addSpinners() {
    for (Position position : Position.values()) {
      JLabel positionLabel = new JLabel(Main.getPositionsMap()
                                            .get(position));

      positionLabel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

      masterPanel.add(positionLabel, GROW_SPAN);

      List<Player> currentSet = Main.getPlayersSets()
                                    .get(position);

      for (int j = 0; j < currentSet.size(); j++) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(Main.SCORE_INI,
                                                               Main.SCORE_MIN,
                                                               Main.SCORE_MAX,
                                                               Main.SCORE_STEP));
        JLabel nameLabel = new JLabel(currentSet.get(j)
                                                .getName());

        spinnersMap.put(currentSet.get(j), spinner);

        labelsMap.put(spinner, nameLabel);

        masterPanel.add(nameLabel, "pushx");
        masterPanel.add(spinnersMap.get(currentSet.get(j)),
                        j % 2 != 0 ? "wrap" : null);
      }

      spinnersMap.values()
                 .forEach(s -> ((DefaultEditor) s.getEditor()).getTextField()
                                                              .setEditable(false));
    }
  }
}