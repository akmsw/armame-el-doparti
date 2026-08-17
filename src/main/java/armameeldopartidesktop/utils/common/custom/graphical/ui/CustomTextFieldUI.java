package armameeldopartidesktop.utils.common.custom.graphical.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextFieldUI;

import armameeldopartidesktop.utils.common.Constants;

/**
 * A custom text field UI that fits the overall program aesthetics.
 *
 * @since 3.1.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CustomTextFieldUI extends BasicTextFieldUI {

  // ---------- Public static methods -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new custom text field UI that fits the overall program aesthetics.
   *
   * <p>The "java:S1172" warning is suppressed since the argument is intentionally unused.
   * <p>The "java:S9149" warning is suppressed since this method hides the parent implementation for createUI.
   *
   * @param component Component to which to apply the custom UI.
   *
   * @return A new custom text field UI.
   */
  @SuppressWarnings({"java:S1172", "java:S9149"})
  public static ComponentUI createUI(JComponent component) {
    return new CustomTextFieldUI();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void installUI(JComponent component) {
    super.installUI(component);

    JTextField textField = (JTextField) component;

    textField.setOpaque(false);
    textField.setBorder(new EmptyBorder(Constants.INSETS_GENERAL));
  }

  @Override
  public void paintSafely(Graphics graphics) {
    JTextField textField = (JTextField) getComponent();

    Graphics2D graphics2d = (Graphics2D) graphics.create();

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.setColor(textField.getBackground());
    graphics2d.fillRoundRect(0, 0, textField.getWidth() - 1, textField.getHeight() - 1, Constants.ROUNDED_BORDER_ARC_GENERAL, Constants.ROUNDED_BORDER_ARC_GENERAL);
    graphics2d.drawRoundRect(0, 0, textField.getWidth() - 1, textField.getHeight() - 1, Constants.ROUNDED_BORDER_ARC_GENERAL, Constants.ROUNDED_BORDER_ARC_GENERAL);
    graphics2d.dispose();

    super.paintSafely(graphics);
  }
}