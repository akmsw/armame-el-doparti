package armameeldoparti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import armameeldoparti.controllers.NamesInputController;
import armameeldoparti.models.Player;
import armameeldoparti.models.Positions;
import armameeldoparti.models.Views;
import java.util.List;
import java.util.stream.Stream;
import javax.naming.InvalidNameException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

  /**
   * Tests if valid strings are correctly stored as players names.
   *
   * @param playerIndex    The index of the player to test in its corresponding list.
   * @param playerPosition The position of the player to test.
   * @param playerName     The name to apply to the player.
   *
   * @throws InvalidNameException     This exception is never thrown since
   *                                  every test string is a valid name.
   * @throws IllegalArgumentException This exception is never thrown since
   *                                  every test string is a valid name.
   */
  @DisplayName("Brief test explanation")
  @ParameterizedTest
  @MethodSource("validParamsProvider")
  void validNamesShouldPass(int playerIndex, Positions playerPosition, String playerName)
                            throws IllegalArgumentException, InvalidNameException {
    List<Player> playersSet = Main.getPlayersSets()
                                  .get(playerPosition);

    NamesInputController testNamesInputController = (
        (NamesInputController) Main.getController(Views.NAMES_INPUT)
    );

    testNamesInputController.textFieldEvent(playerIndex, playersSet, playerName);

    assertEquals(playerName.replace(" ", "_"), Main.getPlayersSets()
                                                   .get(playerPosition)
                                                   .get(playerIndex)
                                                   .getName());
  }

  // ---------------------------------------- Arguments providers -------------------------------

  /**
   * Provides valid params for testing.
   *
   * @return Valid params for testing.
   */
  static Stream<Arguments> validParamsProvider() {
    return Stream.of(
      Arguments.of(0, Positions.CENTRAL_DEFENDER, "PLAYER A"),
      Arguments.of(1, Positions.CENTRAL_DEFENDER, "PLAYER B"),
      Arguments.of(0, Positions.LATERAL_DEFENDER, "PLAYER C"),
      Arguments.of(1, Positions.LATERAL_DEFENDER, "PLAYER D"),
      Arguments.of(2, Positions.LATERAL_DEFENDER, "PLAYER E"),
      Arguments.of(3, Positions.LATERAL_DEFENDER, "PLAYER F"),
      Arguments.of(0, Positions.MIDFIELDER, "PLAYER G"),
      Arguments.of(1, Positions.MIDFIELDER, "PLAYER H"),
      Arguments.of(2, Positions.MIDFIELDER, "PLAYER I"),
      Arguments.of(3, Positions.MIDFIELDER, "PLAYER J"),
      Arguments.of(0, Positions.FORWARD, "PLAYER K"),
      Arguments.of(1, Positions.FORWARD, "PLAYER L"),
      Arguments.of(0, Positions.GOALKEEPER, "PLAYER M"),
      Arguments.of(1, Positions.GOALKEEPER, "PLAYER N")
    );
  }
}