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
import lombok.NonNull;
import net.miginfocom.swing.MigLayout;

/**
 * Anchorages view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since v3.0
 */
@Getter
public class AnchoragesView extends View {

  // ---------------------------------------- Private fields ------------------------------------

  private JButton finishButton;
  private JButton newAnchorageButton;
  private JButton deleteAnchorageButton;
  private JButton deleteLastAnchorageButton;
  private JButton clearAnchoragesButton;

  private JPanel leftPanel;
  private JPanel rightPanel;

  private JScrollPane scrollPane;

  private JTextArea textArea;

  private List<JButton> anchorageButtons;

  /**
   * Map that associates each checkboxes-list with its corresponding position.
   */
  private Map<Position, List<JCheckBox>> checkBoxesMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the anchorages view.
   */
  public AnchoragesView() {
    super("Anclaje de jugadores", "wrap 2");

    leftPanel = new JPanel(new MigLayout("wrap 2"));
    rightPanel = new JPanel(new MigLayout("wrap"));

    textArea = new JTextArea();

    scrollPane = new JScrollPane(textArea,
                                 ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    anchorageButtons = new ArrayList<>();

    checkBoxesMap = new EnumMap<>(Position.class);

    for (Position position : Position.values()) {
      checkBoxesMap.put(position, new ArrayList<>());
    }

    initializeInterface();
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
                  fillCheckBoxesSet(value, checkBoxesMap.get(key));
                  addCheckBoxesSet(checkBoxesMap.get(key),
                                   CommonFields.getPositionsMap()
                                               .get(key));
                });

    textArea.setBorder(BorderFactory.createBevelBorder(1));
    textArea.setEditable(false);

    getMasterPanel().add(leftPanel, "west");
    getMasterPanel().add(rightPanel, "east");

    addButtons();
    add(getMasterPanel());
    setTitle(getFrameTitle());
    setResizable(false);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(Constants.ICON
                          .getImage());
    pack();
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
        .finishButtonEvent(CommonFunctions.getComponentFromEvent(e))
    );

    newAnchorageButton = new JButton("Anclar");
    newAnchorageButton.addActionListener(e ->
        ((AnchoragesController) CommonFunctions.getController(ProgramView.ANCHORAGES))
        .newAnchorageButtonEvent(CommonFunctions.getComponentFromEvent(e))
    );

    deleteAnchorageButton = new JButton("Borrar un anclaje");
    deleteAnchorageButton.addActionListener(e ->
        ((AnchoragesController) CommonFunctions.getController(ProgramView.ANCHORAGES))
        .deleteAnchorageButtonEvent(CommonFunctions.getComponentFromEvent(e))
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
  private void fillCheckBoxesSet(@NonNull List<Player> playersSet,
                                 @NonNull List<JCheckBox> cbSet) {
    playersSet.forEach(p -> cbSet.add(new JCheckBox(p.getName())));
  }

  /**
   * Adds the checkboxes to the view with a label that specifies the corresponding position.
   *
   * @param cbSet     Check boxes to add.
   * @param labelText Label text.
   */
  private void addCheckBoxesSet(@NonNull List<JCheckBox> cbSet,
                                @NonNull String labelText) {
    JLabel label = new JLabel(labelText);

    label.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

    leftPanel.add(label, "growx, span");

    cbSet.forEach(cb -> leftPanel.add(cb, "align left, pushx"));
  }
}