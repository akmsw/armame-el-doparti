package armameeldoparti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import armameeldoparti.views.HelpView;
import armameeldoparti.views.NamesInputView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * Main menu view-controller unit tests class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 04/08/2022
 */
@TestInstance(Lifecycle.PER_CLASS)
class MainMenuTest {

  // ---------------------------------------- Tests setup ---------------------------------------

  /**
   * Setup made before running any test.
   */
  @BeforeAll
  void setUp() {
    Main.main(null);
  }

  // ---------------------------------------- Tests bodies --------------------------------------

  /**
   * The help view should start visible on the first page.
   *
   * <p>Also tests the correct navigation between the main menu and the help view
   */
  @DisplayName("Tests the initial state of the help view and the navigation")
  @Test
  void helpViewInitialState() {
    Main.getMainMenuController()
        .helpButtonEvent();

    assertEquals(0, Main.getHelpController()
                        .getPageNumber());

    assertTrue(Main.getHelpController()
                   .getView()
                   .isVisible());

    assertTrue(((HelpView) Main.getHelpController()
                               .getView()).getBackButton()
                                          .isEnabled());

    Main.getHelpController()
        .backButtonEvent();

    assertTrue(Main.getMainMenuController()
                   .getView()
                   .isVisible());

    assertFalse(Main.getHelpController()
                    .getView()
                    .isVisible());
  }

  /**
   * The names input view should start visible, with the first combo box options selected,
   * the text area empty, the anchorages checkbox not selected, the mix button disabled,
   * and the text fields empty.
   *
   * <p>Also tests the correct navigation between the main menu and the names input view.
   */
  @DisplayName("Tests the initial state of the names input view and the navigation")
  @Test
  void namesInputViewInitialState() {
    Main.getMainMenuController()
        .startButtonEvent();

    assertTrue(Main.getNamesInputController()
                   .getView()
                   .isVisible());

    assertFalse(Main.getMainMenuController()
                    .getView()
                    .isVisible());

    assertEquals(0, ((NamesInputView) Main.getNamesInputController()
                                          .getView()).getComboBox()
                                                     .getSelectedIndex());

    assertEquals("", ((NamesInputView) Main.getNamesInputController()
                                           .getView()).getTextArea()
                                                      .getText());

    assertFalse(((NamesInputView) Main.getNamesInputController()
                                      .getView()).getAnchoragesCheckBox()
                                                 .isSelected());


    assertFalse(((NamesInputView) Main.getNamesInputController()
                                      .getView()).getMixButton()
                                                 .isEnabled());

    ((NamesInputView) Main.getNamesInputController()
                          .getView()).getTextFieldsMap()
                                     .values()
                                     .forEach(tfl -> {
                                       tfl.forEach(tf -> assertEquals("", tf.getText()));
                                     });

    Main.getNamesInputController()
        .backButtonEvent();

    assertTrue(Main.getMainMenuController()
                   .getView()
                   .isVisible());

    assertFalse(Main.getNamesInputController()
                    .getView()
                    .isVisible());
  }
}