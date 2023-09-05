package armameeldoparti.controllers;

import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import armameeldoparti.models.Team;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.CustomTable;
import armameeldoparti.utils.mixers.BySkillPointsMixer;
import armameeldoparti.utils.mixers.RandomMixer;
import armameeldoparti.views.ResultsView;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

  private BySkillPointsMixer bySkillPointsMixer;

  private RandomMixer randomMixer;

  private CustomTable table;

  private Team team1;
  private Team team2;

  private List<Team> teams;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the results view controller.
   *
   * @param resultsView View to control.
   */
  public ResultsController(ResultsView resultsView) {
    super(resultsView);

    bySkillPointsMixer = new BySkillPointsMixer();

    randomMixer = new RandomMixer();

    team1 = new Team(1);
    team2 = new Team(2);

    teams = new ArrayList<>();

    setUpListeners();
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Creates the teams and the results table, applies the needed table format, fills the
   * non-variable table cells and displays the distribution results.
   */
  public void setUp() {
    teams = (CommonFields.getDistribution() == Constants.MIX_RANDOM
                                               ? randomMix(Arrays.asList(team1, team2))
                                               : bySkillPointsMix(Arrays.asList(team1, team2)));

    view.setTable(
      new CustomTable(
        Constants.PLAYERS_PER_TEAM + CommonFields.getDistribution() + 1,
        TABLE_COLUMNS
      )
    );
    view.initializeInterface();

    table = (CustomTable) view.getTable();

    overrideTableFormat();
    fillTableFields();
    updateTable();

    table.adjustCells();

    view.pack();
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

    teams = randomMix(Arrays.asList(team1, team2));

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

    teams.forEach(
        team -> {
          Arrays.stream(Position.values())
                .forEach(position -> team.getTeamPlayers()
                                         .get(position)
                                         .forEach(
                                           player -> table.setValueAt(player.getName(),
                                                                      wrapper.row++,
                                                                      wrapper.column)));

          wrapper.column++;
          wrapper.row = 1;
        }
    );

    if (CommonFields.getDistribution() == Constants.MIX_BY_SKILL_POINTS) {
      for (int teamIndex = 0; teamIndex < teams.size(); teamIndex++) {
        table.setValueAt(
            teams.get(teamIndex)
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
   * @param teams Teams to populate randomly.
   *
   * @return The updated teams with the players distributed.
   */
  public List<Team> randomMix(List<Team> teams) {
    return CommonFields.isAnchoragesEnabled() ? randomMixer.withAnchorages(teams)
                                              : randomMixer.withoutAnchorages(teams);
  }

  /**
   * Distributes the players based on their skill points.
   *
   * @param teams Teams to populate by skill points.
   *
   * @return The updated teams with the players distributed.
   */
  public List<Team> bySkillPointsMix(List<Team> teams) {
    return CommonFields.isAnchoragesEnabled() ? bySkillPointsMixer.withAnchorages(teams)
                                              : bySkillPointsMixer.withoutAnchorages(teams);
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Disposes the controlled view and creates a new one to control.
   */
  @Override
  protected void resetView() {
    view.dispose();

    setView(new ResultsView());
    setUpListeners();
  }

  @Override
  protected void setUpInitialState() {
    // Body not needed in this particular controller
  }

  @Override
  protected void setUpListeners() {
    view.getBackButton()
        .addActionListener(e -> backButtonEvent());

    view.getRemixButton()
        .addActionListener(e -> remixButtonEvent());
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Fills the table cells whose texts do not change.
   */
  private void fillTableFields() {
    int rowCount = table.getRowCount() - 1;

    Map<Position, String> positionsMap = CommonFields.getPositionsMap();

    for (int teamIndex = 0; teamIndex < teams.size(); teamIndex++) {
      table.setValueAt("EQUIPO " + (teamIndex + 1), 0, teamIndex + 1);
    }

    for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
      if (rowIndex == 1) {
        table.setValueAt(
            positionsMap.get(Position.CENTRAL_DEFENDER),
            rowIndex,
            0
        );
      } else if (rowIndex < 4) {
        table.setValueAt(
            positionsMap.get(Position.LATERAL_DEFENDER),
            rowIndex,
            0
        );
      } else if (rowIndex < 6) {
        table.setValueAt(
            positionsMap.get(Position.MIDFIELDER),
            rowIndex,
            0
        );
      } else if (rowIndex < 7) {
        table.setValueAt(
            positionsMap.get(Position.FORWARD),
            rowIndex,
            0
        );
      }
    }

    if (CommonFields.getDistribution() == Constants.MIX_BY_SKILL_POINTS) {
      for (int teamIndex = 0; teamIndex < teams.size(); teamIndex++) {
        table.setValueAt(
            teamIndex == 0 ? positionsMap.get(Position.GOALKEEPER)
                           : "Puntuación del equipo",
            table.getRowCount() + teamIndex - 2,
            0
        );
      }
    } else {
      table.setValueAt(
          positionsMap.get(Position.GOALKEEPER),
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
                .forEach(player -> player.setTeamNumber(0));
  }

  /**
   * Overrides the table cells format in order to match the overall program aesthetics, including
   * text alignment and background and foreground colors.
   *
   * <p>Row 0 and column 0 have dark green background and white foreground. The remaining cells will
   * have black foreground.
   *
   * <p>The background color will be medium green if the cell shows any skill points related
   * information. If the cell contains an anchored player name, its background will be the
   * corresponding from the ANCHORAGES_COLORS array. If not, its background will be light green.
   *
   * <p>The cell text will be centered if it shows any skill points related information, or a team
   * name. Otherwise, it will be left-aligned.
   */
  private void overrideTableFormat() {
    view.getTable().setDefaultRenderer(
        Object.class,
        new DefaultTableCellRenderer() {
          @Override
          public Component getTableCellRendererComponent(JTable myTable, Object value,
                                                         boolean isSelected, boolean hasFocus,
                                                         int row, int column) {
            JComponent c = (JComponent) super.getTableCellRendererComponent(
                myTable, value, isSelected, hasFocus, row, column
            );

            boolean mixBySkill = CommonFields.getDistribution() == Constants.MIX_BY_SKILL_POINTS
                                 && row == view.getTable()
                                               .getRowCount() - 1;

            c.setOpaque(false);
            c.setBorder(
              new EmptyBorder(Constants.INSETS_GENERAL)
            );

            if (row == 0) {
              c.setBackground(Constants.COLOR_GREEN_DARK);
              c.setForeground(Color.WHITE);

              ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);

              return c;
            }

            if (column == 0) {
              if (mixBySkill) {
                c.setBackground(Constants.COLOR_GREEN_MEDIUM);
                c.setForeground(Color.WHITE);

                ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);

                return c;
              }

              c.setBackground(Constants.COLOR_GREEN_DARK);
              c.setForeground(Color.WHITE);

              ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.LEFT);

              return c;
            }

            if (mixBySkill) {
              c.setBackground(Constants.COLOR_GREEN_MEDIUM);
              c.setForeground(Color.WHITE);

              ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);

              return c;
            }

            Player playerOnCell = CommonFunctions.retrieveOptional(
                CommonFields.getPlayersSets()
                            .values()
                            .stream()
                            .flatMap(List::stream)
                            .filter(player -> player.getName() == value)
                            .findFirst()
            );

            c.setBackground(
                playerOnCell.getAnchorageNumber() != 0
                ? Constants.COLORS_ANCHORAGES
                           .get(playerOnCell.getAnchorageNumber() - 1)
                : Constants.COLOR_GREEN_LIGHT_WHITE
            );
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