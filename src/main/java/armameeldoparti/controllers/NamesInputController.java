package armameeldoparti.controllers;

import armameeldoparti.models.Player;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.views.NamesInputView;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.naming.InvalidNameException;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * Names input view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class NamesInputController extends Controller<NamesInputView> {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the names input view controller.
   *
   * @param namesInputView View to control.
   */
  public NamesInputController(NamesInputView namesInputView) {
    super(namesInputView);
    setUpListeners();
    setUpInitialState();
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Resets the combobox to the initial state and gives it the view focus.
   */
  public void resetComboBox() {
    JComboBox<String> comboBox = view.getComboBox();

    comboBox.setSelectedIndex(0);
    comboBox.requestFocusInWindow();
  }

  /**
   * 'Back' button event handler.
   *
   * <p>Resets the controlled view to its default values, turns the anchorages flags to false, makes the controlled view invisible
   * and shows the main menu view.
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
   * <p>Asks the user for the players distribution method, makes the controlled view invisible and shows the corresponding
   * following view.
   *
   * @param parentComponent Graphical component where the dialogs associated with the event should be displayed.
   */
  public void mixButtonEvent(Component parentComponent) {
    hideView();

    CommonFields.setDistribution(view.getRadioButtonRandom()
                                     .isSelected() ? Constants.MIX_RANDOM
                                                   : Constants.MIX_BY_SKILL_POINTS);

    if (CommonFields.isAnchoragesEnabled()) {
      ((AnchoragesController) CommonFunctions.getController(ProgramView.ANCHORAGES)).updateCheckboxesText();

      CommonFunctions.getController(ProgramView.ANCHORAGES)
                     .showView();
    } else if (CommonFields.getDistribution() == Constants.MIX_RANDOM) {
      // Random distribution
      ((ResultsController) CommonFunctions.getController(ProgramView.RESULTS)).setUp();

      CommonFunctions.getController(ProgramView.RESULTS)
                     .showView();
    } else {
      // By skill points distribution
      ((SkillPointsInputController) CommonFunctions.getController(ProgramView.SKILL_POINTS)).updateNameLabels();

      CommonFunctions.getController(ProgramView.SKILL_POINTS)
                     .showView();
    }
  }

  /**
   * Text fields input event handler.
   *
   * <p>Validates the user input with a regular expression that checks if the string contains only latin characters from A to Z
   * including Ñ, uppercase or lowercase, with or without accent mark, with or without spaces. If the input is not valid or
   * already exists, the program asks for a new input.
   *
   * <p>If the input is valid, it will be applied as a player name in the players set corresponding to the combobox selected
   * option.
   *
   * @param playerIndex The index of the player which name will be the text filed input.
   * @param playersSet  The set of players corresponding to the selected combobox option.
   * @param text        The user input in the text field.
   *
   * @throws IllegalArgumentException When the input is an invalid string.
   * @throws InvalidNameException     When the input is an invalid name.
   */
  public void textFieldEvent(int playerIndex, List<Player> playersSet, String text)
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
    validateMixButtonEnable();
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

  /**
   * Radio buttons click event handler.
   *
   * <p>Since there can be only one distribution method at a time, if one radio button is selected, the other is unselected
   * automatically. Then, if the conditions are met, the mix button is enabled.
   *
   * @param e Radio button click event.
   */
  public void radioButtonEvent(ItemEvent e) {
    if (e.getStateChange() == ItemEvent.SELECTED) {
      (e.getSource() == view.getRadioButtonRandom()
                        ? view.getRadioButtonBySkillPoints()
                        : view.getRadioButtonRandom()).setSelected(false);
    }

    validateMixButtonEnable();
  }

  // ---------------------------------------- Protected methods ---------------------------------

  @Override
  protected void resetView() {
    hideView();
    clearPlayersNames();

    view.getAnchoragesCheckbox()
        .setSelected(false);
    view.getComboBox()
        .setSelectedIndex(0);
    view.getComboBox()
        .requestFocusInWindow();
    view.getTextArea()
        .setText("");
    view.getMixButton()
        .setEnabled(false);
    view.getRadioButtonRandom()
        .setSelected(false);
    view.getRadioButtonBySkillPoints()
        .setSelected(false);

    updateTextFields(view.getComboBox()
                         .getItemAt(0));
  }

  @Override
  protected void setUpInitialState() {
    view.getMixButton()
        .setEnabled(false);
  }

  @Override
  protected void setUpListeners() {
    view.getMixButton()
        .addActionListener(e -> mixButtonEvent(view));

    view.getBackButton()
        .addActionListener(e -> backButtonEvent());

    view.getRadioButtonRandom()
        .addItemListener(this::radioButtonEvent);

    view.getRadioButtonBySkillPoints()
        .addItemListener(this::radioButtonEvent);

    view.getComboBox()
        .addActionListener(e -> comboBoxEvent((String) Objects.requireNonNull(((JComboBox<?>) e.getSource()).getSelectedItem())));

    view.getAnchoragesCheckbox()
        .addActionListener(e -> CommonFields.setAnchoragesEnabled(!CommonFields.isAnchoragesEnabled()));


    view.getTextFieldsMap()
        .forEach((player, textFieldsSet) ->
          textFieldsSet.forEach(textField ->
            textField.addActionListener(e -> {
                /*
                 * If the entered text is both a valid string and name, it will be applied to the corresponding player.
                 * If not, an error message will be shown and the text field will be reset to the player name.
                 */
                try {
                  textFieldEvent(textFieldsSet.indexOf(textField),
                                 CommonFields.getPlayersSets()
                                             .get(player),
                                 textField.getText());
                } catch (IllegalArgumentException | InvalidNameException ex) {
                  CommonFunctions.showErrorMessage(
                      ex instanceof IllegalArgumentException ? Constants.MSG_ERROR_INVALID_STRING
                                                             : Constants.MSG_ERROR_INVALID_NAME,
                      CommonFunctions.getComponentFromEvent(e)
                  );

                  textField.setText(CommonFields.getPlayersSets()
                                                .get(player)
                                                .get(textFieldsSet.indexOf(textField))
                                                .getName());
                }
              }
            )
          )
        );
  }

  /**
   * Makes the controlled view visible.
   *
   * <p>Updates the view state according to the combobox initial state, and makes it visible.
   */
  @Override
  protected void showView() {
    updateTextFields(
      Objects.requireNonNull(
        view.getComboBox()
            .getSelectedItem(),
        Constants.MSG_ERROR_NULL_RESOURCE
      ).toString()
    );
    centerView();
    resetComboBox();

    view.setVisible(true);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * The mix button is enabled only when every condition needed to distribute the players is met.
   *
   * @see #readyToDistribute()
   */
  private void validateMixButtonEnable() {
    view.getMixButton()
        .setEnabled(readyToDistribute());
  }

  /**
   * Updates the text displayed in the read-only text area.
   *
   * <p>The players names are shown in the order they are positioned in their respective list. The order is the same of the
   * positions enum.
   */
  private void updateTextArea() {
    var wrapperCounter = new Object() {
      private int counter;
    };

    view.getTextArea()
        .setText("");

    CommonFields.getPlayersSets()
                .forEach(
                  (key, value) -> value.stream()
                                       .filter(player -> !player.getName()
                                                                .equals(""))
                                       .forEach(
                                         player -> {
                                           if (wrapperCounter.counter != 0
                                               && Constants.PLAYERS_PER_TEAM * 2 - wrapperCounter.counter != 0) {
                                             view.getTextArea()
                                                 .append(System.lineSeparator());
                                           }

                                           wrapperCounter.counter++;

                                           view.getTextArea()
                                               .append(wrapperCounter.counter + " - " + player.getName());
                                         }
                                       )
                );
  }

  /**
   * Toggles the text fields visibility.
   *
   * @param selectedOption Combobox selected option.
   */
  private void updateTextFields(String selectedOption) {
    JPanel leftTopPanel = view.getLeftTopPanel();

    // Removes the text fields from the view's top left panel
    view.getTextFieldsMap()
        .values()
        .stream()
        .flatMap(Collection::stream)
        .filter(textField -> textField.getParent() == leftTopPanel)
        .forEach(leftTopPanel::remove);

    view.getTextFieldsMap()
        .get(CommonFunctions.getCorrespondingPosition(CommonFields.getPositionsMap(), selectedOption.toUpperCase()))
        .forEach(textField -> leftTopPanel.add(textField, Constants.MIG_LAYOUT_GROWX));

    leftTopPanel.revalidate();
    leftTopPanel.repaint();
  }

  /**
   * Clears the players names and text fields.
   */
  private void clearPlayersNames() {
    view.getTextFieldsMap()
        .values()
        .stream()
        .flatMap(List::stream)
        .forEach(textField -> textField.setText(null));

    CommonFields.getPlayersSets()
                .values()
                .stream()
                .flatMap(List::stream)
                .forEach(player -> player.setName(""));
  }

  /**
   * Checks if there is already a player with the specified name.
   *
   * @param name Name to validate.
   *
   * @return Whether there is already a player with the specified name or not.
   */
  private boolean alreadyExists(String name) {
    return CommonFields.getPlayersSets()
                       .values()
                       .stream()
                       .flatMap(Collection::stream)
                       .anyMatch(player -> player.getName()
                                                 .equals(name));
  }


  /**
   * Checks if every player has a valid non-empty name assigned and the players distribution method has been chosen.
   *
   * @return Whether every condition needed to distribute the players is met or not.
   */
  private boolean readyToDistribute() {
    return !alreadyExists("") && distributionMethodHasBeenChosen();
  }

  /**
   * Checks if any players distribution method has been chosen.
   *
   * @return Whether the user has chosen a players distribution method or not.
   */
  private boolean distributionMethodHasBeenChosen() {
    return view.getRadioButtonRandom()
               .isSelected()
           || view.getRadioButtonBySkillPoints()
                  .isSelected();
  }

  /**
   * Checks if the given string matches the string validation regex.
   *
   * @param string The string to validate.
   *
   * @return Whether the string matches the string validation regex or not.
   */
  private boolean validString(String string) {
    return Pattern.matches(Constants.REGEX_NAMES_VALIDATION, string);
  }

  /**
   * Checks if the given name has at most MAX_NAME_LEN characters, is not empty or blank and if there isn't already a player with
   * that name.
   *
   * @param name The name to validate.
   *
   * @return If the given name is valid according to the specified conditions.
   */
  private boolean validName(String name) {
    return name.length() <= Constants.MAX_NAME_LEN
           && !(name.isBlank() || name.isEmpty() || alreadyExists(name));
  }
}