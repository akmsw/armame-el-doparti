package armameeldopartidesktop.utils.common.custom.graphical.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

import armameeldopartidesktop.utils.common.Constants;

/**
 * A custom button UI that fits the overall program aesthetics.
 *
 * @since 3.1.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CustomButtonUI extends BasicButtonUI {

  // ---------- Private constants -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private static final String CLIENT_PROPERTY_ARC = "arc";

  // ---------- Public static methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new custom button UI that fits the overall program aesthetics.
   *
   * <p>The "java:S1172" warning is suppressed since the argument is intentionally unused.
   * <p>The "java:S9149" warning is suppressed since this method hides the parent implementation for createUI.
   *
   * @param component Component to which to apply the custom UI.
   *
   * @return A new custom button UI.
   */
  @SuppressWarnings({"java:S1172", "java:S9149"})
  public static ComponentUI createUI(JComponent component) {
    return new CustomButtonUI();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void installUI(JComponent component) {
    super.installUI(component);

    JButton button = (JButton) component;

    button.setBackground(Constants.COLOR_GREEN_DARK);
    button.setForeground(Color.WHITE);
    button.setContentAreaFilled(false);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setOpaque(false);
    button.setRolloverEnabled(true);
    button.setBorder(
      new EmptyBorder(
        Constants.INSETS_GENERAL.top,
        Constants.INSETS_GENERAL.left,
        Constants.INSETS_GENERAL.bottom,
        Constants.INSETS_GENERAL.right
      )
    );

    setArc(button, Constants.ROUNDED_BORDER_ARC_GENERAL);
  }

  @Override
  public void paint(Graphics graphics, JComponent component) {
    JButton button = (JButton) component;

    Graphics2D graphics2d = (Graphics2D) graphics.create();

    int arc = getArc(button);

    if (!button.isEnabled()) {
      graphics2d.setColor(Constants.COLOR_GREEN_MEDIUM);
    } else if (button.getModel().isPressed()) {
      graphics2d.setColor(Constants.COLOR_GREEN_MEDIUM);
    } else if (button.getModel().isRollover()) {
      graphics2d.setColor(Constants.COLOR_GREEN_DARK_MEDIUM);
    } else {
      graphics2d.setColor(button.getBackground());
    }

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.fill(new RoundRectangle2D.Double(0, 0, ((double) component.getWidth()) - 1, ((double) component.getHeight()) - 1, arc, arc));
    graphics2d.dispose();

    super.paint(graphics, component);
  }

  @Override
  protected void paintText(Graphics graphics, AbstractButton button, Rectangle textRect, String text) {
    Graphics2D graphics2d = (Graphics2D) graphics.create();

    FontMetrics fontMetrics = graphics2d.getFontMetrics();

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.setColor(button.isEnabled() ? button.getForeground() : Constants.COLOR_GREEN_LIGHT);
    graphics2d.drawString(text, textRect.x, (textRect.y + fontMetrics.getAscent()));
    graphics2d.dispose();
  }

  @Override
  protected void paintFocus(Graphics graphics, AbstractButton button, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
    // Intentionally left empty.
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the button arc.
   *
   * @param button Button whose arc should be retrieved.
   *
   * @return The configured arc or the default one.
   */
  private int getArc(JButton button) {
    return ((int) button.getClientProperty(CLIENT_PROPERTY_ARC));
  }

  // ---------- Public static utility methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Sets a custom arc for a button.
   *
   * @param button Button to configure.
   * @param arc    Arc to use.
   */
  public static void setArc(JButton button, int arc) {
    button.putClientProperty(CLIENT_PROPERTY_ARC, arc);
  }
}