package armameeldoparti.views;

import armameeldoparti.abstractclasses.View;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.utils.Main;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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

  private static final String FRAME_TITLE = "Ingreso de jugadores";
  private static final String PDA_DATA_RETRIEVE_REGEX = "[CLMFG].+>.+";
  private static final String PDA_FILENAME = "dist.pda";

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
    getPlayersDistributionData();
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
  public Map<Position, List<JTextField>> getTextFieldsMap() {
    return textFieldsMap;
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Initializes the view and makes it visible.
   */
  @Override
  protected void initializeInterface() {
    textFieldsMap = new EnumMap<>(Position.class);

    for (Position position : Position.values()) {
      List<Player> playersSet = new ArrayList<>();

      initializeSet(playersSet, position, Main.getPlayersAmountMap()
                                              .get(position) * 2);

      Main.getPlayersSets()
          .put(position, playersSet);

      textFieldsMap.put(position, new ArrayList<>());
    }

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
        Main.getNamesInputController()
            .mixButtonEvent()
    );

    backButton.addActionListener(e ->
        Main.getNamesInputController()
            .backButtonEvent()
    );

    rightPanel.add(mixButton, "grow");
    rightPanel.add(backButton, "grow");
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Fills the set with players without name and skill points, with the specified position.
   *
   * @param set      Players set to initialize.
   * @param position Players position.
   * @param capacity Set's initial capacity.
   */
  private void initializeSet(List<Player> set, Position position, int capacity) {
    for (int i = 0; i < capacity; i++) {
      set.add(new Player("", position));
    }
  }

  /**
   * Adds the combobox.
   */
  private void addComboBox() {
    comboBox = new JComboBox<>(OPTIONS_COMBOBOX);

    comboBox.setSelectedIndex(0);
    comboBox.addActionListener(e ->
        Main.getNamesInputController()
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
    for (Position position : Position.values()) {
      for (int i = 0; i < Main.getPlayersAmountMap()
                              .get(position) * 2; i++) {
        JTextField tf = new JTextField();

        tf.addActionListener(e ->
          Main.getNamesInputController()
              .textFieldEvent(textFieldsMap.get(position), Main.getPlayersSets()
                                                               .get(position),
                              tf, (JTextField) e.getSource())
        );

        textFieldsMap.get(position)
                     .add(tf);
      }
    }
  }

  /**
   * Gets the number of players for each position per team using regular expressions.
   *
   * <p>[CLMFG].+>.+ : Retrieves the lines that start with C, L, M, F, or W,
   * followed by at least one '>' character (these are the lines that matters in the
   * .pda file).
   *
   * <p>(?!(?<=X)\\d). : Gets the part of the line that is not a number that we are
   * interested in (the number would take the place of the X).
   *
   * <p>If the .pda file is modified in terms of the order of the important lines,
   * it must be taken into account that Position.values()[index] trusts that what is found
   * corresponds to the order in which the values in the Position enum are declared.
   * Idem, if the order of the Position enum values are changed, it should be noted that
   * Position.values()[index] trusts the order in which the data will be retrieved from the
   * .pda file and, therefore, you should review the order of the important lines in the file.
   */
  private void getPlayersDistributionData() {
    BufferedReader buff = new BufferedReader(
        new InputStreamReader(getClass().getClassLoader()
                                        .getResourceAsStream(Main.DOCS_PATH + PDA_FILENAME))
    );

    var wrapperIndex = new Object() {
      private int index;
    };

    buff.lines()
        .forEach(l -> {
          if (l.matches(PDA_DATA_RETRIEVE_REGEX)) {
            Main.getPlayersAmountMap()
                .put(Position.values()[wrapperIndex.index],
                    Integer.parseInt(l.replaceAll("(?!(?<="
                                                  + Main.PLAYERS_PER_TEAM
                                                  + ")\\d).", "")));

            wrapperIndex.index++;
          }
        });
  }
}