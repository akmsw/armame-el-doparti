package armameeldopartidesktop.utils.common;

import java.awt.GraphicsDevice;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import armameeldopartidesktop.controllers.Controller;
import armameeldopartidesktop.models.Anchorage;
import armameeldopartidesktop.models.Player;
import armameeldopartidesktop.models.enums.Distribution;
import armameeldopartidesktop.models.enums.Position;
import armameeldopartidesktop.models.enums.ProgramView;
import armameeldopartidesktop.views.View;

/**
 * Common-use fields class.
 *
 * @since 3.0.0
 *
 * @version 1.1.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public final class CommonFields {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private static boolean anchoragesEnabled;

  private static Distribution distribution;

  private static GraphicsDevice activeMonitor;

  private static RoundRectangle2D tooltipRectangle;

  private static JFrame mainFrame;

  private static JPanel mainPanel;

  private static List<Anchorage> anchorages;

  private static Map<Position, Integer> playerLimitPerPosition;
  private static Map<Position, List<Player>> playersSets;
  private static Map<ProgramView, Controller<? extends View>> controllersMap;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Empty, private constructor.
   */
  private CommonFields() {
    // Body not needed
  }

  // ---------- Public getters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public static boolean isAnchoragesEnabled() {
    return anchoragesEnabled;
  }

  public static Distribution getDistribution() {
    return distribution;
  }

  public static GraphicsDevice getActiveMonitor() {
    return activeMonitor;
  }

  public static RoundRectangle2D getTooltipRectangle() {
    return tooltipRectangle;
  }

  public static JFrame getMainFrame() {
    return mainFrame;
  }

  public static JPanel getMainPanel() {
    return mainPanel;
  }

  public static List<Anchorage> getAnchorages() {
    return anchorages;
  }

  public static Map<Position, Integer> getPlayerLimitPerPosition() {
    return playerLimitPerPosition;
  }

  public static Map<Position, List<Player>> getPlayersSets() {
    return playersSets;
  }

  public static Map<ProgramView, Controller<? extends View>> getControllersMap() {
    return controllersMap;
  }

  // ---------- Public setters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public static void setAnchoragesEnabled(boolean anchoragesEnabled) {
    CommonFields.anchoragesEnabled = anchoragesEnabled;
  }

  public static void setDistribution(Distribution distribution) {
    CommonFields.distribution = distribution;
  }

  public static void setActiveMonitor(GraphicsDevice activeMonitor) {
    CommonFields.activeMonitor = activeMonitor;
  }

  public static void setTooltipRectangle(RoundRectangle2D tooltipRectangle) {
    CommonFields.tooltipRectangle = tooltipRectangle;
  }

  public static void setMainFrame(JFrame mainFrame) {
    CommonFields.mainFrame = mainFrame;
  }

  public static void setMainPanel(JPanel mainPanel) {
    CommonFields.mainPanel = mainPanel;
  }

  public static void setAnchorages(List<Anchorage> anchorages) {
    CommonFields.anchorages = anchorages;
  }

  public static void setPlayerLimitPerPosition(Map<Position, Integer> playerLimitPerPosition) {
    CommonFields.playerLimitPerPosition = playerLimitPerPosition;
  }

  public static void setPlayersSets(Map<Position, List<Player>> playersSets) {
    CommonFields.playersSets = playersSets;
  }

  public static void setControllersMap(Map<ProgramView, Controller<? extends View>> controllerMap) {
    CommonFields.controllersMap = controllerMap;
  }
}