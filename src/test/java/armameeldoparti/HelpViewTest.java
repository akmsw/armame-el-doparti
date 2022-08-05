package armameeldoparti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import armameeldoparti.controllers.HelpController;
import armameeldoparti.views.HelpView;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Help view-controller unit tests class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 01/08/2022
 */
@TestInstance(Lifecycle.PER_CLASS)
class HelpViewTest {

  // ---------------------------------------- Private fields ------------------------------------

  private HelpView helpView;
  private HelpController helpController;

  // ---------------------------------------- Tests setup ---------------------------------------

  /**
   * Setup made before running any test.
   *
   * <p>Instantiates the help view and its controller.
   */
  @BeforeAll
  void setUp() {
    helpView = new HelpView();
    helpController = new HelpController(helpView);
  }

  /**
   * Setup made before running each test.
   *
   * <p>Resets the help view to its default values.
   */
  @BeforeEach
  void resetPages() {
    helpController.resetView();
  }

  // ---------------------------------------- Tests bodies --------------------------------------

  /**
   * Tests if the page number shown on the label is correct
   * after navigating between pages.
   *
   * @param timesNext     How many times the next page button is pressed.
   * @param timesPrevious How many times the previous page button is pressed.
   */
  @DisplayName("The page number should change when navigating")
  @ParameterizedTest
  @MethodSource("navigationProvider")
  void pageNumberChangeOnNavigation(int timesNext, int timesPrevious) {
    for (int i = 0; i < timesNext; i++) {
      helpController.nextPageButtonEvent();
    }

    for (int i = 0; i < timesPrevious; i++) {
      helpController.previousPageButtonEvent();
    }

    int expectedPageNumber = timesNext - timesPrevious;

    String expectedLabel = expectedPageNumber + 1 + "/8";

    assertEquals(expectedPageNumber, helpController.getPageNumber());

    assertEquals(expectedLabel, helpView.getPagesCounter()
                                        .getText());
  }

  /**
   * Bla.
   */
  @DisplayName("Tests the limits for the navigation buttons")
  @Test
  void navigationButtonsLimits() {
    assertFalse(helpView.getPreviousPageButton()
                        .isEnabled());

    assertTrue(helpView.getNextPageButton()
                       .isEnabled());

    for (int i = 0; i < helpController.getTotalPagesAmount() - 1; i++) {
      helpController.nextPageButtonEvent();
    }

    assertFalse(helpView.getNextPageButton()
                        .isEnabled());

    assertTrue(helpView.getPreviousPageButton()
                       .isEnabled());
  }

  // ---------------------------------------- Arguments providers -------------------------------

  /**
   * Provides how many times the test should navigate
   * back and forth in the help view pages.
   *
   * @return How many times the test should navigate
   *         back and forth in the help view pages.
   */
  static Stream<Arguments> navigationProvider() {
    return Stream.of(
      Arguments.of(2, 1),
      Arguments.of(5, 3),
      Arguments.of(7, 1),
      Arguments.of(1, 0),
      Arguments.of(0, 0)
    );
  }
}