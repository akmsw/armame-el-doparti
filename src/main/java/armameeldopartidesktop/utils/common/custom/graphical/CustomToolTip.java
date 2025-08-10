package armameeldopartidesktop.utils.common.custom.graphical;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.IllegalComponentStateException;
import java.awt.Insets;
import java.awt.Window;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import armameeldopartidesktop.models.enums.Error;
import armameeldopartidesktop.utils.common.CommonFields;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;

/**
 * A custom tooltip that fits the overall program aesthetics.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class CustomToolTip extends JToolTip {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic tooltip using the established program aesthetics.
   *
   * @param component The component that will contain the tooltip.
   */
  public CustomToolTip(JComponent component) {
    // Initialize the tooltip round rectangle to avoid showing white background the first time the tooltip is shown due to null initial value
    if (CommonFields.getTooltipRectangle() == null) {
      FontMetrics fontMetrics = getFontMetrics(getFont());

      String tooltipText = component.getToolTipText();

      float roundRectangleInitialWidth = (fontMetrics.stringWidth(tooltipText) + Constants.INSETS_TOOLTIP.left + Constants.INSETS_TOOLTIP.right);
      float roundRectangleInitialHeight = (fontMetrics.getHeight() + Constants.INSETS_TOOLTIP.top + Constants.INSETS_TOOLTIP.bottom);

      CommonFields.setTooltipRectangle(new RoundRectangle2D.Float(0, 0, roundRectangleInitialWidth, roundRectangleInitialHeight, Constants.ROUNDED_BORDER_ARC_TOOLTIP, Constants.ROUNDED_BORDER_ARC_TOOLTIP));
    }

    setComponent(component);
    setUpGraphicalProperties();
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Configures the graphical properties for the tooltip in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setOpaque(false);
    setBorder(new EmptyBorder(Constants.INSETS_TOOLTIP));
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * This method is overridden in order to maintain the tooltip graphical properties outside of the frame boundaries.
   */
  @Override
  public void addNotify() {
    super.addNotify();

    Window window = SwingUtilities.windowForComponent(this);

    if (window != null && !(window instanceof JFrame)) {
      Component parent = getParent();

      if (parent instanceof JComponent parentComponent) {
        parentComponent.setOpaque(false);
      }

      try {
        window.setShape(CommonFields.getTooltipRectangle());
      } catch (IllegalComponentStateException | UnsupportedOperationException exception) {
        CommonFunctions.exitProgram(Error.ERROR_INTERNAL, exception);
      }
    }
  }

  @Override
  public void paintComponent(Graphics graphics) {
    Graphics2D graphics2d = (Graphics2D) graphics.create();

    FontMetrics fontMetric = graphics2d.getFontMetrics();

    String tooltipText = getTipText();

    CommonFields.setTooltipRectangle(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), Constants.ROUNDED_BORDER_ARC_TOOLTIP, Constants.ROUNDED_BORDER_ARC_TOOLTIP));

    // Transparent background painting
    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.setComposite(AlphaComposite.Clear);
    graphics2d.setColor(Constants.COLOR_TRANSPARENT);
    graphics2d.fillRect(0, 0, getWidth(), getHeight());

    // Rounded rectangle background painting
    graphics2d.setComposite(AlphaComposite.SrcOver);
    graphics2d.setColor(Constants.COLOR_GREEN_DARK_MEDIUM);
    graphics2d.fill(CommonFields.getTooltipRectangle());

    // Text painting
    graphics2d.setColor(Color.WHITE);
    graphics2d.drawString(tooltipText, ((getWidth() - fontMetric.stringWidth(tooltipText)) / 2), (((getHeight() - fontMetric.getHeight()) / 2) + fontMetric.getAscent()));
    graphics2d.dispose();
  }

  @Override
  public Insets getInsets() {
    return Constants.INSETS_TOOLTIP;
  }

  @Override
  public Dimension getPreferredSize() {
    FontMetrics fontMetric = getFontMetrics(getFont());

    Insets insets = getInsets();

    return new Dimension((fontMetric.stringWidth(getTipText()) + insets.left + insets.right), (fontMetric.getHeight() + insets.top + insets.bottom));
  }
}