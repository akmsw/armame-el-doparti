package armameeldoparti.views;

import armameeldoparti.controllers.SkillPointsInputController;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import java.util.Arrays;
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
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

/**
 * Skill points input view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 06/03/2021
 */
public class SkillPointsInputView extends View {

  // ---------------------------------------- Private fields ------------------------------------

  private final JPanel masterPanel;

  private final transient @Getter Map<Player, JSpinner> spinnersMap;
  private final transient Map<JSpinner, JLabel> labelsMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the skill points input view.
   */
  public SkillPointsInputView() {
    super("Ingreso de puntuaciones");

    masterPanel = new JPanel(new MigLayout());

    spinnersMap = new HashMap<>();
    labelsMap = new HashMap<>();

    initializeInterface();
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Initializes the view and makes it visible.
   */
  @Override
  public void initializeInterface() {
    setTitle(getFrameTitle());
    setResizable(false);
    setIconImage(Constants.ICON
                          .getImage());
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    addSpinners();
    addButtons();
    add(masterPanel);
    pack();
    
  }

  /**
   * Updates the labels with the players names.
   */
  public void updateNameLabels() {
    for (Position position : Position.values()) {
      for (Player player : CommonFields.getPlayersSets()
                                       .get(position)) {
        labelsMap.get(spinnersMap.get(player))
                 .setText(player.getName());
      }
    }
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Adds the buttons to their corresponding panel.
   */
  @Override
  protected void addButtons() {
    JButton finishButton = new JButton("Finalizar");
    JButton resetSkillPointsButton = new JButton("Reiniciar puntuaciones");
    JButton backButton = new JButton("Atrás");

    finishButton.addActionListener(e ->
        ((SkillPointsInputController) CommonFunctions.getController(ProgramView.SKILL_POINTS))
        .finishButtonEvent()
    );

    resetSkillPointsButton.addActionListener(e ->
        ((SkillPointsInputController) CommonFunctions.getController(ProgramView.SKILL_POINTS))
        .resetSkillsButtonEvent()
    );

    backButton.addActionListener(e ->
        ((SkillPointsInputController) CommonFunctions.getController(ProgramView.SKILL_POINTS))
        .backButtonEvent()
    );

    for (JButton button : Arrays.asList(finishButton, resetSkillPointsButton, backButton)) {
      masterPanel.add(button, "grow, span");
    }
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the spinners to their corresponding panel.
   */
  private void addSpinners() {
    for (Position position : Position.values()) {
      JLabel positionLabel = new JLabel(CommonFields.getPositionsMap()
                                                    .get(position));

      positionLabel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

      masterPanel.add(positionLabel, "grow, span");

      List<Player> currentSet = CommonFields.getPlayersSets()
                                            .get(position);

      for (int j = 0; j < currentSet.size(); j++) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(Constants.SKILL_INI,
                                                               Constants.SKILL_MIN,
                                                               Constants.SKILL_MAX,
                                                               Constants.SKILL_STEP));

        JLabel nameLabel = new JLabel(currentSet.get(j)
                                                .getName());

        spinnersMap.put(currentSet.get(j), spinner);

        labelsMap.put(spinner, nameLabel);

        masterPanel.add(nameLabel, "pushx");
        masterPanel.add(spinnersMap.get(currentSet.get(j)), j % 2 != 0 ? "wrap" : null);
      }

      spinnersMap.values()
                 .forEach(s -> ((DefaultEditor) s.getEditor()).getTextField()
                                                              .setEditable(false));
    }
  }
}