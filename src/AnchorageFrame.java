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

import java.util.ArrayList;
import java.util.List;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class AnchorageFrame extends JFrame {
    
    // Constantes privadas.
    private static final int frameWidth = 300; // Ancho de la ventana.
    private static final int frameHeight = 432; // Alto de la ventana.
    private static final int CENTRALDEFENDER = 0; //
    private static final int LATERALDEFENDER = 1; //
    private static final int MIDFIELDER = 2;      // Índices del arreglo 'sets' correspondientes
    private static final int FORWARD = 3;         // a cada array de jugadores.
    private static final int WILDCARD = 4;        //
    private static final String frameTitle = "Anclaje de jugadores";

    // Campos privados.
    private int maxAnchor = 5; // Máxima cantidad de jugadores por anclaje.
    private List<Player[]> sets;
    private ArrayList<JCheckBox> cdCB, ldCB, mfCB, fwCB, wcCB; // Arreglos de checkboxes correspondientes a los jugadores ingresados separados por posición.
    private JPanel anchoragePanel;
    private JButton okButton;
    private ResultFrame resultFrame;

    /**
     * Creación de la ventana de anclajes.
     * 
     * @param icon Ícono para la ventana.
     */
    public AnchorageFrame(ImageIcon icon, List<Player[]> sets, int distribution) {
        this.sets = sets;

        anchoragePanel = new JPanel();

        anchoragePanel.setLayout(new MigLayout("wrap 2"));

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
     * 
     */
    private void initializeComponents(ImageIcon icon, int distribution) {
        fillCBSet(sets.get(CENTRALDEFENDER), cdCB);
        fillCBSet(sets.get(LATERALDEFENDER), ldCB);
        fillCBSet(sets.get(MIDFIELDER), mfCB);
        fillCBSet(sets.get(FORWARD), fwCB);
        fillCBSet(sets.get(WILDCARD), wcCB);

        addCBSet(anchoragePanel, cdCB, "DEFENSORES CENTRALES");
        addCBSet(anchoragePanel, ldCB, "DEFENSORES LATERALES");
        addCBSet(anchoragePanel, mfCB, "MEDIOCAMPISTAS");
        addCBSet(anchoragePanel, fwCB, "DELANTEROS");
        addCBSet(anchoragePanel, wcCB, "COMODINES");

        okButton.addActionListener(new ActionListener() {
            /**
             * Este evento hace las validaciones de datos necesarias
             * y, si todo se cumple, hace invisible la ventana de anclaje
             * cuando el usuario hizo los anclajes deseados y
             * está listo para distribuir los jugadores.
             * 
             * @param e Evento de click.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                setAnchors(cdCB, sets.get(CENTRALDEFENDER), 1);
                setAnchors(ldCB, sets.get(LATERALDEFENDER), 1);
                setAnchors(mfCB, sets.get(MIDFIELDER), 1);
                setAnchors(fwCB, sets.get(FORWARD), 1);
                setAnchors(wcCB, sets.get(WILDCARD), 1);

                if (!checkTotalAnchors(maxAnchor))
                    errorMsg("No pueden haber más de " + maxAnchor + " jugadores en un mismo anclaje.");
                else if (!(fullAnchored(sets.get(CENTRALDEFENDER)) &&
                           fullAnchored(sets.get(LATERALDEFENDER)) &&
                           fullAnchored(sets.get(MIDFIELDER)) &&
                           fullAnchored(sets.get(FORWARD)) &&
                           fullAnchored(sets.get(WILDCARD))))
                    errorMsg("Ningún conjunto de jugadores puede tener más de la mitad de sus integrantes anclados.");
                else
                    setVisible(false);

                resultFrame = new ResultFrame(distribution, icon, sets);

                resultFrame.addWindowListener(new WindowEventsHandler());
                resultFrame.setVisible(true);
            }
        });

        anchoragePanel.add(okButton, "growx, span");

        setTitle(frameTitle);
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setIconImage(icon.getImage());
        add(anchoragePanel);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    /**
     * Este método se encarga de llenar los arreglos de checkboxes
     * correspondientes a cada posición.
     * 
     * @param playersSet Conjunto de jugadores de donde obtener los nombres.
     * @param cbSet Conjunto de checkboxes a llenar.
     */
    private void fillCBSet(Player[] playersSet, ArrayList<JCheckBox> cbSet) {
        for (Player player : playersSet)
            cbSet.add(new JCheckBox(player.getName()));
    }

    /**
     * Este método se encarga de colocar en el panel los checkboxes
     * correspondiente a cada posición junto con una etiqueta que los
     * distinga.
     * 
     * @param panel Panel donde se colocarán los checkboxes.
     * @param cbSet Conjunto de checkboxes a colocar.
     * @param title Texto de la etiqueta de acompañamiento.
     */
    private void addCBSet(JPanel panel, ArrayList<JCheckBox> cbSet, String title) {
        panel.add(new JLabel(title), "wrap");

        for (JCheckBox cb : cbSet)
            panel.add(cb);
        
        panel.add(new JSeparator(JSeparator.HORIZONTAL), "growx, span");
    }

    /**
     * Este método se encarga de setear el número
     * de anclaje correspondiente a cada jugador
     * cuya checkbox haya sido tildada.
     * 
     * @param cbSet Arreglo de checkboxes a chequear.
     * @param playersSet Arreglo de jugadores correspondiente
     *                   al arreglo de checkboxes
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
     * Este método se encarga de verificar que el arreglo
     * de jugadores recibido por parámetro no tenga más
     * de la mitad de jugadores anclados.
     * 
     * @param playersSet Arreglo de jugadores con anclaje a verificar.
     * @return Si el arreglo tiene más de la mitad de los jugadores anclados.
     */
    private boolean fullAnchored(Player[] playersSet) {
        int count = 0;

        for (int i = 0; i < playersSet.length; i++)
            if (playersSet[i].getAnchor() != 0)
                count++;
        
        return count <= (playersSet.length / 2);
    }

    /**
     * Este método se encarga de verificar que no se sobrepase
     * la cantidad máxima de anclajes posibles.
     * 
     * @param maxAnchor Máxima cantidad posible de jugadores a anclar.
     * 
     * @return Si el límite de anclajes fue sobrepasado.
     */
    private boolean checkTotalAnchors(int maxAnchor) {
        int anchored = 0;

        for (int i = 0; i < sets.get(CENTRALDEFENDER).length; i++)
            if (sets.get(CENTRALDEFENDER)[i].getAnchor() != 0)
                anchored++;
        
        for (int i = 0; i < sets.get(LATERALDEFENDER).length; i++)
            if (sets.get(LATERALDEFENDER)[i].getAnchor() != 0)
                anchored++;

        for (int i = 0; i < sets.get(MIDFIELDER).length; i++)
            if (sets.get(MIDFIELDER)[i].getAnchor() != 0)
                anchored++;
        
        for (int i = 0; i < sets.get(FORWARD).length; i++)
            if (sets.get(FORWARD)[i].getAnchor() != 0)
                anchored++;
        
        for (int i = 0; i < sets.get(WILDCARD).length; i++)
            if (sets.get(WILDCARD)[i].getAnchor() != 0)
                anchored++;
        
        return anchored <= maxAnchor;
    }

    /**
     * Este método se encarga de crear una ventana de error
     * con un texto personalizado.
     * 
     * @param errorText Texto de error a mostrar en la ventana.
     */
    private void errorMsg(String errorText) {
        JOptionPane.showMessageDialog(null, errorText, "¡Error!", JOptionPane.ERROR_MESSAGE, null);
    }

    // ----------------------------------------Clases privadas----------------------------------

    /**
     * Clase privada para lidiar con los eventos de las ventanas.
     */
    private class WindowEventsHandler extends WindowAdapter {

        /**
         * TODO.
         * 
         * @param e Evento de ventana.
         */
        @Override
        public void windowOpened(WindowEvent e) {
            // TODO.
        }

        /**
         * TODO.
         * 
         * @param e Evento de ventana.
         */
        @Override
        public void windowClosing(WindowEvent e) {
            // TODO.
        }
    }
}