package armameeldopartidesktop.controllers;

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

import armameeldopartidesktop.models.Player;
import armameeldopartidesktop.models.Team;
import armameeldopartidesktop.models.enums.Distribution;
import armameeldopartidesktop.models.enums.Position;
import armameeldopartidesktop.models.enums.ProgramView;
import armameeldopartidesktop.utils.common.CommonFields;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.utils.mixers.BySkillPointsMixer;
import armameeldopartidesktop.utils.mixers.RandomMixer;
import armameeldopartidesktop.views.ResultsView;

/**
 * Results view controller.
 *
 * @since 3.0.0
 *
 * @version 1.0.1
 *
 * @author Bonino, Francisco Ignacio.
 */
public class ResultsController extends Controller<ResultsView> {

  // ---------- Private constants -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private static final int TABLE_COLUMNS = 3;

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private BySkillPointsMixer bySkillPointsMixer;

  private RandomMixer randomMixer;

  private JTable table;

  private Team team1;
  private Team team2;

  private List<Team> teams;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the results view controller.
   *
   * @param resultsView View to control.
   */
  public ResultsController(ResultsView resultsView) {
    super(resultsView);

    bySkillPointsMixer = new BySkillPointsMixer();
    randomMixer        = new RandomMixer();
    team1              = new Team();
    team2              = new Team();
    teams              = new ArrayList<>();

    setUpListeners();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates the teams and the results table, applies the needed table format, fills the non-variable table cells and displays the distribution results.
   */
  private void setUpView() {
    teams = (CommonFields.getDistribution() == Distribution.MIX_RANDOM ? randomMix(Arrays.asList(team1, team2)) : bySkillPointsMix(Arrays.asList(team1, team2)));

    view.setTable(new JTable(Constants.PLAYERS_PER_TEAM + (CommonFields.getDistribution() == Distribution.MIX_RANDOM ? 1 : 2), TABLE_COLUMNS));
    view.initializeInterface();

    table = view.getTable();

    overrideTableCellRenderer();
    fillTableHeaders();
    updateTableData();
    adjustTableCells();

    view.pack();
  }

  /**
   * Resets the teams, resets the controlled view to its default values and makes it invisible, and shows the corresponding previous view.
   */
  public void backButtonEvent() {
    teams.forEach(Team::clear);

    resetView();

    ProgramView previousView = ProgramView.SKILL_POINTS;

    if (CommonFields.getDistribution() == Distribution.MIX_RANDOM) {
      previousView = CommonFields.isAnchoragesEnabled() ? ProgramView.ANCHORAGES : ProgramView.NAMES_INPUT;
    }

    CommonFunctions.getController(previousView).showView();
  }

  /**
   * Resets the teams, redistributes the players with the specified method and updates the results table.
   */
  public void remixButtonEvent() {
    teams.forEach(Team::clear);

    teams = randomMix(Arrays.asList(team1, team2));

    updateTableData();
  }

  /**
   * Fills the table with the distribution results.
   *
   * <p>The table cells are filled trusting the positions order in the first column (same order as the Position enum).
   *
   * @see armameeldopartidesktop.models.enums.Position
   */
  public void updateTableData() {
    int column = 1;
    int row    = 1;

    for (Team team : teams) {
      for (Position position : Position.values()) {
        for (Player player : team.getPlayers().get(position)) {
          table.setValueAt(player.getName(), row++, column);
        }
      }

      column++;
      row = 1;
    }

    if (CommonFields.getDistribution() == Distribution.MIX_BY_SKILL_POINTS) {
      for (int teamIndex = 0; teamIndex < Constants.TEAMS_TOTAL; teamIndex++) {
        table.setValueAt(teams.get(teamIndex)
                              .getPlayers()
                              .values()
                              .stream()
                              .flatMap(List::stream)
                              .mapToInt(Player::getSkillPoints)
                              .reduce(0, Math::addExact),
                         table.getRowCount() - 1,
                         teamIndex + 1);
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
    return CommonFields.isAnchoragesEnabled() ? randomMixer.withAnchorages(teams) : randomMixer.withoutAnchorages(teams);
  }

  /**
   * Distributes the players based on their skill points.
   *
   * @param teams Teams to populate by skill points.
   *
   * @return The updated teams with the players distributed.
   */
  public List<Team> bySkillPointsMix(List<Team> teams) {
    return CommonFields.isAnchoragesEnabled() ? bySkillPointsMixer.withAnchorages(teams) : bySkillPointsMixer.withoutAnchorages(teams);
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void showView() {
    setUpView();
    centerView();

    view.setVisible(true);
  }

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
    view.getBackButton().addActionListener(_ -> backButtonEvent());
    view.getRemixButton().addActionListener(_ -> remixButtonEvent());
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Fills the table cells whose texts indicates a position or a team.
   */
  private void fillTableHeaders() {
    int rowCount = table.getRowCount() - 1;

    for (int teamIndex = 0; teamIndex < Constants.TEAMS_TOTAL; teamIndex++) {
      table.setValueAt("EQUIPO " + (teamIndex + 1), 0, teamIndex + 1);
    }

    for (int row = 1; row < rowCount; row++) {
      table.setValueAt(
        Constants.MAP_POSITIONS.get(
          switch (row) {
            case 1    -> Position.CENTRAL_DEFENDER;
            case 2, 3 -> Position.LATERAL_DEFENDER;
            case 4, 5 -> Position.MIDFIELDER;
            default   -> Position.FORWARD;
          }
        ),
        row,
        0
      );
    }

    if (CommonFields.getDistribution() == Distribution.MIX_BY_SKILL_POINTS) {
      for (int column = 0; column < Constants.TEAMS_TOTAL; column++) {
        table.setValueAt(column == 0 ? Constants.MAP_POSITIONS.get(Position.GOALKEEPER) : "Puntuación del equipo", table.getRowCount() + column - 2, 0);
      }

      return;
    }

    table.setValueAt(Constants.MAP_POSITIONS.get(Position.GOALKEEPER), table.getRowCount() - 1, 0);
  }

  /**
   * Overrides the default table cell renderer in order to fit the program aesthetics, including text alignment and background & foreground colors.
   *
   * <p>Cell (0,0) is unused and has a medium green background.
   *
   * <p>Row 0 and column 0 have dark green background and white foreground. The remaining cells will have black foreground.
   *
   * <p>The background color will be medium green if the cell shows any skill points related information. If the cell contains an anchored player name, its background will be the corresponding from the anchorages
   * colors array. If not, its background will be light green.
   *
   * <p>The cell text will be centered if it shows any skill points related information or a team name. Otherwise, it will be left-aligned.
   */
  private void overrideTableCellRenderer() {
    view.getTable()
        .setDefaultRenderer(
          Object.class,
          new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable myTable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
              JComponent component = (JComponent) super.getTableCellRendererComponent(myTable, value, isSelected, hasFocus, row, column);

              boolean mixBySkill = (CommonFields.getDistribution() == Distribution.MIX_BY_SKILL_POINTS) && (row == (view.getTable().getRowCount() - 1));

              component.setOpaque(false);
              component.setBorder(new EmptyBorder(Constants.INSETS_GENERAL));

              if (row == 0 && column == 0) {
                component.setBackground(Constants.COLOR_GREEN_MEDIUM);

                return component;
              }

              if (row == 0) {
                component.setBackground(Constants.COLOR_GREEN_DARK);
                component.setForeground(Color.WHITE);

                ((DefaultTableCellRenderer) component).setHorizontalAlignment(SwingConstants.CENTER);

                return component;
              }

              if (column == 0) {
                if (mixBySkill) {
                  component.setBackground(Constants.COLOR_GREEN_MEDIUM);
                  component.setForeground(Color.WHITE);

                  ((DefaultTableCellRenderer) component).setHorizontalAlignment(SwingConstants.CENTER);

                  return component;
                }

                component.setBackground(Constants.COLOR_GREEN_DARK);
                component.setForeground(Color.WHITE);

                ((DefaultTableCellRenderer) component).setHorizontalAlignment(SwingConstants.LEFT);

                return component;
              }

              if (mixBySkill) {
                component.setBackground(Constants.COLOR_GREEN_MEDIUM);
                component.setForeground(Color.WHITE);

                ((DefaultTableCellRenderer) component).setHorizontalAlignment(SwingConstants.CENTER);

                return component;
              }

              Player playerOnCell = CommonFunctions.retrieveOptional(CommonFields.getPlayersSets()
                                                                                 .values()
                                                                                 .stream()
                                                                                 .flatMap(List::stream)
                                                                                 .filter(player -> player.getName().equals(value))
                                                                                 .findFirst());

              int anchorageIndex = CommonFields.getAnchorages()
                                               .stream()
                                               .filter(anchorage -> anchorage.getPlayers().contains(playerOnCell))
                                               .findFirst()
                                               .map(CommonFields.getAnchorages()::indexOf)
                                               .orElse(Constants.PLAYER_NO_ANCHORAGE_ASSIGNED);

              component.setBackground(anchorageIndex == Constants.PLAYER_NO_ANCHORAGE_ASSIGNED ? Constants.COLOR_GREEN_LIGHT_WHITE : Constants.COLORS_ANCHORAGES.get(anchorageIndex));
              component.setForeground(Color.BLACK);

              ((DefaultTableCellRenderer) component).setHorizontalAlignment(SwingConstants.LEFT);

              return component;
            }

            @Override
            protected void paintComponent(Graphics graphics) {
              Graphics2D graphics2d = (Graphics2D) graphics.create();

              graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
              graphics2d.setColor(getBackground());
              graphics2d.fillRoundRect(0, 0, (getWidth() - 1), (getHeight() - 1), Constants.ROUNDED_BORDER_ARC_TABLE_CELLS, Constants.ROUNDED_BORDER_ARC_TABLE_CELLS);

              super.paintComponent(graphics2d);
            }
          }
        );
  }

  /**
   * Adjusts the cells size to fit the biggest content shown in the table.
   */
  private void adjustTableCells() {
    int maxCellWidth  = 0;
    int maxCellHeight = 0;

    for (int row = 0; row < table.getRowCount(); row++) {
      for (int column = 0; column < table.getColumnCount(); column++) {
        Component cellComponent = table.prepareRenderer(table.getCellRenderer(row, column), row, column);

        maxCellWidth = Math.max(maxCellWidth, (cellComponent.getPreferredSize().width + table.getIntercellSpacing().width));
        maxCellHeight = Math.max(maxCellHeight, (cellComponent.getPreferredSize().height + table.getIntercellSpacing().height));

        table.getColumnModel().getColumn(column).setPreferredWidth(maxCellWidth);
      }

      table.setRowHeight(row, maxCellHeight);
    }
  }
}