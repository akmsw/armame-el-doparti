package armameeldopartidesktop.utils.common.custom.graphical.ui;

import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicRadioButtonUI;

import armameeldopartidesktop.utils.common.Constants;

/**
 * A custom radio button UI that fits the overall program aesthetics.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class CustomRadioButtonUI extends BasicRadioButtonUI {

  // ---------- Public static methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new custom radio button UI that fits the overall program aesthetics.
   *
   * @param component Component to which to apply the custom UI.
   *
   * @return A new custom radio button UI.
   */
  public static ComponentUI createUI(JComponent component) {
    JRadioButton radioButton = (JRadioButton) component;

    radioButton.setBackground(Constants.COLOR_GREEN_LIGHT);
    radioButton.setFocusable(false);
    radioButton.setIcon(Constants.ICON_RB_E_US_UF);
    radioButton.setSelectedIcon(Constants.ICON_RB_E_S_UF);
    radioButton.setDisabledIcon(Constants.ICON_RB_D_US);
    radioButton.setDisabledSelectedIcon(Constants.ICON_RB_D_S);
    radioButton.setRolloverIcon(Constants.ICON_RB_E_US_F);
    radioButton.setRolloverSelectedIcon(Constants.ICON_RB_E_S_F);
    radioButton.setPressedIcon(Constants.ICON_RB_E_US_P);

    return new CustomRadioButtonUI();
  }
}