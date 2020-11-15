package FiestaDeFulbitoPackage;

public class MainClass
{
	//Clase principal
	
	//Campos
	private static FramesManager mainFrame = new FramesManager("");
	
	//El constructor de esta clase inicia todo el programa
	public static void main(String args[]) { mainFrame.setTitle("Fiesta de Fulbito - " + mainFrame.getSoftwareVersion()); }
}