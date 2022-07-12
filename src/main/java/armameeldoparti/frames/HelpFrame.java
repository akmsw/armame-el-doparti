package armameeldoparti.frames;

import armameeldoparti.utils.BackButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
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

    private static final int TOTAL_PAGES = 5;

    // ---------------------------------------- Campos privados -----------------------------------

    private int pageNum;

    private JFrame previousFrame;

    private JPanel masterPanel;

    // ---------------------------------------- Constructor ---------------------------------------

    /**
     * Construye la ventana de ayuda.
     */
    public HelpFrame(JFrame previousFrame) {
        this.previousFrame = previousFrame;

        pageNum = 1;

        initializeGUI("Ayuda");
    }

    // ---------------------------------------- Métodos privados ----------------------------------

    /**
     * Inicializa y muestra la interfaz gráfica de esta ventana.
     *
     * @param frameTitle Título de la ventana.
     */
    private void initializeGUI(String frameTitle) {
        masterPanel = new JPanel(new MigLayout("wrap"));

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

        JButton previousPageButton = new JButton("Anterior");
        JButton nextPageButton = new JButton("Siguiente");

        previousPageButton.setEnabled(false);
        previousPageButton.addActionListener(e -> {
            if (--pageNum > 1) {
                if (!nextPageButton.isEnabled()) {
                    nextPageButton.setEnabled(true);
                }
            } else {
                previousPageButton.setEnabled(false);
            }

            // Cambiar a página anterior
        });

        nextPageButton.setEnabled(true);
        nextPageButton.addActionListener(e -> {
            if (++pageNum < TOTAL_PAGES) {
                if (!previousPageButton.isEnabled()) {
                    previousPageButton.setEnabled(true);
                }
            } else {
                nextPageButton.setEnabled(false);
            }

            // Cambiar a página siguiente
        });

        masterPanel.add(previousPageButton, "growx, span, split 2, center");
        masterPanel.add(nextPageButton, "growx");
        masterPanel.add(backButton, "growx, span");
    }
}
