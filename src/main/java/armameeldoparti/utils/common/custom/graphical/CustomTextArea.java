package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

/**
 * Custom text area class.
 *
 * <p>This class is used to instantiate a custom text area that fits the overall program
 * aesthetics. It is supposed to be used along with a CustomScrollPane if a scrollbar is needed.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomTextArea extends JTextArea {

  // ---------------------------------------- Constructors --------------------------------------

  /**
   * Builds a basic text area using the established program aesthetics.
   *
   * @param rows       Row count for the text area.
   * @param columns    Column count for the text area.
   * @param scrollable If the text area could handle a text long enough to have a scrollbar.
   */
  public CustomTextArea(int rows, int columns, boolean scrollable) {
    super(rows, columns);
    setUpGraphicalProperties(scrollable);
  }

  /**
   * Builds a basic text area using the established program aesthetics.
   *
   * @param scrollable If the text area could handle a text long enough to have a scrollbar.
   */
  public CustomTextArea(boolean scrollable) {
    super();
    setUpGraphicalProperties(scrollable);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Configures the graphical properties for the text area in order to fit the program aesthetics.
   *
   * @param scrollable If the text area could handle a text long enough to have a scrollbar. If so,
   *                   no border is set for the text area. If not, a bevel border is set.
   */
  private void setUpGraphicalProperties(boolean scrollable) {
    setEditable(false);

    if (!scrollable) {
      setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
                                                Constants.GREEN_MEDIUM,
                                                Constants.GREEN_MEDIUM));
    }
  }
}