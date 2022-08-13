package armameeldoparti.controllers;

import armameeldoparti.views.View;
import java.awt.Toolkit;

/**
 * Abstract class that specifies the basic methods for
 * interaction between controllers and their assigned views.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 28/07/2022
 */
public abstract class Controller {

  // ---------------------------------------- Private fields ------------------------------------

  private View controlledView;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the view controller.
   *
   * @param controlledView View to control.
   */
  protected Controller(View controlledView) {
    setView(controlledView);
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Gets the controlled view.
   *
   * @return The controlled view.
   */
  public View getView() {
    return controlledView;
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Updates the controlled view object.
   *
   * @param controlledView The new controlled view.
   */
  protected void setView(View controlledView) {
    this.controlledView = controlledView;
  }

  /**
   * Makes the controlled view visible.
   */
  protected void showView() {
    controlledView.setVisible(true);
  }

  /**
   * Makes the controlled view invisible.
   */
  protected void hideView() {
    controlledView.setVisible(false);

    centerView();
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Centers the controlled view on the main screen.
   *
   * <p>ON LINUX: If the combination between {@code setLocation} with
   * Toolkit and {@code setLocationRelativeTo(null)} is not used,
   * the frame won't be centered correctly.
   */
  private void centerView() {
    controlledView.setLocation((Toolkit.getDefaultToolkit()
                                       .getScreenSize()
                                       .width - controlledView.getSize()
                                                              .width) / 2,
                               (Toolkit.getDefaultToolkit()
                                       .getScreenSize()
                                       .height - controlledView.getSize()
                                                               .height) / 2);
    controlledView.setLocationRelativeTo(null);
  }

  // ---------------------------------------- Abstract protected methods ------------------------

  /**
   * Resets the controlled view to its default values.
   */
  protected abstract void resetView();
}