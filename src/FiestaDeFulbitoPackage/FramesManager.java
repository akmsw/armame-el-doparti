package FiestaDeFulbitoPackage;

//Importes de java.util
import java.util.ArrayList;

//Importes de java.awt
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.Color;

//Importes de javax.swing
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Icon;

public class FramesManager extends JFrame
{
	//Campos
	private int screenWidth,screenHeight,playersAmount;
	private Toolkit screen;
	private Image frameIcon;
	private Icon creditsIcon;
	private Dimension screenSize;
	private ImageIcon backgroundImage,chichaButtonImage,WCMix,CDMix,LDMix,MMix,FMix,icon;
	private JLabel backgroundLabel,enteredPlayersAmountLabel,T1Label,T2Label,WCLabel,CDLabel,LD1Label,LD2Label,MF1Label,MF2Label,FWLabel,WCT1,WCT2,CDT1,CDT2,LD1T1,LD2T1,LD1T2,LD2T2,M1T1,M2T1,M1T2,M2T2,FWT1,FWT2,ratingLabel,ratingT1,ratingT2;
	private JPanel mainPanel,mixPanel,enteredPlayersNamesPanel,resultPanel,centerResultPanel,southResultPanel;  
	private JFrame mixFrame,resultFrame;
	private JButton WCButton,CDButton,LDButton,MButton,FButton,startMixButton,chichaButton,cancelMixButton,exitButton,resetMixButton,startButton,exitMixButton,remixButton1,remixButton2;
	private ArrayList<Player> WCSet,CDSet,LDSet,MFSet,FWSet,WCSetCOPY,CDSetCOPY,LDSetCOPY,MFSetCOPY,FWSetCOPY;
	private ArrayList<JButton> mixFrameButtons,mainFrameButtons,buttons;
	private ArrayList<String> enteredPlayersNamesList;
	private ArrayList<ArrayList<Player>> setsList;
	private String line,labelText,softwareVersion = "v2.1";
	private ClassicPlayersMixer classicPlayersMixer;
	private ByRatingPlayersMixer byRatingPlayersMixer;
	
	//Métodos públicos
	
	//Constructor. Se incializa la pantalla principal y se la centra
    public FramesManager(String title)
	{
		initializeSets();
		
		startMixButton = new JButton("Comenzar");
		
		initializeScreenElements();
		
		setIcons();
		
		setTitle(title);
		
		setBounds((screenWidth/4)-30,screenHeight/7,709,533);
		
		playersAmount = 0;
		
		initializeFrame("MAIN");
	}
    
    //Getters
    public String getSoftwareVersion() { return softwareVersion; }
    
    //Métodos privados
    
    //Método encargado de escanear las dimensiones de la pantalla
    private void initializeScreenElements()
    {
    	screen = Toolkit.getDefaultToolkit();
		
		screenSize = screen.getScreenSize();
		
		screenHeight = screenSize.height;
		screenWidth = screenSize.width;
    }
    
    //Método encargado de setear los iconos e imágenes para los botones y ventanas
    private void setIcons()
    {
    	frameIcon = screen.getImage("graphics/myIcon.png");
    	
    	icon = new ImageIcon("graphics/myIcon.png");
    	WCMix = new ImageIcon("graphics/wc.png");
		CDMix = new ImageIcon("graphics/cenDef.jpg");
		LDMix = new ImageIcon("graphics/latDef.jpg");
		MMix = new ImageIcon("graphics/mid.jpg");
		FMix = new ImageIcon("graphics/fw.png");
		backgroundImage = new ImageIcon("graphics/backgroundImage.png");
		chichaButtonImage = new ImageIcon("graphics/chicha.jpg");
		creditsIcon = new ImageIcon(icon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH));
    }
    
    //Método encargado de inicializar los sets de jugadores
    private void initializeSets()
    {
    	setsList = new ArrayList<ArrayList<Player>>();
		
		WCSet = new ArrayList<Player>();
		CDSet = new ArrayList<Player>();
		LDSet = new ArrayList<Player>();
		MFSet = new ArrayList<Player>();
		FWSet = new ArrayList<Player>();
		
		enteredPlayersNamesList = new ArrayList<String>();
		
		setsList.add(WCSet);
		setsList.add(CDSet);
		setsList.add(LDSet);
		setsList.add(MFSet);
		setsList.add(FWSet);
    }
    
    //Método encargado de hacer visible y redimensiobale una ventana y de asignarle un icono
    private void setResizableVisibleAndIcon(JFrame frame)
    {
		frame.setResizable(false);
		
		frame.setIconImage(frameIcon);
		
		frame.setVisible(true);
    }
    
    //Método encargado de inicializar las características de distintas ventanas
    private void initializeFrame(String frameType)
    {
    	switch(frameType)
    	{
    		case "MAIN": //Si es la ventana principal...
    		{
    			mainPanel = new JPanel();
    	    	
    			backgroundLabel = new JLabel("",backgroundImage,JLabel.CENTER);
    			backgroundLabel.setBounds(0,0,709,533);
    			
    			this.getContentPane().add(mainPanel);
    	    	
    	    	addFrameButtons(mainPanel,"MAIN");
    	    	
    	    	mainPanel.setLayout(null);
    	    	mainPanel.add(backgroundLabel);
    			
    			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    			
    			setResizableVisibleAndIcon(this);
    			
    			break;
    		}
    		
    		case "MIX": //Si es la ventana donde se ingresan y mezclan jugadores...
    		{
    			startButton.setEnabled(false);

    			mixFrame = new JFrame("Nuevo partido");
    	    	
                mixPanel = new JPanel();
                enteredPlayersNamesPanel = new JPanel();
                
                labelText = "<html><body>Jugadores agregados:";
                
                enteredPlayersAmountLabel = new JLabel(labelText);
                
                enteredPlayersNamesPanel.add(enteredPlayersAmountLabel);
    	    	
    	    	mixFrame.getContentPane().setLayout(new BorderLayout());
    	    	mixFrame.setBounds((screenWidth/4)+40,(screenHeight/7)+80,550,300);
    	    	mixFrame.add(mixPanel,BorderLayout.WEST);
    	    	mixFrame.add(enteredPlayersNamesPanel,BorderLayout.CENTER);
    	    	
    	    	addFrameButtons(mixPanel,"MIX");
    	    	
    	    	setResizableVisibleAndIcon(mixFrame);
    	    	
    	    	break;
    		}

    		default: break;
    	}
    }
    
    //Método encargado de agregar los botones de distintas ventanas
    private void addFrameButtons(JPanel panel, String frameType)
    {
    	switch(frameType)
    	{
	    	case "MAIN": //Si es la ventana principal, se agregan los botones de comienzo, salida y el botón 'chichaButton'...
	    	{
	    		chichaButton = new JButton();
	    		chichaButton.setBounds(600,400,100,100);
	    		chichaButton.setIcon(new ImageIcon(chichaButtonImage.getImage().getScaledInstance(chichaButton.getWidth(),chichaButton.getHeight(),Image.SCALE_SMOOTH)));
	    		
	    		startButton = new JButton("Comenzar");
	    		startButton.setBounds(100,300,100,50);
	    		startButton.setEnabled(true);
	    		
	    		exitButton = new JButton("Salir");
	    		exitButton.setBounds(100,400,100,50);
	    		exitButton.setEnabled(true);
	    		
	    		mainFrameButtons = new ArrayList<JButton>();
	    		mainFrameButtons.add(startButton);
	    		mainFrameButtons.add(exitButton);
	    		mainFrameButtons.add(chichaButton);
	    		
	    		setFrameActionListeners(mainFrameButtons,"MAIN"); //Y se setean los oyentes de acción respectivos
	    		
	    		for(JButton registeredButton : mainFrameButtons) panel.add(registeredButton);
	    		
	    		break;
	    	}
	    	
	    	case "MIX": //Si es la ventana donde se ingresan y mezclan jugadores, se agregan los botones de cada posición, de cancelación, de reseteo y de mezcla...
	    	{
	    		cancelMixButton = new JButton("Cancelar");
	    		resetMixButton = new JButton("Resetear jugadores");
	    		mixFrameButtons = new ArrayList<JButton>();
	    		
	    		panel.setLayout(new GridLayout(4,2));
	    		
	    		WCButton = new JButton("Agregar comodines");
	    		CDButton = new JButton("Agregar defensores centrales");
	    		LDButton = new JButton("Agregar defensores laterales");
	    		MButton = new JButton("Agregar mediocampistas");
	    		FButton = new JButton("Agregar delanteros");

	    		startMixButton = new JButton("Mezclar");
	    		startMixButton.setEnabled(false);
	    		
	    		mixFrameButtons.add(CDButton);
	    		mixFrameButtons.add(LDButton);
	    		mixFrameButtons.add(MButton);
	    		mixFrameButtons.add(FButton);
	    		mixFrameButtons.add(WCButton);
	    		mixFrameButtons.add(startMixButton);
	    		mixFrameButtons.add(cancelMixButton);
	    		mixFrameButtons.add(resetMixButton);
	    		
	    		setFrameActionListeners(mixFrameButtons,"MIX"); //Se setean los oyentes de acción respectivos...
	    		
	    		setBackgroundAndForeground(mixFrameButtons); //Y se setean los colores de los botones especiales
	    		
	    		for(JButton registeredButton : mixFrameButtons) panel.add(registeredButton);
	    		
	    		break;
	    	}
    	}
    }
    
    //Método encargado de setear los colores de los botones especiales
    private void setBackgroundAndForeground(ArrayList<JButton> buttons)
    {
    	for(JButton button : buttons)
    		if(button.getText().equals("Cancelar") || button.getText().equals("Resetear jugadores") || button.getText().equals("Salir")) //A los botones de cancelación y de reseteo, se los pinta de fondo rojo con letras blancas...
    		{
    			button.setBackground(Color.RED);
	    		button.setForeground(Color.WHITE);
    		}
    		else if(button.getText().equals("Mezclar")) //Al botón de mezcla se lo pinta de fondo verde con letras blancas...
    		{
    			button.setBackground(new Color(15,120,0));
	    		button.setForeground(Color.WHITE);
    		}
    		else if(button.getText().equals("Rehacer") || button.getText().equals("Rehacer ")) //Al botón de rehacer se lo pinta de fondo azul con letras blancas...
    		{
    			button.setBackground(Color.BLUE);
	    		button.setForeground(Color.WHITE);
    		}
    		else //En otro caso, no se le asigna color ni al botón ni a las letras
    		{
    			button.setBackground(null);
	    		button.setForeground(null);
    		}
    }
    
    //Método encargado de setear los oyentes de acción de los botones de las distintas ventanas
    private void setFrameActionListeners(ArrayList<JButton> buttons, String frameType)
    {
    	switch(frameType)
    	{
	    	case "MAIN": //Seteo en la ventana principal
	    	{
	    		for(JButton buttonIndex : buttons)
	    			if(buttonIndex.getText().equals("Comenzar")) buttonIndex.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event){ initializeFrame("MIX"); } } );
	    			else if(buttonIndex.getText().equals("Salir")) buttonIndex.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event){ System.exit(0); } } );
	    			else buttonIndex.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event){ chicha(); } } );
	    		
	    		break;
	    	}
	    	
	    	case "MIX": //Seteo en la ventana donde se ingresan y mezclan jugadores
	    	{
	    		for(JButton buttonIndex : buttons)
	    			if(buttonIndex.getText().equals("Agregar comodines")) buttonIndex.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { addPlayers(Position.WILDCARD); } } );
	    			else if(buttonIndex.getText().equals("Agregar defensores centrales")) buttonIndex.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { addPlayers(Position.CENTRALDEFENDER); } } );
	    			else if(buttonIndex.getText().equals("Agregar defensores laterales")) buttonIndex.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { addPlayers(Position.LATERALDEFENDER); } } );
	    			else if(buttonIndex.getText().equals("Agregar mediocampistas")) buttonIndex.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { addPlayers(Position.MIDFIELDER); } } );
	    			else if(buttonIndex.getText().equals("Agregar delanteros")) buttonIndex.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { addPlayers(Position.FORWARD); } } );
	    			else if(buttonIndex.getText().equals("Mezclar")) buttonIndex.addActionListener(new ActionListener()
	    					{
	    						public void actionPerformed(ActionEvent event)
	    						{ 
	    							unableMixFrameButtons();
	    							
	    							String[] whichMix = {"Aleatoria","Por puntajes"};
	    							
	    							int mixType = JOptionPane.showOptionDialog(null,"Seleccione cómo desea realizar la mezcla de jugadores","Elección de tipo de reparto de jugadores",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,creditsIcon,whichMix,whichMix[0]);
	    							
	    							if(mixType==0) startClassicMix();
	    							else startByRatingMix();
	    						}
	    					});
	    			else if(buttonIndex.getText().equals("Cancelar")) buttonIndex.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { enteredPlayersNamesList.clear(); closeFrame("MIX FRAME");} } );
	    			else buttonIndex.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { clearSets(); enteredPlayersNamesList.clear(); } } );
	    		
	    		break;
	    	}
	    	
	    	case "START": //Seteo en la ventana donde se muestran los equipos formados
	    	{
	    		for(JButton buttonIndex: buttons)
	    			if(buttonIndex.getText().equals("Salir")) buttonIndex.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { startMixButton.setEnabled(true); cancelMixButton.setEnabled(true); resetMixButton.setEnabled(true); WCButton.setEnabled(true); CDButton.setEnabled(true); LDButton.setEnabled(true); MButton.setEnabled(true); FButton.setEnabled(true); closeFrame("RESULT FRAME"); } } );
	    			else if(buttonIndex.getText().equals("Rehacer")) { buttonIndex.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { closeFrame("RESULT FRAME"); startClassicMix(); } } ); }
	    			else buttonIndex.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { closeFrame("RESULT FRAME"); startByRatingMix(); } } );
	    		
	    		break;
	    	}
    	}
    }
    
    //Método encargado de inhabilitar los botones de la ventana donde se ingresan y mezclan jugadores
    private void unableMixFrameButtons() { for(JButton button : mixFrameButtons) button.setEnabled(false); }
    
    //Método encargado de agregar los jugadores a los sets respectivos
    private void addPlayers(Position playerType)
    {
    	switch(playerType)
    	{
    		case WILDCARD: //Si se selecciona el set de comodines...
    		{
    			if(isEmptySet(Position.WILDCARD)) //Si el set está vacío, se ingresan los nombres de los jugadores cuyo nombre no sea nulo, vacío, con caracteres del tipo ' ', no esté repetido o cuya longitud no sea mayor a 12 caracteres
    			{
    				for(int i=0; i<2; i++)
        			{
    					String name = (String)JOptionPane.showInputDialog(null,"Ingrese el nombre del comodín #" + (i+1),"Agregando comodines",0,WCMix,null,null);
        				
        				while(name.equals(null) || name.equals("") || name.length()>12 || alreadyExists(setsList,name) || isEmptyString(name)) name = (String)JOptionPane.showInputDialog(null,"El nombre no puede ser vacío, nulo, estar repetido o tener más de 12 caracteres. Reingrese el nombre del comodín #" + (i+1),"Agregando comodines",0,WCMix,null,null);
        				
        				String playerName = name.trim().replace(" ","_").toUpperCase();
        				
        				WCSet.add(new WildCard(playerName));
        				
        				modifyLabel2(enteredPlayersNamesList,playerName); //Se agregan los nombres ingresados a la etiqueta de la ventana donde se ingresan y mezclan jugadores
        			}
    			}
    			else //Si el set no está vacío...
    			{
    				int choice = JOptionPane.showConfirmDialog(mixFrame,"El set de comodines está lleno. ¿Vaciarlo y reingresar comodines?","Set lleno",2);
    				
    				if(choice==0) //Si se desea reingresar los jugadores respectivos...
    				{
    					modifyLabel1(enteredPlayersNamesList,WCSet); //Se borran los jugadores de esta posición tanto de los sets como de la etiqueta de la ventana donde se ingresan y mezclan jugadores
    					
    					for(int i=0; i<2; i++) //Se reingresan los mismos tanto a los sets como a la etiqueta de la ventana donde se ingresan y mezclan jugadores
            			{
            				String name = (String)JOptionPane.showInputDialog(null,"Ingrese el nombre del comodín #" + (i+1),"Agregando comodines",0,WCMix,null,null);
            				
            				while(name.equals(null) || name.equals("") || name.length()>12 || alreadyExists(setsList,name) || isEmptyString(name)) name = (String)JOptionPane.showInputDialog(null,"El nombre no puede ser vacío, nulo, estar repetido o tener más de 12 caracteres. Reingrese el nombre del comodín #" + (i+1),"Agregando comodines",0,WCMix,null,null);
            				
            				String playerName = name.trim().replace(" ","_").toUpperCase();
            				
            				WCSet.add(new WildCard(playerName));
            				
            				playersAmount++;
            				
            				modifyLabel2(enteredPlayersNamesList,playerName);
            			}
    				}
    				else break; //Si no se desea reingresar los jugadores respectivos, el programa no hace nada
    			}
    			
    			if(WCSet.size()==2 && CDSet.size()==2 && LDSet.size()==4 && MFSet.size()==4) { startMixButton.setEnabled(true); } //Si todos los sets están llenos, se habilita el botón para setear los puntajes de los jugadores y armar los equipos
    			
    			break;
    		}
    		
    		//Mismo principio de funcionamiento para todos los jugadores de las demás posiciones...
    		case CENTRALDEFENDER:
    		{
    			if(isEmptySet(Position.CENTRALDEFENDER))
    			{
    				for(int i=0; i<2; i++)
        			{
        				String name = (String)JOptionPane.showInputDialog(null,"Ingrese el nombre del defensor central #" + (i+1),"Agregando defensores centrales",0,CDMix,null,null);
        				
        				while(name.equals(null) || name.equals("") || name.length()>12 || alreadyExists(setsList,name) || isEmptyString(name)) name = (String)JOptionPane.showInputDialog(null,"El nombre no puede ser vacío, nulo, estar repetido o tener más de 12 caracteres. Reingrese el nombre del defensor central #" + (i+1),"Agregando defensores centrales",0,CDMix,null,null);
        				
        				String playerName = name.trim().replace(" ","_").toUpperCase();
        				
        				CDSet.add(new CentralDefender(playerName));
        				
        				modifyLabel2(enteredPlayersNamesList,playerName);
        			}
    			}
    			else
    			{
    				int choice = JOptionPane.showConfirmDialog(mixFrame,"El set de defensores centrales está lleno. ¿Vaciarlo y reingresar defensores centrales?","Set lleno",2);
    				
    				if(choice==0)
    				{
    					modifyLabel1(enteredPlayersNamesList,CDSet);
    					
    					for(int i=0; i<2; i++)
            			{
            				String name = (String)JOptionPane.showInputDialog(null,"Ingrese el nombre del defensor central #" + (i+1),"Agregando defensores centrales",0,CDMix,null,null);
            				
            				while(name.equals(null) || name.equals("") || name.length()>12 || alreadyExists(setsList,name) || isEmptyString(name)) name = (String)JOptionPane.showInputDialog(null,"El nombre no puede ser vacío, nulo, estar repetido o tener más de 12 caracteres. Reingrese el nombre del defensor central #" + (i+1),"Agregando defensores centrales",0,CDMix,null,null);
            				
            				String playerName = name.trim().replace(" ","_").toUpperCase();
            				
            				CDSet.add(new CentralDefender(playerName));
            				
            				modifyLabel2(enteredPlayersNamesList,playerName);
            			}
    				}
    				else break;
    			}
    			
    			if(WCSet.size()==2 && CDSet.size()==2 && LDSet.size()==4 && MFSet.size()==4) { startMixButton.setEnabled(true); }
    			
    			break;
    		}
    		
    		case LATERALDEFENDER:
    		{
    			if(isEmptySet(Position.LATERALDEFENDER))
    			{
    				for(int i=0; i<4; i++)
        			{
        				String name = (String)JOptionPane.showInputDialog(null,"Ingrese el nombre del defensor lateral #" + (i+1),"Agregando defensores laterales",0,LDMix,null,null);
        				
        				while(name.equals(null) || name.equals("") || name.length()>12 || alreadyExists(setsList,name) || isEmptyString(name)) name = (String)JOptionPane.showInputDialog(null,"El nombre no puede ser vacío, nulo, estar repetido o tener más de 12 caracteres. Reingrese el nombre del defensor lateral #" + (i+1),"Agregando defensores laterales",0,LDMix,null,null);
        				
        				String playerName = name.trim().replace(" ","_").toUpperCase();
        				
        				LDSet.add(new LateralDefender(playerName));
        				
        				modifyLabel2(enteredPlayersNamesList,playerName);
        			}
    			}
    			else
    			{
    				int choice = JOptionPane.showConfirmDialog(mixFrame,"El set de defensores laterales está lleno. ¿Vaciarlo y reingresar defensores laterales?","Set lleno",2);
    				
    				if(choice==0)
    				{
    					modifyLabel1(enteredPlayersNamesList,LDSet);
    					
    					for(int i=0; i<4; i++)
            			{
            				String name = (String)JOptionPane.showInputDialog(null,"Ingrese el nombre del defensor lateral #" + (i+1),"Agregando defensores laterales",0,LDMix,null,null);
            				
            				while(name.equals(null) || name.equals("") || name.length()>12 || alreadyExists(setsList,name) || isEmptyString(name)) name = (String)JOptionPane.showInputDialog(null,"El nombre no puede ser vacío, nulo, estar repetido o tener más de 12 caracteres. Reingrese el nombre del defensor lateral #" + (i+1),"Agregando defensores laterales",0,LDMix,null,null);
            				
            				String playerName = name.trim().replace(" ","_").toUpperCase();
            				
            				LDSet.add(new LateralDefender(playerName));
            				
            				modifyLabel2(enteredPlayersNamesList,playerName);
            			}
    				}
    				else break;
    			}
    			
    			if(WCSet.size()==2 && CDSet.size()==2 && LDSet.size()==4 && MFSet.size()==4) { startMixButton.setEnabled(true); }
    			
    			break;
    		}
    		
    		case MIDFIELDER:
    		{
    			if(isEmptySet(Position.MIDFIELDER))
    			{
    				for(int i=0; i<4; i++)
        			{
        				String name = (String)JOptionPane.showInputDialog(null,"Ingrese el nombre del mediocampista #" + (i+1),"Agregando mediocampistas",0,MMix,null,null);
        				
        				while(name.equals(null) || name.equals("") || name.length()>12 || alreadyExists(setsList,name) || isEmptyString(name)) name = (String)JOptionPane.showInputDialog(null,"El nombre no puede ser vacío, nulo, estar repetido o tener más de 12 caracteres. Reingrese el nombre del mediocampista #" + (i+1),"Agregando mediocampistas",0,MMix,null,null);
        				
        				String playerName = name.trim().replace(" ","_").toUpperCase();
        				
        				MFSet.add(new Midfielder(playerName));
        				
        				modifyLabel2(enteredPlayersNamesList,playerName);
        			}
    			}
    			else
    			{
    				int choice = JOptionPane.showConfirmDialog(mixFrame,"El set de mediocampistas está lleno. ¿Vaciarlo y reingresar mediocampistas?","Set lleno",2);
    				
    				if(choice==0)
    				{
    					modifyLabel1(enteredPlayersNamesList,MFSet);
    					
        				for(int i=0; i<4; i++)
            			{
            				String name = (String)JOptionPane.showInputDialog(null,"Ingrese el nombre del mediocampista #" + (i+1),"Agregando mediocampistas",0,MMix,null,null);
            				
            				while(name.equals(null) || name.equals("") || name.length()>12 || alreadyExists(setsList,name) || isEmptyString(name)) name = (String)JOptionPane.showInputDialog(null,"El nombre no puede ser vacío, nulo, estar repetido o tener más de 12 caracteres. Reingrese el nombre del mediocampista #" + (i+1),"Agregando mediocampistas",0,MMix,null,null);
            				
            				String playerName = name.trim().replace(" ","_").toUpperCase();
            				
            				MFSet.add(new Midfielder(playerName));
            				
            				modifyLabel2(enteredPlayersNamesList,playerName);
            			}
    				}
    				else break;
    			}
    			
    			if(WCSet.size()==2 && CDSet.size()==2 && LDSet.size()==4 && MFSet.size()==4) { startMixButton.setEnabled(true); }
    			
    			break;
    		}
    		
    		case FORWARD:
    		{
    			if(isEmptySet(Position.FORWARD))
    			{
    				for(int i=0; i<2; i++)
        			{
        				String name = (String)JOptionPane.showInputDialog(null,"Ingrese el nombre del delantero #" + (i+1),"Agregando delanteros",0,FMix,null,null);
        				
        				while(name.equals(null) || name.equals("") || name.length()>12 || alreadyExists(setsList,name) || isEmptyString(name)) name = (String)JOptionPane.showInputDialog(null,"El nombre no puede ser vacío, nulo, estar repetido o tener más de 12 caracteres. Reingrese el nombre del delantero #" + (i+1),"Agregando delanteros",0,FMix,null,null);
        				
        				String playerName = name.trim().replace(" ","_").toUpperCase();
        				
        				FWSet.add(new Forward(playerName));
        				
        				modifyLabel2(enteredPlayersNamesList,playerName);
        			}
    			}
    			else
    			{
    				int choice = JOptionPane.showConfirmDialog(mixFrame,"El set de delanteros está lleno. ¿Vaciarlo y reingresar delanteros?","Set lleno",2);
    				
    				if(choice==0)
    				{
    					modifyLabel1(enteredPlayersNamesList,FWSet);
    					
        				for(int i=0; i<2; i++)
            			{
            				String name = (String)JOptionPane.showInputDialog(null,"Ingrese el nombre del delantero #" + (i+1),"Agregando delanteros",0,FMix,null,null);
            				
            				while(name.equals(null) || name.equals("") || name.length()>12 || alreadyExists(setsList,name) || isEmptyString(name)) name = (String)JOptionPane.showInputDialog(null,"El nombre no puede ser vacío, nulo, estar repetido o tener más de 12 caracteres. Reingrese el nombre del delantero #" + (i+1),"Agregando delanteros",0,FMix,null,null);
            				
            				String playerName = name.trim().replace(" ","_").toUpperCase();
            				
            				FWSet.add(new Forward(playerName));
            				
            				modifyLabel2(enteredPlayersNamesList,playerName);
            			}
    				}
    			}
    			
    			if(WCSet.size()==2 && CDSet.size()==2 && LDSet.size()==4 && MFSet.size()==4) { startMixButton.setEnabled(true); }
    			
    			break;
    		}
    	}
    }
    
    //Método encargado de setear los puntajes de los jugadores. El puntaje sólo será válido si está en el rango [1,4]
    private void ratePlayers()
    {
    	String[] options = {"1","2","3","4"};
    	
    	for(ArrayList<Player> set : setsList)
    		for(Player player : set)
    		{
    			int playerRating = JOptionPane.showOptionDialog(null,"Seleccione el puntaje para el jugador " + player.getName(),"Asignando puntajes",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,creditsIcon,options,options[0]);
    			
    			player.setRating(playerRating+1);
    		}
    }
    
    //Métodos para modificación de la etiqueta de la ventana donde se ingresan y mezclan jugadores
    
    //Método encargado de limpiar el set de jugadores el cual se quiere reingresar
    private void modifyLabel1(ArrayList<String> enteredPlayersNamesList, ArrayList<Player> set)
    {
    	for(Player player : set)
			if(enteredPlayersNamesList.contains(player.getName())) enteredPlayersNamesList.remove(player.getName());
    	
    	set.clear();
    	
    	modifyLabel3(enteredPlayersNamesList);
    }
    
    //Método encargado de agregar el nombre del jugador ingresado a la etiqueta de la ventana donde se ingresan y mezclan jugadores
    private void modifyLabel2(ArrayList<String> enteredPlayersNamesList, String playerName)
    {
    	enteredPlayersNamesList.add(playerName);
		
		modifyLabel3(enteredPlayersNamesList);
    }
    
    //Método encargado de llenar con los nombres de los jugadores ingresados la etiqueta de la ventana donde se ingresan y mezclan jugadores
    private void modifyLabel3(ArrayList<String> enteredPlayersNamesList)
    {
    	labelText = "<html><body>Jugadores agregados:";
		
		playersAmount = 0;
		
		for(String pName : enteredPlayersNamesList)
		{
			playersAmount++;
			
			labelText += "<br>" + playersAmount + ": " + pName;
		}
		
		enteredPlayersAmountLabel.setText(labelText);
    }
    
    //Método encargado de chequear si un set de jugadores está vacío. Sólo se podrá chequear con sets exitentes; de lo contrario, se lanzará una excepción
    private boolean isEmptySet(Position playerType)
    {
    	switch(playerType)
    	{
    		case WILDCARD:
    		{
    			if(WCSet.isEmpty()) return true;
    			else return false;
    		}
    		
    		case CENTRALDEFENDER:
    		{
    			if(CDSet.isEmpty()) return true;
    			else return false;
    		}
    		
    		case LATERALDEFENDER:
    		{
    			if(LDSet.isEmpty()) return true;
    			else return false;
    		}
    		
    		case MIDFIELDER:
    		{
    			if(MFSet.isEmpty()) return true;
    			else return false;
    		}
    		
    		case FORWARD:
    		{
    			if(FWSet.isEmpty()) return true;
    			else return false;
    		}
    		
    		default: throw new IllegalArgumentException("No se reconoce la posición especificada. Reingrese el tipo de jugador.");
    	}
    }
    
    //Método encargado de chequear si una cadena de caracteres está vacía o no
    private boolean isEmptyString(String name)
    {
    	boolean isEmpty = false;

    	char[] charArray = name.toCharArray();
    	
    	for(int i=0; i<charArray.length; i++)
    		if(charArray[i] == ' ') isEmpty = true;
    		else return false;
    	
    	return isEmpty;
    }
    
    //Método encargado de chequear si el nombre de un jugador está repetido en algún set
    private boolean alreadyExists(ArrayList<ArrayList<Player>> list, String name)
    {
    	for(ArrayList<Player> listIndex : list)
	    	for(Player registeredPlayer : listIndex) if(name.trim().replace(" ","_").toUpperCase().equals(registeredPlayer.getName())) return true;
    	
    	return false;
    }
    
    //Método encargado de mezclar y distribuir los jugadores en dos equipos
    private void startClassicMix()
    {
    	classicPlayersMixer = new ClassicPlayersMixer(); //Se inicializa el mezclador de jugadores...
    	
    	//Resumen de acciones a realizar...
    	actionsResume1();
		
		remixButton1 = new JButton("Rehacer");
		
		mixFrameButtons.add(remixButton1);
		
		//Se mezclan y se distribuyen los jugadores de cada set...
		classicPlayersMixer.mixAndDistribute(WCSetCOPY,2,Position.WILDCARD);
		classicPlayersMixer.mixAndDistribute(CDSetCOPY,2,Position.CENTRALDEFENDER);
		classicPlayersMixer.mixAndDistribute(LDSetCOPY,4,Position.LATERALDEFENDER);
		classicPlayersMixer.mixAndDistribute(MFSetCOPY,4,Position.MIDFIELDER);
		classicPlayersMixer.mixAndDistribute(FWSetCOPY,2,Position.FORWARD);
		
		//Se colocan en orden los nombres de los jugadores del equipo 1...
		WCT1.setText(classicPlayersMixer.getTeam(1).get("WILDCARD #1").getName());
		CDT1.setText(classicPlayersMixer.getTeam(1).get("CENTRALDEFENDER #1").getName());
		LD1T1.setText(classicPlayersMixer.getTeam(1).get("LATERALDEFENDER #1").getName());
		LD2T1.setText(classicPlayersMixer.getTeam(1).get("LATERALDEFENDER #2").getName());
		M1T1.setText(classicPlayersMixer.getTeam(1).get("MIDFIELDER #1").getName());
		M2T1.setText(classicPlayersMixer.getTeam(1).get("MIDFIELDER #2").getName());
		FWT1.setText(classicPlayersMixer.getTeam(1).get("FORWARD #1").getName());
		
		//Se colocan en orden los nombres de los jugadores del equipo 2...
		WCT2.setText(classicPlayersMixer.getTeam(2).get("WILDCARD #1").getName());
		CDT2.setText(classicPlayersMixer.getTeam(2).get("CENTRALDEFENDER #1").getName());
		LD1T2.setText(classicPlayersMixer.getTeam(2).get("LATERALDEFENDER #1").getName());
		LD2T2.setText(classicPlayersMixer.getTeam(2).get("LATERALDEFENDER #2").getName());
		M1T2.setText(classicPlayersMixer.getTeam(2).get("MIDFIELDER #1").getName());
		M2T2.setText(classicPlayersMixer.getTeam(2).get("MIDFIELDER #2").getName());
		FWT2.setText(classicPlayersMixer.getTeam(2).get("FORWARD #1").getName());
		
		//Resumen de acciones a realizar...
		actionsResume2();
		
		buttons.add(remixButton1);
		
		//Retoques finales de la creación de la ventana de resultados...
		finishFrameCreation();
    }
    
    //Método encargado de mezclar y distribuir los jugadores en dos equipos en base a los puntajes
    private void startByRatingMix()
    {
    	ratePlayers(); //Primero se setean las puntuaciones de cada jugador...
    	
    	byRatingPlayersMixer = new ByRatingPlayersMixer();
    	
    	//Resumen de acciones a realizar...
    	actionsResume1();
		
		remixButton2 = new JButton("Rehacer ");
		
		mixFrameButtons.add(remixButton2);
		
		ratingLabel = new JLabel("PUNTOS");
		ratingT1 = new JLabel();
		ratingT2 = new JLabel();
		
		ratingLabel.setBounds(10,10,200,370);
		
		//Se mezclan en base a los puntajes...
		byRatingPlayersMixer.mixAndDistribute(WCSetCOPY,CDSetCOPY,LDSetCOPY,MFSetCOPY,FWSetCOPY);
		
		//Se colocan en orden los nombres de los jugadores del equipo 1 junto con la puntuación general del equipo...
		WCT1.setText(byRatingPlayersMixer.getTeam(1).get("WILDCARD #1").getName());
		CDT1.setText(byRatingPlayersMixer.getTeam(1).get("CENTRALDEFENDER #1").getName());
		LD1T1.setText(byRatingPlayersMixer.getTeam(1).get("LATERALDEFENDER #1").getName());
		LD2T1.setText(byRatingPlayersMixer.getTeam(1).get("LATERALDEFENDER #2").getName());
		M1T1.setText(byRatingPlayersMixer.getTeam(1).get("MIDFIELDER #1").getName());
		M2T1.setText(byRatingPlayersMixer.getTeam(1).get("MIDFIELDER #2").getName());
		FWT1.setText(byRatingPlayersMixer.getTeam(1).get("FORWARD #1").getName());
		ratingT1.setText(String.valueOf(byRatingPlayersMixer.getTeamRating(1)));
		
		//Se colocan en orden los nombres de los jugadores del equipo 2...
		WCT2.setText(byRatingPlayersMixer.getTeam(2).get("WILDCARD #1").getName());
		CDT2.setText(byRatingPlayersMixer.getTeam(2).get("CENTRALDEFENDER #1").getName());
		LD1T2.setText(byRatingPlayersMixer.getTeam(2).get("LATERALDEFENDER #1").getName());
		LD2T2.setText(byRatingPlayersMixer.getTeam(2).get("LATERALDEFENDER #2").getName());
		M1T2.setText(byRatingPlayersMixer.getTeam(2).get("MIDFIELDER #1").getName());
		M2T2.setText(byRatingPlayersMixer.getTeam(2).get("MIDFIELDER #2").getName());
		FWT2.setText(byRatingPlayersMixer.getTeam(2).get("FORWARD #1").getName());
		ratingT2.setText(String.valueOf(byRatingPlayersMixer.getTeamRating(2)));
		
		//Resumen de acciones a realizar...
		actionsResume2();
		
		ratingT1.setForeground(Color.RED);
		ratingT2.setForeground(Color.BLUE);
		
		ratingT1.setBounds(72,10,200,370);
		ratingT2.setBounds(180,10,300,370);
		
		centerResultPanel.add(ratingLabel);
		centerResultPanel.add(ratingT1);
		centerResultPanel.add(ratingT2);
		
		buttons.add(remixButton2);
		
		//Retoques finales de la creación de la ventana de resultados...
		finishFrameCreation();
    }
    
    //Método encargado de cerrar distintas ventanas
    private void closeFrame(String frameType)
    {
    	switch(frameType)
    	{
    		case "MIX FRAME": //Si se quiere cerrar la ventana donde se ingresan y mezclan jugadores...
    		{
    			clearSets(); //Se vacían los sets...
    			
    			mixFrame.setVisible(false); //Se cierra la ventana respectiva...
    			startButton.setEnabled(true); //Se habilita el botón 'Comenzar'
    			
    			break;
    		}
    		
    		case "RESULT FRAME": //Si se quiere cerrar la ventana donde se muestran los equipos armados...
    		{
    			resultFrame.setVisible(false); //Se cierra la ventana respectiva
    			
    			break;
    		}
    	}	
    }
    
    //Método encargado de vaciar los sets de jugadores, de deshabilitar el botón de mezcla y de vaciar la etiqueta utilizada en la ventana donde se ingresan y mezclan jugadores
    private void clearSets()
    {
    	for(ArrayList<Player> registeredArray : setsList) registeredArray.clear();
    	
    	startMixButton.setEnabled(false);
    	
    	enteredPlayersAmountLabel.setText("<html><body>Jugadores agregados:");
    }
    
    //Método encargado de clonar un set de jugadores
    private ArrayList<Player> cloneSet(ArrayList<Player> copySet)
	{
		ArrayList<Player> copiedSet = new ArrayList<Player>();
		
		for(Player playerRegistered : copySet) copiedSet.add(playerRegistered);
	
		return copiedSet;
	}
    
    //Método encargado de clonar todos los sets de jugadores
    private void cloneSets()
    {
    	WCSetCOPY = cloneSet(WCSet);
		CDSetCOPY = cloneSet(CDSet);
		LDSetCOPY = cloneSet(LDSet);
		MFSetCOPY = cloneSet(MFSet);
		FWSetCOPY = cloneSet(FWSet);
    }
    
    //Métodos para la creación de la ventana de resultados
    
    //Método encargado de inicializar la ventana de resultados y agregar los botones
    private void createFrameAndButtons()
    {
    	resultFrame = new JFrame("Equipos armados");
		
    	exitMixButton = new JButton("Salir");
		
		mixFrameButtons.add(exitMixButton);
    }
    
    //Método encargado de inicializar los paneles y etiquetas a utilizar en la ventana de resultados
    private void createPanelsAndLabels()
    {
    	resultPanel = new JPanel();
		centerResultPanel = new JPanel();
		southResultPanel = new JPanel();
		
		T1Label = new JLabel("EQUIPO OSCURO");
		T2Label = new JLabel("EQUIPO CLARO");
		WCLabel = new JLabel("COM");
		CDLabel = new JLabel("DFC");
		LD1Label = new JLabel("LAT#1");
		LD2Label = new JLabel("LAT#2");
		MF1Label = new JLabel("MED#1");
		MF2Label = new JLabel("MED#2");
		FWLabel = new JLabel("DEL");
		WCT1 = new JLabel();
		WCT2 = new JLabel();
		CDT1 = new JLabel();
		CDT2 = new JLabel();
		LD1T1 = new JLabel();
		LD2T1 = new JLabel();
		LD1T2 = new JLabel();
		LD2T2 = new JLabel();
		M1T1 = new JLabel();
		M2T1 = new JLabel();
		M1T2 = new JLabel();
		M2T2 = new JLabel();
		FWT1 = new JLabel();
		FWT2 = new JLabel();
    }
    
    //Método encargado de setear los colores y dimensiones de las etiquetas de la ventana de resultados
    private void setLabelsDimensionsAndColors()
    {
    	WCLabel.setBounds(10,10,200,90);
		CDLabel.setBounds(10,10,200,130);
		LD1Label.setBounds(10,10,200,170);
		LD2Label.setBounds(10,10,200,210);
		MF1Label.setBounds(10,10,200,250);
		MF2Label.setBounds(10,10,200,290);
		FWLabel.setBounds(10,10,200,330);
		T1Label.setBounds(72,10,300,50);
		T2Label.setBounds(180,10,400,50);
		
		T1Label.setForeground(Color.RED);
		T2Label.setForeground(Color.BLUE);
    }
    
    //Método encargado de setear el color de las etiquetas de la ventana de resultados
    private void setLabelsColors()
    {
    	//Se colorean de rojo los nombes de los jugadores del equipo 1...
		WCT1.setForeground(Color.RED);
		CDT1.setForeground(Color.RED);
		LD1T1.setForeground(Color.RED);
		LD2T1.setForeground(Color.RED);
		M1T1.setForeground(Color.RED);
		M2T1.setForeground(Color.RED);
		FWT1.setForeground(Color.RED);
		
		//Se colorean de azul los nombes de los jugadores del equipo 2...
		WCT2.setForeground(Color.BLUE);
		CDT2.setForeground(Color.BLUE);
		LD1T2.setForeground(Color.BLUE);
		LD2T2.setForeground(Color.BLUE);
		M1T2.setForeground(Color.BLUE);
		M2T2.setForeground(Color.BLUE);
		FWT2.setForeground(Color.BLUE);
    }
    
    //Método encargado de ubicar las etiquetas donde corresponde en la ventana de resultados
    private void setLabelsBounds()
    {
    	//Se ubican en su lugar los nombres de los jugadores del equipo 1...
		WCT1.setBounds(72,10,200,90);
		CDT1.setBounds(72,10,200,130);
		LD1T1.setBounds(72,10,200,170);
		LD2T1.setBounds(72,10,200,210);
		M1T1.setBounds(72,10,200,250);
		M2T1.setBounds(72,10,200,290);
		FWT1.setBounds(72,10,200,330);
		
		//Se ubican en su lugar los nombres de los jugadores del equipo 2...
		WCT2.setBounds(180,10,200,90);
		CDT2.setBounds(180,10,300,130);
		LD1T2.setBounds(180,10,300,170);
		LD2T2.setBounds(180,10,300,210);
		M1T2.setBounds(180,10,300,250);
		M2T2.setBounds(180,10,300,290);
		FWT2.setBounds(180,10,300,330);
    }
    
    //Método encargado de agregar las etiquetas al panel central de la ventana de resultados
    private void addLabels()
    {
    	//Se agregan al panel central las etiquetas creadas luego de haber seteado el layout correspondiente...
    	centerResultPanel.setLayout(null);
    	
    	centerResultPanel.add(T1Label);
    	centerResultPanel.add(T2Label);
    	centerResultPanel.add(WCLabel);
    	centerResultPanel.add(CDLabel);
    	centerResultPanel.add(LD1Label);
    	centerResultPanel.add(LD2Label);
    	centerResultPanel.add(MF1Label);
    	centerResultPanel.add(MF2Label);
    	centerResultPanel.add(FWLabel);
    	centerResultPanel.add(WCT1);
    	centerResultPanel.add(WCT2);
    	centerResultPanel.add(CDT1);
    	centerResultPanel.add(CDT2);
    	centerResultPanel.add(LD1T1);
    	centerResultPanel.add(LD2T1);
    	centerResultPanel.add(LD1T2);
    	centerResultPanel.add(LD2T2);
    	centerResultPanel.add(M1T1);
    	centerResultPanel.add(M2T1);
    	centerResultPanel.add(M1T2);
    	centerResultPanel.add(M2T2);
    	centerResultPanel.add(FWT1);
    	centerResultPanel.add(FWT2);
    }
    
    //Método encargado de setear los layouts de los paneles para la ventana de resultados
    private void setLayouts()
    {
    	//Se setean los layouts del panel de resultados...
    	southResultPanel.setLayout(new FlowLayout());
    	resultPanel.setLayout(new BorderLayout());
    	
    	resultPanel.add(southResultPanel,BorderLayout.SOUTH);
    	resultPanel.add(centerResultPanel,BorderLayout.CENTER);
    	
    	buttons = new ArrayList<JButton>();
    	buttons.add(exitMixButton);
    }
    
    //Método encargado de dar los retoques finales a la ventana de resultados
    private void finishFrameCreation()
    {
    	setBackgroundAndForeground(buttons);
		
		setFrameActionListeners(buttons,"START"); //Seteo de los oyentes de acción de los botones de la ventana respectiva...
		
		for(JButton registeredButton : buttons) southResultPanel.add(registeredButton);
		
		resultFrame.getContentPane().add(resultPanel);
		
		resultFrame.setBounds((screenWidth/4)+190,(screenHeight/4)+10,300,280);

		setResizableVisibleAndIcon(resultFrame); //Se establece como visible la ventana ya finalizada
    }
    
    //Resumen de acciones a realizar a la hora de crear la ventana de resultados
    private void actionsResume1()
    {
    	//Se clonan los sets...
    	cloneSets();
		
		//Se crea la ventana de resultados...
		createFrameAndButtons();
		
		//Se crean los paneles y etiquetas a utilizar...
		createPanelsAndLabels();
		
		//Se setean las dimensiones y colores de cada etiqueta creada...
		setLabelsDimensionsAndColors();
    }
    
    //Resumen de acciones a realizar a la hora de crear la ventana de resultados
    private void actionsResume2()
    {
    	//Se colorean las etiquetas con nombres...
    	setLabelsColors();
    	
    	//Se ubican en su lugar los nombres de los jugadores...
    	setLabelsBounds();
    	
    	//Se agregan al panel central las etiquetas creadas...
    	addLabels();
    	
    	//Se setean los layouts del panel de resultados...
    	setLayouts();
    }
    
    //Método CHICHA
    
    //Método encargado de mostrar los créditos con el botón 'chichaButton'
    private void chicha()
    {
    	line = "<html>FIESTA DE FULBITO " + getSoftwareVersion() + "<p><p>    Créditos<p>TODO: ©AkamaiSoftware";
    	
    	JOptionPane.showMessageDialog(null,line,"Créditos",JOptionPane.PLAIN_MESSAGE,creditsIcon);
    }
}