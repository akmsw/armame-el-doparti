package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

/**
 * Custom text field class.
 *
 * <p>This class is used to instantiate a custom text field that fits the overall program
 * aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomTextField extends JTextField {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds a basic text field using the established program aesthetics.
   */
  public CustomTextField() {
    setUpGraphicalProperties();
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Configures the graphical properties for the text field in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
                                              Constants.GREEN_MEDIUM,
                                              Constants.GREEN_MEDIUM));
  }
}