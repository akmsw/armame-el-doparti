package armameeldoparti.controllers;

import armameeldoparti.views.MainMenuView;

/**
 * Clase correspondiente al controlador de la ventana del menú principal.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 26/07/2022
 */
public class MainMenuController {

  // ---------------------------------------- Campos privados -----------------------------------

  private static MainMenuView mainMenuView = new MainMenuView();

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Constructor vacío.
   */
  private MainMenuController() {
    // No implementado
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  /**
   * Hace visible la ventana del menú principal.
   */
  public static void showMainMenuView() {
    mainMenuView.setVisible(true);
  }

  /**
   * Hace visible la ventana del menú principal.
   */
  public static void hideMainMenuView() {
    mainMenuView.setVisible(false);
  }

  /**
   * Controlador para la pulsación del botón de ayuda.
   *
   * <p>Hace invisible la ventana del menú principal
   * y hace visible la ventana de ayuda.
   */
  public static void helpButtonEvent() {
    hideMainMenuView();

    HelpController.updatePage();
    HelpController.showHelpView();
  }

  /**
   * Controlador para la pulsación del botón de ayuda.
   *
   * <p>Hace invisible la ventana del menú principal
   *  y hace visible la ventana de ingreso de nombres.
   */
  public static void startButtonEvent() {
    NamesInputController.showNamesInputView();

    hideMainMenuView();
  }
}