package armameeldoparti.controllers;

import armameeldoparti.models.Error;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.views.HelpView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;

/**
 * Help view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
@Getter
public class HelpController extends Controller<HelpView> {

  // ---------------------------------------- Private constants ---------------------------------

  private static final Map<Integer, List<String>> pagesMap = Map.of(
      0, Arrays.asList("INTRODUCCIÓN", "helpIntro.hlp"),
      1, Arrays.asList("CRITERIOS ESTABLECIDOS", "helpCriteria.hlp"),
      2, Arrays.asList("INGRESO DE JUGADORES", "helpNames.hlp"),
      3, Arrays.asList("ANCLAJES", "helpAnchorages.hlp"),
      4, Arrays.asList("PUNTUACIONES", "helpScores.hlp"),
      5, Arrays.asList("DISTRIBUCIÓN ALEATORIA", "helpRandomMix.hlp"),
      6, Arrays.asList("DISTRIBUCIÓN POR PUNTUACIONES", "helpBySkillsMix.hlp"),
      7, Arrays.asList("SUGERENCIAS, REPORTES Y CONTACTO", "helpContact.hlp")
  );

  private static final int PAGE_FILENAME_INDEX = 1;
  private static final int PAGE_TITLE_INDEX = 0;
  private static final int TOTAL_PAGES = pagesMap.size();

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

    currentPageNumber = 0;
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Resets the controlled view to its default values.
   */
  @Override
  public void resetView() {
    currentPageNumber = 0;

    updatePage();
    resetButtons();
  }

  /**
   * 'Back' button event handler.
   *
   * <p>Resets the page to the beginning, makes the controlled view invisible and shows the main
   * menu view.
   */
  public void backButtonEvent() {
    hideView();
    resetView();

    CommonFunctions.getController(ProgramView.MAIN_MENU)
                   .showView();
  }

  /**
   * 'Next page' button event handler.
   *
   * <p>Increments the page number, updating the state of the buttons, the displayed page in the
   * text area and the reading progress label.
   */
  public void nextPageButtonEvent() {
    if (++currentPageNumber < TOTAL_PAGES - 1) {
      getView().getPreviousPageButton()
               .setEnabled(true);
    } else {
      getView().getNextPageButton()
               .setEnabled(false);
    }

    updatePage();
  }

  /**
   * 'Previous page' button event handler.
   *
   * <p>Decrements the page number, updating the state of the buttons, the displayed page in the
   * text area and the reading progress label.
   */
  public void previousPageButtonEvent() {
    if (--currentPageNumber > 0) {
      getView().getNextPageButton()
               .setEnabled(true);
    } else {
      getView().getPreviousPageButton()
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
    getView().getPageTitleTextField()
             .setText(pagesMap.get(currentPageNumber)
                              .get(PAGE_TITLE_INDEX));
    getView().getTextArea()
             .setText("");

    updateLabel();

    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(
          Objects.requireNonNull(
            HelpController.class
                          .getClassLoader()
                          .getResourceAsStream(Constants.PATH_HELP_DOCS
                                               + pagesMap.get(currentPageNumber)
                                                         .get(PAGE_FILENAME_INDEX)),
            Constants.MSG_ERROR_NULL_RESOURCE
          ),
          StandardCharsets.UTF_8)
    )) {
      getView().getTextArea()
               .read(reader, null);
    } catch (IOException e) {
      e.printStackTrace();

      CommonFunctions.exitProgram(Error.INTERNAL_FILES_ERROR);
    }
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Updates the reading progress label text.
   */
  private void updateLabel() {
    getView().getPagesCounter()
             .setText(currentPageNumber + 1 + "/" + TOTAL_PAGES);
  }

  /**
   * Resets the navigation buttons to their initial state.
   */
  private void resetButtons() {
    getView().getPreviousPageButton()
             .setEnabled(false);
    getView().getNextPageButton()
             .setEnabled(true);
  }
}