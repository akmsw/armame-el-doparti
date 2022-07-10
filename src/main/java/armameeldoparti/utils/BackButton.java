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

    // ---------------------------------------- Constantes privadas -------------------------------

    /**
     * Texto por defecto a mostrar en el botón.
     */
    private static final String DEFAULT_TEXT = "Atrás";

    // ---------------------------------------- Constructor ---------------------------------------

    /**
     * Construye un botón para navegar hacia atrás entre ventanas.
     *
     * @param currentFrame  Ventana donde está colocado el botón.
     * @param previousFrame Ventana a la que regresar cuando se pulse el botón.
     * @param text          Texto a mostrar en el botón. Si este campo es nulo, es un string vacío
     *                      o es un string en blanco, se aplica el texto por defecto.
     */
    public BackButton(JFrame currentFrame, JFrame previousFrame, String text) {
        setText(text == null || text.isBlank() || text.isEmpty() ? DEFAULT_TEXT : text);
        setEnabled(true);
        setVisible(true);
        addActionListener(e -> {
            previousFrame.setVisible(true);

            currentFrame.dispose();
        });
    }
}
