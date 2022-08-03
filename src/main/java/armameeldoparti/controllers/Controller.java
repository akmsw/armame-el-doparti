package armameeldoparti.controllers;

import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * Interface that specifies the basic methods for
 * interaction between controllers and their assigned views.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 28/07/2022
 */
public interface Controller {

  // ---------------------------------------- Abstract public methods ---------------------------

  /**
   * Makes the controlled view visible.
   */
  void showView();

  /**
   * Makes the controlled view invisible.
   */
  void hideView();

  /**
   * Resets the controlled view to its default values.
   */
  void resetView();

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
}