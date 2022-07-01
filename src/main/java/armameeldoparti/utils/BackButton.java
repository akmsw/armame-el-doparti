package armameeldoparti.utils;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Clase correspondiente a los botones de navegación entre ventanas.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 27/06/2021
 */
public class BackButton extends JButton {

    // ---------------------------------------- Constructor --------------------------------------

    /**
     * Constructor del botón para navegar hacia atrás entre ventanas.
     *
     * @param currentFrame  Ventana donde está colocado el botón.
     * @param previousFrame Ventana a la que regresar cuando se pulse el botón.
     */
    public BackButton(JFrame currentFrame, JFrame previousFrame) {
        setText("Atrás");
        setEnabled(true);
        setVisible(true);

        addActionListener(e -> {
            previousFrame.setVisible(true);

            currentFrame.dispose();
        });
    }
}
