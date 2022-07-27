package armameeldoparti.views;

import armameeldoparti.controllers.MainMenuController;
import armameeldoparti.utils.Main;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
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
public class MainMenuView extends JFrame {

  // ---------------------------------------- Constantes públicas -------------------------------

  /**
   * Imagen estándar del icono de la aplicación.
   */
  public static final ImageIcon ICON = new ImageIcon(MainMenuView.class
                                                                 .getClassLoader()
                                                                 .getResource(
                                                                            Main.IMG_PATH
                                                                            + Main.ICON_FILENAME));

  /**
   * Imagen escalada del icono de la aplicación.
   */
  public static final ImageIcon SCALED_ICON = new ImageIcon(ICON.getImage()
                                                                .getScaledInstance(
                                                                  50, 50, Image.SCALE_SMOOTH));

  // ---------------------------------------- Constantes privadas -------------------------------

  private static final String GROWX = "growx";
  private static final String FRAME_TITLE = Main.PROGRAM_TITLE + " " + Main.PROGRAM_VERSION;

  // ---------------------------------------- Campos privados -----------------------------------

  private JPanel masterPanel;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye la ventana principal.
   */
  public MainMenuView() {
    initializeInterface();
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Inicializa y muestra la interfaz gráfica de esta ventana.
   */
  private void initializeInterface() {
    masterPanel = new JPanel(new MigLayout("wrap"));

    addBackground();
    addButtons();
    add(masterPanel);
    setResizable(false);
    setTitle(FRAME_TITLE);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(ICON.getImage());
    pack();
    setLocationRelativeTo(null);
  }

  /**
   * Añade la imagen de fondo al panel de la ventana.
   */
  private void addBackground() {
    ImageIcon bgImage = new ImageIcon(getClass().getClassLoader()
                                                .getResource(Main.IMG_PATH
                                                             + Main.BG_IMG_FILENAME));

    JLabel bgLabel = new JLabel("", bgImage, SwingConstants.CENTER);

    masterPanel.add(bgLabel, GROWX);
  }

  /**
   * Añade los botones al panel de la ventana.
   */
  private void addButtons() {
    JButton startButton = new JButton("Comenzar");
    JButton helpButton = new JButton("Ayuda");

    startButton.addActionListener(e -> MainMenuController.startButtonEvent());
    helpButton.addActionListener(e -> MainMenuController.helpButtonEvent());

    masterPanel.add(startButton, GROWX);
    masterPanel.add(helpButton, GROWX);
  }
}