package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import lombok.NonNull;

/**
 * Custom scroll pane class.
 *
 * <p>This class is used to instantiate a custom scroll pane that fits the overall program
 * aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomScrollPane extends JScrollPane {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds a basic scroll pane using the established program aesthetics.
   *
   * @param textArea Text area associated to the scroll pane that will be controlled.
   */
  public CustomScrollPane(@NonNull JTextArea textArea) {
    super(textArea,
          ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
          ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    setupGraphicalProperties();
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Configures the graphical properties of the scroll pane in order to fit the program aesthetics.
   */
  private void setupGraphicalProperties() {
    setBackground(Constants.GREEN_LIGHT);
    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
                                              Constants.GREEN_MEDIUM,
                                              Constants.GREEN_MEDIUM));
    getVerticalScrollBar().setUI(new BasicScrollBarUI() {
      /**
       * Configures the scrollbar colors to fit the program aesthetics.
       *
       * <p>The thumb will be dark green and the track will be medium green.
       */
      @Override
      protected void configureScrollBarColors() {
        this.thumbColor = Constants.GREEN_DARK;
        this.trackColor = Constants.GREEN_MEDIUM;
      }

      /**
       * Configures the scrollbar decrease button to fit the program aesthetics.
       *
       * @see CommonFunctions#buildArrowButton(int)
       *
       * @param orientation The button arrow orientation.
       *
       * @return The scrollbar decrease button.
       */
      @Override
      protected JButton createDecreaseButton(int orientation) {
        return CommonFunctions.buildArrowButton(orientation);
      }

      /**
       * Configures the scrollbar increase button to fit the program aesthetics. It'll have the same
       * graphical properties as the scrollbar decrease button.
       *
       * @see #createDecreaseButton(int)
       *
       * @param orientation The button arrow orientation.
       *
       * @return The scrollbar increase button.
       */
      @Override
      protected JButton createIncreaseButton(int orientation) {
        return createDecreaseButton(orientation);
      }
    });
  }
}