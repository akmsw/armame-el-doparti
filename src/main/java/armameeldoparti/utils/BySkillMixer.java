package armameeldoparti.utils;

import armameeldoparti.interfaces.PlayersMixer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Clase correspondiente a los algoritmos de distribución de jugadores en base a sus puntuaciones.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 12/07/2022
 */
public class BySkillMixer implements PlayersMixer {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye el objeto repartidor de jugadores.
   */
  public BySkillMixer() {
      // No necesita cuerpo
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  /**
   * Distribuye los jugadores en base a sus puntuaciones de la manera más equitativa posible
   * considerando los anclajes establecidos.
   *
   * @param teams Lista contenedora de equipos.
   *
   * @return Los equipos con los jugadores distribuidos de la manera deseada.
   */
  @Override
  public List<Team> withAnchorages(List<Team> teams) {
    return teams;
  }

  /**
   * Distribuye los jugadores en base a sus puntuaciones de la manera más equitativa posible
   * sin considerar anclajes.
   *
   * <p>Se recorren las posiciones en orden inverso para lograr la mejor distribución.
   *
   * <p>Se ordenan los jugadores de cada posición en base a su puntuación, de mayor a menor.
   * Luego se ordenan los equipos en base a la suma de los puntuaciones de sus jugadores
   * hasta el momento, de menor a mayor.
   *
   * <p>Si la cantidad de jugadores a repartir es 2, al equipo con menor puntuación se le
   * asigna el jugador de mayor puntuación. Al equipo con mayor puntuación se le asigna
   * el jugador de menor puntuación.
   *
   * <p>Si la cantidad de jugadores a repartir es 4, se arman dos subconjuntos con
   * los jugadores de los extremos, desde afuera hacia adentro, alternando entre
   * subconjuntos. Luego se ordenan estos subconjuntos en base a sus puntuaciones,
   * de mayor a menor.
   *
   * <p>Al equipo con menor puntuación se le asigna el conjunto de jugadores que sumen
   * mayor puntuación. Al equipo con mayor puntuación se le asigna el conjunto de jugadores
   * que sumen menor puntuación.
   *
   * @param teams Lista contenedora de equipos.
   *
   * @return Los equipos con los jugadores distribuidos de la manera deseada.
   */
  @Override
  public List<Team> withoutAnchorages(List<Team> teams) {
    List<Position> reversedEnum = Arrays.asList(Position.values());

    Collections.reverse(reversedEnum);

    for (Position position : reversedEnum) {
      List<Player> currentSet = Main.getPlayersSets()
                                    .get(position);

      currentSet.sort(Comparator.comparingInt(Player::getSkill)
                                .reversed());

      teams.sort(Comparator.comparingInt(Team::getTeamSkill));

      if (currentSet.size() == 2) {
        teams.get(0)
             .getPlayers()
             .get(position)
             .add(currentSet.get(0));

        teams.get(1)
             .getPlayers()
             .get(position)
             .add(currentSet.get(1));
      } else {
        List<Player> playersSubset1 = new ArrayList<>();
        List<Player> playersSubset2 = new ArrayList<>();

        /*
         * Si la cantidad de jugadores a repartir no es 4, esto debería
         * hacerse con un bucle que altere entre subconjuntos mediante
         * una operación de la forma:
         *
         * (i % 2 == 0 ? playersSubset1 : playersSubset2).add(...)
         */
        playersSubset1.add(currentSet.get(0));
        playersSubset1.add(currentSet.get(currentSet.size() - 1));

        playersSubset2.add(currentSet.get(1));
        playersSubset2.add(currentSet.get(currentSet.size() - 2));

        List<List<Player>> playersSubsets = new ArrayList<>();

        playersSubsets.add(playersSubset1);
        playersSubsets.add(playersSubset2);
        playersSubsets.sort(Comparator.comparingInt(ps -> ps.stream()
                                                            .mapToInt(Player::getSkill)
                                                            .reduce(0, Math::addExact)));

        teams.get(0)
             .getPlayers()
             .get(position)
             .addAll(playersSubsets.get(1));

        teams.get(1)
             .getPlayers()
             .get(position)
             .addAll(playersSubsets.get(0));
      }
    }

    return teams;
  }
}