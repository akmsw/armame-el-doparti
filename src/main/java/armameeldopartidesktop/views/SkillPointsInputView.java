package armameeldopartidesktop.views;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import armameeldopartidesktop.models.Player;
import armameeldopartidesktop.models.enums.Position;
import armameeldopartidesktop.utils.common.CommonFields;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.utils.common.custom.graphical.CustomLabel;

/**
 * Skill points input view class.
 *
 * @since 3.0.0
 *
 * @version 1.1.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class SkillPointsInputView extends View {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private JButton backButton;
  private JButton finishButton;
  private JButton resetSkillPointsButton;

  private transient Map<JSpinner, JLabel> labelsMap;
  private transient Map<Player, JSpinner> spinnersMap;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the skill points input view.
   */
  public SkillPointsInputView() {
    super(Constants.TITLE_VIEW_SKILL_POINTS_INPUT, null);

    setSpinnersMap(new HashMap<>());
    setLabelsMap(new HashMap<>());
    initializeInterface();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void initializeInterface() {
    addSpinners();
    addButtons();
    refreshView();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void addButtons() {
    setBackButton(new JButton("Atrás"));
    setFinishButton(new JButton("Finalizar"));
    setResetSkillPointsButton(new JButton("Reiniciar puntuaciones"));

    add(finishButton, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROW, Constants.MIG_LAYOUT_SPAN));
    add(resetSkillPointsButton, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROW, Constants.MIG_LAYOUT_SPAN));
    add(backButton, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROW, Constants.MIG_LAYOUT_SPAN));
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Adds the spinners.
   */
  private void addSpinners() {
    for (Position position : Position.values()) {
      add(
        new CustomLabel(CommonFunctions.capitalize(Constants.MAP_POSITIONS.get(position)), null, SwingConstants.CENTER),
        CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROW, Constants.MIG_LAYOUT_SPAN)
      );

      List<Player> players = new ArrayList<>(CommonFields.getPlayersSets().get(position));

      players.sort(Comparator.comparing(player -> player.getPosition().ordinal()));

      for (Player player : players) {
        spinnersMap.put(player, new JSpinner(new SpinnerNumberModel(Constants.SKILL_INI, Constants.SKILL_MIN, Constants.SKILL_MAX, Constants.SKILL_STEP)));

        labelsMap.put(spinnersMap.get(player), new JLabel(player.getName()));

        add(labelsMap.get(spinnersMap.get(player)), Constants.MIG_LAYOUT_PUSHX);
        add(spinnersMap.get(player), (((players.indexOf(player) % 2) != 0) ? Constants.MIG_LAYOUT_WRAP : null));
      }
    }
  }

  // ---------- Public getters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

  // ---------- Public setters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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