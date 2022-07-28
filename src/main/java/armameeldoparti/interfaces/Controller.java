package armameeldoparti.interfaces;

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
}