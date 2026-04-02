package armameeldopartidesktop.models;

import java.util.List;

import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.models.enums.Error;

/**
 * Anchorage class.
 *
 * @since 3.1.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class Anchorage {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private List<Player> players;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public Anchorage(List<Player> players) {
    setPlayers(players);
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void removePlayer(Player player) {
    if (!players.remove(player)) {
      CommonFunctions.exitProgram(Error.ERROR_INTERNAL, new IllegalArgumentException(Constants.MSG_ERROR_DEBUG_PLAYER_NOT_IN_ANCHORAGE));
    }
  }

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public List<Player> getPlayers() {
    return players;
  }

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setPlayers(List<Player> players) {
    if (players.size() > Constants.MAX_ANCHORAGE_SIZE) {
      CommonFunctions.exitProgram(Error.ERROR_INTERNAL, new IllegalArgumentException(Constants.MSG_ERROR_DEBUG_INVALID_ANCHORAGE_SIZE));
    }

    this.players = players;
  }
}