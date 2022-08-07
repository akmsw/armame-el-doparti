package armameeldoparti.controllers;

import armameeldoparti.Main;
import armameeldoparti.models.Views;
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
public class MainMenuController extends Controller {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the main menu view controller.
   *
   * @param mainMenuView View to control.
   */
  public MainMenuController(MainMenuView mainMenuView) {
    super(mainMenuView);
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Makes the controlled view visible.
   */
  @Override
  public void showView() {
    getView().setVisible(true);
  }

  /**
   * 'Help' button event handler.
   *
   * <p>Makes the controlled view invisible
   * and shows the help view.
   */
  public void helpButtonEvent() {
    hideView();

    ((HelpController) Main.getController(Views.HELP)).updatePage();

    Main.getController(Views.HELP)
        .showView();
  }

  /**
   * 'Start' button event handler.
   *
   * <p>Makes the controlled view invisible
   * and shows the names input view.
   */
  public void startButtonEvent() {
    Main.getController(Views.NAMES_INPUT)
        .showView();

    hideView();
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Resets the controlled view to its default values.
   */
  @Override
  protected void resetView() {
    // Not needed in this controller
  }
}