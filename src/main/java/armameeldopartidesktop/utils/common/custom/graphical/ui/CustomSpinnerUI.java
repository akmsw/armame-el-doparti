package armameeldopartidesktop.utils.common.custom.graphical.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.text.DefaultCaret;

import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.utils.common.custom.graphical.CustomArrowButton;

/**
 * A custom spinner UI that fits the overall program aesthetics.
 *
 * @since 3.0.0
 *
 * @version 1.0.1
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CustomSpinnerUI extends BasicSpinnerUI {

  // ---------- Public static methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new custom spinner UI that fits the overall program aesthetics.
   *
   * @param component Component to which to apply the custom UI.
   *
   * @return A new custom spinner UI.
   */
  public static ComponentUI createUI(JComponent component) {
    JSpinner spinner = (JSpinner) component;

    spinner.setBackground(Constants.COLOR_GREEN_LIGHT_WHITE);
    spinner.setOpaque(false);
    spinner.setBorder(
      new AbstractBorder() {
        @Override
        public void paintBorder(Component component, Graphics graphics, int x, int y, int width, int height) {
          Graphics2D graphics2d = (Graphics2D) graphics;

          graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
          graphics2d.setColor(Constants.COLOR_GREEN_LIGHT_WHITE);
          graphics2d.fillRoundRect(x, y, width, height, Constants.ROUNDED_BORDER_ARC_SPINNER, Constants.ROUNDED_BORDER_ARC_SPINNER);
        }

        @Override
        public Insets getBorderInsets(Component component) {
          return Constants.INSETS_GENERAL;
        }
      }
    );

    JComponent editor = spinner.getEditor();

    JFormattedTextField spinnerTextField = ((DefaultEditor) editor).getTextField();

    editor.setOpaque(false);

    spinnerTextField.setOpaque(false);
    spinnerTextField.setBorder(null);
    spinnerTextField.setEditable(false);
    spinnerTextField.setCaret(
      new DefaultCaret() {
        @Override
        public boolean isVisible() {
          return false;
        }

        @Override
        public void setSelectionVisible(boolean visible) {
          super.setSelectionVisible(false);
        }

        @Override
        public boolean isSelectionVisible() {
          return false;
        }
      }
    );

    return new CustomSpinnerUI();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void paint(Graphics graphics, JComponent component) {
    Graphics2D graphics2d = (Graphics2D) graphics;

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.fillRoundRect(0, 0, component.getWidth(), component.getHeight(), Constants.ROUNDED_BORDER_ARC_SPINNER, Constants.ROUNDED_BORDER_ARC_SPINNER);
    graphics2d.dispose();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected Component createPreviousButton() {
    Component previousButton = new CustomArrowButton(SwingConstants.SOUTH);

    previousButton.setName("Spinner.previousButton");

    installPreviousButtonListeners(previousButton);

    return previousButton;
  }

  @Override
  protected Component createNextButton() {
    Component nextButton = new CustomArrowButton(SwingConstants.NORTH);

    nextButton.setName("Spinner.nextButton");

    installNextButtonListeners(nextButton);

    return nextButton;
  }
}