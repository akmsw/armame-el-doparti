package armameeldoparti.frames;

import armameeldoparti.utils.BackButton;
import armameeldoparti.utils.Main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

  private static final int TOTAL_PAGES = 6;
  private static final int TEXT_AREA_ROWS = 20;
  private static final int TEXT_AREA_COLUMNS = 30;

  private static final String FRAME_TITLE = "Ayuda";

  // ---------------------------------------- Campos privados -----------------------------------

  private int pageNum;

  private JFrame previousFrame;

  private JLabel pagesCounter;

  private JPanel masterPanel;

  private JTextArea textArea;

  private List<String> helpPages;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye la ventana de ayuda.
   */
  public HelpFrame(JFrame previousFrame) {
    this.previousFrame = previousFrame;

    helpPages = new ArrayList<>();

    helpPages.addAll(Arrays.asList("helpIntro.txt", "helpNames.txt",
                                   "helpAnchorages.txt", "helpScores.txt",
                                   "helpRandomMix.txt", "helpByScoresMix.txt"));

    pageNum = 0;

    initializeInterface();
  }

  // ---------------------------------------- Métodos privados ----------------------------------

  /**
   * Inicializa y muestra la interfaz gráfica de esta ventana.
   */
  private void initializeInterface() {
    masterPanel = new JPanel(new MigLayout("wrap"));

    addTextArea();
    addPagesLabel();
    addButtons();
    add(masterPanel);
    setTitle(FRAME_TITLE);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(MainFrame.ICON.getImage());
    setResizable(false);
    pack();
    setLocationRelativeTo(null);
    updatePage();
  }

  /**
   * Añade el área de texto para mostrar las instrucciones del programa.
   */
  private void addTextArea() {
    textArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS);

    textArea.setBorder(BorderFactory.createBevelBorder(1));
    textArea.setEditable(false);

    JScrollPane scrollPane = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    scrollPane.getVerticalScrollBar()
              .setUI(new BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                  this.thumbColor = Main.LIGHT_GREEN;
                  this.trackColor = Main.DARK_GREEN;
                }
              });

    masterPanel.add(scrollPane);
  }

  /**
   * Agrega la etiqueta que muestra el progreso de lectura de las instrucciones.
   */
  private void addPagesLabel() {
    pagesCounter = new JLabel();

    pagesCounter.setBorder(BorderFactory.createBevelBorder(1));
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
      if (--pageNum > 0) {
        nextPageButton.setEnabled(true);
      } else {
        previousPageButton.setEnabled(false);
      }

      updatePage();
    });

    nextPageButton.setEnabled(true);
    nextPageButton.addActionListener(e -> {
      if (++pageNum < TOTAL_PAGES - 1) {
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
    textArea.setText("");

    updateLabel();

    try {
      BufferedReader br = new BufferedReader(
          new InputStreamReader(getClass().getClassLoader()
                                          .getResourceAsStream(Main.DOCS_PATH + helpPages.get(pageNum)))
      );

      String line;

      while ((line = br.readLine()) != null) {
        textArea.append(line);
        textArea.append(System.lineSeparator());
      }

      textArea.setCaretPosition(0);

    } catch (IOException ex) {
      ex.printStackTrace();
      System.exit(-1);
    }
  }

  /**
   * Actualiza el texto mostrado en la etiqueta de progreso de lectura.
   */
  private void updateLabel() {
    pagesCounter.setText(pageNum + 1 + "/" + TOTAL_PAGES);
  }
}