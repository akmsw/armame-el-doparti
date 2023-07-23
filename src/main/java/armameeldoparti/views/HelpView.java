package armameeldoparti.views;

import armameeldoparti.controllers.HelpController;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.CustomButton;
import armameeldoparti.utils.common.custom.graphical.CustomLabel;
import armameeldoparti.utils.common.custom.graphical.CustomScrollPane;
import armameeldoparti.utils.common.custom.graphical.CustomTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import lombok.Getter;
import net.miginfocom.layout.CC;

/**
 * Help view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
@Getter
public class HelpView extends View {

  // ---------------------------------------- Private constants ---------------------------------

  private static final int TEXT_AREA_ROWS = 20;
  private static final int TEXT_AREA_COLUMNS = 30;

  // ---------------------------------------- Private fields ------------------------------------

  private JButton previousPageButton;
  private JButton nextPageButton;
  private JButton backButton;

  private JLabel pageTitleTextField;
  private JLabel pagesCounter;

  private JScrollPane scrollPane;

  private JTextArea textArea;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the help view.
   */
  public HelpView() {
    super("Ayuda", Constants.MIG_LAYOUT_WRAP);
    initializeInterface();
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Initializes the view and makes it visible.
   */
  @Override
  protected void initializeInterface() {
    addPageTitleTextField();
    addTextArea();
    addPagesLabel();
    addButtons();
    add(getMasterPanel());
    pack();
  }

  /**
   * Adds the buttons to their corresponding panel.
   */
  @Override
  protected void addButtons() {
    previousPageButton = new CustomButton("Anterior");
    previousPageButton.addActionListener(e ->
        ((HelpController) CommonFunctions.getController(ProgramView.HELP)).previousPageButtonEvent()
    );

    nextPageButton = new CustomButton("Siguiente");
    nextPageButton.addActionListener(e ->
        ((HelpController) CommonFunctions.getController(ProgramView.HELP)).nextPageButtonEvent()
    );

    backButton = new CustomButton("Volver al menú principal");
    backButton.addActionListener(e ->
        ((HelpController) CommonFunctions.getController(ProgramView.HELP)).backButtonEvent()
    );

    previousPageButton.setEnabled(false);

    getMasterPanel().add(previousPageButton, new CC().width("50%")
                                                     .split());
    getMasterPanel().add(nextPageButton, new CC().width("50%")
                                                 .wrap());
    getMasterPanel().add(
        backButton,
        CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROWX,
                                                  Constants.MIG_LAYOUT_SPAN)
    );
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the text field where to display the current page title.
   */
  private void addPageTitleTextField() {
    pageTitleTextField = new CustomLabel(SwingConstants.CENTER);

    getMasterPanel().add(pageTitleTextField, Constants.MIG_LAYOUT_GROW);
  }

  /**
   * Adds the text area where to display the instructions of the program.
   */
  private void addTextArea() {
    textArea = new CustomTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS);

    getMasterPanel().add(new CustomScrollPane(textArea));
  }

  /**
   * Adds the reading progress label.
   */
  private void addPagesLabel() {
    pagesCounter = new CustomLabel(SwingConstants.CENTER);

    getMasterPanel().add(pagesCounter, Constants.MIG_LAYOUT_GROWX);
  }
}