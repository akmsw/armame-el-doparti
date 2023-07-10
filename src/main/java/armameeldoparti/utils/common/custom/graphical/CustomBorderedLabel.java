package armameeldoparti.utils.common.custom.graphical;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import lombok.NonNull;

/**
 * Custom bordered label class.
 *
 * <p>This class is used to instantiate a custom bordered label that fits the overall program
 * aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomBorderedLabel extends JLabel {

  // ---------------------------------------- Constructors --------------------------------------

  /**
   * Builds a basic empty bordered label using the established program aesthetics.
   *
   * @param alignment The label text alignment.
   */
  public CustomBorderedLabel(int alignment) {
    super();
    setupGraphicalProperties(alignment);
  }

  /**
   * Builds a basic bordered label using the established program aesthetics.
   *
   * @param text      The label text.
   * @param alignment The label text alignment.
   */
  public CustomBorderedLabel(@NonNull String text, int alignment) {
    super(text);
    setupGraphicalProperties(alignment);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Configures the graphical properties of the label in order to fit the program aesthetics. The
   * label will have a lowered bevel border.
   *
   * @param alignment The label text alignment.
   */
  private void setupGraphicalProperties(int alignment) {
    setBorder(BorderFactory.createLoweredSoftBevelBorder());
    setHorizontalAlignment(alignment);
  }
}