package armameeldoparti.views;

import armameeldoparti.controllers.MainMenuController;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import net.miginfocom.layout.CC;
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

  // ---------------------------------------- Private fields ------------------------------------

  private final JPanel masterPanel;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the main menu view.
   */
  public MainMenuView() {
    super(Constants.PROGRAM_TITLE + " " + Constants.PROGRAM_VERSION);

    masterPanel = new JPanel(new MigLayout("wrap"));

    initializeInterface();
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Initializes the view and makes it visible.
   */
  @Override
  protected void initializeInterface() {
    addBackground();
    addButtons();
    add(masterPanel);
    setResizable(false);
    setTitle(getFrameTitle());
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(Constants.ICON
                          .getImage());
    pack();
  }

  /**
   * Adds the buttons to their corresponding panel.
   */
  @Override
  protected void addButtons() {
    JButton startButton = new JButton("Comenzar");

    startButton.addActionListener(e ->
        ((MainMenuController) CommonFunctions.getController(ProgramView.MAIN_MENU)).startButtonEvent()
    );

    JButton helpButton = new JButton("Ayuda");

    helpButton.addActionListener(e ->
        ((MainMenuController) CommonFunctions.getController(ProgramView.MAIN_MENU)).helpButtonEvent()
    );

    JButton contactButton = new JButton("Contacto");

    contactButton.addActionListener(e ->
        ((MainMenuController) CommonFunctions.getController(ProgramView.MAIN_MENU)).contactButtonEvent()
    );

    JButton issuesButton = new JButton("Reportes y sugerencias");

    issuesButton.addActionListener(e ->
        ((MainMenuController) CommonFunctions.getController(ProgramView.MAIN_MENU)).issuesButtonEvent()
    );

    for (JButton button : Arrays.asList(startButton, helpButton)) {
      masterPanel.add(button, "growx");
    }

    masterPanel.add(contactButton, new CC().width("50%")
                                           .split());
    masterPanel.add(issuesButton, new CC().width("50%"));
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the background image to the panel.
   */
  private void addBackground() {
    ImageIcon bgImg = new ImageIcon(Objects.requireNonNull(
        getClass().getClassLoader()
                  .getResource(Constants.PATH_IMG + Constants.FILENAME_BG_IMG),
        Constants.MSG_ERROR_NULL_RESOURCE
      )
    );

    JLabel bgLabel = new JLabel("", bgImg, SwingConstants.CENTER);

    masterPanel.add(bgLabel, "growx");
  }
}