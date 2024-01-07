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
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

/**
 * Custom combobox class.
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

  // --------------------------------------------------------------- Constructor ---------------------------------------------------------------------

  /**
   * Builds a basic combobox using the established program aesthetics.
   *
   * @param items Items array used in the combobox.
   */
  public CustomComboBox(E[] items) {
    super(items);
    setUpGraphicalProperties();
  }

  // ---------------------------------------------------------------- Public methods -----------------------------------------------------------------

  @Override
  public Insets getInsets() {
    return Constants.INSETS_COMBOBOX;
  }

  // --------------------------------------------------------------- Protected methods ---------------------------------------------------------------

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
    g2.dispose();

    super.paintComponent(g);
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

  // ---------------------------------------------------------------- Private methods ----------------------------------------------------------------

  /**
   * Configures the graphical properties for the combobox in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setOpaque(false);
    setUI(
      new BasicComboBoxUI() {
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
            protected void configureList() {
              super.configureList();
              list.setCellRenderer(new CustomListCellRenderer());
            }

            @Override
            public Insets getInsets() {
              return Constants.INSETS_COMBOBOX;
            }

            @Override
            protected void paintBorder(Graphics g) {
              Graphics2D g2 = (Graphics2D) g.create();

              g2.setColor(Constants.COLOR_GREEN_MEDIUM);
              g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
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

            @Override
            protected void paintComponent(Graphics g) {
              Graphics2D g2 = (Graphics2D) g.create();

              g2.setColor(Constants.COLOR_GREEN_MEDIUM);
              g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
              g2.fillRoundRect(
                0,
                0,
                (getWidth() - 1),
                (getHeight() - 1),
                Constants.ROUNDED_BORDER_ARC_GENERAL,
                Constants.ROUNDED_BORDER_ARC_GENERAL
              );
              g2.dispose();

              super.paintComponent(g);
            }
          };
        }
      }
    );
  }

  private static class CustomListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
      JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

      Color fillColor = isSelected ? Constants.COLOR_GREEN_DARK_MEDIUM : list.getBackground();

      renderer.setOpaque(false);
      renderer.setBackground(fillColor);
      renderer.setForeground(list.getForeground());
      renderer.setBorder(new EmptyBorder(Constants.INSETS_COMBOBOX));

      return renderer;
    }

    @Override
    public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g.create();

      g2.setColor(getBackground());
      g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
      g2.fillRoundRect(
        0,
        0,
        (getWidth() - 1),
        (getHeight() - 1),
        Constants.ROUNDED_BORDER_ARC_COMBOBOX,
        Constants.ROUNDED_BORDER_ARC_COMBOBOX
      );
      g2.dispose();

      super.paintComponent(g);
    }
  }
}