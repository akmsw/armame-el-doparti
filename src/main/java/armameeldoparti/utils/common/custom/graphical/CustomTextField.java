package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.JTextField;

/**
 * Custom text field class.
 *
 * <p>This class is used to instantiate a custom text field that fits the overall program aesthetics.
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

  // ---------------------------------------- Public methods ------------------------------------

  @Override
  public Insets getInsets() {
    return Constants.INSETS_GENERAL;
  }

  // ---------------------------------------- Protected methods ---------------------------------

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    g2.setColor(getBackground());
    g2.fillRoundRect(
      0,
      0,
      (getWidth() - 1),
      (getHeight() - 1),
      Constants.ROUNDED_BORDER_ARC_GENERAL,
      Constants.ROUNDED_BORDER_ARC_GENERAL
    );
    g2.dispose();

    super.paintComponent(g);
  }

  @Override
  protected void paintBorder(Graphics g) {
    g.setColor(Constants.COLOR_GREEN_LIGHT);

    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
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

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Configures the graphical properties for the text field in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setOpaque(false);
  }
}