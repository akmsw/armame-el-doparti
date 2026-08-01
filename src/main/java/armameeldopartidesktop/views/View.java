package armameeldopartidesktop.views;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * Abstract class that specifies the basic view methods and fields.
 *
 * @since 3.0.0
 *
 * @version 2.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public abstract class View extends JPanel {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private String viewTitle;

  // ---------- Protected fields --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  protected JPanel mainPanel;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Basic view constructor.
   *
   * @param viewTitle                 The frame title.
   * @param mainPanelLayoutConstraints The layout constraints for the view's master panel.
   */
  protected View(String viewTitle, String mainPanelLayoutConstraints) {
    setViewTitle(viewTitle);
    setLayout(new MigLayout());
    setMainPanel(new JPanel(new MigLayout(mainPanelLayoutConstraints)));
    setOpaque(true);
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void refreshView() {
    revalidate();
    repaint();
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

  // ---------- Public getters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public JPanel getMainPanel() {
    return mainPanel;
  }

  public String getViewTitle() {
    return viewTitle;
  }

  // ---------- Public setters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setViewTitle(String viewTitle) {
    this.viewTitle = viewTitle;
  }

  public void setMainPanel(JPanel mainPanel) {
    this.mainPanel = mainPanel;
  }
}