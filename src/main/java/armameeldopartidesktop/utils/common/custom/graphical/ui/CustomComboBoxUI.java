package armameeldopartidesktop.utils.common.custom.graphical.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.utils.common.custom.graphical.CustomArrowButton;

/**
 * A custom combo box UI that fits the overall program aesthetics.
 *
 * @since 3.1.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CustomComboBoxUI extends BasicComboBoxUI {

  // ---------- Public static methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new custom combo box UI that fits the overall program aesthetics.
   *
   * <p>The "java:S1172" warning is suppressed since the argument is intentionally unused.
   *
   * @param component Component to which to apply the custom UI.
   *
   * @return A new custom combo box UI.
   */
  @SuppressWarnings("java:S1172")
  public static ComponentUI createUI(JComponent component) {
    return new CustomComboBoxUI();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void installUI(JComponent component) {
    super.installUI(component);

    JComboBox<?> comboBox = (JComboBox<?>) component;

    comboBox.setOpaque(false);
    comboBox.setBorder(new EmptyBorder(Constants.INSETS_COMBOBOX));
  }

  @Override
  public void paint(Graphics graphics, JComponent component) {
    Graphics2D graphics2d = (Graphics2D) graphics.create();

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.setColor(component.getBackground());
    graphics2d.fillRoundRect(0, 0, component.getWidth() - 1, component.getHeight() - 1, Constants.ROUNDED_BORDER_ARC_GENERAL, Constants.ROUNDED_BORDER_ARC_GENERAL);
    graphics2d.drawRoundRect(0, 0, component.getWidth() - 1, component.getHeight() - 1, Constants.ROUNDED_BORDER_ARC_GENERAL, Constants.ROUNDED_BORDER_ARC_GENERAL);
    graphics2d.dispose();

    super.paint(graphics, component);
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected JButton createArrowButton() {
    return new CustomArrowButton(SwingConstants.SOUTH);
  }

  @Override
  protected ListCellRenderer<Object> createRenderer() {
    return new CustomListCellRenderer();
  }

  @Override
  protected ComboPopup createPopup() {
    return new BasicComboPopup(comboBox) {

      @Override
      public Insets getInsets() {
        return Constants.INSETS_COMBOBOX;
      }

      @Override
      protected void paintBorder(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics.create();

        graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
        graphics2d.setColor(Constants.COLOR_GREEN_MEDIUM);
        graphics2d.drawRoundRect(0, 0, (getWidth() - 1), (getHeight() - 1), Constants.ROUNDED_BORDER_ARC_GENERAL, Constants.ROUNDED_BORDER_ARC_GENERAL);
        graphics2d.dispose();
      }

      @Override
      protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics.create();

        graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
        graphics2d.setColor(Constants.COLOR_GREEN_MEDIUM);
        graphics2d.fillRoundRect(0, 0, getWidth(), (getHeight() - 1), Constants.ROUNDED_BORDER_ARC_GENERAL, Constants.ROUNDED_BORDER_ARC_GENERAL);
        graphics2d.dispose();

        super.paintComponent(graphics);
      }
    };
  }

  // ---------- Private classes ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private static final class CustomListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
      JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

      renderer.setOpaque(false);
      renderer.setBackground(isSelected ? Constants.COLOR_GREEN_DARK_MEDIUM : list.getBackground());
      renderer.setForeground(list.getForeground());
      renderer.setBorder(new EmptyBorder(Constants.INSETS_COMBOBOX));

      return renderer;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
      Graphics2D graphics2d = (Graphics2D) graphics.create();

      graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
      graphics2d.setColor(getBackground());
      graphics2d.fillRoundRect(0, 0, getWidth(), (getHeight() - 1), Constants.ROUNDED_BORDER_ARC_COMBOBOX_SELECTOR, Constants.ROUNDED_BORDER_ARC_COMBOBOX_SELECTOR);
      graphics2d.dispose();

      super.paintComponent(graphics);
    }
  }
}