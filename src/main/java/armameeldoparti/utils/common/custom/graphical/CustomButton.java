package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import lombok.NonNull;

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
  public CustomButton(@NonNull String text) {
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
      g.setColor(Constants.GREEN_MEDIUM);
    } else if (getModel().isRollover()) {
      g.setColor(Constants.GREEN_DARK_MEDIUM);
    } else {
      g.setColor(getBackground());
    }

    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g2.fill(
      new RoundRectangle2D.Double(
        0,
        0,
        ((double) getWidth()) - 1,
        ((double) getHeight()) - 1,
        Constants.BUTTON_BORDER_ARC,
        Constants.BUTTON_BORDER_ARC
      )
    );

    super.paintComponent(g);
  }

  /**
   * Paints the custom button rounded border.
   *
   * @param g The custom button graphics to edit and paint.
   */
  @Override
  protected void paintBorder(Graphics g) {
    g.setColor(Constants.GREEN_MEDIUM);

    Graphics2D g2 = (Graphics2D) g;

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.draw(
      new RoundRectangle2D.Double(
        0,
        0,
        ((double) getWidth()) - 1,
        ((double) getHeight()) - 1,
        Constants.BUTTON_BORDER_ARC,
        Constants.BUTTON_BORDER_ARC
      )
    );
  }
}