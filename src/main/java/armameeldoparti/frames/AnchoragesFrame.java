package armameeldoparti.frames;

import armameeldoparti.utils.BackButton;
import armameeldoparti.utils.Main;
import armameeldoparti.utils.Player;
import armameeldoparti.utils.Position;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
public class AnchoragesFrame extends JFrame {

  // ---------------------------------------- Constantes privadas -------------------------------

  private static final String GROW = "grow";
  private static final String GROWX_SPAN = "growx, span";

  private static final String FRAME_TITLE = "Anclaje de jugadores";

  // ---------------------------------------- Campos privados -----------------------------------

  private int maxPlayersPerAnchorage;
  private int anchoragesAmount;
  private int playersAnchored;

  private List<JCheckBox> cdCheckboxes;
  private List<JCheckBox> ldCheckboxes;
  private List<JCheckBox> mfCheckboxes;
  private List<JCheckBox> fwCheckboxes;
  private List<JCheckBox> gkCheckboxes;

  private JButton finishButton;
  private JButton newAnchorageButton;
  private JButton clearAnchoragesButton;
  private JButton deleteAnchorageButton;
  private JButton deleteLastAnchorageButton;

  private JFrame previousFrame;

  private JPanel leftPanel;
  private JPanel rightPanel;

  private JScrollPane scrollPane;

  private JTextArea textArea;

  private Map<Position, List<JCheckBox>> checkBoxesMap;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye una ventana de anclajes.
   *
   * @param previousFrame Ventana cuya visibilidad será conmutada.
   * @param playersAmount Cantidad de jugadores por equipo.
   */
  public AnchoragesFrame(JFrame previousFrame, int playersAmount) {
    this.previousFrame = previousFrame;

    maxPlayersPerAnchorage = playersAmount - 1;

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

    anchoragesAmount = 0;
    playersAnchored = 0;

    initializeInterface();
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Inicializa y muestra la interfaz gráfica de esta ventana.
   */
  private void initializeInterface() {
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
    updateTextArea();
    toggleButtons();
    setTitle(FRAME_TITLE);
    setResizable(false);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(MainFrame.ICON.getImage());
    add(masterPanel);
    pack();
    setLocationRelativeTo(null);
  }

  /**
   * Coloca los botones en los paneles de la ventana.
   */
  private void addButtons() {
    finishButton = new JButton("Finalizar");

    newAnchorageButton = new JButton("Anclar");
    deleteAnchorageButton = new JButton("Borrar un anclaje");
    deleteLastAnchorageButton = new JButton("Borrar último anclaje");
    clearAnchoragesButton = new JButton("Limpiar anclajes");

    finishButton.setEnabled(false);

    BackButton backButton = new BackButton(this, previousFrame, null);

    backButton.addActionListener(e -> {
      clearAnchorages();
      updateTextArea();
      toggleButtons();

      previousFrame.setVisible(true);

      dispose();
    });

    finishButton.addActionListener(e -> {
      if (!validAnchoragesCombination()) {
        showErrMsg("Error msg");
      } else {
        finish();
      }
    });

    newAnchorageButton.addActionListener(e -> {
      newAnchorage();
      updateTextArea();
      toggleButtons();
    });

    deleteLastAnchorageButton.addActionListener(e -> {
      deleteAnchorage(anchoragesAmount);
      updateTextArea();
      toggleButtons();
    });

    clearAnchoragesButton.addActionListener(e -> {
      clearAnchorages();
      updateTextArea();
      toggleButtons();
    });

    deleteAnchorageButton.addActionListener(e -> {
      String[] optionsDelete = new String[anchoragesAmount];

      for (int i = 0; i < anchoragesAmount; i++) {
        optionsDelete[i] = Integer.toString(i + 1);
      }

      int anchorageToDelete = JOptionPane.showOptionDialog(null,
                                                           "Seleccione qué anclaje desea borrar",
                                                           "Antes de continuar...", 2,
                                                           JOptionPane.QUESTION_MESSAGE,
                                                           MainFrame.SCALED_ICON, optionsDelete,
                                                           optionsDelete[0]);

      if (anchorageToDelete != JOptionPane.CLOSED_OPTION) {
        deleteAnchorage(anchorageToDelete + 1);
        updateTextArea();
        toggleButtons();
      }
    });

    leftPanel.add(finishButton, GROWX_SPAN);
    leftPanel.add(backButton, GROWX_SPAN);

    rightPanel.add(scrollPane, "span2, push, grow");
    rightPanel.add(newAnchorageButton, GROW);
    rightPanel.add(deleteAnchorageButton, GROW);
    rightPanel.add(deleteLastAnchorageButton, GROW);
    rightPanel.add(clearAnchoragesButton, GROW);
  }

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

  /**
   * Crea una nueva ventana de ingreso de puntuaciones si se requiere, o
   * una nueva ventana de muestra de resultados.
   *
   * <p>Aquellas casillas que quedaron seleccionadas cuyos jugadores no
   * fueron anclados, se deseleccionan.
   */
  private void finish() {
    checkBoxesMap.values()
                 .stream()
                 .flatMap(List::stream)
                 .filter(cb -> cb.isSelected() && cb.isVisible())
                 .forEach(cb -> cb.setSelected(false));

    if (Main.getDistribution() == 1) {
      // Distribución por puntuaciones
      SkillsInputFrame scoresInputFrame = new SkillsInputFrame(this);
      scoresInputFrame.setVisible(true);
    } else {
      // Distribución aleatoria
      ResultsFrame resultsFrame = new ResultsFrame(this);
      resultsFrame.setVisible(true);
    }

    setVisible(false);
    setLocationRelativeTo(null);
  }

  /**
   * Crea un nuevo anclaje en base a los jugadores correspondientes a las casillas
   * seleccionadas, corroborando las condiciones necesarias.
   */
  private void newAnchorage() {
    int playersToAnchorAmount = (int) checkBoxesMap.values()
                                                   .stream()
                                                   .flatMap(List::stream)
                                                   .filter(JCheckBox::isSelected)
                                                   .count();

    if (!validChecksAmount(playersToAnchorAmount)) {
      showErrMsg("No puede haber más de " + maxPlayersPerAnchorage
                 + " ni menos de 2 jugadores en un mismo anclaje");
      return;
    }

    if (!validCheckedPlayersPerPosition()) {
      showErrMsg("No puede haber más de la mitad de jugadores de una misma posición "
                 + "en un mismo anclaje");
      return;
    }

    if (!validAnchoredPlayersAmount(playersToAnchorAmount)) {
      showErrMsg("No puede haber más de " + 2 * maxPlayersPerAnchorage
                 + " jugadores anclados en total");
      return;
    }

    anchoragesAmount++;

    checkBoxesMap.values()
                 .stream()
                 .filter(cbs -> cbs.stream()
                                   .anyMatch(JCheckBox::isSelected))
                 .forEach(cbs -> setAnchorages(cbs, Main.getPlayersSets()
                                                        .get(getPositionFromCBSet(cbs))));
  }

  /**
   * Obtiene la posición correspondiente al conjunto de casillas
   * recibido por parámetro.
   *
   * @param cbSet Conjunto de casillas de las cuales se quiere obtener
   *              su posición asociada.
   *
   * @return Posición asociada al conjunto de casillas.
   */
  private Position getPositionFromCBSet(List<JCheckBox> cbSet) {
    return (Position) checkBoxesMap.entrySet()
                                   .stream()
                                   .filter(e -> e.getValue()
                                                 .equals(cbSet))
                                   .map(Map.Entry::getKey)
                                   .toArray()[0];
  }

  /**
   * Revisa si -.
   *
   * @return Si -.
   */
  private boolean validAnchoragesCombination() {
    // TODO
    return true;
  }

  /**
   * Cambia el número de anclaje de los jugadores deseados.
   *
   * <p>Si el nuevo anclaje a aplicar es 0 (se quiere borrar un anclaje),
   * entonces las casillas de los jugadores del anclaje objetivo se vuelven
   * a poner visibles y se decrementa el número de jugadores anclados en la
   * cantidad que corresponda.
   *
   * @param target      Anclaje a reemplazar.
   * @param replacement Nuevo anclaje a aplicar.
   */
  private void changeAnchorage(int target, int replacement) {
    Main.getPlayersSets()
        .values()
        .stream()
        .flatMap(List::stream)
        .filter(p -> p.getAnchor() == target)
        .forEach(p -> {
          p.setAnchor(replacement);

          if (replacement == 0) {
            checkBoxesMap.values()
                         .stream()
                         .flatMap(List::stream)
                         .filter(cb -> cb.getText()
                                         .equals(p.getName()))
                         .forEach(cb -> {
                           cb.setVisible(true);
                           playersAnchored--;
                         });
          }
        });
  }

  /**
   * Borra un anclaje específico elegido por el usuario.
   *
   * <p>Los que tenían ese anclaje, ahora tienen anclaje 0.
   * Si se desea borrar un anclaje que no sea el último, entonces
   * a los demás (desde el anclaje elegido + 1 hasta anchoragesAmount)
   * se les decrementa en 1 su número de anclaje.
   *
   * @param anchorageToDelete Número de anclaje a borrar.
   */
  private void deleteAnchorage(int anchorageToDelete) {
    for (int j = 0; j < checkBoxesMap.size(); j++) {
      changeAnchorage(anchorageToDelete, 0);
    }

    if (anchorageToDelete != anchoragesAmount) {
      for (int k = anchorageToDelete + 1; k <= anchoragesAmount; k++) {
        for (int j = 0; j < checkBoxesMap.size(); j++) {
          changeAnchorage(k, k - 1);
        }
      }
    }

    anchoragesAmount--;
  }

  /**
   * Borra todos los anclajes que se hayan generado.
   *
   * @see armameeldoparti.frames.AnchoragesFrame#deleteAnchorage(int)
   */
  private void clearAnchorages() {
    do {
      deleteAnchorage(anchoragesAmount);
    } while (anchoragesAmount > 0);
  }

  /**
   * Actualiza el área de texto mostrando la cantidad de anclajes
   * y los jugadores anclados a los mismos.
   */
  private void updateTextArea() {
    textArea.setText(null);

    var wrapperAnchorageNum = new Object() {
      private int anchorageNum;
    };

    var wrapperCounter = new Object() {
      private int counter = 1;
    };

    for (wrapperAnchorageNum.anchorageNum = 1; wrapperAnchorageNum.anchorageNum <= anchoragesAmount;
                                               wrapperAnchorageNum.anchorageNum++) {
      textArea.append(" ----- ANCLAJE #" + wrapperAnchorageNum.anchorageNum
                      + " -----" + System.lineSeparator());

      Main.getPlayersSets()
          .entrySet()
          .forEach(ps -> ps.getValue()
                           .stream()
                           .filter(p -> p.getAnchor() == wrapperAnchorageNum.anchorageNum)
                           .forEach(p -> {
                             textArea.append(" " + wrapperCounter.counter + ". "
                                             + p.getName() + System.lineSeparator());
                             wrapperCounter.counter++;
                           }));

      if (wrapperAnchorageNum.anchorageNum != anchoragesAmount) {
        textArea.append(System.lineSeparator());
      }

      wrapperCounter.counter = 1;
    }
  }

  /**
   * Conmuta la habilitación de los botones del panel derecho
   * y las casillas del panel izquierdo de la ventana.
   */
  private void toggleButtons() {
    if (anchoragesAmount > 0 && anchoragesAmount < 2) {
      finishButton.setEnabled(true);
      deleteAnchorageButton.setEnabled(false);
      deleteLastAnchorageButton.setEnabled(true);
      clearAnchoragesButton.setEnabled(true);
    } else if (anchoragesAmount >= 2) {
      deleteAnchorageButton.setEnabled(true);
      deleteLastAnchorageButton.setEnabled(true);
    } else {
      finishButton.setEnabled(false);
      deleteAnchorageButton.setEnabled(false);
      deleteLastAnchorageButton.setEnabled(false);
      clearAnchoragesButton.setEnabled(false);
    }

    if (2 * maxPlayersPerAnchorage - playersAnchored < 2) {
      newAnchorageButton.setEnabled(false);

      checkBoxesMap.values()
                   .stream()
                   .flatMap(List::stream)
                   .forEach(cb -> cb.setEnabled(!cb.isEnabled()));
    } else {
      newAnchorageButton.setEnabled(true);

      checkBoxesMap.values()
                   .stream()
                   .flatMap(List::stream)
                   .filter(cb -> !cb.isEnabled() && !cb.isSelected())
                   .forEach(cb -> cb.setEnabled(true));
    }
  }

  /**
   * Crea una ventana de error con un texto personalizado.
   *
   * @param errMsg Mensaje de error a mostrar en la ventana.
   */
  private void showErrMsg(String errMsg) {
    JOptionPane.showMessageDialog(null, errMsg, "¡Error!", JOptionPane.ERROR_MESSAGE, null);
  }

  /**
   * Revisa si el anclaje no contiene más de la mitad de jugadores de algún conjunto.
   *
   * @return Si el anclaje no contiene más de la mitad de jugadores de algún conjunto.
   */
  private boolean validCheckedPlayersPerPosition() {
    return checkBoxesMap.values()
                        .stream()
                        .noneMatch(cbs -> cbs.stream()
                                             .filter(JCheckBox::isSelected)
                                             .count() > cbs.size() / 2);
  }

  /**
   * Revisa si la cantidad de jugadores anclados es al menos 2 y no más de maxPlayersPerAnchorage.
   *
   * @param playersToAnchorAmount Cantidad de jugadores que se intenta anclar.
   *
   * @return Si la cantidad de jugadores anclados es al menos 2 y no más de maxPlayersPerAnchorage.
   */
  private boolean validChecksAmount(int playersToAnchorAmount) {
    return playersToAnchorAmount <= maxPlayersPerAnchorage && playersToAnchorAmount >= 2;
  }

  /**
   * Revisa si la cantidad de jugadores anclados en total no supera el máximo permitido.
   *
   * @param playersToAnchorAmount Cantidad de jugadores que se intenta anclar.
   *
   * @return Si la cantidad de jugadores anclados en total no supera el máximo permitido.
   */
  private boolean validAnchoredPlayersAmount(int playersToAnchorAmount) {
    return playersAnchored + playersToAnchorAmount <= 2 * maxPlayersPerAnchorage;
  }

  // ---------------------------------------- Setters -------------------------------------------

  /**
   * Aplica el número de anclaje correspondiente a cada jugador.
   * Luego, deselecciona sus casillas y las hace invisibles para
   * evitar que dos o más anclajes contengan uno o más jugadores iguales.
   *
   * @param cbSet      Arreglo de casillas a recorrer.
   * @param playersSet Arreglo de jugadores correspondiente al arreglo de casillas.
   */
  private void setAnchorages(List<JCheckBox> cbSet, List<Player> playersSet) {
    cbSet.stream()
         .filter(JCheckBox::isSelected)
         .forEach(cb -> {
           playersSet.stream()
                     .filter(p -> p.getName()
                                   .equals(cb.getText()))
                     .forEach(p -> {
                       p.setAnchor(anchoragesAmount);

                       playersAnchored++;

                       cb.setVisible(false);
                     });

           cb.setSelected(false);
         });
  }
}