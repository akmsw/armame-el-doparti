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

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ResultFrame extends JFrame {

    public ResultFrame(int distribution, ImageIcon icon) {
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if (distribution == 0)
            setTitle("MEZCLA ALEATORIA");
        else
            setTitle("MEZCLA POR PUNTAJES");

        setResizable(false);
        setIconImage(icon.getImage());
    }
}
