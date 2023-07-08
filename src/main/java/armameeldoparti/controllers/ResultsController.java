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
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import lombok.NonNull;

/**
 * Results view controller.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since v3.0
 */
public class ResultsController extends Controller {

  // ---------------------------------------- Private constants ---------------------------------

  /**
   * Fixed table cells width (in pixels). This value depends on the program's font and the maximum
   * player name length.
   */
  private static final int FIXED_CELL_WIDTH = 250;
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

  private BySkillsMixer bySkillsMixer;

  private RandomMixer randomMixer;

  private List<Team> teams;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the results view controller.
   *
   * @param resultsView View to control.
   */
  public ResultsController(@NonNull ResultsView resultsView) {
    super(resultsView);

    bySkillsMixer = new BySkillsMixer();

    randomMixer = new RandomMixer();

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

    ((ResultsView) getView()).setTable(new CustomTable(Constants.PLAYERS_PER_TEAM + 1
                                                       + CommonFields.getDistribution(),
                                                       TABLE_COLUMNS));
    ((ResultsView) getView()).initializeInterface();

    setTableFormat();
    fillTableFields();
    updateTable();
    setTableCellsSize();
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
                  .forEach(player -> ((ResultsView) getView()).getTable()
                                                              .setValueAt(player.getName(),
                                                                          wrapper.row++,
                                                                          wrapper.column)));

      wrapper.column++;
      wrapper.row = 1;
    });

    if (CommonFields.getDistribution() == Constants.MIX_BY_SKILLS) {
      for (int teamIndex = 0; teamIndex < 2; teamIndex++) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(teams.get(teamIndex)
                                                  .getTeamPlayers()
                                                  .values()
                                                  .stream()
                                                  .flatMap(List::stream)
                                                  .mapToInt(Player::getSkillPoints)
                                                  .reduce(0, Math::addExact),
                                             ((ResultsView) getView()).getTable()
                                                                      .getRowCount() - 1,
                                             teamIndex + 1);
      }
    }
  }

  /**
   * Distributes the players randomly.
   *
   * @return The updated teams with the players distributed.
   */
  @NonNull
  public List<Team> randomMix() {
    return CommonFields.isAnchoragesEnabled() ? randomMixer.withAnchorages(teams)
                                              : randomMixer.withoutAnchorages(teams);
  }

  /**
   * Distributes the players based on their skill points.
   *
   * @return The updated teams with the players distributed.
   */
  @NonNull
  public List<Team> bySkillsMix() {
    return CommonFields.isAnchoragesEnabled() ? bySkillsMixer.withAnchorages(teams)
                                              : bySkillsMixer.withoutAnchorages(teams);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Sets the ideal table cells size.
   */
  public void setTableCellsSize() {
    JTable table = ((ResultsView) getView()).getTable();

    int columnCount = table.getColumnCount();
    int rowCount = table.getRowCount();

    for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
      table.getColumnModel()
           .getColumn(columnIndex)
           .setPreferredWidth(FIXED_CELL_WIDTH);
    }

    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
      int rowHeight = table.getRowHeight();

      for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
        Component component = table.prepareRenderer(table.getCellRenderer(rowIndex, columnIndex),
                                                    rowIndex,
                                                    columnIndex);

        rowHeight = Math.max(rowHeight, component.getPreferredSize()
                                                 .height);
      }

      table.setRowHeight(rowIndex, rowHeight);
    }
  }

  /**
   * Sets the table cells format, including text alignment and background and foreground colors.
   *
   * <p>Row 0 & column 0 have dark green background and white foreground. The remaining cells will
   * have black foreground.
   *
   * <p>The background color will be yellow-ish if the cell shows any skill points related
   * information. If the cell contains an anchored player name, its background will be the
   * corresponding from the ANCHORAGES_COLORS array. If not, its background will be white.
   *
   * <p>The cell text will be centered if it shows any skill points related information, or a team
   * name. Otherwise, it will be left-aligned.
   */
  private void setTableFormat() {
    ((ResultsView) getView()).getTable().setDefaultRenderer(
        Object.class,
        new DefaultTableCellRenderer() {
          /**
           * Configures the table cells background and foreground colors.
           *
           * @param myTable    Source table.
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
    for (int teamIndex = 0; teamIndex < 2; teamIndex++) {
      ((ResultsView) getView()).getTable()
                               .setValueAt("EQUIPO #" + (teamIndex + 1), 0, teamIndex + 1);
    }

    int rowCount = ((ResultsView) getView()).getTable()
                                            .getRowCount() - 1;

    for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
      if (rowIndex == 1) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(CommonFields.getPositionsMap()
                                                         .get(Position.CENTRAL_DEFENDER),
                                             rowIndex,
                                             0);
      } else if (rowIndex < 4) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(CommonFields.getPositionsMap()
                                                         .get(Position.LATERAL_DEFENDER),
                                             rowIndex,
                                             0);
      } else if (rowIndex < 6) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(CommonFields.getPositionsMap()
                                                         .get(Position.MIDFIELDER),
                                             rowIndex,
                                             0);
      } else if (rowIndex < 7) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(CommonFields.getPositionsMap()
                                                         .get(Position.FORWARD),
                                             rowIndex,
                                             0);
      }
    }

    if (CommonFields.getDistribution() == Constants.MIX_BY_SKILLS) {
      for (int teamIndex = 0; teamIndex < 2; teamIndex++) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(teamIndex == 0 ? CommonFields.getPositionsMap()
                                                                          .get(Position.GOALKEEPER)
                                                            : "PUNTAJE DEL EQUIPO",
                                             ((ResultsView) getView()).getTable()
                                                                     .getRowCount() + teamIndex - 2,
                                             0);
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