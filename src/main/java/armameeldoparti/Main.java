package armameeldoparti;

import armameeldoparti.controllers.MainMenuController;
import armameeldoparti.models.Views;
import armameeldoparti.utils.CommonFields;
import armameeldoparti.utils.CommonFunctions;

/**
 * Main class, only for program and useful fields initialization.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 15/02/2021
 */
public final class Main {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Empty constructor.
   */
  private Main() {
    // No body needed
  }

  // ---------------------------------------- Main entry point ----------------------------------

  /**
   * Runs the program.
   *
   * @param args Program arguments (not used yet).
   */
  public static void main(String[] args) {
    CommonFields.initializeMaps();
    CommonFields.setAnchorages(false);

    CommonFunctions.setGraphicalProperties();
    CommonFunctions.getPlayersDistributionData();
    CommonFunctions.populatePlayersSets();
    CommonFunctions.setUpControllers();

    ((MainMenuController) CommonFunctions.getController(Views.MAIN_MENU)).showView();
  }
}