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

  private int anchorageId;

  private List<Player> players;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public Anchorage(int anchorageId) {
    setAnchorageId(anchorageId);
  }

  public Anchorage(int anchorageId, List<Player> players) {
    setAnchorageId(anchorageId);
    setPlayers(players);
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void clearAnchorage() {
    players.clear();
  }

  public void removePlayer(Player player) {
    if (!players.remove(player)) {
      CommonFunctions.exitProgram(Error.ERROR_INTERNAL, new IllegalArgumentException(Constants.MSG_ERROR_DEBUG_PLAYER_NOT_IN_ANCHORAGE));
    }
  }

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public int getAnchorageId() {
    return anchorageId;
  }

  public List<Player> getPlayers() {
    return players;
  }

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setAnchorageId(int anchorageId) {
    if (anchorageId < 0) {
      CommonFunctions.exitProgram(Error.ERROR_INTERNAL, new IllegalArgumentException(Constants.MSG_ERROR_DEBUG_INVALID_ANCHORAGE_ID));
    }

    this.anchorageId = anchorageId;
  }

  public void setPlayers(List<Player> players) {
    if (players.size() <= Constants.MAX_ANCHORAGE_SIZE) {
      this.players = players;

      for (Player player : players) {
        player.setAnchorageId(anchorageId);
      }
    }
  }
}