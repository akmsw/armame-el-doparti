package armameeldoparti.views;

import armameeldoparti.controllers.HelpController;
import armameeldoparti.models.Views;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
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
import lombok.Getter;
import net.miginfocom.layout.CC;
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

  // ---------------------------------------- Private fields ------------------------------------

  private @Getter JButton previousPageButton;
  private @Getter JButton nextPageButton;
  private @Getter JButton backButton;

  private @Getter JLabel pagesCounter;

  private final JPanel masterPanel;

  private @Getter JScrollPane scrollPane;

  private @Getter JTextArea textArea;

  // ---------------------------------------- Constructor ---------------------------------------

  /**
   * Builds the help view.
   */
  public HelpView() {
    super("Ayuda");

    masterPanel = new JPanel(new MigLayout("wrap"));

    initializeInterface();
  }

  // ---------------------------------------- Protected methods ---------------------------------

  /**
   * Initializes the view and makes it visible.
   */
  @Override
  protected void initializeInterface() {
    addTextArea();
    addPagesLabel();
    addButtons();
    add(masterPanel);
    setTitle(getFrameTitle());
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(Constants.ICON
                          .getImage());
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
        ((HelpController) CommonFunctions.getController(Views.HELP)).previousPageButtonEvent()
    );

    nextPageButton.addActionListener(e ->
        ((HelpController) CommonFunctions.getController(Views.HELP)).nextPageButtonEvent()
    );

    backButton.addActionListener(e ->
        ((HelpController) CommonFunctions.getController(Views.HELP)).backButtonEvent()
    );

    previousPageButton.setEnabled(false);

    masterPanel.add(previousPageButton, new CC().width("50%")
                                                .split());
    masterPanel.add(nextPageButton, new CC().width("50%")
                                            .wrap());
    masterPanel.add(backButton, "growx, span");
  }

  // ---------------------------------------- Private methods -----------------------------------

  /**
   * Adds the text area where to display the instructions of the program.
   */
  private void addTextArea() {
    textArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS);

    textArea.setBackground(Constants.GREEN_LIGHT);
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);

    scrollPane = new JScrollPane(textArea,
                                 ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    scrollPane.setBackground(Constants.GREEN_LIGHT);
    scrollPane.getVerticalScrollBar()
              .setUI(new BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                  this.thumbColor = Constants.GREEN_DARK;
                  this.trackColor = Constants.GREEN_MEDIUM;
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