package FiestaDeFulbitoCOPIAPackage;

public class Player
{
	//Clase general de los jugadores
	
	//Campos
	private int rating;
	private String name;
	private Position position;
	
	//Métodos públicos
	
	//Constructor. Asigna nombre y posición, inicializa el puntaje del jugador por defecto en '1'
	public Player(String name, Position position)
	{
		setName(name);
		setPosition(position);
		setRating(1);
	}
	
	//Getters
	public int getRating() { return rating; }
	
	public String getName() { return name; }
	
	public Position getPosition() { return position; }
	
	//Setters
	public void setRating(int rating) //No se admitirán puntajes que no estén en el rango [1,4].
	{
		if(rating<=0 || rating>=5) throw new IllegalArgumentException("No es posible asignar como puntaje un número menor a 1 o un número mayor a 4. Reingrese el puntaje del jugador.");
		else this.rating = rating;
	}
	
	public void setName(String name) //No se admitirán nombres nulos o vacíos.
	{
		if(name.equals(null) || name.equals("")) throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
		else this.name = name;
	}
	
	public void setPosition(Position position) //No se admitirán posiciones que no hayan sido especificadas en el enum 'Position'
	{
		if(position.equals(null)) throw new IllegalArgumentException("Posición inválida.");
		else this.position = position;
	}
}