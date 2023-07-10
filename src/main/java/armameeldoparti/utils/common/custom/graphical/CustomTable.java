package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Custom text area class.
 *
 * <p>This class is used to instantiate a custom table that fits the overall program
 * aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomTable extends JTable {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds a basic table using the established program aesthetics.
   *
   * @param rows    Row count for the table.
   * @param columns Column count for the table.
   */
  public CustomTable(int rows, int columns) {
    super(rows, columns);
    setupGraphicalProperties();
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Configures the graphical properties for the table in order to fit the program aesthetics.
   */
  private void setupGraphicalProperties() {
    setBorder(BorderFactory.createLineBorder(Constants.GREEN_DARK));
    setDefaultRenderer(
        Object.class,
        new DefaultTableCellRenderer() {
          /**
           * Configures the table cells background and foreground colors.
           *
           * @param myTable    Source table.
           * @param value      Table cell value.
           * @param isSelected If the cell is selected.
           * @param hasFocus   If the cell is focused.
           * @param row        Cell row number.
           * @param column     Cell column number.
           */
          @Override
          public Component getTableCellRendererComponent(JTable myTable, Object value,
                                                         boolean isSelected, boolean hasFocus,
                                                         int row, int column) {
            final Component c = super.getTableCellRendererComponent(myTable, value, isSelected,
                                                                    hasFocus, row, column);

            if (row == 0) {
              c.setBackground(Constants.GREEN_DARK);
              c.setForeground(Color.WHITE);

              ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);

              return c;
            }

            if (column == 0) {
              c.setBackground(Constants.GREEN_DARK);
              c.setForeground(Color.WHITE);

              ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.LEFT);

              return c;
            }

            c.setBackground(Color.WHITE);
            c.setForeground(Color.BLACK);

            ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.LEFT);

            return c;
          }
        });
  }
}