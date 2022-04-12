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

public class MainFrame extends JFrame implements ActionListener {

    /* ---------------------------------------- Constantes privadas ------------------------------ */

    private static final String GROWX = "growx";

    /* ---------------------------------------- Campos públicos ---------------------------------- */

    public static final ImageIcon icon = new ImageIcon(Main.IMG_PATH + "icon.png");
    public static final ImageIcon smallIcon = new ImageIcon(icon.getImage()
                                                                .getScaledInstance(50, 50, Image.SCALE_SMOOTH));

    /* ---------------------------------------- Campos privados ---------------------------------- */

    private JButton startButton;
    private JButton helpButton;

    /* ---------------------------------------- Constructor -------------------------------------- */

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
        setIconImage(icon.getImage());

        pack();

        setLocationRelativeTo(null);
    }

    /* ---------------------------------------- Métodos privados --------------------------------- */

    /**
     * Este método despliega las instrucciones de uso del programa.
     */
    private void help() {
        // TODO.
    }

    /* ---------------------------------------- Métodos públicos --------------------------------- */

    /**
     * Override para indicar qué hacer en base a cada botón pulsado.
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