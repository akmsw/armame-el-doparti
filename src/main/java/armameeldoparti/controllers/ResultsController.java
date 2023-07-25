package armameeldoparti.controllers;

import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import armameeldoparti.models.Team;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.CustomTable;
import armameeldoparti.utils.mixers.BySkillsMixer;
import armameeldoparti.utils.mixers.RandomMixer;
import armameeldoparti.views.ResultsView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Results view controller.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class ResultsController extends Controller {

  // ---------------------------------------- Private constants ---------------------------------

  private static final int TABLE_COLUMNS = 3;

  // ---------------------------------------- Private fields ------------------------------------

  private BySkillsMixer bySkillsMixer;

  private RandomMixer randomMixer;

  private ResultsView view;

  private CustomTable table;

  private List<Team> teams;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the results view controller.
   *
   * @param resultsView View to control.
   */
  public ResultsController(ResultsView resultsView) {
    super(resultsView);

    bySkillsMixer = new BySkillsMixer();

    randomMixer = new RandomMixer();

    view = (ResultsView) getView();

    teams = new ArrayList<>();
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Disposes the controlled view and creates a new one to control.
   */
  @Override
  public void resetView() {
    getView().dispose();
    setView(new ResultsView());
  }

  /**
   * Creates the teams and the results table, applies the needed table format, fills the
   * non-variable table cells and displays the distribution results.
   */
  public void setUp() {
    Team team1 = new Team(1);
    Team team2 = new Team(2);

    teams.clear();
    teams.add(team1);
    teams.add(team2);

    teams = (CommonFields.getDistribution() == Constants.MIX_RANDOM ? randomMix() : bySkillsMix());

    view.setTable(
      new CustomTable(
        Constants.PLAYERS_PER_TEAM + CommonFields.getDistribution() + 1,
        TABLE_COLUMNS
      )
    );
    view.initializeInterface();

    table = (CustomTable) view.getTable();

    fillTableFields();
    updateTable();

    table.adjustCells();

    getView().pack();
  }

  /**
   * 'Back' button event handler.
   *
   * <p>Resets the teams, resets the controlled view to its default values and makes it invisible,
   * and shows the corresponding previous view.
   */
  public void backButtonEvent() {
    resetTeams();
    resetView();

    ProgramView previousView;

    if (CommonFields.getDistribution() == Constants.MIX_RANDOM) {
      previousView = CommonFields.isAnchoragesEnabled() ? ProgramView.ANCHORAGES
                                                        : ProgramView.NAMES_INPUT;
    } else {
      previousView = ProgramView.SKILL_POINTS;
    }

    CommonFunctions.getController(previousView)
                   .showView();
  }

  /**
   * 'Remix' button event handler.
   *
   * <p>Resets the teams, redistributes the players with the specified method and updates the
   * results table.
   */
  public void remixButtonEvent() {
    resetTeams();

    teams = randomMix();

    updateTable();
  }

  /**
   * Fills the table with the distribution results.
   *
   * <p>The table cells are filled trusting the positions order in the first column (same order as
   * the positions enum).
   */
  public void updateTable() {
    var wrapper = new Object() {
      int column = 1;
      int row = 1;
    };

    teams.forEach(team -> {
      Arrays.stream(Position.values())
            .forEach(position ->
              team.getTeamPlayers()
                  .get(position)
                  .forEach(player -> table.setValueAt(player.getName(),
                                                      wrapper.row++,
                                                      wrapper.column)));

      wrapper.column++;
      wrapper.row = 1;
    });

    if (CommonFields.getDistribution() == Constants.MIX_BY_SKILLS) {
      for (int teamIndex = 0; teamIndex < 2; teamIndex++) {
        table.setValueAt(teams.get(teamIndex)
                              .getTeamPlayers()
                              .values()
                              .stream()
                              .flatMap(List::stream)
                              .mapToInt(Player::getSkillPoints)
                              .reduce(0, Math::addExact),
                         table.getRowCount() - 1,
                         teamIndex + 1
        );
      }
    }
  }

  /**
   * Distributes the players randomly.
   *
   * @return The updated teams with the players distributed.
   */
  public List<Team> randomMix() {
    return CommonFields.isAnchoragesEnabled() ? randomMixer.withAnchorages(teams)
                                              : randomMixer.withoutAnchorages(teams);
  }

  /**
   * Distributes the players based on their skill points.
   *
   * @return The updated teams with the players distributed.
   */
  public List<Team> bySkillsMix() {
    return CommonFields.isAnchoragesEnabled() ? bySkillsMixer.withAnchorages(teams)
                                              : bySkillsMixer.withoutAnchorages(teams);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Fills the table cells whose texts do not change.
   */
  private void fillTableFields() {
    for (int teamIndex = 0; teamIndex < 2; teamIndex++) {
      table.setValueAt("EQUIPO #" + (teamIndex + 1), 0, teamIndex + 1);
    }

    int rowCount = table.getRowCount() - 1;

    for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
      if (rowIndex == 1) {
        table.setValueAt(
            CommonFields.getPositionsMap()
                        .get(Position.CENTRAL_DEFENDER),
            rowIndex,
            0
        );
      } else if (rowIndex < 4) {
        table.setValueAt(
            CommonFields.getPositionsMap()
                        .get(Position.LATERAL_DEFENDER),
            rowIndex,
            0
        );
      } else if (rowIndex < 6) {
        table.setValueAt(
            CommonFields.getPositionsMap()
                        .get(Position.MIDFIELDER),
            rowIndex,
            0
        );
      } else if (rowIndex < 7) {
        table.setValueAt(
            CommonFields.getPositionsMap()
                        .get(Position.FORWARD),
            rowIndex,
            0
        );
      }
    }

    if (CommonFields.getDistribution() == Constants.MIX_BY_SKILLS) {
      for (int teamIndex = 0; teamIndex < 2; teamIndex++) {
        table.setValueAt(
            teamIndex == 0 ? CommonFields.getPositionsMap()
                                         .get(Position.GOALKEEPER)
                           : "PUNTAJE DEL EQUIPO",
            table.getRowCount() + teamIndex - 2,
            0
        );
      }
    } else {
      table.setValueAt(
          CommonFields.getPositionsMap()
                      .get(Position.GOALKEEPER),
          table.getRowCount() - 1,
          0
      );
    }
  }

  /**
   * Resets both teams.
   */
  private void resetTeams() {
    teams.forEach(Team::clear);

    CommonFields.getPlayersSets()
                .values()
                .stream()
                .flatMap(List::stream)
                .forEach(p -> p.setTeamNumber(0));
  }
}