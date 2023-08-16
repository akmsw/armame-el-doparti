package armameeldoparti.controllers;

import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.views.View;
import java.awt.Rectangle;

/**
 * Abstract class that specifies the basic methods for interaction between controllers and their
 * assigned views.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public abstract class Controller<T extends View> {

  // ---------------------------------------- Protected fields ----------------------------------

  protected T view;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the view controller.
   *
   * @param view View to control.
   */
  protected Controller(T view) {
    setView(view);
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Centers the controlled view on the current active monitor.
   */
  protected final void centerView() {
    Rectangle activeMonitorBounds = CommonFields.getActiveMonitor()
                                                .getDefaultConfiguration()
                                                .getBounds();

    view.setLocation(
        (activeMonitorBounds.width - view.getWidth()) / 2 + activeMonitorBounds.x,
        (activeMonitorBounds.height - view.getHeight()) / 2 + activeMonitorBounds.y
    );
  }

  /**
   * Makes the controlled view invisible.
   */
  protected final void hideView() {
    view.setVisible(false);

    CommonFunctions.updateActiveMonitorFromView(view);
  }

  /**
   * Makes the controlled view visible.
   */
  protected void showView() {
    centerView();

    view.setVisible(true);
  }

  // ---------------------------------------- Abstract protected methods ------------------------

  /**
   * Resets the controlled view to its default values.
   */
  protected abstract void resetView();

  // ---------------------------------------- Getters -------------------------------------------

  public T getView() {
    return view;
  }

  // ---------------------------------------- Setters -------------------------------------------

  public void setView(T view) {
    this.view = view;
  }
}