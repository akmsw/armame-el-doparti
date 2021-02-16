/**
 * @author  Bonino, Francisco Ignacio.s
 * 
 * @version 1.0.0
 * 
 * @since   15/02/2021
 */

public abstract class Player {
    
    //Campos privados.
    private int rating;
    private String name;
    private Position position;

    /**
     * Constructor.
     * 
     * @param   name        El nombre del jugador.
     * @param   position    La posición del jugador.
     */
    protected Player(String name, Position position) {
        setName(name);
        setPosition(position);
    }

    //----------------------------------------Getters------------------------------------------

    /**
     * @return  El nombre del jugador.
     */
    private String getName() {
        return name;
    }

    /**
     * @return  El puntaje asignado al jugador.
     */
    private int getRating() {
        return rating;
    }

    /**
     * @return  La posición del jugador.
     */
    private Position getPosition() {
        return position;
    }

    //----------------------------------------Setters------------------------------------------

    /**
     * En este método se setea el nombre del jugador.
     * 
     * @param name  El nombre del jugador.
     * 
     * @throws  IllegalArgumentException    Si el nombre pasado por argumento es nulo.
     */
    public void setName(String name) throws IllegalArgumentException {
        if(name.equals("") || name.length() > 12) throw new IllegalArgumentException("El nombre del jugador no puede ser nulo o tener más de 12 caracteres.");
		else this.name = name;
    }

    /**
     * En este método se setea el puntaje del jugador.
     * 
     * @param rating    El puntaje del jugador.
     * 
     * @throws  IllegalArgumentException    Si el puntaje pasado por argumento es menor a 1 o mayor a 4.
     */
    public void setRating(int rating) {
        if(rating <= 0 || rating >= 5) throw new IllegalArgumentException("No es posible asignar como puntaje un número menor a 1 o un número mayor a 4. Reingrese el puntaje del jugador.");
		else this.rating = rating;
    }

    /**
     * En este método se setea la posición del jugador.
     * 
     * @param position  La posición del jugador.
     */
    public void setPosition(Position position) {
        this.position = position;
    }
}