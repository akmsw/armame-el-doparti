package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * Custom label class.
 *
 * <p>This class is used to instantiate a custom label that fits the overall program
 * aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomLabel extends JLabel {

  // ---------------------------------------- Constructors --------------------------------------

  /**
   * Builds a basic empty label using the established program aesthetics.
   *
   * @param alignment The label text alignment.
   */
  public CustomLabel(int alignment) {
    super();
    setUpGraphicalProperties(alignment);
  }

  /**
   * Builds a basic label using the established program aesthetics.
   *
   * @param text      The label text.
   * @param alignment The label text alignment.
   */
  public CustomLabel(String text, int alignment) {
    super(text);
    setUpGraphicalProperties(alignment);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Configures the graphical properties of the label in order to fit the program aesthetics. The
   * label will have a lowered bevel border.
   *
   * @param alignment The label text alignment.
   */
  private void setUpGraphicalProperties(int alignment) {
    setHorizontalAlignment(alignment);
    setOpaque(false);
    setBorder(
        BorderFactory.createEmptyBorder(
          Constants.ROUNDED_BORDER_INSETS_GENERAL,
          Constants.ROUNDED_BORDER_INSETS_GENERAL,
          Constants.ROUNDED_BORDER_INSETS_GENERAL,
          Constants.ROUNDED_BORDER_INSETS_GENERAL
        )
    );
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
}