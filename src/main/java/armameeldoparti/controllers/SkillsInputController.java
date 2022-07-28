package armameeldoparti.controllers;

import armameeldoparti.interfaces.Controller;
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
public class SkillsInputController implements Controller {

  // ---------------------------------------- Campos privados -----------------------------------

  private SkillsInputView skillsInputView;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye el controlador para la vista de ingreso de puntuaciones.
   *
   * @param skillsInputView Vista a controlar.
   */
  public SkillsInputController(SkillsInputView skillsInputView) {
    this.skillsInputView = skillsInputView;
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  /**
   * Hace visible la ventana controlada.
   */
  @Override
  public void showView() {
    skillsInputView.setLocationRelativeTo(null);
    skillsInputView.setVisible(true);
  }

  /**
   * Hace invisible la ventana controlada.
   */
  @Override
  public void hideView() {
    skillsInputView.setVisible(false);
  }

  /**
   * Reinicia la ventana controlada a sus valores por defecto.
   */
  @Override
  public void resetView() {
    resetSkills();
    hideView();
  }

  /**
   * Controlador para la pulsación del botón de finalización.
   *
   * <p>Aplica las puntuaciones establecidas a cada jugador,
   * hace invisible la ventana de ingreso de puntuaciones y
   * muestra la ventana de resultados.
   */
  public void finishButtonEvent() {
    skillsInputView.getSpinnersMap()
                   .forEach((k, v) -> k.setSkill((int) v.getValue()));

    hideView();

    Main.getResultsController()
        .setUp();

    Main.getResultsController()
        .showView();
  }

  /**
   * Controlador para la pulsación del botón de reinicio de puntuaciones.
   *
   * <p>Aplica a todos los jugadores la puntuación por defecto (0) y
   * reinicia el valor de todos los campos de ingreso de puntuación.
   */
  public void resetSkillsButtonEvent() {
    resetSkills();
  }

  /**
   * Controlador para la pulsación del botón de retorno.
   *
   * <p>Elimina la ventana de ingreso de puntuaciones
   * y hace visible la ventana correspondiente.
   */
  public void backButtonEvent() {
    resetView();

    if (Main.thereAreAnchorages()) {
      Main.getAnchoragesController()
          .showView();
    } else {
      Main.getNamesInputController()
          .showView();
    }
  }

  /**
   * Actualiza las etiquetas con los nombres de los jugadores.
   */
  public void updateNameLabels() {
    skillsInputView.updateNameLabels();
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Aplica a todos los jugadores la puntuación por defecto (0) y
   * reinicia el valor de todos los campos de ingreso de puntuación.
   */
  private void resetSkills() {
    skillsInputView.getSpinnersMap()
                   .forEach((k, v) -> {
                     k.setSkill(0);
                     v.setValue(Main.SCORE_MIN);
                   });
  }
}