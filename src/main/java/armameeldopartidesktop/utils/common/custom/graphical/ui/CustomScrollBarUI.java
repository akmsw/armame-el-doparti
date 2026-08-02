package armameeldopartidesktop.utils.common.custom.graphical.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.utils.common.custom.graphical.CustomArrowButton;

/**
 * A custom scrollbar UI that fits the overall program aesthetics.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class CustomScrollBarUI extends BasicScrollBarUI {

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new custom scrollbar UI that fits the overall program aesthetics.
   *
   * <p>The "java:S1172" warning is suppressed since the argument is intentionally unused.
   *
   * @param component Component to which to apply the custom UI.
   *
   * @return A new custom scrollbar UI.
   */
  @SuppressWarnings("java:S1172")
  public static ComponentUI createUI(JComponent component) {
    return new CustomScrollBarUI();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void configureScrollBarColors() {
    thumbColor = Constants.COLOR_GREEN_DARK;
    trackColor = Constants.COLOR_GREEN_MEDIUM;
  }

  @Override
  protected void paintThumb(Graphics graphics, JComponent component, Rectangle thumbBounds) {
    Graphics2D graphics2d = (Graphics2D) graphics.create();

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.setColor(Constants.COLOR_GREEN_DARK);
    graphics2d.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, Constants.ROUNDED_BORDER_ARC_SCROLLBAR, Constants.ROUNDED_BORDER_ARC_SCROLLBAR);
    graphics2d.dispose();
  }

  @Override
  protected void paintTrack(Graphics graphics, JComponent component, Rectangle trackBounds) {
    Graphics2D graphics2d = (Graphics2D) graphics.create();

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.setColor(Constants.COLOR_GREEN_MEDIUM);
    graphics2d.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, Constants.ROUNDED_BORDER_ARC_SCROLLBAR, Constants.ROUNDED_BORDER_ARC_SCROLLBAR);
    graphics2d.dispose();
  }

  @Override
  protected JButton createDecreaseButton(int orientation) {
    return new CustomArrowButton(orientation);
  }

  @Override
  protected JButton createIncreaseButton(int orientation) {
    return new CustomArrowButton(orientation);
  }
}