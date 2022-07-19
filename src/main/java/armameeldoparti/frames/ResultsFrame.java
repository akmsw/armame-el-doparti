package armameeldoparti.frames;

import armameeldoparti.utils.BackButton;
import armameeldoparti.utils.BySkillMixer;
import armameeldoparti.utils.Main;
import armameeldoparti.utils.Player;
import armameeldoparti.utils.Position;
import armameeldoparti.utils.RandomMixer;
import armameeldoparti.utils.Team;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import net.miginfocom.swing.MigLayout;

/**
 * Clase correspondiente a la ventana de resultados de distribución de jugadores.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 06/03/2021
 */
public class ResultsFrame extends JFrame {

  // ---------------------------------------- Constantes públicas -------------------------------

  /**
   * Tamaño de ancho (en píxeles) fijo para las celdas de la tabla de resultados.
   * Valor ajustado a fuente del programa teniendo en cuenta su tamaño y la cantidad
   * máxima de caracteres en los nombres de los jugadores.
   */
  private static final int FIXED_CELL_WIDTH = 250;

  private static final int TABLE_COLUMNS = 3;

  /**
   * Colores utilizados para los distintos anclajes en la tabla de resultados.
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

  private String frameTitle;

  private JFrame previousFrame;

  private JPanel panel;

  private JTable table;

  private transient Team team1;
  private transient Team team2;

  private transient List<Team> teams;

  private transient RandomMixer randomMixer;
  private transient BySkillMixer bySkillMixer;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye una ventana de resultados.
   *
   * @param previousFrame Ventana fuente que crea la ventana ResultFrame.
   */
  public ResultsFrame(JFrame previousFrame) {
    this.previousFrame = previousFrame;

    team1 = new Team();
    team2 = new Team();
    teams = new ArrayList<>();

    teams.add(team1);
    teams.add(team2);

    randomMixer = new RandomMixer();
    bySkillMixer = new BySkillMixer();

    if (Main.getDistribution() == Main.RANDOM_MIX) {
      setFrameTitle("Aleatorio - ");

      table = new JTable(Main.PLAYERS_PER_TEAM + 1, TABLE_COLUMNS);

      if (Main.thereAreAnchorages()) {
        setFrameTitle(getFrameTitle().concat("Con anclajes"));
        teams = randomMixer.withAnchorages(teams);
      } else {
        setFrameTitle(getFrameTitle().concat("Sin anclajes"));
        teams = randomMixer.withoutAnchorages(teams);
      }
    } else {
      setFrameTitle("Por puntuaciones - ");

      table = new JTable(Main.PLAYERS_PER_TEAM + 2, TABLE_COLUMNS);

      if (Main.thereAreAnchorages()) {
        setFrameTitle(getFrameTitle().concat("Con anclajes"));
        teams = bySkillMixer.withAnchorages(teams);
      } else {
        setFrameTitle(getFrameTitle().concat("Sin anclajes"));
        teams = bySkillMixer.withoutAnchorages(teams);
      }
    }

    initializeInterface();
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  // --------------------------------------------- Getters --------------------------------------

  /**
   * Retorna el título de la ventana.
   *
   * @return El título de la ventana.
   */
  public String getFrameTitle() {
    return frameTitle;
  }

  // --------------------------------------------- Setters --------------------------------------

  /**
   * Actualiza el título de la ventana.
   *
   * @param frameTitle El nuevo título para la ventana.
   */
  public void setFrameTitle(String frameTitle) {
    this.frameTitle = frameTitle;
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Inicializa y muestra la interfaz gráfica de esta ventana.
   */
  private void initializeInterface() {
    panel = new JPanel(new MigLayout("wrap"));

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(MainFrame.ICON.getImage());
    setTitle(getFrameTitle());
    setResizable(false);
    addTable();
    addButtons();
    add(panel);
    fillTable();

    // Ajuste del ancho de las celdas
    for (int column = 0; column < table.getColumnCount(); column++) {
      table.getColumnModel()
           .getColumn(column)
           .setPreferredWidth(FIXED_CELL_WIDTH);
    }

    // Ajuste del alto de las celdas
    for (int i = 0; i < table.getRowCount(); i++) {
      int rowHeight = table.getRowHeight();

      for (int j = 0; j < table.getColumnCount(); j++) {
        Component component = table.prepareRenderer(table.getCellRenderer(i, j), i, j);
        rowHeight = Math.max(rowHeight, component.getPreferredSize().height);
      }

      table.setRowHeight(i, rowHeight);
    }

    pack();
    setLocationRelativeTo(null);
  }

  /**
   * Coloca en el panel principal de la ventana los botones de navegación
   * y de redistribución en caso de ser necesario.
   */
  private void addButtons() {
    BackButton backButton = new BackButton(this, previousFrame, null);

    // Se eliminan todos los equipos asignados en caso de querer retroceder
    backButton.addActionListener(e -> {
      resetTeams();

      previousFrame.setVisible(true);

      dispose();
    });

    if (Main.getDistribution() == Main.RANDOM_MIX) {
      JButton remixButton = new JButton("Redistribuir");

      remixButton.addActionListener(e -> {
        resetTeams();

        teams = Main.thereAreAnchorages()
                ? randomMixer.withAnchorages(teams) : randomMixer.withoutAnchorages(teams);

        fillTable();
      });

      panel.add(remixButton, "growx");
    }

    panel.add(backButton, "growx");
  }

  /**
   * Coloca en el panel principal de la ventana la tabla donde se mostrarán los jugadores
   * y sus respectivas posiciones para cada equipo armado.
   */
  private void addTable() {
    setTableFormat();

    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.setCellSelectionEnabled(false);
    table.setRowSelectionAllowed(false);
    table.setColumnSelectionAllowed(false);
    table.setBorder(BorderFactory.createLineBorder(Main.DARK_GREEN));
    table.setEnabled(false);
    table.setVisible(true);
    ((DefaultTableCellRenderer) table.getTableHeader()
                                     .getDefaultRenderer())
                                     .setHorizontalAlignment(SwingConstants.CENTER);

    fillTableFields();

    panel.add(table, "push, grow, span, center");
  }

  /**
   * Ajusta el formato de centrado de texto y de colores de fondo y letra
   * para cada celda de la tabla de resultados.
   */
  private void setTableFormat() {
    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      /**
       * Configura el color de fondo y de letra de las casillas de la tabla.
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

        boolean byScoresMixFlag = Main.getDistribution() == Main.BY_SCORES_MIX
                                  && row == table.getRowCount() - 1;

        /*

         */
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
    table.setValueAt("EQUIPO #1", 0, 1);
    table.setValueAt("EQUIPO #2", 0, 2);

    for (int i = 1; i < table.getRowCount() - 1; i++) {
      if (i == 1) {
        table.setValueAt(Main.getPositionsMap()
                             .get(Position.CENTRAL_DEFENDER), i, 0);
      } else if (i < 4) {
        table.setValueAt(Main.getPositionsMap()
                             .get(Position.LATERAL_DEFENDER), i, 0);
      } else if (i < 6) {
        table.setValueAt(Main.getPositionsMap()
                             .get(Position.MIDFIELDER), i, 0);
      } else if (i < 7) {
        table.setValueAt(Main.getPositionsMap()
                             .get(Position.FORWARD), i, 0);
      }
    }

    if (Main.getDistribution() == Main.BY_SCORES_MIX) {
      table.setValueAt(Main.getPositionsMap()
                           .get(Position.GOALKEEPER),
                       table.getRowCount() - 2, 0);
      table.setValueAt("PUNTAJE DEL EQUIPO", table.getRowCount() - 1, 0);
    } else {
      table.setValueAt(Main.getPositionsMap()
                           .get(Position.GOALKEEPER),
                       table.getRowCount() - 1, 0);
    }
  }

  /**
   * Llena la tabla con los datos cargados de los jugadores en cada equipo.
   */
  private void fillTable() {
    int column = 1;

    /*
      * ¡¡¡IMPORTANTE!!!
      *
      * Aquí se llenan los recuadros de la tabla confiando en el
      * orden en el que se escribieron las posiciones en las filas
      * de la columna 0 (el mismo orden del enum de posiciones).
      * Es decir, los primeros jugadores a cargar serán defensores
      * centrales, luego defensores laterales, mediocampistas,
      * delanteros y por último arqueros. Si se cambian de lugar las
      * etiquetas de las posiciones en la tabla, deberá cambiarse esta
      * manera de llenarla, ya que no se respetará el nuevo orden establecido.
      */
    for (Team team : teams) {
      int row = 1;

      for (Position position : Position.values()) {
        for (Player player : team.getPlayers().get(position)) {
          table.setValueAt(player.getName(), row++, column);
        }
      }

      column++;
    }

    if (Main.getDistribution() == Main.BY_SCORES_MIX) {
      table.setValueAt(teams.get(0)
                            .getPlayers()
                            .values()
                            .stream()
                            .flatMap(List::stream)
                            .mapToInt(Player::getSkill)
                            .reduce(0, Math::addExact),
                       table.getRowCount() - 1, 1);
      table.setValueAt(teams.get(1)
                            .getPlayers()
                            .values()
                            .stream()
                            .flatMap(List::stream)
                            .mapToInt(Player::getSkill)
                            .reduce(0, Math::addExact),
                       table.getRowCount() - 1, 2);
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