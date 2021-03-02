/**
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 27/02/2021
 */

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    // Campos privados
    private String version; // Versión del programa.
    private Toolkit toolkit; // Recurso para obtener información gráfica del (y para el) software.
    private JPanel panel; // Panel de la ventana principal.
    private JButton startButton, exitButton, chichaButton; // Botones de la ventana principal.
    private MixFrame mixFrame; // Ventana mostrada al pulsar el botón de "Comenzar".
    private ImageIcon icon, smallIcon; // Iconos utilizados para las ventanas.

    /**
     * Constructor. Aquí se instancia todo lo relativo a la ventana principal, como
     * sus botones, las imágenes y los handlers de eventos.
     * 
     * @param frameTitle Título a mostrar en la ventana principal.
     * @param version    Versión del software.
     */
    public MainFrame(String frameTitle, String version) {
        this.version = version;

        initializeComponents(frameTitle);

        setVisible(true);
    }

    // ----------------------------------------Métodos privados---------------------------------

    /**
     * Este método se encarga de inicializar los botones y la imagen de fondo de la
     * ventana principal.
     * 
     * @param frameTitle Título de la ventana.
     */
    private void initializeComponents(String frameTitle) {
        toolkit = Toolkit.getDefaultToolkit();

        ImageIcon bgImage = new ImageIcon(
                toolkit.getImage(this.getClass().getResource("/graphics/backgroundImage.png")));

        icon = new ImageIcon(toolkit.getImage(this.getClass().getResource("/graphics/myIcon.png")));
        smallIcon = new ImageIcon(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        int bgWidth = bgImage.getIconWidth();
        int bgHeight = bgImage.getIconHeight();

        setSize(bgWidth, bgHeight);
        setLocationRelativeTo(null);
        setTitle(frameTitle);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setIconImage(icon.getImage());
        setResizable(false);

        panel = new JPanel();
        panel.setBounds(0, 0, bgWidth, bgHeight);
        panel.setLayout(null);

        addButtons(panel);

        JLabel bgLabel = new JLabel("", bgImage, JLabel.CENTER);
        bgLabel.setBounds(0, 0, bgWidth, bgHeight);

        panel.add(bgLabel);

        add(panel);
    }

    /**
     * Este método se encarga de agregar todos los botones necesarios en la ventana
     * principal.
     * 
     * @param panel Panel de la ventana principal donde se colocarán los botones.
     */
    private void addButtons(JPanel panel) {
        startButton = new JButton("Comenzar");
        exitButton = new JButton("Salir");
        chichaButton = new JButton();

        ImageIcon chichaImage = new ImageIcon(toolkit.getImage(this.getClass().getResource("/graphics/chicha.jpg")));

        startButton.setBounds(100, 300, 100, 50);
        startButton.setEnabled(true);

        exitButton.setBounds(100, 400, 100, 50);
        exitButton.setEnabled(true);

        chichaButton.setBounds(600, 400, 92, 94);
        chichaButton.setIcon(new ImageIcon(chichaImage.getImage().getScaledInstance(chichaButton.getWidth(),
                chichaButton.getHeight(), Image.SCALE_SMOOTH)));

        addActionListeners();

        panel.add(startButton);
        panel.add(exitButton);
        panel.add(chichaButton);
    }

    /**
     * Este método se encarga de togglear el estado de un JButton. Si el botón está
     * activo, se lo desactiva; y viceversa.
     * 
     * @param button Botón a togglear.
     */
    private void toggleButton(JButton button) {
        if (button.isEnabled())
            button.setEnabled(false);
        else
            button.setEnabled(true);
    }

    /**
     * Este método se encarga de añadir el handler de eventos a cada botón.
     */
    private void addActionListeners() {
        EventsHandler eventHandler = new EventsHandler();

        startButton.addActionListener(eventHandler);
        exitButton.addActionListener(eventHandler);
        chichaButton.addActionListener(eventHandler);
    }

    // ----------------------------------------Clases
    // privadas----------------------------------

    /**
     * Clase privada para lidiar con los eventos de los botones.
     */
    private class EventsHandler implements ActionListener {

        // ----------------------------------------Métodos públicos---------------------------------

        /**
         * Override para indicar qué hacer en base a cada boton pulsado.
         * 
         * @param e Evento ocurrido (botón pulsado).
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) {
                String[] options = { "7", "8" };

                int playersAmount = JOptionPane.showOptionDialog(null, "Seleccione la cantidad de jugadores por equipo",
                        "Antes de empezar...", 2, JOptionPane.QUESTION_MESSAGE, smallIcon, options, options[0]);

                if (playersAmount != JOptionPane.CLOSED_OPTION) {
                    try {
                        mixFrame = new MixFrame((playersAmount + 7), icon);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        System.exit(-1);
                    }

                    mixFrame.addWindowListener(new WindowEventsHandler());
                }
            } else if (e.getSource() == chichaButton)
                chicha(version);
            else System.exit(0);
        }

        // ----------------------------------------Métodos privados---------------------------------

        /**
         * Este método se encarga de desplegar los créditos del programa.
         * 
         * @param   version Versión del software.
         */
        private void chicha(String version) {
            String line = "<html>FIESTA DE FULBITO " + version + "<p><p>    Créditos<p>©AkamaiSoftware - 2021";

            JOptionPane.showMessageDialog(null, line, "Créditos", JOptionPane.PLAIN_MESSAGE, smallIcon);
        }
    }

    /**
     * Clase privada para lidiar con los eventos de las ventanas.
     */
    private class WindowEventsHandler extends WindowAdapter {

        /**
         * Este método se encarga de togglear el estado de los botones del frame
         * principal.
         * 
         * @param   e   Evento de ventana.
         */
        @Override
        public void windowOpened(WindowEvent e) {
            toggleButton(startButton);
            toggleButton(chichaButton);
            toggleButton(exitButton);

            setVisible(false); // Main frame visible.
        }

        /**
         * Este método se encarga de togglear el estado de los botones del frame
         * principal y hacer invisible el frame creado al pulsar el botón de "Comenzar".
         * 
         * @param   e   Evento de ventana.
         */
        @Override
        public void windowClosing(WindowEvent e) {
            toggleButton(startButton);
            toggleButton(chichaButton);
            toggleButton(exitButton);

            mixFrame.setVisible(false);

            setVisible(true); // Main frame visible.
        }
    }
}