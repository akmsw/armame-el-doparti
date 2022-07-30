package armameeldoparti.views;

import armameeldoparti.abstracts.View;
import armameeldoparti.models.Player;
import armameeldoparti.models.Position;
import armameeldoparti.utils.Main;
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
 * Clase correspondiente a la ventana de anclaje de jugadores.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 15/03/2021
 */
public class AnchoragesView extends View {

  // ---------------------------------------- Constantes privadas -------------------------------

  private static final String GROW = "grow";
  private static final String GROWX_SPAN = "growx, span";
  private static final String FRAME_TITLE = "Anclaje de jugadores";

  // ---------------------------------------- Campos privados -----------------------------------

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

  private Map<Position, List<JCheckBox>> checkBoxesMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye una ventana de anclajes.
   */
  public AnchoragesView() {
    cdCheckboxes = new ArrayList<>();
    ldCheckboxes = new ArrayList<>();
    mfCheckboxes = new ArrayList<>();
    fwCheckboxes = new ArrayList<>();
    gkCheckboxes = new ArrayList<>();

    checkBoxesMap = new EnumMap<>(Position.class);

    checkBoxesMap.put(Position.CENTRAL_DEFENDER, cdCheckboxes);
    checkBoxesMap.put(Position.LATERAL_DEFENDER, ldCheckboxes);
    checkBoxesMap.put(Position.MIDFIELDER, mfCheckboxes);
    checkBoxesMap.put(Position.FORWARD, fwCheckboxes);
    checkBoxesMap.put(Position.GOALKEEPER, gkCheckboxes);

    initializeInterface();
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  /**
   * Actualiza las casillas con los nombres de los jugadores.
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
   * Obtiene el botón de finalización.
   *
   * @return El botón de finalización.
   */
  public JButton getFinishButton() {
    return finishButton;
  }

  /**
   * Obtiene el botón de nuevo anclaje.
   *
   * @return El botón de nuevo anclaje.
   */
  public JButton getNewAnchorageButton() {
    return newAnchorageButton;
  }

  /**
   * Obtiene el botón de borrado de un anclaje.
   *
   * @return El botón de borrado de un anclaje.
   */
  public JButton getDeleteAnchorageButton() {
    return deleteAnchorageButton;
  }

  /**
   * Obtiene el botón de borrado del último anclaje.
   *
   * @return El botón de borrado del último anclaje.
   */
  public JButton getDeleteLastAnchorageButton() {
    return deleteLastAnchorageButton;
  }

  /**
   * Obtiene el botón de limpieza de anclajes.
   *
   * @return El botón de limpieza de anclajes.
   */
  public JButton getClearAnchoragesButton() {
    return clearAnchoragesButton;
  }

  /**
   * Obtiene el área de texto.
   *
   * @return El área de texto.
   */
  public JTextArea getTextArea() {
    return textArea;
  }

  /**
   * Obtiene el mapa de casillas de selección.
   *
   * @return El mapa de casillas de selección.
   */
  public Map<Position, List<JCheckBox>> getCheckBoxesMap() {
    return checkBoxesMap;
  }

  // ---------------------------------------- Métodos protegidos --------------------------------

  /**
   * Inicializa y muestra la interfaz gráfica de la ventana.
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
                  this.thumbColor = Main.DARK_GREEN;
                  this.trackColor = Main.MEDIUM_GREEN;
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
    setIconImage(Main.ICON.getImage());
    pack();
    setLocationRelativeTo(null);
  }

  /**
   * Coloca los botones en los paneles de la ventana.
   */
  @Override
  protected void addButtons() {
    finishButton = new JButton("Finalizar");

    finishButton.setEnabled(false);
    finishButton.addActionListener(e ->
        Main.getAnchoragesController()
            .finishButtonEvent()
    );

    newAnchorageButton = new JButton("Anclar");

    newAnchorageButton.addActionListener(e ->
        Main.getAnchoragesController()
            .newAnchorageButtonEvent()
    );

    deleteAnchorageButton = new JButton("Borrar un anclaje");

    deleteAnchorageButton.addActionListener(e ->
        Main.getAnchoragesController()
            .deleteAnchorageButtonEvent()
    );

    deleteLastAnchorageButton = new JButton("Borrar último anclaje");

    deleteLastAnchorageButton.addActionListener(e ->
        Main.getAnchoragesController()
            .deleteLastAnchorageButtonEvent()
    );

    clearAnchoragesButton = new JButton("Limpiar anclajes");

    clearAnchoragesButton.addActionListener(e ->
        Main.getAnchoragesController()
            .clearAnchoragesButtonEvent()
    );

    JButton backButton = new JButton("Atrás");

    backButton.addActionListener(e ->
        Main.getAnchoragesController()
            .backButtonEvent()
    );

    leftPanel.add(finishButton, GROWX_SPAN);
    leftPanel.add(backButton, GROWX_SPAN);

    rightPanel.add(scrollPane, "span2, push, grow");
    rightPanel.add(newAnchorageButton, GROW);
    rightPanel.add(deleteAnchorageButton, GROW);
    rightPanel.add(deleteLastAnchorageButton, GROW);
    rightPanel.add(clearAnchoragesButton, GROW);
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Llena los arreglos de casillas correspondientes a cada posición.
   *
   * @param playersSet Conjunto de jugadores de donde obtener los nombres.
   * @param cbSet      Conjunto de casillas a llenar.
   */
  private void fillCheckboxesSet(List<Player> playersSet, List<JCheckBox> cbSet) {
    playersSet.forEach(p -> cbSet.add(new JCheckBox(p.getName())));
  }

  /**
   * Coloca en el panel las casillas correspondientes a cada posición
   * junto con una etiqueta que los distinga.
   *
   * @param cbSet Conjunto de casillas a colocar.
   * @param title Texto de la etiqueta de acompañamiento.
   */
  private void addCheckboxesSet(List<JCheckBox> cbSet, String title) {
    JLabel label = new JLabel(title);

    label.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

    leftPanel.add(label, GROWX_SPAN);

    cbSet.forEach(cb -> leftPanel.add(cb, "align left, pushx"));
  }
}