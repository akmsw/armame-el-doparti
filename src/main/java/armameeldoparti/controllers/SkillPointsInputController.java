package armameeldoparti.controllers;

import armameeldoparti.Main;
import armameeldoparti.models.Views;
import armameeldoparti.utils.Constants;
import armameeldoparti.views.SkillPointsInputView;

/**
 * Skill points input view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 26/07/2022
 */
public class SkillPointsInputController extends Controller {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the skills input view controller.
   *
   * @param skillPointsInputView View to control.
   */
  public SkillPointsInputController(SkillPointsInputView skillPointsInputView) {
    super(skillPointsInputView);
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Resets the controlled view to its default values
   * and makes it invisible.
   */
  @Override
  public void resetView() {
    resetSkills();
    hideView();
  }

  /**
   * 'Finish' button event handler.
   *
   * <p>Sets the entered skill points for each player, makes
   * the controlled view invisible and shows the results view.
   */
  public void finishButtonEvent() {
    hideView();

    ((SkillPointsInputView) getView()).getSpinnersMap()
                                      .forEach((k, v) -> k.setSkillPoints((int) v.getValue()));

    ((ResultsController) Main.getController(Views.RESULTS)).setUp();

    Main.getController(Views.RESULTS)
        .showView();
  }

  /**
   * 'Reset skill points' button event handler.
   *
   * <p>Sets 0 skill points to every player and resets every spinner
   * value to the minimum skill point.
   */
  public void resetSkillsButtonEvent() {
    resetSkills();
  }

  /**
   * 'Back' button event handler.
   *
   * <p>Resets the controlled view to its default values
   * and shows the corresponding next view.
   */
  public void backButtonEvent() {
    resetView();

    Main.getController(Main.thereAreAnchorages() ? Views.ANCHORAGES : Views.NAMES_INPUT)
        .showView();
  }

  /**
   * Updates the players name labels.
   */
  public void updateNameLabels() {
    ((SkillPointsInputView) getView()).updateNameLabels();
    getView().pack();
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Sets 0 skill points to every player and resets every spinner
   * value to the minimum skill point.
   */
  private void resetSkills() {
    ((SkillPointsInputView) getView()).getSpinnersMap()
                                      .forEach((k, v) -> {
                                        k.setSkillPoints(0);
                                        v.setValue(Constants.SKILL_MIN);
                                      });
  }
}