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

import java.util.HashMap;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ResultFrame extends JFrame {

    /* ---------------------------------------- Campos privados ---------------------------------- */

    private HashMap<String, Player> team1, team2;

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

        team1 = new HashMap<String, Player>();
        team2 = new HashMap<String, Player>();

        if (inputFrame.distribution == 0) {
            randomMix(inputFrame.thereAreAnchorages());
    
            initializeComponents();
        } else {
            ratingsMix(inputFrame.thereAreAnchorages());
    
            initializeComponents();
        }
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

        if (inputFrame.distribution == 0) {
            JButton remixButton = new JButton("Nueva mezcla");

            remixButton.addActionListener(new ActionListener() {
                /**
                 * Este método simplemente vuelve a mezclar los
                 * jugadores de manera aleatoria considerando
                 * los anclajes ingresados por el usuario.
                 * 
                 * @param e Evento de click.
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    randomMix(inputFrame.thereAreAnchorages());
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
     * Este método se encarga de repartir de manera aleatoria los
     * jugadores en dos equipos.
     * 
     * @param anchorages Si la mezcla aleatoria debe tener
     *                   en cuenta anclajes establecidos.
     */
    private void randomMix(boolean anchorages) {
        if (anchorages) {
            setTitle("MEZCLA ALEATORIA - CON ANCLAJES");
        } else {
            setTitle("MEZCLA ALEATORIA - SIN ANCLAJES");
        }
    }

    /**
     * Este método se encarga de repartir los jugadores en dos
     * equipos de la manera más equitativa posible en base a
     * los puntajes ingresados por el usuario.
     * 
     * @param anchorages Si la mezcla aleatoria debe tener
     *                   en cuenta anclajes establecidos.
     */
    private void ratingsMix(boolean anchorages) {
        if (anchorages) {
            setTitle("MEZCLA POR PUNTAJES - CON ANCLAJES");
        } else {
            setTitle("MEZCLA POR PUNTAJES - SIN ANCLAJES");
        }
    }
}