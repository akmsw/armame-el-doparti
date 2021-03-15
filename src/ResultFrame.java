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
import javax.swing.JPanel;

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
    private List<Player[]> sets;

    /**
     * Constructor.
     * Aquí se crea la ventana de resultados, la cual
     * también creará una ventana de anclaje de jugadores
     * si es necesario.
     * 
     * @param distribution Tipo de distribución elegida.
     * @param icon Ícono para la ventana.
     * @param sets Conjuntos de jugadores a mezclar.
     */
    public ResultFrame(int distribution, ImageIcon icon, List<Player[]> sets) {
        this.sets = sets;

        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setIconImage(icon.getImage());

        if (distribution == 0)
            randomMix();
        else
            ratingMix();
    }

    // ----------------------------------------Métodos privados---------------------------------

    /**
     * Este método crea la ventana de anclaje de jugadores.
     * 
     * @param playersAmount La cantidad de jugadores por equipo.
     */
    /*private void playersAnchor() {
        checkboxes = new ArrayList<>();

        anchorPanel.setLayout(new MigLayout());

        for (Player[] players : sets)
            for (Player player : players)
                checkboxes.add(new JCheckBox(player.getName()));
        
        int aux = 0;

        for (int i = 0; i < sets.size(); i++) {
            anchorPanel.add(new JLabel(groupName(Position.values()[i])), "wrap");

            for (int j = 0; j < sets.get(i).length; j++) {
                if ((j % 2) != 0)
                    anchorPanel.add(checkboxes.get(aux), "span");
                else
                    anchorPanel.add(checkboxes.get(aux));
                
                aux++;
            }
        }
    }*/

    /**
     * Este método ayuda a mostrar al usuario
     * las posiciones de los jugadores de manera
     * más amigable.
     * 
     * @param position Posición del jugador (directo del enum).
     * 
     * @return Posición en formato user-friendly.
     */
    private String groupName(Position position) {
        switch (position) {
            case CENTRALDEFENDER:
                return "DEFENSORES CENTRALES";

            case LATERALDEFENDER:
                return "DEFENSORES LATERALES";

            case MIDFIELDER:
                return "MEDIOCAMPISTAS";

            case FORWARD:
                return "DELANTEROS";
        
            default:
                return "COMODINES";
        }
    }

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