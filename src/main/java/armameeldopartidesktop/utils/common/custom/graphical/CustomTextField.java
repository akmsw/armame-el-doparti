package armameeldopartidesktop.utils.common.custom.graphical;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JTextField;

import armameeldopartidesktop.utils.common.Constants;

/**
 * A custom text field that fits the overall program aesthetics.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CustomTextField extends JTextField implements CustomComponent {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a custom text field that fits the overall program aesthetics.
   */
  public CustomTextField() {
    setUpGraphicalProperties();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void setUpGraphicalProperties() {
    setOpaque(false);
  }

  @Override
  public Insets getInsets() {
    return Constants.INSETS_GENERAL;
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void paintComponent(Graphics graphics) {
    Graphics2D graphics2d = (Graphics2D) graphics.create();

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.setColor(getBackground());
    graphics2d.fillRoundRect(0, 0, (getWidth() - 1), (getHeight() - 1), Constants.ROUNDED_BORDER_ARC_GENERAL, Constants.ROUNDED_BORDER_ARC_GENERAL);
    graphics2d.dispose();

    super.paintComponent(graphics);
  }

  @Override
  protected void paintBorder(Graphics graphics) {
    graphics.setColor(Constants.COLOR_GREEN_LIGHT);

    Graphics2D graphics2d = (Graphics2D) graphics.create();

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.setColor(getBackground());
    graphics2d.drawRoundRect(0, 0, (getWidth() - 1), (getHeight() - 1), Constants.ROUNDED_BORDER_ARC_GENERAL, Constants.ROUNDED_BORDER_ARC_GENERAL);
    graphics2d.dispose();
  }
}