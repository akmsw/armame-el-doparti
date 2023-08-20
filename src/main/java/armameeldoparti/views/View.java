package armameeldoparti.views;

import armameeldoparti.utils.common.Constants;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import net.miginfocom.swing.MigLayout;

/**
 * Abstract class that specifies the basic views methods and fields.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public abstract class View extends JFrame {

  // ---------------------------------------- Protected fields ----------------------------------

  protected String frameTitle;

  protected JPanel masterPanel;

  // ---------------------------------------- Protected constructor -----------------------------

  /**
   * Basic view constructor.
   *
   * @param frameTitle                   The frame title.
   * @param masterPanelLayoutConstraints The layout constraints for the view's master panel.
   */
  protected View(String frameTitle, String masterPanelLayoutConstraints) {
    setMasterPanel(new JPanel(new MigLayout(masterPanelLayoutConstraints)));
    setResizable(false);
    setFrameTitle(frameTitle);
    setTitle(getFrameTitle());
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(Constants.ICON_MAIN
                          .getImage());
  }

  // ---------------------------------------- Abstract protected methods ------------------------

  /**
   * Initializes the view and makes it visible.
   */
  protected abstract void initializeInterface();

  /**
   * Adds the buttons to their corresponding panel.
   */
  protected abstract void addButtons();

  // ---------------------------------------- Getters -------------------------------------------

  public String getFrameTitle() {
    return frameTitle;
  }

  public JPanel getMasterPanel() {
    return masterPanel;
  }

  // ---------------------------------------- Setters -------------------------------------------

  public void setFrameTitle(String frameTitle) {
    this.frameTitle = frameTitle;
  }

  public void setMasterPanel(JPanel masterPanel) {
    this.masterPanel = masterPanel;
  }
}