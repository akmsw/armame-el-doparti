package armameeldoparti.views;

import armameeldoparti.Main;
import armameeldoparti.controllers.NamesInputController;
import armameeldoparti.models.Positions;
import armameeldoparti.models.Views;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.naming.InvalidNameException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
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

  private static final String FRAME_TITLE = "Ingreso de jugadores";

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

  private Map<Positions, List<JTextField>> textFieldsMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the names input view.
   */
  public NamesInputView() {
    textFieldsMap = new EnumMap<>(Positions.class);

    for (Positions position : Positions.values()) {
      textFieldsMap.put(position, new ArrayList<>());
    }

    initializeInterface();
  }

  // ---------------------------------------- Public methods -------------------------------------

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
  public Map<Positions, List<JTextField>> getTextFieldsMap() {
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
    setTitle(FRAME_TITLE);
    setIconImage(Main.ICON.getImage());
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

    JButton backButton = new JButton("Atrás");

    mixButton.setEnabled(false);

    mixButton.addActionListener(e ->
        ((NamesInputController) Main.getController(Views.NAMES_INPUT)).mixButtonEvent()
    );

    backButton.addActionListener(e ->
        ((NamesInputController) Main.getController(Views.NAMES_INPUT)).backButtonEvent()
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
        ((NamesInputController) Main.getController(Views.NAMES_INPUT))
            .comboBoxEvent((String) ((JComboBox<?>) e.getSource()).getSelectedItem())
    );

    leftPanel.add(comboBox, "growx");
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

    anchoragesCheckBox.addActionListener(e -> Main.setAnchorages(!Main.thereAreAnchorages()));

    rightPanel.add(anchoragesCheckBox, "center");
  }

  /**
   * Builds, stores and configures each position text fields.
   */
  private void addTextFields() {
    for (Positions position : Positions.values()) {
      for (int i = 0; i < Main.getPlayersAmountMap()
                              .get(position) * 2; i++) {
        JTextField tf = new JTextField();

        tf.addActionListener(e -> {
              try {
                ((NamesInputController) Main.getController(Views.NAMES_INPUT))
                    .textFieldEvent(textFieldsMap.get(position)
                                                 .indexOf(tf),
                                    Main.getPlayersSets()
                                        .get(position),
                                    tf.getText());
              } catch (IllegalArgumentException stringEx) {
                JOptionPane.showMessageDialog(null,
                                              "El nombre del jugador debe estar formado por letras"
                                              + " de la A a la Z", "¡Error!",
                                              JOptionPane.ERROR_MESSAGE, null);

                tf.setText("");

                return;
              } catch (InvalidNameException nameEx) {
                JOptionPane.showMessageDialog(null,
                                              "El nombre del jugador no puede estar vacío,"
                                              + " tener más de " + Main.MAX_NAME_LEN
                                              + " caracteres, o estar repetido",
                                              "¡Error!", JOptionPane.ERROR_MESSAGE, null);

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
}