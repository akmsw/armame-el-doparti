package armameeldoparti.views;

import armameeldoparti.controllers.NamesInputController;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.CustomButton;
import armameeldoparti.utils.common.custom.graphical.CustomCheckBox;
import armameeldoparti.utils.common.custom.graphical.CustomComboBox;
import armameeldoparti.utils.common.custom.graphical.CustomLabel;
import armameeldoparti.utils.common.custom.graphical.CustomRadioButton;
import armameeldoparti.utils.common.custom.graphical.CustomScrollPane;
import armameeldoparti.utils.common.custom.graphical.CustomSeparator;
import armameeldoparti.utils.common.custom.graphical.CustomTextArea;
import armameeldoparti.utils.common.custom.graphical.CustomTextField;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.naming.InvalidNameException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

/**
 * Names input view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 3.0
 */
public class NamesInputView extends View {

  // ---------------------------------------- Private constants ---------------------------------

  private static final int TEXT_AREA_ROWS = 14;
  private static final int TEXT_AREA_COLUMNS = 9;

  // ---------------------------------------- Private fields ------------------------------------

  private JButton mixButton;
  private JButton backButton;

  private JCheckBox anchoragesCheckbox;

  private JComboBox<String> comboBox;

  private JPanel leftPanel;
  private JPanel leftTopPanel;
  private JPanel leftBottomPanel;
  private JPanel rightPanel;

  private JRadioButton radioButtonByRatings;
  private JRadioButton radioButtonRandom;

  private JTextArea textArea;

  private Map<Position, List<JTextField>> textFieldsMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the names input view.
   */
  public NamesInputView() {
    super("Ingreso de jugadores", String.join(" ", Constants.MIG_LAYOUT_WRAP, "2"));

    leftPanel = new JPanel(new MigLayout(Constants.MIG_LAYOUT_WRAP, Constants.MIG_LAYOUT_GROW));
    leftTopPanel = new JPanel(new MigLayout(Constants.MIG_LAYOUT_WRAP));
    leftBottomPanel = new JPanel(new MigLayout(Constants.MIG_LAYOUT_WRAP));
    rightPanel = new JPanel(new MigLayout(Constants.MIG_LAYOUT_WRAP));

    initializeTextFieldsMap();
    initializeInterface();
  }

  // ---------------------------------------- Protected methods ---------------------------------

  @Override
  protected void initializeInterface() {
    leftPanel.add(
        leftTopPanel,
        CommonFunctions.buildMigLayoutConstraints(
          Constants.MIG_LAYOUT_GROWY,
          Constants.MIG_LAYOUT_PUSHY
        )
    );
    leftPanel.add(leftBottomPanel, Constants.MIG_LAYOUT_SOUTH);
    getMasterPanel().add(leftPanel, Constants.MIG_LAYOUT_WEST);
    getMasterPanel().add(rightPanel, Constants.MIG_LAYOUT_EAST);
    addComboBox();
    addTextFields();
    addRadioButtons();
    addAnchoragesCheckbox();
    addTextArea();
    addButtons();
    add(getMasterPanel());
    pack();
  }

  @Override
  protected void addButtons() {
    mixButton = new CustomButton("Distribuir", Constants.ROUNDED_BORDER_ARC_GENERAL);
    mixButton.setEnabled(false);
    mixButton.addActionListener(e ->
        ((NamesInputController) CommonFunctions.getController(ProgramView.NAMES_INPUT))
        .mixButtonEvent(CommonFunctions.getComponentFromEvent(e))
    );

    backButton = new CustomButton("Atrás", Constants.ROUNDED_BORDER_ARC_GENERAL);
    backButton.addActionListener(e ->
        ((NamesInputController) CommonFunctions.getController(ProgramView.NAMES_INPUT))
        .backButtonEvent()
    );

    rightPanel.add(mixButton, Constants.MIG_LAYOUT_GROW);
    rightPanel.add(backButton, Constants.MIG_LAYOUT_GROW);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the combobox.
   */
  private void addComboBox() {
    comboBox = new CustomComboBox<>(Constants.OPTIONS_POSITIONS_COMBOBOX
                                             .toArray(new String[0]));

    comboBox.addActionListener(e ->
        ((NamesInputController) CommonFunctions.getController(ProgramView.NAMES_INPUT))
                                               .comboBoxEvent(
                                                 (String) Objects.requireNonNull(
                                                   ((JComboBox<?>) e.getSource()).getSelectedItem()
                                                 )
                                               )
    );

    leftTopPanel.add(comboBox, Constants.MIG_LAYOUT_GROWX);
  }

  /**
   * Adds the read-only text area where the entered player names will be displayed in real time.
   */
  private void addTextArea() {
    textArea = new CustomTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS);

    rightPanel.add(
        new CustomScrollPane(textArea),
        CommonFunctions.buildMigLayoutConstraints(
          Constants.MIG_LAYOUT_PUSH,
          Constants.MIG_LAYOUT_GROW,
          Constants.MIG_LAYOUT_SPAN
        )
    );
  }

  /**
   * Adds the anchorages enablement checkbox.
   */
  private void addAnchoragesCheckbox() {
    anchoragesCheckbox = new CustomCheckBox("Anclar jugadores");

    anchoragesCheckbox.addActionListener(
        e -> CommonFields.setAnchoragesEnabled(!CommonFields.isAnchoragesEnabled())
    );

    leftBottomPanel.add(anchoragesCheckbox, Constants.MIG_LAYOUT_GROWX);
  }

  /**
   * Builds, stores and configures each position text fields.
   */
  private void addTextFields() {
    for (Position position : Position.values()) {
      int totalPlayersInPosition = CommonFields.getPlayersAmountMap()
                                               .get(position) * 2;

      for (int i = 0; i < totalPlayersInPosition; i++) {
        JTextField tf = new CustomTextField();

        tf.addActionListener(e -> {
              /*
               * If the entered text is both a valid string and name, it will be applied to the
               * corresponding player. If not, an error message will be shown and the text field
               * will be reset to the player name.
               */
              try {
                ((NamesInputController) CommonFunctions.getController(ProgramView.NAMES_INPUT))
                                                       .textFieldEvent(textFieldsMap.get(position)
                                                                                    .indexOf(tf),
                                                                       CommonFields.getPlayersSets()
                                                                                   .get(position),
                                                                       tf.getText());
              } catch (IllegalArgumentException | InvalidNameException ex) {
                CommonFunctions.showErrorMessage(
                    ex instanceof IllegalArgumentException ? Constants.MSG_ERROR_INVALID_STRING
                                                           : Constants.MSG_ERROR_INVALID_NAME,
                    CommonFunctions.getComponentFromEvent(e)
                );

                tf.setText(
                    CommonFields.getPlayersSets()
                                .get(position)
                                .get(textFieldsMap.get(position)
                                                  .indexOf(tf))
                                .getName()
                );
              }
            }
        );

        textFieldsMap.get(position)
                     .add(tf);
      }
    }
  }

  /**
   * Initializes the text fields map.
   */
  private void initializeTextFieldsMap() {
    textFieldsMap = new EnumMap<>(Position.class);

    for (Position position : Position.values()) {
      textFieldsMap.put(position, new ArrayList<>());
    }
  }

  /**
   * Adds the radio buttons to choose the players distribution method.
   *
   * <p>The "java:S1612" warning is suppressed because of how the suggested method reference deals
   * with the triggered event.
   *
   * <p>When using lambda expressions, the event handler is called whenever the event is triggered.
   * This means that the controller is retrieved only when the radio buttons are clicked, avoiding
   * null-reference problems.
   *
   * <p>When using a method reference (::radioButtonEvent), a radioButtonEvent method reference is
   * created when the view is being builded. This means that the controller should be retrieved when
   * the method reference is created, and it could be before any radio button click event is
   * triggered, meaning it could potentially cause null-reference problems since the view must be
   * fully created before the controller can be created. This is method reference causes a cyclic
   * dependence between the view and the controller.
   *
   * <p>The event handler could be written in this class, but for the sake of the MVC design pattern
   * the controller should be the responsible for events handling.
   */
  @SuppressWarnings("java:S1612")
  private void addRadioButtons() {
    radioButtonRandom = new CustomRadioButton("Aleatoria");
    radioButtonRandom.addItemListener(e ->
        ((NamesInputController) CommonFunctions.getController(ProgramView.NAMES_INPUT))
        .radioButtonEvent(e)
    );

    radioButtonByRatings = new CustomRadioButton("Por puntajes");
    radioButtonByRatings.addItemListener(e ->
        ((NamesInputController) CommonFunctions.getController(ProgramView.NAMES_INPUT))
        .radioButtonEvent(e)
    );

    leftBottomPanel.add(
        new CustomLabel("DISTRIBUCIÓN", SwingConstants.LEFT),
        CommonFunctions.buildMigLayoutConstraints(
          Constants.MIG_LAYOUT_GROWX,
          Constants.MIG_LAYOUT_PUSHX
        )
    );
    leftBottomPanel.add(radioButtonRandom);
    leftBottomPanel.add(radioButtonByRatings);
    leftBottomPanel.add(
        new CustomSeparator(),
        CommonFunctions.buildMigLayoutConstraints(
          Constants.MIG_LAYOUT_GROWX,
          Constants.MIG_LAYOUT_PUSHX
        )
    );
  }

  // ---------------------------------------- Getters -------------------------------------------

  public JButton getBackButton() {
    return backButton;
  }

  public JButton getMixButton() {
    return mixButton;
  }

  public JCheckBox getAnchoragesCheckbox() {
    return anchoragesCheckbox;
  }

  public JComboBox<String> getComboBox() {
    return comboBox;
  }

  public JPanel getLeftPanel() {
    return leftPanel;
  }

  public JPanel getLeftTopPanel() {
    return leftTopPanel;
  }

  public JPanel getLeftBottomPanel() {
    return leftBottomPanel;
  }

  public JPanel getRightPanel() {
    return rightPanel;
  }

  public JRadioButton getRadioButtonByRatings() {
    return radioButtonByRatings;
  }

  public JRadioButton getRadioButtonRandom() {
    return radioButtonRandom;
  }

  public JTextArea getTextArea() {
    return textArea;
  }

  public Map<Position, List<JTextField>> getTextFieldsMap() {
    return textFieldsMap;
  }

  // ---------------------------------------- Setters -------------------------------------------

  public void setBackButton(JButton backButton) {
    this.backButton = backButton;
  }

  public void setMixButton(JButton mixButton) {
    this.mixButton = mixButton;
  }

  public void setAnchoragesCheckbox(JCheckBox anchoragesCheckbox) {
    this.anchoragesCheckbox = anchoragesCheckbox;
  }

  public void setComboBox(JComboBox<String> comboBox) {
    this.comboBox = comboBox;
  }

  public void setLeftPanel(JPanel leftPanel) {
    this.leftPanel = leftPanel;
  }

  public void setLeftTopPanel(JPanel leftTopPanel) {
    this.leftTopPanel = leftTopPanel;
  }

  public void setLeftBottomPanel(JPanel leftBottomPanel) {
    this.leftBottomPanel = leftBottomPanel;
  }

  public void setRightPanel(JPanel rightPanel) {
    this.rightPanel = rightPanel;
  }

  public void setTextArea(JTextArea textArea) {
    this.textArea = textArea;
  }

  public void setTextFieldsMap(Map<Position, List<JTextField>> textFieldsMap) {
    this.textFieldsMap = textFieldsMap;
  }

  public void setRadioButtonByRatings(JRadioButton radioButtonByRatings) {
    this.radioButtonByRatings = radioButtonByRatings;
  }

  public void setRadioButtonRandom(JRadioButton radioButtonRandom) {
    this.radioButtonRandom = radioButtonRandom;
  }
}