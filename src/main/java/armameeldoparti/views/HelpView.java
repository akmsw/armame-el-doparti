package armameeldoparti.views;

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

  @Override
  protected void initializeInterface() {
    addPageTitleTextField();
    addTextArea();
    addPagesLabel();
    addButtons();
    add(masterPanel);
    pack();
  }

  @Override
  protected void addButtons() {
    previousPageButton = new CustomButton("Anterior", Constants.ROUNDED_BORDER_ARC_GENERAL);
    nextPageButton = new CustomButton("Siguiente", Constants.ROUNDED_BORDER_ARC_GENERAL);
    backButton = new CustomButton("Volver al menú principal", Constants.ROUNDED_BORDER_ARC_GENERAL);

    masterPanel.add(
        previousPageButton,
        new CC().width("50%")
                .split()
    );
    masterPanel.add(
        nextPageButton,
        new CC().width("50%")
                .wrap()
    );
    masterPanel.add(
        backButton,
        CommonFunctions.buildMigLayoutConstraints(
          Constants.MIG_LAYOUT_GROWX,
          Constants.MIG_LAYOUT_SPAN
        )
    );
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the text field where to display the current page title.
   */
  private void addPageTitleTextField() {
    pageTitleTextField = new CustomLabel(null, SwingConstants.CENTER);

    masterPanel.add(pageTitleTextField, Constants.MIG_LAYOUT_GROW);
  }

  /**
   * Adds the text area where to display the instructions of the program.
   */
  private void addTextArea() {
    textArea = new CustomTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS);

    masterPanel.add(new CustomScrollPane(textArea));
  }

  /**
   * Adds the reading progress label.
   */
  private void addPagesLabel() {
    pagesCounter = new CustomLabel(null, SwingConstants.CENTER);

    masterPanel.add(pagesCounter, Constants.MIG_LAYOUT_GROWX);
  }

  // ---------------------------------------- Getters -------------------------------------------

  public JButton getPreviousPageButton() {
    return previousPageButton;
  }

  public JButton getNextPageButton() {
    return nextPageButton;
  }

  public JButton getBackButton() {
    return backButton;
  }

  public JLabel getPageTitleTextField() {
    return pageTitleTextField;
  }

  public JLabel getPagesCounter() {
    return pagesCounter;
  }

  public JScrollPane getScrollPane() {
    return scrollPane;
  }

  public JTextArea getTextArea() {
    return textArea;
  }

  // ---------------------------------------- Setters -------------------------------------------

  public void setPreviousPageButton(JButton previousPageButton) {
    this.previousPageButton = previousPageButton;
  }

  public void setNextPageButton(JButton nextPageButton) {
    this.nextPageButton = nextPageButton;
  }

  public void setBackButton(JButton backButton) {
    this.backButton = backButton;
  }

  public void setPageTitleTextField(JLabel pageTitleTextField) {
    this.pageTitleTextField = pageTitleTextField;
  }

  public void setPagesCounter(JLabel pagesCounter) {
    this.pagesCounter = pagesCounter;
  }

  public void setScrollPane(JScrollPane scrollPane) {
    this.scrollPane = scrollPane;
  }

  public void setTextArea(JTextArea textArea) {
    this.textArea = textArea;
  }
}