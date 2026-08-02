package armameeldopartidesktop.views;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import armameeldopartidesktop.models.Player;
import armameeldopartidesktop.models.enums.Position;
import armameeldopartidesktop.utils.common.CommonFields;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.utils.common.custom.graphical.CustomButton;
import armameeldopartidesktop.utils.common.custom.graphical.CustomLabel;
import armameeldopartidesktop.utils.common.custom.graphical.CustomScrollPane;

import net.miginfocom.swing.MigLayout;

/**
 * Anchorages view class.
 *
 * @since 3.0.0
 *
 * @version 1.1.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class AnchoragesView extends View {

  // ---------- Private constants -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private static final int TEXT_AREA_ROWS    = 10;
  private static final int TEXT_AREA_COLUMNS = 12;

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private JButton backButton;
  private JButton editAnchorageButton;
  private JButton deleteLastAnchorageButton;
  private JButton deleteAnchorageButton;
  private JButton clearAnchoragesButton;
  private JButton finishButton;
  private JButton newAnchorageButton;

  private JPanel leftPanel;
  private JPanel rightPanel;

  private JScrollPane scrollPane;

  private JTextArea textArea;

  private List<JButton> anchorageButtons;

  /**
   * Map that associates each position with a checkboxes list.
   */
  private Map<Position, List<JCheckBox>> checkboxesMap;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the anchorages view.
   */
  public AnchoragesView() {
    super(Constants.TITLE_VIEW_ANCHORAGES, Constants.MIG_LAYOUT_WRAP_2);

    setLeftPanel(new JPanel(new MigLayout(Constants.MIG_LAYOUT_WRAP_2)));
    setRightPanel(new JPanel(new MigLayout(Constants.MIG_LAYOUT_WRAP)));
    setTextArea(new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS));
    setScrollPane(new CustomScrollPane(textArea));
    setAnchorageButtons(new ArrayList<>());
    initializeCheckBoxesMap();
    initializeInterface();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void initializeInterface() {
    add(leftPanel, Constants.MIG_LAYOUT_WEST);
    add(rightPanel, Constants.MIG_LAYOUT_EAST);

    addCheckBoxes();
    addTextArea();
    addButtons();
    refreshView();
  }

  @Override
  protected void addButtons() {
    setBackButton(new CustomButton("Atrás"));
    setEditAnchorageButton(new CustomButton("Editar un anclaje"));
    setDeleteLastAnchorageButton(new CustomButton("Borrar último anclaje"));
    setDeleteAnchorageButton(new CustomButton("Borrar un anclaje"));
    setClearAnchoragesButton(new CustomButton("Limpiar anclajes"));
    setFinishButton(new CustomButton("Finalizar"));
    setNewAnchorageButton(new CustomButton("Anclar"));

    anchorageButtons.add(finishButton);
    anchorageButtons.add(newAnchorageButton);
    anchorageButtons.add(editAnchorageButton);
    anchorageButtons.add(deleteLastAnchorageButton);
    anchorageButtons.add(deleteAnchorageButton);
    anchorageButtons.add(clearAnchoragesButton);

    leftPanel.add(finishButton, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROWX, Constants.MIG_LAYOUT_SPAN));
    leftPanel.add(backButton, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROWX, Constants.MIG_LAYOUT_SPAN));

    rightPanel.add(newAnchorageButton, Constants.MIG_LAYOUT_GROW);
    rightPanel.add(editAnchorageButton, Constants.MIG_LAYOUT_GROW);
    rightPanel.add(deleteLastAnchorageButton, Constants.MIG_LAYOUT_GROW);
    rightPanel.add(deleteAnchorageButton, Constants.MIG_LAYOUT_GROW);
    rightPanel.add(clearAnchoragesButton, Constants.MIG_LAYOUT_GROW);
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Initializes the checkboxes map.
   */
  private void initializeCheckBoxesMap() {
    setCheckboxesMap(new EnumMap<>(Position.class));

    for (Position position : Position.values()) {
      checkboxesMap.put(position, new ArrayList<>());
    }
  }

  /**
   * Adds the checkboxes and their position labels.
   */
  private void addCheckBoxes() {
    CommonFields.getPlayersSets()
                .forEach((position, playersSet) -> {
                  fillCheckboxesSet(playersSet, checkboxesMap.get(position));
                  addCheckboxesSet(checkboxesMap.get(position), CommonFunctions.capitalize(Constants.MAP_POSITIONS.get(position)));
                });
  }

  /**
   * Adds the text area where to display the anchorages.
   */
  private void addTextArea() {
    rightPanel.add(scrollPane, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_SPAN2, Constants.MIG_LAYOUT_PUSH, Constants.MIG_LAYOUT_GROW));
  }

  /**
   * Fills the checkboxes sets.
   *
   * @param playersSet Players sets from where to obtain the names.
   * @param cbSet      Check boxes set to fill.
   */
  private void fillCheckboxesSet(List<Player> playersSet, List<JCheckBox> cbSet) {
    for (Player player : playersSet) {
      cbSet.add(new JCheckBox(player.getName()));
    }
  }

  /**
   * Adds the checkboxes with a label that specifies the corresponding position.
   *
   * @param cbSet     Check boxes to add.
   * @param labelText Label text.
   */
  private void addCheckboxesSet(List<JCheckBox> cbSet, String labelText) {
    leftPanel.add(new CustomLabel(labelText, null, SwingConstants.CENTER), CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROWX, Constants.MIG_LAYOUT_SPAN));

    for (JCheckBox jCheckBox : cbSet) {
      leftPanel.add(jCheckBox, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_ALIGN_LEFT, Constants.MIG_LAYOUT_PUSHX));
    }
  }

  // ---------- Public getters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public JButton getBackButton() {
    return backButton;
  }

  public JButton getEditAnchorageButton() {
    return editAnchorageButton;
  }

  public JButton getDeleteLastAnchorageButton() {
    return deleteLastAnchorageButton;
  }

  public JButton getDeleteAnchorageButton() {
    return deleteAnchorageButton;
  }

  public JButton getClearAnchoragesButton() {
    return clearAnchoragesButton;
  }

  public JButton getFinishButton() {
    return finishButton;
  }

  public JButton getNewAnchorageButton() {
    return newAnchorageButton;
  }

  public JPanel getLeftPanel() {
    return leftPanel;
  }

  public JPanel getRightPanel() {
    return rightPanel;
  }

  public JScrollPane getScrollPane() {
    return scrollPane;
  }

  public JTextArea getTextArea() {
    return textArea;
  }

  public List<JButton> getAnchorageButtons() {
    return anchorageButtons;
  }

  public Map<Position, List<JCheckBox>> getCheckboxesMap() {
    return checkboxesMap;
  }

  // ---------- Public setters ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setBackButton(JButton backButton) {
    this.backButton = backButton;
  }

  public void setEditAnchorageButton(JButton editAnchorageButton) {
    this.editAnchorageButton = editAnchorageButton;
  }

  public void setDeleteLastAnchorageButton(JButton deleteLastAnchorageButton) {
    this.deleteLastAnchorageButton = deleteLastAnchorageButton;
  }

  public void setDeleteAnchorageButton(JButton deleteAnchorageButton) {
    this.deleteAnchorageButton = deleteAnchorageButton;
  }

  public void setClearAnchoragesButton(JButton clearAnchoragesButton) {
    this.clearAnchoragesButton = clearAnchoragesButton;
  }

  public void setFinishButton(JButton finishButton) {
    this.finishButton = finishButton;
  }

  public void setNewAnchorageButton(JButton newAnchorageButton) {
    this.newAnchorageButton = newAnchorageButton;
  }

  public void setLeftPanel(JPanel leftPanel) {
    this.leftPanel = leftPanel;
  }

  public void setRightPanel(JPanel rightPanel) {
    this.rightPanel = rightPanel;
  }

  public void setScrollPane(JScrollPane scrollPane) {
    this.scrollPane = scrollPane;
  }

  public void setTextArea(JTextArea textArea) {
    this.textArea = textArea;
  }

  public void setAnchorageButtons(List<JButton> anchorageButtons) {
    this.anchorageButtons = anchorageButtons;
  }

  public void setCheckboxesMap(Map<Position, List<JCheckBox>> checkboxesMap) {
    this.checkboxesMap = checkboxesMap;
  }
}