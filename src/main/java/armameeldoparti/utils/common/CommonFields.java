package armameeldoparti.utils.common;

import armameeldoparti.controllers.Controller;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
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

  private static @Getter @Setter boolean anchoragesEnabled;

  private static @Getter @Setter GraphicsDevice activeMonitor;

  private static @Getter Map<Position, Integer> playersAmountMap;
  private static @Getter Map<Position, List<Player>> playersSets;
  private static @Getter Map<Position, String> positionsMap;
  private static @Getter Map<ProgramView, Controller> controllersMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Empty, private constructor. Not needed.
   */
  private CommonFields() {
    // Not needed.
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Establishes the main monitor as active monitor by default.
   */
  public static void initializeActiveMonitor() {
    setActiveMonitor(GraphicsEnvironment.getLocalGraphicsEnvironment()
                                        .getDefaultScreenDevice());
  }

  /**
   * Initializes the common-used maps.
   */
  public static void initializeMaps() {
    controllersMap = new EnumMap<>(ProgramView.class);
    playersAmountMap = new EnumMap<>(Position.class);
    positionsMap = new EnumMap<>(Position.class);

    playersSets = new TreeMap<>();

    positionsMap.put(Position.CENTRAL_DEFENDER, Constants.POSITION_CENTRAL_DEFENDERS);
    positionsMap.put(Position.LATERAL_DEFENDER, Constants.POSITION_LATERAL_DEFENDERS);
    positionsMap.put(Position.MIDFIELDER, Constants.POSITION_MIDFIELDERS);
    positionsMap.put(Position.FORWARD, Constants.POSITION_FORWARDS);
    positionsMap.put(Position.GOALKEEPER, Constants.POSITION_GOALKEEPERS);
  }
}