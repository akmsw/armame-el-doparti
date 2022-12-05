package armameeldoparti.utils.common;

import armameeldoparti.controllers.Controller;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.Views;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Common-use fields class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 30/10/2022
 */
public final class CommonFields {

  // ---------------------------------------- Private fields ------------------------------------

  private static int distribution;

  private static boolean anchorages;

  private static Map<Position, Integer> playersAmountMap;
  private static Map<Position, List<Player>> playersSets;
  private static Map<Position, String> positionsMap;
  private static Map<Views, Controller> controllersMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Empty, private constructor. Not needed.
   */
  private CommonFields() {
    // Not needed.
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Initializes the common-used maps.
   */
  public static final void initializeMaps() {
    controllersMap = new EnumMap<>(Views.class);
    playersAmountMap = new EnumMap<>(Position.class);
    positionsMap = new EnumMap<>(Position.class);

    playersSets = new TreeMap<>();

    positionsMap.put(Position.CENTRAL_DEFENDER, "DEFENSORES CENTRALES");
    positionsMap.put(Position.LATERAL_DEFENDER, "DEFENSORES LATERALES");
    positionsMap.put(Position.MIDFIELDER, "MEDIOCAMPISTAS");
    positionsMap.put(Position.FORWARD, "DELANTEROS");
    positionsMap.put(Position.GOALKEEPER, "ARQUEROS");
  }

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Gets the chosen players distribution.
   *
   * @return The chosen players distribution.
   */
  public static final int getDistribution() {
    return distribution;
  }

  /**
   * Indicates if the anchorages checkbox is checked or not.
   *
   * @return Whether the anchorages checkbox is checked or not.
   */
  public static final boolean thereAreAnchorages() {
    return anchorages;
  }

  /**
   * Gets the map that contains the total amount of players for each position.
   *
   * @return The map that contains the total amount of players for each position.
   */
  public static final Map<Position, Integer> getPlayersAmountMap() {
    return playersAmountMap;
  }

  /**
   * Gets the map that associates each players set with its corresponding position.
   *
   * @return The map that associates each players set with its corresponding position.
   */
  public static final Map<Position, List<Player>> getPlayersSets() {
    return playersSets;
  }

  /**
   * Gets the map that associates each position with its string representation.
   *
   * @return The map that associates each position with its string representation.
   */
  public static final Map<Position, String> getPositionsMap() {
    return positionsMap;
  }

  /**
   * Gets the map that associates each view with its corresponding controller.
   *
   * @return Gets the map that associates each view with its corresponding controller.
   */
  public static final Map<Views, Controller> getControllersMap() {
    return controllersMap;
  }

  // ---------------------------------------- Setters -------------------------------------------

  /**
   * Updates the chosen players distribution.
   *
   * @param newDistribution The new chosen players distribution.
   */
  public static final void setDistribution(int newDistribution) {
    distribution = newDistribution;
  }

  /**
   * Updates the anchorages option state.
   *
   * @param newAnchoragesState The new anchorages option state.
   */
  public static final void setAnchorages(boolean newAnchoragesState) {
    anchorages = newAnchoragesState;
  }
}