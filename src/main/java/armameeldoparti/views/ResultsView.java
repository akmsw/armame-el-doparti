package armameeldoparti.views;

import armameeldoparti.controllers.ResultsController;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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

  // ---------------------------------------- Private fields ------------------------------------

  private @Getter @Setter JTable table;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds an empty results view.
   */
  public ResultsView() {
    super(getUpdatedFrameTitle(), "wrap");
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Initializes the view and makes it visible.
   */
  @Override
  public void initializeInterface() {
    setFrameTitle(getUpdatedFrameTitle());
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(Constants.ICON
                          .getImage());
    setResizable(false);
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

      getMasterPanel().add(remixButton, "growx");
    }

    getMasterPanel().add(backButton, "growx");
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
    table.setBorder(BorderFactory.createLineBorder(Constants.GREEN_DARK));
    table.setEnabled(false);

    getMasterPanel().add(table, "push, grow, span, center");
  }
}