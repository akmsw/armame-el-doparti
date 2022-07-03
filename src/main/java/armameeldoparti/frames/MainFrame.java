package armameeldoparti.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Enumeration;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.FontUIResource;
import net.miginfocom.swing.MigLayout;

/**
 * Clase correspondiente a la ventana del menú principal del programa.
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
    public static final ImageIcon ICON = new ImageIcon(MainFrame.class
                                                                .getResource(Main.IMG_PATH + Main.ICON_FILENAME));

    /**
     * Imagen escalada del icono de la aplicación.
     */
    public static final ImageIcon SCALED_ICON = new ImageIcon(ICON.getImage()
                                                                  .getScaledInstance(50, 50, Image.SCALE_SMOOTH));

    // ---------------------------------------- Constantes privadas ------------------------------

    /**
     * Posibles cantidades de jugadores por equipo.
     */
    private static final Integer[] PLAYERS_PER_TEAM = {7, 8};

    /**
     * Configuración utilizada frecuentemente.
     */
    private static final String GROWX = "growx";

    /**
     * Nombre del archivo de imagen de fondo para el menú principal.
     */
    private static final String BG_IMG_FILENAME = "bg.png";

    // ---------------------------------------- Campos privados ----------------------------------

    private JButton startButton;
    private JButton helpButton;

    private Font programFont;

    // ---------------------------------------- Constructor --------------------------------------

    /**
     * Construye la ventana principal.
     */
    public MainFrame() {
        setGUIProperties();

        ImageIcon bgImage = new ImageIcon(this.getClass()
                                              .getResource(Main.IMG_PATH + BG_IMG_FILENAME));

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

        panel.setBackground(Main.LIGHT_GREEN);

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
     * Despliega las instrucciones de uso del programa.
     */
    private void help() {
        // TODO
    }

    /**
     * Configura las propiedades de la interfaz gráfica del programa.
     */
    private void setGUIProperties() {
        UIManager.put("OptionPane.background", Main.LIGHT_GREEN);
        UIManager.put("Panel.background", Main.LIGHT_GREEN);
        UIManager.put("CheckBox.background", Main.LIGHT_GREEN);
        UIManager.put("Separator.background", Main.LIGHT_GREEN);
        UIManager.put("Button.background", Main.DARK_GREEN);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("CheckBox.focus", Main.LIGHT_GREEN);
        UIManager.put("Button.focus", Main.DARK_GREEN);
        UIManager.put("ToggleButton.focus", Main.DARK_GREEN);
        UIManager.put("ComboBox.focus", Color.WHITE);

        try {
            // Se registra la fuente para poder utilizarla
            programFont = Font.createFont(Font.TRUETYPE_FONT,
                                          this.getClass()
                                              .getClassLoader()
                                              .getResourceAsStream(Main.TTF_PATH + Main.FONT_NAME))
                              .deriveFont(Main.FONT_SIZE);

            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(programFont);
        } catch (IOException | FontFormatException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        setUIFont(programFont);
    }

    /**
     * Aplica la fuente para el programa.
     *
     * @param f Fuente a utilizar.
     */
    private void setUIFont(Font f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();

        while (keys.hasMoreElements()) {
            Object k = keys.nextElement();
            Object value = UIManager.get(k);

            if (value instanceof FontUIResource) {
                UIManager.put(k, f);
            }
        }
    }

    // ---------------------------------------- Métodos públicos ---------------------------------

    /**
     * Indica qué hacer en base a cada botón pulsado.
     * <p>
     * Si se presiona el botón "Comenzar", se crea una ventana de tipo InputFrame.
     * <p>
     * Si se presiona el botón "Ayuda", se crea una ventana de tipo helpFrame.
     *
     * @param e Evento de click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            int selected = JOptionPane.showOptionDialog(null, "Seleccione la cantidad de jugadores por equipo",
                                                        "Antes de empezar...", 2, JOptionPane.QUESTION_MESSAGE,
                                                        SCALED_ICON, PLAYERS_PER_TEAM, PLAYERS_PER_TEAM[0]);

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
        } else {
            help();
        }
    }
}
