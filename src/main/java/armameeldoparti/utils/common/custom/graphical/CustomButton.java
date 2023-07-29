package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.models.Error;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
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
      int arcAngle = -180;

      if (getOrientation() == SwingConstants.SOUTH) {
        arcAngle *= -1;
      }

      g2.fillArc(0, 0, (getWidth() - 1), (getHeight() - 1), (-arcAngle), arcAngle);
    } else {
      g2.fillRoundRect(0, 0, (getWidth() - 1), (getHeight() - 1), arc, arc);
    }

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