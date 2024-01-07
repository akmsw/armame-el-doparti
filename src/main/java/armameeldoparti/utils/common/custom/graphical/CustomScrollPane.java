package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Custom scroll pane class.
 *
 * <p>This class is used to instantiate a custom scroll pane that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomScrollPane extends JScrollPane {

  // --------------------------------------------------------------- Constructor ---------------------------------------------------------------------

  /**
   * Builds a basic scroll pane using the established program aesthetics.
   *
   * @param textArea Text area associated to the scroll pane that will be controlled.
   */
  public CustomScrollPane(JTextArea textArea) {
    super(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    setUpGraphicalProperties();
  }

  // --------------------------------------------------------------- Protected methods ---------------------------------------------------------------

  @Override
  protected void paintBorder(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    g2.setColor(Constants.COLOR_GREEN_LIGHT_WHITE);
    g2.fillRoundRect(
      0,
      0,
      (getWidth() - 1),
      (getHeight() - 1),
      Constants.ROUNDED_BORDER_ARC_GENERAL,
      Constants.ROUNDED_BORDER_ARC_GENERAL
    );
    g2.dispose();
  }

  // ---------------------------------------------------------------- Private methods ----------------------------------------------------------------

  /**
   * Configures the graphical properties of the scroll pane in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setOpaque(false);
    setBorder(new EmptyBorder(Constants.INSETS_GENERAL));
    getViewport().setBackground(Constants.COLOR_GREEN_LIGHT_WHITE);
    getVerticalScrollBar().setUI(new BasicScrollBarUI() {
      @Override
      protected void configureScrollBarColors() {
        this.thumbColor = Constants.COLOR_GREEN_DARK;
        this.trackColor = Constants.COLOR_GREEN_MEDIUM;
      }

      @Override
      protected JButton createDecreaseButton(int orientation) {
        return new CustomArrowButton(orientation);
      }

      @Override
      protected JButton createIncreaseButton(int orientation) {
        return new CustomArrowButton(orientation);
      }

      @Override
      protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
        g2.setColor(Constants.COLOR_GREEN_DARK);
        g2.fillRoundRect(
          thumbBounds.x,
          thumbBounds.y,
          thumbBounds.width,
          thumbBounds.height,
          Constants.ROUNDED_BORDER_ARC_SCROLLBAR,
          Constants.ROUNDED_BORDER_ARC_SCROLLBAR
        );
        g2.dispose();
      }

      @Override
      protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
        g2.setColor(Constants.COLOR_GREEN_MEDIUM);
        g2.fillRoundRect(
          trackBounds.x,
          trackBounds.y,
          trackBounds.width,
          trackBounds.height,
          Constants.ROUNDED_BORDER_ARC_SCROLLBAR,
          Constants.ROUNDED_BORDER_ARC_SCROLLBAR
        );
        g2.dispose();
      }
    });
    getVerticalScrollBar().setOpaque(false);
  }
}