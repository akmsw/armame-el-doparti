/**
 * Clase correspondiente a la ventana de resultados
 * de distribución de jugadores.
 * 
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 3.0.0
 * 
 * @since 06/03/2021
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ResultFrame extends JFrame {

    /* ---------------------------------------- Campos privados ---------------------------------- */

    private JButton mainMenuButton;

    private JFrame previousFrame;

    private JPanel panel;
    
    private BackButton backButton;
    
    private InputFrame inputFrame;

    /**
     * Creación de la ventana de resultados.
     * 
     * @param inputFrame    La ventana de ingreso de datos, de la cual se obtendrá
     *                      información importante.
     * @param previousFrame La ventana fuente que crea la ventana ResultFrame.
     */
    public ResultFrame(InputFrame inputFrame, JFrame previousFrame) {
        this.inputFrame = inputFrame;
        this.previousFrame = previousFrame;

        if (inputFrame.getDistribution() == 0)
            randomMix();
        else
            ratingMix();
    }

    /* ---------------------------------------- Métodos privados --------------------------------- */

    /**
     * Este método inicializa los componentes de la ventana de resultados.
     */
    private void initializeComponents() {
        mainMenuButton = new JButton("Volver al menú principal");

        backButton = new BackButton(ResultFrame.this, previousFrame);

        panel = new JPanel(new MigLayout("wrap"));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(MainFrame.icon.getImage());

        mainMenuButton.addActionListener(new ActionListener() {
            /**
             * Este método devuelve al usuario al menú principal
             * de la aplicación, eliminando la ventana de resultados.
             * 
             * @param e Evento de click.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultFrame.this.dispose();
                inputFrame.dispose();
                previousFrame.dispose();

                MainFrame mainFrame = new MainFrame();

                mainFrame.setVisible(true);
            }
        });

        if (inputFrame.getDistribution() == 0) {
            JButton remixButton = new JButton("Nueva mezcla");

            remixButton.addActionListener(new ActionListener() {
                /**
                 * TODO
                 * 
                 * @param e Evento de click.
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("REMIX");
                }
            });

            panel.add(remixButton, "growx");
        }

        panel.add(backButton, "growx");
        panel.add(mainMenuButton, "growx");

        add(panel);

        pack();

        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Este método se encarga de armar los equipos de manera completamente
     * aleatoria.
     */
    private void randomMix() {
        if (inputFrame.thereAreAnchorages())
            setTitle("MEZCLA ALEATORIA - CON ANCLAJES");
        else
            setTitle("MEZCLA ALEATORIA - SIN ANCLAJES");

        initializeComponents();
    }

    /**
     * Este método se encarga de armar los equipos de la manera más
     * equitativa en base a las puntuaciones seteadas a los jugadores
     * y los anclajes existentes.
     */
    private void ratingMix() {
        if (inputFrame.thereAreAnchorages())
            setTitle("MEZCLA POR PUNTAJES - CON ANCLAJES");
        else
            setTitle("MEZCLA POR PUNTAJES - SIN ANCLAJES");

        initializeComponents();
    }
}