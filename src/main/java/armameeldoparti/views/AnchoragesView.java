package armameeldoparti.views;

import armameeldoparti.controllers.AnchoragesController;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.Views;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import java.util.ArrayList;
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
import net.miginfocom.swing.MigLayout;

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

  private List<JCheckBox> cdCheckBoxes;
  private List<JCheckBox> ldCheckBoxes;
  private List<JCheckBox> mfCheckBoxes;
  private List<JCheckBox> fwCheckBoxes;
  private List<JCheckBox> gkCheckBoxes;

  private Map<Position, List<JCheckBox>> checkBoxesMap;

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

    cdCheckBoxes = new ArrayList<>();
    ldCheckBoxes = new ArrayList<>();
    mfCheckBoxes = new ArrayList<>();
    fwCheckBoxes = new ArrayList<>();
    gkCheckBoxes = new ArrayList<>();
    anchorageButtons = new ArrayList<>();

    checkBoxesMap = new EnumMap<>(Position.class);

    checkBoxesMap.put(Position.CENTRAL_DEFENDER, cdCheckBoxes);
    checkBoxesMap.put(Position.LATERAL_DEFENDER, ldCheckBoxes);
    checkBoxesMap.put(Position.MIDFIELDER, mfCheckBoxes);
    checkBoxesMap.put(Position.FORWARD, fwCheckBoxes);
    checkBoxesMap.put(Position.GOALKEEPER, gkCheckBoxes);

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

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Gets the 'finish' button.
   *
   * @return The finish button.
   */
  public JButton getFinishButton() {
    return finishButton;
  }

  /**
   * Gets the 'new anchorage' button.
   *
   * @return The new anchorage button.
   */
  public JButton getNewAnchorageButton() {
    return newAnchorageButton;
  }

  /**
   * Gets the 'delete anchorage' button.
   *
   * @return The delete anchorage button.
   */
  public JButton getDeleteAnchorageButton() {
    return deleteAnchorageButton;
  }

  /**
   * Gets the 'delete last anchorage' button.
   *
   * @return The delete last anchorage button.
   */
  public JButton getDeleteLastAnchorageButton() {
    return deleteLastAnchorageButton;
  }

  /**
   * Gets the 'clear anchorages' button.
   *
   * @return The 'clear anchorages' button.
   */
  public JButton getClearAnchoragesButton() {
    return clearAnchoragesButton;
  }

  /**
   * Gets the text area.
   *
   * @return The text area.
   */
  public JTextArea getTextArea() {
    return textArea;
  }

  /**
   * Gets the anchorage-related buttons.
   *
   * @return The anchorage-related buttons.
   */
  public List<JButton> getAnchoragesButtons() {
    return anchorageButtons;
  }

  /**
   * Gets the map that associates each checkboxes list with its corresponding position.
   *
   * @return The map that associates each checkboxes list with its corresponding position.
   */
  public Map<Position, List<JCheckBox>> getCheckBoxesMap() {
    return checkBoxesMap;
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
                .entrySet()
                .forEach(ps -> {
                  final Position currentPosition = ps.getValue()
                                                     .get(0)
                                                     .getPosition();

                  fillCheckBoxesSet(ps.getValue(), checkBoxesMap.get(currentPosition));
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
        ((AnchoragesController) CommonFunctions.getController(Views.ANCHORAGES))
        .finishButtonEvent()
    );

    newAnchorageButton = new JButton("Anclar");

    newAnchorageButton.addActionListener(e ->
        ((AnchoragesController) CommonFunctions.getController(Views.ANCHORAGES))
        .newAnchorageButtonEvent()
    );

    deleteAnchorageButton = new JButton("Borrar un anclaje");

    deleteAnchorageButton.addActionListener(e ->
        ((AnchoragesController) CommonFunctions.getController(Views.ANCHORAGES))
        .deleteAnchorageButtonEvent()
    );

    deleteLastAnchorageButton = new JButton("Borrar último anclaje");

    deleteLastAnchorageButton.addActionListener(e ->
        ((AnchoragesController) CommonFunctions.getController(Views.ANCHORAGES))
        .deleteLastAnchorageButtonEvent()
    );

    clearAnchoragesButton = new JButton("Limpiar anclajes");

    clearAnchoragesButton.addActionListener(e ->
        ((AnchoragesController) CommonFunctions.getController(Views.ANCHORAGES))
        .clearAnchoragesButtonEvent()
    );

    JButton backButton = new JButton("Atrás");

    backButton.addActionListener(e ->
        ((AnchoragesController) CommonFunctions.getController(Views.ANCHORAGES))
        .backButtonEvent()
    );

    anchorageButtons.add(finishButton);
    anchorageButtons.add(newAnchorageButton);
    anchorageButtons.add(deleteAnchorageButton);
    anchorageButtons.add(deleteLastAnchorageButton);
    anchorageButtons.add(clearAnchoragesButton);

    leftPanel.add(finishButton, Constants.GROWX_SPAN);
    leftPanel.add(backButton, Constants.GROWX_SPAN);

    rightPanel.add(scrollPane, "span2, push, grow");
    rightPanel.add(newAnchorageButton, Constants.GROW);
    rightPanel.add(deleteAnchorageButton, Constants.GROW);
    rightPanel.add(deleteLastAnchorageButton, Constants.GROW);
    rightPanel.add(clearAnchoragesButton, Constants.GROW);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Fills the checkboxes sets.
   *
   * @param playersSet Players sets from where to obtain the names.
   * @param cbSet      Check boxes set to fill.
   */
  private void fillCheckBoxesSet(List<Player> playersSet, List<JCheckBox> cbSet) {
    playersSet.forEach(p -> cbSet.add(new JCheckBox(p.getName())));
  }

  /**
   * Adds the checkboxes to the view with a label that specifies the corresponding position.
   *
   * @param cbSet Check boxes to add.
   * @param title Label text.
   */
  private void addCheckBoxesSet(List<JCheckBox> cbSet, String text) {
    JLabel label = new JLabel(text);

    label.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

    leftPanel.add(label, Constants.GROWX_SPAN);

    cbSet.forEach(cb -> leftPanel.add(cb, "align left, pushx"));
  }
}