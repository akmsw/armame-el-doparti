/**
 * @author  Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since   15/02/2020
 */

/**
 * Enum con las posibles posiciones para un jugador. Se excluye la posición de
 * arquero y es reemplazada por 'WILDCARD' por pedido del cliente.
 */
public enum Position {
    CENTRALDEFENDER, LATERALDEFENDER, MIDFIELDER, FORWARD, WILDCARD
}