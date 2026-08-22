package armameeldopartidesktop.controllers;

import armameeldopartidesktop.models.enums.Position;
import armameeldopartidesktop.models.enums.ProgramView;
import armameeldopartidesktop.models.Player;
import armameeldopartidesktop.utils.common.CommonFields;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.views.SkillPointsInputView;

/**
 * Skill points input view controller class.
 *
 * @since 3.0.0
 *
 * @version 1.1.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class SkillPointsInputController extends Controller<SkillPointsInputView> {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the skill points input view controller.
   *
   * @param skillPointsInputView View to control.
   */
  public SkillPointsInputController(SkillPointsInputView skillPointsInputView) {
    super(skillPointsInputView);

    setUpListeners();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the entered skill points for each player, makes the controlled view invisible and shows the results view.
   */
  public void finishButtonEvent() {
    hideView();

    view.getSpinnersMap().forEach((player, spinner) -> player.setSkillPoints((int) spinner.getValue()));

    CommonFunctions.getController(ProgramView.RESULTS).showView();
  }

  /**
   * Sets 0 skill points to every player and resets every spinner value to the minimum assignable skill point.
   */
  public void resetSkillPointsButtonEvent() {
    resetSkillPoints();
  }

  /**
   * Resets the controlled view to its default values, makes it invisible and shows the corresponding next view.
   */
  public void backButtonEvent() {
    resetView();
    hideView();

    CommonFunctions.getController(CommonFields.isAnchoragesEnabled() ? ProgramView.ANCHORAGES : ProgramView.NAMES_INPUT).showView();
  }

  /**
   * Updates the players name labels.
   */
  public void updateNameLabels() {
    for (Position position : Position.values()) {
      for (Player player : CommonFields.getPlayersSets().get(position)) {
        view.getLabelsMap()
            .get(view.getSpinnersMap().get(player))
            .setText(player.getName());
      }
    }

    view.refreshView();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void showView() {
    updateNameLabels();

    super.showView();
  }

  @Override
  protected void resetView() {
    resetSkillPoints();
  }

  @Override
  protected void setUpInitialState() {
    // Body not needed in this particular controller
  }

  @Override
  protected void setUpListeners() {
    view.getBackButton().addActionListener(_ -> backButtonEvent());
    view.getFinishButton().addActionListener(_ -> finishButtonEvent());
    view.getResetSkillPointsButton().addActionListener(_ -> resetSkillPointsButtonEvent());
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Sets 0 skill points to every player and resets every spinner value to the minimum skill point.
   */
  private void resetSkillPoints() {
    view.getSpinnersMap()
        .forEach(
          (player, spinner) -> {
            player.setSkillPoints(Constants.PLAYER_NO_SKILL_POINTS_ASSIGNED);
            spinner.setValue(Constants.SKILL_MIN);
          }
        );
  }
}