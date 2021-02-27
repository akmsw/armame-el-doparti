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
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    // Campos privados.
    private static ArrayList<String> data;

    public static void main(String[] args) {
        Scanner monitorScan = new Scanner(System.in);

        data = new ArrayList<String>();

        HashMap<Position, Integer> playersMap = new HashMap<Position, Integer>();

        System.out.print("Ingrese la cantidad de jugadores por equipo: ");

        int playersAmount = monitorScan.nextInt();

        try {
            collectData(String.valueOf(playersAmount));

            int index = 0;

            for (Position position : Position.values()) {
                playersMap.put(position, Integer.parseInt(data.get(index)));
                index++;
            }

            data.clear();

            playersMap.forEach((key, value) -> System.out.println("POSICIÓN " + key + ": " + value));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        monitorScan.close();
    }

    //----------------------------------------Métodos privados---------------------------------

    /**
     * Este método rescata la cantidad de jugadores para cada posición por equipo
     * mediante expresiones regulares.
     * 
     * (C|L|M|F|W).>+.[0-9] : Matchea las líneas que comiencen con C, L, M, F, ó W,
     * estén seguidas por al menos un caracter >, y luego tengan algún número.
     * 
     * [A-Z].>+. : Matchea el trozo de la línea que no es un número.
     * 
     * @param fileName Nombre del archivo a buscar.
     * 
     * @throws IOException Si el archivo no existe.
     */
    private static void collectData(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("useful/FDF_F" + fileName + ".PDA"))) {
            String line;

            while ((line = br.readLine()) != null)
                if (line.matches("(C|L|M|F|W).>+.[0-9]"))
                    data.add(line.replaceAll("[A-Z].>+.", ""));
        }
    }
}