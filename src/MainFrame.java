/**
 * Clase correspondiente a la ventana del
 * menú principal del programa.
 * 
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 3.0.0
 * 
 * @since 27/02/2021
 */

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class MainFrame extends JFrame implements ActionListener {

    /* ---------------------------------------- Campos públicos ---------------------------------- */

    public static ImageIcon icon, smallIcon;

    /* ---------------------------------------- Campos privados ---------------------------------- */

    private ImageIcon bgImage;

    private JButton startButton, helpButton;

    private JLabel bgLabel;

    private JPanel panel;

    /**
     * Constructor de la ventana principal.
     */
    public MainFrame() {
        initializeComponents();
    }

    /* ---------------------------------------- Métodos privados --------------------------------- */

    /**
     * Este método se encarga de inicializar los botones y la imagen de fondo de la
     * ventana principal.
     */
    private void initializeComponents() {
        bgImage = new ImageIcon(Main.IMG_PATH + "bg.png");
        icon = new ImageIcon(Main.IMG_PATH + "icon.png");
        smallIcon = new ImageIcon(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        startButton = new JButton("Comenzar");
        helpButton = new JButton("Ayuda");

        bgLabel = new JLabel("", bgImage, JLabel.CENTER);

        panel = new JPanel(new MigLayout("wrap"));

        startButton.setEnabled(true);
        startButton.addActionListener(this);

        helpButton.setEnabled(true);
        helpButton.addActionListener(this);

        panel.add(bgLabel, "growx");
        panel.add(startButton, "growx");
        panel.add(helpButton, "growx");

        panel.setBackground(Main.FRAMES_BG_COLOR);

        add(panel);

        setResizable(false);
        setTitle(Main.PROGRAM_TITLE + " " + Main.PROGRAM_VERSION);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(icon.getImage());

        pack();

        setLocationRelativeTo(null);
    }

    /**
     * Este método despliega las instrucciones de uso del programa.
     */
    private void help() {
        // TODO.
    }

    /* ---------------------------------------- Métodos públicos --------------------------------- */

    /**
     * Override para indicar qué hacer en base a cada boton pulsado.
     * 
     * @param e Evento de click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            String[] options = { "7", "8" };

            int playersAmount = JOptionPane.showOptionDialog(null, "Seleccione la cantidad de jugadores por equipo",
                                                             "Antes de empezar...", 2, JOptionPane.QUESTION_MESSAGE,
                                                             smallIcon, options, options[0]);

            if (playersAmount != JOptionPane.CLOSED_OPTION) {
                try {
                    // + 7 para compensar el índice de la selección
                    InputFrame inputFrame = new InputFrame(MainFrame.this, playersAmount + 7);

                    inputFrame.setVisible(true);

                    MainFrame.this.setVisible(false);
                } catch (IOException ex) {
                    ex.printStackTrace();

                    System.exit(-1);
                }
            }
        } else if (e.getSource() == helpButton)
            help();
        else
            System.exit(0);
    }
}