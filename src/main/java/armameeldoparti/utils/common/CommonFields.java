package armameeldoparti.utils.common;

import armameeldoparti.controllers.Controller;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.Views;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;
import lombok.Setter;

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

  private static @Getter @Setter int distribution;

  private static @Getter @Setter boolean anchorages;

  private static @Getter  Map<Position, Integer> playersAmountMap;
  private static @Getter  Map<Position, List<Player>> playersSets;
  private static @Getter  Map<Position, String> positionsMap;
  private static @Getter  Map<Views, Controller> controllersMap;

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
}