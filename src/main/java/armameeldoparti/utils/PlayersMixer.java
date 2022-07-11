package armameeldoparti.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Clase correspondiente a los algoritmos de distribución de jugadores.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 04/07/2022
 */
public class PlayersMixer {

    // ---------------------------------------- Campos privados -----------------------------------

    private Random randomGenerator;

    // ---------------------------------------- Constructor ---------------------------------------

    /**
     * Construye el objeto repartidor de jugadores.
     */
    public PlayersMixer() {
        randomGenerator = new Random();
    }

    // ---------------------------------------- Métodos públicos ----------------------------------

    /**
     * Reparte los jugadores de manera aleatoria en dos equipos.
     *
     * @param teams      Lista contenedora de equipos.
     * @param anchorages Si la mezcla debe tener en cuenta anclajes establecidos.
     *
     * @return Los equipos actualizados con los jugadores repartidos.
     */
    public List<Team> randomMix(List<Team> teams, boolean anchorages) {
        /*
         * Se elige un número aleatorio entre 0 y 1 (+1)
         * para asignarle como equipo a un conjunto de jugadores,
         * y el resto tendrá asignado el equipo opuesto.
         */
        randomGenerator = new Random();

        int index;
        int teamSubset1 = randomGenerator.nextInt(teams.size());
        int teamSubset2 = 1 - teamSubset1;

        if (anchorages) {
            Team currentWorkingTeam = teams.get(teamSubset1);

            teamFull:
            for (Position position : Position.values()) {
                List<Player> playersSet = Main.getPlayersSets()
                                              .get(position);

                List<Integer> alreadySetted = new ArrayList<>();

                for (int i = 0; i < playersSet.size() / 2; i++) {
                    do {
                        index = randomGenerator.nextInt(playersSet.size());
                    } while (alreadySetted.contains(index));

                    alreadySetted.add(index);

                    Player player = playersSet.get(index);

                    if (player.getAnchor() != 0 && player.getTeam() == 0) {
                        List<Player> anchoredPlayers = Main.getPlayersSets()
                                                           .values()
                                                           .stream()
                                                           .flatMap(List::stream)
                                                           .filter(p -> p.getAnchor() == player.getAnchor())
                                                           .collect(Collectors.toList());

                        if (currentWorkingTeam.getPlayers()
                                              .values()
                                              .stream()
                                              .mapToInt(List::size)
                                              .sum() + anchoredPlayers.size() <= Main.PLAYERS_PER_TEAM) {
                            anchoredPlayers.forEach(p -> {
                                p.setTeam(teamSubset1 + 1);

                                currentWorkingTeam.getPlayers().get(p.getPosition()).add(p);
                            });
                        }
                    } else {
                        if (player.getTeam() == 0 && currentWorkingTeam.getPlayers()
                                                                       .values()
                                                                       .stream()
                                                                       .mapToInt(List::size)
                                                                       .sum() + 1 <= Main.PLAYERS_PER_TEAM) {
                            player.setTeam(teamSubset1 + 1);

                            currentWorkingTeam.getPlayers().get(player.getPosition()).add(player);
                        }
                    }

                    if (currentWorkingTeam.getPlayers()
                                          .values()
                                          .stream()
                                          .mapToInt(List::size)
                                          .sum()  == Main.PLAYERS_PER_TEAM) {
                        break teamFull;
                    }
                }
            }

            List<Player> remainingPlayers = Main.getPlayersSets()
                                                .values()
                                                .stream()
                                                .flatMap(List::stream)
                                                .filter(p -> p.getTeam() == 0)
                                                .collect(Collectors.toList());

            remainingPlayers.forEach(p -> {
                p.setTeam(teamSubset2 + 1);

                teams.get(teamSubset2)
                     .getPlayers()
                     .get(p.getPosition())
                     .add(p);
            });
        } else {
            for (Position position : Position.values()) {
                /*
                 * Se recorre la mitad de los jugadores del conjunto de manera
                 * aleatoria y se les asigna a los jugadores escogidos
                 * como equipo el número aleatorio generado al principio.
                 * A medida que se van eligiendo jugadores, su índice en
                 * el arreglo se almacena para evitar reasignarle un equipo.
                 * Al resto de jugadores que quedaron sin elegir de manera
                 * aleatoria (aquellos con team == 0) del mismo grupo, se
                 * les asigna el número de equipo opuesto.
                 */
                List<Player> playersSet = Main.getPlayersSets()
                                              .get(position);
                List<Integer> alreadySetted = new ArrayList<>();

                for (int i = 0; i < playersSet.size() / 2; i++) {
                    do {
                        index = randomGenerator.nextInt(playersSet.size());
                    } while (alreadySetted.contains(index));

                    alreadySetted.add(index);

                    Player chosenPlayer = playersSet.get(index);

                    chosenPlayer.setTeam(teamSubset1 + 1);

                    teams.get(teamSubset1)
                         .getPlayers()
                         .get(chosenPlayer.getPosition())
                         .add(chosenPlayer);
                }

                playersSet.stream()
                          .filter(p -> p.getTeam() == 0)
                          .forEach(p -> {
                              p.setTeam(teamSubset2 + 1);

                              teams.get(teamSubset2)
                                   .getPlayers()
                                   .get(p.getPosition())
                                   .add(p);
                          });

                alreadySetted.clear();
            }
        }

        return teams;
    }

    /**
     * Reparte los jugadores en dos equipos de la manera más equitativa posible
     * en base a los puntuaciones ingresados por el usuario.
     *
     * @param anchorages Si la mezcla por puntuaciones debe tener en cuenta anclajes establecidos.
     */
    public List<Team> ratingsMix(List<Team> teams, boolean anchorages) {
        if (anchorages) {
            // TODO
        } else {
            for (Position position : Position.values()) {
                List<Player> currentSet = Main.getPlayersSets()
                                              .get(position);

                // Se ordenan los jugadores de esta posición en base a su puntuación, de mayor a menor
                currentSet.sort(Comparator.comparingInt(Player::getRating)
                                          .reversed());

                /*
                 * Se ordenan los equipos en base a la suma de los puntuaciones
                 * de sus jugadores hasta el momento, de menor a mayor.
                 */
                teams.sort(Comparator.comparingInt(t -> t.getPlayers()
                                                         .values()
                                                         .stream()
                                                         .flatMap(List::stream)
                                                         .mapToInt(Player::getRating)
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

                    List<List<Player>> playersSubsets = new ArrayList<>();

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

                    playersSubsets.add(playersSubset1);
                    playersSubsets.add(playersSubset2);

                    // Ordenamos los subconjuntos en base a sus puntuaciones, de mayor a menor
                    playersSubsets.sort(Comparator.comparingInt(ps -> ps.stream()
                                                                        .mapToInt(Player::getRating)
                                                                        .reduce(0, Math::addExact)));

                    // Al equipo con menor puntuación se le asigna el conjunto de jugadores que sumen mayor puntuación
                    teams.get(0)
                         .getPlayers()
                         .get(position)
                         .addAll(playersSubsets.get(1));

                    // Al equipo con mayor puntuación se le asigna el conjunto de jugadores que sumen menor puntuación
                    teams.get(1)
                         .getPlayers()
                         .get(position)
                         .addAll(playersSubsets.get(0));
                }
            }
        }

        return teams;
    }
}
