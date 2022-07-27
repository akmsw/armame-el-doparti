package armameeldoparti.controllers;

import armameeldoparti.utils.Main;
import armameeldoparti.views.SkillsInputView;

/**
 * Clase correspondiente al controlador de la ventana de ingreso de puntuaciones.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 26/07/2022
 */
public class SkillsInputController {

  // ---------------------------------------- Constantes públicas -------------------------------

  public static final int SCORE_INI = 1;
  public static final int SCORE_MIN = 1;
  public static final int SCORE_MAX = 5;
  public static final int SCORE_STEP = 1;

  // ---------------------------------------- Campos privados -----------------------------------

  private static SkillsInputView skillsInputView = new SkillsInputView();

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Constructor vacío.
   */
  private SkillsInputController() {
    // No necesita cuerpo
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  /**
   * Hace visible la ventana de ingreso de resultados.
   */
  public static void showSkillsInputView() {
    skillsInputView.setVisible(true);
  }

  /**
   * Controlador para la pulsación del botón de finalización.
   *
   * <p>Aplica las puntuaciones establecidas a cada jugador,
   * hace invisible la ventana de ingreso de puntuaciones y
   * muestra la ventana de resultados.
   */
  public static void finishButtonEvent() {
    skillsInputView.getSpinnersMap()
                   .forEach((k, v) -> k.setSkill((int) v.getValue()));

    hideSkillsInputView();

    ResultsController.setUp();
    ResultsController.showResultsView();
  }

  /**
   * Controlador para la pulsación del botón de reinicio de puntuaciones.
   *
   * <p>Aplica a todos los jugadores la puntuación por defecto (0) y
   * reinicia el valor de todos los campos de ingreso de puntuación.
   */
  public static void resetSkillsButtonEvent() {
    skillsInputView.getSpinnersMap()
                   .forEach((k, v) -> {
                     k.setSkill(0);
                     v.setValue(SCORE_MIN);
                   });
  }

  /**
   * Controlador para la pulsación del botón de retorno.
   *
   * <p>Elimina la ventana de ingreso de puntuaciones
   * y hace visible la ventana correspondiente.
   */
  public static void backButtonEvent() {
    disposeSkillsInputView();

    if (Main.thereAreAnchorages()) {
      AnchoragesController.showAnchoragesView();
    } else {
      NamesInputController.showNamesInputView();
    }
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Hace invisible la ventana de ingreso de resultados.
   */
  private static void hideSkillsInputView() {
    skillsInputView.setVisible(false);
  }

  /**
   * Elimina la ventana y la reinstancia para que la próxima vez que se
   * la requiera, tenga toda su información por defecto.
   */
  private static void disposeSkillsInputView() {
    skillsInputView.dispose();
    skillsInputView = new SkillsInputView();
  }
}