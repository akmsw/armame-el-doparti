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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    // Campos privados
    private String version;
    private Toolkit toolkit;
    private JPanel panel;
    private JButton startButton, exitButton, chichaButton;

    /**
     * Constructor.
     * 
     * @param   frameTitle  Título a mostrar en la ventana principal.
     * @param   version     Versión del software.
     */
    public MainFrame(String frameTitle, String version) {
        this.version = version;

        initializeComponents(frameTitle);

        setVisible(true);
        setResizable(false);
    }

    // ----------------------------------------Métodos privados---------------------------------

    /**
     * Este método se encarga de inicializar los elementos básicos de la ventana
     * principal.
     * 
     * @param frameTitle Título de la ventana.
     */
    private void initializeComponents(String frameTitle) {
        toolkit = Toolkit.getDefaultToolkit();

        ImageIcon bgImage = new ImageIcon(toolkit.getImage(this.getClass().getResource("/graphics/backgroundImage.png")));

        int bgWidth = bgImage.getIconWidth();
        int bgHeight = bgImage.getIconHeight();

        setSize(bgWidth, bgHeight);
        setLocationRelativeTo(null);
        setTitle(frameTitle);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

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
     * @param panel Panel del frame donde se colocarán los botones.
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
        chichaButton.setIcon(new ImageIcon(chichaImage.getImage().getScaledInstance(chichaButton.getWidth(), chichaButton.getHeight(), Image.SCALE_SMOOTH)));

        addActionListeners();

        panel.add(startButton);
        panel.add(exitButton);
        panel.add(chichaButton);
    }

    /**
     * Este método se encarga de añadir el handler de eventos a cada botón.
     */
    private void addActionListeners() {
        ActionHandler eventHandler = new ActionHandler();

        startButton.addActionListener(eventHandler);
        exitButton.addActionListener(eventHandler);
        chichaButton.addActionListener(eventHandler);
    }

    /**
     * Este método se encarga de desplegar los créditos del programa.
     * 
     * @param   version Versión del software.
     */
    private void chicha(String version) {
        ImageIcon creditsIcon = new ImageIcon(toolkit.getImage(this.getClass().getResource("/graphics/myIcon.png")));
        
        creditsIcon = new ImageIcon(creditsIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

    	String line = "<html>FIESTA DE FULBITO " + version + "<p><p>   Créditos<p> ©AkamaiSoftware";
    	
    	JOptionPane.showMessageDialog(null, line, "Créditos", JOptionPane.PLAIN_MESSAGE, creditsIcon);
    }

    /**
     * Este método se encarga de togglear el estado de un JButton.
     * Si el botón está activo, se lo desactiva; y viceversa.
     * 
     * @param   button  Botón a togglear.
     */
    private void toggleButton(JButton button) {
        if (button.isEnabled()) button.setEnabled(false);
        else button.setEnabled(true);
    }

    // ----------------------------------------Clases privadas----------------------------------

    /**
     * Clase privada para lidiar con los eventos de los botones.
     */
    private class ActionHandler implements ActionListener {

        /**
         * Override para indicar qué hacer en base a cada boton pulsado.
         * 
         * @param   e   Evento ocurrido (botón pulsado).
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) {
                toggleButton(chichaButton);
                toggleButton(exitButton);
                System.out.println("START BUTTON PULSADO");
                toggleButton(chichaButton);
                toggleButton(exitButton);
            }
            else if (e.getSource() == chichaButton) {
                toggleButton(startButton);
                toggleButton(exitButton);
                chicha(version);
                toggleButton(startButton);
                toggleButton(exitButton);
            }
            else System.exit(0);
        }
    }
}