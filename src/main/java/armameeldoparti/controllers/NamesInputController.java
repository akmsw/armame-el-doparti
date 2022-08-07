package armameeldoparti.controllers;

import armameeldoparti.Main;
import armameeldoparti.models.Player;
import armameeldoparti.views.NamesInputView;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import javax.naming.InvalidNameException;
import javax.swing.JOptionPane;

/**
 * Names input view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 26/07/2022
 */
public class NamesInputController extends Controller {

  // ---------------------------------------- Private constants ---------------------------------

  /**
   * Possible players distribution methods.
   */
  private static final String[] OPTIONS_MIX = {
    "Aleatoriamente",
    "Por puntuaciones"
  };

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the names input view controller.
   *
   * @param namesInputView View to control.
   */
  public NamesInputController(NamesInputView namesInputView) {
    super(namesInputView);
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Makes the controlled view visible.
   *
   * <p>Updates the view state according to the combobox
   * initial state, and makes it visible.
   */
  @Override
  public void showView() {
    updateTextFields(((NamesInputView) getView()).getComboBox()
                                                 .getSelectedItem()
                                                 .toString());

    ((NamesInputView) getView()).setVisible(true);
  }

  /**
   * Resets the controlled view to its default values and
   * makes it invisible.
   */
  @Override
  public void resetView() {
    hideView();
    clearPlayersNames();

    ((NamesInputView) getView()).getAnchoragesCheckBox()
                                .setSelected(false);

    ((NamesInputView) getView()).getComboBox()
                                .setSelectedIndex(0);

    ((NamesInputView) getView()).getTextArea()
                                .setText("");

    updateTextFields(((NamesInputView) getView()).getComboBox()
                                                 .getItemAt(0));
  }

  /**
   * 'Back' button event handler.
   *
   * <p>Resets the controlled view to its default values, makes
   * it invisible and shows the main menu view.
   */
  public void backButtonEvent() {
    resetView();

    Main.getMainMenuController()
        .showView();
  }

  /**
   * 'Mix' button event handler.
   *
   * <p>Asks the user for the players distribution method, makes
   * the controlled view invisible and shows the corresponding
   * following view.
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
      // Random distribution
      Main.getResultsController()
          .setUp();

      Main.getResultsController()
          .showView();
    } else {
      // By skill points distribution
      Main.getSkillPointsInputController()
          .updateNameLabels();

      Main.getSkillPointsInputController()
          .showView();
    }

    hideView();
  }

  /**
   * Text fields input event handler.
   *
   * <p>Validates the user input with a regular expression that checks if the string
   * contains only latin characters from A to Z including Ñ, uppercase or lowercase,
   * with or without accent mark, with or without spaces.
   * If the input is not valid or already exists, the program asks for a new input.
   *
   * <p>If the input is valid, it will be applied as a player name in the players set
   * corresponding to the combobox selected option.
   *
   * @param playerIndex  The index of the player which name will be the text filed input.
   * @param playersSet   The set of players corresponding to the selected combobox option.
   * @param text         The user input in the text field.
   *
   * @throws IllegalArgumentException When the input is an invalid string.
   * @throws InvalidNameException     When the input is an invalid name.
   */
  public void textFieldEvent(int playerIndex,
                             List<Player> playersSet,
                             String text)
                             throws IllegalArgumentException,
                                    InvalidNameException {
    if (!validString(text)) {
      throw new IllegalArgumentException();
    }

    String name = text.trim()
                      .toUpperCase()
                      .replace(" ", "_");

    if (!validName(name)) {
      throw new InvalidNameException();
    }

    playersSet.get(playerIndex)
              .setName(name);

    updateTextArea();

    // The mix button is enabled only when every player has a name
    ((NamesInputView) getView()).getMixButton()
                                .setEnabled(!alreadyExists(""));
  }

  /**
   * Combobox option change event handler.
   *
   * <p>Updates the shown text field according to the selected combobox option.
   *
   * @param selectedOption Combobox selected option.
   */
  public void comboBoxEvent(String selectedOption) {
    updateTextFields(selectedOption);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Updates the text displayed in the read-only text area.
   *
   * <p>The players names are shown in the order they are positioned in their respective list.
   * The order is the same of the positions enum.
   */
  private void updateTextArea() {
    var wrapperCounter = new Object() {
      private int counter;
    };

    ((NamesInputView) getView()).getTextArea()
                                .setText("");

    Main.getPlayersSets()
        .entrySet()
        .forEach(ps -> ps.getValue()
                         .stream()
                         .filter(p -> !p.getName()
                                        .equals(""))
                         .forEach(p -> {
                           if (wrapperCounter.counter != 0
                               && Main.PLAYERS_PER_TEAM * 2 - wrapperCounter.counter != 0) {
                             ((NamesInputView) getView()).getTextArea()
                                                         .append(System.lineSeparator());
                           }

                           wrapperCounter.counter++;

                           ((NamesInputView) getView()).getTextArea()
                                                       .append(wrapperCounter.counter
                                                               + " - " + p.getName());
                         }));
  }

  /**
   * Toggles the text fields visibility.
   *
   * @param selectedOption Combobox selected option.
   */
  private void updateTextFields(String selectedOption) {
    clearLeftPanel();

    for (int i = 0; i < ((NamesInputView) getView()).getComboBoxOptions()
                                                    .length; i++) {
      if (selectedOption.equals(((NamesInputView) getView()).getComboBoxOptions()[i])) {
        ((NamesInputView) getView()).getTextFieldsMap()
                                    .get(Main.getCorrespondingPosition(Main.getPositionsMap(),
                                                                      selectedOption.toUpperCase()))
                                    .forEach(tf -> ((NamesInputView) getView()).getLeftPanel()
                                                                               .add(tf, "growx"));
        break;
      }
    }

    ((NamesInputView) getView()).getLeftPanel()
                                .revalidate();

    ((NamesInputView) getView()).getLeftPanel()
                                .repaint();
  }

  /**
   * Clears the players names and text fields.
   */
  private void clearPlayersNames() {
    ((NamesInputView) getView()).getTextFieldsMap()
                                .values()
                                .stream()
                                .flatMap(List::stream)
                                .forEach(tf -> tf.setText(null));

    for (List<Player> playersSet : Main.getPlayersSets()
                                       .values()) {
      for (Player player : playersSet) {
        player.setName("");
      }
    }
  }

  /**
   * Removes the text fields from the view's left panel.
   */
  private void clearLeftPanel() {
    ((NamesInputView) getView()).getTextFieldsMap()
                                .values()
                                .stream()
                                .flatMap(Collection::stream)
                                .filter(tf -> tf.getParent()
                                              == ((NamesInputView) getView()).getLeftPanel())
                                .forEach(tf -> ((NamesInputView) getView()).getLeftPanel()
                                                                           .remove(tf));
  }

  /**
   * Checks if there is already a player with the specified name.
   *
   * @param name Name to validate.
   *
   * @return Whether there is already a player with the specified name or not.
   */
  private boolean alreadyExists(String name) {
    return Main.getPlayersSets()
               .values()
               .stream()
               .flatMap(Collection::stream)
               .anyMatch(p -> p.getName()
                               .equals(name));
  }

  /**
   * Checks if the given string matches the string validation regex.
   *
   * @param string The string to validate.
   *
   * @return Whether the string matches the string validation regex or not.
   */
  private boolean validString(String string) {
    return Pattern.matches(Main.NAMES_VALIDATION_REGEX, string);
  }

  /**
   * Checks if the given name has at most MAX_NAME_LEN characters,
   * is not empty or blank and if there isn't already a player
   * with that name.
   *
   * @param name The name to validate.
   *
   * @return If the given name is valid according to
   *         the specified conditions.
   */
  private boolean validName(String name) {
    return name.length() <= Main.MAX_NAME_LEN && !name.isBlank()
           && !name.isEmpty() && !alreadyExists(name);
  }
}