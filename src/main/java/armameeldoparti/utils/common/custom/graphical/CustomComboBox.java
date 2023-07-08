package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import lombok.NonNull;

/**
 * Custom text area class.
 *
 * <p>This class is used to instantiate a custom combobox that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since v3.0
 */
public class CustomComboBox<E> extends JComboBox<E> {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds a basic combobox using the established program aesthetics.
   */
  public CustomComboBox(@NonNull E[] items) {
    super(items);
    setupGraphicalProperties();
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Configures the graphical properties for the combobox in order to fit the program aesthetics.
   */
  private void setupGraphicalProperties() {
    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
                                              Constants.GREEN_MEDIUM,
                                              Constants.GREEN_DARK));
    setUI(new BasicComboBoxUI() {
      @Override
      protected JButton createArrowButton() {
        return new CustomArrowButton(SwingConstants.SOUTH);
      }
    });
  }
}