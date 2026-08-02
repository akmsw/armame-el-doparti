package armameeldopartidesktop.views;

import javax.swing.JButton;
import javax.swing.JTable;

import armameeldopartidesktop.models.enums.Distribution;
import armameeldopartidesktop.utils.common.CommonFields;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.utils.common.custom.graphical.CustomButton;

/**
 * Results view class.
 *
 * @since 3.0.0
 *
 * @version 1.1.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class ResultsView extends View {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private JButton backButton;
  private JButton remixButton;

  private JTable table;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds an empty results view.
   */
  public ResultsView() {
    super(getUpdatedFrameTitle(), Constants.MIG_LAYOUT_WRAP);

    setBackButton(new CustomButton("Atrás"));
    setRemixButton(new CustomButton("Redistribuir"));
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void initializeInterface() {
    CommonFields.getMainFrame().setTitle(getUpdatedFrameTitle());

    addTable();
    addButtons();
    add(panel);
    refreshView();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void addButtons() {
    if (CommonFields.getDistribution() == Distribution.MIX_RANDOM) {
      panel.add(remixButton, Constants.MIG_LAYOUT_GROWX);
    }

    panel.add(backButton, Constants.MIG_LAYOUT_GROWX);
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @return The updated frame title based on the chosen distribution and the anchorages option.
   */
  private static String getUpdatedFrameTitle() {
    return String.join(" - ", (CommonFields.getDistribution() == Distribution.MIX_RANDOM) ? "Aleatorio" : "Por puntuaciones", CommonFields.isAnchoragesEnabled() ? "Con anclajes" : "Sin anclajes");
  }

  /**
   * Adds the results table in the view panel.
   */
  private void addTable() {
    panel.add(table, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_PUSH, Constants.MIG_LAYOUT_GROW, Constants.MIG_LAYOUT_SPAN, Constants.MIG_LAYOUT_CENTER));
  }

  // ---------- Public getters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public JButton getBackButton() {
    return backButton;
  }

  public JButton getRemixButton() {
    return remixButton;
  }

  public JTable getTable() {
    return table;
  }

  // ---------- Public setters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setBackButton(JButton backButton) {
    this.backButton = backButton;
  }

  public void setRemixButton(JButton remixButton) {
    this.remixButton = remixButton;
  }

  public void setTable(JTable table) {
    this.table = table;
  }
}