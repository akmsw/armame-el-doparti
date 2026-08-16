package armameeldopartidesktop.utils.common.custom.graphical.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicOptionPaneUI;

import armameeldopartidesktop.utils.common.Constants;
import net.miginfocom.swing.MigLayout;

/**
 * A custom option pane UI that fits the overall program aesthetics.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CustomOptionPaneUI extends BasicOptionPaneUI {

  // ---------- Public static methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new custom option pane UI that fits the overall program aesthetics.
   *
   * @param component Component to which to apply the custom UI.
   *
   * @return A new custom option pane UI.
   */
  public static ComponentUI createUI(JComponent component) {
    component.setBackground(Constants.COLOR_GREEN_LIGHT);

    return new CustomOptionPaneUI();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void installUI(JComponent component) {
    super.installUI(component);

    SwingUtilities.invokeLater(
      () -> {
        if (SwingUtilities.getWindowAncestor(optionPane) instanceof JDialog parentDialog) {
          parentDialog.setIconImage(Constants.ICON_MAIN.getImage());
        }
      }
    );
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a button for each string in {@code buttons} and adds them to {@code container}. If {@code buttons} does not contain any string-instance object, an array is retrieved using the message type for the
   * dialog. This array contains the strings that will be used in the buttons to be placed in the dialog.
   *
   * <p>A minimum button size is forced in order to ensure that the buttons showing only anchorages numbers have the same size, since the font used is not monospaced for user readability matters.
   *
   * @param container      A container for the buttons.
   * @param buttonsStrings An array with the strings for each button of the dialog.
   * @param initialIndex   An initial index used for validation, inherited from parent class method signature.
   *
   * @see #getButtonsForMessageType(int)
   */
  @Override
  protected void addButtonComponents(Container container, Object [] buttonsStrings, int initialIndex) {
    if ((buttonsStrings == null) || (buttonsStrings.length <= 0)) {
      return;
    }

    int buttonsCount = buttonsStrings.length;

    if (Arrays.asList(buttonsStrings).stream().noneMatch(String.class::isInstance)) {
      buttonsStrings = getButtonsForMessageType(optionPane.getMessageType());
    }

    JPanel buttonPanel = new JPanel();

    buttonPanel.setLayout(new MigLayout());
    buttonPanel.setOpaque(false);

    for (Object buttonText : buttonsStrings) {
      JButton customButton = new JButton((String) buttonText);

      CustomButtonUI.setArc(customButton, Constants.ROUNDED_BORDER_ARC_BUTTON_DIALOG);

      customButton.setMinimumSize(new Dimension(Constants.SIZE_BUTTON_DIALOG_MIN_WIDTH, Constants.SIZE_BUTTON_DIALOG_MIN_HEIGHT));
      customButton.addActionListener(
        _ -> {
          if (initialIndex >= 0 && initialIndex < buttonsCount) {
            ((JOptionPane) SwingUtilities.getAncestorOfClass(JOptionPane.class, container)).setValue(buttonText);
          }
        }
      );

      buttonPanel.add(customButton);
    }

    container.add(buttonPanel, Constants.MIG_LAYOUT_SOUTH);
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @param messageType The type of the message to be shown in the dialog.
   *
   * @return An array with the strings for each button to be placed in the dialog based on the message type.
   */
  private Object [] getButtonsForMessageType(int messageType) {
    return switch (messageType) {
      case JOptionPane.QUESTION_MESSAGE -> new Object [] { UIManager.getString("OptionPane.yesButtonText"), UIManager.getString("OptionPane.noButtonText") };
      default -> new Object [] { UIManager.getString("OptionPane.okButtonText") };
    };
  }
}