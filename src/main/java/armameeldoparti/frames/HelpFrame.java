package armameeldoparti.frames;

import armameeldoparti.utils.BackButton;
import armameeldoparti.utils.Main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;
import net.miginfocom.swing.MigLayout;

/**
 * Clase correspondiente a la ventana de ayuda.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 03/07/2022
 */
public class HelpFrame extends JFrame {

  // ---------------------------------------- Constantes privadas -------------------------------

  private static final Map<Integer, List<String>> pagesMap = Map.of(
    0, Arrays.asList("INTRODUCCIÓN", "helpIntro.txt"),
    1, Arrays.asList("CRITERIOS ESTABLECIDOS", "helpCriteria.txt"),
    2, Arrays.asList("INGRESO DE JUGADORES", "helpNames.txt"),
    3, Arrays.asList("ANCLAJES", "helpAnchorages.txt"),
    4, Arrays.asList("PUNTUACIONES", "helpScores.txt"),
    5, Arrays.asList("DISTRIBUCIÓN ALEATORIA", "helpRandomMix.txt"),
    6, Arrays.asList("DISTRIBUCIÓN POR PUNTUACIONES", "helpByScoresMix.txt"),
    7, Arrays.asList("SUGERENCIAS, REPORTES Y CONTACTO", "helpContact.txt")
  );

  private static final int TOTAL_PAGES = pagesMap.size();
  private static final int TEXT_AREA_ROWS = 20;
  private static final int TEXT_AREA_COLUMNS = 30;
  private static final int PAGE_TITLE_INDEX = 0;
  private static final int PAGE_FILENAME_INDEX = 1;

  private static final String FRAME_TITLE = "Ayuda";

  // ---------------------------------------- Campos privados -----------------------------------

  private int pageNumber;

  private JFrame previousFrame;

  private JLabel pagesCounter;

  private JPanel masterPanel;

  private JScrollPane scrollPane;

  private JTextArea textArea;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye la ventana de ayuda.
   */
  public HelpFrame(JFrame previousFrame) {
    this.previousFrame = previousFrame;

    pageNumber = 0;

    initializeInterface();
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Inicializa y muestra la interfaz gráfica de esta ventana.
   */
  private void initializeInterface() {
    masterPanel = new JPanel(new MigLayout("wrap"));

    addTextArea();
    addButtons();
    setTitle(FRAME_TITLE);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(MainFrame.ICON.getImage());
    setResizable(false);
    add(masterPanel);
    pack();
    setLocationRelativeTo(null);
  }

  /**
   * Añade el área de texto para mostrar las instrucciones del programa.
   */
  private void addTextArea() {
    textArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS);

    textArea.setBackground(Main.LIGHT_GREEN);
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);

    scrollPane = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    scrollPane.setBackground(Main.LIGHT_GREEN);
    scrollPane.setForeground(Main.DARK_GREEN);
    scrollPane.getVerticalScrollBar()
              .setUI(new BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                  this.thumbColor = Main.DARK_GREEN;
                  this.trackColor = Main.MEDIUM_GREEN;
                }
              });

    masterPanel.add(scrollPane);

    addPagesLabel();
    updatePage();
  }

  /**
   * Agrega la etiqueta que muestra el progreso de lectura de las instrucciones.
   */
  private void addPagesLabel() {
    pagesCounter = new JLabel();

    pagesCounter.setBorder(BorderFactory.createLoweredSoftBevelBorder());
    pagesCounter.setHorizontalAlignment(SwingConstants.CENTER);

    masterPanel.add(pagesCounter, "grow, span");
  }

  /**
   * Añade los botones al panel de la ventana.
   */
  private void addButtons() {
    JButton previousPageButton = new JButton("Anterior");
    JButton nextPageButton = new JButton("Siguiente");

    previousPageButton.setEnabled(false);
    previousPageButton.addActionListener(e -> {
      if (--pageNumber > 0) {
        nextPageButton.setEnabled(true);
      } else {
        previousPageButton.setEnabled(false);
      }

      updatePage();
    });

    nextPageButton.setEnabled(true);
    nextPageButton.addActionListener(e -> {
      if (++pageNumber < TOTAL_PAGES - 1) {
        previousPageButton.setEnabled(true);
      } else {
        nextPageButton.setEnabled(false);
      }

      updatePage();
    });

    BackButton backButton = new BackButton(this, previousFrame, "Volver al menú principal");

    masterPanel.add(previousPageButton, "growx, span, split 2, center");
    masterPanel.add(nextPageButton, "growx");
    masterPanel.add(backButton, "growx, span");
  }

  /**
   * Actualiza la página de instrucciones mostrada en el área de texto.
   */
  private void updatePage() {
    scrollPane.setBorder(BorderFactory.createTitledBorder(pagesMap.get(pageNumber)
                                                                  .get(PAGE_TITLE_INDEX)));

    textArea.setText("");

    updateLabel();

    try {
      textArea.read(new BufferedReader(
                      new InputStreamReader(
                        getClass().getClassLoader()
                                  .getResourceAsStream(Main.HELP_DOCS_PATH
                                                       + pagesMap.get(pageNumber)
                                                                 .get(PAGE_FILENAME_INDEX))
                      )
                    ), null);
    } catch (IOException ex) {
      ex.printStackTrace();
      System.exit(-1);
    }
  }

  /**
   * Actualiza el texto mostrado en la etiqueta de progreso de lectura.
   */
  private void updateLabel() {
    pagesCounter.setText(pageNumber + 1 + "/" + TOTAL_PAGES);
  }
}