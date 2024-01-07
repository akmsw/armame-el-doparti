package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import javax.swing.JCheckBox;

/**
 * Custom checkbox class.
 *
 * <p>This class is used to instantiate a custom checkbox that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomCheckBox extends JCheckBox {

  // --------------------------------------------------------------- Constructor ---------------------------------------------------------------------

  /**
   * Builds a basic checkbox using the established program aesthetics.
   *
   * @param text The text shown in the checkbox.
   */
  public CustomCheckBox(String text) {
    super(text);
    setUpGraphicalProperties();
  }

  // ---------------------------------------------------------------- Private methods ----------------------------------------------------------------

  /**
   * Configures the graphical properties of the checkbox in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setIcon(Constants.ICON_CB_E_US_UF);
    setSelectedIcon(Constants.ICON_CB_E_S_UF);
    setDisabledIcon(Constants.ICON_CB_D_US);
    setDisabledSelectedIcon(Constants.ICON_CB_D_S);
    setRolloverIcon(Constants.ICON_CB_E_US_F);
    setRolloverSelectedIcon(Constants.ICON_CB_E_S_F);
    setPressedIcon(Constants.ICON_CB_E_US_P);
  }
}