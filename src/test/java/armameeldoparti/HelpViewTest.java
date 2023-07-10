package armameeldoparti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import armameeldoparti.controllers.HelpController;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.views.HelpView;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;
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
 * @since 3.0
 */
@TestInstance(Lifecycle.PER_CLASS)
class HelpViewTest {

  // ---------------------------------------- Private fields ------------------------------------

  private long helpPagesAmount;

  private HelpView helpView;
  private HelpController helpController;

  // ---------------------------------------- Tests setup ---------------------------------------

  /**
   * Setup made before running any test.
   *
   * <p>Instantiates the help view and its controller.
   *
   * @throws URISyntaxException If the specified URI for the help pages directory is not correctly
   *                            formatted.
   */
  @BeforeAll
  void setUp() throws URISyntaxException {
    helpView = new HelpView();
    helpController = new HelpController(helpView);

    helpPagesAmount = Objects.requireNonNull(
                        Paths.get(Objects.requireNonNull(
                            getClass().getClassLoader()
                                      .getResource(Constants.PATH_HELP_DOCS),
                            Constants.MSG_ERROR_NULL_RESOURCE
                          ).toURI()
                        ).toFile()
                         .list(),
                        Constants.MSG_ERROR_NULL_RESOURCE
                      ).length;
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
   * Tests whether the page number shown on the label is correct after navigating between pages.
   *
   * @param timesNext     How many times the next page button is pressed.
   * @param timesPrevious How many times the previous page button is pressed.
   */
  @DisplayName("The page number should change when navigating")
  @ParameterizedTest
  @MethodSource("navigationProvider")
  void currentPageNumberShouldChangeOnNavigation(int timesNext, int timesPrevious) {
    for (int i = 0; i < timesNext; i++) {
      helpController.nextPageButtonEvent();
    }

    for (int i = 0; i < timesPrevious; i++) {
      helpController.previousPageButtonEvent();
    }

    int expectedCurrentPageNumber = timesNext - timesPrevious;

    String expectedLabel = expectedCurrentPageNumber + 1 + "/" + helpPagesAmount;

    assertEquals(expectedCurrentPageNumber, helpController.getCurrentPageNumber());
    assertEquals(expectedLabel, helpView.getPagesCounter()
                                        .getText());
  }

  /**
   * Tests the limits for the navigation buttons.
   */
  @DisplayName("Tests the limits for the navigation buttons")
  @Test
  void navigationButtonsLimitsTest() {
    assertFalse(helpView.getPreviousPageButton()
                        .isEnabled());
    assertTrue(helpView.getNextPageButton()
                       .isEnabled());

    for (int i = 0; i < helpPagesAmount - 1; i++) {
      helpController.nextPageButtonEvent();
    }

    assertFalse(helpView.getNextPageButton()
                        .isEnabled());
    assertTrue(helpView.getPreviousPageButton()
                       .isEnabled());
  }

  // ---------------------------------------- Arguments providers -------------------------------

  /**
   * Provides how many times the test should navigate back and forth in the help view pages.
   *
   * @return How many times the test should navigate back and forth in the help view pages.
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