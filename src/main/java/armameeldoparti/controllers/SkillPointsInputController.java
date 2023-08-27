package armameeldoparti.controllers;

import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.views.SkillPointsInputView;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JSpinner;

/**
 * Skill points input view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class SkillPointsInputController extends Controller<SkillPointsInputView> {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the skill points input view controller.
   *
   * @param skillPointsInputView View to control.
   */
  public SkillPointsInputController(SkillPointsInputView skillPointsInputView) {
    super(skillPointsInputView);
    setUpListeners();
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * 'Finish' button event handler.
   *
   * <p>Sets the entered skill points for each player, makes the controlled view invisible and shows
   * the results view.
   */
  public void finishButtonEvent() {
    hideView();

    view.getSpinnersMap()
        .forEach((k, v) -> k.setSkillPoints((int) v.getValue()));

    ((ResultsController) CommonFunctions.getController(ProgramView.RESULTS)).setUp();

    CommonFunctions.getController(ProgramView.RESULTS)
                   .showView();
  }

  /**
   * 'Reset skill points' button event handler.
   *
   * <p>Sets 0 skill points to every player and resets every spinner value to the minimum skill
   * point.
   */
  public void resetSkillPointsButtonEvent() {
    resetSkillPoints();
  }

  /**
   * 'Back' button event handler.
   *
   * <p>Resets the controlled view to its default values and shows the corresponding next view.
   */
  public void backButtonEvent() {
    resetView();

    CommonFunctions.getController(CommonFields.isAnchoragesEnabled() ? ProgramView.ANCHORAGES
                                                                     : ProgramView.NAMES_INPUT)
                   .showView();
  }

  /**
   * Updates the players name labels.
   */
  public void updateNameLabels() {
    Map<JSpinner, JLabel> labelsMap = view.getLabelsMap();
    Map<Player, JSpinner> spinnersMap = view.getSpinnersMap();

    for (Position position : Position.values()) {
      for (Player player : CommonFields.getPlayersSets()
                                       .get(position)) {
        labelsMap.get(spinnersMap.get(player))
                 .setText(player.getName());
      }
    }

    view.pack();
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Resets the controlled view to its default values and makes it invisible.
   */
  @Override
  protected void resetView() {
    resetSkillPoints();
    hideView();
  }

  @Override
  protected void setUpInitialState() {
    // Body not needed in this particular controller
  }

  @Override
  protected void setUpListeners() {
    view.getBackButton()
        .addActionListener(e -> backButtonEvent());

    view.getFinishButton()
        .addActionListener(e -> finishButtonEvent());

    view.getResetSkillPointsButton()
        .addActionListener(e -> resetSkillPointsButtonEvent());
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Sets 0 skill points to every player and resets every spinner value to the minimum skill point.
   */
  private void resetSkillPoints() {
    view.getSpinnersMap()
        .forEach((player, spinner) -> {
          player.setSkillPoints(0);
          spinner.setValue(Constants.SKILL_MIN);
        });
  }
}