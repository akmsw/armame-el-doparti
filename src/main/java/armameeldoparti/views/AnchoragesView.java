package armameeldoparti.views;

import armameeldoparti.controllers.AnchoragesController;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import org.jetbrains.annotations.NotNull;

/**
 * Anchorages view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 15/03/2021
 */
public class AnchoragesView extends View {

  // ---------------------------------------- Private fields ------------------------------------

  private @Getter JButton finishButton;
  private @Getter JButton newAnchorageButton;
  private @Getter JButton deleteAnchorageButton;
  private @Getter JButton deleteLastAnchorageButton;
  private @Getter JButton clearAnchoragesButton;

  private final JPanel leftPanel;
  private final JPanel rightPanel;

  private final JScrollPane scrollPane;

  private @Getter JTextArea textArea;

  private @Getter List<JButton> anchorageButtons;

  /**
   * Map that associates each checkboxes-list with its corresponding position.
   */
  private @Getter Map<Position, List<JCheckBox>> checkBoxesMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the anchorages view.
   */
  public AnchoragesView() {
    super("Anclaje de jugadores");

    leftPanel = new JPanel(new MigLayout("wrap 2"));
    rightPanel = new JPanel(new MigLayout("wrap"));

    textArea = new JTextArea();

    scrollPane = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    anchorageButtons = new ArrayList<>();

    checkBoxesMap = new EnumMap<>(Position.class);

    for (Position position : Position.values()) {
      checkBoxesMap.put(position, new ArrayList<>());
    }

    initializeInterface();
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Updates the checkboxes text with the players names.
   */
  public void updateCheckBoxesText() {
    for (Position position : Position.values()) {
      for (int i = 0; i < CommonFields.getPlayersSets()
                                      .get(position)
                                      .size(); i++) {
        checkBoxesMap.get(position)
                     .get(i)
                     .setText(CommonFields.getPlayersSets()
                                          .get(position)
                                          .get(i)
                                          .getName());
      }
    }
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Initializes the view and makes it visible.
   */
  @Override
  protected void initializeInterface() {
    scrollPane.getVerticalScrollBar()
              .setUI(new BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                  this.thumbColor = Constants.GREEN_DARK;
                  this.trackColor = Constants.GREEN_MEDIUM;
                }
              });

    CommonFields.getPlayersSets()
                .forEach((key, value) -> {
                  final Position currentPosition = value.get(0)
                                                        .getPosition();

                  fillCheckBoxesSet(value, checkBoxesMap.get(currentPosition));
                  addCheckBoxesSet(checkBoxesMap.get(currentPosition),
                                   CommonFields.getPositionsMap()
                                               .get(currentPosition));
                });

    textArea.setBorder(BorderFactory.createBevelBorder(1));
    textArea.setEditable(false);

    JPanel masterPanel = new JPanel(new MigLayout("wrap 2"));

    masterPanel.add(leftPanel, "west");
    masterPanel.add(rightPanel, "east");

    addButtons();
    add(masterPanel);
    setTitle(getFrameTitle());
    setResizable(false);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(Constants.ICON
                          .getImage());
    pack();
    setLocationRelativeTo(null);
  }

  /**
   * Adds the buttons to their corresponding panel.
   */
  @Override
  protected void addButtons() {
    finishButton = new JButton("Finalizar");

    finishButton.setEnabled(false);
    finishButton.addActionListener(e ->
        ((AnchoragesController) CommonFunctions.getController(ProgramView.ANCHORAGES))
        .finishButtonEvent()
    );

    newAnchorageButton = new JButton("Anclar");

    newAnchorageButton.addActionListener(e ->
        ((AnchoragesController) CommonFunctions.getController(ProgramView.ANCHORAGES))
        .newAnchorageButtonEvent()
    );

    deleteAnchorageButton = new JButton("Borrar un anclaje");

    deleteAnchorageButton.addActionListener(e ->
        ((AnchoragesController) CommonFunctions.getController(ProgramView.ANCHORAGES))
        .deleteAnchorageButtonEvent()
    );

    deleteLastAnchorageButton = new JButton("Borrar último anclaje");

    deleteLastAnchorageButton.addActionListener(e ->
        ((AnchoragesController) CommonFunctions.getController(ProgramView.ANCHORAGES))
        .deleteLastAnchorageButtonEvent()
    );

    clearAnchoragesButton = new JButton("Limpiar anclajes");

    clearAnchoragesButton.addActionListener(e ->
        ((AnchoragesController) CommonFunctions.getController(ProgramView.ANCHORAGES))
        .clearAnchoragesButtonEvent()
    );

    JButton backButton = new JButton("Atrás");

    backButton.addActionListener(e ->
        ((AnchoragesController) CommonFunctions.getController(ProgramView.ANCHORAGES))
        .backButtonEvent()
    );

    anchorageButtons.add(finishButton);
    anchorageButtons.add(newAnchorageButton);
    anchorageButtons.add(deleteAnchorageButton);
    anchorageButtons.add(deleteLastAnchorageButton);
    anchorageButtons.add(clearAnchoragesButton);

    for (JButton button : Arrays.asList(finishButton, backButton)) {
      leftPanel.add(button, "growx, span");
    }

    rightPanel.add(scrollPane, "span2, push, grow");
    rightPanel.add(newAnchorageButton, "grow");
    rightPanel.add(deleteAnchorageButton, "grow");
    rightPanel.add(deleteLastAnchorageButton, "grow");
    rightPanel.add(clearAnchoragesButton, "grow");
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Fills the checkboxes sets.
   *
   * @param playersSet Players sets from where to obtain the names.
   * @param cbSet      Check boxes set to fill.
   */
  private void fillCheckBoxesSet(@NotNull List<Player> playersSet,
                                 @NotNull List<JCheckBox> cbSet) {
    playersSet.forEach(p -> cbSet.add(new JCheckBox(p.getName())));
  }

  /**
   * Adds the checkboxes to the view with a label that specifies the corresponding position.
   *
   * @param cbSet      Check boxes to add.
   * @param labelText  Label text.
   */
  private void addCheckBoxesSet(@NotNull List<JCheckBox> cbSet,
                                @NotNull String labelText) {
    JLabel label = new JLabel(labelText);

    label.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

    leftPanel.add(label, "growx, span");

    cbSet.forEach(cb -> leftPanel.add(cb, "align left, pushx"));
  }
}