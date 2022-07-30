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
 * Clase correspondiente a la ventana de resultados de distribución de jugadores.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 06/03/2021
 */
public class ResultsView extends View {

  /**
   * Tamaño de ancho (en píxeles) fijo para las celdas de la tabla de resultados.
   * Valor ajustado a fuente del programa teniendo en cuenta su tamaño y la cantidad
   * máxima de caracteres en los nombres de los jugadores.
   */
  private static final int FIXED_CELL_WIDTH = 250;

  // ---------------------------------------- Campos privados -----------------------------------

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

  // ---------------------------------------- Métodos públicos ----------------------------------

  /**
   * Inicializa y muestra la interfaz gráfica de la ventana.
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
   * Aplica el tamaño ideal para las celdas de la tabla.
   */
  public void setTableCellsSize() {
    // Ajuste del ancho de las celdas
    for (int column = 0; column < table.getColumnCount(); column++) {
      table.getColumnModel()
           .getColumn(column)
           .setPreferredWidth(FIXED_CELL_WIDTH);
    }

    // Ajuste del alto de las celdas
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
   * Obtiene el título de la ventana.
   *
   * @return El título de la ventana.
   */
  public String getFrameTitle() {
    return frameTitle;
  }

  /**
   * Obtiene la tabla que muestra los resultados de la distribución.
   *
   * @return La tabla que muestra los resultados de la distribución.
   */
  public JTable getTable() {
    return table;
  }

  // --------------------------------------------- Setters --------------------------------------

  /**
   * Actualiza el objeto tabla que muestra los resultados de la distribución.
   *
   * @param table El nuevo objeto tabla.
   */
  public void setTable(JTable table) {
    this.table = table;
  }

  /**
   * Actualiza el título de la ventana.
   *
   * @param frameTitle El nuevo título para la ventana.
   */
  public void setFrameTitle(String frameTitle) {
    this.frameTitle = frameTitle;
  }

  // ---------------------------------------- Métodos protegidos --------------------------------

  /**
   * Coloca los botones en los paneles de la ventana.
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

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Coloca en el panel principal de la ventana la tabla donde se mostrarán los jugadores
   * y sus respectivas posiciones para cada equipo armado.
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