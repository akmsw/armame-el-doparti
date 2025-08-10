package armameeldopartidesktop.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import armameeldopartidesktop.utils.common.Constants;

import net.miginfocom.swing.MigLayout;

/**
 * Abstract class that specifies the basic view methods and fields.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public abstract class View extends JFrame {

  // ---------- Protected fields --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  protected JPanel masterPanel;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Basic view constructor.
   *
   * @param frameTitle                   The frame title.
   * @param masterPanelLayoutConstraints The layout constraints for the view's master panel.
   */
  protected View(String frameTitle, String masterPanelLayoutConstraints) {
    setMasterPanel(new JPanel(new MigLayout(masterPanelLayoutConstraints)));
    setResizable(false);
    setTitle(frameTitle);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(Constants.ICON_MAIN_SCALED.getImage());
  }

  // ---------- Abstract protected methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Initializes the view interface.
   */
  protected abstract void initializeInterface();

  /**
   * Adds the buttons to their corresponding panel.
   */
  protected abstract void addButtons();

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public JPanel getMasterPanel() {
    return masterPanel;
  }

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setMasterPanel(JPanel masterPanel) {
    this.masterPanel = masterPanel;
  }
}