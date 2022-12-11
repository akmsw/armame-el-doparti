package armameeldoparti.views;

import armameeldoparti.controllers.NamesInputController;
import armameeldoparti.models.Position;
import armameeldoparti.models.Views;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.naming.InvalidNameException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import net.miginfocom.swing.MigLayout;

/**
 * Names input view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 28/02/2021
 */
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

  private JCheckBox anchoragesCheckBox;

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
    super("Ingreso de jugadores");
    initializeTextFieldsMap();
    initializeInterface();
  }

  // ---------------------------------------- Public methods ------------------------------------

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Gets the 'mix' button.
   *
   * @return The 'mix' button.
   */
  public JButton getMixButton() {
    return mixButton;
  }

  /**
   * Gets the anchorages checkbox.
   *
   * @return The anchorages checkbox.
   */
  public JCheckBox getAnchoragesCheckBox() {
    return anchoragesCheckBox;
  }

  /**
   * Gets the combobox.
   *
   * @return The combobox.
   */
  public JComboBox<String> getComboBox() {
    return comboBox;
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
   * Gets the view's left panel.
   *
   * @return The view's left panel.
   */
  public JPanel getLeftPanel() {
    return leftPanel;
  }

  /**
   * Gets the combobox options.
   *
   * @return The combobox options.
   */
  public String[] getComboBoxOptions() {
    return OPTIONS_COMBOBOX;
  }

  /**
   * Gets the map that associates each text fields set with its
   * corresponding position.
   *
   * @return The map that associates each text fields set with its
   *         corresponding position.
   */
  public Map<Position, List<JTextField>> getTextFieldsMap() {
    return textFieldsMap;
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Initializes the view and makes it visible.
   */
  @Override
  protected void initializeInterface() {
    leftPanel = new JPanel(new MigLayout("wrap"));
    rightPanel = new JPanel(new MigLayout("wrap"));

    JPanel masterPanel = new JPanel(new MigLayout("wrap 2"));

    masterPanel.add(leftPanel, "west");
    masterPanel.add(rightPanel, "east");

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle(getFrameTitle());
    setIconImage(Constants.ICON
                          .getImage());
    setResizable(false);
    addComboBox();
    addTextFields();
    addTextArea();
    addButtons();
    addAnchoragesCheckBox();
    add(masterPanel);
    pack();
    setLocationRelativeTo(null);
  }

  /**
   * Adds the buttons to their corresponding panel.
   */
  @Override
  protected void addButtons() {
    mixButton = new JButton("Distribuir");

    mixButton.setEnabled(false);
    mixButton.addActionListener(e ->
        ((NamesInputController) CommonFunctions.getController(Views.NAMES_INPUT)).mixButtonEvent()
    );

    JButton backButton = new JButton("Atrás");

    backButton.addActionListener(e ->
        ((NamesInputController) CommonFunctions.getController(Views.NAMES_INPUT)).backButtonEvent()
    );

    rightPanel.add(mixButton, Constants.GROW);
    rightPanel.add(backButton, Constants.GROW);
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the combobox.
   */
  private void addComboBox() {
    comboBox = new JComboBox<>(OPTIONS_COMBOBOX);

    comboBox.setSelectedIndex(0);
    comboBox.addActionListener(e ->
        ((NamesInputController) CommonFunctions.getController(Views.NAMES_INPUT))
        .comboBoxEvent((String) ((JComboBox<?>) e.getSource()).getSelectedItem())
    );

    leftPanel.add(comboBox, Constants.GROWX);
  }

  /**
   * Adds the read-only text area where the entered players names will be displayed
   * in real time.
   */
  private void addTextArea() {
    textArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS);

    textArea.setBorder(BorderFactory.createBevelBorder(1));
    textArea.setEditable(false);

    rightPanel.add(textArea, "push, grow, span");
  }

  /**
   * Adds the anchorages checkbox.
   */
  private void addAnchoragesCheckBox() {
    anchoragesCheckBox = new JCheckBox("Anclar jugadores", false);

    anchoragesCheckBox.addActionListener(e -> CommonFields.setAnchorages(
                                              !CommonFields.thereAreAnchorages()));

    rightPanel.add(anchoragesCheckBox, "center");
  }

  /**
   * Builds, stores and configures each position text fields.
   */
  private void addTextFields() {
    for (Position position : Position.values()) {
      for (int i = 0; i < CommonFields.getPlayersAmountMap()
                                      .get(position) * 2; i++) {
        JTextField tf = new JTextField();

        tf.addActionListener(e -> {
              try {
                ((NamesInputController) CommonFunctions.getController(Views.NAMES_INPUT))
                                                       .textFieldEvent(textFieldsMap.get(position)
                                                                                    .indexOf(tf),
                                                                       CommonFields.getPlayersSets()
                                                                                   .get(position),
                                                                       tf.getText());
              } catch (IllegalArgumentException stringEx) {
                CommonFunctions.showErrorMessage("El nombre del jugador debe estar formado "
                                                 + "por letras de la A a la Z");

                tf.setText("");

                return;
              } catch (InvalidNameException nameEx) {
                CommonFunctions.showErrorMessage("El nombre del jugador no puede estar vacío,"
                                                 + " tener más de " + Constants.MAX_NAME_LEN
                                                 + " caracteres, o estar repetido");

                tf.setText("");

                return;
              }
            }
        );

        textFieldsMap.get(position)
                     .add(tf);
      }
    }
  }

  /**
   * Initializes the textfields map.
   */
  private void initializeTextFieldsMap() {
    textFieldsMap = new EnumMap<>(Position.class);

    for (Position position : Position.values()) {
      textFieldsMap.put(position, new ArrayList<>());
    }
  }
}