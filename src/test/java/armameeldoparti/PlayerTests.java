package armameeldoparti;

import static org.junit.jupiter.api.Assertions.assertEquals;

import armameeldoparti.models.Player;
import armameeldoparti.models.Positions;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * Players unit tests class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 23/07/2022
 */
@TestInstance(Lifecycle.PER_CLASS)
class PlayerTests implements ArgumentsProvider {

  // ---------------------------------------- Tests bodies --------------------------------------

  /**
   * Tests the default values when creating a player.
   */
  @DisplayName("Tests the default values when creating a player")
  @ParameterizedTest
  @ArgumentsSource(PlayerTests.class)
  void defaultValuesInPlayersCreation(String name, Positions position) {
    Player player = new Player(name, position);

    assertEquals(0, player.getAnchorageNumber());
    assertEquals(0, player.getSkillPoints());
    assertEquals(0, player.getTeam());
    assertEquals(name, player.getName());
    assertEquals(position, player.getPosition());
  }

  // ---------------------------------------- Arguments providers -------------------------------

  /**
   * Generates values for the tests.
   *
   * @return An array with parameters to be used in the unit tests.
   */
  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    return Stream.of(
      Arguments.of("player a", Positions.CENTRAL_DEFENDER),
      Arguments.of("player b", Positions.LATERAL_DEFENDER),
      Arguments.of("player c", Positions.MIDFIELDER),
      Arguments.of("player d", Positions.FORWARD),
      Arguments.of("player e", Positions.GOALKEEPER)
    );
  }
}