package armameeldoparti.views;

import armameeldoparti.controllers.NamesInputController;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.CustomComboBox;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

/**
 * Names input view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since v3.0
 */
@Getter
public class NamesInputView extends View {

  // ---------------------------------------- Private constants ---------------------------------

  private static final int TEXT_AREA_ROWS = 14;
  private static final int TEXT_AREA_COLUMNS = 12;

  private static final String[] OPTIONS_COMBOBOX = {
    "Defensores centrales",
    "Defensores laterales",
    "Mediocampistas",
    "Delanteros",
    "Arqueros"
  };

  // ---------------------------------------- Private fields ------------------------------------

  private JButton mixButton;

  private JCheckBox anchoragesCheckbox;

  private JComboBox<String> comboBox;

  private JPanel leftPanel;
  private JPanel rightPanel;

  private JTextArea textArea;

  private Map<Position, List<JTextField>> textFieldsMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the names input view.
   */
  public NamesInputView() {
    super("Ingreso de jugadores", String.join(" ", Constants.MIG_LAYOUT_WRAP, "2"));

    leftPanel = new JPanel(new MigLayout(Constants.MIG_LAYOUT_WRAP));
    rightPanel = new JPanel(new MigLayout(Constants.MIG_LAYOUT_WRAP));

    initializeTextFieldsMap();
    initializeInterface();
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Initializes the view and makes it visible.
   */
  @Override
  protected void initializeInterface() {
    getMasterPanel().add(leftPanel, Constants.MIG_LAYOUT_WEST);
    getMasterPanel().add(rightPanel, Constants.MIG_LAYOUT_EAST);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle(getFrameTitle());
    setResizable(false);
    addComboBox();
    addTextFields();
    addTextArea();
    addButtons();
    addAnchoragesCheckbox();
    add(getMasterPanel());
    setIconImage(Constants.ICON
                          .getImage());
    pack();
  }

  /**
   * Adds the buttons to their corresponding panel.
   */
  @Override
  protected void addButtons() {
    mixButton = new JButton("Distribuir");
    mixButton.setEnabled(false);
    mixButton.addActionListener(e ->
        ((NamesInputController) CommonFunctions.getController(ProgramView.NAMES_INPUT))
        .mixButtonEvent(CommonFunctions.getComponentFromEvent(e))
    );

    JButton backButton = new JButton("Atrás");
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
    comboBox = new CustomComboBox<>(OPTIONS_COMBOBOX);

    comboBox.setSelectedIndex(0);
    comboBox.addActionListener(e ->
        ((NamesInputController) CommonFunctions.getController(ProgramView.NAMES_INPUT))
                                               .comboBoxEvent((String) Objects.requireNonNull(
                                                 ((JComboBox<?>) e.getSource()).getSelectedItem())
                                               )
    );

    leftPanel.add(comboBox, Constants.MIG_LAYOUT_GROWX);
  }

  /**
   * Adds the read-only text area where the entered player names will be displayed in real time.
   */
  private void addTextArea() {
    textArea = new CustomTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS, false);

    rightPanel.add(textArea, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_PUSH,
                                                                       Constants.MIG_LAYOUT_GROW,
                                                                       Constants.MIG_LAYOUT_SPAN));
  }

  /**
   * Adds the anchorages enablement checkbox.
   */
  private void addAnchoragesCheckbox() {
    anchoragesCheckbox = new JCheckBox("Anclar jugadores", false);

    anchoragesCheckbox.addActionListener(
        e -> CommonFields.setAnchoragesEnabled(!CommonFields.isAnchoragesEnabled())
    );

    rightPanel.add(anchoragesCheckbox, Constants.MIG_LAYOUT_CENTER);
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
}