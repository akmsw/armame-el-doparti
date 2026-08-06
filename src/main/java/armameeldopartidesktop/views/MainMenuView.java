package armameeldopartidesktop.views;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.utils.common.custom.graphical.CustomLabel;

import net.miginfocom.layout.CC;

/**
 * Main menu view class.
 *
 * @since 3.0.0
 *
 * @version 1.1.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class MainMenuView extends View {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private JButton startButton;
  private JButton helpButton;
  private JButton contactButton;
  private JButton issuesButton;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the main menu view.
   */
  public MainMenuView() {
    super(Constants.TITLE_VIEW_MAIN_MENU, Constants.MIG_LAYOUT_WRAP);

    initializeInterface();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void initializeInterface() {
    addBackground();
    addButtons();
    refreshView();
  }

  @Override
  protected void addButtons() {
    setStartButton(new JButton("Comenzar"));
    setHelpButton(new JButton("Ayuda"));
    setContactButton(new JButton("Contacto"));
    setIssuesButton(new JButton("Reportes y sugerencias"));

    add(startButton, Constants.MIG_LAYOUT_GROWX);
    add(helpButton, Constants.MIG_LAYOUT_GROWX);
    add(contactButton, new CC().width("50%").split());
    add(issuesButton, new CC().width("50%"));
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Adds the background image and labels.
   */
  private void addBackground() {
    addBackgroundImage();
    addLabel(Constants.PROGRAM_TITLE, null, Constants.MIG_LAYOUT_ALIGN_CENTER, Constants.COLOR_GREEN_LIGHT, Constants.COLOR_GREEN_DARK, Constants.SIZE_FONT_TITLE_LABEL);
    addLabel(Constants.PROGRAM_AUTHOR, null, Constants.MIG_LAYOUT_ALIGN_CENTER, Constants.COLOR_GREEN_LIGHT, Color.WHITE, Constants.SIZE_FONT_AUTHOR_LABEL);
    addLabel(Constants.PROGRAM_VERSION, Constants.TOOLTIP_MSG_PROGRAM_VERSION, Constants.MIG_LAYOUT_ALIGN_RIGHT, Constants.COLOR_GREEN_LIGHT, Constants.COLOR_GREEN_DARK, Constants.SIZE_FONT_VERSION_LABEL);
  }

  /**
   * Adds the background image.
   */
  private void addBackgroundImage() {
    add(new JLabel(null, Constants.ICON_BACKGROUND, SwingConstants.CENTER), Constants.MIG_LAYOUT_GROWX);
  }

  /**
   * Adds a customizable label.
   *
   * @param text            The label text.
   * @param tooltipText     The label tooltip text.
   * @param constraints     The label MiG Layout constraints.
   * @param backgroundColor The color used for the label background.
   * @param foregroundColor The color used for the label foreground.
   * @param fontSize        The font size for the label text.
   */
  private void addLabel(String text, String tooltipText, String constraints, Color backgroundColor, Color foregroundColor, int fontSize) {
    add(new CustomLabel(text.toLowerCase(), tooltipText, backgroundColor, foregroundColor, SwingConstants.CENTER, fontSize), constraints);
  }

  // ---------- Public getters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

  // ---------- Public setters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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