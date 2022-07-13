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
         * Se elige un número aleatorio entre 0 y 1 para
         * asignarle como equipo a un conjunto de jugadores,
         * y el resto tendrá asignado el equipo opuesto.
         */
        randomGenerator = new Random();

        int index;
        int teamSubset1 = randomGenerator.nextInt(teams.size());
        int teamSubset2 = 1 - teamSubset1;

        if (anchorages) {
            /*
             * Si hay anclajes, se comienza recorriendo cada posición.
             * Mientras la posición en la que se está trabajando no tenga la
             * cantidad de jugadores especificada para dicha posición por equipo,
             * se seguirá iterando.
             * Se escoge un jugador de manera aleatoria del conjunto total de
             * jugadores con la posición seleccionada, y se chequea si está
             * disponible (equipo = 0) y si tiene anclajes (anclaje != 0).
             * Si el jugador está disponible y está anclado con otros jugadores,
             * se toman todos los jugadores de todas las posiciones con su mismo
             * número de anclaje y se valida si se pueden agregar al equipo sin
             * sobrepasar la cantidad de jugadores permitida por cada posición.
             * En caso de que sí se pueda, se los agrega. Si no, se los ignora y se
             * continúa iterando.
             * Si el jugador no tiene anclaje y se lo puede agregar sin sobrepasar el
             * límite de jugadores para su posición, se lo agrega.
             * Cuando el primer equipo elegido está lleno, se deja de iterar.
             * Se toman todos los jugadores restantes y se les asigna el número de
             * equipo contrario al elegido en un principio.
             */
            Team currentWorkingTeam = teams.get(teamSubset1);

            boolean teamFull = false;

            for (int i = 0; i < Position.values().length && !teamFull; i++) {
                List<Player> playersSet = Main.getPlayersSets()
                                              .get(Position.values()[i]);

                List<Integer> alreadySetted = new ArrayList<>();

                while (currentWorkingTeam.getPlayers()
                                         .get(Position.values()[i])
                                         .size() < Main.getPlayersAmountMap()
                                                       .get(Position.values()[i])
                       && !teamFull) {
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

                        if (currentWorkingTeam.getPlayersCount() + anchoredPlayers.size() <= Main.PLAYERS_PER_TEAM
                            && validateAnchoredPlayers(currentWorkingTeam, anchoredPlayers)) {
                            anchoredPlayers.forEach(p -> {
                                p.setTeam(teamSubset1 + 1);

                                currentWorkingTeam.getPlayers().get(p.getPosition()).add(p);
                            });
                        }
                    } else {
                        if (player.getTeam() == 0
                            && currentWorkingTeam.getPlayersCount() + 1 <= Main.PLAYERS_PER_TEAM) {
                            player.setTeam(teamSubset1 + 1);

                            currentWorkingTeam.getPlayers()
                                              .get(player.getPosition())
                                              .add(player);
                        }
                    }

                    if (currentWorkingTeam.getPlayersCount() == Main.PLAYERS_PER_TEAM) {
                        teamFull = true;
                        break;
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

    // ---------------------------------------- Métodos privados ----------------------------------

    /**
     * Valida si todos los jugadores anclados pueden ser agregados al equipo.
     * <p>
     * Se recorren los conjuntos de jugadores con las posiciones de todos los anclados, y
     * se evalúa si al agregarlos no se supera el número de jugadores permitidos por posición
     * por equipo.
     * <p>
     * Esto se hace con el fin de evitar que en un equipo queden más de la mitad de jugadores
     * de una posición.
     *
     * @param team            Equipo donde se desea registrar los jugadores anclados
     * @param anchoredPlayers Lista de jugadores con el mismo anclaje.
     *
     * @return Si se pueden agregar al equipo los jugadores anclados especificados.
     */
    private boolean validateAnchoredPlayers(Team team, List<Player> anchoredPlayers) {
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
