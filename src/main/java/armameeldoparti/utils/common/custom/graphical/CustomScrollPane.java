package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Custom scroll pane class.
 *
 * <p>This class is used to instantiate a custom scroll pane that fits the overall program
 * aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomScrollPane extends JScrollPane {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds a basic scroll pane using the established program aesthetics.
   *
   * @param textArea Text area associated to the scroll pane that will be controlled.
   */
  public CustomScrollPane(JTextArea textArea) {
    super(
        textArea,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
    );
    setUpGraphicalProperties();
  }

  // ---------------------------------------- Protected methods ---------------------------------

  @Override
  protected void paintBorder(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g2.setColor(Constants.GREEN_LIGHT_WHITE);
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

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Configures the graphical properties of the scroll pane in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setOpaque(false);
    setBorder(
        BorderFactory.createEmptyBorder(
          Constants.ROUNDED_BORDER_INSETS_SCROLLPANE,
          Constants.ROUNDED_BORDER_INSETS_SCROLLPANE,
          Constants.ROUNDED_BORDER_INSETS_SCROLLPANE,
          Constants.ROUNDED_BORDER_INSETS_SCROLLPANE
        )
    );
    getViewport().setBackground(Constants.GREEN_LIGHT_WHITE);
    getVerticalScrollBar().setUI(new BasicScrollBarUI() {
      /**
       * Configures the scrollbar colors to fit the program aesthetics.
       *
       * <p>The thumb will be dark green and the track will be medium green.
       */
      @Override
      protected void configureScrollBarColors() {
        this.thumbColor = Constants.GREEN_DARK;
        this.trackColor = Constants.GREEN_MEDIUM;
      }

      /**
       * Configures the scrollbar decrease button to fit the program aesthetics.
       *
       * @see CommonFunctions#buildCustomArrowButton(int, Object)
       *
       * @param orientation The button arrow orientation.
       *
       * @return The scrollbar decrease button.
       */
      @Override
      protected JButton createDecreaseButton(int orientation) {
        return new CustomButton("");
      }

      /**
       * Configures the scrollbar increase button to fit the program aesthetics. It'll have the same
       * graphical properties as the scrollbar decrease button.
       *
       * @see #createDecreaseButton(int)
       *
       * @param orientation The button arrow orientation.
       *
       * @return The scrollbar increase button.
       */
      @Override
      protected JButton createIncreaseButton(int orientation) {
        return new CustomButton("");
      }

      @Override
      protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setColor(Constants.GREEN_DARK);
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

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setColor(Constants.GREEN_MEDIUM);
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