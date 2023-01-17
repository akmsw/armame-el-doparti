package armameeldoparti.controllers;

import armameeldoparti.views.View;
import java.awt.Toolkit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

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

  private @Getter @Setter(AccessLevel.PROTECTED) View view;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the view controller.
   *
   * @param view View to control.
   */
  protected Controller(@NotNull View view) {
    setView(view);
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Makes the controlled view invisible.
   */
  protected void hideView() {
    view.setVisible(false);

    centerView();
  }

  /**
   * Makes the controlled view visible.
   */
  protected void showView() {
    view.setVisible(true);
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
    view.setLocation((Toolkit.getDefaultToolkit()
                             .getScreenSize()
                             .width - view.getSize()
                                         .width) / 2,
                     (Toolkit.getDefaultToolkit()
                             .getScreenSize()
                             .height - view.getSize()
                                           .height) / 2);
    view.setLocationRelativeTo(null);
  }

  // ---------------------------------------- Abstract protected methods ------------------------

  /**
   * Resets the controlled view to its default values.
   */
  protected abstract void resetView();
}