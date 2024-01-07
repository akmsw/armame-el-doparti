package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.JButton;

/**
 * Custom button class.
 *
 * <p>This class is used to instantiate a custom button that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomButton extends JButton {

  // ---------------------------------------------------------------- Private fields -----------------------------------------------------------------

  private int arc;

  // --------------------------------------------------------------- Constructor ---------------------------------------------------------------------

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

  // ---------------------------------------------------------------- Public methods -----------------------------------------------------------------

  @Override
  public Insets getInsets() {
    return Constants.INSETS_GENERAL;
  }

  // --------------------------------------------------------------- Protected methods ---------------------------------------------------------------

  @Override
  protected void paintComponent(Graphics g) {
    if (getModel().isPressed()) {
      g.setColor(Constants.COLOR_GREEN_MEDIUM);
    } else if (getModel().isRollover()) {
      g.setColor(Constants.COLOR_GREEN_DARK_MEDIUM);
    } else {
      g.setColor(isEnabled() ? getBackground() : Constants.COLOR_GREEN_MEDIUM);
    }

    Graphics2D g2 = (Graphics2D) g.create();

    int buttonHeight = getHeight();
    int buttonWidth = getWidth();

    g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    g2.fillRoundRect(0, 0, (buttonWidth - 1), (buttonHeight - 1), arc, arc);
    g2.dispose();

    super.paintComponent(g);
  }

  // ---------------------------------------------------------------- Private methods ----------------------------------------------------------------

  /**
   * Configures the graphical properties of the button in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setOpaque(false);
    setFocusPainted(false);
    setContentAreaFilled(false);
    setBorderPainted(false);
  }

  // -------------------------------------------------------------------- Getters --------------------------------------------------------------------

  public int getArc() {
    return arc;
  }

  // -------------------------------------------------------------------- Setters --------------------------------------------------------------------

  private void setArc(int arc) {
    this.arc = arc;
  }
}