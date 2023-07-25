package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.Map;
import javax.swing.JTextField;

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
    setOpaque(false);
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHints(
        Map.of(
          RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
          RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY,
          RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE
        )
    );
    g2.setColor(getBackground());
    g2.fillRoundRect(
        0,
        0,
        (getWidth() - 1),
        (getHeight() - 1),
        Constants.ROUNDED_BORDER_ARC_GENERAL,
        Constants.ROUNDED_BORDER_ARC_GENERAL
    );

    super.paintComponent(g);

    g2.dispose();
  }

  @Override
  protected void paintBorder(Graphics g) {
    g.setColor(Constants.GREEN_LIGHT);

    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHints(
        Map.of(
          RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
          RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY,
          RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE
        )
    );
    g2.setColor(getBackground());
    g2.drawRoundRect(
        0,
        0,
        (getWidth() - 1),
        (getHeight() - 1),
        Constants.ROUNDED_BORDER_ARC_GENERAL,
        Constants.ROUNDED_BORDER_ARC_GENERAL
    );
    g2.dispose();
  }

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