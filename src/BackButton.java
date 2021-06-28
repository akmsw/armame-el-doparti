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

import javax.swing.JButton;
import javax.swing.JFrame;

 public class BackButton extends JButton {

    // Constructor.
    public BackButton(JFrame currentFrame, JFrame previousFrame) {
        setText("Atrás");
        setEnabled(true);
        setVisible(true);
        setBounds(224, 310, 100, 30);

        addActionListener(new ActionListener() {
            /**
             * Este método togglea la visibilidad de las ventanas.
             * 
             * @param e Evento de click.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                previousFrame.setVisible(true);
                currentFrame.setVisible(false);
            }
        });
    }
}