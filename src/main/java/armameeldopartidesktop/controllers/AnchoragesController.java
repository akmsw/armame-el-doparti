package armameeldopartidesktop.controllers;

import java.awt.Component;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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
 * @version 1.0.1
 *
 * @author Bonino, Francisco Ignacio.
 */
public class AnchoragesController extends Controller<AnchoragesView> {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private int anchoredPlayersCount;

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

    view.pack();
  }

  /**
   * Checks if the necessary anchorages conditions are met. If so, it proceeds with the distribution.
   *
   * @param parentComponent Graphical component where the dialog windows associated with the event should be displayed.
   */
  public void finishButtonEvent(Component parentComponent) {
    if (!validAnchoragesCombination(0, Arrays.asList(new Team(0), new Team(1)))) {
      CommonFunctions.showMessageDialog(parentComponent, Constants.MSG_WARNING_ANCHORAGES_CONFLICTS, JOptionPane.WARNING_MESSAGE);

      return;
    }

    // The checkboxes that were selected whose players were not anchored, are deselected. Then, the corresponding following view is shown.
    hideView();
    clearCheckboxes();

    CommonFunctions.getController((CommonFields.getDistribution() == Distribution.MIX_BY_SKILL_POINTS) ? ProgramView.SKILL_POINTS : ProgramView.RESULTS).showView();
  }

  /**
   * Checks if the necessary conditions to make a new anchorage are met. If so, it does it.
   *
   * @param parentComponent Graphical component where the dialog windows associated with the event should be displayed.
   */
  public void newAnchorageButtonEvent(Component parentComponent) {
    if (!validCheckedPlayersPerPosition()) {
      CommonFunctions.showMessageDialog(parentComponent, Constants.MSG_WARNING_ANCHORAGES_HALF_SET_LIMIT, JOptionPane.WARNING_MESSAGE);

      return;
    }

    int playersToAnchorCount = (int) view.getCheckboxesMap()
                                         .values()
                                         .stream()
                                         .flatMap(List::stream)
                                         .filter(JCheckBox::isSelected)
                                         .count();

    if (playersToAnchorCount == 0) {
      CommonFunctions.showMessageDialog(parentComponent, Constants.MSG_INFO_ANCHORAGES_NO_SELECTION, JOptionPane.INFORMATION_MESSAGE);

      return;
    }

    if (playersToAnchorCount < Constants.MIN_ANCHORAGE_SIZE) {
      CommonFunctions.showMessageDialog(parentComponent, Constants.MSG_WARNING_ANCHORAGE_LOWER_LIMIT, JOptionPane.WARNING_MESSAGE);

      return;
    }

    if (playersToAnchorCount > Constants.MAX_ANCHORAGE_SIZE) {
      CommonFunctions.showMessageDialog(parentComponent, Constants.MSG_WARNING_ANCHORAGE_UPPER_LIMIT, JOptionPane.WARNING_MESSAGE);

      return;
    }

    if (!validAnchoredPlayersCount(playersToAnchorCount)) {
      CommonFunctions.showMessageDialog(parentComponent, Constants.MSG_WARNING_ANCHORAGES_TOTAL_LIMITS, JOptionPane.WARNING_MESSAGE);

      return;
    }

    newAnchorage();
    updateTextArea();
    toggleButtons();
  }

  /**
   * Deletes the last anchorage made, updating the text area and the state of the buttons.
   */
  public void deleteLastAnchorageButtonEvent() {
    deleteAnchorage(CommonFunctions.getAnchorages().size());
    updateTextArea();
    toggleButtons();
  }

  /**
   * Prompts the user for the number of the anchorage to delete, and removes it, updating the text area and the state of the buttons.
   *
   * @param parentComponent Graphical component where the dialog windows associated with the event should be displayed.
   */
  public void deleteAnchorageButtonEvent(Component parentComponent) {
    String [] optionsDelete = IntStream.rangeClosed(1, CommonFunctions.getAnchorages().size())
                                       .mapToObj(Integer::toString)
                                       .toArray(String[]::new);

    int anchorageToDelete = CommonFunctions.showOptionDialog(parentComponent, "Seleccione qué anclaje desea borrar", optionsDelete);

    if (anchorageToDelete != JOptionPane.CLOSED_OPTION) {
      deleteAnchorage(anchorageToDelete + 1);
      updateTextArea();
      toggleButtons();
    }
  }

  /**
   * Resets the controlled view to its default values.
   */
  public void clearAnchoragesButtonEvent() {
    resetView();
  }

  /**
   * Makes the controlled view invisible, deletes every anchorage made, resets the controlled view to its default state and shows the names input view.
   */
  public void backButtonEvent() {
    hideView();
    resetView();

    CommonFunctions.getController(ProgramView.NAMES_INPUT).showView();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Resets the controlled view to its default values.
   *
   * <p>Clears every anchorage made, updating the text area and the state of the buttons, and the checkboxes that were selected whose players were not anchored, are deselected.
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
    anchoredPlayersCount = 0;

    view.getFinishButton().setEnabled(false);
  }

  /**
   * Sets up the GUI components event listeners.
   */
  @Override
  protected void setUpListeners() {
    view.getFinishButton().addActionListener(event -> finishButtonEvent(CommonFunctions.getComponentFromEvent(event)));
    view.getNewAnchorageButton().addActionListener(event -> newAnchorageButtonEvent(CommonFunctions.getComponentFromEvent(event)));
    view.getDeleteAnchorageButton().addActionListener(event -> deleteAnchorageButtonEvent(CommonFunctions.getComponentFromEvent(event)));
    view.getDeleteLastAnchorageButton().addActionListener(_ -> deleteLastAnchorageButtonEvent());
    view.getClearAnchoragesButton().addActionListener(_ -> clearAnchoragesButtonEvent());
    view.getBackButton().addActionListener(_ -> backButtonEvent());
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Sets a new anchorage based on the players checked.
   *
   * @see #setAnchorages(List)
   */
  private void newAnchorage() {
    setAnchorages(view.getCheckboxesMap()
                      .values()
                      .stream()
                      .flatMap(List::stream)
                      .filter(JCheckBox::isSelected)
                      .toList());

    anchoredPlayersCount = (int) CommonFields.getPlayersSets()
                                             .values()
                                             .stream()
                                             .flatMap(List::stream)
                                             .filter(Player::isAnchored)
                                             .count();
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

    IntStream.range(0, CommonFunctions.getAnchorages().size())
             .forEach(anchorageNumber -> {
               view.getTextArea().append("ANCLAJE " + (anchorageNumber + 1) + System.lineSeparator());

               List<Player> anchorage = CommonFields.getPlayersSets()
                                                    .entrySet()
                                                    .stream()
                                                    .flatMap(players -> players.getValue()
                                                                               .stream()
                                                                               .filter(player -> player.getAnchorageId() == anchorageNumber + 1))
                                                    .sorted(Comparator.comparing(player -> player.getPosition().ordinal()))
                                                    .toList();

               for (Player player : anchorage) {
                 view.getTextArea().append((anchorage.indexOf(player) + 1) + " - " + player.getName() + System.lineSeparator());
               }

               if ((anchorageNumber + 1) != CommonFunctions.getAnchorages().size()) {
                 view.getTextArea().append(System.lineSeparator());
               }
             });
  }

  /**
   * Toggles the buttons and checkboxes states.
   */
  private void toggleButtons() {
    for (JButton button : view.getAnchorageButtons()) {
      button.setEnabled(false);
    }

    if (CommonFunctions.getAnchorages().size() == 1) {
      view.getFinishButton().setEnabled(true);
      view.getDeleteLastAnchorageButton().setEnabled(true);
      view.getClearAnchoragesButton().setEnabled(true);
    } else if (CommonFunctions.getAnchorages().size() > 1) {
      for (JButton button : view.getAnchorageButtons()) {
        button.setEnabled(true);
      }
    }

    if (Constants.MAX_TOTAL_ANCHORED_PLAYERS - anchoredPlayersCount < 2) {
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
   * Clears the anchorages made, if any.
   *
   * @see #deleteAnchorage(int)
   */
  private void clearAnchorages() {
    while (!CommonFunctions.getAnchorages().isEmpty()) {
      deleteAnchorage(CommonFunctions.getAnchorages().size());
    }
  }

  /**
   * Deletes a specific anchorage.
   *
   * <p>The players that have the specified anchorage now will have anchorage number 0. If the anchorage number to delete is not the last one, then the remaining players (from {@code anchorageToDelete + 1} up to
   * {@code CommonFunctions.getAnchorages().size()}) will have their anchorage number decreased by 1.
   *
   * @param anchorageToDelete Anchorage number to delete.
   */
  private void deleteAnchorage(int anchorageToDelete) {
    changeAnchorage(anchorageToDelete, 0);

    if (anchorageToDelete != CommonFunctions.getAnchorages().size()) {
      for (int anchorageNumber = anchorageToDelete + 1; anchorageNumber <= CommonFunctions.getAnchorages().size(); anchorageNumber++) {
        changeAnchorage(anchorageNumber, anchorageNumber - 1);
      }
    }
  }

  /**
   * Changes the anchorage number of certain players.
   *
   * <p>If the replacement is 0 (an anchorage must be removed), then those players will be set as not-anchored, the players corresponding checkboxes will be visible and enabled again, and the anchored players count
   * will be decreased as needed.
   *
   * @param target      Anchorage number to replace.
   * @param replacement New anchorage number to set.
   */
  private void changeAnchorage(int target, int replacement) {
    CommonFields.getPlayersSets()
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(player -> player.getAnchorageId() == target)
                .forEach(
                  player -> {
                    player.setAnchorageId(replacement);

                    if (replacement == Constants.PLAYER_NO_ANCHORAGE_ASSIGNED) {
                      CommonFunctions.retrieveOptional(view.getCheckboxesMap()
                                                           .get(player.getPosition())
                                                           .stream()
                                                           .filter(checkbox -> checkbox.getText().equals(player.getName()))
                                                           .findFirst())
                                     .setVisible(true);

                      anchoredPlayersCount--;
                    }
                  }
                );
  }

  /**
   * Sets the corresponding anchorage number to the selected players. Then, unchecks their checkboxes and makes them invisible.
   *
   * @param cbSet Check boxes set with players checked.
   */
  private void setAnchorages(List<JCheckBox> cbSet) {
    CommonFields.getAnchorages()
                .add(new Anchorage(CommonFields.getAnchorages().size() + 1,
                                   CommonFields.getPlayersSets()
                                               .values()
                                               .stream()
                                               .flatMap(List::stream)
                                               .filter(player -> cbSet.stream().anyMatch(checkbox -> checkbox.getText().equals(player.getName())))
                                               .toList()));

    for (JCheckBox checkbox : cbSet) {
      checkbox.setVisible(false);
      checkbox.setSelected(false);
    }
  }

  /**
   * Unchecks the checked checkboxes.
   */
  private void clearCheckboxes() {
    view.getCheckboxesMap()
        .values()
        .stream()
        .flatMap(List::stream)
        .filter(checkbox -> checkbox.isSelected() && checkbox.isVisible())
        .forEach(checkbox -> checkbox.setSelected(false));
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
    return (anchoredPlayersCount + playersToAnchorCount) <= Constants.MAX_TOTAL_ANCHORED_PLAYERS;
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
    if (recursiveVerificationIndex == CommonFunctions.getAnchorages().size()) {
      return areValidTeams(teams);
    }

    List<Player> anchorage = CommonFunctions.getAnchorages().get(recursiveVerificationIndex);

    for (Team team : teams) {
      if (!anchoragesConflictExists(team, anchorage)) {
        for (Player player : anchorage) {
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
  private boolean anchoragesConflictExists(Team team, List<Player> anchorage) {
    Map<Position, Integer> playersCountPerPosition = team.getPlayersCountPerPosition();

    for (Player player : anchorage) {
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