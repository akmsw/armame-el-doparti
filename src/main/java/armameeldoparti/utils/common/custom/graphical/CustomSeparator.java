package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JSeparator;

/**
 * Custom separator class.
 *
 * <p>This class is used to instantiate a custom separator that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomSeparator extends JSeparator {

  // ---------------------------------------- Protected methods ---------------------------------

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    g2.fillRoundRect(
        0,
        getHeight() / 2,
        getWidth(),
        5,
        Constants.ROUNDED_BORDER_ARC_SEPARATOR,
        Constants.ROUNDED_BORDER_ARC_SEPARATOR
    );
  }
}