package armameeldopartidesktop.utils.common.custom.graphical;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.border.EmptyBorder;

import armameeldopartidesktop.utils.common.Constants;

/**
 * A custom label that fits the overall program aesthetics.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CustomLabel extends JLabel implements CustomComponent {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a custom label that fits the overall program aesthetics.
   *
   * @param text        The label text.
   * @param tooltipText The label tooltip text
   * @param alignment   The label text alignment.
   */
  public CustomLabel(String text, String tooltipText, int alignment) {
    super(text);

    setHorizontalAlignment(alignment);
    setBackground(Constants.COLOR_GREEN_MEDIUM_LIGHT);
    setFont(new Font(getFont().getName(), Font.PLAIN, (int) Constants.SIZE_FONT_DEFAULT));
    setToolTipText(tooltipText);
    setUpGraphicalProperties();
  }

  /**
   * Builds a custom label that fits the overall program aesthetics.
   *
   * @param text            The label text.
   * @param tooltipText     The label tooltip text
   * @param backgroundColor The background color for the label.
   * @param foregroundColor The fireground color for the label.
   * @param alignment       The label text alignment.
   * @param fontSize        The font size for the label text.
   */
  public CustomLabel(String text, String tooltipText, Color backgroundColor, Color foregroundColor, int alignment, int fontSize) {
    super(text);

    setHorizontalAlignment(alignment);
    setBackground(backgroundColor);
    setForeground(foregroundColor);
    setFont(new Font(getFont().getName(), Font.PLAIN, fontSize));
    setToolTipText(tooltipText);
    setUpGraphicalProperties();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void setUpGraphicalProperties() {
    setOpaque(false);
    setBorder(new EmptyBorder(Constants.INSETS_LABEL));
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);

    Graphics2D graphics2d = (Graphics2D) graphics.create();

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.setColor(getBackground());
    graphics2d.fillRoundRect(0, 0, (getWidth() - 1), (getHeight() - 1), Constants.ROUNDED_BORDER_ARC_GENERAL, Constants.ROUNDED_BORDER_ARC_GENERAL);
    graphics2d.dispose();
  }

  @Override
  public JToolTip createToolTip() {
    return new CustomToolTip();
  }
}