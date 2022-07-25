package armameeldoparti;

import static org.junit.jupiter.api.Assertions.assertEquals;

import armameeldoparti.utils.Player;
import armameeldoparti.utils.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * Clase de pruebas unitarias para jugadores.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 1.0.0
 *
 * @since 23/07/2022
 */
class PlayersTests implements ArgumentsProvider {

  /**
   * Pruebas unitarias simples para los métodos de un jugador.
   */
  @ParameterizedTest
  @ArgumentsSource(PlayersTests.class)
  void creation(String name, Position position) {
    Player player = new Player(name, position);

    assertEquals(0, player.getAnchor());
    assertEquals(0, player.getSkill());
    assertEquals(0, player.getTeam());
    assertEquals(name, player.getName());
    assertEquals(position, player.getPosition());
  }

  /**
   * Genera valores para las pruebas unitarias.
   *
   * @return Arreglo con parámetros para las pruebas unitarias.
   */
  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    return Stream.of(
      Arguments.of("jugador a", Position.CENTRAL_DEFENDER),
      Arguments.of("jugador b", Position.LATERAL_DEFENDER),
      Arguments.of("jugador c", Position.MIDFIELDER),
      Arguments.of("jugador d", Position.FORWARD),
      Arguments.of("jugador e", Position.GOALKEEPER)
    );
  }
}