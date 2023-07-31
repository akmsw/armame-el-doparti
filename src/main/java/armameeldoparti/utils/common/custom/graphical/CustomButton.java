package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.models.Error;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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
@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class CustomButton extends JButton {

  private boolean isArrowButton;

  private int arc;
  private int orientation;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds a basic button using the established program aesthetics.
   *
   * @param text The text to display on the button.
   * @param arc  The round borders arc.
   */
  public CustomButton(String text, int arc) {
    super(text);
    setArc(arc);
    setArrowButton(false);
    setOpaque(false);
    setFocusPainted(false);
    setContentAreaFilled(false);
    setBorderPainted(false);
  }

  /**
   * A.
   *
   * @param orientation B.
   */
  public CustomButton(int orientation) {
    if (orientation != SwingConstants.NORTH && orientation != SwingConstants.SOUTH) {
      CommonFunctions.exitProgram(Error.FATAL_INTERNAL_ERROR);
    }

    setArrowButton(true);
    setOrientation(orientation);
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

    if (isArrowButton()) {
      int[] pointsX = {
        getWidth() / 2,
        (int) (getWidth() * 0.75),
        (int) (getWidth() * 0.25)
      };

      int[] pointsY = (
        (orientation == SwingConstants.NORTH) ? new int[] {
          (int) (getHeight() * 0.25),
          (int) (getHeight() * 0.75),
          (int) (getHeight() * 0.75)
        }
        : new int[] {
          (int) (getHeight() * 0.75),
          (int) (getHeight() * 0.25),
          (int) (getHeight() * 0.25)
        }
      );

      Polygon triangle = new Polygon(pointsX, pointsY, 3);

      g2.setStroke(
        new BasicStroke(
          Constants.STROKE_BUTTON_ARROW,
          BasicStroke.CAP_ROUND,
          BasicStroke.JOIN_ROUND
        )
      );
      g2.drawPolygon(triangle);
      g2.fillPolygon(triangle);
    } else {
      g2.fillRoundRect(0, 0, (getWidth() - 1), (getHeight() - 1), arc, arc);
    }

    g2.dispose();

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