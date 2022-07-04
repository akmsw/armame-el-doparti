package armameeldoparti.frames;

import armameeldoparti.utils.BackButton;
import armameeldoparti.utils.Main;
import java.io.InputStreamReader;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import net.miginfocom.swing.MigLayout;

/**
 * Clase correspondiente a la ventana de ayuda.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 03/07/2021
 */
public class HelpFrame extends JFrame {
    // ---------------------------------------- Constantes públicas ------------------------------

    // ---------------------------------------- Constantes privadas ------------------------------

    /**
     * Cantidad de filas para el área de texto.
     */
    private static final int TEXT_AREA_ROWS = 25;

    /**
     * Cantidad de columnas para el área de texto.
     */
    private static final int TEXT_AREA_COLUMNS = 40;

    /**
     * Nombre del archivo .pia.
     */
    private static final String PIA_FILENAME = "help.pia";

    // ---------------------------------------- Campos públicos ----------------------------------

    // ---------------------------------------- Campos privados ----------------------------------

    private JFrame previousFrame;

    private JPanel masterPanel;

    // ---------------------------------------- Constructor --------------------------------------

    /**
     * Construye la ventana de ayuda.
     */
    public HelpFrame(JFrame previousFrame) {
        this.previousFrame = previousFrame;

        initializeComponents("Ayuda");
    }

    // ---------------------------------------- Métodos privados ---------------------------------

    /**
     * Inicializa los componentes de la ventana.
     *
     * @param frameTitle Título de la ventana.
     */
    private void initializeComponents(String frameTitle) {
        masterPanel = new JPanel(new MigLayout());

        addTextArea();
        addButtons();
        add(masterPanel);

        pack();

        setResizable(false);
        setLocationRelativeTo(null);
        setTitle(frameTitle);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(MainFrame.ICON.getImage());
    }

    /**
     * Añade los botones al panel de la ventana.
     */
    private void addButtons() {
        BackButton backButton = new BackButton(HelpFrame.this, previousFrame, "Volver al menú principal");

        masterPanel.add(backButton, "push, grow, span");
    }

    /**
     * Añade el área de texto con las instrucciones extraídas del archivo .pia.
     */
    private void addTextArea() {
        JTextArea textArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS);

        textArea.setBorder(new BevelBorder(1));
        textArea.setBackground(Main.LIGHT_GREEN);
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        Scanner scanner = new Scanner(new InputStreamReader(this.getClass()
                                                                .getClassLoader()
                                                                .getResourceAsStream(PIA_FILENAME)));

        while (scanner.hasNextLine()) {
            textArea.append(scanner.nextLine());

            if (scanner.hasNextLine()) {
                textArea.append(System.lineSeparator());
            }
        }

        textArea.setCaretPosition(0);

        masterPanel.add(scrollPane, "push, grow, span");
    }
}
