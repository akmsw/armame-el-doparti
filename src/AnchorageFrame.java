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
    private static final int frameWidth = 402; // Ancho de la ventana.
    private static final int frameHeight = 432; // Alto de la ventana.
    private static final int maxAnchor = 5; // Máxima cantidad de jugadores por anclaje.
    private static final int CENTRALDEFENDER = 0; //
    private static final int LATERALDEFENDER = 1; //
    private static final int MIDFIELDER = 2;      // Índices del arreglo 'sets' correspondientes
    private static final int FORWARD = 3;         // a cada array de jugadores.
    private static final int WILDCARD = 4;        //
    private static final String frameTitle = "Anclaje de jugadores";
    private static final Color bgColor = new Color(200, 200, 200); // Color de fondo de la ventana.

    // Campos privados.
    private int anchorageNum = 0; // Número de anclaje.
    private List<Player[]> playersSet; // Arreglo con los todos los jugadores.
    private ArrayList<JCheckBox> cdCB, ldCB, mfCB, fwCB, wcCB; // Arreglos de checkboxes correspondientes a los
                                                               // jugadores ingresados separados por posición.
    private ArrayList<ArrayList<JCheckBox>> cbSets; // Arreglo de arreglos de checkboxes de los jugadores.
    private JFrame inputFrame; // Frame de inputs cuya visibilidad será toggleada.
    private JPanel masterPanel, leftPanel, rightPanel; // Paneles contenedores de los componentes de la ventana de
                                                       // anclajes.
    private JButton okButton, newAnchorage, clearAnchorages; // Botones de la ventana de anclajes.
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
        this.playersSet = sets;
        this.inputFrame = inputFrame;

        masterPanel = new JPanel(new MigLayout("wrap 2"));
        leftPanel = new JPanel();
        rightPanel = new JPanel();

        leftPanel.setLayout(new MigLayout("wrap 2"));
        rightPanel.setLayout(new MigLayout());

        okButton = new JButton("Finalizar");

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
        fillCBSet(playersSet.get(CENTRALDEFENDER), cdCB);
        fillCBSet(playersSet.get(LATERALDEFENDER), ldCB);
        fillCBSet(playersSet.get(MIDFIELDER), mfCB);
        fillCBSet(playersSet.get(FORWARD), fwCB);
        fillCBSet(playersSet.get(WILDCARD), wcCB);

        addCBSet(leftPanel, cdCB, "DEFENSORES CENTRALES");
        addCBSet(leftPanel, ldCB, "DEFENSORES LATERALES");
        addCBSet(leftPanel, mfCB, "MEDIOCAMPISTAS");
        addCBSet(leftPanel, fwCB, "DELANTEROS");
        addCBSet(leftPanel, wcCB, "COMODINES");

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

                resultFrame = new ResultFrame(distribution, icon, playersSet);

                resultFrame.addWindowListener(new WindowEventsHandler(inputFrame));
                resultFrame.setVisible(true);
            }
        });

        leftPanel.add(okButton, "growx, span");
        leftPanel.setBackground(bgColor);

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
                    errorMsg("No pueden haber más de " + maxAnchor + " ni menos de 2 jugadores en un mismo anclaje.");
                    return;
                }

                anchorageNum++;

                setAnchors(cdCB, playersSet.get(CENTRALDEFENDER));
                setAnchors(ldCB, playersSet.get(LATERALDEFENDER));
                setAnchors(mfCB, playersSet.get(MIDFIELDER));
                setAnchors(fwCB, playersSet.get(FORWARD));
                setAnchors(wcCB, playersSet.get(WILDCARD));

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

                setAnchors(cdCB, playersSet.get(CENTRALDEFENDER));
                setAnchors(ldCB, playersSet.get(LATERALDEFENDER));
                setAnchors(mfCB, playersSet.get(MIDFIELDER));
                setAnchors(fwCB, playersSet.get(FORWARD));
                setAnchors(wcCB, playersSet.get(WILDCARD));

                textArea.setText("");
            }
        });

        rightPanel.add(scrollPane, "w 128:213:213, h 289!, growy, wrap");
        rightPanel.add(newAnchorage, "growx, wrap");
        rightPanel.add(clearAnchorages, "growx");
        rightPanel.setBackground(bgColor);

        masterPanel.add(leftPanel, "west");
        masterPanel.add(rightPanel, "center, growx, span");
        masterPanel.setBackground(bgColor);

        setSize(frameWidth, frameHeight);
        setTitle(frameTitle);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(icon.getImage());
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
            cb.setBackground(bgColor);
            
            panel.add(cb, "align left");
        }

        JSeparator line = new JSeparator(JSeparator.HORIZONTAL);

        line.setBackground(bgColor);

        panel.add(line, "growx, span");
    }

    /**
     * Este método se encarga de setear el número de anclaje correspondiente a cada
     * jugador cuya checkbox haya sido tildada.
     * 
     * @param cbSet Arreglo de checkboxes a chequear.
     * @param pSet  Arreglo de jugadores correspondiente al arreglo de checkboxes
     */
    private void setAnchors(ArrayList<JCheckBox> cbSet, Player[] pSet) {
        for (Player player : pSet)
            for (JCheckBox cb : cbSet)
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

    /**
     * @return Si la cantidad de jugadores en el anclaje es al menos 2 y no más de 5.
     */
    private boolean checkAnchorages() {
        int anchored = 0;

        for (ArrayList<JCheckBox> cbset : cbSets)
            for (JCheckBox cb : cbset)
                if (cb.isSelected())
                    anchored++;

        return (anchored <= maxAnchor) && (anchored >= 2);
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

            for (Player[] pSet : playersSet)
                for (Player player : pSet)
                    if (player.getAnchor() == (i + 1)) {
                        textArea.append(" " + (counter + 1) + ". " + player.getName() + "\n");
                        counter++;
                    }
            
            textArea.append("\n");
        }
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