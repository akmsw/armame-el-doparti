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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Results view controller.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class ResultsController extends Controller<ResultsView> {

  // ---------------------------------------- Private constants ---------------------------------

  private static final int TABLE_COLUMNS = 3;

  // ---------------------------------------- Private fields ------------------------------------

  private BySkillsMixer bySkillsMixer;

  private RandomMixer randomMixer;

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

    getView().setTable(
      new CustomTable(
        Constants.PLAYERS_PER_TEAM + CommonFields.getDistribution() + 1,
        TABLE_COLUMNS
      )
    );
    getView().initializeInterface();

    table = (CustomTable) getView().getTable();

    overrideTableFormat();
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
      table.setValueAt("EQUIPO " + (teamIndex + 1), 0, teamIndex + 1);
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

  /**
   * Overrides the table cells format in order to match the overall program aesthetics, including
   * text alignment and background and foreground colors.
   *
   * <p>Row 0 and column 0 have dark green background and white foreground. The remaining cells will
   * have black foreground.
   *
   * <p>The background color will be yellow-ish if the cell shows any skill points related
   * information. If the cell contains an anchored player name, its background will be the
   * corresponding from the ANCHORAGES_COLORS array. If not, its background will be white.
   *
   * <p>The cell text will be centered if it shows any skill points related information, or a team
   * name. Otherwise, it will be left-aligned.
   */
  private void overrideTableFormat() {
    getView().getTable().setDefaultRenderer(
        Object.class,
        new DefaultTableCellRenderer() {
          /**
           * Configures the table cells format.
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
            JComponent c = (JComponent) super.getTableCellRendererComponent(
                myTable, value, isSelected, hasFocus, row, column
            );

            boolean byScoresMixFlag = CommonFields.getDistribution() == Constants.MIX_BY_SKILLS
                                      && row == getView().getTable()
                                                         .getRowCount() - 1;

              c.setOpaque(false);
              c.setBorder(
                new EmptyBorder(
                  Constants.ROUNDED_BORDER_INSETS_GENERAL,
                  Constants.ROUNDED_BORDER_INSETS_GENERAL,
                  Constants.ROUNDED_BORDER_INSETS_GENERAL,
                  Constants.ROUNDED_BORDER_INSETS_GENERAL
                )
              );

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
                              ? Constants.COLORS_ANCHORAGES
                                         .get(playerOnCell.getAnchorageNumber() - 1)
                              : Constants.GREEN_LIGHT_WHITE);
              c.setForeground(Color.BLACK);
              ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.LEFT);

              return c;
          }

          @Override
          protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
            g2.setColor(getBackground());
            g2.fillRoundRect(
                0,
                0,
                (getWidth() - 1),
                (getHeight() - 1),
                Constants.ROUNDED_BORDER_ARC_TABLE_CELLS,
                Constants.ROUNDED_BORDER_ARC_TABLE_CELLS
            );

            super.paintComponent(g2);

            g2.dispose();
          }
        }
    );
  }
}