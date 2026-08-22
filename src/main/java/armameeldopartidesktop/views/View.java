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

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Basic view constructor.
   *
   * @param title             The view title.
   * @param layoutConstraints The view layout constraints.
   */
  protected View(String title, String layoutConstraints) {
    setTitle(title);
    setLayout(new MigLayout(layoutConstraints));
    setOpaque(true);
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Refreshes the view to reflect any changes.
   */
  public void refreshView() {
    revalidate();
    repaint();
  }

  // ---------- Abstract protected methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Initializes the view's graphical interface.
   *
   * <p>All the graphical components should be added to the view in this method.
   */
  protected abstract void initializeInterface();

  /**
   * Adds the buttons.
   */
  protected abstract void addButtons();

  // ---------- Public getters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public String getTitle() {
    return title;
  }

  // ---------- Public setters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setTitle(String title) {
    this.title = title;
  }
}