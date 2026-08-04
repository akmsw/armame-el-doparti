package armameeldopartidesktop.controllers;

import armameeldopartidesktop.models.enums.ProgramView;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.views.MainMenuView;

/**
 * Main menu view controller class.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class MainMenuController extends Controller<MainMenuView> {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the main menu view controller.
   *
   * @param mainMenuView View to control.
   */
  public MainMenuController(MainMenuView mainMenuView) {
    super(mainMenuView);

    setUpListeners();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void showView() {
    super.showView();
  }

  /**
   * Makes the controlled view invisible and shows the help view.
   */
  public void helpButtonEvent() {
    hideView();

    CommonFunctions.getController(ProgramView.HELP)
                   .showView();
  }

  /**
   * Makes the controlled view invisible and shows the names input view.
   */
  public void startButtonEvent() {
    hideView();

    CommonFunctions.getController(ProgramView.NAMES_INPUT)
                   .showView();
  }

  /**
   * Opens the browser on the contact URL.
   */
  public void contactButtonEvent() {
    CommonFunctions.browserRedirect(Constants.URL_CONTACT);
  }

  /**
   * Opens the browser on the issues URL.
   */
  public void issuesButtonEvent() {
    CommonFunctions.browserRedirect(Constants.URL_ISSUES);
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void resetView() {
    // Body not needed in this particular controller
  }

  @Override
  protected void setUpInitialState() {
    // Body not needed in this particular controller
  }

  @Override
  protected void setUpListeners() {
    view.getStartButton().addActionListener(_ -> startButtonEvent());
    view.getHelpButton().addActionListener(_ -> helpButtonEvent());
    view.getContactButton().addActionListener(_ -> contactButtonEvent());
    view.getIssuesButton().addActionListener(_ -> issuesButtonEvent());
  }
}