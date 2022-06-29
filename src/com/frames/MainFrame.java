package com.frames;

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
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import net.miginfocom.swing.MigLayout;

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
public class MainFrame extends JFrame implements ActionListener {

    // ---------------------------------------- Constantes públicas ------------------------------

    /**
     * Imagen estándar del icono de la aplicación.
     */
    public static final ImageIcon ICON = new ImageIcon(Main.IMG_PATH + "icon.png");

    /**
     * Imagen escalada del icono de la aplicación.
     */
    public static final ImageIcon SMALL_ICON = new ImageIcon(ICON.getImage()
                                                                 .getScaledInstance(50, 50, Image.SCALE_SMOOTH));

    // ---------------------------------------- Constantes privadas ------------------------------

    private static final Integer[] PLAYERS_PER_TEAM = {7, 8};

    private static final String GROWX = "growx";

    // ---------------------------------------- Campos privados ----------------------------------

    private JButton startButton;
    private JButton helpButton;

    // ---------------------------------------- Constructor --------------------------------------

    /**
     * Constructor de la ventana principal.
     */
    public MainFrame() {
        ImageIcon bgImage = new ImageIcon(Main.IMG_PATH + "bg.png");

        JLabel bgLabel = new JLabel("", bgImage, SwingConstants.CENTER);

        JPanel panel = new JPanel(new MigLayout("wrap"));

        startButton = new JButton("Comenzar");
        helpButton = new JButton("Ayuda");

        startButton.setEnabled(true);
        startButton.addActionListener(this);

        helpButton.setEnabled(true);
        helpButton.addActionListener(this);

        panel.add(bgLabel, GROWX);
        panel.add(startButton, GROWX);
        panel.add(helpButton, GROWX);

        panel.setBackground(Main.FRAMES_BG_COLOR);

        add(panel);

        setResizable(false);
        setTitle(Main.PROGRAM_TITLE + " " + Main.PROGRAM_VERSION);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(ICON.getImage());

        pack();

        setLocationRelativeTo(null);
    }

    // ---------------------------------------- Métodos privados ---------------------------------

    /**
     * Este método despliega las instrucciones de uso del programa.
     */
    private void help() {
        // TODO.
    }

    // ---------------------------------------- Métodos públicos ---------------------------------

    /**
     * Override para indicar qué hacer en base a cada botón pulsado.
     *
     * @param e Evento de click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            int selected = JOptionPane.showOptionDialog(null, "Seleccione la cantidad de jugadores por equipo",
                                                        "Antes de empezar...", 2, JOptionPane.QUESTION_MESSAGE,
                                                        SMALL_ICON, PLAYERS_PER_TEAM, PLAYERS_PER_TEAM[0]);

            if (selected != JOptionPane.CLOSED_OPTION) {
                try {
                    InputFrame inputFrame = new InputFrame(MainFrame.this, PLAYERS_PER_TEAM[selected]);

                    inputFrame.setVisible(true);

                    MainFrame.this.setVisible(false);
                    MainFrame.this.setLocationRelativeTo(null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.exit(-1);
                }
            }
        } else if (e.getSource() == helpButton) {
            help();
        } else {
            System.exit(0);
        }
    }
}
