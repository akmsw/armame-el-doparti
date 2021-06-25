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

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

    // Campos públicos.
    public static ImageIcon iconBall; // Ícono para la ventana.

    // Campos privados.
    private ImageIcon smallIconBall, iconAKMSW, smallIconAKMSW; // Íconos utilizados para la ventana.
    private JButton startButton, exitButton, chichaButton;
    private JPanel panel;

    /**
     * Se crea la ventana principal.
     */
    public MainFrame() {
        initializeComponents();
    }

    // ----------------------------------------Métodos privados---------------------------------

    /**
     * Este método se encarga de inicializar los botones y la imagen de fondo de la
     * ventana principal.
     */
    private void initializeComponents() {
        ImageIcon bgImage = new ImageIcon(Main.IMG_PATH + "bg.png");

        iconBall = new ImageIcon(Main.IMG_PATH + "icon.png");
        smallIconBall = new ImageIcon(iconBall.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        iconAKMSW = new ImageIcon(Main.IMG_PATH + "akmsw.png");
        smallIconAKMSW = new ImageIcon(iconAKMSW.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

        int bgWidth = bgImage.getIconWidth(); // Ancho de la imagen de fondo.
        int bgHeight = bgImage.getIconHeight(); // Alto de la imagen de fondo.

        setSize(bgWidth, bgHeight);
        setLocationRelativeTo(null);
        setTitle(Main.PROGRAM_TITLE + " " + Main.PROGRAM_VERSION);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setIconImage(iconBall.getImage());
        setResizable(false);

        panel = new JPanel();
        panel.setBounds(0, 0, bgWidth, bgHeight);
        panel.setLayout(null);

        addButtons();

        JLabel bgLabel = new JLabel("", bgImage, JLabel.CENTER);

        bgLabel.setBounds(0, 0, bgWidth, bgHeight);

        panel.add(bgLabel);

        add(panel);
    }

    /**
     * Este método se encarga de agregar todos los botones necesarios en la ventana
     * principal.
     */
    private void addButtons() {
        startButton = new JButton("Comenzar");
        exitButton = new JButton("Salir");
        chichaButton = new JButton();

        ImageIcon chichaImage = new ImageIcon(Main.IMG_PATH + "chicha.jpg");

        startButton.setBounds(100, 300, 105, 50);
        startButton.setEnabled(true);

        exitButton.setBounds(100, 400, 105, 50);
        exitButton.setEnabled(true);

        chichaButton.setBounds(600, 400, 92, 93);
        chichaButton.setIcon(new ImageIcon(chichaImage.getImage().getScaledInstance(chichaButton.getWidth(),
                                           chichaButton.getHeight(), Image.SCALE_SMOOTH)));

        startButton.addActionListener(this);
        exitButton.addActionListener(this);
        chichaButton.addActionListener(this);

        panel.add(startButton);
        panel.add(exitButton);
        panel.add(chichaButton);
    }

    /**
     * Este método se encarga de desplegar los créditos del programa.
     */
    private void chicha() {
        String line = "<html>" + Main.PROGRAM_TITLE + " " + Main.PROGRAM_VERSION + "<p><p>    Créditos<p>©AkamaiSoftware - 2021";

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
                        "Antes de empezar...", 2, JOptionPane.QUESTION_MESSAGE, smallIconBall, options, options[0]);

            if (playersAmount != JOptionPane.CLOSED_OPTION) {
                try {
                    InputFrame inputFrame = new InputFrame(playersAmount + 7); // + 7 para compensar el índice de la selección.

                    inputFrame.setVisible(true);
                    inputFrame.addWindowListener(new WindowEventsHandler(MainFrame.this));
                } catch (IOException ex) {
                    ex.printStackTrace();

                    System.exit(-1);
                }
            }
        } else if (e.getSource() == chichaButton)
            chicha();
        else
            System.exit(0);
    }
}