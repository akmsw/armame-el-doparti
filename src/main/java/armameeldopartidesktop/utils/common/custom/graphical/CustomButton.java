package armameeldopartidesktop.utils.common.custom.graphical;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JButton;

import armameeldopartidesktop.utils.common.Constants;

/**
 * A custom button that fits the overall program aesthetics.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class CustomButton extends JButton {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private int arc;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic rounded button using the established program aesthetics.
   *
   * @param text The text to display on the button.
   */
  public CustomButton(String text) {
    super(text);
    setArc(Constants.ROUNDED_BORDER_ARC_GENERAL);
    setUpGraphicalProperties();
  }

  /**
   * Builds a basic rounded button using the established program aesthetics.
   *
   * @param text The text to display on the button.
   * @param arc  The round borders arc.
   */
  public CustomButton(String text, int arc) {
    super(text);
    setArc(arc);
    setUpGraphicalProperties();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public Insets getInsets() {
    return Constants.INSETS_GENERAL;
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void paintComponent(Graphics graphics) {
    if (getModel().isPressed()) {
      graphics.setColor(Constants.COLOR_GREEN_MEDIUM);
    } else if (getModel().isRollover()) {
      graphics.setColor(Constants.COLOR_GREEN_DARK_MEDIUM);
    } else {
      graphics.setColor(isEnabled() ? getBackground() : Constants.COLOR_GREEN_MEDIUM);
    }

    Graphics2D graphics2d = (Graphics2D) graphics.create();

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.fillRoundRect(0, 0, (getWidth() - 1), (getHeight() - 1), arc, arc);
    graphics2d.dispose();

    super.paintComponent(graphics);
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Configures the graphical properties of the button in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setBackground(Constants.COLOR_GREEN_DARK);
    setForeground(Color.WHITE);
    setContentAreaFilled(false);
    setFocusPainted(false);
    setBorderPainted(false);
  }

  // ---------- Public getters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public int getArc() {
    return arc;
  }

  // ---------- Private setters ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private void setArc(int arc) {
    this.arc = arc;
  }
}