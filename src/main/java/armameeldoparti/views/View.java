package armameeldoparti.views;

import javax.swing.JFrame;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract class that specifies the basic views methods and fields.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 28/07/2022
 */
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public abstract class View extends JFrame {

  // ---------------------------------------- Protected fields ----------------------------------

  protected String frameTitle;

  // ---------------------------------------- Protected constructor -----------------------------

  /**
   * Basic view constructor.
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
}