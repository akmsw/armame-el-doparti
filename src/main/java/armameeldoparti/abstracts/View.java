package armameeldoparti.abstracts;

import javax.swing.JFrame;

/**
 * Clase abstracta que especifica los métodos básicos para las vistas.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 28/07/2022
 */
public abstract class View extends JFrame {

  // ---------------------------------------- Métodos protegidos abstractos ---------------------

  /**
   * Inicializa y muestra la interfaz gráfica de la ventana.
   */
  protected abstract void initializeInterface();

  /**
   * Coloca los botones en los paneles de la ventana.
   */
  protected abstract void addButtons();
}