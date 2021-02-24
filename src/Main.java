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
import java.util.Scanner;

public class Main {

    //Campos privados.
    private static int playersAmount;
    private static ArrayList<String> vector;

    public static void main(String[] args) {
        Scanner monitorScan = new Scanner(System.in);
        
        vector = new ArrayList<String>();

        System.out.print("Ingrese la cantidad de jugadores por equipo: ");
        
        playersAmount = monitorScan.nextInt();

        try {
            printFileInfo(String.valueOf(playersAmount));
        } catch (IOException e) {
            e.printStackTrace();
        }

        monitorScan.close();
    }

    /**
     * Este método rescata la cantidad de jugadores para
     * cada posición por equipo mediante expresiones regulares.
     * 
     * (C|L|M|F|W).>+.[0-9] :   Matchea las líneas que comiencen
     *                          con C, L, M, F, ó W, estén seguidas
     *                          por al menos un caracter >, y luego
     *                          tengan algún número.
     * 
     * [A-Z].>+.            :   Matchea el trozo de la línea que
     *                          no es un número.
     * 
     * @param   fileName    Nombre del archivo a buscar.
     * 
     * @throws IOException  Si el archivo no existe.
     */
    private static void printFileInfo(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("useful/FDF_F" + fileName + ".PDA"))) {
            String line;

            while ((line = br.readLine()) != null)
                if (line.matches("(C|L|M|F|W).>+.[0-9]"))
                    vector.add(line.replaceAll("[A-Z].>+.", ""));
         }
    }
}