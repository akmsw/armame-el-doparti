package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.JButton;

/**
 * Custom text area class.
 *
 * <p>This class is used to instantiate a custom button that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomButton extends JButton {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds a basic button using the established program aesthetics.
   *
   * @param text The text to display on the button.
   */
  public CustomButton(String text) {
    super(text);
    setOpaque(false);
    setFocusPainted(false);
    setContentAreaFilled(false);
    setBorderPainted(false);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Paints the custom button graphics.
   *
   * @param g The custom button graphics to edit and paint.
   */
  @Override
  protected void paintComponent(Graphics g) {
    if (getModel().isPressed()) {
      g.setColor(Constants.COLOR_GREEN_MEDIUM);
    } else if (getModel().isRollover()) {
      g.setColor(Constants.COLOR_GREEN_DARK_MEDIUM);
    } else {
      g.setColor(getBackground());
    }

    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    g2.fillRoundRect(
        0,
        0,
        (getWidth() - 1),
        (getHeight() - 1),
        Constants.ROUNDED_BORDER_ARC_GENERAL,
        Constants.ROUNDED_BORDER_ARC_GENERAL
    );

    super.paintComponent(g);
  }

  /**
   * Hmm.
   */
  @Override
  public Insets getInsets() {
    return new Insets(
      Constants.ROUNDED_BORDER_INSETS_GENERAL,
      Constants.ROUNDED_BORDER_INSETS_GENERAL,
      Constants.ROUNDED_BORDER_INSETS_GENERAL,
      Constants.ROUNDED_BORDER_INSETS_GENERAL
    );
  }
}