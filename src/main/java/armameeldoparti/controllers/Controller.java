package armameeldoparti.controllers;

import armameeldoparti.views.View;
import java.awt.Toolkit;
import javax.swing.JFrame;

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

  // ---------------------------------------- Static methods ------------------------------------

  /**
   * Centers the controlled view on the main screen.
   *
   * <p>ON LINUX: If the combination between {@code setLocation} with
   * Toolkit and {@code setLocationRelativeTo(null)} is not used,
   * the frame won't be centered correctly.
   *
   * @param frame Frame to center.
   */
  static void centerView(JFrame frame) {
    frame.setLocation((Toolkit.getDefaultToolkit()
                              .getScreenSize().width - frame.getSize().width) / 2,
                      (Toolkit.getDefaultToolkit()
                              .getScreenSize().height - frame.getSize().height) / 2);
    frame.setLocationRelativeTo(null);
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
    centerView(controlledView);
  }

  // ---------------------------------------- Abstract protected methods ------------------------

  /**
   * Resets the controlled view to its default values.
   */
  protected abstract void resetView();
}