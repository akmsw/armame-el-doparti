package armameeldoparti.utils.mixers;

import armameeldoparti.models.Team;
import java.util.List;
import lombok.NonNull;

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
   *
   * @return The updated teams with the players distributed without considering anchorages.
   */
  @NonNull
  List<Team> withoutAnchorages(@NonNull List<Team> teams);

  /**
   * Distributes the players considering anchorages.
   *
   * @param teams List that contains the two teams.
   *
   * @return The updated teams with the players distributed considering anchorages.
   */
  @NonNull
  List<Team> withAnchorages(@NonNull List<Team> teams);
}