package armameeldoparti.controllers;

import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.views.AnchoragesView;
import java.awt.Component;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * Anchorages view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class AnchoragesController extends Controller<AnchoragesView> {

  // ---------------------------------------- Private fields ------------------------------------

  private int anchoragesAmount;
  private int anchoredPlayersAmount;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the anchorages view controller.
   *
   * @param anchoragesView View to control.
   */
  public AnchoragesController(AnchoragesView anchoragesView) {
    super(anchoragesView);
    setUpListeners();
    setUpInitialState();
    toggleButtons();
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Updates the checkboxes text with the players names.
   */
  public void updateCheckboxesText() {
    for (Position position : Position.values()) {
      int positionSetSize = CommonFields.getPlayersSets()
                                        .get(position)
                                        .size();

      for (int checkboxIndex = 0; checkboxIndex < positionSetSize; checkboxIndex++) {
        view.getCheckboxesMap()
            .get(position)
            .get(checkboxIndex)
            .setText(CommonFields.getPlayersSets()
                                 .get(position)
                                 .get(checkboxIndex)
                                 .getName());
      }
    }

    view.pack();
  }

  /**
   * 'Finish' button event handler.
   *
   * <p>Checks if the necessary anchorages conditions are met. If so, it proceeds with the
   * distribution.
   *
   * @param parentComponent Graphical component where the dialogs associated with the event should
   *                        be displayed.
   */
  public void finishButtonEvent(Component parentComponent) {
    if (!validAnchoragesCombination()) {
      CommonFunctions.showErrorMessage("Error message", parentComponent);

      return;
    }

    finish();
  }

  /**
   * 'New anchorage' button event handler.
   *
   * <p>Checks if the necessary conditions to make a new anchorage are met. If so, it does it.
   *
   * @param parentComponent Graphical component where the dialogs associated with the event should
   *                        be displayed.
   */
  public void newAnchorageButtonEvent(Component parentComponent) {
    int playersToAnchorAmount = (int) view.getCheckboxesMap()
                                          .values()
                                          .stream()
                                          .flatMap(List::stream)
                                          .filter(JCheckBox::isSelected)
                                          .count();

    if (!validChecksAmount(playersToAnchorAmount)) {
      CommonFunctions.showErrorMessage(
          "No puede haber más de " + Constants.MAX_PLAYERS_PER_ANCHORAGE
          + " ni menos de " + Constants.MIN_PLAYERS_PER_ANCHORAGE
          + " jugadores en un mismo anclaje",
          parentComponent
      );

      return;
    }

    if (!validCheckedPlayersPerPosition()) {
      CommonFunctions.showErrorMessage(
          "No puede haber más de la mitad de jugadores  de una misma posición en un mismo anclaje",
          parentComponent
      );

      return;
    }

    if (!validAnchoredPlayersAmount(playersToAnchorAmount)) {
      CommonFunctions.showErrorMessage(
          "No puede haber más de " + Constants.MAX_ANCHORED_PLAYERS
          + " jugadores anclados en total",
          parentComponent
      );

      return;
    }

    newAnchorage();
    updateTextArea();
    toggleButtons();
  }

  /**
   * 'Delete last anchorage' button event handler.
   *
   * <p>Deletes the last anchorage made, updating the text area and the state of the buttons.
   */
  public void deleteLastAnchorageButtonEvent() {
    deleteAnchorage(anchoragesAmount);
    updateTextArea();
    toggleButtons();
  }

  /**
   * 'Delete anchorage' button event handler.
   *
   * <p>Prompts the user for the number of the anchorage to delete, and removes it, updating the
   * text area and the state of the buttons.
   *
   * @param parentComponent Graphical component where the dialogs associated with the event should
   *                        be displayed.
   */
  public void deleteAnchorageButtonEvent(Component parentComponent) {
    String[] optionsDelete = new String[anchoragesAmount];

    for (int anchorageNumber = 0; anchorageNumber < anchoragesAmount; anchorageNumber++) {
      optionsDelete[anchorageNumber] = Integer.toString(anchorageNumber + 1);
    }

    int anchorageToDelete = JOptionPane.showOptionDialog(
        parentComponent,
        "Seleccione qué anclaje desea borrar",
        "Antes de continuar...",
        JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        Constants.ICON_DIALOG, optionsDelete,
        optionsDelete[0]
    );

    if (anchorageToDelete != JOptionPane.CLOSED_OPTION) {
      deleteAnchorage(anchorageToDelete + 1);
      updateTextArea();
      toggleButtons();
    }
  }

  /**
   * 'Clear anchorages' button event handler.
   *
   * <p>Resets the controlled view to its default values.
   */
  public void clearAnchoragesButtonEvent() {
    resetView();
  }

  /**
   * 'Back' button event handler.
   *
   * <p>Makes the controlled view invisible, deletes every anchorage made, resets the controlled
   * view to its default state and shows the names input view.
   */
  public void backButtonEvent() {
    hideView();
    resetView();

    CommonFunctions.getController(ProgramView.NAMES_INPUT)
                   .showView();
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Resets the controlled view to its default values.
   *
   * <p>Clears every anchorage made, updating the text area and the state of the buttons, and the
   * checkboxes that were selected whose players were not anchored, are deselected.
   */
  @Override
  protected void resetView() {
    clearCheckboxes();
    clearAnchorages();
    updateTextArea();
    toggleButtons();
  }

  @Override
  protected void setUpInitialState() {
    anchoragesAmount = 0;
    anchoredPlayersAmount = 0;

    view.getFinishButton()
        .setEnabled(false);
  }

  @Override
  protected void setUpListeners() {
    view.getFinishButton()
        .addActionListener(e -> finishButtonEvent(CommonFunctions.getComponentFromEvent(e)));

    view.getNewAnchorageButton()
        .addActionListener(e -> newAnchorageButtonEvent(CommonFunctions.getComponentFromEvent(e)));

    view.getDeleteAnchorageButton()
        .addActionListener(
          e -> deleteAnchorageButtonEvent(CommonFunctions.getComponentFromEvent(e))
        );

    view.getDeleteLastAnchorageButton()
        .addActionListener(e -> deleteLastAnchorageButtonEvent());

    view.getClearAnchoragesButton()
        .addActionListener(e -> clearAnchoragesButtonEvent());

    view.getBackButton()
        .addActionListener(e -> backButtonEvent());
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Sets a new anchorage based on the players checked.
   *
   * @see #setAnchorages(List)
   */
  private void newAnchorage() {
    anchoragesAmount++;

    view.getCheckboxesMap()
        .values()
        .stream()
        .filter(
          cbs -> cbs.stream()
                    .anyMatch(JCheckBox::isSelected)
        )
        .forEach(this::setAnchorages);

    anchoredPlayersAmount = (int) CommonFields.getPlayersSets()
                                              .values()
                                              .stream()
                                              .flatMap(List::stream)
                                              .filter(Player::isAnchored)
                                              .count();
  }

  /**
   * Updates the text area showing the anchorages details.
   */
  private void updateTextArea() {
    view.getTextArea()
        .setText("");

    var wrapper = new Object() {
      private int anchorageNumber;
      private int counter = 1;
    };

    for (wrapper.anchorageNumber = 1;
         wrapper.anchorageNumber <= anchoragesAmount;
         wrapper.anchorageNumber++) {
      view.getTextArea()
          .append("ANCLAJE " + wrapper.anchorageNumber + System.lineSeparator());

      CommonFields.getPlayersSets()
                  .forEach((key, value) -> value.stream()
                                                .filter(
                                                  p -> p.getAnchorageNumber()
                                                       == wrapper.anchorageNumber
                                                )
                                                .forEach(
                                                  p -> {
                                                    view.getTextArea()
                                                        .append(
                                                          wrapper.counter + ". " + p.getName()
                                                          + System.lineSeparator()
                                                        );

                                                    wrapper.counter++;
                                                  }
                                                ));

      if (wrapper.anchorageNumber != anchoragesAmount) {
        view.getTextArea()
            .append(System.lineSeparator());
      }

      wrapper.counter = 1;
    }
  }

  /**
   * Toggles the buttons and checkboxes states.
   */
  private void toggleButtons() {
    view.getAnchorageButtons()
        .forEach(b -> b.setEnabled(false));

    if (anchoragesAmount == 1) {
      view.getFinishButton()
          .setEnabled(true);
      view.getDeleteLastAnchorageButton()
          .setEnabled(true);
      view.getClearAnchoragesButton()
          .setEnabled(true);
    } else if (anchoragesAmount > 1) {
      view.getAnchorageButtons()
          .forEach(b -> b.setEnabled(true));
    }

    if (Constants.MAX_ANCHORED_PLAYERS - anchoredPlayersAmount < 2) {
      view.getNewAnchorageButton()
          .setEnabled(false);
      view.getCheckboxesMap()
          .values()
          .stream()
          .flatMap(List::stream)
          .forEach(cb -> cb.setEnabled(!cb.isEnabled()));
    } else {
      view.getNewAnchorageButton()
          .setEnabled(true);
      view.getCheckboxesMap()
          .values()
          .stream()
          .flatMap(List::stream)
          .filter(cb -> !cb.isEnabled() && !cb.isSelected())
          .forEach(cb -> cb.setEnabled(true));
    }
  }

  /**
   * Clears the anchorages made (if there are any).
   *
   * @see #deleteAnchorage(int)
   */
  private void clearAnchorages() {
    while (anchoragesAmount > 0) {
      deleteAnchorage(anchoragesAmount);
    }
  }

  /**
   * Deletes a specific anchorage.
   *
   * <p>The players that have the specified anchorage, now will have anchorage number 0. If the
   * anchorage number to delete is not the last one, then the remaining players (from the chosen
   * anchor + 1 to anchoragesAmount) will have their anchorage number decreased by 1.
   *
   * @param anchorageToDelete Anchorage number to delete.
   */
  private void deleteAnchorage(int anchorageToDelete) {
    changeAnchorage(anchorageToDelete, 0);

    if (anchorageToDelete != anchoragesAmount) {
      for (int anchorageNumber = anchorageToDelete + 1;
               anchorageNumber <= anchoragesAmount;
               anchorageNumber++) {
        changeAnchorage(anchorageNumber, anchorageNumber - 1);
      }
    }

    anchoragesAmount--;
  }

  /**
   * Changes the anchorage number of certain players.
   *
   * <p>If the replacement is 0 (an anchorage must be removed), then those players will be set as
   * not-anchored, the players corresponding checkboxes will be visible and enabled again, and the
   * anchored players amount will be decreased as needed.
   *
   * @param target      Anchorage number to replace.
   * @param replacement New anchorage number to set.
   */
  private void changeAnchorage(int target, int replacement) {
    CommonFields.getPlayersSets()
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(p -> p.getAnchorageNumber() == target)
                .forEach(p -> {
                  p.setAnchorageNumber(replacement);

                  if (replacement == 0) {
                    p.setAnchored(false);

                    CommonFunctions.retrieveOptional(
                      view.getCheckboxesMap()
                          .values()
                          .stream()
                          .flatMap(List::stream)
                          .filter(cb -> cb.getText()
                                          .equals(p.getName()))
                          .findFirst()
                    ).setVisible(true);

                    anchoredPlayersAmount--;
                  }
                });
  }

  /**
   * The checkboxes that were selected whose players were not anchored, are deselected. Then, shows
   * the corresponding following view.
   */
  private void finish() {
    hideView();
    clearCheckboxes();

    if (CommonFields.getDistribution() == Constants.MIX_BY_SKILLS) {
      ((SkillPointsInputController) CommonFunctions.getController(ProgramView.SKILL_POINTS))
      .updateNameLabels();

      CommonFunctions.getController(ProgramView.SKILL_POINTS)
                     .showView();
    } else {
      ((ResultsController) CommonFunctions.getController(ProgramView.RESULTS)).setUp();

      CommonFunctions.getController(ProgramView.RESULTS)
                     .showView();
    }
  }

  /**
   * Sets the corresponding anchorage number to the selected players. Then, unchecks their
   * checkboxes and makes them invisible.
   *
   * @param cbSet Check boxes set with players checked.
   */
  private void setAnchorages(List<JCheckBox> cbSet) {
    CommonFields.getPlayersSets()
                .get(CommonFunctions.getCorrespondingPosition(view.getCheckboxesMap(), cbSet))
                .stream()
                .filter(
                  p -> cbSet.stream()
                            .filter(JCheckBox::isSelected)
                            .anyMatch(
                              cb -> cb.getText()
                                      .equals(p.getName())
                            )
                )
                .forEach(p -> {
                  p.setAnchorageNumber(anchoragesAmount);
                  p.setAnchored(true);
                });

    cbSet.stream()
         .filter(JCheckBox::isSelected)
         .forEach(cb -> {
           cb.setVisible(false);
           cb.setSelected(false);
         });
  }

  /**
   * Unchecks the remaining checked checkboxes.
   */
  private void clearCheckboxes() {
    view.getCheckboxesMap()
        .values()
        .stream()
        .flatMap(List::stream)
        .filter(cb -> cb.isSelected() && cb.isVisible())
        .forEach(cb -> cb.setSelected(false));
  }

  /**
   * Checks if the selected players amount is at least 2 and at most MAX_PLAYERS_PER_ANCHORAGE.
   *
   * @param playersToAnchorAmount Checked players to anchor.
   *
   * @return Whether the checked players amount is at least 2 and at most MAX_PLAYERS_PER_ANCHORAGE,
   *         or not.
   */
  private boolean validChecksAmount(int playersToAnchorAmount) {
    return playersToAnchorAmount <= Constants.MAX_PLAYERS_PER_ANCHORAGE
           && playersToAnchorAmount >= 2;
  }

  /**
   * Checks if more than half of any players set is selected or not.
   *
   * @return Whether more than half of any players set is checked, or not.
   */
  private boolean validCheckedPlayersPerPosition() {
    return view.getCheckboxesMap()
               .values()
               .stream()
               .noneMatch(
                 cbs -> cbs.stream()
                           .filter(JCheckBox::isSelected)
                           .count() > cbs.size() / 2
               );
  }

  /**
   * Checks if the selected players amount is at most the maximum allowed per anchorage.
   *
   * @param playersToAnchorAmount Checked players amount.
   *
   * @return Whether the selected players amount is at most the maximum allowed per anchorage or
   *         not.
   */
  private boolean validAnchoredPlayersAmount(int playersToAnchorAmount) {
    return anchoredPlayersAmount + playersToAnchorAmount <= Constants.MAX_ANCHORED_PLAYERS;
  }

  /**
   * TODO.
   *
   * @return WIP.
   */
  private boolean validAnchoragesCombination() {
    return true;
  }
}