package armameeldoparti.controllers;

import armameeldoparti.Main;
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

  // ---------------------------------------- Private fields ------------------------------------

  private SkillPointsInputView skillPointsInputView;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the skills input view controller.
   *
   * @param skillPointsInputView View to control.
   */
  public SkillPointsInputController(SkillPointsInputView skillPointsInputView) {
    this.skillPointsInputView = skillPointsInputView;
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Makes the controlled view visible.
   */
  @Override
  public void showView() {
    skillPointsInputView.setVisible(true);
  }

  /**
   * Makes the controlled view invisible.
   */
  @Override
  public void hideView() {
    skillPointsInputView.setVisible(false);
    Controller.centerView(skillPointsInputView);
  }

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
    skillPointsInputView.getSpinnersMap()
                   .forEach((k, v) -> k.setSkillPoints((int) v.getValue()));

    hideView();

    Main.getResultsController()
        .setUp();

    Main.getResultsController()
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

    if (Main.thereAreAnchorages()) {
      Main.getAnchoragesController()
          .showView();
    } else {
      Main.getNamesInputController()
          .showView();
    }
  }

  /**
   * Updates the players name labels.
   */
  public void updateNameLabels() {
    skillPointsInputView.updateNameLabels();
    skillPointsInputView.pack();
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Sets 0 skill points to every player and resets every spinner
   * value to the minimum skill point.
   */
  private void resetSkills() {
    skillPointsInputView.getSpinnersMap()
                   .forEach((k, v) -> {
                     k.setSkillPoints(0);
                     v.setValue(Main.SKILL_MIN);
                   });
  }
}