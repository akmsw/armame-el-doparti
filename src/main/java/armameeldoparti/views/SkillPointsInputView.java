package armameeldoparti.views;

import armameeldoparti.controllers.SkillPointsInputController;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.graphical.CustomSpinner;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import lombok.Getter;

/**
 * Skill points input view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since v3.0
 */
@Getter
public class SkillPointsInputView extends View {

  // ---------------------------------------- Private fields ------------------------------------

  private transient Map<Player, JSpinner> spinnersMap;
  private transient Map<JSpinner, JLabel> labelsMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the skill points input view.
   */
  public SkillPointsInputView() {
    super("Ingreso de puntuaciones", "");

    spinnersMap = new HashMap<>();
    labelsMap = new HashMap<>();

    initializeInterface();
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Initializes the view and makes it visible.
   */
  @Override
  public void initializeInterface() {
    setTitle(getFrameTitle());
    setResizable(false);
    setIconImage(Constants.ICON
                          .getImage());
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    addSpinners();
    addButtons();
    add(getMasterPanel());
    pack();
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Adds the buttons to their corresponding panel.
   */
  @Override
  protected void addButtons() {
    JButton finishButton = new JButton("Finalizar");
    finishButton.addActionListener(e ->
        ((SkillPointsInputController) CommonFunctions.getController(ProgramView.SKILL_POINTS))
        .finishButtonEvent()
    );

    JButton resetSkillPointsButton = new JButton("Reiniciar puntuaciones");
    resetSkillPointsButton.addActionListener(e ->
        ((SkillPointsInputController) CommonFunctions.getController(ProgramView.SKILL_POINTS))
        .resetSkillsButtonEvent()
    );

    JButton backButton = new JButton("Atrás");
    backButton.addActionListener(e ->
        ((SkillPointsInputController) CommonFunctions.getController(ProgramView.SKILL_POINTS))
        .backButtonEvent()
    );

    Arrays.asList(finishButton, resetSkillPointsButton, backButton)
          .forEach(b -> getMasterPanel().add(b, "grow, span"));
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the spinners to their corresponding panel.
   */
  private void addSpinners() {
    for (Position position : Position.values()) {
      JLabel positionLabel = new JLabel(CommonFields.getPositionsMap()
                                                    .get(position));

      positionLabel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

      getMasterPanel().add(positionLabel, "grow, span");

      List<Player> currentSet = CommonFields.getPlayersSets()
                                            .get(position);

      int currentSetSize = currentSet.size();

      for (int playerIndex = 0; playerIndex < currentSetSize; playerIndex++) {
        JSpinner spinner = new CustomSpinner(new SpinnerNumberModel(Constants.SKILL_INI,
                                                                    Constants.SKILL_MIN,
                                                                    Constants.SKILL_MAX,
                                                                    Constants.SKILL_STEP));

        JLabel nameLabel = new JLabel(currentSet.get(playerIndex)
                                                .getName());

        spinnersMap.put(currentSet.get(playerIndex), spinner);

        labelsMap.put(spinner, nameLabel);

        getMasterPanel().add(nameLabel, "pushx");
        getMasterPanel().add(spinnersMap.get(currentSet.get(playerIndex)),
                             playerIndex % 2 != 0 ? "wrap" : null);
      }

      spinnersMap.values()
                 .forEach(s -> ((DefaultEditor) s.getEditor()).getTextField()
                                                              .setEditable(false));
    }
  }
}