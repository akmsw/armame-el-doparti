package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * Custom arrow button class.
 *
 * <p>This class is used to instantiate a custom arrow button that fits the overall program
 * aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomArrowButton extends BasicArrowButton {

  /**
   * Builds a basic arrow button pointing up or down, using the established program aesthetics.
   *
   * @param orientation The text to display on the button.
   */
  public CustomArrowButton(int orientation) {
    super(orientation);
    setUpGraphicalProperties();
  }

  // ---------------------------------------- Public methods ------------------------------------

  @Override
  public Insets getInsets() {
    return Constants.INSETS_GENERAL;
  }

  // ---------------------------------------- Public methods ------------------------------------

  @Override
  public void paint(Graphics g) {
    if (getModel().isPressed()) {
      g.setColor(Constants.COLOR_GREEN_MEDIUM);
    } else if (getModel().isRollover()) {
      g.setColor(Constants.COLOR_GREEN_DARK_MEDIUM);
    } else {
      g.setColor(isEnabled() ? getBackground() : Constants.COLOR_GREEN_MEDIUM);
    }

    Graphics2D g2 = (Graphics2D) g.create();

    double heightScaleFactor = (getDirection() == SwingConstants.NORTH) ? 0.75 : 0.25;
    double complementaryHeightScaleFactor = 1.0 - heightScaleFactor;

    int buttonHeight = getHeight();
    int buttonWidth = getWidth();

    int[] pointsX = {
      buttonWidth / 2,
      (int) (buttonWidth * 0.75),
      (int) (buttonWidth * 0.25)
    };

    int[] pointsY = {
      (int) (buttonHeight * complementaryHeightScaleFactor),
      (int) (buttonHeight * heightScaleFactor),
      (int) (buttonHeight * heightScaleFactor)
    };

    Polygon triangle = new Polygon(pointsX, pointsY, 3);

    g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    g2.setStroke(
      new BasicStroke(
        Constants.STROKE_BUTTON_ARROW,
        BasicStroke.CAP_ROUND,
        BasicStroke.JOIN_ROUND
      )
    );
    g2.drawPolygon(triangle);
    g2.fillPolygon(triangle);
    g2.dispose();

    super.paintComponent(g);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Configures the graphical properties of the arrow button in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setOpaque(false);
    setFocusPainted(false);
    setContentAreaFilled(false);
    setBorderPainted(false);
    setBackground(Constants.COLOR_GREEN_DARK);
  }
}