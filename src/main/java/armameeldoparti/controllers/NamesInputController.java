package armameeldoparti.controllers;

import armameeldoparti.models.Player;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.views.NamesInputView;
import java.awt.Component;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.naming.InvalidNameException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jetbrains.annotations.NotNull;

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
    Constants.MIX_OPTION_RANDOM,
    Constants.MIX_OPTION_BY_SKILL
  };

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the names input view controller.
   *
   * @param namesInputView View to control.
   */
  public NamesInputController(@NotNull NamesInputView namesInputView) {
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
    updateTextFields(Objects.requireNonNull(
        ((NamesInputView) getView()).getComboBox()
                                    .getSelectedItem(),
        Constants.MSG_ERROR_NULL_RESOURCE
      ).toString()
    );
    centerView();
    getView().setVisible(true);
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
    ((NamesInputView) getView()).getMixButton()
                                .setEnabled(false);

    updateTextFields(((NamesInputView) getView()).getComboBox()
                                                 .getItemAt(0));
  }

  /**
   * 'Back' button event handler.
   *
   * <p>Resets the controlled view to its default values,
   * turns the anchorages flags to false, makes the
   * controlled view invisible and shows the main menu view.
   */
  public void backButtonEvent() {
    resetView();

    CommonFields.setAnchoragesEnabled(false);

    CommonFunctions.getController(ProgramView.MAIN_MENU)
                   .showView();
  }

  /**
   * 'Mix' button event handler.
   *
   * <p>Asks the user for the players distribution method, makes
   * the controlled view invisible and shows the corresponding
   * following view.
   *
   * @param parentComponent Graphical component where the dialogs associated
   *                        with the event should be displayed.
   */
  public void mixButtonEvent(Component parentComponent) {
    int distribution = JOptionPane.showOptionDialog(
        parentComponent,
        "Seleccione el criterio de distribución de jugadores",
        "Antes de continuar...", JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE, Constants.ICON_SCALED,
        OPTIONS_MIX, OPTIONS_MIX[0]
    );

    if (distribution == JOptionPane.CLOSED_OPTION) {
      return;
    }

    hideView();

    CommonFields.setDistribution(distribution);

    if (CommonFields.isAnchoragesEnabled()) {
      ((AnchoragesController) CommonFunctions.getController(ProgramView.ANCHORAGES))
      .updateCheckBoxesText();

      CommonFunctions.getController(ProgramView.ANCHORAGES)
                     .showView();
    } else if (CommonFields.getDistribution() == Constants.MIX_RANDOM) {
      // Random distribution
      ((ResultsController) CommonFunctions.getController(ProgramView.RESULTS)).setUp();

      CommonFunctions.getController(ProgramView.RESULTS)
                     .showView();
    } else {
      // By skill points distribution
      ((SkillPointsInputController) CommonFunctions.getController(ProgramView.SKILL_POINTS))
      .updateNameLabels();

      CommonFunctions.getController(ProgramView.SKILL_POINTS)
                     .showView();
    }
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
   * @param playerIndex The index of the player which name will be the text filed input.
   * @param playersSet  The set of players corresponding to the selected combobox option.
   * @param text        The user input in the text field.
   *
   * @throws IllegalArgumentException When the input is an invalid string.
   * @throws InvalidNameException     When the input is an invalid name.
   */
  public void textFieldEvent(int playerIndex,
                             @NotNull List<Player> playersSet,
                             @NotNull String text)
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
  public void comboBoxEvent(@NotNull String selectedOption) {
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

    CommonFields.getPlayersSets()
                .forEach((key, value) -> value.stream()
                                              .filter(p -> !p.getName()
                                                             .equals(""))
                                              .forEach(p -> {
                                                if (wrapperCounter.counter != 0
                                                    && Constants.PLAYERS_PER_TEAM * 2
                                                       - wrapperCounter.counter != 0) {
                                                  ((NamesInputView) getView())
                                                      .getTextArea()
                                                      .append(System.lineSeparator());
                                                }

                                                wrapperCounter.counter++;

                                                ((NamesInputView) getView())
                                                    .getTextArea()
                                                    .append(wrapperCounter.counter
                                                            + " - " + p.getName());
                                              }));
  }

  /**
   * Toggles the text fields visibility.
   *
   * @param selectedOption Combobox selected option.
   */
  private void updateTextFields(@NotNull String selectedOption) {
    JPanel leftPanel = ((NamesInputView) getView()).getLeftPanel();

    // Removes the text fields from the view's left panel.
    ((NamesInputView) getView()).getTextFieldsMap()
                                .values()
                                .stream()
                                .flatMap(Collection::stream)
                                .filter(tf -> tf.getParent() == leftPanel)
                                .forEach(leftPanel::remove);

    for (int i = 0; i < NamesInputView.getOPTIONS_COMBOBOX()
                                      .length; i++) {
      if (selectedOption.equals(NamesInputView.getOPTIONS_COMBOBOX()[i])) {
        ((NamesInputView) getView()).getTextFieldsMap()
                                    .get(CommonFunctions.getCorrespondingPosition(
                                          CommonFields.getPositionsMap(),
                                          selectedOption.toUpperCase()))
                                    .forEach(tf -> leftPanel.add(tf, "growx"));

        break;
      }
    }

    leftPanel.revalidate();
    leftPanel.repaint();
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

    CommonFields.getPlayersSets()
                .values()
                .stream()
                .flatMap(List::stream)
                .forEach(p -> p.setName(""));
  }

  /**
   * Checks if there is already a player with the specified name.
   *
   * @param name Name to validate.
   *
   * @return Whether there is already a player with the specified name or not.
   */
  private boolean alreadyExists(@NotNull String name) {
    return CommonFields.getPlayersSets()
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
  private boolean validString(@NotNull String string) {
    return Pattern.matches(Constants.REGEX_NAMES_VALIDATION, string);
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
  private boolean validName(@NotNull String name) {
    return name.length() <= Constants.MAX_NAME_LEN
           && !(name.isBlank() || name.isEmpty() || alreadyExists(name));
  }
}