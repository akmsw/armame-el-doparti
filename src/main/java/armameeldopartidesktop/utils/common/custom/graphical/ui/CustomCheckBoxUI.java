package armameeldopartidesktop.utils.common.custom.graphical.ui;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxUI;

import armameeldopartidesktop.utils.common.Constants;

/**
 * A custom checkbox UI that fits the overall program aesthetics.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CustomCheckBoxUI extends BasicCheckBoxUI {

  // ---------- Public static methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new custom checkbox UI that fits the overall program aesthetics.
   *
   * @param component Component to which to apply the custom UI.
   *
   * @return A new custom checkbox UI.
   */
  public static ComponentUI createUI(JComponent component) {
    JCheckBox checkBox = (JCheckBox) component;

    checkBox.setBackground(Constants.COLOR_GREEN_LIGHT);
    checkBox.setFocusable(false);
    checkBox.setIcon(Constants.ICON_CB_E_US_UF);
    checkBox.setSelectedIcon(Constants.ICON_CB_E_S_UF);
    checkBox.setDisabledIcon(Constants.ICON_CB_D_US);
    checkBox.setDisabledSelectedIcon(Constants.ICON_CB_D_S);
    checkBox.setRolloverIcon(Constants.ICON_CB_E_US_F);
    checkBox.setRolloverSelectedIcon(Constants.ICON_CB_E_S_F);
    checkBox.setPressedIcon(Constants.ICON_CB_E_US_P);

    return new CustomCheckBoxUI();
  }
}