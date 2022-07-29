package armameeldoparti.controllers;

import armameeldoparti.interfaces.Controller;
import armameeldoparti.models.Player;
import armameeldoparti.utils.Main;
import armameeldoparti.views.NamesInputView;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Clase correspondiente al controlador de la ventana de ingreso de nombres.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 26/07/2022
 */
public class NamesInputController implements Controller {

  // ---------------------------------------- Campos privados -----------------------------------

  private NamesInputView namesInputView;

  /**
   * Criterios de distribución de jugadores.
   */
  private static final String[] OPTIONS_MIX = {
    "Aleatoriamente",
    "Por puntuaciones"
  };

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye el controlador para la vista de ingreso de nombres.
   *
   * @param namesInputView Vista a controlar.
   */
  public NamesInputController(NamesInputView namesInputView) {
    this.namesInputView = namesInputView;
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  /**
   * Hace visible la ventana controlada.
   *
   * <p>Actualiza el estado de la ventana de ingreso de nombres para
   * coincidir con el estado inicial de la lista desplegable, y
   * la hace visible.
   */
  @Override
  public void showView() {
    updateTextFields(namesInputView.getComboBox()
                                   .getSelectedItem()
                                   .toString());

    namesInputView.setVisible(true);
  }

  /**
   * Hace invisible la ventana controlada.
   */
  @Override
  public void hideView() {
    namesInputView.setVisible(false);
    Controller.centerView(namesInputView);
  }

  /**
   * Reinicia la ventana controlada a sus valores por defecto.
   */
  @Override
  public void resetView() {
    namesInputView.dispose();
    namesInputView = new NamesInputView();
  }

  /**
   * Controlador para la pulsación del botón de retorno.
   *
   * <p>Elimina y reinstancia la ventana de ingreso de nombres,
   * y hace visible la ventana del menú principal.
   */
  public void backButtonEvent() {
    resetView();

    Main.getMainMenuController()
        .showView();
  }

  /**
   * Controlador para la pulsación del botón de selección de distribución.
   *
   * <p>Solicita al usuario el criterio de distribución de jugadores y hace
   * visible la ventana que corresponda dependiendo de lo elegido.
   */
  public void mixButtonEvent() {
    int distribution = JOptionPane.showOptionDialog(
        null, "Seleccione el criterio de distribución de jugadores",
        "Antes de continuar...", 2, JOptionPane.QUESTION_MESSAGE,
        Main.SCALED_ICON, OPTIONS_MIX, OPTIONS_MIX[0]
    );

    if (distribution == JOptionPane.CLOSED_OPTION) {
      return;
    }

    Main.setDistribution(distribution);

    if (Main.thereAreAnchorages()) {
      Main.getAnchoragesController()
          .updateCheckBoxesText();

      Main.getAnchoragesController()
          .showView();
    } else if (Main.getDistribution() == Main.RANDOM_MIX) {
      // Distribución aleatoria
      Main.getResultsController()
          .setUp();

      Main.getResultsController()
          .showView();
    } else {
      // Distribución por puntuaciones
      Main.getSkillsInputController()
          .updateNameLabels();

      Main.getSkillsInputController()
          .showView();
    }

    hideView();
  }

  /**
   * Controlador para la escritura en campos de texto para ingreso de nombres.
   *
   * <p>Valida lo escrito por el usuario con una expresión regular para letras en
   * alfabeto latino de la A a la Z, mayúsculas o minúsculas, con tildes o sin tildes,
   * con o sin espacios. Si el nombre es inválido o ya existe, se solicita un reingreso.
   *
   * <p>Si el nombre ingresado es válido, se lo aplica a un jugador de la posición
   * mostrada en la lista desplegable.
   */
  public void textFieldEvent(List<JTextField> textFieldSet, List<Player> playersSet,
                             JTextField tf, JTextField source) {
    if (!(Pattern.matches(Main.NAMES_VALIDATION_REGEX, tf.getText()))) {
      JOptionPane.showMessageDialog(null,
                                    "El nombre del jugador debe estar formado por letras"
                                    + " de la A a la Z", "¡Error!",
                                    JOptionPane.ERROR_MESSAGE, null);
      tf.setText(null);
    } else {
      String name = tf.getText()
                      .trim()
                      .toUpperCase()
                      .replace(" ", "_");

      if ((name.length() > Main.MAX_NAME_LEN) || name.isBlank()
          || name.isEmpty() || alreadyExists(name)) {
        JOptionPane.showMessageDialog(null,
                                      "El nombre del jugador no puede estar vacío,"
                                      + " tener más de " + Main.MAX_NAME_LEN
                                      + " caracteres, o estar repetido",
                                      "¡Error!", JOptionPane.ERROR_MESSAGE, null);
        tf.setText(null);
      } else {
        playersSet.get(textFieldSet.indexOf(source))
                  .setName(name);

        updateTextArea();

        // Se habilita el botón de mezcla sólo cuando todos los jugadores tengan nombre
        namesInputView.getMixButton()
                      .setEnabled(!alreadyExists(""));
      }
    }
  }

  /**
   * Controlador para la selección de opciones en la lista desplegable
   *
   * <p>Actualiza los campos de texto para ingreso de nombres en base a la
   * opción elegida de la lista desplegada.
   *
   * @param selectedOption Opción elegida de la lista desplegable.
   */
  public void comboBoxEvent(String selectedOption) {
    updateTextFields(selectedOption);
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Actualiza el texto mostrado en el área de sólo lectura.
   *
   * <p>Se muestran los jugadores ingresados en el orden en el que estén posicionados en sus
   * respectivos arreglos.
   *
   * <p>El orden en el que se muestran es el mismo que el del enum de posiciones.
   */
  private void updateTextArea() {
    var wrapperCounter = new Object() {
      private int counter;
    };

    namesInputView.getTextArea()
                  .setText(null);

    Main.getPlayersSets()
        .entrySet()
        .forEach(ps -> ps.getValue()
                         .stream()
                         .filter(p -> !p.getName()
                                        .equals(""))
                         .forEach(p -> {
                           if (wrapperCounter.counter != 0
                               && Main.PLAYERS_PER_TEAM * 2 - wrapperCounter.counter != 0) {
                             namesInputView.getTextArea()
                                           .append(System.lineSeparator());
                           }

                           wrapperCounter.counter++;

                           namesInputView.getTextArea()
                                         .append(wrapperCounter.counter + " - " + p.getName());
                         }));
  }

  /**
   * Conmuta la visibilidad de los campos de texto de ingreso de jugadores.
   *
   * @param text Ítem seleccionado de la lista desplegable.
   */
  private void updateTextFields(String text) {
    clearLeftPanel();

    for (int i = 0; i < namesInputView.getComboBoxOptions().length; i++) {
      if (text.equals(namesInputView.getComboBoxOptions()[i])) {
        namesInputView.getTextFields()
                      .get(i)
                      .forEach(tf -> namesInputView.getLeftPanel()
                                                   .add(tf, "growx"));
        break;
      }
    }

    namesInputView.getLeftPanel()
                  .revalidate();
    namesInputView.getLeftPanel()
                  .repaint();
  }

  /**
   * Quita los campos de texto del panel izquierdo de la ventana.
   */
  private void clearLeftPanel() {
    namesInputView.getTextFields()
                  .stream()
                  .flatMap(Collection::stream)
                  .filter(tf -> tf.getParent() == namesInputView.getLeftPanel())
                  .forEach(tf -> namesInputView.getLeftPanel()
                                               .remove(tf));
  }

  /**
   * Revisa si ya existe algún jugador con el nombre recibido por parámetro.
   *
   * @param name Nombre a validar.
   *
   * @return Si ya existe algún jugador con el nombre recibido por parámetro.
   */
  private boolean alreadyExists(String name) {
    return Main.getPlayersSets()
               .values()
               .stream()
               .flatMap(Collection::stream)
               .anyMatch(p -> p.getName()
                               .equals(name));
  }
}