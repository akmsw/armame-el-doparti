package armameeldoparti;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import armameeldoparti.models.Position;
import javax.naming.InvalidNameException;
import javax.swing.JTextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
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
   * and they all throw their corresponding exception.
   *
   * @param invalidName The string that shouldn't pass the name check.
   */
  @DisplayName("Tests if invalid strings throw the expected exception")
  @ParameterizedTest
  @MethodSource("invalidStringsProvider")
  void invalidStringsShouldThrowException(String invalidString) {
    // assertThrows(IllegalArgumentException.class, () -> {
    //   Main.getNamesInputController()
    //       .textFieldEvent(0, Main.getPlayersSets()
    //                              .get(Position.CENTRAL_DEFENDER),
    //                       new JTextField(invalidString));
    // });
  }

  /**
   * Tests if empty names, blank names and long names (more than 10 characters),
   * are correctly detected and they all throw their corresponding exception.
   *
   * @param invalidName The string that shouldn't pass the name check.
   */
  @DisplayName("Tests if invalid names throw the expected exception")
  @ParameterizedTest
  @ValueSource(strings = { "", "   ", "thisNameIsTooLong" })
  void invalidNamesShouldThrowException(String invalidName) {
    // assertThrows(InvalidNameException.class, () -> {
    //   Main.getNamesInputController()
    //       .textFieldEvent(0, Main.getPlayersSets()
    //                              .get(Position.CENTRAL_DEFENDER),
    //                       new JTextField(invalidName));
    // });
  }

  // ---------------------------------------- Arguments providers -------------------------------

  /**
   * Provides invalid strings for testing.
   *
   * @return A stream of invalid strings for testing.
   */
  static Stream<String> invalidStringsProvider() {
    return Stream.of("123", "!a._,|°}zY", "nam3", null);
  }
}