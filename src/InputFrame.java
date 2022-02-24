/**
 * Clase correspondiente a la ventana de ingreso
 * de nombres de jugadores y mezcla de los mismos
 * para el sorteo de los equipos.
 * 
 * @author Bonino, Francisco Ignacio.
 * 
 * @version 3.0.0
 * 
 * @since 28/02/2021
 */

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class InputFrame extends JFrame implements ActionListener {

    /* ---------------------------------------- Constantes privadas ------------------------------ */

    private static final int MAX_NAME_LEN = 10; // Cantidad máxima de caracteres por nombre.

    /* ---------------------------------------- Campos privados  ---------------------------------- */

    // Arreglos de campos de texto para ingresar nombres.
    private ArrayList<JTextField> textFieldCD, textFieldLD, textFieldMF, textFieldFW, textFieldWC;

    // Lista con los arreglos de campos de texto para ingresar nombres.
    private ArrayList<ArrayList<JTextField>> textFields;
    private BackButton backButton;

    // Mapa que asocia a cada posición un valor numérico (cuántos jugadores por posición por equipo).
    private EnumMap<Position, Integer> playersAmountMap;
    private ImageIcon smallIcon;
    private Player[] setCD, setLD, setMF, setFW, setWC;
    private JButton mixButton;
    private JCheckBox anchor;
    private JComboBox<String> comboBox;
    private JFrame previousFrame;    
    private JPanel masterPanel, leftPanel, rightPanel;
    private JScrollPane scrollPane;
    private JTextArea textArea; // Área de texto donde se mostrarán los jugadores añadidos en tiempo real.

    /* ---------------------------------------- Campos públicos ---------------------------------- */

    public int distribution; // Tipo de distribución de jugadores elegida.
    public int playersAmount; // Cantidad de jugadores por equipo.
    public List<Player[]> playersSets; // Lista con los arreglos de jugadores.

    // Opciones para el menú desplegable.
    private static final String[] OPTIONS_COMBOBOX = { "Agregar defensores centrales",
                                                       "Agregar defensores laterales",
                                                       "Agregar mediocampistas",
                                                       "Agregar delanteros",
                                                       "Agregar arqueros" };

    // Opciones de distribución de jugadores.
    private static final String[] OPTIONS_MIX = { "Aleatoriamente", "Por puntajes" };

    /**
     * Constructor de la ventana de mezcla.
     * 
     * @param previousFrame Ventana fuente que crea la ventana InputFrame.
     * @param playersAmount Cantidad de jugadores por equipo.
     * 
     * @throws IOException Cuando hay un error de lectura en los archivos PDA.
     */
    public InputFrame(JFrame previousFrame, int playersAmount) throws IOException {
        this.previousFrame = previousFrame;
        this.playersAmount = playersAmount;

        playersAmountMap = new EnumMap<>(Position.class);

        collectPDData(playersAmount);

        initializeComponents("Ingreso de jugadores - Fútbol " + playersAmount);
    }

    /* ---------------------------------------- Métodos privados --------------------------------- */

    /**
     * Este método rescata la cantidad de jugadores para cada posición por equipo
     * mediante expresiones regulares.
     * 
     * [CLMFG].+>.+ : Matchea las líneas que comiencen con C, L, M, F, ó W,
     * seguido por al menos un caracter '>' (esta regex busca las líneas que
     * nos importan en el archivo .PDA).
     * 
     * (?!(?<=X)\\d). : Matchea el trozo de la línea que no sea un número que nos
     * interesa (el número que nos interesa ocuparía el lugar de la X).
     * 
     * ¡¡¡IMPORTANTE!!!
     * 
     * Si el archivo .PDA es modificado en cuanto a orden de las líneas importantes,
     * se debe tener en cuenta que Position.values()[index] confía en que lo hallado
     * se corresponde con el orden en el que están declarados los valores en el enum
     * Position. Idem, si se cambian de orden los valores del enum Position, se
     * deberá tener en cuenta que Position.values()[index] confía en el orden en el
     * que se leerán los datos del archivo .PDA y, por consiguiente, se deberá rever
     * el orden de las líneas importantes de dichos archivos.
     * 
     * @param playersAmount Cantidad de jugadores por equipo.
     * 
     * @throws IOException Si el archivo no existe.
     */
    private void collectPDData(int playersAmount) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("useful/DIST.PDA"))) {
            String line;

            int index = 0;

            while ((line = br.readLine()) != null)
                if (line.matches("[CLMFG].+>.+")) {
                    playersAmountMap.put(Position.values()[index],
                            Integer.parseInt(line.replaceAll("(?!(?<=" + playersAmount + ")\\d).", "")));
                    index++;
                }
        }
    }

    /**
     * Este método inicializa el conjunto de jugadores recibido con jugadores sin
     * nombre y con la posición recibida.
     * 
     * @param set      Arreglo de jugadores a inicializar.
     * @param position Posición de los jugadores del arreglo.
     */
    private void initializeSet(Player[] set, Position position) {
        for (int i = 0; i < set.length; i++)
            set[i] = new Player("", position);
    }

    /**
     * Este método se encarga de inicializar los componentes de la ventana de
     * mezcla.
     * 
     * @param frameTitle Título de la ventana.
     */
    private void initializeComponents(String frameTitle) {
        textFieldCD = new ArrayList<>();
        textFieldLD = new ArrayList<>();
        textFieldMF = new ArrayList<>();
        textFieldFW = new ArrayList<>();
        textFieldWC = new ArrayList<>();
        textFields = new ArrayList<>();

        setCD = new Player[(int) (playersAmountMap.get(Position.CENTRAL_DEFENDER) * 2)];
        setLD = new Player[(int) (playersAmountMap.get(Position.LATERAL_DEFENDER) * 2)];
        setMF = new Player[(int) (playersAmountMap.get(Position.MIDFIELDER) * 2)];
        setFW = new Player[(int) (playersAmountMap.get(Position.FORWARD) * 2)];
        setWC = new Player[(int) (playersAmountMap.get(Position.GOALKEEPER) * 2)];

        initializeSet(setCD, Position.CENTRAL_DEFENDER);
        initializeSet(setLD, Position.LATERAL_DEFENDER);
        initializeSet(setMF, Position.MIDFIELDER);
        initializeSet(setFW, Position.FORWARD);
        initializeSet(setWC, Position.GOALKEEPER);

        textFields.add(textFieldCD);
        textFields.add(textFieldLD);
        textFields.add(textFieldMF);
        textFields.add(textFieldFW);
        textFields.add(textFieldWC);

        playersSets = Arrays.asList(setCD, setLD, setMF, setFW, setWC);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(frameTitle);
        setIconImage(MainFrame.iconBall.getImage());

        masterPanel = new JPanel(new MigLayout("wrap 2"));
        leftPanel = new JPanel(new MigLayout("wrap"));
        rightPanel = new JPanel(new MigLayout("wrap"));

        addComboBox();
        addTextFields(Position.CENTRAL_DEFENDER, textFieldCD, setCD);
        addTextFields(Position.LATERAL_DEFENDER, textFieldLD, setLD);
        addTextFields(Position.MIDFIELDER, textFieldMF, setMF);
        addTextFields(Position.FORWARD, textFieldFW, setFW);
        addTextFields(Position.GOALKEEPER, textFieldWC, setWC);
        addTextArea();
        addButtons();
        addAnchorCheckBox();

        leftPanel.setBackground(Main.FRAMES_BG_COLOR);
        rightPanel.setBackground(Main.FRAMES_BG_COLOR);
        masterPanel.setBackground(Main.FRAMES_BG_COLOR);

        masterPanel.add(leftPanel, "west");
        masterPanel.add(rightPanel, "east");

        add(masterPanel);

        setResizable(false);

        pack();

        setLocationRelativeTo(null);
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
    private void addTextFields(Position position, ArrayList<JTextField> textFieldSet, Player[] playersSet) {
        for (int i = 0; i < (playersAmountMap.get(position) * 2); i++) {
            JTextField aux = new JTextField();

            aux.addActionListener(new ActionListener() {
                int index; // Índice que indica el campo de texto donde se ingresó el nombre.

                /**
                 * En este método se evalúa que la cadena ingresada como nombre de jugador sea
                 * válida. Una vez validada, se setea la cadena como el nombre del jugador
                 * correspondiente a tal campo de texto.
                 * 
                 * @param e Evento ocurrido (nombre ingresado).
                 */
                public void actionPerformed(ActionEvent e) {
                    JTextField auxTF = (JTextField) e.getSource();

                    for (index = 0; index < textFieldSet.size(); index++)
                        if (auxTF == textFieldSet.get(index))
                            break;

                    // Nombre sin espacios ni al principio ni al fin, en mayúsculas,
                    // y cualquier espacio intermedio es reemplazado por un guión bajo.
                    String name = aux.getText().trim().toUpperCase().replaceAll(" ", "_");

                    if (name.length() == 0 || name.length() > MAX_NAME_LEN
                        || isEmptyString(name) || alreadyExists(name))
                        JOptionPane.showMessageDialog(null,
                                "El nombre del jugador no puede estar vacío, tener más de " + MAX_NAME_LEN
                                + " caracteres o estar repetido",  "¡Error!", JOptionPane.ERROR_MESSAGE, null);
                    else {
                        playersSet[index].setName(name);

                        updateTextArea();

                        mixButton.setEnabled(checkMixButton());
                    }
                }
            });

            textFieldSet.add(aux);
        }

        for (int i = 0; i < textFieldSet.size(); i++)
            leftPanel.add(textFieldSet.get(i), "growx");
    }

    /**
     * Este método se encarga de agregar la lista desplegable al frame,
     * y setear el handler de eventos a la misma.
     */
    private void addComboBox() {
        comboBox = new JComboBox<>(OPTIONS_COMBOBOX);

        comboBox.addActionListener(this);

        // Se muestra el output correspondiente al estado inicial del JComboBox.
        updateOutput(comboBox.getSelectedItem().toString());

        leftPanel.add(comboBox, "growx");
    }

    /**
     * Este método se encarga de añadir los botones
     * al panel de ingreso de jugadores.
     */
    private void addButtons() {
        smallIcon = new ImageIcon(MainFrame.iconBall.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        mixButton = new JButton("Mezclar");
        backButton = new BackButton(InputFrame.this, previousFrame);

        mixButton.setEnabled(false);
        mixButton.setVisible(true);

        mixButton.addActionListener(new ActionListener() {
            /**
             * Este método se encarga de tomar el criterio de búsqueda especificado por el
             * usuario. Además, se chequea si se deben anclar jugadores y se trabaja en base
             * a eso.
             * 
             * @param e Evento (criterio elegido).
             */
            @Override
            @SuppressWarnings("unused")
            public void actionPerformed(ActionEvent e) {
                distribution = JOptionPane.showOptionDialog(null,
                        "Seleccione el criterio de distribución de jugadores", "Antes de continuar...", 2,
                        JOptionPane.QUESTION_MESSAGE, smallIcon, OPTIONS_MIX, OPTIONS_MIX[0]);

                if (distribution != JOptionPane.CLOSED_OPTION) {
                    if (anchor.isSelected()) {
                        AnchorageFrame anchorageFrame = new AnchorageFrame(InputFrame.this, playersAmount);

                        anchorageFrame.setVisible(true);
                    } else {
                        ResultFrame resultFrame = new ResultFrame(InputFrame.this, InputFrame.this);
                    }

                    InputFrame.this.setVisible(false);
                }
            }
        });

        rightPanel.add(mixButton, "growx, span");
        rightPanel.add(backButton, "growx, span");
    }

    /**
     * Este método se encarga de añadir al panel el campo de texto de sólo lectura
     * donde se mostrarán en tiempo real los nombres de jugadores ingresados por el
     * usuario.
     */
    private void addTextArea() {
        textArea = new JTextArea();

        textArea.setBorder(BorderFactory.createBevelBorder(1));
        textArea.setEditable(false);
        textArea.setVisible(true);

        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                     JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        rightPanel.add(scrollPane, "grow, span 1 55");

        updateTextArea();
    }

    /**
     * Este método se encarga de actualizar lo mostrado en la ventana en base al
     * ítem seleccionado en la lista desplegable. Se togglea la visibilidad de las
     * imágenes y de los campos de texto de ingreso de jugadores.
     * 
     * @param text Opción seleccionada del arreglo de Strings 'OPTIONS_COMBOBOX'.
     */
    private void updateOutput(String text) {
        for (int i = 0; i < OPTIONS_COMBOBOX.length; i++)
            if (text.equals(OPTIONS_COMBOBOX[i])) {
                for (int j = 0; j < textFields.size(); j++) {
                    final int i2 = i;
                    final int j2 = j;

                    // TODO: Check this method.

                    textFields.get(j).forEach(tf -> tf.setVisible(j2 == i2));
                }

                return;
            }
    }

    /**
     * Indica si una cadena está vacía o no. Si la cadena está compuesta por
     * caracteres en blanco (espacios), se la tomará como vacía.
     * 
     * @param string Cadena a analizar
     * 
     * @return Si la cadena está vacía o no.
     */
    private boolean isEmptyString(String string) {
        char[] charArray = string.toCharArray();

        for (int i = 0; i < charArray.length; i++)
            if (charArray[i] != ' ')
                return false;

        return true;
    }

    /**
     * Este método se encarga de chequear si un nombre está repetido en un arreglo
     * de jugadores.
     * 
     * @param name Nombre a chequear.
     * 
     * @return Si hay algún jugador con el mismo nombre.
     */
    private boolean alreadyExists(String name) {
        for (int i = 0; i < playersSets.size(); i++)
            for (int j = 0; j < playersSets.get(i).length; j++)
                if (playersSets.get(i)[j].getName().equals(name))
                    return true;

        return false;
    }

    /**
     * Este método se encarga de actualizar el texto mostrado en el campo de sólo
     * lectura. Se muestran los jugadores ingresados en el orden en el que estén
     * posicionados en sus respectivos arreglos. El orden en el que se muestran es:
     * Defensores centrales > Defensores laterales > Mediocampistas > Delanteros >
     * Arqueros.
     */
    private void updateTextArea() {
        int counter = 0;

        textArea.setText(null);

        for (int i = 0; i < playersSets.size(); i++)
            for (int j = 0; j < playersSets.get(i).length; j++)
                if (!playersSets.get(i)[j].getName().equals("")) {
                    if (i == 0 && j == 0)
                        textArea.append(" " + (counter + 1) + ". " + playersSets.get(i)[j].getName());
                    else
                        textArea.append("\n " + (counter + 1) + ". " + playersSets.get(i)[j].getName());

                    counter++;
                }
    }

    /**
     * Este método se encarga de chequear si se han ingresado los nombres de todos
     * los jugadores para habilitar el botón de mezcla.
     */
    private boolean checkMixButton() {
        for (int i = 0; i < playersSets.size(); i++)
            for (int j = 0; j < playersSets.get(i).length; j++)
                if (playersSets.get(i)[j].getName().equals(""))
                    return false;

        return true;
    }

    /**
     * Este método se encarga de agregar el checkbox de anclaje de jugadores en el
     * panel del frame.
     */
    private void addAnchorCheckBox() {
        anchor = new JCheckBox("Anclar jugadores", false);

        anchor.setFont(Main.PROGRAM_FONT.deriveFont(Main.CB_FONT_SIZE));
        anchor.setBackground(Main.FRAMES_BG_COLOR);
        anchor.setVisible(true);

        rightPanel.add(anchor, "growx");
    }

    /* ---------------------------------------- Métodos públicos --------------------------------- */

    /**
     * Handler para los eventos ocurridos de la lista desplegable. Se trata la
     * fuente del evento ocurrido como un JComboBox y se trata como un String el
     * ítem seleccionado en el mismo para pasarlo al método updateOutput.
     * 
     * @param e Evento ocurrido (ítem seleccionado).
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        updateOutput((String) ((JComboBox<?>) e.getSource()).getSelectedItem());
    }

    /**
     * @return La cantidad de jugadores por equipo.
     */
    public int getPlayersAmount() {
        return playersAmount;
    }

    /**
     * @return El tipo de distribución de jugadores elegida.
     */
    public int getDistribution() {
        return distribution;
    }

    /**
     * @return La lista con los arreglos de jugadores.
     */
    public List<Player[]> getPlayersSets() {
        return playersSets;
    }
}