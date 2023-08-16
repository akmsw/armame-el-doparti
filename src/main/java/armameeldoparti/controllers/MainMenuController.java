package armameeldoparti.controllers;

import armameeldoparti.models.Error;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.views.MainMenuView;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Main menu view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class MainMenuController extends Controller<MainMenuView> {

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

  @Override
  public void showView() {
    centerView();

    view.setVisible(true);
  }

  /**
   * 'Help' button event handler.
   *
   * <p>Makes the controlled view invisible and shows the help view.
   */
  public void helpButtonEvent() {
    HelpController controller = (HelpController) CommonFunctions.getController(ProgramView.HELP);

    hideView();

    controller.updatePage();
    controller.showView();
  }

  /**
   * 'Start' button event handler.
   *
   * <p>Makes the controlled view invisible and shows the names input view.
   */
  public void startButtonEvent() {
    hideView();

    CommonFunctions.getController(ProgramView.NAMES_INPUT)
                   .showView();
  }

  /**
   * 'Contact' button event handler.
   *
   * <p>Opens the browser on the contact URL.
   */
  public void contactButtonEvent() {
    browserRedirect(Constants.URL_CONTACT);
  }

  /**
   * 'Reports & suggestions' button event handler.
   *
   * <p>Opens the browser on the issues URL.
   */
  public void issuesButtonEvent() {
    browserRedirect(Constants.URL_ISSUES);
  }

  // ---------------------------------------- Protected methods ---------------------------------

  @Override
  protected void resetView() {
    // Body not needed in this particular controller
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Opens a new tab in the default web browser with the specified URL.
   *
   * @param link Destination URL.
   */
  private void browserRedirect(String link) {
    try {
      Desktop.getDesktop()
             .browse(new URI(link));
    } catch (IOException | URISyntaxException e) {
      CommonFunctions.exitProgram(Error.BROWSER_ERROR);
    }
  }
}