package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.models.Error;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicSpinnerUI;

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
  public CustomSpinner(SpinnerNumberModel spinnerNumberModel) {
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
    ((DefaultEditor) getEditor()).getTextField()
                                 .setBackground(Constants.COLOR_GREEN_LIGHT_WHITE);

    setOpaque(false);
    setBorder(
      new AbstractBorder() {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
          Graphics2D g2 = (Graphics2D) g;

          g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
          g2.setColor(Constants.COLOR_GREEN_LIGHT_WHITE);
          g2.fillRoundRect(
              x,
              y,
              (width - 1),
              (height - 1),
              Constants.ROUNDED_BORDER_ARC_SPINNER,
              Constants.ROUNDED_BORDER_ARC_SPINNER
          );
        }

        @Override
        public Insets getBorderInsets(Component c) {
          return new Insets(
            Constants.ROUNDED_BORDER_INSETS_GENERAL,
            Constants.ROUNDED_BORDER_INSETS_GENERAL,
            Constants.ROUNDED_BORDER_INSETS_GENERAL,
            Constants.ROUNDED_BORDER_INSETS_GENERAL
          );
        }
      }
    );
    setUI(new BasicSpinnerUI() {
      /**
       * Configures the spinner 'previous' button to fit the program aesthetics.
       *
       * <p>The "unchecked type" warning is suppressed since the Java compiler can't know at compile
       * time the type of the model minimum (a Comparable) and the current value (an Object).
       *
       * @return The spinner 'previous' button.
       */
      @Override
      @SuppressWarnings("unchecked")
      protected Component createPreviousButton() {
        JButton previousButton = new CustomButton(SwingConstants.SOUTH);

        previousButton.addActionListener(e -> {
          // We know for sure that this custom spinner has a SpinnerNumberModel
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
       * <p>The "unchecked type" warning is suppressed since the Java compiler can't know at compile
       * time the type of the model maximum (a Comparable) and the current value (an Object).
       *
       * @return The spinner 'next' button.
       */
      @Override
      @SuppressWarnings("unchecked")
      protected Component createNextButton() {
        JButton nextButton = new CustomButton(SwingConstants.NORTH);

        nextButton.addActionListener(e -> {
          // We know for sure that this custom spinner has a SpinnerNumberModel
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