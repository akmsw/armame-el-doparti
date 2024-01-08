package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Custom table class.
 *
 * <p>This class is used to instantiate a custom table that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomTable extends JTable {

  // --------------------------------------------------------------- Constructor ---------------------------------------------------------------------

  /**
   * Builds a basic table using the established program aesthetics.
   *
   * @param rows    Row count for the table.
   * @param columns Column count for the table.
   */
  public CustomTable(int rows, int columns) {
    super(rows, columns);
    setUpGraphicalProperties();
  }

  // ---------------------------------------------------------------- Public methods -----------------------------------------------------------------

  /**
   * Adjusts the cells size to fit the biggest content shown in the table.
   */
  public void adjustCells() {
    int maxCellWidth = 0;
    int maxCellHeight = 0;

    for (int row = 0; row < getRowCount(); row++) {
      for (int column = 0; column < getColumnCount(); column++) {
        Component cellComponent = prepareRenderer(getCellRenderer(row, column), row, column);

        maxCellWidth = Math.max(maxCellWidth, cellComponent.getPreferredSize().width + getIntercellSpacing().width);
        maxCellHeight = Math.max(maxCellHeight, cellComponent.getPreferredSize().height + getIntercellSpacing().height);
      }
    }

    for (int row = 0; row < getRowCount(); row++) {
      setRowHeight(row, maxCellHeight);
    }

    for (int column = 0; column < getColumnCount(); column++) {
      getColumnModel().getColumn(column)
                      .setPreferredWidth(maxCellWidth);
    }
  }

  // ---------------------------------------------------------------- Private methods ----------------------------------------------------------------

  /**
   * Configures the graphical properties for the table in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setOpaque(false);
    setGridColor(Constants.COLOR_GREEN_LIGHT);
    setDefaultRenderer(
        Object.class,
        new DefaultTableCellRenderer() {
          @Override
          public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (comp instanceof JComponent) {
              JComponent auxComp = (JComponent) comp;

              auxComp.setOpaque(false);
              auxComp.setBorder(new EmptyBorder(Constants.INSETS_GENERAL));

              if (row == 0) {
                auxComp.setBackground(Constants.COLOR_GREEN_DARK);
                auxComp.setForeground(Color.WHITE);

                ((DefaultTableCellRenderer) auxComp).setHorizontalAlignment(SwingConstants.CENTER);

                return auxComp;
              }

              if (column == 0) {
                auxComp.setBackground(Constants.COLOR_GREEN_DARK);
                auxComp.setForeground(Color.WHITE);

                ((DefaultTableCellRenderer) auxComp).setHorizontalAlignment(SwingConstants.LEFT);

                return auxComp;
              }

              auxComp.setBackground(Constants.COLOR_GREEN_LIGHT_WHITE);
              auxComp.setForeground(Color.BLACK);
            }

            return comp;
          }

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
              Constants.ROUNDED_BORDER_ARC_TABLE_CELLS,
              Constants.ROUNDED_BORDER_ARC_TABLE_CELLS
            );

            super.paintComponent(g2);

            g2.dispose();
          }
      }
    );
  }
}