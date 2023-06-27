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
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import net.miginfocom.layout.CC;

/**
 * Main menu view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since v3.0
 */
public class MainMenuView extends View {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the main menu view.
   */
  public MainMenuView() {
    super(Constants.PROGRAM_TITLE + " " + Constants.PROGRAM_VERSION, "wrap");
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
    add(getMasterPanel());
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
        ((MainMenuController) CommonFunctions.getController(ProgramView.MAIN_MENU))
        .startButtonEvent()
    );

    JButton helpButton = new JButton("Ayuda");
    helpButton.addActionListener(e ->
        ((MainMenuController) CommonFunctions.getController(ProgramView.MAIN_MENU))
        .helpButtonEvent()
    );

    JButton contactButton = new JButton("Contacto");
    contactButton.addActionListener(e ->
        ((MainMenuController) CommonFunctions.getController(ProgramView.MAIN_MENU))
        .contactButtonEvent()
    );

    JButton issuesButton = new JButton("Reportes y sugerencias");
    issuesButton.addActionListener(e ->
        ((MainMenuController) CommonFunctions.getController(ProgramView.MAIN_MENU))
        .issuesButtonEvent()
    );

    for (JButton button : Arrays.asList(startButton, helpButton)) {
      getMasterPanel().add(button, "growx");
    }

    getMasterPanel().add(contactButton, new CC().width("50%")
                                                .split());
    getMasterPanel().add(issuesButton, new CC().width("50%"));
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

    getMasterPanel().add(bgLabel, "growx");
  }
}