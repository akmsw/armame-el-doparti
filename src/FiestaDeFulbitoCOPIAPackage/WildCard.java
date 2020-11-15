package FiestaDeFulbitoCOPIAPackage;

public class WildCard extends Player
{
	//Clase de comodines
	private static Position position = Position.WILDCARD;
	
	//El constructor de esta clase asigna nombre y posición
	public WildCard(String name) { super(name,position); }
}