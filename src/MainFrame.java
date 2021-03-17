/**
 * Clase correspondiente a la ventana del
 * menú principal del programa.
 * 
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 27/02/2021
 */

import java.io.IOException;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

    // Constantes privadas.
    private static final String imagesPath = "src/graphics/"; // Carpeta donde buscar las imágenes.

    // Campos privados
    private String version; // Versión del programa.
    private ImageIcon iconBall, smallIconBall, iconAKMSW, smallIconAKMSW; // Iconos utilizados para las ventanas.
    private JButton startButton, exitButton, chichaButton; // Botones de la ventana principal.
    private JPanel panel; // Panel de la ventana principal.
    private InputFrame inputFrame; // Ventana mostrada al pulsar el botón de "Comenzar".

    /**
     * Se crea la ventana principal.
     * 
     * @param frameTitle Título a mostrar en la ventana principal.
     * @param version    Versión del software.
     */
    public MainFrame(String version) {
        this.version = version;

        initializeComponents();
    }

    // ----------------------------------------Métodos privados---------------------------------

    /**
     * Este método se encarga de inicializar los botones y la imagen de fondo de la
     * ventana principal.
     * 
     * @param frameTitle Título de la ventana.
     */
    private void initializeComponents() {
        ImageIcon bgImage = new ImageIcon(imagesPath + "bg.png");

        iconBall = new ImageIcon(imagesPath + "icon.png");
        smallIconBall = new ImageIcon(iconBall.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        iconAKMSW = new ImageIcon(imagesPath + "akmsw.png");
        smallIconAKMSW = new ImageIcon(iconAKMSW.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

        int bgWidth = bgImage.getIconWidth(); // Ancho de la imagen de fondo.
        int bgHeight = bgImage.getIconHeight(); // Alto de la imagen de fondo.

        setSize(bgWidth, bgHeight);
        setLocationRelativeTo(null);
        setTitle("Fiesta de fulbito " + version);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setIconImage(iconBall.getImage());
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
     * @param panel Panel donde se colocarán los botones.
     */
    private void addButtons(JPanel panel) {
        startButton = new JButton("Comenzar");
        exitButton = new JButton("Salir");
        chichaButton = new JButton();

        ImageIcon chichaImage = new ImageIcon(imagesPath + "chicha.jpg");

        startButton.setBounds(100, 300, 105, 50);
        startButton.setEnabled(true);

        exitButton.setBounds(100, 400, 105, 50);
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
     * Este método se encarga de añadir el handler de eventos a cada botón.
     */
    private void addActionListeners() {
        startButton.addActionListener(this);
        exitButton.addActionListener(this);
        chichaButton.addActionListener(this);
    }

    /**
     * Este método se encarga de desplegar los créditos del programa.
     */
    private void chicha() {
        String line = "<html>FIESTA DE FULBITO " + version + "<p><p>    Créditos<p>©AkamaiSoftware - 2021";

        JOptionPane.showMessageDialog(null, line, "Créditos", JOptionPane.PLAIN_MESSAGE, smallIconAKMSW);
    }

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
                                                             "Antes de empezar...", 2, JOptionPane.QUESTION_MESSAGE,
                                                             smallIconBall, options, options[0]);

            if (playersAmount != JOptionPane.CLOSED_OPTION) {
                try {
                    inputFrame = new InputFrame((playersAmount + 7), iconBall);

                    inputFrame.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();

                    System.exit(-1);
                }

                inputFrame.addWindowListener(new WindowEventsHandler(MainFrame.this));
            }
        } else if (e.getSource() == chichaButton)
            chicha();
        else
            System.exit(0);
    }
}