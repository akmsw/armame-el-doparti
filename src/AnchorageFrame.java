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
    private int anchored; // Número de jugadores anclados.
    private int anchorageNum = 1; // Número de anclaje.
    private List<Player[]> playersSet; // Arreglo con los todos los jugadores.
    private ArrayList<JCheckBox> cdCB, ldCB, mfCB, fwCB, wcCB; // Arreglos de checkboxes correspondientes a los
                                                               // jugadores ingresados separados por posición.
    private JFrame inputFrame; // Frame de inputs cuya visibilidad será toggleada.
    private JPanel masterPanel, leftPanel, rightPanel; // Paneles contenedores de los componentes de la ventana de
                                                       // anclajes.
    private JButton okButton, newAnchorage, deleteAnchorage, clearAnchorages; // Botones de la ventana de anclajes.
    private JTextArea textArea; // Área de texto donde se mostrarán los anclajes en tiempo real.
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

        okButton = new JButton("Aceptar");

        cdCB = new ArrayList<>();
        ldCB = new ArrayList<>();
        mfCB = new ArrayList<>();
        fwCB = new ArrayList<>();
        wcCB = new ArrayList<>();

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
                setAnchors(cdCB, playersSet.get(CENTRALDEFENDER), anchorageNum);
                setAnchors(ldCB, playersSet.get(LATERALDEFENDER), anchorageNum);
                setAnchors(mfCB, playersSet.get(MIDFIELDER), anchorageNum);
                setAnchors(fwCB, playersSet.get(FORWARD), anchorageNum);
                setAnchors(wcCB, playersSet.get(WILDCARD), anchorageNum);

                if (!checkTotalAnchors())
                    errorMsg("No pueden haber más de " + maxAnchor + " jugadores en un mismo anclaje.");
                else if (!(validAnchorage(playersSet.get(CENTRALDEFENDER)) && validAnchorage(playersSet.get(LATERALDEFENDER))
                        && validAnchorage(playersSet.get(MIDFIELDER)) && validAnchorage(playersSet.get(FORWARD))
                        && validAnchorage(playersSet.get(WILDCARD))))
                    errorMsg("Ningún conjunto de jugadores puede tener más de la mitad de sus integrantes anclados.");
                else {
                    setVisible(false);

                    resultFrame = new ResultFrame(distribution, icon, playersSet);

                    resultFrame.addWindowListener(new WindowEventsHandler(inputFrame));
                    resultFrame.setVisible(true);
                }
            }
        });

        leftPanel.add(okButton, "growx, span");
        leftPanel.setBackground(bgColor);

        textArea = new JTextArea();

        textArea.setBorder(BorderFactory.createBevelBorder(1));
        textArea.setEditable(false);
        textArea.append(" ----- ANCLAJE #" + anchorageNum + " -----");
        textArea.setVisible(true);

        newAnchorage = new JButton("Nuevo anclaje");
        deleteAnchorage = new JButton("Borrar anclaje");
        clearAnchorages = new JButton("Limpiar anclajes");

        rightPanel.add(textArea, "w 128:213:213, h 289!, span");
        rightPanel.add(newAnchorage, "growx, span");
        rightPanel.add(deleteAnchorage, "growx, span");
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
            cb.addActionListener(new ActionListener() {
                /**
                 * Este evento modela el comportamiento al togglear la selección de cualquier
                 * JCheckBox que haya en la ventana de anclajes.
                 * 
                 * @param e Toggle de selección.
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    textArea.append("\n" + cb.getText() + ">" + cb.isSelected());
                }
            });

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
     * @param cbSet        Arreglo de checkboxes a chequear.
     * @param playersSet   Arreglo de jugadores correspondiente al arreglo de
     *                     checkboxes
     * @param anchorageNum Número de anclaje del jugador.
     */
    private void setAnchors(ArrayList<JCheckBox> cbSet, Player[] playersSet, int anchorageNum) {

        for (int i = 0; i < cbSet.size(); i++)
            for (int j = 0; j < playersSet.length; j++)
                if (playersSet[j].getName().equals(cbSet.get(i).getText())) {
                    if (cbSet.get(i).isSelected())
                        playersSet[j].setAnchor(anchorageNum);
                    else
                        playersSet[j].setAnchor(0);
                }
    }

    /**
     * @param playersSet Arreglo de jugadores con anclaje a verificar.
     * 
     * @return Si el arreglo tiene una cantidad válida de jugadores anclados.
     */
    private boolean validAnchorage(Player[] playersSet) {
        int count = 0;

        for (int i = 0; i < playersSet.length; i++)
            if (playersSet[i].getAnchor() != 0)
                count++;

        return count <= (playersSet.length / 2);
    }

    /**
     * @return Si el límite de jugadores posibles en un mismo anclaje fue
     *         sobrepasado.
     */
    private boolean checkTotalAnchors() {
        anchored = 0;

        playersSet.forEach(pset -> {
            for (int i = 0; i < pset.length; i++)
                if (pset[i].getAnchor() != 0)
                    anchored++;
        });

        return anchored <= maxAnchor;
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