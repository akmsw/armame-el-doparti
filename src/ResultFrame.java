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
import javax.swing.table.TableCellRenderer;

import net.miginfocom.swing.MigLayout;

public class ResultFrame extends JFrame {

    /* ---------------------------------------- Campos privados ---------------------------------- */

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

        if (inputFrame.distribution == 0)
            randomMix(inputFrame.thereAreAnchorages());
        else
            ratingsMix(inputFrame.thereAreAnchorages());

        fillTable();
        
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

        autoFitTable();

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
        table.setValueAt("EQUIPO #1", 0, 1);
        table.setValueAt("EQUIPO #2", 0, 2);
        table.setValueAt("ARQUERO", 1, 0);

        int cdSetLength_half = inputFrame.playersSets.get(Position.CENTRAL_DEFENDER).length / 2;
        int ldSetLength_half = inputFrame.playersSets.get(Position.LATERAL_DEFENDER).length / 2;
        int mfSetLength_half = inputFrame.playersSets.get(Position.MIDFIELDER).length / 2;
        int fwSetLength_half = inputFrame.playersSets.get(Position.FORWARD).length / 2;
        
        for (int i = 0; i < cdSetLength_half; i++)
            table.setValueAt("DEFENSOR CENTRAL", (i + 2), 0);
        
        for (int i = 0; i < ldSetLength_half; i++)
            table.setValueAt("DEFENSOR LATERAL", (i + 2 + cdSetLength_half), 0);
        
        for (int i = 0; i < mfSetLength_half; i++)
            table.setValueAt("MEDIOCAMPISTA", (i + 2 + cdSetLength_half + ldSetLength_half), 0);
        
        for (int i = 0; i < fwSetLength_half; i++)
            table.setValueAt("DELANTERO", (i + 2 + cdSetLength_half + ldSetLength_half + mfSetLength_half), 0);
    }

    /**
     * Este método se encarga de ajustar las dimensiones de la tabla
     * de manera que todos los elementos se aprecien completamente.
     */
    private void autoFitTable() {
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);

            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);

                Component c = table.prepareRenderer(cellRenderer, row, column);
                
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                
                preferredWidth = Math.max(preferredWidth, width);

                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;

                    break;
                }
            }

            tableColumn.setPreferredWidth(preferredWidth);
        }

        for (int row = 0; row < table.getRowCount(); row++) {
            int rowHeight = table.getRowHeight();

            for (int column = 0; column < table.getColumnCount(); column++) {
                Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);

                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }

            table.setRowHeight(row, rowHeight);
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
            setTitle("MEZCLA ALEATORIA - CON ANCLAJES");
        } else {
            setTitle("MEZCLA ALEATORIA - SIN ANCLAJES");

            for (int i = 0; i < Position.values().length; i++)
                mixAndDistribute(Position.values()[i]);
            
            mixTest();
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
            setTitle("MEZCLA POR PUNTAJES - CON ANCLAJES");
        } else {
            setTitle("MEZCLA POR PUNTAJES - SIN ANCLAJES");
        }
    }

    /**
     * Este método se encarga de distribuir los jugadores
     * de cierta posición de manera equitativa y aleatoria
     * entre los dos equipos.
     * Para esto, se elige un número aleatorio entre 0 y 1 (+1)
     * para asignarle como equipo a un conjunto de jugadores
     * del grupo recibido, y el resto tendrá asignado el
     * equipo opuesto.
     * Se recorre la mitad de los jugadores del set de manera
     * aleatoria y se le asigna el equipo del subset1.
     * A medida que se van eligiendo jugadores, su índice en
     * el arreglo se almacena para evitar reasignarle un equipo.
     * Al resto de jugadores que quedaron sin elegir de manera
     * aleatoria (aquellos con team == 0), se les asigna el
     * número de equipo opuesto.
     * Finalmente, se agregan estos jugadores a los mapas de los
     * equipos.
     * 
     * @param position Posición de los jugadores a distribuir.
     */
    private void mixAndDistribute(Position position) {
        int teamSubset1 = randomGenerator.nextInt(2) + 1;
        int teamSubset2 = (teamSubset1 == 1) ? 2 : 1;
        int index;

        Player[] set = inputFrame.playersSets.get(position);

        ArrayList<Integer> alreadySetted = new ArrayList<>();

        System.out.println("teamSubset1 = " + teamSubset1 + "\nteamSubset2 = " + teamSubset2);

        for (int i = 0; i < (set.length / 2); i++) {
            do {
                index = randomGenerator.nextInt(set.length);
            } while (alreadySetted.contains(index));

            alreadySetted.add(index);

            set[index].setTeam(teamSubset1);

            System.out.println("JUGADOR " + set[index].getName() + " TIENE TEAM " + teamSubset1);
        }

        for (int i = 0; i < set.length; i++)
            if (set[i].getTeam() == 0) {
                set[i].setTeam(teamSubset2);
                System.out.println("JUGADOR " + set[i].getName() + " TENÍA TEAM 0, AHORA TIENE TEAM " + teamSubset2);
            }
    }

    /**
     * Este método se encarga de resetear los equipos de todos los jugadores.
     */
    private void resetTeams() {
        for (Map.Entry<Position, Player[]> ps : inputFrame.playersSets.entrySet())
            for (Player p : ps.getValue())
                p.setTeam(0);
        
        mixTest();
    }

    /**
     * Método de prueba para testear que los jugadores se hayan repartido
     * de la manera correspondiente entre los equipos.
     */
    private void mixTest() {
        System.out.println("//////////////////////////////////////////////");

        for (int i = 0; i < inputFrame.playersSets.size(); i++)
            for (int j = 0; j < inputFrame.playersSets.get(Position.values()[i]).length; j++)
                System.out.println("JUGADOR " + inputFrame.playersSets.get(Position.values()[i])[j].getName()
                        + " (" + inputFrame.playersSets.get(Position.values()[i])[j].getPosition()
                        + " > EQUIPO = " + inputFrame.playersSets.get(Position.values()[i])[j].getTeam());
    }
}