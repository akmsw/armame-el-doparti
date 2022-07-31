package armameeldoparti.views;

import armameeldoparti.abstractclasses.View;
import armameeldoparti.utils.Main;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import net.miginfocom.swing.MigLayout;

/**
 * Main menu view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 27/02/2021
 */
public class MainMenuView extends View {

  // ---------------------------------------- Private constants ---------------------------------

  private static final String GROWX = "growx";
  private static final String FRAME_TITLE = Main.PROGRAM_TITLE + " " + Main.PROGRAM_VERSION;

  // ---------------------------------------- Private fields ------------------------------------

  private JPanel masterPanel;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye la ventana principal.
   */
  public MainMenuView() {
    initializeInterface();
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Initializes the view and makes it visible.
   */
  @Override
  protected void initializeInterface() {
    masterPanel = new JPanel(new MigLayout("wrap"));

    addBackground();
    addButtons();
    add(masterPanel);
    setResizable(false);
    setTitle(FRAME_TITLE);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(Main.ICON.getImage());
    pack();
    setLocationRelativeTo(null);
  }

  /**
   * Adds the buttons to their corresponding panel.
   */
  @Override
  protected void addButtons() {
    JButton startButton = new JButton("Comenzar");
    JButton helpButton = new JButton("Ayuda");

    startButton.addActionListener(e ->
        Main.getMainMenuController()
            .startButtonEvent()
    );

    helpButton.addActionListener(e ->
        Main.getMainMenuController()
            .helpButtonEvent()
    );

    masterPanel.add(startButton, GROWX);
    masterPanel.add(helpButton, GROWX);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the background image to the panel.
   */
  private void addBackground() {
    ImageIcon bgImage = new ImageIcon(getClass().getClassLoader()
                                                .getResource(Main.IMG_PATH
                                                             + Main.BG_IMG_FILENAME));

    JLabel bgLabel = new JLabel("", bgImage, SwingConstants.CENTER);

    masterPanel.add(bgLabel, GROWX);
  }
}