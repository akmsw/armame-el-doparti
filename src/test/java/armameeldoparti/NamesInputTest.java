package armameeldoparti;

import static org.junit.jupiter.api.Assertions.assertThrows;

import armameeldoparti.controllers.NamesInputController;
import armameeldoparti.models.Player;
import armameeldoparti.models.Positions;
import armameeldoparti.models.Views;
import java.util.List;
import javax.naming.InvalidNameException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Names input view-controller unit tests class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 05/08/2022
 */
@TestInstance(Lifecycle.PER_CLASS)
class NamesInputTest {

  // ---------------------------------------- Tests setup ---------------------------------------

  /**
   * Setup made before running any test.
   */
  @BeforeAll
  void setUp() {
    Main.main(null);
  }

  /**
   * Cleanup made before running each test.
   */
  @BeforeEach
  void cleanUp() {
    Main.getPlayersSets()
        .values()
        .forEach(List::clear);
  }

  // ---------------------------------------- Tests bodies --------------------------------------

  /**
   * Tests if names with numbers and/or symbols are correctly detected
   * and they all trigger their corresponding exception.
   *
   * @param invalidName The string that shouldn't pass the name check.
   */
  @DisplayName("Tests if invalid strings throw the expected exception")
  @ParameterizedTest
  @ValueSource(strings = { "", "123", "!a._,|°}zY", "nam3" })
  void invalidStringsShouldThrowException(String invalidString) {
    List<Player> playersSet = Main.getPlayersSets()
                                  .get(Positions.CENTRAL_DEFENDER);

    NamesInputController testNamesInputController = (
        (NamesInputController) Main.getController(Views.NAMES_INPUT)
    );

    assertThrows(IllegalArgumentException.class, () -> {
      testNamesInputController.textFieldEvent(0, playersSet, invalidString);
    });
  }

  /**
   * Tests if empty names, blank names and long names (more than 10 characters),
   * are correctly detected and they all trigger their corresponding exception.
   *
   * @param invalidName The string that shouldn't pass the name check.
   */
  @DisplayName("Tests if invalid names throw the expected exception")
  @ParameterizedTest
  @ValueSource(strings = { "   ", "thisNameIsTooLong" })
  void invalidNamesShouldThrowException(String invalidName) {
    List<Player> playersSet = Main.getPlayersSets()
                                  .get(Positions.CENTRAL_DEFENDER);

    NamesInputController testNamesInputController = (
        (NamesInputController) Main.getController(Views.NAMES_INPUT)
    );

    assertThrows(InvalidNameException.class, () -> {
      testNamesInputController.textFieldEvent(0, playersSet, invalidName);
    });
  }
}