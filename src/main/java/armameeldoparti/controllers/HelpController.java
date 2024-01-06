package armameeldoparti.controllers;

import armameeldoparti.models.Error;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.views.HelpView;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.swing.JTextArea;

/**
 * Help view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class HelpController extends Controller<HelpView> {

  // ---------------------------------------- Private constants ---------------------------------

  private static final int TOTAL_HELP_PAGES = Constants.MAP_HELP_PAGES_FILES
                                                       .size();

  // ---------------------------------------- Private fields ------------------------------------

  private int currentPageNumber;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the help view controller.
   *
   * @param helpView View to control.
   */
  public HelpController(HelpView helpView) {
    super(helpView);
    setUpListeners();
    setUpInitialState();
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Resets the page to the beginning, makes the controlled view invisible and shows the main menu view.
   */
  public void backButtonEvent() {
    hideView();
    resetView();

    CommonFunctions.getController(ProgramView.MAIN_MENU)
                   .showView();
  }

  /**
   * Increments the page number updating the state of the buttons, the displayed page in the text area and the reading progress label.
   */
  public void nextPageButtonEvent() {
    if (++currentPageNumber < TOTAL_HELP_PAGES - 1) {
      view.getPreviousPageButton()
          .setEnabled(true);
    } else {
      view.getNextPageButton()
          .setEnabled(false);
    }

    updatePage();
  }

  /**
   * Decrements the page number updating the state of the buttons, the displayed page in the text area and the reading progress label.
   */
  public void previousPageButtonEvent() {
    if (--currentPageNumber > 0) {
      view.getNextPageButton()
          .setEnabled(true);
    } else {
      view.getPreviousPageButton()
          .setEnabled(false);
    }

    updatePage();
  }

  /**
   * Updates the displayed page in the text area.
   *
   * <p>Finds the text file corresponding to the page number and displays its content.
   */
  public void updatePage() {
    JTextArea textArea = view.getTextArea();

    view.getPageTitleTextField()
        .setText(Constants.MAP_HELP_PAGES_FILES
                          .get(currentPageNumber)
                          .get(Constants.INDEX_HELP_PAGE_TITLE));

    textArea.setText("");

    updateLabel();

    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(
          Objects.requireNonNull(
            HelpController.class
                          .getClassLoader()
                          .getResourceAsStream(Constants.PATH_HELP_DOCS + Constants.MAP_HELP_PAGES_FILES
                                                                                   .get(currentPageNumber)
                                                                                   .get(Constants.INDEX_HELP_PAGE_FILENAME)),
            Constants.MSG_ERROR_NULL_RESOURCE
          ),
          StandardCharsets.UTF_8
        )
    )) {
      textArea.read(reader, null);
      textArea.setCaretPosition(0);
      textArea.scrollRectToVisible(new Rectangle(0, 0, 1, 1));
    } catch (IOException e) {
      CommonFunctions.exitProgram(Error.ERROR_FILES);
    }
  }

  // ---------------------------------------- Protected methods ---------------------------------

  @Override
  protected void resetView() {
    currentPageNumber = 0;

    updatePage();
    resetButtons();
  }

  @Override
  protected void setUpInitialState() {
    currentPageNumber = 0;

    view.getPreviousPageButton()
        .setEnabled(false);
  }

  @Override
  protected void setUpListeners() {
    view.getPreviousPageButton()
        .addActionListener(e -> previousPageButtonEvent());
    view.getNextPageButton()
        .addActionListener(e -> nextPageButtonEvent());
    view.getBackButton()
        .addActionListener(e -> backButtonEvent());
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Updates the reading progress label text.
   */
  private void updateLabel() {
    view.getPagesCounter()
        .setText(currentPageNumber + 1 + "/" + TOTAL_HELP_PAGES);
  }

  /**
   * Resets the navigation buttons to their initial state.
   */
  private void resetButtons() {
    view.getPreviousPageButton()
        .setEnabled(false);
    view.getNextPageButton()
        .setEnabled(true);
  }

  // ---------------------------------------- Getters -------------------------------------------

  public int getCurrentPageNumber() {
    return currentPageNumber;
  }

  // ---------------------------------------- Setters -------------------------------------------

  public void setCurrentPageNumber(int currentPageNumber) {
    this.currentPageNumber = currentPageNumber;
  }
}