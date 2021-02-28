/**
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 27/02/2021
 */

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    /**
     * Constructor.
     */
    public MainFrame(String frameTitle) {
        initializeComponents(frameTitle);

        setVisible(true);
        setResizable(false);
    }

    /**
     * Este método se encarga de inicializar los elementos básicos
     * de la ventana principal.
     * 
     * @param   frameTitle  Título de la ventana.
     */
    private void initializeComponents(String frameTitle) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        ImageIcon bgImage = new ImageIcon(toolkit.getImage(this.getClass().getResource("/graphics/backgroundImage.png")));

        int bgWidth = bgImage.getIconWidth();
        int bgHeight = bgImage.getIconHeight();

        setSize(bgWidth, bgHeight);
        setLocationRelativeTo(null);
        setTitle(frameTitle);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, bgWidth, bgHeight);

        JLabel bgLabel = new JLabel("", bgImage, JLabel.CENTER);
        bgLabel.setBounds(0, 0, bgWidth, bgHeight);

        panel.add(bgLabel);

        add(panel);
    }
}