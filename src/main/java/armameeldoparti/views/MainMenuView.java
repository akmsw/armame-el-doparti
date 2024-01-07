package armameeldoparti.views;

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

  // ---------------------------------------------------------------- Private fields -----------------------------------------------------------------

  JButton startButton;
  JButton helpButton;
  JButton contactButton;
  JButton issuesButton;

  // --------------------------------------------------------------- Constructor ---------------------------------------------------------------------

  /**
   * Builds the main menu view.
   */
  public MainMenuView() {
    super(CommonFunctions.capitalize(Constants.PROGRAM_TITLE), Constants.MIG_LAYOUT_WRAP);
    initializeInterface();
  }

  // --------------------------------------------------------------- Protected methods ---------------------------------------------------------------

  @Override
  protected void initializeInterface() {
    addBackground();
    addButtons();
    add(masterPanel);
    pack();
  }

  @Override
  protected void addButtons() {
    startButton = new CustomButton("Comenzar", Constants.ROUNDED_BORDER_ARC_GENERAL);
    helpButton = new CustomButton("Ayuda", Constants.ROUNDED_BORDER_ARC_GENERAL);
    contactButton = new CustomButton("Contacto", Constants.ROUNDED_BORDER_ARC_GENERAL);
    issuesButton = new CustomButton("Reportes y sugerencias", Constants.ROUNDED_BORDER_ARC_GENERAL);

    masterPanel.add(startButton, Constants.MIG_LAYOUT_GROWX);
    masterPanel.add(helpButton, Constants.MIG_LAYOUT_GROWX);
    masterPanel.add(contactButton, new CC().width("50%")
                                           .split());
    masterPanel.add(issuesButton, new CC().width("50%"));
  }

  // ---------------------------------------------------------------- Private methods ----------------------------------------------------------------

  /**
   * Adds the background image and labels to the main menu view.
   */
  private void addBackground() {
    addBackgroundImage();
    addBackgroundLabel(Constants.SIZE_FONT_TITLE_LABEL, Constants.PROGRAM_TITLE, Constants.MIG_LAYOUT_ALIGN_CENTER, Constants.COLOR_GREEN_DARK);
    addBackgroundLabel(Constants.SIZE_FONT_AUTHOR_LABEL, Constants.PROGRAM_AUTHOR, Constants.MIG_LAYOUT_ALIGN_CENTER, Color.WHITE);
    addBackgroundLabel(Constants.SIZE_FONT_VERSION_LABEL, Constants.PROGRAM_VERSION, Constants.MIG_LAYOUT_ALIGN_RIGHT, Constants.COLOR_GREEN_DARK);
  }

  /**
   * Adds the background image to the panel.
   */
  private void addBackgroundImage() {
    masterPanel.add(new JLabel("", Constants.ICON_BACKGROUND, SwingConstants.CENTER), Constants.MIG_LAYOUT_GROWX);
  }

  /**
   * Creates a basic label for the main menu view that will be placed in the background.
   *
   * @param fontSize    The font size for the label text.
   * @param text        The label text.
   * @param constraints The label MiG Layout constraints.
   * @param color       The color used for the label foreground.
   */
  private void addBackgroundLabel(int fontSize, String text, String constraints, Color color) {
    JLabel label = new JLabel(text.toLowerCase());

    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setForeground(color);
    label.setFont(new Font(label.getFont()
                                .getName(),
                           Font.PLAIN,
                           fontSize));

    masterPanel.add(label, constraints);
  }

  // -------------------------------------------------------------------- Getters --------------------------------------------------------------------

  public JButton getStartButton() {
    return startButton;
  }

  public JButton getHelpButton() {
    return helpButton;
  }

  public JButton getContactButton() {
    return contactButton;
  }

  public JButton getIssuesButton() {
    return issuesButton;
  }

  // -------------------------------------------------------------------- Setters --------------------------------------------------------------------

  public void setStartButton(JButton startButton) {
    this.startButton = startButton;
  }

  public void setHelpButton(JButton helpButton) {
    this.helpButton = helpButton;
  }

  public void setContactButton(JButton contactButton) {
    this.contactButton = contactButton;
  }

  public void setIssuesButton(JButton issuesButton) {
    this.issuesButton = issuesButton;
  }
}