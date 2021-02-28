/**
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 27/02/2021
 */

import java.awt.Toolkit;
// import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    // Campos privados.
    Toolkit screen;
    // Dimension screenSize;
    ImageIcon bgImage;
    // int screenHeight, screenWidth; // Alto y ancho de la pantalla, respectivamente.
    int bgHeight, bgWidth; // Alto y ancho de la imagen de fondo, respectivamente.

    public MainFrame() {
        screen = Toolkit.getDefaultToolkit();

        bgImage = new ImageIcon(screen.getImage(this.getClass().getResource("/graphics/backgroundImage.png")));

        bgHeight = bgImage.getIconHeight();
        bgWidth = bgImage.getIconHeight();

        setSize(bgWidth, bgHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}