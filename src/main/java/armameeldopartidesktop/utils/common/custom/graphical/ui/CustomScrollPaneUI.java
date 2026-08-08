package armameeldopartidesktop.utils.common.custom.graphical.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollPaneUI;

import armameeldopartidesktop.utils.common.Constants;

/**
 * A custom scroll pane UI that fits the overall program aesthetics.
 *
 * @since 3.1.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CustomScrollPaneUI extends BasicScrollPaneUI {

  /**
   * Creates a new custom scroll pane UI that fits the overall program aesthetics.
   *
   * <p>The "java:S1172" warning is suppressed since the argument is intentionally unused.
   *
   * @param component Component to which to apply the custom UI.
   *
   * @return A new custom scroll pane UI.
   */
  @SuppressWarnings("java:S1172")
  public static ComponentUI createUI(JComponent component) {
    return new CustomScrollPaneUI();
  }

  @Override
  public void installUI(JComponent component) {
    super.installUI(component);

    JScrollPane scrollPane = (JScrollPane) component;

    scrollPane.setOpaque(false);

    scrollPane.setBorder(new EmptyBorder(Constants.INSETS_GENERAL));

    scrollPane.getViewport()
              .setBackground(Constants.COLOR_GREEN_LIGHT_WHITE);

    scrollPane.getVerticalScrollBar()
              .setOpaque(false);
  }

  @Override
  public void paint(Graphics graphics, JComponent component) {
    Graphics2D graphics2d = (Graphics2D) graphics.create();

    try {
      graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
      graphics2d.setColor(Constants.COLOR_GREEN_LIGHT_WHITE);

      graphics2d.fillRoundRect(0, 0, (component.getWidth() - 1), (component.getHeight() - 1), Constants.ROUNDED_BORDER_ARC_GENERAL, Constants.ROUNDED_BORDER_ARC_GENERAL);

      super.paint(graphics2d, component);
    } finally {
      graphics2d.dispose();
    }
  }
}