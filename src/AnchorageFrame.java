/**
 * Clase correspondiente a la ventana de anclaje
 * de jugadores
 * 
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 15/03/2021
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class AnchorageFrame extends JFrame {

    // Constantes privadas.
    private static final int FRAME_WIDTH = 402; // Ancho de la ventana.
    private static final int FRAME_HEIGHT = 432; // Alto de la ventana.
    private static final int MAX_ANCHOR = 5; // Máxima cantidad de jugadores por anclaje.
    private static final String FRAME_TITLE = "Anclaje de jugadores";
    private static final Color BG_COLOR = new Color(200, 200, 200); // Color de fondo de la ventana.

    // Campos públicos.
    public static int distribution; // Distribución de jugadores elegida.

    // Campos privados.
    private int anchorageNum = 0; // Número de anclaje.
    private ArrayList<JCheckBox> cdCB, ldCB, mfCB, fwCB, wcCB; // Arreglos de checkboxes de los jugadores separados por
                                                               // posición.
    private ArrayList<ArrayList<JCheckBox>> cbSets; // Arreglo de arreglos de checkboxes de los jugadores.
    private JFrame inputFrame; // Frame de inputs cuya visibilidad será toggleada.
    private JPanel masterPanel, leftPanel, rightPanel; // Paneles contenedores de los componentes de la ventana de
                                                       // anclajes.
    private JButton okButton, newAnchorage, clearAnchorages, deleteAnchorage, deleteLastAnchorage; // Botones de la
                                                                                                   // ventana de
                                                                                                   // anclajes.
    private JTextArea textArea; // Área de texto donde se mostrarán los anclajes en tiempo real.
    private JScrollPane scrollPane; // Scrollpane para el área de texto.
    private ImageIcon smallIcon; // Íconos para la ventana.

    /**
     * Creación de la ventana de anclajes.
     * 
     * @param distribution Distribución de jugadores elegida.
     * @param inputFrame   Ventana cuya visibilidad será toggleada.
     */
    public AnchorageFrame(int distribution, JFrame inputFrame) {
        AnchorageFrame.distribution = distribution;

        this.inputFrame = inputFrame;

        smallIcon = new ImageIcon(MainFrame.iconBall.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        masterPanel = new JPanel(new MigLayout("wrap 2"));
        leftPanel = new JPanel(new MigLayout("wrap 2"));
        rightPanel = new JPanel(new MigLayout());

        cdCB = new ArrayList<>();
        ldCB = new ArrayList<>();
        mfCB = new ArrayList<>();
        fwCB = new ArrayList<>();
        wcCB = new ArrayList<>();

        cbSets = new ArrayList<>();

        cbSets.add(cdCB);
        cbSets.add(ldCB);
        cbSets.add(mfCB);
        cbSets.add(fwCB);
        cbSets.add(wcCB);

        initializeComponents(distribution);
    }

    // ----------------------------------------Métodos privados---------------------------------

    /**
     * Este método se encarga de inicializar los componentes de la ventana de
     * anclaje.
     * 
     * @param distribution Tipo de distribución elegida.
     */
    private void initializeComponents(int distribution) {
        for (int i = 0; i < cbSets.size(); i++)
            fillCBSet(InputFrame.playersSets.get(i), cbSets.get(i));

        addCBSet(leftPanel, cdCB, "DEFENSORES CENTRALES");
        addCBSet(leftPanel, ldCB, "DEFENSORES LATERALES");
        addCBSet(leftPanel, mfCB, "MEDIOCAMPISTAS");
        addCBSet(leftPanel, fwCB, "DELANTEROS");
        addCBSet(leftPanel, wcCB, "COMODINES");

        okButton = new JButton("Finalizar");

        okButton.addActionListener(new ActionListener() {
            /**
             * Este evento hace las validaciones de datos necesarias y, si todo se cumple,
             * hace invisible la ventana de anclaje cuando el usuario hizo los anclajes
             * deseados y está listo para distribuir los jugadores.
             * 
             * @param e Evento de click.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);

                ResultFrame resultFrame = new ResultFrame();

                resultFrame.addWindowListener(new WindowEventsHandler(inputFrame));
                resultFrame.setVisible(true);
            }
        });

        leftPanel.add(okButton, "growx, span");
        leftPanel.setBackground(BG_COLOR);

        textArea = new JTextArea();

        textArea.setBorder(BorderFactory.createBevelBorder(1));
        textArea.setEditable(false);
        textArea.setVisible(true);

        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        newAnchorage = new JButton("Anclar");

        newAnchorage.addActionListener(new ActionListener() {
            /**
             * Este método se encarga de anclar los jugadores cuya checkbox está tildada.
             * Sólo se permitirán hacer anclajes de entre 2 y 5 jugadores. No se podrán
             * hacer anclajes que tengan más de la mitad de jugadores de una misma posición,
             * ya que en ese caso el otro equipo no tendrá la misma cantidad de jugadores en
             * dicha posición.
             * 
             * @param e Evento de click.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validChecksAmount()) {
                    errorMsg("No puede haber más de " + MAX_ANCHOR + " ni menos de 2 jugadores en un mismo anclaje.");
                    return;
                } else if (!isValidAnchorage()) {
                    errorMsg("No puede haber más de la mitad de jugadores de una misma posición en un mismo anclaje.");
                    return;
                }

                anchorageNum++;

                for (int i = 0; i < cbSets.size(); i++)
                    setAnchors(cbSets.get(i), InputFrame.playersSets.get(i));

                updateTextArea();
            }
        });

        deleteAnchorage = new JButton("Borrar un anclaje");

        deleteAnchorage.addActionListener(new ActionListener() {
            /**
             * Este método se encarga de borrar un anclaje en específico señalado por el
             * usuario.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] OPTIONS_DELETE = new String[anchorageNum];

                for (int i = 0; i < anchorageNum; i++)
                    OPTIONS_DELETE[i] = Integer.toString(i + 1);

                int anchor = JOptionPane.showOptionDialog(null, "Seleccione qué anclaje desea borrar",
                        "Antes de continuar...", 2, JOptionPane.QUESTION_MESSAGE, smallIcon, OPTIONS_DELETE,
                        OPTIONS_DELETE[0]) + 1; // + 1 para compensar índice del arreglo.

                // Los que tenían anclaje igual a 'anchor' ahora tienen anclaje '0'.
                for (int i = 0; i < cbSets.size(); i++)
                    changeAnchor(InputFrame.playersSets.get(i), cbSets.get(i), anchor, 0);

                // A los que tienen anclaje desde 'anchor + 1' hasta 'anchorageNum'
                // les decremento en 1 su número de anclaje.
                for (int i = (anchor + 1); i <= anchorageNum; i++)
                    for (int j = 0; j < cbSets.size(); j++)
                        changeAnchor(InputFrame.playersSets.get(j), cbSets.get(j), i, (i - 1));

                anchorageNum--;

                updateTextArea();
            }
        });

        deleteLastAnchorage = new JButton("Borrar último anclaje");

        deleteLastAnchorage.addActionListener(new ActionListener() {
            /**
             * Este método se encarga de borrar el último anclaje realizado.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < cbSets.size(); i++)
                    changeAnchor(InputFrame.playersSets.get(i), cbSets.get(i), anchorageNum, 0);

                anchorageNum--;

                updateTextArea();
            }
        });

        clearAnchorages = new JButton("Limpiar anclajes");

        clearAnchorages.addActionListener(new ActionListener() {
            /**
             * Este método se encarga de borrar todos los anclajes que se hayan generado.
             * 
             * @param e Evento de click.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                anchorageNum = 0;

                for (int i = 0; i < cbSets.size(); i++)
                    setAnchors(cbSets.get(i), InputFrame.playersSets.get(i));

                updateTextArea();
            }
        });

        rightPanel.add(scrollPane, "w 128:213:213, h 260:289:289, growy, wrap");
        rightPanel.add(newAnchorage, "growx, wrap");
        rightPanel.add(deleteAnchorage, "growx, wrap");
        rightPanel.add(deleteLastAnchorage, "growx, wrap");
        rightPanel.add(clearAnchorages, "growx");
        rightPanel.setBackground(BG_COLOR);

        masterPanel.add(leftPanel, "west");
        masterPanel.add(rightPanel, "center, growx, span");
        masterPanel.setBackground(BG_COLOR);

        updateTextArea();

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(FRAME_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(MainFrame.iconBall.getImage());
        add(masterPanel);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Este método se encarga de llenar los arreglos de checkboxes correspondientes
     * a cada posición.
     * 
     * @param playersSet Conjunto de jugadores de donde obtener los nombres.
     * @param cbSet      Conjunto de checkboxes a llenar.
     */
    private void fillCBSet(Player[] playersSet, ArrayList<JCheckBox> cbSet) {
        for (Player player : playersSet)
            cbSet.add(new JCheckBox(player.getName()));
    }

    /**
     * Este método se encarga de colocar en el panel los checkboxes correspondientes
     * a cada posición junto con una etiqueta que los distinga.
     * 
     * @param panel Panel donde se colocarán los checkboxes.
     * @param cbSet Conjunto de checkboxes a colocar.
     * @param title Texto de la etiqueta de acompañamiento.
     */
    private void addCBSet(JPanel panel, ArrayList<JCheckBox> cbSet, String title) {
        panel.add(new JLabel(title), "span");

        for (JCheckBox cb : cbSet) {
            cb.setBackground(BG_COLOR);

            panel.add(cb, "align left");
        }

        JSeparator line = new JSeparator(JSeparator.HORIZONTAL);

        line.setBackground(BG_COLOR);

        panel.add(line, "growx, span");
    }

    /**
     * @return Si la cantidad de jugadores anclados es al menos 2 y no más de 5.
     */
    private boolean validChecksAmount() {
        int anchored = 0;

        for (ArrayList<JCheckBox> cbset : cbSets)
            for (JCheckBox cb : cbset)
                if (cb.isSelected())
                    anchored++;

        return ((anchored <= MAX_ANCHOR) && (anchored >= 2));
    }

    /**
     * @return Si el anclaje no posee más de la mitad de algún conjunto de
     *         jugadores.
     */
    private boolean isValidAnchorage() {
        for (ArrayList<JCheckBox> cbSet : cbSets) {
            int anchor = 0;

            for (JCheckBox cb : cbSet)
                if (cb.isSelected())
                    anchor++;

            if (anchor > (cbSet.size() / 2))
                return false;
        }

        return true;
    }

    /**
     * Este método se encarga de crear una ventana de error con un texto
     * personalizado.
     * 
     * @param errorText Texto de error a mostrar en la ventana.
     */
    private void errorMsg(String errorText) {
        JOptionPane.showMessageDialog(null, errorText, "¡Error!", JOptionPane.ERROR_MESSAGE, null);
    }

    /**
     * Este método se encarga de setear el número de anclaje correspondiente a cada
     * jugador. Luego, se deseleccionan estas checkboxes y se las hace invisibles
     * para evitar que dos o más anclajes contengan uno o más jugadores iguales. En
     * caso de que el campo 'anchorageNum' sea 0 (se han querido limpiar los
     * anclajes), se resetearán los números de anclaje de cada jugador y todas las
     * checkboxes quedarán visibles y deseleccionadas.
     * 
     * @param cbSet Arreglo de checkboxes a chequear.
     * @param pSet  Arreglo de jugadores correspondiente al arreglo de checkboxes.
     */
    private void setAnchors(ArrayList<JCheckBox> cbSet, Player[] pSet) {
        for (Player player : pSet)
            for (JCheckBox cb : cbSet) {
                if (anchorageNum != 0) {
                    if (cb.getText().equals(player.getName()) && cb.isSelected()) {
                        player.setAnchor(anchorageNum);

                        cb.setSelected(false);
                        cb.setVisible(false);
                    }
                } else {
                    player.setAnchor(anchorageNum);

                    cb.setSelected(false);
                    cb.setVisible(true);
                }
            }
    }

    /**
     * Este método se encarga de actualizar el área de texto mostrando la cantidad
     * de anclajes y los jugadores anclados a los mismos.
     */
    private void updateTextArea() {
        textArea.setText("");

        for (int i = 1; i <= anchorageNum; i++) {
            int counter = 1;

            textArea.append(" ----- ANCLAJE #" + i + " -----\n");

            for (Player[] pSet : InputFrame.playersSets)
                for (Player player : pSet)
                    if (player.getAnchor() == i) {
                        textArea.append(" " + counter + ". " + player.getName() + "\n");
                        counter++;
                    }

            textArea.append("\n");
        }

        toggleButtons();
    }

    /**
     * Este método se encarga de togglear los botones del panel derecho de la
     * ventana.
     */
    private void toggleButtons() {
        if (anchorageNum > 0 && anchorageNum < 2) {
            deleteAnchorage.setEnabled(false);
            deleteLastAnchorage.setEnabled(true);
            clearAnchorages.setEnabled(true);
        } else if (anchorageNum >= 2) {
            deleteAnchorage.setEnabled(true);
            deleteLastAnchorage.setEnabled(true);
            clearAnchorages.setEnabled(true);
        } else {
            deleteAnchorage.setEnabled(false);
            deleteLastAnchorage.setEnabled(false);
            clearAnchorages.setEnabled(false);
        }
    }

    /**
     * Este método se encarga de cambiar el número de anclaje de los jugadores.
     * 
     * @param playersSet  Conjunto de jugadores a recorrer.
     * @param cbSet       Conjunto de checkboxes a recorrer.
     * @param target      Anclaje a reemplazar.
     * @param replacement Nuevo anclaje a setear.
     */
    private void changeAnchor(Player[] playersSet, ArrayList<JCheckBox> cbSet, int target, int replacement) {
        for (JCheckBox cb : cbSet)
            for (Player player : playersSet)
                if (cb.getText().equals(player.getName()) && (player.getAnchor() == target)) {
                    player.setAnchor(replacement);

                    if (replacement == 0)
                        cb.setVisible(true);
                }
    }
}