/**
 * Clase correspondiente a la ventana de ingreso
 * de nombres de jugadores y mezcla de los mismos
 * para el sorteo de los equipos.
 * 
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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MixFrame extends JFrame implements ActionListener {

    // Constantes privadas.
    private static final int frameWidth = 450; // Ancho de la ventana.
    private static final int frameHeight = 350; // Alto de la ventana.
    private static final String[] options = { "Agregar defensores centrales", "Agregar defensores laterales", // Opciones para el menú desplegable.
                                              "Agregar mediocampistas", "Agregar delanteros", "Agregar comodines" };

    // Campos privados.
    private int counter; // Contador para el área de texto donde se muestran los jugadores ingresados hasta el momento.
    private ImageIcon icon; // Icono para la ventana.
    private JPanel panel; // Panel para la ventana de mezcla.
    private JButton mixButton; // Botón para mezclar jugadores.
    private String previousName; // Variable auxiliar para eliminar ciertos jugadores.
    private JComboBox<String> comboBox; // Menú desplegable.
    private JTextArea textArea; // Área de texto donde se mostrarán los jugadores añadidos en tiempo real.
    private ArrayList<Player> setCD, setLD, setMF, setFW, setWC;
    private ArrayList<JTextField> textFieldCD, textFieldLD, textFieldMF, textFieldFW, textFieldWC; // Arreglos de campos de texto para ingresar nombres.
    private EnumMap<Position, Integer> playersAmountMap; // Mapa que asocia a cada posición un valor numérico
                                                         // (cuántos jugadores por posición por equipo).

    /**
     * Constructor. Aquí se crea la ventana de mezcla.
     * 
     * @throws IOException Cuando hay un error de lectura en los archivos PDA.
     */
    public MixFrame(int playersAmount, ImageIcon icon) throws IOException {
        this.icon = icon;

        playersAmountMap = new EnumMap<>(Position.class);

        textFieldCD = new ArrayList<>();
        textFieldLD = new ArrayList<>();
        textFieldMF = new ArrayList<>();
        textFieldFW = new ArrayList<>();
        textFieldWC = new ArrayList<>();

        setCD = new ArrayList<>();
        setLD = new ArrayList<>();
        setMF = new ArrayList<>();
        setFW = new ArrayList<>();
        setWC = new ArrayList<>();

        collectPDData(playersAmount);

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
     * ¡¡¡IMPORTANTE!!!
     * 
     * Si los archivos .PDA son modificados en cuanto a orden de las líneas
     * importantes (C >> NÚMERO, etc.), se debe tener en cuenta que
     * Position.values()[index] confía en que lo hallado se corresponde con el orden
     * en el que están declarados los valores en el enum Position. Idem, si se
     * cambian de orden los valores del enum Position, se deberá tener en cuenta que
     * Position.values()[index] confía en el orden en el que se leerán los datos de
     * los archivos .PDA y, por consiguiente, se deberá rever el orden de las líneas
     * importantes de dichos archivos.
     * 
     * @param fileName Nombre del archivo a buscar.
     * 
     * @throws IOException Si el archivo no existe.
     */
    private void collectPDData(int fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("useful/FDF_F" + fileName + ".PDA"))) {
            String line;
            int index = 0;

            while ((line = br.readLine()) != null)
                if (line.matches("[CLMFW].>+.[0-9]")) {
                    playersAmountMap.put(Position.values()[index], Integer.parseInt(line.replaceAll("[A-Z].>+.", "")));
                    index++;
                }
        }
    }

    /**
     * Este método se encarga de inicializar los componentes de la ventana de
     * mezcla.
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

        addTextFields(Position.CENTRALDEFENDER, textFieldCD, setCD);
        addTextFields(Position.LATERALDEFENDER, textFieldLD, setLD);
        addTextFields(Position.MIDFIELDER, textFieldMF, setMF);
        addTextFields(Position.FORWARD, textFieldFW, setFW);
        addTextFields(Position.WILDCARD, textFieldWC, setWC);

        addComboBox();

        addButtons();

        addTextArea();

        panel.setBackground(new Color(200, 200, 200));

        add(panel);
    }

    /**
     * Este método se encarga de agregar la lista desplegable al frame, y setear el
     * handler de eventos a la misma.
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
     * Este método se encarga de añadir al panel
     * los botones pertenecientes a este frame.
     */
    private void addButtons() {
        mixButton = new JButton("Mezclar");

        mixButton.setBounds(220, 274, 100, 30);
        mixButton.setEnabled(false);
        mixButton.setVisible(true);

        panel.add(mixButton);
    }

    /**
     * Este método se encarga de añadir al panel
     * el campo de texto de sólo lectura donde
     * se mostrarán los nombres de jugadores
     * ingresados por el usuario.
     */
    private void addTextArea() {
        textArea = new JTextArea();

        textArea.setBounds(215, 5, 110, 260);
        textArea.setBorder(BorderFactory.createBevelBorder(1));
        textArea.setEditable(false);
        textArea.setVisible(true);

        panel.add(textArea);
    }

    /**
     * Este método se encarga de crear, almacenar y configurar los campos de texto
     * correspondientes a cada posición.
     * 
     * @param position     Posición a buscar en el EnumMap.
     * @param textFieldSet Arreglo de campos de texto para cada posición.
     * @param playersSet   Arreglo de jugadores donde se almacenarán los nombres
     *                     ingresados en los campos de texto.
     */
    private void addTextFields(Position position, ArrayList<JTextField> textFieldSet, ArrayList<Player> playersSet) {
        for (int i = 0; i < (playersAmountMap.get(position) * 2); i++) {
            JTextField aux = new JTextField();

            aux.setBounds(5, (45 * (i + 1)), 201, 30);

            aux.addActionListener(new ActionListener() {
                int index; // Índice que indica el campo de texto donde se ingresó el nombre.

                /**
                 * En este método se evalúa que el string ingresado como nombre de jugador sea
                 * válido. Una vez validado, se chequea según el campo de texto si tal jugador
                 * está en el arreglo correspondiente o no. Si lo está, se lo reemplaza por un
                 * nuevo jugador con el nombre cambiado. Si no está, simplemente se crea un
                 * nuevo jugador con el nombre ingresado.
                 * 
                 * @param   e   Evento ocurrido (nombre ingresado).
                 */
                public void actionPerformed(ActionEvent e) {
                    JTextField auxTF = (JTextField)e.getSource();

                    for (index = 0; index < textFieldSet.size(); index++)
                        if (auxTF == textFieldSet.get(index))
                            break;

                    // Nombre sin espacios ni al principio ni al fin, en mayúsculas,
                    // y cualquier espacio intermedio es reemplazado por un guión bajo.
                    String name = aux.getText().trim().toUpperCase().replaceAll(" ", "_");

                    if (name.length() == 0 || name.length() > 12 || isEmptyString(name) || alreadyExists(name))
                        JOptionPane.showMessageDialog(null, "El nombre del jugador no puede estar vacío, tener más de 12 caracteres o estar repetido",
                                                            "¡Error!", JOptionPane.ERROR_MESSAGE, null);
                    else if (index >= playersSet.size()) {
                        playersSet.add(new Player(name, position));
                        previousName = name; // Se setea como nombre previo el nombre del jugador recién ingresado
                                             // en caso de no cambiar el foco ni de usar TAB / mouse para cambiar
                                             // el nombre del jugador ingresado en el mismo campo de texto.
                        updateTextArea();
                        checkSizes();
                    } else {
                        playersSet.removeIf(p -> p.getName().equals(previousName));
                        playersSet.add(new Player(name, position));
                        previousName = name; // Ídem al else-if superior.
                        updateTextArea();
                        checkSizes();
                    }
                }
            });

            aux.addMouseListener(new MouseAdapter() {
                /**
                 * Este método se encarga de servir como handle
                 * en los eventos en los que el foco cambie de
                 * componente en componente por medio de eventos
                 * de mouse como clicks o selección de texto.
                 * Este método entra en juego cuando el foco no
                 * cambia pero el texto del campo se selecciona
                 * para cambiarlo.
                 * 
                 * @param   e   Evento (cambio de foco).
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    previousName = aux.getText().trim().toUpperCase().replaceAll(" ", "_");
                }
            });

            aux.addFocusListener(new FocusAdapter() {
                /**
                 * Este método se encarga de servir como handle
                 * en los eventos en los que el foco cambie de
                 * componente en componente por medio de TABs.
                 * 
                 * @param   e   Evento (cambio de foco).
                 */
                @Override
                public void focusGained(final FocusEvent e) {
                    previousName = aux.getText().trim().toUpperCase().replaceAll(" ", "_");
                }
            });

            textFieldSet.add(aux);
        }

        for (JTextField textField : textFieldSet)
            panel.add(textField);
    }

    /**
     * Este método se encarga de chequear si la cantidad
     * de jugadores ingresados es 14 para poder habilitar
     * el botón de "Mezclar".
     */
    private void checkSizes() {
        if (setCD.size() == (playersAmountMap.get(Position.CENTRALDEFENDER) * 2) &&
            setLD.size() == (playersAmountMap.get(Position.LATERALDEFENDER) * 2) &&
            setMF.size() == (playersAmountMap.get(Position.MIDFIELDER) * 2) &&
            setFW.size() == (playersAmountMap.get(Position.FORWARD) * 2) &&
            setWC.size() == (playersAmountMap.get(Position.CENTRALDEFENDER) * 2))
            mixButton.setEnabled(true);
    }

    /**
     * Indica si una cadena está vacía o no.
     * Si la cadena está compuesta por caracteres
     * en blanco (espacios), se la tomará como vacía.
     * 
     * @param   string    Cadena a analizar
     * 
     * @return  Si la cadena está vacía o no.
     */
    private boolean isEmptyString(String string) {
    	char[] charArray = string.toCharArray();
    	
    	for (int i = 0; i < charArray.length; i++)
    		if (charArray[i] != ' ') return false;
    	
    	return true;
    }

    /**
     * Este método se encarga de chequear si un nombre está repetido
     * en un arreglo de jugadores.
     * 
     * @param   name        Nombre a chequear.
     * 
     * @return  Si hay algún jugador con el mismo nombre.
     */
    private boolean alreadyExists(String name)
    {
        if ((setCD.stream().filter(p -> p.getName().equals(name)).count() != 0) ||
            (setLD.stream().filter(p -> p.getName().equals(name)).count() != 0) ||
            (setMF.stream().filter(p -> p.getName().equals(name)).count() != 0) ||
            (setFW.stream().filter(p -> p.getName().equals(name)).count() != 0) ||
            (setWC.stream().filter(p -> p.getName().equals(name)).count() != 0))
            return true;
        else return false;
    }

    /**
     * 
     */
    private void updateTextArea() {
        counter = 0;

        textArea.setText(null);

        setCD.forEach(p -> { textArea.append(" " + (counter + 1) + ". " + p.getName() + "\n"); counter++; });
        setLD.forEach(p -> { textArea.append(" " + (counter + 1) + ". " + p.getName() + "\n"); counter++; });
        setMF.forEach(p -> { textArea.append(" " + (counter + 1) + ". " + p.getName() + "\n"); counter++; });
        setFW.forEach(p -> { textArea.append(" " + (counter + 1) + ". " + p.getName() + "\n"); counter++; });
        setWC.forEach(p -> { textArea.append(" " + (counter + 1) + ". " + p.getName() + "\n"); counter++; });
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
                textFieldCD.forEach(tf -> tf.setVisible(true));
                textFieldLD.forEach(tf -> tf.setVisible(false));
                textFieldMF.forEach(tf -> tf.setVisible(false));
                textFieldFW.forEach(tf -> tf.setVisible(false));
                textFieldWC.forEach(tf -> tf.setVisible(false));
                
                break;
            }

            case "Agregar defensores laterales": {
                textFieldCD.forEach(tf -> tf.setVisible(false));
                textFieldLD.forEach(tf -> tf.setVisible(true));
                textFieldMF.forEach(tf -> tf.setVisible(false));
                textFieldFW.forEach(tf -> tf.setVisible(false));
                textFieldWC.forEach(tf -> tf.setVisible(false));

                break;
            }

            case "Agregar mediocampistas": {
                textFieldCD.forEach(tf -> tf.setVisible(false));
                textFieldLD.forEach(tf -> tf.setVisible(false));
                textFieldMF.forEach(tf -> tf.setVisible(true));
                textFieldFW.forEach(tf -> tf.setVisible(false));
                textFieldWC.forEach(tf -> tf.setVisible(false));

                break;
            }

            case "Agregar delanteros": {
                textFieldCD.forEach(tf -> tf.setVisible(false));
                textFieldLD.forEach(tf -> tf.setVisible(false));
                textFieldMF.forEach(tf -> tf.setVisible(false));
                textFieldFW.forEach(tf -> tf.setVisible(true));
                textFieldWC.forEach(tf -> tf.setVisible(false));
                
                break;
            }

            default: {
                textFieldCD.forEach(tf -> tf.setVisible(false));
                textFieldLD.forEach(tf -> tf.setVisible(false));
                textFieldMF.forEach(tf -> tf.setVisible(false));
                textFieldFW.forEach(tf -> tf.setVisible(false));
                textFieldWC.forEach(tf -> tf.setVisible(true));
                
                break;
            }
        }
    }
}