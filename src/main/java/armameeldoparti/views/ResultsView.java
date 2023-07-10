package armameeldoparti.views;

import armameeldoparti.controllers.ResultsController;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import javax.swing.JButton;
import javax.swing.JTable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Results view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
@Getter
@Setter
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

  /**
   * Initializes the view and makes it visible.
   */
  @Override
  public void initializeInterface() {
    setFrameTitle(getUpdatedFrameTitle());
    setTitle(getFrameTitle());
    addTable();
    addButtons();
    add(getMasterPanel());
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Adds the buttons to their corresponding panel.
   */
  @Override
  protected void addButtons() {
    JButton backButton = new JButton("Atrás");

    backButton.addActionListener(e ->
        ((ResultsController) CommonFunctions.getController(ProgramView.RESULTS))
        .backButtonEvent()
    );

    if (CommonFields.getDistribution() == Constants.MIX_RANDOM) {
      JButton remixButton = new JButton("Redistribuir");

      remixButton.addActionListener(e ->
          ((ResultsController) CommonFunctions.getController(ProgramView.RESULTS))
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
  @NonNull
  private static String getUpdatedFrameTitle() {
    return (CommonFields.getDistribution() == Constants.MIX_RANDOM
            ? "Aleatorio - "
            : "Por puntuaciones - ").concat(CommonFields.isAnchoragesEnabled() ? "Con anclajes"
                                                                               : "Sin anclajes");
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
}