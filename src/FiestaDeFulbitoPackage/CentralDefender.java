package FiestaDeFulbitoPackage;

public class CentralDefender extends Player
{
	//Clase de defensores centrales
	private static Position position = Position.CENTRALDEFENDER;
	
	//El constructor de esta clase asigna nombre y posición
	public CentralDefender(String name) { super(name,position); }
}