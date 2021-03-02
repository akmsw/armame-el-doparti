/**
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 1.0.0
 * 
 * @since 28/02/2021
 */

import java.util.ArrayList;
import java.util.EnumMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MixFrame extends JFrame implements ActionListener {

    // Constantes privadas.
    private static final int frameWidth = 450; // Ancho de la ventana.
    private static final int frameHeight = 344; // Alto de la ventana.
    private static final String[] options = { "Agregar defensores centrales", "Agregar defensores laterales",
                                              "Agregar mediocampistas", "Agregar delanteros", "Agregar comodines" };

    // Campos privados.
    private ImageIcon icon;
    private JPanel panel;
    private JComboBox<String> comboBox;
    private ArrayList<String> data;
    private ArrayList<JTextField> textFieldCD, textFieldLD, textFieldMF, textFieldFW, textFieldWC;
    private EnumMap<Position, Integer> playersAmountMap;

    /**
     * Constructor. Aquí se crea la ventana de mezcla.
     * 
     * @throws  IOException Cuando hay un error de lectura en los archivos PDA.
     */
    public MixFrame(int playersAmount, ImageIcon icon) throws IOException {
        this.icon = icon;

        playersAmountMap = new EnumMap<>(Position.class);

        data = new ArrayList<>();

        textFieldCD = new ArrayList<>();
        textFieldLD = new ArrayList<>();
        textFieldMF = new ArrayList<>();
        textFieldFW = new ArrayList<>();
        textFieldWC = new ArrayList<>();

        collectPDData(playersAmount);

        int index = 0;

        for (Position position : Position.values()) {
            playersAmountMap.put(position, Integer.parseInt(data.get(index)));

            index++;
        }

        playersAmountMap.forEach((key, value) -> System.out.println("POSICIÓN " + key + ": " + value));

        data.clear();

        initializeComponents("Ingreso de jugadores - Fútbol " + playersAmount);

        setVisible(true);
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
    private void collectPDData(int fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("useful/FDF_F" + fileName + ".PDA"))) {
            String line;

            while ((line = br.readLine()) != null)
                if (line.matches("[CLMFW].>+.[0-9]"))
                    data.add(line.replaceAll("[A-Z].>+.", ""));
        }
    }

    /**
     * Este método se encarga de inicializar los componentes de la ventana de
     * comienzo.
     * 
     * @param frameTitle Título de la ventana.
     */
    private void initializeComponents(String frameTitle) {
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(frameTitle);
        setResizable(false);
        setIconImage(icon.getImage());

        panel = new JPanel();
        panel.setBounds(0, 0, frameWidth, frameHeight);
        panel.setLayout(null);
        
        addTextFields(Position.CENTRALDEFENDER, textFieldCD);
        addTextFields(Position.LATERALDEFENDER, textFieldLD);
        addTextFields(Position.MIDFIELDER, textFieldMF);
        addTextFields(Position.FORWARD, textFieldFW);
        addTextFields(Position.WILDCARD, textFieldWC);

        addComboBox();

        add(panel);
    }

    /**
     * Este método se encarga de agregar la lista desplegable
     * al frame, y setear el handler de eventos a la misma.
     */
    private void addComboBox() {
        comboBox = new JComboBox<>(options);

        comboBox.setBounds(5, 5, 200, 30);
        comboBox.addActionListener(this);

        updateOutput(comboBox.getSelectedItem().toString()); // Para que se muestre el output correspondiente
                                                             // al estado inicial del JComboBox.

        panel.add(comboBox);
    }

    /**
     * Este método se encarga de crear, almacenar y configurar
     * los campos de texto correspondientes a cada posición.
     * 
     * @param   position        Posición a buscar en el EnumMap.
     * @param   textFieldSet    Arreglo de campos de texto para cada posición.
     */
    private void addTextFields(Position position, ArrayList<JTextField> textFieldSet) {
        for (int i = 0; i < (playersAmountMap.get(position) * 2); i++) {
            JTextField aux = new JTextField(position + " #" + (i + 1));

            aux.setBounds(5, (45 * (i + 1)), 201, 30);

            textFieldSet.add(aux);
        }

        for (JTextField textField : textFieldSet)
            panel.add(textField);
    }

    /**
     * Handler para los eventos ocurridos de la lista desplegable.
     * Se trata la fuente del evento ocurrido como un JComboBox y
     * se trata como un String el item seleccionado en el mismo
     * para pasarlo al método updateOutput.
     * 
     * @param   e   Evento ocurrido (item seleccionado).
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        updateOutput((String)((JComboBox<?>)e.getSource()).getSelectedItem());
    }

    /**
     * Este método se encarga de actualizar lo mostrado en la
     * ventana en base al item seleccionado en la lista desplegable.
     * 
     * @param   text    Opción seleccionada del arreglo de Strings 'options'.
     */
    private void updateOutput(String text) {
        switch (text) {
            case "Agregar defensores centrales": {
                textFieldCD.forEach((tf) -> tf.setVisible(true));
                textFieldLD.forEach((tf) -> tf.setVisible(false));
                textFieldMF.forEach((tf) -> tf.setVisible(false));
                textFieldFW.forEach((tf) -> tf.setVisible(false));
                textFieldWC.forEach((tf) -> tf.setVisible(false));
                
                break;
            }

            case "Agregar defensores laterales": {
                textFieldCD.forEach((tf) -> tf.setVisible(false));
                textFieldLD.forEach((tf) -> tf.setVisible(true));
                textFieldMF.forEach((tf) -> tf.setVisible(false));
                textFieldFW.forEach((tf) -> tf.setVisible(false));
                textFieldWC.forEach((tf) -> tf.setVisible(false));

                break;
            }

            case "Agregar mediocampistas": {
                textFieldCD.forEach((tf) -> tf.setVisible(false));
                textFieldLD.forEach((tf) -> tf.setVisible(false));
                textFieldMF.forEach((tf) -> tf.setVisible(true));
                textFieldFW.forEach((tf) -> tf.setVisible(false));
                textFieldWC.forEach((tf) -> tf.setVisible(false));

                break;
            }

            case "Agregar delanteros": {
                textFieldCD.forEach((tf) -> tf.setVisible(false));
                textFieldLD.forEach((tf) -> tf.setVisible(false));
                textFieldMF.forEach((tf) -> tf.setVisible(false));
                textFieldFW.forEach((tf) -> tf.setVisible(true));
                textFieldWC.forEach((tf) -> tf.setVisible(false));
                
                break;
            }

            default: {
                textFieldCD.forEach((tf) -> tf.setVisible(false));
                textFieldLD.forEach((tf) -> tf.setVisible(false));
                textFieldMF.forEach((tf) -> tf.setVisible(false));
                textFieldFW.forEach((tf) -> tf.setVisible(false));
                textFieldWC.forEach((tf) -> tf.setVisible(true));
                
                break;
            }
        }
    }
}