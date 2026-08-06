package armameeldopartidesktop.utils.common.custom.graphical.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.SeparatorUI;

import armameeldopartidesktop.utils.common.Constants;

/**
 * A custom separator UI that fits the overall program aesthetics.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CustomSeparatorUI extends SeparatorUI {

  // ---------- Public static methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new custom separator UI that fits the overall program aesthetics.
   *
   * @param component Component to which to apply the custom UI.
   *
   * @return A new custom separator UI.
   */
  public static ComponentUI createUI(JComponent component) {
    component.setBackground(Constants.COLOR_GREEN_LIGHT);
    component.setForeground(Constants.COLOR_GREEN_MEDIUM);

    return new CustomSeparatorUI();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void paint(Graphics graphics, JComponent component) {
    Graphics2D graphics2d = (Graphics2D) graphics;

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.fillRoundRect(0, (component.getHeight() / 2), component.getWidth(), 5, Constants.ROUNDED_BORDER_ARC_SEPARATOR, Constants.ROUNDED_BORDER_ARC_SEPARATOR);
    graphics2d.dispose();
  }
}