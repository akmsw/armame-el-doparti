package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

/**
 * Custom text area class.
 *
 * <p>This class is used to instantiate a custom text area that fits the overall program
 * aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since v3.0
 */
public class CustomTextArea extends JTextArea {

  // ---------------------------------------- Constructors --------------------------------------

  /**
   * Builds a basic text area using the established program aesthetics.
   *
   * @param rows    Row count for the text area.
   * @param columns Column count for the text area.
   */
  public CustomTextArea(int rows, int columns, boolean scrollable) {
    super(rows, columns);
    setupGraphicalProperties(scrollable);
  }

  /**
   * Builds a basic text area using the established program aesthetics.
   */
  public CustomTextArea(boolean scrollable) {
    super();
    setupGraphicalProperties(scrollable);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Configures the graphical properties for the text area in order to fit the program aesthetics.
   */
  private void setupGraphicalProperties(boolean scrollable) {
    setEditable(false);

    if (!scrollable) {
      setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
                                                Constants.GREEN_MEDIUM,
                                                Constants.GREEN_MEDIUM));
    }
  }
}