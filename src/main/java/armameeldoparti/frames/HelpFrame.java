package armameeldoparti.frames;

import armameeldoparti.utils.BackButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * @since 03/07/2021
 */
public class HelpFrame extends JFrame implements ActionListener {

    // ---------------------------------------- Campos privados ----------------------------------

    private JButton previousPageButton;

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

        JButton nextPageButton = new JButton("Siguiente");

        previousPageButton = new JButton("Anterior");

        previousPageButton.setEnabled(false);
        previousPageButton.addActionListener(this);

        nextPageButton.setEnabled(true);
        nextPageButton.addActionListener(this);

        masterPanel.add(previousPageButton, "growx");
        masterPanel.add(nextPageButton, "span");

        masterPanel.add(backButton, "push, grow, span");
    }

    // ---------------------------------------- Métodos públicos ---------------------------------

    /**
     * Indica qué hacer en base a cada botón pulsado.
     * <p>
     * Si se presiona el botón "Anterior", se cambia la página de ayuda mostrada por la anterior.
     * <p>
     * Si se presiona el botón "Siguiente", se cambia la página de ayuda mostrada por la siguiente.
     *
     * @param e Evento de click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == previousPageButton) {
            // TODO
        } else {
            // TODO
        }
    }
}
