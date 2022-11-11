package armameeldoparti.views;

import armameeldoparti.controllers.MainMenuController;
import armameeldoparti.models.Views;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
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

  private static final String FRAME_TITLE = Constants.PROGRAM_TITLE + " "
                                            + Constants.PROGRAM_VERSION;

  // ---------------------------------------- Private fields ------------------------------------

  private JPanel masterPanel;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the main menu view.
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
    setIconImage(Constants.ICON
                          .getImage());
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
        ((MainMenuController) CommonFunctions.getController(Views.MAIN_MENU)).startButtonEvent()
    );

    helpButton.addActionListener(e ->
        ((MainMenuController) CommonFunctions.getController(Views.MAIN_MENU)).helpButtonEvent()
    );

    masterPanel.add(startButton, Constants.GROWX);
    masterPanel.add(helpButton, Constants.GROWX);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the background image to the panel.
   */
  private void addBackground() {
    ImageIcon bgImg = new ImageIcon(getClass().getClassLoader()
                                              .getResource(Constants.PATH_IMG
                                                           + Constants.FILENAME_BG_IMG));

    JLabel bgLabel = new JLabel("", bgImg, SwingConstants.CENTER);

    masterPanel.add(bgLabel, Constants.GROWX);
  }
}