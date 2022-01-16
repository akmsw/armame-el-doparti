/**
 * Clase correspondiente a los botones de navegación entre ventanas.
 * 
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 27/06/2021
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;

public class BackButton extends JButton {

    // Campos privados.
    private static final Rectangle BACKBUTTON_BOUNDS = new Rectangle(224, 310, 100, 30);

    /**
     * Constructor del botón para navegar hacia atrás entre ventanas.
     * 
     * @param currentFrame Ventana donde está colocado el botón.
     * @param previousFrame Ventana a la que regresar cuando se pulse el botón.
     */
    public BackButton(JFrame currentFrame, JFrame previousFrame) {
        setText("Atrás");
        setEnabled(true);
        setVisible(true);
        setBounds(BACKBUTTON_BOUNDS);

        addActionListener(new ActionListener() {
            /**
             * Este método togglea la visibilidad de la ventana anterior,
             * eliminando la ventana actual para evitar múltiples
             * instancias de una misma ventana con distintas
             * visibilidades. Esto podría llevar a corrupción de
             * datos y a mantener información inutilizada en memoria
             * (e.g.: JFrames duplicados donde uno es visible y otro no).
             * 
             * @param e Evento de click.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                previousFrame.setVisible(true);
                currentFrame.dispose();
            }
        });
    }
}