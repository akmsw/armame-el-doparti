package FiestaDeFulbitoPackage;

//Importes de java.util
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ClassicPlayersMixer
{
	//Campos
	private HashMap<String,Player> team1,team2;
	private Random randomGenerator;
	
	//M�todos p�blicos
	
	//Constructor. Inicializa los HashMaps donde ser�n almacenados los jugadores, e inicializa el generador de n�meros Random
	public ClassicPlayersMixer()
	{
		team1 = new HashMap<String,Player>();
		team2 = new HashMap<String,Player>();
		
		randomGenerator = new Random();
	}
	
	//M�todo encargado de mezclar y distribuir los jugadores utilizando el m�todo privado 'forCycle'
	public void mixAndDistribute(ArrayList<Player> playersList, int maximumSetSize, Position position)
    {
		forCycle(1,playersList,maximumSetSize,position);
		forCycle(2,playersList,maximumSetSize,position);
    }
	
	//Getters
	public HashMap<String,Player> getTeam(int i)
	{
		if(i!=1 && i!=2) throw new IllegalArgumentException("Argumento inv�lido. Elija equipo '1' o equipo '2'.");
		else if(i==1) return team1;
		else return team2;
	}
	
	//M�todos privados
	
	//M�todo encargado de mezclar y distribuir los jugadores
	private void forCycle(int team, ArrayList<Player> playersList, int maximumSetSize, Position position)
	{
		int index; //Variable cuyo valor ser� asignado con el generador Random y servir� para tomar un jugador de forma aleatoria de los sets
		
		switch(team) //S�lo es posible armar dos equipos. En caso de querer asignar jugadores a un equipo de n�mero mayor o igual a 3 o menor a 1, se lanzar� una excepci�n de argumento inv�lido
		{
			case 1: //En caso de llenar el equipo 1...
			{
				for(int i=0; i<maximumSetSize/2; i++) //Se iterar� sobre la variable 'i' desde su valor inicial '0' hasta el valor '(maximumSetSize/2)-1', quedando en evidencia que la mitad de jugadores ir� para un equipo y la otra mitad para el otro
		        {
		        	index = randomGenerator.nextInt(playersList.size());
		            
		            team1.put((position.toString() + " #" +(i+1)),playersList.get(index)); //Se coloca en el HashMap del equipo 1 el jugador random
		            
		            playersList.remove(index); //El jugador agregado al equipo 1 es borrado de los sets para evitar repeticiones en el equipo 2
		        }
				
				break;
			}
			
			case 2: //Mismo funcionamiento que el caso 1
			{
				for(int i=0; i<maximumSetSize/2; i++)
		        {
		            index = randomGenerator.nextInt(playersList.size());
		            
		            team2.put((position.toString() + " #" +(i+1)),playersList.get(index));
		            
		            playersList.remove(index);
		        }
				
				break;
			}
			
			default: throw new IllegalArgumentException("El n�mero de equipo no es v�lido.");
		}
	}
}