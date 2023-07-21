package armameeldoparti.views;

import armameeldoparti.controllers.MainMenuController;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.CustomButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import net.miginfocom.layout.CC;

/**
 * Main menu view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class MainMenuView extends View {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the main menu view.
   */
  public MainMenuView() {
    super(CommonFunctions.capitalize(Constants.PROGRAM_TITLE), Constants.MIG_LAYOUT_WRAP);
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
    pack();
  }

  /**
   * Adds the buttons to their corresponding panel.
   */
  @Override
  protected void addButtons() {
    JButton startButton = new CustomButton("Comenzar");
    startButton.addActionListener(e ->
        ((MainMenuController) CommonFunctions.getController(ProgramView.MAIN_MENU))
        .startButtonEvent()
    );

    JButton helpButton = new CustomButton("Ayuda");
    helpButton.addActionListener(e ->
        ((MainMenuController) CommonFunctions.getController(ProgramView.MAIN_MENU))
        .helpButtonEvent()
    );

    JButton contactButton = new CustomButton("Contacto");
    contactButton.addActionListener(e ->
        ((MainMenuController) CommonFunctions.getController(ProgramView.MAIN_MENU))
        .contactButtonEvent()
    );

    JButton issuesButton = new CustomButton("Reportes y sugerencias");
    issuesButton.addActionListener(e ->
        ((MainMenuController) CommonFunctions.getController(ProgramView.MAIN_MENU))
        .issuesButtonEvent()
    );

    getMasterPanel().add(startButton, Constants.MIG_LAYOUT_GROWX);
    getMasterPanel().add(helpButton, Constants.MIG_LAYOUT_GROWX);
    getMasterPanel().add(contactButton, new CC().width("50%")
                                                .split());
    getMasterPanel().add(issuesButton, new CC().width("50%"));
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the background image and labels to the main menu view.
   */
  private void addBackground() {
    addBackgroundImage();
    addBackgroundLabel(
        Constants.SIZE_FONT_TITLE_LABEL,
        Constants.PROGRAM_TITLE,
        Constants.MIG_LAYOUT_ALIGN_CENTER,
        Constants.GREEN_DARK,
        null
    );
    addBackgroundLabel(
        Constants.SIZE_FONT_AUTHOR_LABEL,
        Constants.PROGRAM_AUTHOR,
        Constants.MIG_LAYOUT_ALIGN_CENTER,
        Color.WHITE,
        null
    );
    addBackgroundLabel(
        Constants.SIZE_FONT_VERSION_LABEL,
        Constants.PROGRAM_VERSION,
        Constants.MIG_LAYOUT_ALIGN_RIGHT,
        Constants.GREEN_DARK,
        "Versión del programa"
    );
  }

  /**
   * Adds the background image to the panel.
   */
  private void addBackgroundImage() {
    getMasterPanel().add(
        new JLabel("", Constants.ICON_BACKGROUND, SwingConstants.CENTER),
        Constants.MIG_LAYOUT_GROWX
    );
  }

  /**
   * Creates a basic label for the main menu view that will be placed and centered in the
   * background.
   *
   * @param fontSize    The font size for the label text.
   * @param text        The label text.
   * @param constraints The label MiG Layout constraints.
   * @param color       The color used for the label foreground.
   * @param tooltip     Optional label tooltip.
   */
  private void addBackgroundLabel(int fontSize,
                                  String text,
                                  String constraints,
                                  Color color,
                                  String tooltip) {
    JLabel label = new JLabel(text.toLowerCase());

    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setForeground(color);
    label.setToolTipText(tooltip);
    label.setFont(new Font(label.getFont()
                                .getName(), Font.PLAIN, fontSize));

    getMasterPanel().add(label, constraints);
  }
}