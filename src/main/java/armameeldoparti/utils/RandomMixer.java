package armameeldoparti.utils;

import armameeldoparti.interfaces.PlayersMixer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Clase correspondiente a los algoritmos de distribución aleatoria de jugadores.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 12/07/2022
 */
public class RandomMixer implements PlayersMixer {

  // ---------------------------------------- Campos privados -----------------------------------

  private int index;
  private int chosenTeam1;
  private int chosenTeam2;

  private Random randomGenerator;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye el objeto repartidor de jugadores.
   */
  public RandomMixer() {
    randomGenerator = new Random();
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  /**
   * Distribuye los jugadores de manera completamente aleatoria.
   *
   * <p>Se recorre la mitad de los jugadores del conjunto de manera aleatoria
   * y se les asigna a los jugadores escogidos como equipo el número aleatorio
   * generado al principio.
   *
   * <p>A medida que se van eligiendo jugadores, su índice en el arreglo se
   * almacena para evitar reasignarle un equipo.
   *
   * <p>Al resto de jugadores que quedaron sin elegir de manera aleatoria
   * (aquellos con team == 0) del mismo grupo, se les asigna el número de equipo
   * opuesto.
   *
   * @param teams Lista contenedora de equipos.
   *
   * @return Los equipos con los jugadores distribuidos de la manera deseada.
   */
  @Override
  public List<Team> withoutAnchorages(List<Team> teams) {
    chosenTeam1 = randomGenerator.nextInt(teams.size());
    chosenTeam2 = 1 - chosenTeam1;

    List<Integer> alreadySetted = new ArrayList<>();

    for (Position position : Position.values()) {
      List<Player> playersSet = Main.getPlayersSets()
                                    .get(position);

      for (int i = 0; i < playersSet.size() / 2; i++) {
        do {
          index = randomGenerator.nextInt(playersSet.size());
        } while (alreadySetted.contains(index));

        alreadySetted.add(index);

        Player chosenPlayer = playersSet.get(index);

        chosenPlayer.setTeam(chosenTeam1 + 1);

        teams.get(chosenTeam1)
             .getPlayers()
             .get(chosenPlayer.getPosition())
             .add(chosenPlayer);
      }

      playersSet.stream()
                .filter(p -> p.getTeam() == 0)
                .forEach(p -> {
                  p.setTeam(chosenTeam2 + 1);

                  teams.get(chosenTeam2)
                       .getPlayers()
                       .get(p.getPosition())
                       .add(p);
                });

      alreadySetted.clear();
    }

    return teams;
  }

  /**
   * Distribuye los jugadores de manera aleatoria considerando los anclajes establecidos.
   *
   * <p>Se elige un número aleatorio entre 0 y 1 para asignarle como equipo a un conjunto
   * de jugadores, y el resto tendrá asignado el equipo opuesto.
   *
   * <p>Se comienza recorriendo cada posición. Mientras la posición en
   * la que se está trabajando no tenga la cantidad de jugadores especificada para dicha
   * posición por equipo, se seguirá iterando.
   *
   * <p>Se escoge un jugador de manera aleatoria del conjunto total de jugadores con la
   * posición seleccionada, y se chequea si está disponible (equipo = 0) y si tiene anclajes
   * (anclaje != 0).
   *
   * <p>Si el jugador está disponible y está anclado con otros jugadores, se toman todos
   * los jugadores de todas las posiciones con su mismo número de anclaje y se valida si se
   * pueden agregar al equipo sin sobrepasar la cantidad de jugadores permitida por cada
   * posición. En caso de que sí se pueda, se los agrega. Si no, se los ignora y se
   * continúa iterando.
   *
   * <p>Si el jugador no tiene anclaje y se lo puede agregar sin sobrepasar el límite de
   * jugadores para su posición, se lo agrega.
   *
   * <p>Cuando el primer equipo elegido está lleno, se deja de iterar. Se toman todos los
   * jugadores restantes y se les asigna el número de equipo contrario al elegido en un
   * principio.
   *
   * @param teams Lista contenedora de equipos.
   *
   * @return Los equipos con los jugadores distribuidos de la manera deseada.
   */
  @Override
  public List<Team> withAnchorages(List<Team> teams) {
    chosenTeam1 = randomGenerator.nextInt(teams.size());
    chosenTeam2 = 1 - chosenTeam1;

    Team currentWorkingTeam = teams.get(chosenTeam1);

    List<Integer> indexesSet = new ArrayList<>();

    boolean teamFull = false;

    for (int i = 0; i < Position.values()
                                .length && !teamFull; i++) {
      List<Player> playersSet = Main.getPlayersSets()
                                    .get(Position.values()[i]);

      while (currentWorkingTeam.getPlayers()
                               .get(Position.values()[i])
                               .size() < Main.getPlayersAmountMap()
                                             .get(Position.values()[i])
             && !teamFull) {
        updateIndex(playersSet.size(), indexesSet);

        Player player = playersSet.get(index);

        if (player.getTeam() != 0) {
          continue;
        }

        if (player.getAnchor() == 0
            && currentWorkingTeam.getPlayersCount() + 1 <= Main.PLAYERS_PER_TEAM) {
          player.setTeam(chosenTeam1 + 1);
          currentWorkingTeam.getPlayers()
                            .get(player.getPosition())
                            .add(player);
          indexesSet.add(index);
          /*
           * Acá se debería actualizar teamFull y hacer un continue,
           * pero se entra en conflicto con S135.
           */
        }

        List<Player> anchoredPlayers = Main.getPlayersSets()
                                           .values()
                                           .stream()
                                           .flatMap(List::stream)
                                           .filter(p -> p.getAnchor() == player.getAnchor())
                                           .collect(Collectors.toList());

        if (validateAnchorage(currentWorkingTeam, anchoredPlayers)) {
          anchoredPlayers.forEach(p -> {
            p.setTeam(chosenTeam1 + 1);
            currentWorkingTeam.getPlayers()
                              .get(p.getPosition())
                              .add(p);
          });
          indexesSet.add(index);
        }

        teamFull = currentWorkingTeam.getPlayersCount() == Main.PLAYERS_PER_TEAM;
      }

      indexesSet.clear();
    }

    Main.getPlayersSets()
        .values()
        .stream()
        .flatMap(List::stream)
        .filter(p -> p.getTeam() == 0)
        .forEach(p -> {
          p.setTeam(chosenTeam2 + 1);
          teams.get(chosenTeam2)
               .getPlayers()
               .get(p.getPosition())
               .add(p);
        });

    return teams;
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Actualiza de manera aleatoria el valor del índice de jugador a seleccionar.
   *
   * @param range      Límite superior (exclusive) para el generador aleatorio.
   * @param indexesSet Conjunto donde revisar si el índice generado ya está presente.
   */
  private void updateIndex(int range, List<Integer> indexesSet) {
    do {
      index = randomGenerator.nextInt(range);
    } while (indexesSet.contains(index));
  }

  /**
   * Valida si todos los jugadores anclados pueden ser agregados al equipo.
   *
   * <p>Se recorren los conjuntos de jugadores con las posiciones de todos los anclados, y
   * se evalúa si al agregarlos no se supera el número de jugadores permitidos por posición
   * por equipo.
   *
   * <p>Esto se hace con el fin de evitar que en un equipo queden más de la mitad de jugadores
   * de una posición.
   *
   * @param team            Equipo donde se desea registrar los jugadores anclados.
   * @param anchoredPlayers Lista de jugadores con el mismo anclaje.
   *
   * @return Si se pueden agregar al equipo los jugadores anclados especificados.
   */
  private boolean validateAnchorage(Team team, List<Player> anchoredPlayers) {
    if (team.getPlayersCount() + anchoredPlayers.size() > Main.PLAYERS_PER_TEAM) {
      return false;
    }

    if (anchoredPlayers.stream()
                       .anyMatch(p -> p.getTeam() != 0 || team.isPositionFull(p.getPosition()))) {
      return false;
    }

    for (Player player : anchoredPlayers) {
      if (team.getPlayers()
              .get(player.getPosition())
              .size()
          + anchoredPlayers.stream()
                           .filter(p -> p.getPosition() == player.getPosition())
                           .count() > Main.getPlayersAmountMap()
                                          .get(player.getPosition())) {
        return false;
      }
    }

    return true;
  }
}