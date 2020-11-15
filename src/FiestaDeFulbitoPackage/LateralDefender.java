package FiestaDeFulbitoPackage;

public class LateralDefender extends Player
{
	//Clase de defensores laterales
	private static Position position = Position.LATERALDEFENDER;
	
	//El constructor de esta clase asigna nombre y posición
	public LateralDefender(String name) { super(name,position); }
}