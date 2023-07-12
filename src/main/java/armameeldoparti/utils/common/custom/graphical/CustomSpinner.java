package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.models.Error;
import armameeldoparti.utils.common.CommonFunctions;
import java.awt.Component;
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
       * @see CommonFunctions#buildArrowButton(int)
       *
       * @return The spiner 'previous' button.
       */
      @Override
      protected Component createPreviousButton() {
        JButton previousButton = CommonFunctions.buildArrowButton(SwingConstants.SOUTH, this);

        previousButton.addActionListener(e -> {
          try {
            spinner.commitEdit();
            spinner.setValue(getPreviousValue());
          } catch (Exception ex) {
            CommonFunctions.exitProgram(Error.FATAL_INTERNAL_ERROR);
          }
        });

        return previousButton;
      }

      /**
       * Configures the spinner 'next' button to fit the program aesthetics.
       *
       * @see CommonFunctions#buildArrowButton(int)
       *
       * @return The spiner 'next' button.
       */
      @Override
      protected Component createNextButton() {
        JButton nextButton = CommonFunctions.buildArrowButton(SwingConstants.NORTH, this);

        nextButton.addActionListener(e -> {
          try {
            spinner.commitEdit();
            spinner.setValue(getNextValue());
          } catch (Exception ex) {
            CommonFunctions.exitProgram(Error.FATAL_INTERNAL_ERROR);
          }
        });

        return nextButton;
      }
    });
  }
}