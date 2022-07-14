package armameeldoparti.utils;

import armameeldoparti.interfaces.PlayersMixer;
import java.util.ArrayList;
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
public class ByScoresMixer implements PlayersMixer {

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye el objeto repartidor de jugadores.
   */
  public ByScoresMixer() {
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
   * @param teams Lista contenedora de equipos.
   *
   * @return Los equipos con los jugadores distribuidos de la manera deseada.
   */
  @Override
  public List<Team> withoutAnchorages(List<Team> teams) {
    for (Position position : Position.values()) {
      List<Player> currentSet = Main.getPlayersSets()
                                    .get(position);

      // Se ordenan los jugadores de esta posición en base a su puntuación, de mayor a menor
      currentSet.sort(Comparator.comparingInt(Player::getScore)
                                .reversed());

      /*
        * Se ordenan los equipos en base a la suma de los puntuaciones
        * de sus jugadores hasta el momento, de menor a mayor.
        */
      teams.sort(Comparator.comparingInt(t -> t.getPlayers()
                                               .values()
                                               .stream()
                                               .flatMap(List::stream)
                                               .mapToInt(Player::getScore)
                                               .reduce(0, Math::addExact)));

      if (currentSet.size() == 2) {
        // Al equipo con menor puntuación se le asigna el jugador de mayor puntuación
        teams.get(0)
             .getPlayers()
             .get(position)
             .add(currentSet.get(0));

        // Al equipo con mayor puntuación se le asigna el jugador de menor puntuación
        teams.get(1)
             .getPlayers()
             .get(position)
             .add(currentSet.get(1));
      } else {
        List<Player> playersSubset1 = new ArrayList<>();
        List<Player> playersSubset2 = new ArrayList<>();

        /*
         * Agregamos los jugadores de los extremos, desde afuera
         * hacia adentro, alternando entre subconjuntos.
         *
         * ¡¡¡IMPORTANTE!!!
         *
         * Si la cantidad de jugadores a repartir no es 4, este reparto debería
         * automatizarse con un bucle 'for' que altere entre subconjuntos mediante
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

        // Ordenamos los subconjuntos en base a sus puntuaciones, de mayor a menor
        playersSubsets.sort(Comparator.comparingInt(ps -> ps.stream()
                                                            .mapToInt(Player::getScore)
                                                            .reduce(0, Math::addExact)));

        /*
         * Al equipo con menor puntuación se le asigna el conjunto de jugadores
         * que sumen mayor puntuación
         */
        teams.get(0)
             .getPlayers()
             .get(position)
             .addAll(playersSubsets.get(1));

        /*
         * Al equipo con mayor puntuación se le asigna el conjunto de jugadores
         * que sumen menor puntuación
         */
        teams.get(1)
             .getPlayers()
             .get(position)
             .addAll(playersSubsets.get(0));
      }
    }

    return teams;
  }
}