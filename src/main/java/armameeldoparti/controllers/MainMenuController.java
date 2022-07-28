package armameeldoparti.controllers;

import armameeldoparti.interfaces.Controller;
import armameeldoparti.utils.Main;
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
public class MainMenuController implements Controller {

  // ---------------------------------------- Campos privados -----------------------------------

  private MainMenuView mainMenuView;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye el controlador para la vista del menú principal.
   *
   * @param mainMenuView Vista a controlar.
   */
  public MainMenuController(MainMenuView mainMenuView) {
    this.mainMenuView = mainMenuView;
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  /**
   * Hace visible la ventana controlada.
   */
  @Override
  public void showView() {
    mainMenuView.setVisible(true);
  }

  /**
   * Hace invisible la ventana controlada.
   */
  @Override
  public void hideView() {
    mainMenuView.setVisible(false);
    Controller.centerView(mainMenuView);
  }

  /**
   * Reinicia la ventana controlada a sus valores por defecto.
   */
  @Override
  public void resetView() {
    // No es necesario implementarlo en esta ventana
  }

  /**
   * Controlador para la pulsación del botón de ayuda.
   *
   * <p>Hace invisible la ventana del menú principal
   * y hace visible la ventana de ayuda.
   */
  public void helpButtonEvent() {
    hideView();

    Main.getHelpController()
        .updatePage();

    Main.getHelpController()
        .showView();
  }

  /**
   * Controlador para la pulsación del botón de ayuda.
   *
   * <p>Hace invisible la ventana del menú principal
   *  y hace visible la ventana de ingreso de nombres.
   */
  public void startButtonEvent() {
    Main.getNamesInputController()
        .showView();

    hideView();
  }
}