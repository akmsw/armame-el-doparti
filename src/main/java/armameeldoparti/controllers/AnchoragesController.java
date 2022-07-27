package armameeldoparti.controllers;

import armameeldoparti.models.Position;
import armameeldoparti.utils.Main;
import armameeldoparti.views.AnchoragesView;
import armameeldoparti.views.MainMenuView;
import java.util.List;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * Clase correspondiente al controlador de la ventana de anclaje de jugadores.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 26/07/2022
 */
public class AnchoragesController {

  // ---------------------------------------- Constantes privadas -------------------------------

  private static final int MAX_PLAYERS_PER_ANCHORAGE = Main.PLAYERS_PER_TEAM - 1;

  // ---------------------------------------- Campos privados -----------------------------------

  private static int playersAnchored = 0;
  private static int anchoragesAmount = 0;

  private static AnchoragesView anchoragesView = new AnchoragesView();

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Constructor vacío.
   */
  private AnchoragesController() {
    // No necesita cuerpo
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  /**
   * Hace visible la ventana de ingreso de anclajes.
   */
  public static void showAnchoragesView() {
    anchoragesView.setVisible(true);
  }

  /**
   * Hace invisible la ventana de ingreso de anclajes.
   */
  public static void hideAnchoragesView() {
    anchoragesView.setVisible(false);
  }

  /**
   * Controlador para la pulsación del botón de finalización.
   *
   * <p>Corrobora las condiciones necesarias para los anclajes
   * establecidos y, si se cumplen, procede con la distribución.
   */
  public static void finishButtonEvent() {
    // if !validAnchoragesCombination()
    //   showErrorMessage("Error message")
    //   return

    finish();
  }

  /**
   * Controlador para la pulsación del botón de nuevo anclaje.
   *
   * <p>Corrobora las condiciones necesarias para poder realizar
   * el anclaje deseado y, si se cumplen, lo aplica.
   */
  public static void newAnchorageButtonEvent() {
    int playersToAnchorAmount = (int) anchoragesView.getCheckBoxesMap()
                                                    .values()
                                                    .stream()
                                                    .flatMap(List::stream)
                                                    .filter(JCheckBox::isSelected)
                                                    .count();

    if (!validChecksAmount(playersToAnchorAmount)) {
      showErrorMessage("No puede haber más de " + MAX_PLAYERS_PER_ANCHORAGE
                       + " ni menos de 2 jugadores en un mismo anclaje");
      return;
    }

    if (!validCheckedPlayersPerPosition()) {
      showErrorMessage("No puede haber más de la mitad de jugadores de una misma posición "
                       + "en un mismo anclaje");
      return;
    }

    if (!validAnchoredPlayersAmount(playersToAnchorAmount)) {
      showErrorMessage("No puede haber más de " + 2 * MAX_PLAYERS_PER_ANCHORAGE
                       + " jugadores anclados en total");
      return;
    }

    newAnchorage();
    updateTextArea();
    toggleButtons();
  }

  /**
   * Controlador para la pulsación del botón de borrado de último anclaje.
   *
   * <p>Borra el último anclaje realizado, actualizando el área de texto y
   * el estado de los botones.
   */
  public static void deleteLastAnchorageButtonEvent() {
    deleteAnchorage(anchoragesAmount);
    updateTextArea();
    toggleButtons();
  }

  /**
   * Controlador para la pulsación del botón de borrado de un anclaje.
   *
   * <p>Solicita al usuario el número de anclaje a borrar, y lo elimina,
   * actualizando el área de texto y el estado de los botones.
   */
  public static void deleteAnchorageButtonEvent() {
    String[] optionsDelete = new String[anchoragesAmount];

    for (int i = 0; i < anchoragesAmount; i++) {
      optionsDelete[i] = Integer.toString(i + 1);
    }

    int anchorageToDelete = JOptionPane.showOptionDialog(null,
                                                         "Seleccione qué anclaje desea borrar",
                                                         "Antes de continuar...", 2,
                                                         JOptionPane.QUESTION_MESSAGE,
                                                         MainMenuView.SCALED_ICON, optionsDelete,
                                                         optionsDelete[0]);

    if (anchorageToDelete != JOptionPane.CLOSED_OPTION) {
      deleteAnchorage(anchorageToDelete + 1);
      updateTextArea();
      toggleButtons();
    }
  }

  /**
   * Controlador para la pulsación del botón de limpieza de anclajes.
   *
   * <p>Borra todos los anclajes realizados, actualizando el área de texto y
   * el estado de los botones.
   */
  public static void clearAnchoragesButtonEvent() {
    clearAnchorages();
    updateTextArea();
    toggleButtons();
  }

  /**
   * Controlador para la pulsación del botón de retorno.
   *
   * <p>Borra todos los anclajes realizados y elimina la ventana
   * de ingreso de anclajes, luego hace visible la ventana
   * de ingreso de nombres.
   */
  public static void backButtonEvent() {
    clearAnchorages();
    disposeAnchoragesView();

    NamesInputController.showNamesInputView();
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Crea una ventana de error con un texto personalizado.
   *
   * @param errorMessage Mensaje de error a mostrar en la ventana.
   */
  private static void showErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(null, errorMessage, "¡Error!", JOptionPane.ERROR_MESSAGE, null);
  }

  /**
   * Crea un nuevo anclaje en base a los jugadores correspondientes
   * a las casillas seleccionadas y actualiza el valor de jugadores
   * anclados en total.
   */
  private static void newAnchorage() {
    anchoragesAmount++;

    anchoragesView.getCheckBoxesMap()
                  .values()
                  .stream()
                  .filter(cbs -> cbs.stream()
                                    .anyMatch(JCheckBox::isSelected))
                  .forEach(AnchoragesController::setAnchorages);

    playersAnchored = (int) Main.getPlayersSets()
                                .values()
                                .stream()
                                .flatMap(List::stream)
                                .filter(p -> p.getAnchor() != 0)
                                .count();
  }

  /**
   * Actualiza el área de texto mostrando la cantidad de anclajes
   * y los jugadores anclados a los mismos.
   */
  private static void updateTextArea() {
    anchoragesView.getTextArea()
                  .setText(null);

    var wrapperAnchorageNum = new Object() {
      private int anchorageNum;
    };

    var wrapperCounter = new Object() {
      private int counter = 1;
    };

    for (wrapperAnchorageNum.anchorageNum = 1; wrapperAnchorageNum.anchorageNum <= anchoragesAmount;
                                               wrapperAnchorageNum.anchorageNum++) {
      anchoragesView.getTextArea()
                    .append(" ----- ANCLAJE #" + wrapperAnchorageNum.anchorageNum
                            + " -----" + System.lineSeparator());

      Main.getPlayersSets()
          .entrySet()
          .forEach(ps -> ps.getValue()
                           .stream()
                           .filter(p -> p.getAnchor() == wrapperAnchorageNum.anchorageNum)
                           .forEach(p -> {
                             anchoragesView.getTextArea()
                                           .append(" " + wrapperCounter.counter + ". "
                                                   + p.getName() + System.lineSeparator());
                             wrapperCounter.counter++;
                           }));

      if (wrapperAnchorageNum.anchorageNum != anchoragesAmount) {
        anchoragesView.getTextArea()
                      .append(System.lineSeparator());
      }

      wrapperCounter.counter = 1;
    }
  }

  /**
   * Conmuta la habilitación de los botones del panel derecho
   * y las casillas del panel izquierdo de la ventana.
   */
  private static void toggleButtons() {
    if (anchoragesAmount > 0 && anchoragesAmount < 2) {
      anchoragesView.getFinishButton()
                    .setEnabled(true);
      anchoragesView.getDeleteAnchorageButton()
                    .setEnabled(false);
      anchoragesView.getDeleteLastAnchorageButton()
                    .setEnabled(true);
      anchoragesView.getClearAnchoragesButton()
                    .setEnabled(true);
    } else if (anchoragesAmount >= 2) {
      anchoragesView.getDeleteAnchorageButton()
                    .setEnabled(true);
      anchoragesView.getDeleteLastAnchorageButton()
                    .setEnabled(true);
    } else {
      anchoragesView.getFinishButton()
                    .setEnabled(false);
      anchoragesView.getDeleteAnchorageButton()
                    .setEnabled(false);
      anchoragesView.getDeleteLastAnchorageButton()
                    .setEnabled(false);
      anchoragesView.getClearAnchoragesButton()
                    .setEnabled(false);
    }

    if (2 * MAX_PLAYERS_PER_ANCHORAGE - playersAnchored < 2) {
      anchoragesView.getNewAnchorageButton()
                    .setEnabled(false);
      anchoragesView.getCheckBoxesMap()
                    .values()
                    .stream()
                    .flatMap(List::stream)
                    .forEach(cb -> cb.setEnabled(!cb.isEnabled()));
    } else {
      anchoragesView.getNewAnchorageButton()
                    .setEnabled(true);
      anchoragesView.getCheckBoxesMap()
                    .values()
                    .stream()
                    .flatMap(List::stream)
                    .filter(cb -> !cb.isEnabled() && !cb.isSelected())
                    .forEach(cb -> cb.setEnabled(true));
    }
  }

  /**
   * Borra todos los anclajes que se hayan generado.
   *
   * @see armameeldoparti.frames.AnchoragesFrame#deleteAnchorage(int)
   */
  private static void clearAnchorages() {
    do {
      deleteAnchorage(anchoragesAmount);
    } while (anchoragesAmount > 0);
  }

  /**
   * Borra un anclaje específico elegido por el usuario.
   *
   * <p>Los que tenían ese anclaje, ahora tienen anclaje 0.
   * Si se desea borrar un anclaje que no sea el último, entonces
   * a los demás (desde el anclaje elegido + 1 hasta anchoragesAmount)
   * se les decrementa en 1 su número de anclaje.
   *
   * @param anchorageToDelete Número de anclaje a borrar.
   */
  private static void deleteAnchorage(int anchorageToDelete) {
    for (int j = 0; j < anchoragesView.getCheckBoxesMap()
                                      .size(); j++) {
      changeAnchorage(anchorageToDelete, 0);
    }

    if (anchorageToDelete != anchoragesAmount) {
      for (int k = anchorageToDelete + 1; k <= anchoragesAmount; k++) {
        for (int j = 0; j < anchoragesView.getCheckBoxesMap()
                                          .size(); j++) {
          changeAnchorage(k, k - 1);
        }
      }
    }

    anchoragesAmount--;
  }

  /**
   * Cambia el número de anclaje de los jugadores deseados.
   *
   * <p>Si el nuevo anclaje a aplicar es 0 (se quiere borrar un anclaje),
   * entonces las casillas de los jugadores del anclaje objetivo se vuelven
   * a poner visibles y se decrementa el número de jugadores anclados en la
   * cantidad que corresponda.
   *
   * @param target      Anclaje a reemplazar.
   * @param replacement Nuevo anclaje a aplicar.
   */
  private static void changeAnchorage(int target, int replacement) {
    Main.getPlayersSets()
        .values()
        .stream()
        .flatMap(List::stream)
        .filter(p -> p.getAnchor() == target)
        .forEach(p -> {
          p.setAnchor(replacement);

          if (replacement == 0) {
            anchoragesView.getCheckBoxesMap()
                          .values()
                          .stream()
                          .flatMap(List::stream)
                          .filter(cb -> cb.getText()
                                          .equals(p.getName()))
                          .forEach(cb -> {
                            cb.setVisible(true);
                            playersAnchored--;
                          });
          }
        });
  }

  /**
   * Crea una nueva ventana de ingreso de puntuaciones si se requiere, o
   * una nueva ventana de muestra de resultados.
   *
   * <p>Aquellas casillas que quedaron seleccionadas cuyos jugadores no
   * fueron anclados, se deseleccionan.
   */
  private static void finish() {
    anchoragesView.getCheckBoxesMap()
                  .values()
                  .stream()
                  .flatMap(List::stream)
                  .filter(cb -> cb.isSelected() && cb.isVisible())
                  .forEach(cb -> cb.setSelected(false));

    if (Main.getDistribution() == Main.BY_SKILLS_MIX) {
      // Distribución por puntuaciones
      SkillsInputController.showSkillsInputView();
    } else {
      // Distribución aleatoria
      ResultsController.setUp();
      ResultsController.showResultsView();
    }

    AnchoragesController.hideAnchoragesView();
  }

  /**
   * Revisa si la cantidad de jugadores anclados es al menos 2
   * y no más de MAX_PLAYERS_PER_ANCHORAGE.
   *
   * @param playersToAnchorAmount Cantidad de jugadores que se intenta anclar.
   *
   * @return Si la cantidad de jugadores anclados es al menos 2
   *         y no más de MAX_PLAYERS_PER_ANCHORAGE.
   */
  private static boolean validChecksAmount(int playersToAnchorAmount) {
    return playersToAnchorAmount <= MAX_PLAYERS_PER_ANCHORAGE && playersToAnchorAmount >= 2;
  }

  /**
   * Revisa si el anclaje no contiene más de la mitad de jugadores de algún conjunto.
   *
   * @return Si el anclaje no contiene más de la mitad de jugadores de algún conjunto.
   */
  private static boolean validCheckedPlayersPerPosition() {
    return anchoragesView.getCheckBoxesMap()
                         .values()
                         .stream()
                         .noneMatch(cbs -> cbs.stream()
                                              .filter(JCheckBox::isSelected)
                                              .count() > cbs.size() / 2);
  }

  /**
   * Revisa si la cantidad de jugadores anclados en total no supera el máximo permitido.
   *
   * @param playersToAnchorAmount Cantidad de jugadores que se intenta anclar.
   *
   * @return Si la cantidad de jugadores anclados en total no supera el máximo permitido.
   */
  private static boolean validAnchoredPlayersAmount(int playersToAnchorAmount) {
    return playersAnchored + playersToAnchorAmount <= 2 * MAX_PLAYERS_PER_ANCHORAGE;
  }

  /**
   * Elimina la ventana y la reinstancia para que la próxima vez que se
   * la requiera, tenga toda su información por defecto.
   */
  private static void disposeAnchoragesView() {
    anchoragesView.dispose();
    anchoragesView = new AnchoragesView();
  }

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Obtiene la posición correspondiente al conjunto de casillas
   * recibido por parámetro.
   *
   * @param cbSet Conjunto de casillas de las cuales se quiere obtener
   *              su posición asociada.
   *
   * @return Posición asociada al conjunto de casillas.
   */
  private static Position getCorrespondingPosition(List<JCheckBox> cbSet) {
    return (Position) anchoragesView.getCheckBoxesMap()
                                    .entrySet()
                                    .stream()
                                    .filter(e -> e.getValue()
                                                  .equals(cbSet))
                                    .map(Map.Entry::getKey)
                                    .toArray()[0];
  }

  // ---------------------------------------- Setters -------------------------------------------

  /**
   * Aplica el número de anclaje correspondiente a cada jugador.
   * Luego, deselecciona sus casillas y las hace invisibles para
   * evitar que dos o más anclajes contengan uno o más jugadores iguales.
   *
   * @param cbSet Arreglo de casillas a recorrer.
   */
  private static void setAnchorages(List<JCheckBox> cbSet) {
    Main.getPlayersSets()
        .get(getCorrespondingPosition(cbSet))
        .stream()
        .filter(p -> cbSet.stream()
                          .filter(JCheckBox::isSelected)
                          .anyMatch(cb -> cb.getText()
                                            .equals(p.getName())))
        .forEach(p -> p.setAnchor(anchoragesAmount));

    cbSet.stream()
         .filter(JCheckBox::isSelected)
         .forEach(cb -> {
           cb.setVisible(false);
           cb.setSelected(false);
         });
  }
}