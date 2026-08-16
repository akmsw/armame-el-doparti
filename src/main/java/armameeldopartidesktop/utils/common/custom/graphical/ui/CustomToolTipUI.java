package armameeldopartidesktop.utils.common.custom.graphical.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolTipUI;

import armameeldopartidesktop.utils.common.Constants;

/**
 * A custom tooltip UI that fits the overall program aesthetics.
 *
 * @since 3.1.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CustomToolTipUI extends BasicToolTipUI {

  // ---------- Public static methods -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new custom tooltip UI that fits the overall program aesthetics.
   *
   * <p>The "java:S1172" warning is suppressed since the argument is intentionally unused.
   *
   * @param component Component to which to apply the custom UI.
   *
   * @return A new custom tooltip UI.
   */
  @SuppressWarnings("java:S1172")
  public static ComponentUI createUI(JComponent component) {
    return new CustomToolTipUI();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void installUI(JComponent component) {
    super.installUI(component);

    component.setOpaque(false);
    component.setForeground(Color.WHITE);
    component.setBorder(new EmptyBorder(Constants.INSETS_TOOLTIP));
  }

  @Override
  public void paint(Graphics graphics, JComponent component) {
    String text = ((JToolTip) component).getTipText();

    if ((text != null) && !text.isBlank()) {
      Graphics2D graphics2d = (Graphics2D) graphics.create();

      graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
      graphics2d.setColor(Constants.COLOR_GREEN_DARK_MEDIUM);
      graphics2d.fillRoundRect(0, 0, component.getWidth(), component.getHeight(), Constants.ROUNDED_BORDER_ARC_TOOLTIP, Constants.ROUNDED_BORDER_ARC_TOOLTIP);
      graphics2d.setFont(component.getFont());

      FontMetrics fontMetrics = graphics2d.getFontMetrics();

      int textX = (component.getWidth()   - fontMetrics.stringWidth(text)) / 2;
      int textY = ((component.getHeight() - fontMetrics.getHeight()      ) / 2) + fontMetrics.getMaxAscent();

      graphics2d.setColor(component.getForeground());
      graphics2d.drawString(text, textX, textY);
      graphics2d.dispose();
    }
  }

  @Override
  public Dimension getPreferredSize(JComponent component) {
    JToolTip toolTip = (JToolTip) component;

    String toolTipText = toolTip.getTipText();

    if ((toolTipText == null) || toolTipText.isBlank()) {
      return super.getPreferredSize(component);
    }

    Insets insets = component.getInsets();

    int textWidth = component.getFontMetrics(component.getFont())
                             .stringWidth(toolTipText);

    int textHeight = component.getFontMetrics(component.getFont())
                              .getHeight();

    return new Dimension(
      textWidth  + insets.left + insets.right,
      textHeight + insets.top  + insets.bottom
    );
  }
}