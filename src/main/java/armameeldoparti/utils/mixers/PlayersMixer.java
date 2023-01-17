package armameeldoparti.utils.mixers;

import armameeldoparti.models.Team;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Interface that specifies the players distribution methods.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 13/07/2022
 */
public interface PlayersMixer {

  // ---------------------------------------- Public abstract methods ---------------------------

  /**
   * Distributes the players without considering anchorages.
   *
   * @param teams List that contains the two teams.
   * @return The updated teams with the players distributed
   *         without considering anchorages.
   */
  @NotNull
  List<Team> withoutAnchorages(@NotNull List<Team> teams);

  /**
   * Distributes the players considering anchorages.
   *
   * @param teams List that contains the two teams.
   * @return The updated teams with the players distributed
   *         considering anchorages.
   */
  @NotNull
  List<Team> withAnchorages(@NotNull List<Team> teams);
}