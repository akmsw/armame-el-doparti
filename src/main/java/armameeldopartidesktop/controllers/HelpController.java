package armameeldopartidesktop.controllers;

import java.awt.Rectangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.swing.JTextArea;

import armameeldopartidesktop.models.enums.Error;
import armameeldopartidesktop.models.enums.ProgramView;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.views.HelpView;

/**
 * Help view controller class.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class HelpController extends Controller<HelpView> {

  // ---------- Private constants -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private static final int TOTAL_HELP_PAGES = Constants.MAP_HELP_PAGES_FILES.size();

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private int currentPageNumber;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Resets the page to the beginning, makes the controlled view invisible and shows the main menu view.
   */
  public void backButtonEvent() {
    hideView();
    resetView();

    CommonFunctions.getController(ProgramView.MAIN_MENU).showView();
  }

  /**
   * Increments the page number updating the state of the buttons, the displayed page in the text area and the reading progress label.
   */
  public void nextPageButtonEvent() {
    currentPageNumber++;

    updateView();
  }

  /**
   * Decrements the page number updating the state of the buttons, the displayed page in the text area and the reading progress label.
   */
  public void previousPageButtonEvent() {
    currentPageNumber--;

    updateView();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void setUpInitialState() {
    resetView();
  }

  @Override
  protected void resetView() {
    setCurrentPageNumber(0);
    updateView();
  }

  @Override
  protected void setUpListeners() {
    view.getPreviousPageButton().addActionListener(_ -> previousPageButtonEvent());
    view.getNextPageButton().addActionListener(_ -> nextPageButtonEvent());
    view.getBackButton().addActionListener(_ -> backButtonEvent());
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Updates the view state according to the current page number.
   */
  private void updateView() {
    updateNavigationButtons();
    updateReadingProgressLabel();
    updatePage();
  }

  /**
   * Updates the state of the navigation buttons according to the current page number.
   */
  private void updateNavigationButtons() {
    view.getPreviousPageButton().setEnabled(currentPageNumber > 0);
    view.getNextPageButton().setEnabled(currentPageNumber < TOTAL_HELP_PAGES - 1);
  }

  /**
   * Updates the reading progress label text according to the current page number.
   */
  private void updateReadingProgressLabel() {
    view.getReadingProgressLabel().setText((currentPageNumber + 1) + "/" + TOTAL_HELP_PAGES);
  }

  /**
   * Updates the displayed page in the text area.
   *
   * <p>Finds the text file corresponding to the page number and displays its content.
   */
  private void updatePage() {
    JTextArea textArea = view.getTextArea();

    view.getPageTitleLabel().setText(Constants.MAP_HELP_PAGES_FILES.get(currentPageNumber).get(Constants.INDEX_HELP_PAGE_TITLE));

    textArea.setText(null);

    updateReadingProgressLabel();

    try (
      BufferedReader reader = new BufferedReader(
                                new InputStreamReader(
                                  Objects.requireNonNull(
                                    HelpController.class
                                                  .getClassLoader()
                                                  .getResourceAsStream(
                                                    Constants.PATH_HELP_DOCS + Constants.MAP_HELP_PAGES_FILES
                                                                                        .get(currentPageNumber)
                                                                                        .get(Constants.INDEX_HELP_PAGE_FILENAME)
                                                  ),
                                    Constants.MSG_ERROR_NULL_GUI_RESOURCE
                                  ),
                                  StandardCharsets.UTF_8
                                )
                              )
    ) {
      textArea.read(reader, null);
      textArea.setCaretPosition(0);
      textArea.scrollRectToVisible(new Rectangle(0, 0, 1, 1));
    } catch (IOException exception) {
      CommonFunctions.exitProgram(Error.ERROR_FILES, exception);
    }
  }

  // ---------- Public getters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public int getCurrentPageNumber() {
    return currentPageNumber;
  }

  // ---------- Public setters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setCurrentPageNumber(int currentPageNumber) {
    this.currentPageNumber = currentPageNumber;
  }
}