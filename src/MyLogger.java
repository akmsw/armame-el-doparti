/**
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 27/02/2021
 */

import java.io.File;
import java.io.IOException;

import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {
    private File file;
    private FileHandler handler;
    private Logger logger;

    /**
     * Constructor.
     * 
     * Aquí se construye el hilo logger que se encargará de mostrar en pantalla las estadísticas
     * de ejecución del programa, y de despertar a los hilos que queden encolados una vez que
     * se haya llegado a la condición de corte para evitar deadlocks.
     * 
     * @param fileName  Nombre del archivo log a crear.
     * @param pNet      Red de Petri del sistema para chequear la condición de corte del programa.
     * @param monitor   Monitor controlador del sistema
     * 
     * @throws IOException  En caso de ocurrir un error en la creación del archivo donde se escribirá la información.
     */
    public MyLogger(String fileName) throws IOException {
        file = new File(fileName);

        if(!file.createNewFile()) throw new IOException("LOG ALREADY CREATED");

        SimpleFormatter formatter = new SimpleFormatter();

        handler = new FileHandler(fileName, true);

        handler.setFormatter(formatter);
    }
}