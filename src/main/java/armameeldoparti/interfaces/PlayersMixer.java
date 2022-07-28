package armameeldoparti.interfaces;

import armameeldoparti.models.Team;
import java.util.List;

/**
 * Interfaz que especifica los modos de distribución de jugadores.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 13/07/2022
 */
public interface PlayersMixer {

  // ---------------------------------------- Métodos públicos abstractos -----------------------

  /**
   * Distribuye los jugadores sin tener en cuenta anclajes.
   *
   * @param teams Lista contenedora de equipos.
   *
   * @return Los equipos con los jugadores distribuidos de la manera deseada.
   */
  List<Team> withoutAnchorages(List<Team> teams);

  /**
   * Distribuye los jugadores teniendo en cuenta anclajes.
   *
   * @param teams Lista contenedora de equipos.
   *
   * @return Los equipos con los jugadores distribuidos de la manera deseada.
   */
  List<Team> withAnchorages(List<Team> teams);
}