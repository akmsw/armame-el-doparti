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

  private String title;

  // ---------- Protected fields --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  protected JPanel panel;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Basic view constructor.
   *
   * @param title                  The frame title.
   * @param panelLayoutConstraints The layout constraints for the view's main panel.
   */
  protected View(String title, String panelLayoutConstraints) {
    setTitle(title);
    setLayout(new MigLayout());
    setPanel(new JPanel(new MigLayout(panelLayoutConstraints)));
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

  public JPanel getPanel() {
    return panel;
  }

  public String getTitle() {
    return title;
  }

  // ---------- Public setters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setTitle(String title) {
    this.title = title;
  }

  public void setPanel(JPanel panel) {
    this.panel = panel;
  }
}