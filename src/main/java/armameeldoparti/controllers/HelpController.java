package armameeldoparti.controllers;

import armameeldoparti.Main;
import armameeldoparti.views.HelpView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;

/**
 * Help view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 26/07/2022
 */
public class HelpController implements Controller {

  // ---------------------------------------- Private constants ---------------------------------

  private static final Map<Integer, List<String>> pagesMap = Map.of(
      0, Arrays.asList("INTRODUCCIÓN", "helpIntro.txt"),
      1, Arrays.asList("CRITERIOS ESTABLECIDOS", "helpCriteria.txt"),
      2, Arrays.asList("INGRESO DE JUGADORES", "helpNames.txt"),
      3, Arrays.asList("ANCLAJES", "helpAnchorages.txt"),
      4, Arrays.asList("PUNTUACIONES", "helpScores.txt"),
      5, Arrays.asList("DISTRIBUCIÓN ALEATORIA", "helpRandomMix.txt"),
      6, Arrays.asList("DISTRIBUCIÓN POR PUNTUACIONES", "helpByScoresMix.txt"),
      7, Arrays.asList("SUGERENCIAS, REPORTES Y CONTACTO", "helpContact.txt")
  );

  private static final int PAGE_TITLE_INDEX = 0;
  private static final int PAGE_FILENAME_INDEX = 1;
  private static final int TOTAL_PAGES = pagesMap.size();

  // ---------------------------------------- Private fields ------------------------------------

  private int pageNumber = 0;

  private HelpView helpView;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the help view controller.
   *
   * @param helpView View to control.
   */
  public HelpController(HelpView helpView) {
    this.helpView = helpView;
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Makes the controlled view visible.
   */
  @Override
  public void showView() {
    helpView.setVisible(true);
  }

  /**
   * Makes the controlled view invisible.
   */
  @Override
  public void hideView() {
    helpView.setVisible(false);
    Controller.centerView(helpView);
  }

  /**
   * Resets the controlled view to its default values.
   */
  @Override
  public void resetView() {
    pageNumber = 0;

    updatePage();
  }

  /**
   * 'Back' button event handler.
   *
   * <p>Resets the page to the beginning, makes the controlled view
   * invisible and shows the main menu view.
   */
  public void backButtonEvent() {
    hideView();
    resetView();

    Main.getMainMenuController()
        .showView();
  }

  /**
   * 'Next page' button event handler.
   *
   * <p>Increments the page number, updating the state of the buttons,
   * the displayed page in the text area and the reading progress label.
   */
  public void nextPageButtonEvent() {
    if (++pageNumber < TOTAL_PAGES - 1) {
      helpView.getPreviousPageButton()
              .setEnabled(true);
    } else {
      helpView.getNextPageButton()
              .setEnabled(false);
    }

    updatePage();
  }

  /**
   * 'Previous page' button event handler.
   *
   * <p>Decrements the page number, updating the state of the buttons,
   * the displayed page in the text area and the reading progress label.
   */
  public void previousPageButtonEvent() {
    if (--pageNumber > 0) {
      helpView.getNextPageButton()
              .setEnabled(true);
    } else {
      helpView.getPreviousPageButton()
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
    helpView.getScrollPane()
            .setBorder(BorderFactory.createTitledBorder(pagesMap.get(pageNumber)
                                                                .get(PAGE_TITLE_INDEX)));
    helpView.getTextArea()
            .setText(null);

    updateLabel();

    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(
          HelpController.class
                        .getClassLoader()
                        .getResourceAsStream(Main.HELP_DOCS_PATH
                                             + pagesMap.get(pageNumber)
                                                       .get(PAGE_FILENAME_INDEX)),
          StandardCharsets.UTF_8)
    )) {
      helpView.getTextArea()
              .read(reader, null);
    } catch (Exception ex) {
      ex.printStackTrace();
      System.exit(-1);
    }
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Updates the reading progress label text.
   */
  private void updateLabel() {
    helpView.getPagesCounter()
            .setText(pageNumber + 1 + "/" + TOTAL_PAGES);
  }
}