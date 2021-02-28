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
     * Aquí se construye el objeto logger que se encargará de escribir en un archivo
     * plano de texto información útil que será utilizada por el algoritmo
     * desarrollado en C.
     * 
     * @param fileName Nombre del archivo log a crear.
     * 
     * @throws IOException En caso de ocurrir un error en la creación del archivo
     *                     donde se escribirá la información.
     */
    public MyLogger(String fileName) throws IOException {
        file = new File(fileName);

        if(!file.exists())
            file.createNewFile();

        SimpleFormatter formatter = new SimpleFormatter();

        handler = new FileHandler(fileName, true);

        handler.setFormatter(formatter);
    }
}