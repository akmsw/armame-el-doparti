package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JTextArea;

/**
 * Custom text area class.
 *
 * <p>This class is used to instantiate a custom text area that fits the overall program
 * aesthetics. It is supposed to be used along with a CustomScrollPane if a scrollbar is needed.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomTextArea extends JTextArea {

  // ---------------------------------------- Constructors --------------------------------------

  /**
   * Builds a basic text area using the established program aesthetics.
   *
   * @param rows       Row count for the text area.
   * @param columns    Column count for the text area.
   * @param scrollable If the text area could handle a text long enough to have a scrollbar.
   */
  public CustomTextArea(int rows, int columns, boolean scrollable) {
    super(rows, columns);
    setUpGraphicalProperties(scrollable);
  }

  /**
   * Builds a basic text area using the established program aesthetics.
   *
   * @param scrollable If the text area could handle a text long enough to have a scrollbar.
   */
  public CustomTextArea(boolean scrollable) {
    super();
    setUpGraphicalProperties(scrollable);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Configures the graphical properties for the text area in order to fit the program aesthetics.
   *
   * @param scrollable If the text area could handle a text long enough to have a scrollbar. If so,
   *                   no border is set for the text area (the border should be the one provided by
   *                   the CustomScrollPane that boxes this CustomTextArea). If not, a bevel border
   *                   is set.
   */
  private void setUpGraphicalProperties(boolean scrollable) {
    setEditable(false);
    setOpaque(false);
    // if (!scrollable) {
    //   setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
    //                                             Constants.GREEN_MEDIUM,
    //                                             Constants.GREEN_MEDIUM));
    // }
  }

  @Override
  public Insets getInsets() {
    return new Insets(
      Constants.ROUNDED_BORDER_INSETS,
      Constants.ROUNDED_BORDER_INSETS,
      Constants.ROUNDED_BORDER_INSETS,
      Constants.ROUNDED_BORDER_INSETS
    );
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g2.setColor(getBackground());
    g2.fillRoundRect(
        0,
        0,
        (getWidth() - 1),
        (getHeight() - 1),
        Constants.ROUNDED_BORDER_ARC,
        Constants.ROUNDED_BORDER_ARC
    );

    super.paintComponent(g);

    g2.dispose();
  }

  @Override
  protected void paintBorder(Graphics g) {
    g.setColor(Constants.GREEN_LIGHT);

    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g2.drawRoundRect(
        0,
        0,
        (getWidth() - 1),
        (getHeight() - 1),
        Constants.ROUNDED_BORDER_ARC,
        Constants.ROUNDED_BORDER_ARC
    );
    g2.dispose();
  }
}