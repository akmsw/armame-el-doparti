package armameeldoparti.utils.common;

import armameeldoparti.controllers.Controller;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import armameeldoparti.views.View;
import java.awt.GraphicsDevice;
import java.util.List;
import java.util.Map;

/**
 * Common-use fields class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public final class CommonFields {

  // ---------------------------------------- Private fields ------------------------------------

  private static int distribution;

  private static boolean anchoragesEnabled;

  private static GraphicsDevice activeMonitor;

  private static Map<Position, Integer> playersAmountMap;
  private static Map<Position, List<Player>> playersSets;
  private static Map<Position, String> positionsMap;
  private static Map<ProgramView, Controller<? extends View>> controllerMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Empty, private constructor.
   */
  private CommonFields() {
    // Body not needed
  }

  // ---------------------------------------- Getters -------------------------------------------

  public static int getDistribution() {
    return distribution;
  }

  public static boolean isAnchoragesEnabled() {
    return anchoragesEnabled;
  }

  public static GraphicsDevice getActiveMonitor() {
    return activeMonitor;
  }

  public static Map<Position, Integer> getPlayersAmountMap() {
    return playersAmountMap;
  }

  public static Map<Position, List<Player>> getPlayersSets() {
    return playersSets;
  }

  public static Map<Position, String> getPositionsMap() {
    return positionsMap;
  }

  /*
   * The "java:S1452" warning is suppressed since the Java compiler can't know at runtime the type
   * of the controlled view.
   */
  @SuppressWarnings("java:S1452")
  public static Map<ProgramView, Controller<? extends View>> getControllerMap() {
    return controllerMap;
  }

  // ---------------------------------------- Setters -------------------------------------------

  public static void setDistribution(int distribution) {
    CommonFields.distribution = distribution;
  }

  public static void setAnchoragesEnabled(boolean anchoragesEnabled) {
    CommonFields.anchoragesEnabled = anchoragesEnabled;
  }

  public static void setActiveMonitor(GraphicsDevice activeMonitor) {
    CommonFields.activeMonitor = activeMonitor;
  }

  public static void setPlayersAmountMap(Map<Position, Integer> playersAmountMap) {
    CommonFields.playersAmountMap = playersAmountMap;
  }

  public static void setPlayersSets(Map<Position, List<Player>> playersSets) {
    CommonFields.playersSets = playersSets;
  }

  public static void setPositionsMap(Map<Position, String> positionsMap) {
    CommonFields.positionsMap = positionsMap;
  }

  public static void setControllerMap(Map<ProgramView, Controller<? extends View>> controllerMap) {
    CommonFields.controllerMap = controllerMap;
  }
}