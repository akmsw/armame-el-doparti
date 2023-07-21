package armameeldoparti.controllers;

import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.views.View;
import java.awt.Rectangle;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public abstract class Controller {

  // ---------------------------------------- Private fields ------------------------------------

  private View view;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the view controller.
   *
   * @param view View to control.
   */
  protected Controller(View view) {
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

    view.setLocation((activeMonitorBounds.width - view.getWidth()) / 2 + activeMonitorBounds.x,
                     (activeMonitorBounds.height - view.getHeight()) / 2 + activeMonitorBounds.y);
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
}