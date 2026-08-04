package armameeldopartidesktop.utils.common.custom.graphical;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;

import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

import armameeldopartidesktop.models.enums.Error;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;

/**
 * A custom arrow button that fits the overall program aesthetics.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class CustomArrowButton extends BasicArrowButton implements CustomComponent {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a custom arrow button that fits the overall program aesthetics.
   *
   * @param orientation The arrow button orientation.
   */
  public CustomArrowButton(int orientation) {
    super(orientation);

    setUpGraphicalProperties();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void setUpGraphicalProperties() {
    setContentAreaFilled(false);
    setFocusPainted(false);
    setBorderPainted(false);
    setBackground(Constants.COLOR_GREEN_DARK);
  }

  @Override
  public void paint(Graphics graphics) {
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
        graphics.setColor(Constants.COLOR_GREEN_MEDIUM_LIGHT);
      } else if (getModel().isRollover()) {
        graphics.setColor(Constants.COLOR_GREEN_DARK_MEDIUM);
      } else {
        graphics.setColor(isEnabled() ? getBackground() : Constants.COLOR_GREEN_MEDIUM);
      }

      Polygon triangle = new Polygon(pointsX, pointsY, 3);

      Graphics2D graphics2d = (Graphics2D) graphics.create();

      graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
      graphics2d.setStroke(new BasicStroke(Constants.STROKE_BUTTON_ARROW, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
      graphics2d.drawPolygon(triangle);
      graphics2d.fillPolygon(triangle);
      graphics2d.dispose();
    } catch (IllegalArgumentException exception) {
      CommonFunctions.exitProgram(Error.ERROR_INTERNAL, exception);
    }
  }

  @Override
  public Insets getInsets() {
    return Constants.INSETS_GENERAL;
  }
}