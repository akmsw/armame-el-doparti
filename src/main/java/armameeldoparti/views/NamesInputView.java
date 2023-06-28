package armameeldoparti.views;

import armameeldoparti.controllers.NamesInputController;
import armameeldoparti.models.Position;
import armameeldoparti.models.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.naming.InvalidNameException;
import javax.swing.BorderFactory;
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
    super("Ingreso de jugadores", "wrap 2");

    leftPanel = new JPanel(new MigLayout("wrap"));
    rightPanel = new JPanel(new MigLayout("wrap"));

    initializeTextFieldsMap();
    initializeInterface();
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Initializes the view and makes it visible.
   */
  @Override
  protected void initializeInterface() {
    getMasterPanel().add(leftPanel, "west");
    getMasterPanel().add(rightPanel, "east");

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle(getFrameTitle());
    setIconImage(Constants.ICON
                          .getImage());
    setResizable(false);
    addComboBox();
    addTextFields();
    addTextArea();
    addButtons();
    addAnchoragesCheckbox();
    add(getMasterPanel());
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

    rightPanel.add(mixButton, "grow");
    rightPanel.add(backButton, "grow");
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the combobox.
   */
  private void addComboBox() {
    comboBox = new JComboBox<>(OPTIONS_COMBOBOX);

    comboBox.setSelectedIndex(0);
    comboBox.addActionListener(e ->
        ((NamesInputController) CommonFunctions.getController(ProgramView.NAMES_INPUT))
                                               .comboBoxEvent((String) Objects.requireNonNull(
                                                 ((JComboBox<?>) e.getSource()).getSelectedItem())
                                               )
    );

    leftPanel.add(comboBox, "growx");
  }

  /**
   * Adds the read-only text area where the entered player names will be displayed in real time.
   */
  private void addTextArea() {
    textArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS);

    textArea.setBorder(BorderFactory.createBevelBorder(1));
    textArea.setEditable(false);

    rightPanel.add(textArea, "push, grow, span");
  }

  /**
   * Adds the anchorages enablement checkbox.
   */
  private void addAnchoragesCheckbox() {
    anchoragesCheckbox = new JCheckBox("Anclar jugadores", false);

    anchoragesCheckbox.addActionListener(
        e -> CommonFields.setAnchoragesEnabled(!CommonFields.isAnchoragesEnabled())
    );

    rightPanel.add(anchoragesCheckbox, "center");
  }

  /**
   * Builds, stores and configures each position text fields.
   */
  private void addTextFields() {
    for (Position position : Position.values()) {
      int totalPlayersInPosition = CommonFields.getPlayersAmountMap()
                                               .get(position) * 2;

      for (int i = 0; i < totalPlayersInPosition; i++) {
        JTextField tf = new JTextField();

        tf.addActionListener(e -> {
              try {
                ((NamesInputController) CommonFunctions.getController(ProgramView.NAMES_INPUT))
                                                       .textFieldEvent(textFieldsMap.get(position)
                                                                                    .indexOf(tf),
                                                                       CommonFields.getPlayersSets()
                                                                                   .get(position),
                                                                       tf.getText());
              } catch (IllegalArgumentException stringEx) {
                CommonFunctions.showErrorMessage(Constants.MSG_ERROR_INVALID_STRING,
                                                 CommonFunctions.getComponentFromEvent(e));

                tf.setText("");
              } catch (InvalidNameException nameEx) {
                CommonFunctions.showErrorMessage(Constants.MSG_ERROR_INVALID_NAME,
                                                 CommonFunctions.getComponentFromEvent(e));

                tf.setText("");
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