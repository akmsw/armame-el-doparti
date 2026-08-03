package armameeldopartidesktop.controllers;

import java.awt.Component;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import armameeldopartidesktop.models.Anchorage;
import armameeldopartidesktop.models.Player;
import armameeldopartidesktop.models.Team;
import armameeldopartidesktop.models.enums.Distribution;
import armameeldopartidesktop.models.enums.Position;
import armameeldopartidesktop.models.enums.ProgramView;
import armameeldopartidesktop.utils.common.CommonFields;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.views.AnchoragesView;

/**
 * Anchorages view controller class.
 *
 * @since 3.0.0
 *
 * @version 1.1.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class AnchoragesController extends Controller<AnchoragesView> {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Updates the checkboxes text with the players names.
   */
  public void updateCheckboxesText() {
    for (Position position : Position.values()) {
      for (int checkboxIndex = 0; checkboxIndex < CommonFields.getPlayersSets().get(position).size(); checkboxIndex++) {
        view.getCheckboxesMap()
            .get(position)
            .get(checkboxIndex)
            .setText(CommonFields.getPlayersSets()
                                 .get(position)
                                 .get(checkboxIndex)
                                 .getName());
      }
    }

    view.refreshView();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Resets the controlled view to its default values.
   *
   * <p>Clears every anchorage made, updating the text area and the state of the buttons, and the checkboxes that were selected whose players were not anchored, are deselected.
   */
  @Override
  protected void resetView() {
    clearAnchorages();
    updateTextArea();
    toggleButtons();
  }

  @Override
  protected void setUpInitialState() {
    view.getFinishButton().setEnabled(false);
  }

  /**
   * Sets up the GUI components event listeners.
   */
  @Override
  protected void setUpListeners() {
    view.getFinishButton().addActionListener(event -> finishButtonEvent(CommonFunctions.getComponentFromEvent(event)));
    view.getNewAnchorageButton().addActionListener(event -> newAnchorageButtonEvent(CommonFunctions.getComponentFromEvent(event)));
    view.getEditAnchorageButton().addActionListener(_ -> editAnchorage());
    view.getDeleteAnchorageButton().addActionListener(event -> deleteAnchorageButtonEvent(CommonFunctions.getComponentFromEvent(event)));
    view.getClearAnchoragesButton().addActionListener(_ -> resetView());
    view.getBackButton().addActionListener(_ -> backButtonEvent());
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Checks if the necessary anchorages conditions are met. If so, it proceeds with the distribution.
   *
   * @param parentComponent Graphical component where the dialog windows associated with the event should be displayed.
   */
  private void finishButtonEvent(Component parentComponent) {
    if (!validAnchoragesCombination(0, Arrays.asList(new Team(), new Team()))) {
      CommonFunctions.showMessageDialog(parentComponent, Constants.MSG_WARNING_ANCHORAGES_CONFLICTS, JOptionPane.WARNING_MESSAGE);

      return;
    }

    hideView();

    // The checkboxes that were selected whose players were not anchored, are deselected. Then, the corresponding following view is shown.
    view.getCheckboxesMap()
        .values()
        .stream()
        .flatMap(List::stream)
        .filter(checkbox -> checkbox.isSelected() && checkbox.isVisible())
        .forEach(checkbox -> checkbox.setSelected(false));

    CommonFunctions.getController((CommonFields.getDistribution() == Distribution.MIX_BY_SKILL_POINTS) ? ProgramView.SKILL_POINTS : ProgramView.RESULTS).showView();
  }

  /**
   * Makes the controlled view invisible, deletes every anchorage made, resets the controlled view to its default state and shows the names input view.
   */
  private void backButtonEvent() {
    hideView();
    resetView();

    CommonFunctions.getController(ProgramView.NAMES_INPUT).showView();
  }

  /**
   * Updates the text displayed in the read-only text area.
   *
   * <p>The order in which the players are displayed in this text area corresponds to the order of the Position enum.
   *
   * @see armameeldopartidesktop.models.enums.Position
   */
  private void updateTextArea() {
    view.getTextArea().setText(null);

    for (int anchorageNumber = 0; anchorageNumber < CommonFields.getAnchorages().size(); anchorageNumber++) {
      view.getTextArea().append("ANCLAJE " + (anchorageNumber + 1) + System.lineSeparator());

      List<Player> anchoredPlayers = CommonFields.getAnchorages()
                                                 .get(anchorageNumber)
                                                 .getPlayers()
                                                 .stream()
                                                 .sorted(Comparator.comparing(player -> player.getPosition().ordinal()))
                                                 .toList();

      for (Player player : anchoredPlayers) {
        view.getTextArea().append((anchoredPlayers.indexOf(player) + 1) + " - " + player.getName() + System.lineSeparator());
      }

      if ((anchorageNumber + 1) != CommonFields.getAnchorages().size()) {
        view.getTextArea().append(System.lineSeparator());
      }
    }
  }

  /**
   * Toggles the buttons and checkboxes states.
   */
  private void toggleButtons() {
    for (JButton button : view.getAnchorageButtons()) {
      button.setEnabled(false);
    }

    if (!CommonFields.getAnchorages().isEmpty()) {
      view.getFinishButton().setEnabled(true);
      view.getClearAnchoragesButton().setEnabled(true);
      view.getEditAnchorageButton().setEnabled(true);
      view.getEditAnchorageButton().setEnabled(true);

      for (JButton button : view.getAnchorageButtons()) {
        button.setEnabled(true);
      }
    }

    if (Constants.MAX_TOTAL_ANCHORED_PLAYERS - CommonFunctions.getPlayersAnchoredCount() < Constants.MIN_ANCHORAGE_SIZE) {
      view.getNewAnchorageButton().setEnabled(false);
      view.getCheckboxesMap()
          .values()
          .stream()
          .flatMap(List::stream)
          .forEach(checkbox -> checkbox.setEnabled(!checkbox.isEnabled()));

      return;
    }

    view.getNewAnchorageButton().setEnabled(true);
    view.getCheckboxesMap()
        .values()
        .stream()
        .flatMap(List::stream)
        .filter(checkbox -> !checkbox.isEnabled() && !checkbox.isSelected())
        .forEach(checkbox -> checkbox.setEnabled(true));
  }

  /**
   * Checks if the necessary conditions to make a new anchorage are met. If so, it does it.
   *
   * @param parentComponent Graphical component where the dialog windows associated with the event should be displayed.
   */
  private void newAnchorageButtonEvent(Component parentComponent) {
    if (!validCheckedPlayersPerPosition()) {
      CommonFunctions.showMessageDialog(parentComponent, Constants.MSG_WARNING_ANCHORAGES_HALF_SET_LIMIT, JOptionPane.WARNING_MESSAGE);

      return;
    }

    List<JCheckBox> selectedPlayersCheckboxes = view.getCheckboxesMap()
                                                    .values()
                                                    .stream()
                                                    .flatMap(List::stream)
                                                    .filter(JCheckBox::isSelected)
                                                    .toList();

    if (selectedPlayersCheckboxes.isEmpty()) {
      CommonFunctions.showMessageDialog(parentComponent, Constants.MSG_INFO_ANCHORAGES_NO_SELECTION, JOptionPane.INFORMATION_MESSAGE);

      return;
    }

    if (selectedPlayersCheckboxes.size() < Constants.MIN_ANCHORAGE_SIZE) {
      CommonFunctions.showMessageDialog(parentComponent, Constants.MSG_WARNING_ANCHORAGE_LOWER_LIMIT, JOptionPane.WARNING_MESSAGE);

      return;
    }

    if (selectedPlayersCheckboxes.size() > Constants.MAX_ANCHORAGE_SIZE) {
      CommonFunctions.showMessageDialog(parentComponent, Constants.MSG_WARNING_ANCHORAGE_UPPER_LIMIT, JOptionPane.WARNING_MESSAGE);

      return;
    }

    if (!validAnchoredPlayersCount(selectedPlayersCheckboxes.size())) {
      CommonFunctions.showMessageDialog(parentComponent, Constants.MSG_WARNING_ANCHORAGES_TOTAL_LIMITS, JOptionPane.WARNING_MESSAGE);

      return;
    }

    CommonFields.getAnchorages()
                .add(new Anchorage(CommonFields.getPlayersSets()
                                               .values()
                                               .stream()
                                               .flatMap(List::stream)
                                               .filter(player -> selectedPlayersCheckboxes.stream().anyMatch(checkbox -> checkbox.getText().equals(player.getName())))
                                               .toList()));

    for (JCheckBox checkbox : selectedPlayersCheckboxes) {
      checkbox.setVisible(false);
      checkbox.setSelected(false);
    }

    updateTextArea();
    toggleButtons();
  }

  /**
   * Prompts the user for the number of the anchorage to delete, and removes it, updating the text area and the state of the buttons.
   *
   * @param parentComponent Graphical component where the dialog windows associated with the event should be displayed.
   */
  private void deleteAnchorageButtonEvent(Component parentComponent) {
    int anchorageToDelete = CommonFunctions.showOptionDialog(parentComponent, "Seleccione qué anclaje desea borrar", CommonFunctions.getAnchoragesAsOptions());

    if (anchorageToDelete != JOptionPane.CLOSED_OPTION) {
      deleteAnchorage(anchorageToDelete);
    }
  }

  /**
   * Deletes the anchorage with the given ordinal identifier, restoring the default state of the checkboxes associated with the players in the deleted anchorage, updating the text area and the state of the buttons.
   *
   * @param targetAnchorageNumber Ordinal identifier of the anchorage to delete.
   */
  private void deleteAnchorage(int targetAnchorageNumber) {
    Anchorage targetAnchorage = CommonFields.getAnchorages().get(targetAnchorageNumber);

    targetAnchorage.getPlayers()
                   .forEach(player -> CommonFunctions.retrieveOptional(view.getCheckboxesMap()
                                                                           .get(player.getPosition())
                                                                           .stream()
                                                                           .filter(checkbox -> checkbox.getText().equals(player.getName()))
                                                                           .findFirst())
                                                     .setVisible(true));

    CommonFields.getAnchorages()
                .remove(targetAnchorage);

    updateTextArea();
    toggleButtons();

    view.refreshView();
  }

  /**
   * Clears the anchorages made, if any.
   *
   * @see #deleteAnchorage(int)
   */
  private void clearAnchorages() {
    while (!CommonFields.getAnchorages().isEmpty()) {
      deleteAnchorage(CommonFields.getAnchorages().size() - 1);
    }
  }

  private void editAnchorage() {
    int anchorageToEdit = CommonFunctions.showOptionDialog(view, "Seleccione qué anclaje desea editar", CommonFunctions.getAnchoragesAsOptions());

    if (anchorageToEdit != JOptionPane.CLOSED_OPTION) {
      // todo
    }
  }

  /**
   * @return Whether more than half of any players set is checked.
   */
  private boolean validCheckedPlayersPerPosition() {
    return view.getCheckboxesMap()
               .values()
               .stream()
               .noneMatch(checkboxesSet -> checkboxesSet.stream()
                                                        .filter(JCheckBox::isSelected)
                                                        .count() > (checkboxesSet.size() / 2));
  }

  /**
   * @param playersToAnchorCount Number of checked players.
   *
   * @return Whether the number of selected players is at most the maximum allowed per anchorage.
   */
  private boolean validAnchoredPlayersCount(int playersToAnchorCount) {
    return (CommonFunctions.getPlayersAnchoredCount() + playersToAnchorCount) <= Constants.MAX_TOTAL_ANCHORED_PLAYERS;
  }

  /**
   * Verifies recursively if the existing anchorages combination is possible to distribute (i.e.: no anchorages conflict exists) prior to perform the distribution itself.
   *
   * <p>It starts by gathering the first anchorage: if there's no conflict in the first team, then it is added to it. If not, it tries to add it to the second team. If the anchorage can't be added successfully to
   * any team, then an anchorages conflict exists. This procedure is repeated recursively with every anchorage. When the final anchorage is reached, the resulting temporary teams are validated to return that as the
   * recursion break condition.
   *
   * @param recursiveVerificationIndex Recursive index used to iterate through the existing anchorages.
   * @param teams                      Empty, temporary teams.
   *
   * @return Whether the existing anchorages combination is possible to distribute.
   */
  private boolean validAnchoragesCombination(int recursiveVerificationIndex, List<Team> teams) {
    if (recursiveVerificationIndex == CommonFields.getAnchorages().size()) {
      return areValidTeams(teams);
    }

    Anchorage anchorage = CommonFields.getAnchorages().get(recursiveVerificationIndex);

    for (Team team : teams) {
      if (!anchoragesConflictExists(team, anchorage)) {
        for (Player player : anchorage.getPlayers()) {
          team.getPlayers()
              .get(player.getPosition())
              .add(player);
        }

        if (validAnchoragesCombination(recursiveVerificationIndex + 1, teams)) {
          return true;
        }

        team.clear();
      }
    }

    return false;
  }

  /**
   * @param team      Temporary team.
   * @param anchorage Anchorage to validate.
   *
   * @return Whether a given anchorage can be added to a given team without exceeding any players limit for their position sets.
   */
  private boolean anchoragesConflictExists(Team team, Anchorage anchorage) {
    Map<Position, Integer> playersCountPerPosition = team.getPlayersCountPerPosition();

    for (Player player : anchorage.getPlayers()) {
      int newCount = playersCountPerPosition.getOrDefault(player.getPosition(), 0) + 1;

      if (newCount > CommonFields.getPlayerLimitPerPosition().get(player.getPosition())) {
        return true;
      }

      playersCountPerPosition.put(player.getPosition(), newCount);
    }

    return false;
  }

  /**
   * @param teams Temporary teams.
   *
   * @return Whether any of the given teams has any position set with more than its allowed players limit.
   */
  private boolean areValidTeams(List<Team> teams) {
    return teams.stream()
                .allMatch(team -> CommonFields.getPlayerLimitPerPosition()
                                              .entrySet()
                                              .stream()
                                              .noneMatch(positionLimit -> team.getPlayersCountPerPosition()
                                                                              .getOrDefault(positionLimit.getKey(), 0) > positionLimit.getValue()));
  }
}