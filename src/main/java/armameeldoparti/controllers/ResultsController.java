package armameeldoparti.controllers;

import armameeldoparti.Main;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.Team;
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
public class ResultsController implements Controller {

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

  private ResultsView resultsView;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the results view controller.
   *
   * @param resultsView View to control.
   */
  public ResultsController(ResultsView resultsView) {
    this.resultsView = resultsView;
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Makes the controlled view visible.
   */
  @Override
  public void showView() {
    resultsView.setVisible(true);
  }

  /**
   * Makes the controlled view invisible.
   */
  @Override
  public void hideView() {
    resultsView.setVisible(false);
    Controller.centerView(resultsView);
  }

  /**
   * Resets the controlled view to its default values and
   * makes it invisible.
   */
  @Override
  public void resetView() {
    resultsView.dispose();
    resultsView = new ResultsView();
  }

  /**
   * Creates the teams and the results table, applies the needed table format, fills
   * the non-variable table cells and displays the distribution results. Then, makes
   * the controlled view visible.
   */
  public void setUp() {
    Team team1 = new Team();
    Team team2 = new Team();

    teams = new ArrayList<>();

    teams.add(team1);
    teams.add(team2);

    if (Main.getDistribution() == Main.RANDOM_MIX) {
      teams = randomMix(Main.thereAreAnchorages());
      resultsView.setTable(new JTable(Main.PLAYERS_PER_TEAM + 1, TABLE_COLUMNS));
    } else {
      teams = bySkillsMix(Main.thereAreAnchorages());
      resultsView.setTable(new JTable(Main.PLAYERS_PER_TEAM + 2, TABLE_COLUMNS));
    }

    resultsView.initializeInterface();

    setTableFormat();
    fillTableFields();
    updateTable();

    resultsView.setTableCellsSize();
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
        Main.getAnchoragesController()
            .showView();
      } else {
        Main.getNamesInputController()
            .showView();
      }
    } else {
      Main.getSkillsInputController()
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
      Arrays.stream(Position.values())
            .forEach(position ->
              team.getPlayers()
                  .get(position)
                  .forEach(player -> resultsView.getTable()
                                                .setValueAt(player.getName(),
                                                            wrapperRow.row++,
                                                            wrapperColumn.column)));
      wrapperColumn.column++;
      wrapperRow.row = 1;
    });

    if (Main.getDistribution() == Main.BY_SKILLS_MIX) {
      resultsView.getTable()
                 .setValueAt(teams.get(0)
                                  .getPlayers()
                                  .values()
                                  .stream()
                                  .flatMap(List::stream)
                                  .mapToInt(Player::getSkill)
                                  .reduce(0, Math::addExact),
                             resultsView.getTable()
                                        .getRowCount() - 1, 1);

      resultsView.getTable()
                 .setValueAt(teams.get(1)
                                  .getPlayers()
                                  .values()
                                  .stream()
                                  .flatMap(List::stream)
                                  .mapToInt(Player::getSkill)
                                  .reduce(0, Math::addExact),
                             resultsView.getTable()
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
    resultsView.getTable().setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        boolean byScoresMixFlag = Main.getDistribution() == Main.BY_SKILLS_MIX
                                  && row == resultsView.getTable()
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
    resultsView.getTable()
               .setValueAt("EQUIPO #1", 0, 1);

    resultsView.getTable()
               .setValueAt("EQUIPO #2", 0, 2);

    for (int i = 1; i < resultsView.getTable().getRowCount() - 1; i++) {
      if (i == 1) {
        resultsView.getTable()
                   .setValueAt(Main.getPositionsMap()
                                   .get(Position.CENTRAL_DEFENDER), i, 0);
      } else if (i < 4) {
        resultsView.getTable()
                   .setValueAt(Main.getPositionsMap()
                                   .get(Position.LATERAL_DEFENDER), i, 0);
      } else if (i < 6) {
        resultsView.getTable()
                   .setValueAt(Main.getPositionsMap()
                                   .get(Position.MIDFIELDER), i, 0);
      } else if (i < 7) {
        resultsView.getTable()
                   .setValueAt(Main.getPositionsMap()
                                   .get(Position.FORWARD), i, 0);
      }
    }

    if (Main.getDistribution() == Main.BY_SKILLS_MIX) {
      resultsView.getTable()
                 .setValueAt(Main.getPositionsMap()
                                 .get(Position.GOALKEEPER),
                             resultsView.getTable()
                                        .getRowCount() - 2, 0);
      resultsView.getTable()
                 .setValueAt("PUNTAJE DEL EQUIPO", resultsView.getTable()
                                                                      .getRowCount() - 1, 0);
    } else {
      resultsView.getTable()
                 .setValueAt(Main.getPositionsMap()
                                 .get(Position.GOALKEEPER),
                             resultsView.getTable()
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