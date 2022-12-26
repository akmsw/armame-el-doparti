package armameeldoparti.controllers;

import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.Team;
import armameeldoparti.models.Views;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.mixers.BySkillsMixer;
import armameeldoparti.utils.mixers.RandomMixer;
import armameeldoparti.views.ResultsView;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Results view controller.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 26/07/2022
 */
public class ResultsController extends Controller {

  // ---------------------------------------- Private constants ---------------------------------

  private static final int TABLE_COLUMNS = 3;

  /**
   * Anchorages colors array used as the background color for the results table.
   */
  private static final Color[] ANCHORAGES_COLORS = {
    new Color(255, 204, 153),
    new Color(184, 224, 227),
    new Color(220, 206, 235),
    new Color(195, 235, 198),
    new Color(151, 197, 216),
    new Color(219, 220, 218)
  };

  // ---------------------------------------- Private fields ------------------------------------

  private BySkillsMixer bySkillsMixer = new BySkillsMixer();

  private RandomMixer randomMixer = new RandomMixer();

  private List<Team> teams;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the results view controller.
   *
   * @param resultsView View to control.
   */
  public ResultsController(ResultsView resultsView) {
    super(resultsView);
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Disposes the controlled view and creates a new one to control.
   */
  @Override
  public void resetView() {
    getView().dispose();
    setControlledView(new ResultsView());
  }

  /**
   * Creates the teams and the results table, applies the needed table format, fills
   * the non-variable table cells and displays the distribution results.
   */
  public void setUp() {
    Team team1 = new Team(1);
    Team team2 = new Team(2);

    int addRows;

    teams = new ArrayList<>();

    teams.add(team1);
    teams.add(team2);

    if (CommonFields.getDistribution() == Constants.MIX_RANDOM) {
      teams = randomMix();

      addRows = 1;
    } else {
      teams = bySkillsMix();

      addRows = 2;
    }

    ((ResultsView) getView()).setTable(new JTable(Constants.PLAYERS_PER_TEAM + addRows,
                                                  TABLE_COLUMNS));
    ((ResultsView) getView()).initializeInterface();

    setTableFormat();
    fillTableFields();
    updateTable();

    ((ResultsView) getView()).setTableCellsSize();

    getView().pack();
    getView().setLocationRelativeTo(null);
  }

  /**
   * 'Back' button event handler.
   *
   * <p>Resets the teams, resets the controlled view to its default
   * values and makes it invisible, and shows the corresponding previous
   * view.
   */
  public void backButtonEvent() {
    resetTeams();
    resetView();

    Views previousView;

    if (CommonFields.getDistribution() == Constants.MIX_RANDOM) {
      previousView = CommonFields.thereAreAnchorages() ? Views.ANCHORAGES : Views.NAMES_INPUT;
    } else {
      previousView = Views.SKILL_POINTS;
    }

    CommonFunctions.getController(previousView)
                   .showView();
  }

  /**
   * 'Remix' button event handler.
   *
   * <p>Resets the teams, redistributes the players with the
   * specified method and updates the results table.
   */
  public void remixButtonEvent() {
    resetTeams();

    teams = randomMix();

    updateTable();
  }

  /**
   * Fills the table with the distribution results.
   *
   * <p>The table cells are filled trusting the positions order in the
   * first column (same order as the positions enum).
   */
  public void updateTable() {
    var wrapper = new Object() {
      int column = 1;
      int row = 1;
    };

    teams.forEach(team -> {
      Arrays.stream(Position.values())
            .forEach(position ->
              team.getPlayers()
                  .get(position)
                  .forEach(player -> ((ResultsView) getView()).getTable()
                                                              .setValueAt(player.getName(),
                                                                          wrapper.row++,
                                                                          wrapper.column)));

      wrapper.column++;
      wrapper.row = 1;
    });

    if (CommonFields.getDistribution() == Constants.MIX_BY_SKILLS) {
      for (int i = 0; i < 2; i++) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(teams.get(i)
                                                  .getPlayers()
                                                  .values()
                                                  .stream()
                                                  .flatMap(List::stream)
                                                  .mapToInt(Player::getSkillPoints)
                                                  .reduce(0, Math::addExact),
                                             ((ResultsView) getView()).getTable()
                                                                      .getRowCount() - 1,
                                             i + 1);
      }
    }
  }

  /**
   * Distributes the players randomly.
   *
   * @return The updated teams with the players distributed.
   */
  public List<Team> randomMix() {
    return CommonFields.thereAreAnchorages() ? randomMixer.withAnchorages(teams)
                                             : randomMixer.withoutAnchorages(teams);
  }

  /**
   * Distributes the players based on their skill points.
   *
   * @return The updated teams with the players distributed.
   */
  public List<Team> bySkillsMix() {
    return CommonFields.thereAreAnchorages() ? bySkillsMixer.withAnchorages(teams)
                                             : bySkillsMixer.withoutAnchorages(teams);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Sets the table cells format, including text alignment and background and
   * foreground colors.
   *
   * <p>Row 0 & column 0 have dark green background and white foreground.
   * The remaining cells will have black foreground.
   *
   * <p>The background color will be yellow-ish if the cell shows any skill points
   * related information. If the cell contains an anchored player name, its background
   * will be the corresponding from the ANCHORAGES_COLORS array. If not, its background
   * will be white.
   *
   * <p>The cell text will be centered if it shows any skill points related information,
   * or a team name. Otherwise, it will be left-aligned.
   */
  private void setTableFormat() {
    ((ResultsView) getView()).getTable().setDefaultRenderer(
        Object.class,
        new DefaultTableCellRenderer() {
          /**
           * Configures the table cells background and foreground colors.
           *
           * @param table      Source table.
           * @param value      Table cell value.
           * @param isSelected If the cell is selected.
           * @param hasFocus   If the cell is focused.
           * @param row        Cell row number.
           * @param column     Cell column number.
           */
          @Override
          public Component getTableCellRendererComponent(JTable myTable, Object value,
                                                         boolean isSelected, boolean hasFocus,
                                                         int row, int column) {
            final Component c = super.getTableCellRendererComponent(myTable, value, isSelected,
                                                                    hasFocus, row, column);

            boolean byScoresMixFlag = CommonFields.getDistribution() == Constants.MIX_BY_SKILLS
                                      && row == ((ResultsView) getView()).getTable()
                                                                         .getRowCount() - 1;

            if (row == 0) {
              c.setBackground(Constants.GREEN_DARK);
              c.setForeground(Color.WHITE);

              ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);

              return c;
            }

            if (column == 0) {
              if (byScoresMixFlag) {
                c.setBackground(Constants.YELLOW_LIGHT);
                c.setForeground(Color.BLACK);

                ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);

                return c;
              }

              c.setBackground(Constants.GREEN_DARK);
              c.setForeground(Color.WHITE);

              ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.LEFT);

              return c;
            }

            if (byScoresMixFlag) {
              c.setBackground(Constants.YELLOW_LIGHT);
              c.setForeground(Color.BLACK);

              ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);

              return c;
            }

            Player playerOnCell = (Player) CommonFields.getPlayersSets()
                                                       .values()
                                                       .stream()
                                                       .flatMap(List::stream)
                                                       .filter(p -> p.getName() == value)
                                                       .toArray()[0];

            c.setBackground(playerOnCell.getAnchorageNumber() != 0
                            ? ANCHORAGES_COLORS[playerOnCell.getAnchorageNumber() - 1]
                            : Color.WHITE);
            c.setForeground(Color.BLACK);
            ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.LEFT);

            return c;
          }
        });
  }

  /**
   * Fills the table cells whose texts do not change.
   */
  private void fillTableFields() {
    for (int i = 0; i < 2; i++) {
      ((ResultsView) getView()).getTable()
                               .setValueAt("EQUIPO #" + (i + 1), 0, i + 1);
    }

    for (int i = 1; i < ((ResultsView) getView()).getTable()
                                                 .getRowCount() - 1; i++) {
      if (i == 1) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(CommonFields.getPositionsMap()
                                                         .get(Position.CENTRAL_DEFENDER), i, 0);
      } else if (i < 4) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(CommonFields.getPositionsMap()
                                                         .get(Position.LATERAL_DEFENDER), i, 0);
      } else if (i < 6) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(CommonFields.getPositionsMap()
                                                         .get(Position.MIDFIELDER), i, 0);
      } else if (i < 7) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(CommonFields.getPositionsMap()
                                                         .get(Position.FORWARD), i, 0);
      }
    }

    if (CommonFields.getDistribution() == Constants.MIX_BY_SKILLS) {
      for (int i = 0; i < 2; i++) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(i == 0 ? CommonFields.getPositionsMap()
                                                                  .get(Position.GOALKEEPER)
                                                    : "PUNTAJE DEL EQUIPO",
                                             ((ResultsView) getView()).getTable()
                                                                      .getRowCount() + i - 2, 0);
      }
    } else {
      ((ResultsView) getView()).getTable()
                               .setValueAt(CommonFields.getPositionsMap()
                                                       .get(Position.GOALKEEPER),
                                           ((ResultsView) getView()).getTable()
                                                                    .getRowCount() - 1, 0);
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