package armameeldoparti.views;

import armameeldoparti.Main;
import armameeldoparti.controllers.HelpController;
import armameeldoparti.models.Views;
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
 * Help view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 03/07/2022
 */
public class HelpView extends View {

  // ---------------------------------------- Private constants ---------------------------------

  private static final int TEXT_AREA_ROWS = 20;
  private static final int TEXT_AREA_COLUMNS = 30;

  private static final String FRAME_TITLE = "Ayuda";

  // ---------------------------------------- Private fields ------------------------------------

  private JButton previousPageButton;
  private JButton nextPageButton;
  private JButton backButton;

  private JLabel pagesCounter;

  private JPanel masterPanel;

  private JScrollPane scrollPane;

  private JTextArea textArea;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the help view.
   */
  public HelpView() {
    initializeInterface();
  }

  // ---------------------------------------- Public methods ------------------------------------

  // ---------------------------------------- Getters -------------------------------------------

  /**
   * Gets the reading progress label.
   *
   * @return The reading progress label.
   */
  public JLabel getPagesCounter() {
    return pagesCounter;
  }

  /**
   * Gets the 'previous page' button.
   *
   * @return The 'previous page' button.
   */
  public JButton getPreviousPageButton() {
    return previousPageButton;
  }

  /**
   * Gets the 'next page' button.
   *
   * @return The 'next page' button.
   */
  public JButton getNextPageButton() {
    return nextPageButton;
  }

  /**
   * Gets the back button.
   *
   * @return The back button.
   */
  public JButton getBackButton() {
    return backButton;
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
   * Gets the scroll pane.
   *
   * @return The scroll pane.
   */
  public JScrollPane getScrollPane() {
    return scrollPane;
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Initializes the view and makes it visible.
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
    setIconImage(Main.ICON.getImage());
    setResizable(false);
    pack();
    setLocationRelativeTo(null);
  }

  /**
   * Adds the buttons to their corresponding panel.
   */
  @Override
  protected void addButtons() {
    previousPageButton = new JButton("Anterior");
    nextPageButton = new JButton("Siguiente");

    backButton = new JButton("Volver al menú principal");

    previousPageButton.addActionListener(e ->
        ((HelpController) Main.getController(Views.HELP)).previousPageButtonEvent()
    );

    nextPageButton.addActionListener(e ->
        ((HelpController) Main.getController(Views.HELP)).nextPageButtonEvent()
    );

    backButton.addActionListener(e ->
        ((HelpController) Main.getController(Views.HELP)).backButtonEvent()
    );

    previousPageButton.setEnabled(false);

    masterPanel.add(previousPageButton, "growx, span, split 2, center");
    masterPanel.add(nextPageButton, "growx");
    masterPanel.add(backButton, "growx, span");
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the text area where to display the instructions of the program.
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
   * Adds the reading progress label.
   */
  private void addPagesLabel() {
    pagesCounter = new JLabel();

    pagesCounter.setBorder(BorderFactory.createLoweredSoftBevelBorder());
    pagesCounter.setHorizontalAlignment(SwingConstants.CENTER);

    masterPanel.add(pagesCounter, "growx");
  }
}