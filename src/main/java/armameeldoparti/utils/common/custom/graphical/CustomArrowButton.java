package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.models.Error;
import armameeldoparti.utils.common.CommonFunctions;
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
 * <p>This class is used to instantiate a custom arrow button that fits the overall program aesthetics.
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
    try {
      int buttonHeight = getHeight();
      int buttonWidth = getWidth();

      double buttonHeight25 = buttonHeight * 0.25;
      double buttonHeight75 = buttonHeight * 0.75;
      double buttonWidth25 = buttonWidth * 0.25;
      double buttonWidth75 = buttonWidth * 0.75;

      int[] pointsX;
      int[] pointsY;

      switch (getDirection()) {
        case SwingConstants.NORTH -> {
          pointsX = new int[] {
            buttonWidth / 2,
            (int) buttonWidth75,
            (int) buttonWidth25
          };

          pointsY = new int[] {
            (int) buttonHeight25,
            (int) buttonHeight75,
            (int) buttonHeight75
          };
        }

        case SwingConstants.SOUTH -> {
          pointsX = new int[] {
            buttonWidth / 2,
            (int) buttonWidth75,
            (int) buttonWidth25
          };

          pointsY = new int[] {
            (int) buttonHeight75,
            (int) buttonHeight25,
            (int) buttonHeight25
          };
        }

        case SwingConstants.EAST -> {
          pointsX = new int[] {
            (int) buttonWidth75,
            (int) buttonWidth25,
            (int) buttonWidth25
          };

          pointsY = new int[] {
            buttonHeight / 2,
            (int) buttonHeight75,
            (int) buttonHeight25
          };
        }

        case SwingConstants.WEST -> {
          pointsX = new int[] {
            (int) buttonWidth25,
            (int) buttonWidth75,
            (int) buttonWidth75
          };

          pointsY = new int[] {
            buttonHeight / 2,
            (int) buttonHeight25,
            (int) buttonHeight75
          };
        }

        default -> throw new IllegalArgumentException();
      }

      if (getModel().isPressed()) {
        g.setColor(Constants.COLOR_GREEN_MEDIUM);
      } else if (getModel().isRollover()) {
        g.setColor(Constants.COLOR_GREEN_DARK_MEDIUM);
      } else {
        g.setColor(isEnabled() ? getBackground() : Constants.COLOR_GREEN_MEDIUM);
      }

      Polygon triangle = new Polygon(pointsX, pointsY, 3);

      Graphics2D g2 = (Graphics2D) g.create();

      g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
      g2.setStroke(new BasicStroke(Constants.STROKE_BUTTON_ARROW, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
      g2.drawPolygon(triangle);
      g2.fillPolygon(triangle);
      g2.dispose();

      super.paintComponent(g);
    } catch (IllegalArgumentException e) {
      CommonFunctions.exitProgram(Error.ERROR_INTERNAL);
    }
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