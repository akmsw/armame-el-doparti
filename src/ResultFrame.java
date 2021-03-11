/**
 * Clase correspondiente a la ventana de resultados
 * en base a los jugadores ingresados y el criterio
 * de distribución elegido por el usuario.
 * 
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 06/03/2021
 */

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ResultFrame extends JFrame {

    // Constantes privadas.
    private static final int CENTRALDEFENDER = 0; //
    private static final int LATERALDEFENDER = 1; //
    private static final int MIDFIELDER = 2;      // Índices del arreglo 'sets' correspondientes
    private static final int FORWARD = 3;         // a cada array de jugadores.
    private static final int WILDCARD = 4;        //

    // Campos privados.
    private JLabel team1, team2;

    public ResultFrame(int distribution, ImageIcon icon, List<Player[]> sets, boolean anchor) {
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setIconImage(icon.getImage());
        
        if (anchor)
            System.out.println("ANCHOR TRUE");
        else
            System.out.println("ANCHOR FALSE");

        if (distribution == 0)
            randomMix();
        else
            ratingMix();
    }

    // ----------------------------------------Métodos privados---------------------------------

    /**
     * Este método se encarga de armar los equipos
     * de manera completamente aleatoria.
     */
    private void randomMix() {
        setTitle("MEZCLA ALEATORIA");
    }

    /**
     * Este método se encarga de armar los equipos
     * en base a las puntuaciones asignadas a los
     * jugadores.
     */
    private void ratingMix() {
        setTitle("MEZCLA POR PUNTAJES");
    }
}