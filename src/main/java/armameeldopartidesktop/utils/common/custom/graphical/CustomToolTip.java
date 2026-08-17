package armameeldopartidesktop.utils.common.custom.graphical;

import java.awt.Insets;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;

import armameeldopartidesktop.utils.common.Constants;

/**
 * A custom tooltip that fits the overall program aesthetics.
 *
 * @since 3.0.0
 *
 * @version 2.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CustomToolTip extends JToolTip implements CustomComponent {

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void setUpGraphicalProperties() {
    // Intentionally left empty.
  }

  @Override
  public void addNotify() {
    super.addNotify();

    Window window = SwingUtilities.windowForComponent(this);

    if ((window != null) && !(window instanceof JFrame)) {
      window.setBackground(Constants.COLOR_TRANSPARENT);
    }
  }

  @Override
  public Insets getInsets() {
    return Constants.INSETS_TOOLTIP;
  }
}