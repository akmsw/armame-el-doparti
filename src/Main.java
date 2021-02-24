/**
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 15/02/2021
 */

import Jama.Matrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static int playersAmount;
    private static int centralDefendersAmount, lateralDefendersAmount, midfieldersAmount, forwardsAmount, wildcardsAmount;
    private static ArrayList<String> vector;

    public static void main(String[] args) throws IOException {
        Scanner monitorScan = new Scanner(System.in);

        vector = new ArrayList<String>();

        System.out.print("Ingrese la cantidad de jugadores por equipo: ");
        
        playersAmount = monitorScan.nextInt();

        printFileInfo(String.valueOf(playersAmount));

        monitorScan.close();
    }

    private static void printFileInfo(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("useful/FDF_F" + fileName + ".PDA"))) {
            String line;

            while ((line = br.readLine()) != null)
                if (line.matches("(C|L|M|F|W).>+.[0-9]"))
                    vector.add(line.replaceAll("[A-Z].>+.", ""));
            
            centralDefendersAmount = Integer.parseInt(vector.get(0));
            lateralDefendersAmount = Integer.parseInt(vector.get(1));
            midfieldersAmount = Integer.parseInt(vector.get(2));
            forwardsAmount = Integer.parseInt(vector.get(3));
            wildcardsAmount = Integer.parseInt(vector.get(4));

            System.out.println("CD: " + centralDefendersAmount);
            System.out.println("LD: " + lateralDefendersAmount);
            System.out.println("M: " + midfieldersAmount);
            System.out.println("F: " + forwardsAmount);
            System.out.println("W: " + wildcardsAmount);
         }
    }
}