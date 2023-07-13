package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.models.Error;
import armameeldoparti.utils.common.CommonFunctions;
import java.awt.Component;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicSpinnerUI;
import lombok.NonNull;

/**
 * Custom spinner class.
 *
 * <p>This class is used to instantiate a custom spinner that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomSpinner extends JSpinner {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds a basic spinner that fits the established program aesthetics.
   *
   * @param spinnerNumberModel The number model used for the spinner.
   */
  public CustomSpinner(@NonNull SpinnerNumberModel spinnerNumberModel) {
    super(spinnerNumberModel);
    setUpGraphicalProperties();
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Configures the graphical properties of the spinner in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    ((DefaultEditor) getEditor()).getTextField()
        .setEditable(false);
    setUI(new BasicSpinnerUI() {
      /**
       * Configures the spinner 'previous' button to fit the program aesthetics.
       *
       * @see CommonFunctions#buildCustomArrowButton(int, Object)
       *
       * @return The spiner 'previous' button.
       */
      @Override
      @SuppressWarnings("unchecked")
      protected Component createPreviousButton() {
        JButton previousButton = CommonFunctions.buildCustomArrowButton(SwingConstants.SOUTH, this);

        previousButton.addActionListener(e -> {
          SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
          Comparable<Object> minimum = (Comparable<Object>) model.getMinimum();
          Comparable<Object> currentValue = (Comparable<Object>) model.getValue();

          if (currentValue.compareTo(minimum) > 0) {
            try {
              spinner.commitEdit();
              spinner.setValue(model.getPreviousValue());
            } catch (ParseException ex) {
              CommonFunctions.exitProgram(Error.FATAL_INTERNAL_ERROR);
            }
          }
        });

        return previousButton;
      }

      /**
       * Configures the spinner 'next' button to fit the program aesthetics.
       *
       * @see CommonFunctions#buildCustomArrowButton(int, Object)
       *
       * @return The spiner 'next' button.
       */
      @Override
      @SuppressWarnings("unchecked")
      protected Component createNextButton() {
        JButton nextButton = CommonFunctions.buildCustomArrowButton(SwingConstants.NORTH, this);

        nextButton.addActionListener(e -> {
          SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
          Comparable<Object> maximum = (Comparable<Object>) model.getMaximum();
          Comparable<Object> currentValue = (Comparable<Object>) model.getValue();

          if (currentValue.compareTo(maximum) < 0) {
            try {
              spinner.commitEdit();
              spinner.setValue(model.getNextValue());
            } catch (ParseException ex) {
              CommonFunctions.exitProgram(Error.FATAL_INTERNAL_ERROR);
            }
          }
        });

        return nextButton;
      }
    });
  }
}