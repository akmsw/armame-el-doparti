package armameeldoparti;

import static org.junit.jupiter.api.Assertions.assertEquals;

import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import java.util.stream.Stream;
import lombok.NonNull;
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
  void defaultValuesInPlayersCreation(@NonNull String name,
                                      @NonNull Position position) {
    Player player = new Player(name, position);

    assertEquals(0, player.getAnchorageNumber());
    assertEquals(0, player.getSkillPoints());
    assertEquals(0, player.getTeamNumber());
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
  @NonNull
  public Stream<? extends Arguments> provideArguments(@NonNull ExtensionContext context) {
    return Stream.of(
      Arguments.of("player a", Position.CENTRAL_DEFENDER),
      Arguments.of("player b", Position.LATERAL_DEFENDER),
      Arguments.of("player c", Position.MIDFIELDER),
      Arguments.of("player d", Position.FORWARD),
      Arguments.of("player e", Position.GOALKEEPER)
    );
  }
}