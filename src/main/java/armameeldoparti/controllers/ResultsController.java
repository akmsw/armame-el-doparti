package armameeldoparti.controllers;

import armameeldoparti.interfaces.Controller;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.Team;
import armameeldoparti.utils.BySkillsMixer;
import armameeldoparti.utils.Main;
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
 * Clase correspondiente al controlador de la ventana de muestra de resultados.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 26/07/2022
 */
public class ResultsController implements Controller {

  // ---------------------------------------- Constantes privadas -------------------------------

  private static final int TABLE_COLUMNS = 3;

  /**
   * Arreglo de colores utilizados para los distintos anclajes en la tabla de resultados.
   */
  private static final Color[] ANCHORAGES_COLORS = {
    new Color(255, 204, 153),
    new Color(184, 224, 227),
    new Color(220, 206, 235),
    new Color(195, 235, 198),
    new Color(151, 197, 216),
    new Color(219, 220, 218)
  };

  // ---------------------------------------- Campos privados -----------------------------------

  private List<Team> teams;

  private RandomMixer randomMixer = new RandomMixer();
  private BySkillsMixer bySkillsMixer = new BySkillsMixer();

  private ResultsView resultsView;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye el controlador para la vista de resultados.
   *
   * @param resultsView Vista a controlar.
   */
  public ResultsController(ResultsView resultsView) {
    this.resultsView = resultsView;
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  /**
   * Hace visible la ventana controlada.
   */
  @Override
  public void showView() {
    resultsView.setLocationRelativeTo(null);
    resultsView.setVisible(true);
  }

  /**
   * Hace invisible la ventana controlada.
   */
  @Override
  public void hideView() {
    resultsView.setVisible(false);
  }

  /**
   * Reinicia la ventana controlada a sus valores por defecto.
   */
  @Override
  public void resetView() {
    resultsView.dispose();
    resultsView = new ResultsView();
  }

  /**
   * Crea los equipos y la tabla donde se mostrarán los resultados dependiendo de la
   * distribución elegida y de si la opción de anclajes fue seleccionada. Aplica el
   * formato necesario para la tabla, llena los campos no variables de la misma y
   * coloca los resultados de la distribución. Finalmente, hace visible la ventana
   * de muestra de resultados.
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
   * Controlador para la pulsación del botón de retorno.
   *
   * <p>Reinicia los equipos, elimina y reinstancia la ventana
   * de ingreso de nombres, y hace visible la correspondiente.
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
   * Controlador para la pulsación del botón de redistribución.
   *
   * <p>Reinicia los equipos, redistribuye los jugadores con el
   * criterio establecido y actualiza la tabla de muestra de
   * resultados.
   */
  public void remixButtonEvent() {
    resetTeams();

    teams = randomMix(Main.thereAreAnchorages());

    updateTable();
  }

  /**
   * Llena la tabla con los datos cargados de los jugadores en cada equipo.
   *
   * <p>Aquí se llenan los recuadros de la tabla confiando en el orden en el
   * que se escribieron las posiciones en las filas de la columna 0
   * (el mismo orden del enum de posiciones).
   * Es decir, los primeros jugadores a cargar serán defensores centrales,
   * luego defensores laterales, mediocampistas, delanteros y por último arqueros.
   *
   * <p>Si se cambian de lugar las etiquetas de las posiciones en la tabla, deberá
   * cambiarse esta manera de llenarla, ya que no se respetará el nuevo orden establecido.
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
   * Distribuye los jugadores de manera aleatoria.
   *
   * @param thereAreAnchorages Si la distribución debe tener en cuenta anclajes.
   *
   * @return Los equipos actualizados con los jugadores distribuidos
   *         con el criterio establecido.
   */
  public List<Team> randomMix(boolean thereAreAnchorages) {
    return thereAreAnchorages
           ? randomMixer.withAnchorages(teams)
           : randomMixer.withoutAnchorages(teams);
  }

  /**
   * Distribuye los jugadores en base a sus puntuaciones.
   *
   * @param thereAreAnchorages Si la distribución debe tener en cuenta anclajes.
   *
   * @return Los equipos actualizados con los jugadores distribuidos
   *         con el criterio establecido.
   */
  public List<Team> bySkillsMix(boolean thereAreAnchorages) {
    return thereAreAnchorages
           ? bySkillsMixer.withAnchorages(teams)
           : bySkillsMixer.withoutAnchorages(teams);
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Ajusta el formato de centrado de texto y de colores de fondo y letra
   * para cada celda de la tabla de resultados.
   *
   * <p>Fila 0 y columna 0 tienen fondo verde oscuro con letras blancas.
   * Las demás celdas tendrán letras negras.
   *
   * <p>El color de fondo será anaranjado si la celda muestra puntajes.
   * Si la celda corresponde a un jugador con un anclaje establecido, se toma
   * como color de fondo el color correspondiente a su número de anclaje.
   *
   * <p>Si la celda muestra un puntaje o un título de equipo, estará centrada.
   * De lo contrario, estará alineada a la izquierda.
   */
  private void setTableFormat() {
    resultsView.getTable().setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      /**
       * Configura el color de fondo y de letra de las casillas de la tabla.
       *
       * @param table      Tabla fuente.
       * @param value      El valor a configurar en la celda.
       * @param isSelected Si la celda está seleccionada.
       * @param hasFocus   Si la celda está en foco.
       * @param row        Coordenada de fila de la celda.
       * @param column     Coordenada de columna de la celda.
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

        c.setBackground(playerOnCell.getAnchor() != 0
                        ? ANCHORAGES_COLORS[playerOnCell.getAnchor() - 1]
                        : Color.WHITE);
        c.setForeground(Color.BLACK);
        ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.LEFT);

        return c;
      }
    });
  }

  /**
   * Llena las celdas fijas de la tabla, cuyos textos no cambian.
   */
  private void fillTableFields() {
    resultsView.getTable()
               .setValueAt("EQUIPO #1", 0, 1);
    resultsView.getTable()
               .setValueAt("EQUIPO #2", 0, 2);

    for (int i = 1; i < resultsView.getTable().getRowCount() - 1; i++) {
      if (i == 1) {
        resultsView.getTable().setValueAt(Main.getPositionsMap()
                             .get(Position.CENTRAL_DEFENDER), i, 0);
      } else if (i < 4) {
        resultsView.getTable().setValueAt(Main.getPositionsMap()
                             .get(Position.LATERAL_DEFENDER), i, 0);
      } else if (i < 6) {
        resultsView.getTable().setValueAt(Main.getPositionsMap()
                             .get(Position.MIDFIELDER), i, 0);
      } else if (i < 7) {
        resultsView.getTable().setValueAt(Main.getPositionsMap()
                             .get(Position.FORWARD), i, 0);
      }
    }

    if (Main.getDistribution() == Main.BY_SKILLS_MIX) {
      resultsView.getTable().setValueAt(Main.getPositionsMap()
                           .get(Position.GOALKEEPER),
                       resultsView.getTable().getRowCount() - 2, 0);
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
   * Reinicia los equipos de todos los jugadores y vacía
   * los arreglos representativos de cada equipo.
   */
  private void resetTeams() {
    teams.forEach(Team::clear);

    Main.getPlayersSets()
        .values()
        .forEach(ps -> ps.forEach(p -> p.setTeam(0)));
  }
}