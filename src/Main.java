/**
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 15/02/2021
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Scanner;

public class Main {

    // Campos privados.
    private static ArrayList<String> data;
    private static ArrayList<Player> CDSet, LDSet, MFSet, FWSet, WCSet;
    private static EnumMap<Position, Integer> playersAmountMap;
    private static Scanner monitorScan;

    /**
     * Método principal.
     * 
     * Aquí se instancia y ejecuta todo el programa.
     */
    public static void main(String[] args) {
        monitorScan = new Scanner(System.in);

        playersAmountMap = new EnumMap<>(Position.class);

        data = new ArrayList<>();
        CDSet = new ArrayList<>();
        LDSet = new ArrayList<>();
        MFSet = new ArrayList<>();
        FWSet = new ArrayList<>();
        WCSet = new ArrayList<>();

        System.out.print("Ingrese la cantidad de jugadores por equipo: ");

        int playersAmount = monitorScan.nextInt();

        try {
            MyLogger log = new MyLogger("useful/DISTRO.PDA");

            collectPDData(String.valueOf(playersAmount));

            int index = 0;

            for (Position position : Position.values()) {
                playersAmountMap.put(position, Integer.parseInt(data.get(index)));
                index++;
            }

            playersAmountMap.forEach((key, value) -> System.out.println("POSICIÓN " + key + ": " + value));

            data.clear();

            fillSet("defensor central", Position.CENTRALDEFENDER, CDSet);
            fillSet("defensor lateral", Position.LATERALDEFENDER, LDSet);
            fillSet("mediocampista", Position.MIDFIELDER, MFSet);
            fillSet("delantero", Position.FORWARD, FWSet);
            fillSet("comodín", Position.WILDCARD, WCSet);

            printSet(Position.CENTRALDEFENDER, CDSet);
            printSet(Position.LATERALDEFENDER, LDSet);
            printSet(Position.MIDFIELDER, MFSet);
            printSet(Position.FORWARD, FWSet);
            printSet(Position.WILDCARD, WCSet);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        monitorScan.close();
    }

    // ----------------------------------------Métodos privados---------------------------------

    /**
     * Este método rescata la cantidad de jugadores para cada posición por equipo
     * mediante expresiones regulares.
     * 
     * [CLMFW].>+.[0-9] : Matchea las líneas que comiencen con C, L, M, F, ó W,
     * estén seguidas por al menos un caracter >, y luego tengan algún número.
     * 
     * [A-Z].>+. : Matchea el trozo de la línea que no es un número.
     * 
     * @param   fileName    Nombre del archivo a buscar.
     * 
     * @throws  IOException Si el archivo no existe.
     */
    private static void collectPDData(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("useful/FDF_F" + fileName + ".PDA"))) {
            String line;

            while ((line = br.readLine()) != null)
                if (line.matches("[CLMFW].>+.[0-9]"))
                    data.add(line.replaceAll("[A-Z].>+.", ""));
        }
    }

    /**
     * Este método se encarga de rellenar un arreglo con un tipo de jugador específico.
     * 
     * @param   playerType  Posición del jugador (para mostrar al usuario).
     * @param   position    Posición del jugador.
     */
    private static void fillSet(String playerType, Position position, ArrayList<Player> set) {
        String playerName;

        for (int i = 0; i < playersAmountMap.get(position); i++) {
            System.out.print("Ingrese el nombre del " + playerType + " #" + i + ": ");
            
            playerName = monitorScan.next();

            set.add(new Player(playerName, position));
        }
    }

    /**
     * Este método se encarga de mostrar en pantalla un arreglo específico.
     * 
     * @param   position    Tipo de jugador.
     * @param   set         Arreglo a mostrar.
     */
    private static void printSet(Position position, ArrayList<Player> set) {
        for (Player player : set) {
            System.out.println("JUGADOR " + position + ": " + player.getName());
        }
    }
}