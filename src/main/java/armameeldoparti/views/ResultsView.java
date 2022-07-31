package armameeldoparti.views;

import armameeldoparti.abstractclasses.View;
import armameeldoparti.utils.Main;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import net.miginfocom.swing.MigLayout;

/**
 * Results view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 06/03/2021
 */
public class ResultsView extends View {

  /**
   * Fixed table cells width (in pixels).
   * This value depends on the program's font and the maximum player name length.
   */
  private static final int FIXED_CELL_WIDTH = 250;

  // ---------------------------------------- Private fields ------------------------------------

  private String frameTitle;

  private JPanel panel;

  private JTable table;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye una ventana de resultados.
   */
  public ResultsView() {

    if (Main.getDistribution() == Main.RANDOM_MIX) {
      setFrameTitle("Aleatorio - ");
    } else {
      setFrameTitle("Por puntuaciones - ");
    }

    setFrameTitle(getFrameTitle().concat(Main.thereAreAnchorages()
                                         ? "Con anclajes"
                                         : "Sin anclajes"));
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Initializes the view and makes it visible.
   */
  @Override
  public void initializeInterface() {
    panel = new JPanel(new MigLayout("wrap"));

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(Main.ICON.getImage());
    setTitle(getFrameTitle());
    setResizable(false);
    addTable();
    addButtons();
    add(panel);
  }

  /**
   * Sets the ideal table cells size.
   */
  public void setTableCellsSize() {
    for (int column = 0; column < table.getColumnCount(); column++) {
      table.getColumnModel()
           .getColumn(column)
           .setPreferredWidth(FIXED_CELL_WIDTH);
    }

    for (int i = 0; i < table.getRowCount(); i++) {
      int rowHeight = table.getRowHeight();

      for (int j = 0; j < table.getColumnCount(); j++) {
        Component component = table.prepareRenderer(table.getCellRenderer(i, j), i, j);
        rowHeight = Math.max(rowHeight, component.getPreferredSize()
                                                 .height);
      }

      table.setRowHeight(i, rowHeight);
    }

    pack();
    setLocationRelativeTo(null);
  }

  // --------------------------------------------- Getters --------------------------------------

  /**
   * Gets the frame title.
   *
   * @return The frame title.
   */
  public String getFrameTitle() {
    return frameTitle;
  }

  /**
   * Gets the results table.
   *
   * @return The results table.
   */
  public JTable getTable() {
    return table;
  }

  // --------------------------------------------- Setters --------------------------------------

  /**
   * Updates the results table.
   *
   * @param table The new results table.
   */
  public void setTable(JTable table) {
    this.table = table;
  }

  /**
   * Updates the frame's title.
   *
   * @param table The frame's new title.
   */
  public void setFrameTitle(String frameTitle) {
    this.frameTitle = frameTitle;
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Adds the buttons to their corresponding panel.
   */
  @Override
  protected void addButtons() {
    JButton backButton = new JButton("Atrás");

    backButton.addActionListener(e ->
        Main.getResultsController()
            .backButtonEvent()
    );

    if (Main.getDistribution() == Main.RANDOM_MIX) {
      JButton remixButton = new JButton("Redistribuir");

      remixButton.addActionListener(e ->
          Main.getResultsController()
              .remixButtonEvent()
      );

      panel.add(remixButton, "growx");
    }

    panel.add(backButton, "growx");
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the results table in the view panel.
   */
  private void addTable() {
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.setCellSelectionEnabled(false);
    table.setRowSelectionAllowed(false);
    table.setColumnSelectionAllowed(false);
    table.setBorder(BorderFactory.createLineBorder(Main.DARK_GREEN));
    table.setEnabled(false);

    panel.add(table, "push, grow, span, center");
  }
}