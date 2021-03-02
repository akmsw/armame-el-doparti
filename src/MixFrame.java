/**
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 28/02/2021
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MixFrame extends JFrame implements ActionListener {

    // Constantes privadas.
    private static final int frameWidth = 400;
    private static final int frameHeight = 400;
    private static final String[] options = { "Agregar defensores centrales", "Agregar defensores laterales",
                                              "Agregar mediocampistas", "Agregar delanteros", "Agregar comodines" };

    // Campos privados.
    private int playersAmount;
    private ImageIcon icon;
    private JPanel panel;
    private JComboBox<String> comboBox;

    /**
     * Constructor. Aquí se crea la ventana de mezcla.
     */
    public MixFrame(int playersAmount, ImageIcon icon) {
        this.playersAmount = playersAmount;
        this.icon = icon;

        initializeComponents("Ingreso de jugadores - Fútbol " + playersAmount);

        setVisible(true);
    }

    // ----------------------------------------Métodos privados---------------------------------

    /**
     * Este método se encarga de inicializar los componentes de la ventana de
     * comienzo.
     * 
     * @param frameTitle Título de la ventana.
     */
    private void initializeComponents(String frameTitle) {
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(frameTitle);
        setResizable(false);
        setIconImage(icon.getImage());

        panel = new JPanel();
        panel.setBounds(0, 0, frameWidth, frameHeight);
        panel.setLayout(null);

        addComboBox();

        add(panel);
    }

    /**
     * Este método se encarga de agregar la lista desplegable
     * al frame, y setear el handler de eventos a la misma.
     */
    private void addComboBox() {
        comboBox = new JComboBox<>(options);

        comboBox.setBounds(5, 5, 200, 30);
        comboBox.addActionListener(this);

        updateOutput(comboBox.getSelectedItem().toString()); // Para que se muestre el output correspondiente
                                                             // al estado inicial del JComboBox.

        panel.add(comboBox);
    }

    /**
     * Handler para los eventos ocurridos de la lista desplegable.
     * Se trata la fuente del evento ocurrido como un JComboBox y
     * se trata como un String el item seleccionado en el mismo
     * para pasarlo al método updateOutput.
     * 
     * @param   e   Evento ocurrido (item seleccionado).
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String text = (String)((JComboBox)e.getSource()).getSelectedItem();

        updateOutput(text);
    }

    /**
     * Este método se encarga de actualizar la salida en base
     * al evento ocurrido con la lista desplegable.
     * 
     * @param   text    Opción seleccionada del arreglo de Strings 'options'.
     */
    private void updateOutput(String text) {
        System.out.println(text);
    }

    // ----------------------------------------Clases privadas----------------------------------

    /**
     * Clase privada para lidiar con los eventos de las ventanas.
     */
    private class WindowEventsHandler extends WindowAdapter {

        /**
         * 
         * 
         * @param   e   Evento de ventana.
         */
        @Override
        public void windowOpened(WindowEvent e) {
            // TODO.
        }

        /**
         * 
         * 
         * @param   e   Evento de ventana.
         */
        @Override
        public void windowClosing(WindowEvent e) {
            // TODO.
        }
    }
}