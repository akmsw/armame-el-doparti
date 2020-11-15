package FiestaDeFulbitoPackage;

public class Midfielder extends Player
{
	//Clase de mediocampistas
	private static Position position = Position.MIDFIELDER;
	
	//El constructor de esta clase asigna nombre y posici�n
	public Midfielder(String name) { super(name,position); }
}