package armameeldoparti.utils.common.graphical;

import armameeldoparti.utils.common.Constants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import lombok.Getter;

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
 * @since v3.0
 */
public class CustomScrollPane extends JScrollPane {

  // ---------------------------------------- Private fields ------------------------------------

  @Getter private JTextArea textArea;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds a basic scroll pane using the established program aesthetics.
   *
   * @param textArea Text area associated to the scroll pane that will be controlled.
   */
  public CustomScrollPane(JTextArea textArea) {
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
       * <p>The button and the shadows will be medium green. The arrow will be light green.
       *
       * @param orientation The button arrow orientation.
       *
       * @return The scrollbar decrease button.
       */
      @Override
      protected JButton createDecreaseButton(int orientation) {
        JButton decreaseButton = new BasicArrowButton(orientation,
                                                      Constants.GREEN_MEDIUM,
                                                      Constants.GREEN_MEDIUM,
                                                      Constants.GREEN_LIGHT,
                                                      Constants.GREEN_MEDIUM);

        decreaseButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
                                                                 Constants.GREEN_MEDIUM,
                                                                 Constants.GREEN_DARK));

        return decreaseButton;
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