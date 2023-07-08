package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * Custom arrow button class.
 *
 * <p>This class is used to instantiate a custom arrow button that fits the overall program
 * aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since v3.0
 */
public class CustomArrowButton extends BasicArrowButton {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds a basic arrow button using the established program aesthetics.
   *
   * @param orientation Arrow button orientation.
   */
  public CustomArrowButton(int orientation) {
    super(
        orientation,
        Constants.GREEN_DARK,
        Constants.GREEN_DARK,
        Constants.GREEN_LIGHT,
        Constants.GREEN_DARK
    );
    setupGraphicalProperties();
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Configures the graphical properties of the arrow button in order to fit the program aesthetics.
   */
  private void setupGraphicalProperties() {
    setBorder(new LineBorder(Constants.GREEN_MEDIUM));
  }
}