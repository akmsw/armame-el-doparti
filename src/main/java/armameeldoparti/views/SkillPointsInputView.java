package armameeldoparti.views;

import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.CustomButton;
import armameeldoparti.utils.common.custom.graphical.CustomLabel;
import armameeldoparti.utils.common.custom.graphical.CustomSpinner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 * Skill points input view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class SkillPointsInputView extends View {

  // ---------------------------------------- Private fields ------------------------------------

  private JButton backButton;
  private JButton finishButton;
  private JButton resetSkillPointsButton;

  private transient Map<JSpinner, JLabel> labelsMap;
  private transient Map<Player, JSpinner> spinnersMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the skill points input view.
   */
  public SkillPointsInputView() {
    super("Ingreso de puntuaciones", "");

    spinnersMap = new HashMap<>();
    labelsMap = new HashMap<>();

    initializeInterface();
  }

  // ---------------------------------------- Public methods ------------------------------------

  @Override
  public void initializeInterface() {
    addSpinners();
    addButtons();
    add(masterPanel);
    pack();
  }

  // ---------------------------------------- Protected methods ---------------------------------

  @Override
  protected void addButtons() {
    backButton = new CustomButton("Atrás", Constants.ROUNDED_BORDER_ARC_GENERAL);
    finishButton = new CustomButton("Finalizar", Constants.ROUNDED_BORDER_ARC_GENERAL);
    resetSkillPointsButton = new CustomButton(
        "Reiniciar puntuaciones", Constants.ROUNDED_BORDER_ARC_GENERAL
    );

    masterPanel.add(
        finishButton,
        CommonFunctions.buildMigLayoutConstraints(
          Constants.MIG_LAYOUT_GROW,
          Constants.MIG_LAYOUT_SPAN
        )
    );
    masterPanel.add(
        resetSkillPointsButton,
        CommonFunctions.buildMigLayoutConstraints(
          Constants.MIG_LAYOUT_GROW,
          Constants.MIG_LAYOUT_SPAN
        )
    );
    masterPanel.add(
        backButton,
        CommonFunctions.buildMigLayoutConstraints(
          Constants.MIG_LAYOUT_GROW,
          Constants.MIG_LAYOUT_SPAN
        )
    );
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the spinners to their corresponding panel.
   */
  private void addSpinners() {
    Map<Position, String> positionsMap = CommonFields.getPositionsMap();

    for (Position position : Position.values()) {
      masterPanel.add(
          new CustomLabel(
            CommonFunctions.capitalize(positionsMap.get(position)),
            SwingConstants.CENTER
          ),
          CommonFunctions.buildMigLayoutConstraints(
            Constants.MIG_LAYOUT_GROW,
            Constants.MIG_LAYOUT_SPAN
          )
      );

      List<Player> currentSet = CommonFields.getPlayersSets()
                                            .get(position);

      int currentSetSize = currentSet.size();

      for (int playerIndex = 0; playerIndex < currentSetSize; playerIndex++) {
        JSpinner spinner = new CustomSpinner(
            new SpinnerNumberModel(
              Constants.SKILL_INI,
              Constants.SKILL_MIN,
              Constants.SKILL_MAX,
              Constants.SKILL_STEP
            )
        );

        JLabel nameLabel = new JLabel(currentSet.get(playerIndex)
                                                .getName());

        spinnersMap.put(currentSet.get(playerIndex), spinner);

        labelsMap.put(spinner, nameLabel);

        masterPanel.add(nameLabel, Constants.MIG_LAYOUT_PUSHX);
        masterPanel.add(spinner, playerIndex % 2 != 0 ? Constants.MIG_LAYOUT_WRAP : null);
      }
    }
  }

  // ---------------------------------------- Getters -------------------------------------------

  public JButton getBackButton() {
    return backButton;
  }

  public JButton getFinishButton() {
    return finishButton;
  }

  public JButton getResetSkillPointsButton() {
    return resetSkillPointsButton;
  }

  public Map<JSpinner, JLabel> getLabelsMap() {
    return labelsMap;
  }

  public Map<Player, JSpinner> getSpinnersMap() {
    return spinnersMap;
  }

  // ---------------------------------------- Setters -------------------------------------------

  public void setBackButton(JButton backButton) {
    this.backButton = backButton;
  }

  public void setFinishButton(JButton finishButton) {
    this.finishButton = finishButton;
  }

  public void setResetSkillPointsButton(JButton resetSkillPointsButton) {
    this.resetSkillPointsButton = resetSkillPointsButton;
  }

  public void setLabelsMap(Map<JSpinner, JLabel> labelsMap) {
    this.labelsMap = labelsMap;
  }

  public void setSpinnersMap(Map<Player, JSpinner> spinnersMap) {
    this.spinnersMap = spinnersMap;
  }
}