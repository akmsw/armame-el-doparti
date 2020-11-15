package FiestaDeFulbitoPackage;

//Importes de java.util
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ByRatingPlayersMixer
{
	//Campos
	private ArrayList<Integer> differences;
	private HashMap<String,Player> team1,team2;
	private Random randomGenerator;
	private int team1Rating,team2Rating,partialSum1,partialSum2,partialSum3,partialSum4,partialSum5,partialSum6,partialSum7,partialSum8,partialSum9,partialSum10,partialSum11,partialSum12,diff1,diff2,diff3,diff4,diff5,diff6,bestDiff,index;
	
	//Métodos públicos
	
	//Constructor. Inicializa los HashMaps donde serán almacenados los jugadores, inicializa variables auxiliares e inicializa el generador de números aleatorios
	public ByRatingPlayersMixer()
	{
		team1 = new HashMap<String,Player>();
		team2 = new HashMap<String,Player>();
		
		differences = new ArrayList<Integer>();
		
		team1Rating = 0;
		team2Rating = 0;
		
		clearAuxVariables(); //Limpieza de variables auxiliares
		
		randomGenerator = new Random();
	}
	
	//Método encargado de mezclar y distribuir los jugadores entre los dos equipos de forma equitativa en base a los puntajes
	public void mixAndDistribute(ArrayList<Player> WCSet, ArrayList<Player> CDSet,ArrayList<Player> LDSet,ArrayList<Player> MFSet,ArrayList<Player> FWSet)
	{
		//Reparto aleatorio de comodines
		index = randomGenerator.nextInt(2);
		
		if(index==0)
		{
			team1.put(WCSet.get(0).getPosition().toString() + " #1",WCSet.get(0));
			team2.put(WCSet.get(1).getPosition().toString() + " #1",WCSet.get(1));
			
			team1Rating += WCSet.get(0).getRating();
			team2Rating += WCSet.get(1).getRating();
		}
		else
		{
			team1.put(WCSet.get(1).getPosition().toString() + " #1",WCSet.get(1));
			team2.put(WCSet.get(0).getPosition().toString() + " #1",WCSet.get(0));
			
			team1Rating += WCSet.get(1).getRating();
			team2Rating += WCSet.get(0).getRating();
		}
		
		//Reparto de jugadores que están en sets cuyo tamaño es 2
		mixBinarySets(CDSet);
		mixBinarySets(FWSet);
		
		///Reparto de jugadores que están en sets cuyo tamaño es 4
		mixBigSets(LDSet);
		mixBigSets(MFSet);
	}
	
	//Getters
	public HashMap<String,Player> getTeam(int i)
	{
		if(i!=1 && i!=2) throw new IllegalArgumentException("Argumento inv�lido. Elija equipo '1' o equipo '2'.");
		else if(i==1) return team1;
		else return team2;
	}
	
	//Métodos privados
	
	//Método encargado de retornar el puntaje de un equipo
	public int getTeamRating(int i)
	{
		if(i!=1 && i!=2) throw new IllegalArgumentException("Argumento inválido. Elija equipo '1' o equipo '2'.");
		else if(i==1) return team1Rating;
		else return team2Rating;
	}
	
	//Método encargado de setear en 0 las variables auxiliares
	private void clearAuxVariables()
	{
		partialSum1 = 0;
		partialSum2 = 0;
		partialSum3 = 0;
		partialSum4 = 0;
		partialSum5 = 0;
		partialSum6 = 0;
		partialSum7 = 0;
		partialSum8 = 0;
		partialSum9 = 0;
		partialSum10 = 0;
		partialSum11 = 0;
		partialSum12 = 0;
		bestDiff = 0;
		diff1 = 0;
		diff2 = 0;
		diff3 = 0;
		diff4 = 0;
		diff5 = 0;
		diff6 = 0;
	}
	
	//Método encargado de repartir entre los dos equipos los jugadores de sets cuyo tamaño sea 2 en base a los puntajes
	private void mixBinarySets(ArrayList<Player> Set)
	{
		//Comparación de las dos posibles sumas de puntuación y reparto de jugadores en base a la que genere una menor diferencia
		partialSum1 = team1Rating + Set.get(0).getRating();
		partialSum2 = team2Rating + Set.get(1).getRating();
		
		diff1 = java.lang.Math.abs(partialSum1-partialSum2);
		
		partialSum1 = team1Rating + Set.get(0).getRating();
		partialSum2 = team2Rating + Set.get(1).getRating();
		
		diff2 = java.lang.Math.abs(partialSum1-partialSum2);
		
		if(diff1>diff2)
		{
			//Asignación de jugadores
			team1.put(Set.get(1).getPosition().toString() + " #1",Set.get(1));
			team2.put(Set.get(0).getPosition().toString() + " #1",Set.get(0));
			
			//Incremento de los puntajes de los equipos
			team1Rating += Set.get(1).getRating();
			team2Rating += Set.get(0).getRating();
		}
		else
		{
			team1.put(Set.get(0).getPosition().toString() + " #1",Set.get(0));
			team2.put(Set.get(1).getPosition().toString() + " #1",Set.get(1));
			
			team1Rating += Set.get(0).getRating();
			team2Rating += Set.get(1).getRating();
		}
		
		clearAuxVariables();
	}
	
	//Método encargado de mezclar y distribuir los jugadores que pertenezcan a sets de tamaño igual a 4
	private void mixBigSets(ArrayList<Player> Set)
	{
		/*
		   Posibles combinaciones: 4!/(2!*(4-2)!) = (4 2) = 4C2 = 6
		   
		   Gráficamente, con cuatro jugadores (A, B, C, y D) las combinaciones posibles tomando de a dos jugadores para cada equipo, sin repetir jugadores, son:
		   
		   		EQ. 1	|	EQ. 2
		   		------------------
		   		A	B	|	C	D
		   		A	C	|	B	D
		   		A	D	|	B	C
		   		B	C	|	A	D
		   		B	D	|	A	C
		   		C	C	|	A	B
		   		
		   Donde A = Set.get(0), ... , D = Set.get(3)
		 */
		
		//Sumas parciales en el equipo 1
		partialSum1 = team1Rating + Set.get(0).getRating() + Set.get(1).getRating();  //A	B
		partialSum2 = team1Rating + Set.get(0).getRating() + Set.get(2).getRating();  //A	C
		partialSum3 = team1Rating + Set.get(0).getRating() + Set.get(3).getRating();  //A	D
		partialSum4 = team1Rating + Set.get(1).getRating() + Set.get(2).getRating();  //B	C
		partialSum5 = team1Rating + Set.get(1).getRating() + Set.get(3).getRating();  //B	D
		partialSum6 = team1Rating + Set.get(2).getRating() + Set.get(3).getRating();  //C	D
		
		//Sumas parciales en el equipo 2
		partialSum7 = team2Rating + Set.get(2).getRating() + Set.get(3).getRating();  //C	D
		partialSum8 = team2Rating + Set.get(1).getRating() + Set.get(3).getRating();  //B	D
		partialSum9 = team2Rating + Set.get(1).getRating() + Set.get(2).getRating();  //B	C
		partialSum10 = team2Rating + Set.get(0).getRating() + Set.get(3).getRating(); //A	D
		partialSum11 = team2Rating + Set.get(0).getRating() + Set.get(2).getRating(); //A	C
		partialSum12 = team2Rating + Set.get(0).getRating() + Set.get(1).getRating(); //A	B
		
		//Cálculo de diferencias parciales
		diff1 = java.lang.Math.abs(partialSum1-partialSum7);
		diff2 = java.lang.Math.abs(partialSum2-partialSum8);
		diff3 = java.lang.Math.abs(partialSum3-partialSum9);
		diff4 = java.lang.Math.abs(partialSum4-partialSum10);
		diff5 = java.lang.Math.abs(partialSum5-partialSum11);
		diff6 = java.lang.Math.abs(partialSum6-partialSum12);
		
		//Se agregan las diferencias calculadas a la lista de diferencias
		differences.add(diff1);
		differences.add(diff2);
		differences.add(diff3);
		differences.add(diff4);
		differences.add(diff5);
		differences.add(diff6);
		
		//Se obtiene el índice de la menor diferencia posible
		bestDiff = differences.indexOf(Collections.min(differences));
		
		//Se reparten los jugadores en base a la menor diferencia posible
		if(differences.get(bestDiff)==diff1)
		{
			//Asignación de jugadores
			team1.put(Set.get(0).getPosition().toString() + " #1",Set.get(0));
			team1.put(Set.get(1).getPosition().toString() + " #2",Set.get(1));
			
			team2.put(Set.get(2).getPosition().toString() + " #1",Set.get(2));
			team2.put(Set.get(3).getPosition().toString() + " #2",Set.get(3));
			
			//Incremento de los puntajes de los equipos
			team1Rating += Set.get(0).getRating() + Set.get(1).getRating();
			team2Rating += Set.get(2).getRating() + Set.get(3).getRating();
		}
		else if(differences.get(bestDiff)==diff2)
		{
			team1.put(Set.get(0).getPosition().toString() + " #1",Set.get(0));
			team1.put(Set.get(2).getPosition().toString() + " #2",Set.get(2));
			
			team2.put(Set.get(1).getPosition().toString() + " #1",Set.get(1));
			team2.put(Set.get(3).getPosition().toString() + " #2",Set.get(3));
			
			team1Rating += Set.get(0).getRating() + Set.get(2).getRating();
			team2Rating += Set.get(1).getRating() + Set.get(3).getRating();
		}
		else if(differences.get(bestDiff)==diff3)
		{
			team1.put(Set.get(0).getPosition().toString() + " #1",Set.get(0));
			team1.put(Set.get(3).getPosition().toString() + " #2",Set.get(3));
			
			team2.put(Set.get(1).getPosition().toString() + " #1",Set.get(1));
			team2.put(Set.get(2).getPosition().toString() + " #2",Set.get(2));
			
			team1Rating += Set.get(0).getRating() + Set.get(3).getRating();
			team2Rating += Set.get(1).getRating() + Set.get(2).getRating();
		}
		else if(differences.get(bestDiff)==diff4)
		{
			team1.put(Set.get(1).getPosition().toString() + " #1",Set.get(1));
			team1.put(Set.get(2).getPosition().toString() + " #2",Set.get(2));
			
			team2.put(Set.get(0).getPosition().toString() + " #1",Set.get(0));
			team2.put(Set.get(3).getPosition().toString() + " #2",Set.get(3));
			
			team1Rating += Set.get(1).getRating() + Set.get(2).getRating();
			team2Rating += Set.get(0).getRating() + Set.get(3).getRating();
		}
		else if(differences.get(bestDiff)==diff5)
		{
			team1.put(Set.get(1).getPosition().toString() + " #1",Set.get(1));
			team1.put(Set.get(3).getPosition().toString() + " #2",Set.get(3));
			
			team2.put(Set.get(0).getPosition().toString() + " #1",Set.get(0));
			team2.put(Set.get(2).getPosition().toString() + " #2",Set.get(2));
			
			team1Rating += Set.get(1).getRating() + Set.get(3).getRating();
			team2Rating += Set.get(0).getRating() + Set.get(2).getRating();
		}
		else
		{
			team1.put(Set.get(2).getPosition().toString() + " #1",Set.get(2));
			team1.put(Set.get(3).getPosition().toString() + " #2",Set.get(3));
			
			team2.put(Set.get(0).getPosition().toString() + " #1",Set.get(0));
			team2.put(Set.get(1).getPosition().toString() + " #2",Set.get(1));
			
			team1Rating += Set.get(2).getRating() + Set.get(3).getRating();
			team2Rating += Set.get(0).getRating() + Set.get(1).getRating();
		}
		
		clearAuxVariables();
	}
}