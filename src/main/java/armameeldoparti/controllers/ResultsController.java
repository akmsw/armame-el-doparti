package armameeldoparti.controllers;

import armameeldoparti.Main;
import armameeldoparti.models.Player;
import armameeldoparti.models.Positions;
import armameeldoparti.models.Team;
import armameeldoparti.models.Views;
import armameeldoparti.utils.BySkillsMixer;
import armameeldoparti.utils.RandomMixer;
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
   * Anchorages colors array used as cell background colors.
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

  private List<Team> teams;

  private RandomMixer randomMixer = new RandomMixer();
  private BySkillsMixer bySkillsMixer = new BySkillsMixer();

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
   * Resets the controlled view to its default values and
   * makes it invisible.
   */
  @Override
  public void resetView() {
    getView().dispose();
    setView(new ResultsView());
  }

  /**
   * Creates the teams and the results table, applies the needed table format, fills
   * the non-variable table cells and displays the distribution results.
   */
  public void setUp() {
    Team team1 = new Team();
    Team team2 = new Team();

    teams = new ArrayList<>();

    teams.add(team1);
    teams.add(team2);

    if (Main.getDistribution() == Main.RANDOM_MIX) {
      teams = randomMix(Main.thereAreAnchorages());
      ((ResultsView) getView()).setTable(new JTable(Main.PLAYERS_PER_TEAM + 1, TABLE_COLUMNS));
    } else {
      teams = bySkillsMix(Main.thereAreAnchorages());
      ((ResultsView) getView()).setTable(new JTable(Main.PLAYERS_PER_TEAM + 2, TABLE_COLUMNS));
    }

    ((ResultsView) getView()).initializeInterface();

    setTableFormat();
    fillTableFields();
    updateTable();

    ((ResultsView) getView()).setTableCellsSize();
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

    if (Main.getDistribution() == Main.RANDOM_MIX) {
      if (Main.thereAreAnchorages()) {
        Main.getController(Views.ANCHORAGES)
            .showView();
      } else {
        Main.getController(Views.NAMES_INPUT)
            .showView();
      }
    } else {
      Main.getController(Views.SKILL_POINTS)
          .showView();
    }
  }

  /**
   * 'Remix' button event handler.
   *
   * <p>Resets the teams, redistributes the players with the
   * specified method and updates the results table.
   */
  public void remixButtonEvent() {
    resetTeams();

    teams = randomMix(Main.thereAreAnchorages());

    updateTable();
  }

  /**
   * Fills the table with the distribution results.
   *
   * <p>The table cells are filled trusting the positions order in the
   * first column (same order as the positions enum).
   */
  public void updateTable() {
    var wrapperColumn = new Object() {
      int column = 1;
    };

    var wrapperRow = new Object() {
      int row = 1;
    };

    teams.forEach(team -> {
      Arrays.stream(Positions.values())
            .forEach(position ->
              team.getPlayers()
                  .get(position)
                  .forEach(player -> ((ResultsView) getView()).getTable()
                                                              .setValueAt(player.getName(),
                                                                          wrapperRow.row++,
                                                                          wrapperColumn.column)));
      wrapperColumn.column++;
      wrapperRow.row = 1;
    });

    if (Main.getDistribution() == Main.BY_SKILL_MIX) {
      ((ResultsView) getView()).getTable()
                               .setValueAt(teams.get(0)
                                                 .getPlayers()
                                                 .values()
                                                 .stream()
                                                 .flatMap(List::stream)
                                                 .mapToInt(Player::getSkillPoints)
                                                 .reduce(0, Math::addExact),
                                           ((ResultsView) getView()).getTable()
                                                                    .getRowCount() - 1, 1);

      ((ResultsView) getView()).getTable()
                               .setValueAt(teams.get(1)
                                                 .getPlayers()
                                                 .values()
                                                 .stream()
                                                 .flatMap(List::stream)
                                                 .mapToInt(Player::getSkillPoints)
                                                 .reduce(0, Math::addExact),
                                           ((ResultsView) getView()).getTable()
                                                                    .getRowCount() - 1, 2);
    }
  }

  /**
   * Distributes the players randomly.
   *
   * @param thereAreAnchorages Whether there are anchorages or not.
   *
   * @return The updated teams with the players distributed.
   */
  public List<Team> randomMix(boolean thereAreAnchorages) {
    return thereAreAnchorages
           ? randomMixer.withAnchorages(teams)
           : randomMixer.withoutAnchorages(teams);
  }

  /**
   * Distributes the players based on their skill points.
   *
   * @param thereAreAnchorages Whether there are anchorages or not.
   *
   * @return The updated teams with the players distributed.
   */
  public List<Team> bySkillsMix(boolean thereAreAnchorages) {
    return thereAreAnchorages
           ? bySkillsMixer.withAnchorages(teams)
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

            boolean byScoresMixFlag = Main.getDistribution() == Main.BY_SKILL_MIX
                                      && row == ((ResultsView) getView()).getTable()
                                                                         .getRowCount() - 1;

            if (row == 0) {
              c.setBackground(Main.DARK_GREEN);
              c.setForeground(Color.WHITE);
              ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);

              return c;
            }

            if (column == 0) {
              if (byScoresMixFlag) {
                c.setBackground(Main.LIGHT_ORANGE);
                c.setForeground(Color.BLACK);
                ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);

                return c;
              }

              c.setBackground(Main.DARK_GREEN);
              c.setForeground(Color.WHITE);
              ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.LEFT);

              return c;
            }

            if (byScoresMixFlag) {
              c.setBackground(Main.LIGHT_ORANGE);
              c.setForeground(Color.BLACK);
              ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);

              return c;
            }

            Player playerOnCell = (Player) Main.getPlayersSets()
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
    ((ResultsView) getView()).getTable()
                             .setValueAt("EQUIPO #1", 0, 1);

    ((ResultsView) getView()).getTable()
                             .setValueAt("EQUIPO #2", 0, 2);

    for (int i = 1; i < ((ResultsView) getView()).getTable()
                                                 .getRowCount() - 1; i++) {
      if (i == 1) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(Main.getPositionsMap()
                                                 .get(Positions.CENTRAL_DEFENDER), i, 0);
      } else if (i < 4) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(Main.getPositionsMap()
                                                 .get(Positions.LATERAL_DEFENDER), i, 0);
      } else if (i < 6) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(Main.getPositionsMap()
                                                 .get(Positions.MIDFIELDER), i, 0);
      } else if (i < 7) {
        ((ResultsView) getView()).getTable()
                                 .setValueAt(Main.getPositionsMap()
                                                 .get(Positions.FORWARD), i, 0);
      }
    }

    if (Main.getDistribution() == Main.BY_SKILL_MIX) {
      ((ResultsView) getView()).getTable()
                               .setValueAt(Main.getPositionsMap()
                                               .get(Positions.GOALKEEPER),
                                           ((ResultsView) getView()).getTable()
                                                                    .getRowCount() - 2, 0);
      ((ResultsView) getView()).getTable()
                               .setValueAt("PUNTAJE DEL EQUIPO",
                                           ((ResultsView) getView()).getTable()
                                                                    .getRowCount() - 1, 0);
    } else {
      ((ResultsView) getView()).getTable()
                               .setValueAt(Main.getPositionsMap()
                                               .get(Positions.GOALKEEPER),
                                           ((ResultsView) getView()).getTable()
                                                                    .getRowCount() - 1, 0);
    }
  }

  /**
   * Resets the teams and clears the players lists.
   */
  private void resetTeams() {
    teams.forEach(Team::clear);

    Main.getPlayersSets()
        .values()
        .forEach(ps -> ps.forEach(p -> p.setTeam(0)));
  }
}