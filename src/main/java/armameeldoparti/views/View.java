package armameeldoparti.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

/**
 * Abstract class that specifies the basic views methods and fields.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 28/07/2022
 */
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public abstract class View extends JFrame {

  // ------------------------------------------ Private fields ----------------------------------

  private JPanel masterPanel;

  // ---------------------------------------- Protected fields ----------------------------------

  protected String frameTitle;

  // ---------------------------------------- Protected constructor -----------------------------

  /**
   * Basic view constructor.
   *
   * @param frameTitle             The frame title.
   * @param masterPanelConstraints The view's master panel layout constraints.
   */
  protected View(@NonNull String frameTitle,
                 @NonNull String masterPanelConstraints) {
    setMasterPanel(new JPanel(new MigLayout(masterPanelConstraints)));
    setFrameTitle(frameTitle);
  }

  // ---------------------------------------- Abstract protected methods ------------------------

  /**
   * Initializes the view and makes it visible.
   */
  protected abstract void initializeInterface();

  /**
   * Adds the buttons to their corresponding panel.
   */
  protected abstract void addButtons();
}