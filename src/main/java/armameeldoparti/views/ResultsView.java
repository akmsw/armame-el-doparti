package armameeldoparti.views;

import armameeldoparti.controllers.ResultsController;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.CustomButton;
import javax.swing.JButton;
import javax.swing.JTable;

/**
 * Results view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class ResultsView extends View {

  // ---------------------------------------- Private fields ------------------------------------

  private JTable table;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds an empty results view.
   */
  public ResultsView() {
    super(getUpdatedFrameTitle(), Constants.MIG_LAYOUT_WRAP);
  }

  // ---------------------------------------- Public methods ------------------------------------

  @Override
  public void initializeInterface() {
    setFrameTitle(getUpdatedFrameTitle());
    setTitle(getFrameTitle());
    addTable();
    addButtons();
    add(getMasterPanel());
  }

  // ---------------------------------------- Protected methods ---------------------------------

  @Override
  protected void addButtons() {
    JButton backButton = new CustomButton("Atrás", Constants.ROUNDED_BORDER_ARC_GENERAL);
    backButton.addActionListener(e ->
        ((ResultsController) CommonFunctions.getController(ProgramView.RESULTS))
        .backButtonEvent()
    );

    if (CommonFields.getDistribution() == Constants.MIX_RANDOM) {
      JButton remixButton = new CustomButton("Redistribuir", Constants.ROUNDED_BORDER_ARC_GENERAL);

      remixButton.addActionListener(
          e -> ((ResultsController) CommonFunctions.getController(ProgramView.RESULTS))
               .remixButtonEvent()
      );

      getMasterPanel().add(remixButton, Constants.MIG_LAYOUT_GROWX);
    }

    getMasterPanel().add(backButton, Constants.MIG_LAYOUT_GROWX);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Updates and returns the frame title based on the chosen distribution and the anchorages option.
   *
   * @return The updated frame title.
   */
  private static String getUpdatedFrameTitle() {
    return String.join(
      " - ",
      CommonFields.getDistribution() == Constants.MIX_RANDOM ? "Aleatorio" : "Por puntuaciones",
      CommonFields.isAnchoragesEnabled() ? "Con anclajes" : "Sin anclajes"
    );
  }

  /**
   * Adds the results table in the view panel.
   */
  private void addTable() {
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.setCellSelectionEnabled(false);
    table.setRowSelectionAllowed(false);
    table.setColumnSelectionAllowed(false);
    table.setEnabled(false);

    getMasterPanel().add(
        table,
        CommonFunctions.buildMigLayoutConstraints(
          Constants.MIG_LAYOUT_PUSH,
          Constants.MIG_LAYOUT_GROW,
          Constants.MIG_LAYOUT_SPAN,
          Constants.MIG_LAYOUT_CENTER
        )
    );
  }

  // ---------------------------------------- Getters -------------------------------------------

  public JTable getTable() {
    return table;
  }

  // ---------------------------------------- Setters -------------------------------------------

  public void setTable(JTable table) {
    this.table = table;
  }
}