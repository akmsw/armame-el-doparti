package armameeldoparti.views;

import armameeldoparti.Main;
import armameeldoparti.controllers.AnchoragesController;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.models.Views;
import armameeldoparti.utils.Constants;
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

  // ---------------------------------------- Private constants ---------------------------------

  private static final String FRAME_TITLE = "Anclaje de jugadores";

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

  private List<JCheckBox> cdCheckboxes;
  private List<JCheckBox> ldCheckboxes;
  private List<JCheckBox> mfCheckboxes;
  private List<JCheckBox> fwCheckboxes;
  private List<JCheckBox> gkCheckboxes;
  private List<JButton> anchorageButtons;

  private Map<Position, List<JCheckBox>> checkBoxesMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the anchorages view.
   */
  public AnchoragesView() {
    cdCheckboxes = new ArrayList<>();
    ldCheckboxes = new ArrayList<>();
    mfCheckboxes = new ArrayList<>();
    fwCheckboxes = new ArrayList<>();
    gkCheckboxes = new ArrayList<>();
    anchorageButtons = new ArrayList<>();

    checkBoxesMap = new EnumMap<>(Position.class);

    checkBoxesMap.put(Position.CENTRAL_DEFENDER, cdCheckboxes);
    checkBoxesMap.put(Position.LATERAL_DEFENDER, ldCheckboxes);
    checkBoxesMap.put(Position.MIDFIELDER, mfCheckboxes);
    checkBoxesMap.put(Position.FORWARD, fwCheckboxes);
    checkBoxesMap.put(Position.GOALKEEPER, gkCheckboxes);

    initializeInterface();
  }

  // ---------------------------------------- Public methods ------------------------------------

  /**
   * Updates the checkboxes text with the players names.
   */
  public void updateCheckBoxesText() {
    for (Position position : Position.values()) {
      for (int i = 0; i < Main.getPlayersSets()
                              .get(position)
                              .size(); i++) {
        checkBoxesMap.get(position)
                     .get(i)
                     .setText(Main.getPlayersSets()
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
    leftPanel = new JPanel(new MigLayout("wrap 2"));
    rightPanel = new JPanel(new MigLayout("wrap"));

    textArea = new JTextArea();

    scrollPane = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    scrollPane.getVerticalScrollBar()
              .setUI(new BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                  this.thumbColor = Constants.DARK_GREEN;
                  this.trackColor = Constants.MEDIUM_GREEN;
                }
              });

    Main.getPlayersSets()
        .entrySet()
        .forEach(ps -> {
          final Position currentPosition = ps.getValue()
                                              .get(0)
                                              .getPosition();

          fillCheckboxesSet(ps.getValue(), checkBoxesMap.get(currentPosition));
          addCheckboxesSet(checkBoxesMap.get(currentPosition), Main.getPositionsMap()
                                                                   .get(currentPosition));
        });

    textArea.setBorder(BorderFactory.createBevelBorder(1));
    textArea.setEditable(false);

    JPanel masterPanel = new JPanel(new MigLayout("wrap 2"));

    masterPanel.add(leftPanel, "west");
    masterPanel.add(rightPanel, "east");

    addButtons();
    add(masterPanel);
    setTitle(FRAME_TITLE);
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
        ((AnchoragesController) Main.getController(Views.ANCHORAGES)).finishButtonEvent()
    );

    newAnchorageButton = new JButton("Anclar");

    newAnchorageButton.addActionListener(e ->
        ((AnchoragesController) Main.getController(Views.ANCHORAGES)).newAnchorageButtonEvent()
    );

    deleteAnchorageButton = new JButton("Borrar un anclaje");

    deleteAnchorageButton.addActionListener(e ->
        ((AnchoragesController) Main.getController(Views.ANCHORAGES)).deleteAnchorageButtonEvent()
    );

    deleteLastAnchorageButton = new JButton("Borrar último anclaje");

    deleteLastAnchorageButton.addActionListener(e ->
        ((AnchoragesController) Main.getController(Views.ANCHORAGES))
        .deleteLastAnchorageButtonEvent()
    );

    clearAnchoragesButton = new JButton("Limpiar anclajes");

    clearAnchoragesButton.addActionListener(e ->
        ((AnchoragesController) Main.getController(Views.ANCHORAGES)).clearAnchoragesButtonEvent()
    );

    JButton backButton = new JButton("Atrás");

    backButton.addActionListener(e ->
        ((AnchoragesController) Main.getController(Views.ANCHORAGES)).backButtonEvent()
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
   * @param cbSet      Checkboxes set to fill.
   */
  private void fillCheckboxesSet(List<Player> playersSet, List<JCheckBox> cbSet) {
    playersSet.forEach(p -> cbSet.add(new JCheckBox(p.getName())));
  }

  /**
   * Adds the checkboxes to the view with a label that specifies the corresponding position.
   *
   * @param cbSet Checkboxes to add.
   * @param title Label text.
   */
  private void addCheckboxesSet(List<JCheckBox> cbSet, String text) {
    JLabel label = new JLabel(text);

    label.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

    leftPanel.add(label, Constants.GROWX_SPAN);

    cbSet.forEach(cb -> leftPanel.add(cb, "align left, pushx"));
  }
}