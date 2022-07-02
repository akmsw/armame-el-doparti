package armameeldoparti.frames;

import armameeldoparti.utils.BackButton;
import armameeldoparti.utils.Player;
import armameeldoparti.utils.Position;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
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
public class ResultFrame extends JFrame {

    // ---------------------------------------- Constantes privadas ------------------------------

    /**
     * Configuración utilizada frecuentemente.
     */
    private static final String GROWX = "growx";

    /**
     * Tamaño de ancho (en píxeles) fijo para las celdas de la tabla de resultados.
     */
    private static final int FIXED_CELL_WIDTH = 250;

    /**
     * Cantidad de columnas para la tabla de resultados.
     */
    private static final int TABLE_COLUMNS = 3;

    // ---------------------------------------- Campos privados ----------------------------------

    private transient ArrayList<Player> team1;
    private transient ArrayList<Player> team2;

    private transient ArrayList<ArrayList<Player>> teams;

    private JFrame previousFrame;

    private JPanel panel;

    private JTable table;

    private Random randomGenerator;

    private String frameTitle;

    private InputFrame inputFrame;

    // ---------------------------------------- Constructor --------------------------------------

    /**
     * Construye una ventana de resultados.
     *
     * @param inputFrame    Ventana de ingreso de datos, de la cual se obtendrá información importante.
     * @param previousFrame Ventana fuente que crea la ventana ResultFrame.
     */
    public ResultFrame(InputFrame inputFrame, JFrame previousFrame) {
        this.inputFrame = inputFrame;
        this.previousFrame = previousFrame;

        randomGenerator = new Random();

        table = new JTable(inputFrame.getPlayersPerTeam() + 1, TABLE_COLUMNS);

        team1 = new ArrayList<>();
        team2 = new ArrayList<>();
        teams = new ArrayList<>();

        teams.add(team1);
        teams.add(team2);

        if (inputFrame.getDistribution() == 0) {
            randomMix(inputFrame.thereAreAnchorages());
        } else {
            ratingsMix(inputFrame.thereAreAnchorages());
        }

        initializeComponents();
    }

    // ---------------------------------------- Métodos privados ---------------------------------

    /**
     * Inicializa los componentes de la ventana de resultados.
     */
    private void initializeComponents() {
        panel = new JPanel(new MigLayout("wrap"));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(MainFrame.ICON.getImage());

        addTable();
        addButtons();
        add(panel);

        fillTable();

        // Ajuste del ancho de las celdas
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);

            /*
             * Valor ajustado a fuente del programa teniendo en cuenta
             * su tamaño y la cantidad máxima de caracteres en los
             * nombres de los jugadores.
             */
            tableColumn.setPreferredWidth(FIXED_CELL_WIDTH);
        }

        // Ajuste del alto de las celdas
        for (int i = 0; i < table.getRowCount(); i++) {
            int rowHeight = table.getRowHeight();

            for (int j = 0; j < table.getColumnCount(); j++) {
                Component comp = table.prepareRenderer(table.getCellRenderer(i, j), i, j);

                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }

            table.setRowHeight(i, rowHeight);
        }

        frameTitle = frameTitle.concat("Fútbol " + inputFrame.getPlayersPerTeam());

        setTitle(frameTitle);
        setResizable(false);

        pack();

        setLocationRelativeTo(null);
    }

    /**
     * Coloca en el panel principal de la ventana los botones de navegación
     * y de redistribución en caso de ser necesario.
     */
    private void addButtons() {
        JButton mainMenuButton = new JButton("Volver al menú principal");

        BackButton backButton = new BackButton(ResultFrame.this, previousFrame);

        mainMenuButton.addActionListener(e -> {
            resetTeams();

            ResultFrame.this.dispose();
            inputFrame.dispose();
            previousFrame.dispose();

            MainFrame mainFrame = new MainFrame();

            mainFrame.setVisible(true);
        });

        // Se eliminan todos los equipos asignados en caso de querer retroceder
        backButton.addActionListener(e -> {
            resetTeams();

            previousFrame.setVisible(true);

            ResultFrame.this.dispose();
        });

        if (inputFrame.getDistribution() == 0) {
            JButton remixButton = new JButton("Redistribuir");

            remixButton.addActionListener(e -> {
                resetTeams();

                randomMix(inputFrame.thereAreAnchorages());

                fillTable();
            });

            panel.add(remixButton, GROWX);
        }

        panel.add(backButton, GROWX);
        panel.add(mainMenuButton, GROWX);
    }

    /**
     * Coloca en el panel principal de la ventana la tabla donde se mostrarán los jugadores
     * y sus respectivas posiciones para cada equipo armado.
     */
    private void addTable() {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
            public Component getTableCellRendererComponent(JTable myTable, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(myTable, value, isSelected,
                                                                        hasFocus, row, column);

                /*
                 * Fila 0 y columna 0 tendrán fondo verde con letras blancas,
                 * el resto tendrá fondo blanco con letras negras.
                 */
                if ((row == 0) || (column == 0)) {
                    c.setBackground(Main.BUTTONS_BG_COLOR);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        });

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setBorder(BorderFactory.createLineBorder(Main.BUTTONS_BG_COLOR));
        table.setEnabled(false);
        table.setVisible(true);

        panel.add(table, "push, grow, span, center");
    }

    /**
     * Llena la tabla con los datos cargados de los jugadores en cada equipo.
     */
    private void fillTable() {
        for (int i = 0; i < 2; i++) {
            table.setValueAt("EQUIPO #" + (i + 1), 0, i + 1);
        }

        int halfCDSetLength = inputFrame.getPlayersMap().get(Position.CENTRAL_DEFENDER).size() / 2;
        int halfLDSetLength = inputFrame.getPlayersMap().get(Position.LATERAL_DEFENDER).size() / 2;
        int halfMFSetLength = inputFrame.getPlayersMap().get(Position.MIDFIELDER).size() / 2;
        int halfFWSetLength = inputFrame.getPlayersMap().get(Position.FORWARD).size() / 2;

        for (int i = 0; i < halfCDSetLength; i++) {
            table.setValueAt(Main.getPositionsMap().get(Position.CENTRAL_DEFENDER), i + 1, 0);
        }

        for (int i = 0; i < halfLDSetLength; i++) {
            table.setValueAt(Main.getPositionsMap().get(Position.LATERAL_DEFENDER), i + 1 + halfCDSetLength, 0);
        }

        for (int i = 0; i < halfMFSetLength; i++) {
            table.setValueAt(Main.getPositionsMap().get(Position.MIDFIELDER),
                             i + 1 + halfCDSetLength + halfLDSetLength, 0);
        }

        for (int i = 0; i < halfFWSetLength; i++) {
            table.setValueAt(Main.getPositionsMap().get(Position.FORWARD),
                             i + 1 + halfCDSetLength + halfLDSetLength + halfMFSetLength, 0);
        }

        table.setValueAt(Main.getPositionsMap().get(Position.GOALKEEPER),
                         1 + halfCDSetLength + halfLDSetLength + halfMFSetLength + halfFWSetLength, 0);

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

        for (int i = 0; i < teams.size(); i++) {
            int row = 1;

            for (Player player : teams.get(i)) {
                table.setValueAt(player.getName(), row++, i + 1);
            }
        }
    }

    /**
     * Reparte los jugadores de manera aleatoria en dos equipos.
     *
     * @param anchorages Si la mezcla aleatoria debe tener en cuenta anclajes establecidos.
     */
    private void randomMix(boolean anchorages) {
        frameTitle = "Aleatorio - ";

        if (anchorages) {
            frameTitle = frameTitle.concat("Con anclajes - ");
        } else {
            frameTitle = frameTitle.concat("Sin anclajes - ");

            int index;

            ArrayList<Integer> alreadySetted = new ArrayList<>();

            for (Position position : Position.values()) {
                /*
                 * Se elige un número aleatorio entre 0 y 1 (+1)
                 * para asignarle como equipo a un conjunto de jugadores,
                 * y el resto tendrá asignado el equipo opuesto.
                 * Se recorre la mitad de los jugadores del conjunto de manera
                 * aleatoria y se les asigna a los jugadores escogidos
                 * como equipo el número aleatorio generado al principio.
                 * A medida que se van eligiendo jugadores, su índice en
                 * el arreglo se almacena para evitar reasignarle un equipo.
                 * Al resto de jugadores que quedaron sin elegir de manera
                 * aleatoria (aquellos con team == 0) del mismo grupo, se
                 * les asigna el número de equipo opuesto.
                 */
                int teamSubset1 = randomGenerator.nextInt(2) + 1;
                int teamSubset2 = (teamSubset1 == 1) ? 2 : 1;

                ArrayList<Player> set = inputFrame.getPlayersMap().get(position);

                for (int j = 0; j < (set.size() / 2); j++) {
                    do {
                        index = randomGenerator.nextInt(set.size());
                    } while (alreadySetted.contains(index));

                    alreadySetted.add(index);

                    set.get(index).setTeam(teamSubset1);

                    (teamSubset1 == 1 ? team1 : team2).add(set.get(index));
                }

                for (Player p : set.stream().filter(p -> p.getTeam() == 0).toList()) {
                    p.setTeam(teamSubset2);

                    (teamSubset2 == 1 ? team1 : team2).add(p);
                }

                alreadySetted.clear();
            }
        }
    }

    /**
     * Reparte los jugadores en dos equipos de la manera más equitativa posible
     * en base a los puntajes ingresados por el usuario.
     *
     * @param anchorages Si la mezcla por puntajes debe tener en cuenta anclajes establecidos.
     */
    private void ratingsMix(boolean anchorages) {
        frameTitle = "Por puntajes - ";

        if (anchorages) {
            frameTitle = frameTitle.concat("Con anclajes - ");
        } else {
            frameTitle = frameTitle.concat("Sin anclajes - ");

            // working here...
        }
    }

    /**
     * Reinicia los equipos de todos los jugadores y vacía
     * los arreglos representativos de cada equipo.
     */
    private void resetTeams() {
        for (Map.Entry<Position, ArrayList<Player>> ps : inputFrame.getPlayersMap().entrySet()) {
            for (Player p : ps.getValue()) {
                p.setTeam(0);
            }
        }

        team1.clear();
        team2.clear();
    }
}
