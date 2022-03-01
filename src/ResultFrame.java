/**
 * Clase correspondiente a la ventana de resultados
 * de distribución de jugadores.
 * 
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 3.0.0
 * 
 * @since 06/03/2021
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;

import net.miginfocom.swing.MigLayout;

public class ResultFrame extends JFrame {

    /* ---------------------------------------- Campos privados ---------------------------------- */

    private ArrayList<Player> team1, team2;

    private ArrayList<ArrayList<Player>> teams;

    private JButton mainMenuButton;

    private JFrame previousFrame;

    private JPanel panel;

    private JTable table;

    private Random randomGenerator;
    
    private BackButton backButton;
    
    private InputFrame inputFrame;

    /**
     * Creación de la ventana de resultados.
     * 
     * @param inputFrame    La ventana de ingreso de datos, de la cual se obtendrá
     *                      información importante.
     * @param previousFrame La ventana fuente que crea la ventana ResultFrame.
     */
    public ResultFrame(InputFrame inputFrame, JFrame previousFrame) {
        this.inputFrame = inputFrame;
        this.previousFrame = previousFrame;

        randomGenerator = new Random();

        table = new JTable((inputFrame.playersAmount + 1), 3);

        team1 = new ArrayList<>();
        team2 = new ArrayList<>();

        teams = new ArrayList<>();

        teams.add(team1);
        teams.add(team2);

        if (inputFrame.distribution == 0)
            randomMix(inputFrame.thereAreAnchorages());
        else
            ratingsMix(inputFrame.thereAreAnchorages());
        
        initializeComponents();
    }

    /* ---------------------------------------- Métodos privados --------------------------------- */

    /**
     * Este método inicializa los componentes de la ventana de resultados.
     */
    private void initializeComponents() {
        panel = new JPanel(new MigLayout("wrap"));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(MainFrame.icon.getImage());

        addTable();
        addButtons();
        add(panel);

        fillTable();

        // Ajuste del ancho de las celdas para apreciar todo el contenido de las mismas
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);

            /*
             * Valor ajustado a fuente del programa teniendo en cuenta
             * su tamaño y la cantidad máxima de caracteres en los
             * nombres de los jugadores.
             */
            tableColumn.setPreferredWidth(200);
        }

        // Ajuste del alto de las celdas para apreciar todo el contenido de las mismas
        for (int i = 0; i < table.getRowCount(); i++) {
            int rowHeight = table.getRowHeight();

            for (int j = 0; j < table.getColumnCount(); j++) {
                Component comp = table.prepareRenderer(table.getCellRenderer(i, j), i, j);

                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }

            table.setRowHeight(i, rowHeight);
        }

        pack();

        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Este método se encarga de colocar en el panel principal
     * del frame los botones de navegación entre ventanas y de
     * redistribución en caso de ser necesario.
     */
    private void addButtons() {
        mainMenuButton = new JButton("Volver al menú principal");

        backButton = new BackButton(ResultFrame.this, previousFrame);

        mainMenuButton.addActionListener(new ActionListener() {
            /**
             * Este método devuelve al usuario al menú principal
             * de la aplicación, eliminando la ventana de resultados.
             * 
             * @param e Evento de click.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTeams();

                ResultFrame.this.dispose();
                inputFrame.dispose();
                previousFrame.dispose();

                MainFrame mainFrame = new MainFrame();

                mainFrame.setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            /**
             * Este método togglea la visibilidad de las ventanas.
             * Se sobreescribe para eliminar todos los equipos
             * asignados en caso de querer retroceder.
             * 
             * @param e Evento de click.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTeams();

                previousFrame.setVisible(true);

                ResultFrame.this.dispose();
            }
        });

        if (inputFrame.distribution == 0) {
            JButton remixButton = new JButton("Redistribuir");

            remixButton.addActionListener(new ActionListener() {
                /**
                 * Este método resetea los equipos de los jugadores
                 * y vuelve a mezclarlos de manera aleatoria
                 * considerando los anclajes ingresados por el usuario.
                 * 
                 * @param e Evento de click.
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    resetTeams();

                    randomMix(inputFrame.thereAreAnchorages());

                    fillTable();
                }
            });

            panel.add(remixButton, "growx");
        }

        panel.add(backButton, "growx");
        panel.add(mainMenuButton, "growx");
    }

    /**
     * Este método se encarga de colocar en el panel principal
     * del frame la tabla donde se mostrarán los jugadores y
     * sus respectivas posiciones para cada equipo armado.
     */
    private void addTable() {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            /**
             * Este método se encarga de setear el color de fondo
             * y de letra de las casillas de la tabla.
             * 
             * @param table Tabla fuente.
             * @param value -.
             * @param isSelected Si la celda está seleccionada.
             * @param hasFocus Si la celda está en foco.
             * @param row Coordenada de fila de la celda.
             * @param column Coordenada de columna de la celda.
             */
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

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
     * Este método se encarga de llenar la tabla con los
     * datos cargados de los jugadores en cada equipo.
     */
    private void fillTable() {
        // Labels de formato
        table.setValueAt("EQUIPO #1", 0, 1);
        table.setValueAt("EQUIPO #2", 0, 2);

        int cdSetLength_half = inputFrame.playersSets.get(Position.CENTRAL_DEFENDER).length / 2;
        int ldSetLength_half = inputFrame.playersSets.get(Position.LATERAL_DEFENDER).length / 2;
        int mfSetLength_half = inputFrame.playersSets.get(Position.MIDFIELDER).length / 2;
        int fwSetLength_half = inputFrame.playersSets.get(Position.FORWARD).length / 2;
        
        for (int i = 0; i < cdSetLength_half; i++)
            table.setValueAt("DEFENSOR CENTRAL", (i + 1), 0);
        
        for (int i = 0; i < ldSetLength_half; i++)
            table.setValueAt("DEFENSOR LATERAL", (i + 1 + cdSetLength_half), 0);
        
        for (int i = 0; i < mfSetLength_half; i++)
            table.setValueAt("MEDIOCAMPISTA", (i + 1 + cdSetLength_half + ldSetLength_half), 0);
        
        for (int i = 0; i < fwSetLength_half; i++)
            table.setValueAt("DELANTERO", (i + 1 + cdSetLength_half + ldSetLength_half + mfSetLength_half), 0);
        
        table.setValueAt("ARQUERO", (1 + cdSetLength_half + ldSetLength_half + mfSetLength_half + fwSetLength_half), 0);

        /*
         *                      ¡¡¡IMPORTANTE!!!
         * 
         * Aquí se llenan los recuadros de la tabla confiando en el
         * orden en el que se escribieron las posiciones en las filas
         * de la columna 0 (el mismo orden del enum de posiciones).
         * Es decir, los primeros jugadores a cargar serán defensores
         * centrales, luego defensores laterales, mediocampistas,
         * delanteros y por último arqueros. Si se cambian de lugar los
         * labels de las posiciones en la tabla, deberá cambiarse esta
         * manera de llenarla, ya que no se respetará el nuevo orden establecido.
         * 
         * TODO: Almacenar datos en un arreglo y alimentar la tabla con el mismo
         *       para lograr mayor abstracción y eficiencia.
         */

        int row = 1;

        for (int i = 0; i < teams.size(); i++) {
            for (Player player : teams.get(i))
                table.setValueAt(player.getName(), row++, (i + 1));
            
            row = 1;
        }
    }

    /**
     * Este método se encarga de repartir de manera aleatoria los
     * jugadores en dos equipos.
     * 
     * @param anchorages Si la mezcla aleatoria debe tener
     *                   en cuenta anclajes establecidos.
     */
    private void randomMix(boolean anchorages) {
        if (anchorages) {
            setTitle("Aleatorio - Con anclajes - Fútbol " + inputFrame.playersAmount);

            // TODO
        } else {
            setTitle("Aleatorio - Sin anclajes - Fútbol " + inputFrame.playersAmount);

            /*
             * Se elige un número aleatorio entre 0 y 1 (+1)
             * para asignarle como equipo a un conjunto de jugadores,
             * y el resto tendrá asignado el equipo opuesto.
             * Se recorre la mitad de los jugadores del set de manera
             * aleatoria y se les asigna a los jugadores escogidos
             * como equipo el número aleatorio generado al principio. 
             * A medida que se van eligiendo jugadores, su índice en
             * el arreglo se almacena para evitar reasignarle un equipo.
             * Al resto de jugadores que quedaron sin elegir de manera
             * aleatoria (aquellos con team == 0) del mismo grupo, se
             * les asigna el número de equipo opuesto.
             */

            int index;

            ArrayList<Integer> alreadySetted = new ArrayList<>();

            for (Position position : Position.values()) {
                int teamSubset1 = randomGenerator.nextInt(2) + 1;
                int teamSubset2 = (teamSubset1 == 1) ? 2 : 1;

                Player[] set = inputFrame.playersSets.get(position);

                for (int j = 0; j < (set.length / 2); j++) {
                    do {
                        index = randomGenerator.nextInt(set.length);
                    } while (alreadySetted.contains(index));
        
                    alreadySetted.add(index);
        
                    set[index].setTeam(teamSubset1);

                    if (teamSubset1 == 1)
                        team1.add(set[index]);
                    else
                        team2.add(set[index]);
                }

                for (Player player : set)
                    if (player.getTeam() == 0) {
                        player.setTeam(teamSubset2);

                        if (teamSubset2 == 1)
                            team1.add(player);
                        else
                            team2.add(player);
                    }
                
                alreadySetted.clear();
            }
        }
    }

    /**
     * Este método se encarga de repartir los jugadores en dos
     * equipos de la manera más equitativa posible en base a
     * los puntajes ingresados por el usuario.
     * 
     * @param anchorages Si la mezcla aleatoria debe tener
     *                   en cuenta anclajes establecidos.
     */
    private void ratingsMix(boolean anchorages) {
        if (anchorages) {
            setTitle("Por puntajes - Con anclajes - Fútbol " + inputFrame.playersAmount);
        } else {
            setTitle("Por puntajes - Sin anclajes - Fútbol " + inputFrame.playersAmount);
        }
    }

    /**
     * Este método se encarga de resetear los equipos
     * de todos los jugadores, y vaciar los arreglos
     * representativos de cada equipo.
     */
    private void resetTeams() {
        for (Map.Entry<Position, Player[]> ps : inputFrame.playersSets.entrySet())
            for (Player p : ps.getValue())
                p.setTeam(0);
        
        team1.clear();
        team2.clear();
    }
}