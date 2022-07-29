package armameeldoparti.interfaces;

import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * Interfaz que especifica los métodos básicos para la interacción
 * entre los controladores y sus vistas asignadas.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 28/07/2022
 */
public interface Controller {

  // ---------------------------------------- Métodos públicos abstractos -----------------------

  /**
   * Hace visible la ventana controlada.
   */
  void showView();

  /**
   * Hace invisible la ventana controlada.
   */
  void hideView();

  /**
   * Reinicia la ventana controlada a sus valores por defecto.
   */
  void resetView();

  /**
   * Centra la ventana en la pantalla principal.
   *
   * <p>EN LINUX: Si no se utiliza la combinación de {@code setLocation}
   * con Toolkit y luego {@code setLocationRelativeTo} con null, la ventana
   * no se centra correctamente, presentando una desviación variable.
   *
   * @param frame Ventana a centrar.
   */
  static void centerView(JFrame frame) {
    frame.setLocation((Toolkit.getDefaultToolkit()
                              .getScreenSize().width - frame.getSize().width) / 2,
                      (Toolkit.getDefaultToolkit()
                              .getScreenSize().height - frame.getSize().height) / 2);
    frame.setLocationRelativeTo(null);
  }
}