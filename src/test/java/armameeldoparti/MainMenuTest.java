package armameeldoparti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import armameeldoparti.controllers.HelpController;
import armameeldoparti.controllers.MainMenuController;
import armameeldoparti.controllers.NamesInputController;
import armameeldoparti.models.Views;
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
    ((MainMenuController) Main.getController(Views.MAIN_MENU)).helpButtonEvent();

    assertEquals(0, ((HelpController) Main.getController(Views.HELP)).getPageNumber());

    assertTrue(Main.getController(Views.HELP)
                   .getView()
                   .isVisible());

    assertTrue(((HelpView) Main.getController(Views.HELP)
                               .getView()).getBackButton()
                                          .isEnabled());

    ((HelpController) Main.getController(Views.HELP)).backButtonEvent();

    assertTrue(Main.getController(Views.MAIN_MENU)
                   .getView()
                   .isVisible());

    assertFalse(Main.getController(Views.HELP)
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
    ((MainMenuController) Main.getController(Views.MAIN_MENU))
        .startButtonEvent();

    assertTrue(Main.getController(Views.NAMES_INPUT)
                   .getView()
                   .isVisible());

    assertFalse(Main.getController(Views.MAIN_MENU)
                    .getView()
                    .isVisible());

    assertEquals(0, ((NamesInputView) Main.getController(Views.NAMES_INPUT)
                                          .getView()).getComboBox()
                                                     .getSelectedIndex());

    assertEquals("", ((NamesInputView) Main.getController(Views.NAMES_INPUT)
                                           .getView()).getTextArea()
                                                      .getText());

    assertFalse(((NamesInputView) Main.getController(Views.NAMES_INPUT)
                                      .getView()).getAnchoragesCheckBox()
                                                 .isSelected());


    assertFalse(((NamesInputView) Main.getController(Views.NAMES_INPUT)
                                      .getView()).getMixButton()
                                                 .isEnabled());

    ((NamesInputView) Main.getController(Views.NAMES_INPUT)
                          .getView()).getTextFieldsMap()
                                     .values()
                                     .forEach(tfl -> {
                                       tfl.forEach(tf -> assertEquals("", tf.getText()));
                                     });

    ((NamesInputController) Main.getController(Views.NAMES_INPUT)).backButtonEvent();

    assertTrue(Main.getController(Views.MAIN_MENU)
                   .getView()
                   .isVisible());

    assertFalse(Main.getController(Views.NAMES_INPUT)
                    .getView()
                    .isVisible());
  }
}