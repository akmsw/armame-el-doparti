package FiestaDeFulbitoPackage;

public class Midfielder extends Player
{
	//Clase de mediocampistas
	private static Position position = Position.MIDFIELDER;
	
	//El constructor de esta clase asigna nombre y posición
	public Midfielder(String name) { super(name,position); }
}