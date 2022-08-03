package armameeldoparti.controllers;

import armameeldoparti.Main;
import armameeldoparti.views.MainMenuView;

/**
 * Main menu view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 26/07/2022
 */
public class MainMenuController implements Controller {

  // ---------------------------------------- Private fields ------------------------------------

  private MainMenuView mainMenuView;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the main menu view controller.
   *
   * @param mainMenuView View to control.
   */
  public MainMenuController(MainMenuView mainMenuView) {
    this.mainMenuView = mainMenuView;
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Makes the controlled view visible.
   */
  @Override
  public void showView() {
    mainMenuView.setVisible(true);
  }

  /**
   * Makes the controlled view invisible.
   */
  @Override
  public void hideView() {
    mainMenuView.setVisible(false);
    Controller.centerView(mainMenuView);
  }

  /**
   * Resets the controlled view to its default values.
   */
  @Override
  public void resetView() {
    // Not needed in this controller
  }

  /**
   * 'Help' button event handler.
   *
   * <p>Makes the controlled view invisible
   * and shows the help view.
   */
  public void helpButtonEvent() {
    hideView();

    Main.getHelpController()
        .updatePage();

    Main.getHelpController()
        .showView();
  }

  /**
   * 'Start' button event handler.
   *
   * <p>Makes the controlled view invisible
   * and shows the names input view.
   */
  public void startButtonEvent() {
    Main.getNamesInputController()
        .showView();

    hideView();
  }
}