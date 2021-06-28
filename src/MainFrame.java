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

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class MainFrame extends JFrame implements ActionListener {

    // Campos públicos.
    public static ImageIcon iconBall; // Ícono para la ventana.

    // Campos privados.
    private ImageIcon smallIconBall, iconAKMSW, smallIconAKMSW, bgImage, chichaImage; // Íconos utilizados para la ventana.
    private JButton startButton, chichaButton; // Botones del menú principal.
    private JLabel bgLabel; // Etiqueta para la imagen de fondo.
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
        bgImage = new ImageIcon(Main.IMG_PATH + "bg.png");
        chichaImage = new ImageIcon(Main.IMG_PATH + "chicha.jpg");

        bgLabel = new JLabel("", bgImage, JLabel.CENTER);

        iconBall = new ImageIcon(Main.IMG_PATH + "icon.png");
        smallIconBall = new ImageIcon(iconBall.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        iconAKMSW = new ImageIcon(Main.IMG_PATH + "akmsw.png");
        smallIconAKMSW = new ImageIcon(iconAKMSW.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

        startButton = new JButton("Comenzar");
        startButton.setEnabled(true);
        startButton.addActionListener(this);

        chichaButton = new JButton();
        chichaButton.setEnabled(true);
        chichaButton.setIcon(new ImageIcon(chichaImage.getImage().getScaledInstance(73, 80, Image.SCALE_SMOOTH)));
        chichaButton.addActionListener(this);

        panel = new JPanel(new MigLayout("wrap"));

        panel.add(bgLabel, "growx");
        panel.add(startButton, "growx");
        panel.add(chichaButton, "align center, span");
        panel.setBackground(Main.FRAMES_BG_COLOR);

        add(panel);
        setResizable(false);
        setTitle(Main.PROGRAM_TITLE + " " + Main.PROGRAM_VERSION);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(iconBall.getImage());   
        pack();
        setLocationRelativeTo(null);
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
                    InputFrame inputFrame = new InputFrame(MainFrame.this, playersAmount + 7); // + 7 para compensar el índice de la selección.

                    inputFrame.setVisible(true);
                    MainFrame.this.setVisible(false);
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