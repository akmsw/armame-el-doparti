package armameeldoparti.views;

import javax.swing.JFrame;

/**
 * Abstract class that specifies the basic views methods.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 28/07/2022
 */
public abstract class View extends JFrame {

  // ---------------------------------------- Protected fields ----------------------------------

  protected String frameTitle;

  // ---------------------------------------- Protected constructor -----------------------------

  /**
   * Simple constructor.
   * Sets the frame title.
   *
   * @param frameTitle The frame title.
   */
  protected View(String frameTitle) {
    setFrameTitle(frameTitle);
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

  // ---------------------------------------- Protected methods ---------------------------------

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Gets the frame title.
   *
   * @return The frame title.
   */
  protected String getFrameTitle() {
    return frameTitle;
  }

  // ---------------------------------------- Setters -------------------------------------------

  /**
   * Sets the frame title.
   *
   * @param frameTitle The new frame title.
   */
  protected void setFrameTitle(String frameTitle) {
    this.frameTitle = frameTitle;
  }
}