package armameeldoparti.controllers;

import armameeldoparti.Main;
import armameeldoparti.views.SkillsInputView;

/**
 * Skills input view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 26/07/2022
 */
public class SkillsInputController extends Controller {

  // ---------------------------------------- Private fields ------------------------------------

  private SkillsInputView skillsInputView;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the skills input view controller.
   *
   * @param skillsInputView View to control.
   */
  public SkillsInputController(SkillsInputView skillsInputView) {
    this.skillsInputView = skillsInputView;
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Makes the controlled view visible.
   */
  @Override
  public void showView() {
    skillsInputView.setVisible(true);
  }

  /**
   * Makes the controlled view invisible.
   */
  @Override
  public void hideView() {
    skillsInputView.setVisible(false);
    Controller.centerView(skillsInputView);
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
    skillsInputView.getSpinnersMap()
                   .forEach((k, v) -> k.setSkill((int) v.getValue()));

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
    skillsInputView.updateNameLabels();
    skillsInputView.pack();
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Sets 0 skill points to every player and resets every spinner
   * value to the minimum skill point.
   */
  private void resetSkills() {
    skillsInputView.getSpinnersMap()
                   .forEach((k, v) -> {
                     k.setSkill(0);
                     v.setValue(Main.SKILL_MIN);
                   });
  }
}