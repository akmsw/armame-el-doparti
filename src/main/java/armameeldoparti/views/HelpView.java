package armameeldoparti.views;

import armameeldoparti.abstracts.View;
import armameeldoparti.utils.Main;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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
 * @version 0.0.1
 *
 * @since 03/07/2022
 */
public class HelpView extends View {

  // ---------------------------------------- Constantes privadas -------------------------------

  private static final int TEXT_AREA_ROWS = 20;
  private static final int TEXT_AREA_COLUMNS = 30;

  private static final String FRAME_TITLE = "Ayuda";

  // ---------------------------------------- Campos privados -----------------------------------

  private JButton previousPageButton;
  private JButton nextPageButton;

  private JLabel pagesCounter;

  private JPanel masterPanel;

  private JScrollPane scrollPane;

  private JTextArea textArea;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Construye la ventana de ayuda.
   */
  public HelpView() {
    initializeInterface();
  }

  // ---------------------------------------- Métodos públicos ----------------------------------

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Obtiene la etiqueta de progreso de lectura.
   *
   * @return La etiqueta de progreso de lectura.
   */
  public JLabel getPagesCounter() {
    return pagesCounter;
  }

  /**
   * Obtiene el botón de página previa.
   *
   * @return El botón de página previa.
   */
  public JButton getPreviousPageButton() {
    return previousPageButton;
  }

  /**
   * Obtiene el botón de página siguiente.
   *
   * @return El botón de página siguiente.
   */
  public JButton getNextPageButton() {
    return nextPageButton;
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
   * Obtiene el área de texto.
   *
   * @return El área de texto.
   */
  public JScrollPane getScrollPane() {
    return scrollPane;
  }

  // ---------------------------------------- Métodos protegidos --------------------------------

  /**
   * Inicializa y muestra la interfaz gráfica de la ventana.
   */
  @Override
  protected void initializeInterface() {
    masterPanel = new JPanel(new MigLayout("wrap"));

    addTextArea();
    addPagesLabel();
    addButtons();
    add(masterPanel);
    setTitle(FRAME_TITLE);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(MainMenuView.ICON.getImage());
    setResizable(false);
    pack();
    setLocationRelativeTo(null);
  }

  /**
   * Coloca los botones en los paneles de la ventana.
   */
  @Override
  protected void addButtons() {
    previousPageButton = new JButton("Anterior");
    nextPageButton = new JButton("Siguiente");

    JButton backButton = new JButton("Volver al menú principal");

    previousPageButton.addActionListener(e ->
        Main.getHelpController()
            .previousPageButtonEvent()
    );

    nextPageButton.addActionListener(e ->
        Main.getHelpController()
            .nextPageButtonEvent()
    );

    backButton.addActionListener(e ->
        Main.getHelpController()
            .backButtonEvent()
    );

    previousPageButton.setEnabled(false);

    masterPanel.add(previousPageButton, "growx, span, split 2, center");
    masterPanel.add(nextPageButton, "growx");
    masterPanel.add(backButton, "growx, span");
  }

  // ---------------------------------------- Métodos privados ----------------------------------

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
    scrollPane.getVerticalScrollBar()
        .setUI(new BasicScrollBarUI() {
          @Override
          protected void configureScrollBarColors() {
            this.thumbColor = Main.DARK_GREEN;
            this.trackColor = Main.MEDIUM_GREEN;
          }
        });

    masterPanel.add(scrollPane);
  }

  /**
   * Agrega la etiqueta que muestra el progreso de lectura de las instrucciones.
   */
  private void addPagesLabel() {
    pagesCounter = new JLabel();

    pagesCounter.setBorder(BorderFactory.createLoweredSoftBevelBorder());
    pagesCounter.setHorizontalAlignment(SwingConstants.CENTER);

    masterPanel.add(pagesCounter, "growx");
  }
}