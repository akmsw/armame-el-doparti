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
import lombok.NonNull;

/**
 * Skill points input view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class SkillPointsInputController extends Controller {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the skills input view controller.
   *
   * @param skillPointsInputView View to control.
   */
  public SkillPointsInputController(@NonNull SkillPointsInputView skillPointsInputView) {
    super(skillPointsInputView);
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Resets the controlled view to its default values and makes it invisible.
   */
  @Override
  public void resetView() {
    resetSkills();
    hideView();
  }

  /**
   * 'Finish' button event handler.
   *
   * <p>Sets the entered skill points for each player, makes the controlled view invisible and shows
   * the results view.
   */
  public void finishButtonEvent() {
    hideView();

    ((SkillPointsInputView) getView()).getSpinnersMap()
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
  public void resetSkillsButtonEvent() {
    resetSkills();
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
    Map<JSpinner, JLabel> labelsMap = ((SkillPointsInputView) getView()).getLabelsMap();
    Map<Player, JSpinner> spinnersMap = ((SkillPointsInputView) getView()).getSpinnersMap();

    for (Position position : Position.values()) {
      for (Player player : CommonFields.getPlayersSets()
                                       .get(position)) {
        labelsMap.get(spinnersMap.get(player))
                 .setText(player.getName());
      }
    }

    getView().pack();
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Sets 0 skill points to every player and resets every spinner value to the minimum skill point.
   */
  private void resetSkills() {
    ((SkillPointsInputView) getView()).getSpinnersMap()
                                      .forEach((k, v) -> {
                                        k.setSkillPoints(0);
                                        v.setValue(Constants.SKILL_MIN);
                                      });
  }
}