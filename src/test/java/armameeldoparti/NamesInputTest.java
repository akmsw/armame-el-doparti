package armameeldoparti;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import armameeldoparti.controllers.NamesInputController;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import java.util.List;
import java.util.stream.Stream;
import javax.naming.InvalidNameException;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
   * Tests whether empty names and names with numbers and/or symbols are correctly detected and if
   * they all trigger their corresponding exception.
   *
   * @param invalidString The string that shouldn't pass the name check.
   */
  @DisplayName("Tests if invalid strings throw the expected exception")
  @ParameterizedTest
  @ValueSource(strings = { "", "123", "!a._,|°}zY", "nam3" })
  void invalidStringsShouldThrowException(@NonNull String invalidString) {
    List<Player> playersSet = CommonFields.getPlayersSets()
                                          .get(Position.CENTRAL_DEFENDER);

    NamesInputController testNamesInputController = (
        (NamesInputController) CommonFunctions.getController(ProgramView.NAMES_INPUT)
    );

    assertThrows(IllegalArgumentException.class,
                 () -> testNamesInputController.textFieldEvent(0, playersSet, invalidString));
  }

  /**
   * Tests whether empty names, blank names and long names (more than 10 characters), are correctly
   * detected and if they all trigger their corresponding exception.
   *
   * @param invalidName The string that shouldn't pass the name check.
   */
  @DisplayName("Tests if invalid names throw the expected exception")
  @ParameterizedTest
  @ValueSource(strings = { "   ", "thisNameIsTooLong" })
  void invalidNamesShouldThrowException(@NonNull String invalidName) {
    List<Player> playersSet = CommonFields.getPlayersSets()
                                          .get(Position.CENTRAL_DEFENDER);

    NamesInputController testNamesInputController = (
        (NamesInputController) CommonFunctions.getController(ProgramView.NAMES_INPUT)
    );

    assertThrows(InvalidNameException.class,
                 () -> testNamesInputController.textFieldEvent(0, playersSet, invalidName));
  }

  /**
   * Tests whether repeated names are correctly detected and if they all trigger their corresponding
   * exception.
   */
  @DisplayName("Tests if repeated names throw the expected exception")
  @Test
  void repeatedNamesShouldThrowException() {
    List<Player> playersSet = CommonFields.getPlayersSets()
                                          .get(Position.CENTRAL_DEFENDER);

    playersSet.forEach(p -> {
      p.setName("");
      p.setPosition(null);
      p.setAnchored(false);
      p.setTeamNumber(0);
      p.setAnchorageNumber(0);
      p.setSkillPoints(0);
    });

    NamesInputController testNamesInputController = (
        (NamesInputController) CommonFunctions.getController(ProgramView.NAMES_INPUT)
    );

    String repeatedPlayerName = "PLAYER A";

    assertDoesNotThrow(
        () -> testNamesInputController.textFieldEvent(0, playersSet, repeatedPlayerName)
    );
    assertThrows(InvalidNameException.class,
                 () -> testNamesInputController.textFieldEvent(1, playersSet, repeatedPlayerName));
  }

  /**
   * Tests whether valid names are correctly stored as players names.
   *
   * @param playerIndex    The index of the player to test in its corresponding list.
   * @param playerPosition The position of the player to test.
   * @param playerName     The name to apply to the player.
   *
   * @throws InvalidNameException     This exception is never thrown since every test string is a
   *                                  valid name.
   * @throws IllegalArgumentException This exception is never thrown since every test string is a
   *                                  valid name.
   */
  @DisplayName("Tests whether valid names are correctly stored as players names")
  @ParameterizedTest
  @MethodSource("validParamsProvider")
  void validNamesShouldPass(int playerIndex,
                            @NonNull Position playerPosition,
                            @NonNull String playerName)
                            throws IllegalArgumentException,
                                   InvalidNameException {
    List<Player> playersSet = CommonFields.getPlayersSets()
                                          .get(playerPosition);

    NamesInputController testNamesInputController = (
        (NamesInputController) CommonFunctions.getController(ProgramView.NAMES_INPUT)
    );

    testNamesInputController.textFieldEvent(playerIndex, playersSet, playerName);

    assertEquals(playerName.replace(" ", "_"),
                 CommonFields.getPlayersSets()
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
  @NonNull
  static Stream<Arguments> validParamsProvider() {
    return Stream.of(
      Arguments.of(0, Position.CENTRAL_DEFENDER, "PLAYER A"),
      Arguments.of(1, Position.CENTRAL_DEFENDER, "PLAYER B"),
      Arguments.of(0, Position.LATERAL_DEFENDER, "PLAYER C"),
      Arguments.of(1, Position.LATERAL_DEFENDER, "PLAYER D"),
      Arguments.of(2, Position.LATERAL_DEFENDER, "PLAYER E"),
      Arguments.of(3, Position.LATERAL_DEFENDER, "PLAYER F"),
      Arguments.of(0, Position.MIDFIELDER, "PLAYER G"),
      Arguments.of(1, Position.MIDFIELDER, "PLAYER H"),
      Arguments.of(2, Position.MIDFIELDER, "PLAYER I"),
      Arguments.of(3, Position.MIDFIELDER, "PLAYER J"),
      Arguments.of(0, Position.FORWARD, "PLAYER K"),
      Arguments.of(1, Position.FORWARD, "PLAYER L"),
      Arguments.of(0, Position.GOALKEEPER, "PLAYER M"),
      Arguments.of(1, Position.GOALKEEPER, "PLAYER N")
    );
  }
}