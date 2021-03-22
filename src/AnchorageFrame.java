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

import java.util.ArrayList;
import java.util.List;

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
    private static final int CENTRALDEFENDER = 0; //
    private static final int LATERALDEFENDER = 1; //
    private static final int MIDFIELDER = 2;      // Índices del arreglo 'sets' correspondientes
    private static final int FORWARD = 3;         // a cada array de jugadores.
    private static final int WILDCARD = 4;        //
    private static final String FRAME_TITLE = "Anclaje de jugadores";
    private static final Color BG_COLOR = new Color(200, 200, 200); // Color de fondo de la ventana.

    // Campos privados.
    private int anchorageNum = 0; // Número de anclaje.
    private List<Player[]> playersSets; // Arreglo con los todos los jugadores.
    private ArrayList<JCheckBox> cdCB, ldCB, mfCB, fwCB, wcCB; // Arreglos de checkboxes correspondientes a los
                                                               // jugadores ingresados separados por posición.
    private ArrayList<ArrayList<JCheckBox>> cbSets; // Arreglo de arreglos de checkboxes de los jugadores.
    private JFrame inputFrame; // Frame de inputs cuya visibilidad será toggleada.
    private JPanel masterPanel, leftPanel, rightPanel; // Paneles contenedores de los componentes de la ventana de
                                                       // anclajes.
    private JButton okButton, newAnchorage, clearAnchorages, deleteAnchorage, deleteLastAnchorage; // Botones de la ventana de anclajes.
    private JTextArea textArea; // Área de texto donde se mostrarán los anclajes en tiempo real.
    private JScrollPane scrollPane; // Scrollpane para el área de texto.
    private ResultFrame resultFrame; // Ventana de resultados.

    /**
     * Creación de la ventana de anclajes.
     * 
     * @param icon         Ícono para la ventana.
     * @param sets         Conjunto de jugadores.
     * @param distribution Distribución de jugadores elegida.
     * @param inputFrame   Ventana cuya visibilidad será toggleada.
     */
    public AnchorageFrame(ImageIcon icon, List<Player[]> sets, int distribution, JFrame inputFrame) {
        this.playersSets = sets;
        this.inputFrame = inputFrame;

        masterPanel = new JPanel(new MigLayout("wrap 2"));
        leftPanel = new JPanel();
        rightPanel = new JPanel();

        leftPanel.setLayout(new MigLayout("wrap 2"));
        rightPanel.setLayout(new MigLayout());

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

        initializeComponents(icon, distribution);
    }

    // ----------------------------------------Métodos privados---------------------------------

    /**
     * Este método se encarga de inicializar los componentes de la ventana de
     * anclaje.
     * 
     * @param icon         Ícono para la ventana.
     * @param distribution Tipo de distribución elegida.
     */
    private void initializeComponents(ImageIcon icon, int distribution) {
        fillCBSet(playersSets.get(CENTRALDEFENDER), cdCB);
        fillCBSet(playersSets.get(LATERALDEFENDER), ldCB);
        fillCBSet(playersSets.get(MIDFIELDER), mfCB);
        fillCBSet(playersSets.get(FORWARD), fwCB);
        fillCBSet(playersSets.get(WILDCARD), wcCB);

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

                resultFrame = new ResultFrame(distribution, icon, playersSets);

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

        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        newAnchorage = new JButton("Anclar");

        newAnchorage.addActionListener(new ActionListener() {
            /**
             * Este método se encarga de anclar los jugadores cuya
             * checkbox está tildada.
             * 
             * @param e Evento de click.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkAnchorages()) {
                    errorMsg("No pueden haber más de " + MAX_ANCHOR + " ni menos de 2 jugadores en un mismo anclaje.");
                    return;
                }

                anchorageNum++;

                setAnchors(cdCB, playersSets.get(CENTRALDEFENDER));
                setAnchors(ldCB, playersSets.get(LATERALDEFENDER));
                setAnchors(mfCB, playersSets.get(MIDFIELDER));
                setAnchors(fwCB, playersSets.get(FORWARD));
                setAnchors(wcCB, playersSets.get(WILDCARD));

                updateTextArea();
            }
        });

        deleteAnchorage = new JButton("Borrar un anclaje");

        deleteAnchorage.addActionListener(new ActionListener() {
            /**
             * 
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO.
            }
        });

        deleteLastAnchorage = new JButton("Borrar último anclaje");

        deleteLastAnchorage.addActionListener(new ActionListener() {
            /**
             * Este método se encarga de borrar
             * el último anclaje realizado.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                anchorageNum--;

                deleteLast(playersSets.get(CENTRALDEFENDER), cdCB);
                deleteLast(playersSets.get(LATERALDEFENDER), ldCB);
                deleteLast(playersSets.get(MIDFIELDER), mfCB);
                deleteLast(playersSets.get(FORWARD), fwCB);
                deleteLast(playersSets.get(WILDCARD), wcCB);
                
                updateTextArea();
            }
        });
        
        clearAnchorages = new JButton("Limpiar anclajes");

        clearAnchorages.addActionListener(new ActionListener() {
            /**
             * Este método se encarga de borrar todos los anclajes
             * que se hayan generado.
             * 
             * @param e Evento de click.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                anchorageNum = 0;

                setAnchors(cdCB, playersSets.get(CENTRALDEFENDER));
                setAnchors(ldCB, playersSets.get(LATERALDEFENDER));
                setAnchors(mfCB, playersSets.get(MIDFIELDER));
                setAnchors(fwCB, playersSets.get(FORWARD));
                setAnchors(wcCB, playersSets.get(WILDCARD));

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
        setIconImage(icon.getImage());
        add(masterPanel);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Este método se encarga de borrar los jugadores
     * correspondientes al último anclaje que estén
     * en el grupo de jugadores pasado por parámetro.
     * 
     * @param playersSet Arreglo de jugadores.
     * @param cbSet      Arreglo de checkboxes.
     */
    private void deleteLast(Player[] playersSet, ArrayList<JCheckBox> cbSet) {
        for (JCheckBox cb : cbSet)
            for (Player player : playersSet)
                if (cb.getText().equals(player.getName()) && (player.getAnchor() == (anchorageNum + 1))) {
                    player.setAnchor(0);
                    cb.setVisible(true);
                }
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
     * Este método se encarga de colocar en el panel los checkboxes correspondiente
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
     * Este método se encarga de setear el número de anclaje correspondiente a cada
     * jugador. Luego, se deseleccionan estas checkboxes y se las hace invisibles
     * para evitar que dos o más anclajes contengan uno o más jugadores iguales.
     * En caso de que el campo 'anchorageNum' sea 0 (se han querido limpiar los anclajes),
     * se resetearán los números de anclaje de cada jugador y todas las checkboxes
     * quedarán visibles y deseleccionadas. 
     * 
     * @param cbSet Arreglo de checkboxes a chequear.
     * @param pSet  Arreglo de jugadores correspondiente al arreglo de checkboxes.
     */
    private void setAnchors(ArrayList<JCheckBox> cbSet, Player[] pSet) {
        for (Player player : pSet)
            for (JCheckBox cb : cbSet) {
                if (anchorageNum != 0) {
                    if(cb.getText().equals(player.getName()) && cb.isSelected()) {
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
     * @return Si la cantidad de jugadores en el anclaje es al menos 2 y no más de 5.
     */
    private boolean checkAnchorages() {
        int anchored = 0;

        for (ArrayList<JCheckBox> cbset : cbSets)
            for (JCheckBox cb : cbset)
                if (cb.isSelected())
                    anchored++;

        return ((anchored <= MAX_ANCHOR) && (anchored >= 2));
    }

    /**
     * Este método se encarga de togglear
     * los botones del panel derecho de la ventana.
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
     * Este método se encarga de actualizar el área de texto
     * mostrando la cantidad de anclajes y los jugadores
     * anclados a los mismos.
     */
    private void updateTextArea() {
        textArea.setText("");

        for (int i = 0; i < anchorageNum; i++) {
            int counter = 0;

            textArea.append(" ----- ANCLAJE #" + (i + 1) + " -----\n");

            for (Player[] pSet : playersSets)
                for (Player player : pSet)
                    if (player.getAnchor() == (i + 1)) {
                        textArea.append(" " + (counter + 1) + ". " + player.getName() + "\n");
                        counter++;
                    }
            
            textArea.append("\n");
        }

        toggleButtons();
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
}