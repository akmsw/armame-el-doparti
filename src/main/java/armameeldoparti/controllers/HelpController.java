package armameeldoparti.controllers;

import armameeldoparti.interfaces.Controller;
import armameeldoparti.utils.Main;
import armameeldoparti.views.HelpView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;

/**
 * Clase correspondiente al controlador de la ventana de ayuda.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 26/07/2022
 */
public class HelpController implements Controller {

  // ---------------------------------------- Constantes privadas -------------------------------

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

  // ---------------------------------------- Campos privados -----------------------------------

  private int pageNumber = 0;

  private HelpView helpView;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye el controlador para la vista de ayuda.
   *
   * @param helpView Vista a controlar.
   */
  public HelpController(HelpView helpView) {
    this.helpView = helpView;
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  /**
   * Hace visible la ventana controlada.
   */
  @Override
  public void showView() {
    helpView.setVisible(true);
  }

  /**
   * Hace invisible la ventana controlada.
   */
  @Override
  public void hideView() {
    helpView.setVisible(false);
    Controller.centerView(helpView);
  }

  /**
   * Reinicia la ventana controlada a sus valores por defecto.
   */
  @Override
  public void resetView() {
    // No es necesario implementarlo en esta ventana
  }

  /**
   * Controlador para la pulsación del botón de retorno.
   *
   * <p>Reinicia la página al comienzo, hace invisible
   * la ventana controlada y hace visible la ventana anterior.
   */
  public void backButtonEvent() {
    pageNumber = 0;

    updatePage();
    hideView();

    Main.getMainMenuController()
        .showView();
  }

  /**
   * Controlador para la pulsación del botón de página siguiente.
   *
   * <p>Incrementa el número de página actualizando el estado de los
   * botones y de la página mostrada en el área de texto.
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
   * Controlador para la pulsación del botón de página previa.
   *
   * <p>Decrementa el número de página actualizando el estado de los
   * botones y de la página mostrada en el área de texto.
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
   * Actualiza la página de instrucciones mostrada en el área de texto.
   *
   * <p>Busca el archivo correspondiente al número de página y muestra su contenido.
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

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Actualiza el texto mostrado en la etiqueta de progreso de lectura.
   */
  private void updateLabel() {
    helpView.getPagesCounter()
            .setText(pageNumber + 1 + "/" + TOTAL_PAGES);
  }
}