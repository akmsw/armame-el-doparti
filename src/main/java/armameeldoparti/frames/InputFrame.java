package armameeldoparti.frames;

import armameeldoparti.utils.BackButton;
import armameeldoparti.utils.Main;
import armameeldoparti.utils.Player;
import armameeldoparti.utils.Position;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import net.miginfocom.swing.MigLayout;

/**
 * Clase correspondiente a la ventana de ingreso de nombres de jugadores y de parámetros de distribución.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 28/02/2021
 */
public class InputFrame extends JFrame {

    // ---------------------------------------- Constantes privadas -------------------------------

    /**
     * Cantidad máxima de caracteres por nombre.
     */
    private static final int MAX_NAME_LEN = 10;

    /**
     * Cantidad de filas para el área de texto.
     */
    private static final int TEXT_AREA_ROWS = 14;

    /**
     * Cantidad de columnas para el área de texto.
     */
    private static final int TEXT_AREA_COLUMNS = 12;

    /**
     * Expresión regular para validación de nombres.
     */
    private static final String NAMES_VALIDATION_REGEX = "[a-z A-ZÁÉÍÓÚáéíóúñÑ]+";

    /**
     * Expresión regular para obtener información del archivo .pda.
     */
    private static final String PDA_DATA_RETRIEVE_REGEX = "[CLMFG].+>.+";

    /**
     * Nombre del archivo .pda.
     */
    private static final String PDA_FILENAME = "dist.pda";

    /**
     * Posiciones para la lista desplegable.
     */
    private static final String[] OPTIONS_COMBOBOX = {"Defensores centrales",
                                                      "Defensores laterales",
                                                      "Mediocampistas",
                                                      "Delanteros",
                                                      "Arqueros"};

    /**
     * Opciones de distribución de jugadores.
     */
    private static final String[] OPTIONS_MIX = {"Aleatoriamente", "Por puntuaciones"};

    // ---------------------------------------- Campos privados -----------------------------------

    private int totalAnchorages;

    private List<List<JTextField>> textFields;

    private JButton mixButton;

    private JComboBox<String> comboBox;

    private JFrame previousFrame;

    private JPanel leftPanel;
    private JPanel rightPanel;

    private JTextArea textArea;

    // ---------------------------------------- Constructor ---------------------------------------

    /**
     * Construye una ventana de ingreso de jugadores.
     *
     * @param previousFrame  Ventana fuente que crea la ventana InputFrame.
     * @param playersPerTeam Cantidad de jugadores por equipo.
     *
     * @throws IOException Cuando hay un error de lectura en el archivo .pda.
     */
    public InputFrame(JFrame previousFrame, int playersPerTeam) throws IOException {
        this.previousFrame = previousFrame;

        setTotalAnchorages(0);
        collectPDData();
        initializeGUI("Ingreso de jugadores - Fútbol " + playersPerTeam);
    }

    // ---------------------------------------- Métodos públicos ----------------------------------

    // ---------------------------------------- Getters -------------------------------------------

    /**
     * @return Cantidad total de anclajes existentes.
     */
    public int getTotalAnchorages() {
        return totalAnchorages;
    }

    // ---------------------------------------- Setters -------------------------------------------

    /**
     * @param totalAnchorages Cantidad total de anclajes existentes.
     */
    public void setTotalAnchorages(int totalAnchorages) {
        this.totalAnchorages = totalAnchorages;
    }

    // ---------------------------------------- Métodos privados ----------------------------------

    /**
     * Obtiene la cantidad de jugadores para cada posición por equipo
     * mediante expresiones regulares.
     * <p>
     * [CLMFG].+>.+ : Obtiene las líneas que comiencen con C, L, M, F, ó W,
     * seguido por al menos un caracter '>' (busca las líneas que nos importan en
     * el archivo .pda).
     * <p>
     * (?!(?<=X)\\d). : Obtiene el trozo de la línea que no sea un número que nos
     * interesa (el número que nos interesa ocuparía el lugar de la X).
     * <p>
     * ¡¡¡IMPORTANTE!!!
     * <p>
     * Si el archivo .pda es modificado en cuanto a orden de las líneas importantes,
     * se debe tener en cuenta que Position.values()[index] confía en que lo hallado
     * se corresponde con el orden en el que están declarados los valores en el enum
     * Position. Idem, si se cambian de orden los valores del enum Position, se
     * deberá tener en cuenta que Position.values()[index] confía en el orden en el
     * que se leerán los datos del archivo .pda y, por consiguiente, se deberá rever
     * el orden de las líneas importantes de dichos archivos.
     *
     * @throws IOException Si el archivo no existe.
     */
    private void collectPDData() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass()
                                                                              .getClassLoader()
                                                                              .getResourceAsStream(PDA_FILENAME)))) {
            int index = 0;

            String line;

            while ((line = br.readLine()) != null) {
                if (line.matches(PDA_DATA_RETRIEVE_REGEX)) {
                    Main.getPlayersAmountMap().put(Position.values()[index],
                                                   Integer.parseInt(line.replaceAll("(?!(?<="
                                                                                    + Main.PLAYERS_PER_TEAM
                                                                                    + ")\\d).", "")));
                    index++;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Inicializa y muestra la interfaz gráfica de esta ventana.
     *
     * @param frameTitle Título de la ventana.
     */
    private void initializeGUI(String frameTitle) {
        List<JTextField> textFieldCD = new ArrayList<>();
        List<JTextField> textFieldLD = new ArrayList<>();
        List<JTextField> textFieldMF = new ArrayList<>();
        List<JTextField> textFieldFW = new ArrayList<>();
        List<JTextField> textFieldGK = new ArrayList<>();

        List<Player> setCD = new ArrayList<>();
        List<Player> setLD = new ArrayList<>();
        List<Player> setMF = new ArrayList<>();
        List<Player> setFW = new ArrayList<>();
        List<Player> setGK = new ArrayList<>();

        initializeSet(setCD, Position.CENTRAL_DEFENDER, Main.getPlayersAmountMap().get(Position.CENTRAL_DEFENDER) * 2);
        initializeSet(setLD, Position.LATERAL_DEFENDER, Main.getPlayersAmountMap().get(Position.LATERAL_DEFENDER) * 2);
        initializeSet(setMF, Position.MIDFIELDER, Main.getPlayersAmountMap().get(Position.MIDFIELDER) * 2);
        initializeSet(setFW, Position.FORWARD, Main.getPlayersAmountMap().get(Position.FORWARD) * 2);
        initializeSet(setGK, Position.GOALKEEPER, Main.getPlayersAmountMap().get(Position.GOALKEEPER) * 2);

        textFields = Arrays.asList(textFieldCD, textFieldLD, textFieldMF, textFieldFW, textFieldGK);

        Main.setPlayersSets(new TreeMap<>());
        Main.getPlayersSets().put(Position.CENTRAL_DEFENDER, setCD);
        Main.getPlayersSets().put(Position.LATERAL_DEFENDER, setLD);
        Main.getPlayersSets().put(Position.MIDFIELDER, setMF);
        Main.getPlayersSets().put(Position.FORWARD, setFW);
        Main.getPlayersSets().put(Position.GOALKEEPER, setGK);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(frameTitle);
        setIconImage(MainFrame.ICON.getImage());

        leftPanel = new JPanel(new MigLayout("wrap"));
        rightPanel = new JPanel(new MigLayout("wrap"));

        JPanel masterPanel = new JPanel(new MigLayout("wrap 2"));

        addComboBox();
        addTextFields(Position.CENTRAL_DEFENDER, textFieldCD, setCD);
        addTextFields(Position.LATERAL_DEFENDER, textFieldLD, setLD);
        addTextFields(Position.MIDFIELDER, textFieldMF, setMF);
        addTextFields(Position.FORWARD, textFieldFW, setFW);
        addTextFields(Position.GOALKEEPER, textFieldGK, setGK);
        addTextArea();
        addButtons();
        addAnchorCheckBox();
        updateTextFields(comboBox.getSelectedItem()
                                 .toString()); // Ajustamos el panel izquierdo al item inicial de la lista desplegable

        masterPanel.add(leftPanel, "west");
        masterPanel.add(rightPanel, "east");

        add(masterPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Llena el conjunto de jugadores recibido con jugadores sin nombre ni puntuación, y con la posición especificada.
     *
     * @param set      Arreglo de jugadores a inicializar.
     * @param position Posición de los jugadores del arreglo.
     * @param capacity Capacidad del arreglo.
     */
    private void initializeSet(List<Player> set, Position position, int capacity) {
        for (int i = 0; i < capacity; i++) {
            set.add(new Player("", position));
        }
    }

    /**
     * Crea, almacena y configura los campos de texto correspondientes a cada posición.
     *
     * @param position     Posición de los jugadores.
     * @param textFieldSet Arreglo de campos de texto para dicha posición.
     * @param playersSet   Arreglo de jugadores con dicha posición.
     */
    private void addTextFields(Position position, List<JTextField> textFieldSet, List<Player> playersSet) {
        for (int i = 0; i < (Main.getPlayersAmountMap().get(position) * 2); i++) {
            JTextField tf = new JTextField();

            tf.addActionListener(e -> {
                int index = textFieldSet.indexOf(e.getSource());

                if (!(Pattern.matches(NAMES_VALIDATION_REGEX, tf.getText()))) {
                    JOptionPane.showMessageDialog(null,
                                          "El nombre del jugador debe estar formado sólo por letras de la A a la Z",
                                          "¡Error!", JOptionPane.ERROR_MESSAGE, null);
                } else {
                    String name = tf.getText().trim().toUpperCase().replace(" ", "_");

                    if ((name.length() > MAX_NAME_LEN) || name.isBlank()
                        || name.isEmpty() || alreadyExists(name)) {
                        JOptionPane.showMessageDialog(null,
                                                      "El nombre del jugador no puede estar vacío, tener más de "
                                                      + MAX_NAME_LEN + " caracteres, o estar repetido",
                                                      "¡Error!", JOptionPane.ERROR_MESSAGE, null);

                        tf.setText("");
                    } else {
                        playersSet.get(index).setName(name);

                        updateTextArea();

                        // Se habilita el botón de mezcla sólo cuando todos los jugadores tengan nombre
                        mixButton.setEnabled(!alreadyExists(""));
                    }
                }
            });

            textFieldSet.add(tf);
        }
    }

    /**
     * Agrega la lista desplegable y le establece el oyente de eventos.
     */
    private void addComboBox() {
        comboBox = new JComboBox<>(OPTIONS_COMBOBOX);

        comboBox.setSelectedIndex(0);
        comboBox.addActionListener(e -> updateTextFields((String) ((JComboBox<?>) e.getSource()).getSelectedItem()));

        leftPanel.add(comboBox, "growx");
    }

    /**
     * Añade los botones al panel de la ventana.
     */
    private void addButtons() {
        BackButton backButton = new BackButton(InputFrame.this, previousFrame, null);

        mixButton = new JButton("Distribuir");

        mixButton.setEnabled(false);
        mixButton.setVisible(true);
        mixButton.addActionListener(e -> {
            Main.setDistribution(JOptionPane.showOptionDialog(null,
                                                      "Seleccione el criterio de distribución de jugadores",
                                                              "Antes de continuar...", 2,
                                                              JOptionPane.QUESTION_MESSAGE, MainFrame.SCALED_ICON,
                                                              OPTIONS_MIX, OPTIONS_MIX[0]));

            if (Main.getDistribution() != JOptionPane.CLOSED_OPTION) {
                if (Main.thereAreAnchorages()) {
                    AnchoragesFrame anchorageFrame = new AnchoragesFrame(InputFrame.this, Main.PLAYERS_PER_TEAM);

                    anchorageFrame.setVisible(true);
                } else if (Main.getDistribution() == 0) {
                    // Distribución aleatoria
                    ResultFrame resultFrame = new ResultFrame(InputFrame.this);

                    resultFrame.setVisible(true);
                } else {
                    // Distribución por puntuaciones
                    RatingsFrame ratingFrame = new RatingsFrame(InputFrame.this);

                    ratingFrame.setVisible(true);
                }

                InputFrame.this.setVisible(false);
                InputFrame.this.setLocationRelativeTo(null);
            }
        });

        rightPanel.add(mixButton, "grow");
        rightPanel.add(backButton, "grow");
    }

    /**
     * Añade al panel de la ventan el campo de texto de sólo lectura donde se
     * mostrarán en tiempo real los nombres de jugadores ingresados por el usuario.
     */
    private void addTextArea() {
        textArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS);

        textArea.setBorder(BorderFactory.createBevelBorder(1));
        textArea.setEditable(false);
        textArea.setVisible(true);

        rightPanel.add(textArea, "push, grow, span");

        updateTextArea();
    }

    /**
     * Agrega la casilla de anclaje de jugadores.
     */
    private void addAnchorCheckBox() {
        JCheckBox anchorCheckBox = new JCheckBox("Anclar jugadores", false);

        anchorCheckBox.setVisible(true);
        anchorCheckBox.addActionListener(e -> Main.setAnchorages(!Main.thereAreAnchorages()));

        rightPanel.add(anchorCheckBox, "center");
    }

    /**
     * Conmuta la visibilidad de los campos de texto de ingreso de jugadores.
     *
     * @param text Ítem seleccionado de la lista desplegable.
     */
    private void updateTextFields(String text) {
        for (int i = 0; i < OPTIONS_COMBOBOX.length; i++) {
            if (text.equals(OPTIONS_COMBOBOX[i])) {
                clearLeftPanel();

                textFields.get(i)
                          .forEach(tf -> leftPanel.add(tf, "growx"));

                break;
            }
        }

        leftPanel.revalidate();
        leftPanel.repaint();
    }

    /**
     * Actualiza el texto mostrado en el campo de sólo lectura.
     * <p>
     * Se muestran los jugadores ingresados en el orden en el que estén
     * posicionados en sus respectivos arreglos.
     * <p>
     * El orden en el que se muestran es:
     * <p>
     * Defensores centrales - Defensores laterales - Mediocampistas - Delanteros - Arqueros.
     */
    private void updateTextArea() {
        int counter = 0;

        textArea.setText(null);

        for (Map.Entry<Position, List<Player>> ps : Main.getPlayersSets().entrySet()) {
            for (Player p : ps.getValue()) {
                if (!p.getName().equals("")) {
                    if (counter != 0 && Main.PLAYERS_PER_TEAM * 2 - counter != 0) {
                        textArea.append(System.lineSeparator());
                    }

                    textArea.append((counter + 1) + " - " + p.getName());

                    counter++;
                }
            }
        }
    }

    /**
     * Quita los campos de texto del panel izquierdo de la ventana.
     */
    private void clearLeftPanel() {
        textFields.stream()
                  .flatMap(Collection::stream)
                  .filter(tf -> isComponentInPanel(tf, leftPanel))
                  .forEach(tf -> leftPanel.remove(tf));
    }

    /**
     * Verifica si un componente es parte de cierto panel.
     *
     * @param c Componente cuya pertenencia se verificará.
     * @param p Panel ante el cual se verificará la pertenencia.
     *
     * @return Si el componente es parte del panel especificado.
     */
    private boolean isComponentInPanel(JComponent c, JPanel p) {
        return c.getParent() == p;
    }

    /**
     * @param name Nombre a validar.
     *
     * @return Si ya existe algún jugador con el nombre recibido por parámetro.
     */
    private boolean alreadyExists(String name) {
        return Main.getPlayersSets().values()
                                    .stream()
                                    .flatMap(Collection::stream)
                                    .anyMatch(p -> p.getName()
                                                    .equals(name));
    }
}
