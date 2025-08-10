package armameeldopartidesktop.utils.common.custom.graphical;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import armameeldopartidesktop.utils.common.Constants;

/**
 * A custom scroll pane that fits the overall program aesthetics.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class CustomScrollPane extends JScrollPane {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic scroll pane using the established program aesthetics.
   *
   * @param textArea Text area associated to the scroll pane that will be controlled.
   */
  public CustomScrollPane(JTextArea textArea) {
    super(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    setUpGraphicalProperties();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void paintBorder(Graphics graphics) {
    Graphics2D graphics2d = (Graphics2D) graphics.create();

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.setColor(Constants.COLOR_GREEN_LIGHT_WHITE);
    graphics2d.fillRoundRect(0, 0, (getWidth() - 1), (getHeight() - 1), Constants.ROUNDED_BORDER_ARC_GENERAL, Constants.ROUNDED_BORDER_ARC_GENERAL);
    graphics2d.dispose();
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Configures the graphical properties of the scroll pane in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setOpaque(false);
    setBorder(new EmptyBorder(Constants.INSETS_GENERAL));
    getViewport().setBackground(Constants.COLOR_GREEN_LIGHT_WHITE);
    getVerticalScrollBar().setOpaque(false);
  }
}