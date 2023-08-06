package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 * Custom text area class.
 *
 * <p>This class is used to instantiate a custom combobox that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomComboBox<E> extends JComboBox<E> {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds a basic combobox using the established program aesthetics.
   *
   * @param items Items array used in the combobox.
   */
  public CustomComboBox(E[] items) {
    super(items);
    setUpGraphicalProperties();
  }

  // ---------------------------------------- Public methods ------------------------------------

  @Override
  public Insets getInsets() {
    return Constants.INSETS_GENERAL;
  }

  // ---------------------------------------- Protected methods ---------------------------------

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    g2.setColor(getBackground());
    g2.fillRoundRect(
        0,
        0,
        (getWidth() - 1),
        (getHeight() - 1),
        Constants.ROUNDED_BORDER_ARC_GENERAL,
        Constants.ROUNDED_BORDER_ARC_GENERAL
    );

    super.paintComponent(g);

    g2.dispose();
  }

  @Override
  protected void paintBorder(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    g2.setColor(getBackground());
    g2.drawRoundRect(
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
   * Configures the graphical properties for the combobox in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setOpaque(false);
    setUI(new BasicComboBoxUI() {
      @Override
      protected JButton createArrowButton() {
        return new CustomArrowButton(SwingConstants.SOUTH);
      }

      @Override
      protected ListCellRenderer<Object> createRenderer() {
        return new DefaultListCellRenderer() {
          @Override
          public Component getListCellRendererComponent(JList<?> list,
                                                        Object value,
                                                        int index,
                                                        boolean isSelected,
                                                        boolean cellHasFocus) {
            JLabel renderer = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus
            );

            if (isSelected) {
              renderer.setBackground(Constants.COLOR_GREEN_DARK_MEDIUM);
              renderer.setForeground(Color.WHITE);
            } else {
              renderer.setBackground(list.getBackground());
              renderer.setForeground(list.getForeground());
            }

            return renderer;
          }
        };
      }
    });
  }
}