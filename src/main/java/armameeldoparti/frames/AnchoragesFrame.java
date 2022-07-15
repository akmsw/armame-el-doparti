package armameeldoparti.frames;

import armameeldoparti.utils.BackButton;
import armameeldoparti.utils.Main;
import armameeldoparti.utils.Player;
import armameeldoparti.utils.Position;
import java.util.ArrayList;
import java.util.Arrays;
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

  private static final String GROWX_SPAN = "growx, span";
  private static final String FRAME_TITLE = "Anclaje de jugadores";

  // ---------------------------------------- Campos privados -----------------------------------

  private int maxPlayersPerAnchorage;
  private int anchorageNum;
  private int playersAnchored;

  private List<JCheckBox> cdCheckboxes;
  private List<JCheckBox> ldCheckboxes;
  private List<JCheckBox> mfCheckboxes;
  private List<JCheckBox> fwCheckboxes;
  private List<JCheckBox> gkCheckboxes;
  private List<List<JCheckBox>> cbSets;

  private JButton okButton;
  private JButton newAnchorageButton;
  private JButton clearAnchoragesButton;
  private JButton deleteSpecificAnchorageButton;
  private JButton deleteLastAnchorageButton;

  private JFrame previousFrame;

  private JPanel leftPanel;
  private JPanel rightPanel;

  private JScrollPane scrollPane;

  private JTextArea textArea;

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

    cbSets = Arrays.asList(cdCheckboxes, ldCheckboxes, mfCheckboxes, fwCheckboxes, gkCheckboxes);

    anchorageNum = 0;
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

    int index = 0;

    for (Map.Entry<Position, List<Player>> ps : Main.getPlayersSets().entrySet()) {
      fillCheckboxesSet(ps.getValue(), cbSets.get(index));
      addCheckboxesSet(cbSets.get(index), Main.getPositionsMap()
                                              .get(Position.values()[index]));

      index++;
    }

    textArea.setBorder(BorderFactory.createBevelBorder(1));
    textArea.setEditable(false);

    addButtons();

    JPanel masterPanel = new JPanel(new MigLayout("wrap 2"));

    masterPanel.add(leftPanel, "west");
    masterPanel.add(rightPanel, "east");

    updateTextArea();
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
    okButton = new JButton("Finalizar");

    newAnchorageButton = new JButton("Anclar");
    deleteSpecificAnchorageButton = new JButton("Borrar un anclaje");
    deleteLastAnchorageButton = new JButton("Borrar último anclaje");
    clearAnchoragesButton = new JButton("Limpiar anclajes");

    okButton.setEnabled(false);
    okButton.addActionListener(e -> {
      cbSets.forEach(cbs -> cbs.forEach(cb -> {
        if (cb.isSelected() && cb.isVisible()) {
          cb.setSelected(false);
        }
      }));

      if (Main.getDistribution() == 1) {
        // Distribución por puntuaciones
        ScoresInputFrame scoresInputFrame = new ScoresInputFrame(this);
        scoresInputFrame.setVisible(true);
      } else {
        // Distribución aleatoria
        ResultsFrame resultsFrame = new ResultsFrame(this);
        resultsFrame.setVisible(true);
      }

      setVisible(false);
      setLocationRelativeTo(null);
    });

    BackButton backButton = new BackButton(this, previousFrame, null);

    backButton.addActionListener(e -> {
      clearAnchorages();

      previousFrame.setVisible(true);

      dispose();
    });

    newAnchorageButton.addActionListener(e -> {
      int anchored = (int) cbSets.stream()
                                 .flatMap(List::stream)
                                 .filter(JCheckBox::isSelected)
                                 .count();

      if (!validChecksAmount(anchored)) {
        showErrMsg("No puede haber más de " + maxPlayersPerAnchorage
                    + " ni menos de 2 jugadores en un mismo anclaje");
        return;
      } else if (!isValidAnchorage()) {
        showErrMsg("No puede haber más de la mitad de jugadores de una misma posición "
                   + "en un mismo anclaje");
        return;
      } else if (!validAnchorageAmount(anchored)) {
        showErrMsg("No puede haber más de " + 2 * maxPlayersPerAnchorage
                   + " jugadores anclados en total");
        return;
      }

      anchorageNum++;

      for (int i = 0; i < cbSets.size(); i++) {
        setAnchors(cbSets.get(i), Main.getPlayersSets()
                                      .get(Position.values()[i]));
      }

      updateTextArea();
    });

    deleteSpecificAnchorageButton.addActionListener(e -> deleteSpecificAnchorage());
    deleteLastAnchorageButton.addActionListener(e -> deleteLast());
    clearAnchoragesButton.addActionListener(e -> clearAnchorages());

    leftPanel.add(okButton, GROWX_SPAN);
    leftPanel.add(backButton, GROWX_SPAN);

    rightPanel.add(scrollPane, "span2, push, grow");
    rightPanel.add(newAnchorageButton, "grow");
    rightPanel.add(deleteSpecificAnchorageButton, "grow");
    rightPanel.add(deleteLastAnchorageButton, "grow");
    rightPanel.add(clearAnchoragesButton, "grow");
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
   * Cambia el número de anclaje de los jugadores deseados.
   *
   * @param target      Anclaje a reemplazar.
   * @param replacement Nuevo anclaje a aplicar.
   */
  private void changeAnchor(int target, int replacement) {
    Main.getPlayersSets()
        .values()
        .stream()
        .flatMap(List::stream)
        .filter(p -> p.getAnchor() == target)
        .forEach(p -> {
          p.setAnchor(replacement);

          if (replacement == 0) {
            cbSets.stream()
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
   */
  private void deleteSpecificAnchorage() {
    String[] optionsDelete = new String[anchorageNum];

    for (int i = 0; i < anchorageNum; i++) {
      optionsDelete[i] = Integer.toString(i + 1);
    }

    int anchor = JOptionPane.showOptionDialog(null,
                                              "Seleccione qué anclaje desea borrar",
                                        "Antes de continuar...", 2,
                                              JOptionPane.QUESTION_MESSAGE,
                                              MainFrame.SCALED_ICON, optionsDelete,
                                              optionsDelete[0]) + 1;

    if (anchor - 1 != JOptionPane.CLOSED_OPTION) {
      // Los que tenían anclaje igual a 'anchor' ahora tienen anclaje '0'
      for (int j = 0; j < cbSets.size(); j++) {
        changeAnchor(anchor, 0);
      }

      /*
        * A los que tienen anclaje desde 'anchor + 1' hasta 'anchorageNum'
        * se les decrementa en 1 su número de anclaje.
        */
      for (int k = anchor + 1; k <= anchorageNum; k++) {
        for (int j = 0; j < cbSets.size(); j++) {
          changeAnchor(k, k - 1);
        }
      }

      anchorageNum--;

      updateTextArea();
    }
  }

  /**
   * Borra el último anclaje realizado.
   */
  private void deleteLast() {
    for (int i = 0; i < cbSets.size(); i++) {
      changeAnchor(anchorageNum, 0);
    }

    anchorageNum--;

    updateTextArea();
  }

  /**
   * Borra todos los anclajes que se hayan generado.
   *
   * @see #deleteLast()
   */
  private void clearAnchorages() {
    do {
      deleteLast();
    } while (anchorageNum > 0);
  }

  /**
   * Actualiza el área de texto mostrando la cantidad de anclajes
   * y los jugadores anclados a los mismos.
   */
  private void updateTextArea() {
    textArea.setText("");

    var wrapperIndex = new Object() {
      private int index;
    };

    var wrapperCounter = new Object() {
      private int counter = 1;
    };

    for (wrapperIndex.index = 1; wrapperIndex.index <= anchorageNum; wrapperIndex.index++) {
      textArea.append(" ----- ANCLAJE #" + wrapperIndex.index + " -----" + System.lineSeparator());

      Main.getPlayersSets()
          .entrySet()
          .forEach(ps -> ps.getValue()
                           .forEach(p -> {
                             if (p.getAnchor() == wrapperIndex.index) {
                               textArea.append(" " + wrapperCounter.counter
                                               + ". " + p.getName() + System.lineSeparator());
                               wrapperCounter.counter++;
                             }
                           }));

      textArea.append(System.lineSeparator());

      wrapperCounter.counter = 1;
    }

    toggleButtons();
  }

  /**
   * Conmuta la habilitación de los botones del panel derecho
   * y las casillas del panel izquierdo de la ventana.
   */
  private void toggleButtons() {
    if (anchorageNum > 0 && anchorageNum < 2) {
      okButton.setEnabled(true);
      deleteSpecificAnchorageButton.setEnabled(false);
      deleteLastAnchorageButton.setEnabled(true);
      clearAnchoragesButton.setEnabled(true);
    } else if (anchorageNum >= 2) {
      deleteSpecificAnchorageButton.setEnabled(true);
      deleteLastAnchorageButton.setEnabled(true);
    } else {
      okButton.setEnabled(false);
      deleteSpecificAnchorageButton.setEnabled(false);
      deleteLastAnchorageButton.setEnabled(false);
      clearAnchoragesButton.setEnabled(false);
    }

    if (2 * maxPlayersPerAnchorage - playersAnchored < 2) {
      newAnchorageButton.setEnabled(false);

      cbSets.forEach(cbs -> cbs.forEach(cb -> cb.setEnabled(!cb.isEnabled())));
    } else {
      newAnchorageButton.setEnabled(true);

      cbSets.forEach(cbs -> cbs.forEach(cb -> {
        if (!cb.isEnabled() && !cb.isSelected()) {
          cb.setEnabled(true);
        }
      }));
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
  private boolean isValidAnchorage() {
    return cbSets.stream()
                  .noneMatch(cbs -> cbs.stream()
                                       .filter(JCheckBox::isSelected)
                                       .count() > cbs.size() / 2);
  }

  /**
   * Revisa si la cantidad de jugadores anclados es al menos 2 y no más de maxPlayersPerAnchorage.
   *
   * @param anchored Cantidad de jugadores que se intenta anclar.
   *
   * @return Si la cantidad de jugadores anclados es al menos 2 y no más de maxPlayersPerAnchorage.
   */
  private boolean validChecksAmount(int anchored) {
    return anchored <= maxPlayersPerAnchorage && anchored >= 2;
  }

  /**
   * Revisa si la cantidad de jugadores anclados en total no supera el máximo permitido.
   *
   * @param playersToAnchor Cantidad de jugadores que se intenta anclar.
   *
   * @return Si la cantidad de jugadores anclados en total no supera el máximo permitido.
   */
  private boolean validAnchorageAmount(int playersToAnchor) {
    return playersAnchored + playersToAnchor <= 2 * maxPlayersPerAnchorage;
  }

  // ---------------------------------------- Setters -------------------------------------------

  /**
   * Configura el número de anclaje correspondiente a cada jugador.
   * Luego, deselecciona estas casillas y las hace invisibles para
   * evitar que dos o más anclajes contengan uno o más jugadores iguales.
   * En caso de que el campo 'anchorageNum' sea 0 (se han querido limpiar los
   * anclajes), se reiniciarán los números de anclaje de cada jugador y todas las
   * casillas quedarán visibles y deseleccionadas.
   *
   * @param cbSet      Arreglo de casillas a recorrer.
   * @param playersSet Arreglo de jugadores correspondiente al arreglo de casillas.
   */
  private void setAnchors(List<JCheckBox> cbSet, List<Player> playersSet) {
    cbSet.stream()
         .filter(JCheckBox::isSelected)
         .forEach(cb -> {
           if (anchorageNum != 0) {
             playersSet.stream()
                       .filter(p -> p.getName()
                                     .equals(cb.getText()))
                       .forEach(p -> {
                         p.setAnchor(anchorageNum);

                         playersAnchored++;

                         cb.setVisible(false);
                       });
           } else {
             playersSet.forEach(p -> {
               p.setAnchor(anchorageNum);
               cb.setVisible(true);
             });
           }

           cb.setSelected(false);
         });
  }
}