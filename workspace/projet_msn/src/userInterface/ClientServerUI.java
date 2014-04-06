package userInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Client;

/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class ClientServerUI
{
	private static JFrame mainFrame;
	private static JFrame dialogFrame;
	protected static Client client;

	protected static JTextField personalMessageField;
	protected static HashMap<String, String[]> clientList;
	private static Set<String> keyClientList;
	private static Vector<JListData> simpleClientList;
	protected static JList<JListData> jClientList;

	private ClientServerListener listenerMenu;
	private ListClientListener listenerList;
	private JMenuBar menuBar;
	protected static JPanel connectionPanel;
	protected static JTextField pseudoField;
	protected static JPasswordField passwordField;

	private Properties properties;

	/**
	 * Lancement de l'application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ClientServerUI window = new ClientServerUI();
					getMainFrame().setVisible(true);
					window.toString();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Création de l'application.
	 */
	public ClientServerUI()
	{
		properties = new Properties();
		try
		{
			FileInputStream file = new FileInputStream("configuration.property");
			properties.loadFromXML(file);
			file.close();
		} catch (IOException e2)
		{
			properties.put("ipServer", "localhost");
			properties.put("TCPServer", "30970");
			properties.put("UDPServer", "30971");
			properties.put("alias", "client");
			properties.put("TCPClient", "3000");
			properties.put("UDPClient", "3001");
			try
			{
				FileOutputStream file = new FileOutputStream("configuration.property");
				properties.storeToXML(file, "");
				file.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		String alias = properties.getProperty("alias");
		int udpPort = Integer.parseInt(properties.getProperty("UDPClient"));
		int tcpPort = Integer.parseInt(properties.getProperty("TCPClient"));
		String ipServer = properties.getProperty("ipServer");
		int udpServerPort = Integer.parseInt(properties.getProperty("UDPServer"));
		int tcpServerPort = Integer.parseInt(properties.getProperty("TCPServer"));

		client = new Client(alias, udpPort, tcpPort, ipServer, udpServerPort, tcpServerPort);
		clientList = client.getClientList();
		keyClientList = clientList.keySet();
		listenerMenu = new ClientServerListener();
		listenerList = new ListClientListener();
		dialogFrame = new DialogUI(client);
		dialogFrame.setVisible(false);
		initialize();

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				HashMap<String, String[]> listTemp = new HashMap<String, String[]>(client.getClientList());
				while (true)
				{
					if (!listTemp.equals(client.getClientList()))
					{
						listTemp = new HashMap<String, String[]>(client.getClientList());
						refreshClient();
					}
					try
					{
						Thread.sleep(100);
					} catch (InterruptedException e)
					{
					}
				}
			}
		}).start();
	}

	public static void refreshClient()
	{
		if (!client.getId().equals(""))
		{
			mainFrame.setTitle("msn-" + client.getName());
			dialogFrame.setTitle("Dialog-" + client.getName());
			if (!dialogFrame.isVisible())
			{
				dialogFrame.setVisible(true);
			}
			connectionPanel.setVisible(false);
			clientList = client.getClientList();
			keyClientList = clientList.keySet();
			simpleClientList = new Vector<JListData>();

			for (String key : keyClientList)
			{
				JListData clientListData = new JListData(key, clientList.get(key)[0] + ((clientList.get(key)[1].equals("")) ? "" : ": " + clientList.get(key)[1]));
				simpleClientList.add(clientListData);
			}
			jClientList.setListData(simpleClientList);
			getMainFrame().getContentPane().add(personalMessageField, BorderLayout.SOUTH);
			getMainFrame().getContentPane().add(jClientList, BorderLayout.CENTER);

			boolean isFocused = personalMessageField.isFocusOwner();
			if (isFocused)
			{
				personalMessageField.requestFocus(isFocused);
			}
			jClientList.setVisible(true);
			personalMessageField.setVisible(true);
		} else
		{
			dialogFrame.setVisible(false);
		}
	}

	/**
	 * Initialise le éléments de la fenêtre principale
	 */
	private void initialize()
	{
		setMainFrame(new JFrame("Projet msn"));
		constructMenu();
		constructConnectionPanel();

		personalMessageField = new JTextField("");
		personalMessageField.getDocument().addDocumentListener(new DocumentListener()
		{

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				updatePersonalMessage();
			}

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				updatePersonalMessage();
			}

			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
				updatePersonalMessage();
			}

			public void updatePersonalMessage()
			{
				client.setPersonalMessage(personalMessageField.getText());
			}
		});

		getMainFrame().getContentPane().add(menuBar, BorderLayout.NORTH);
		getMainFrame().getContentPane().add(connectionPanel, BorderLayout.CENTER);
		jClientList = new JList<JListData>();
		jClientList.setListData(new Vector<JListData>());
		jClientList.updateUI();
		jClientList.addMouseListener(listenerList);

		getMainFrame().setLocation(400, 300);
		getMainFrame().setMinimumSize(new Dimension(200, 300));
		getMainFrame().setResizable(false);
		getMainFrame().setVisible(true);
		getMainFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getMainFrame().addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent)
			{
				if (JOptionPane.showConfirmDialog(getMainFrame(), "Etes vous sur de vouloir quitter?", "Fermeture", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					properties.setProperty("alias", client.getName());
					properties.setProperty("ipServer", client.getIpServer());
					properties.setProperty("TCPServer", client.getTcpServerPort() + "");
					properties.setProperty("UDPServer", client.getUdpServerPort() + "");
					properties.setProperty("TCPClient", client.getListeningTCPPort() + "");
					properties.setProperty("UDPClient", client.getListeningUDPPort() + "");

					try
					{
						properties.storeToXML(new FileOutputStream("configuration.property"), "");
					} catch (IOException e)
					{
						e.printStackTrace();
					}
					System.exit(0);
				}
			}
		});
	}

	private void constructMenu()
	{
		menuBar = new JMenuBar();
		JMenu menuPrincipal = new JMenu("Fichier");
		JMenu menuConfiguration = new JMenu("Configuration");
		JMenuItem refresh = new JMenuItem("Rafraichir");
		refresh.addActionListener(listenerMenu);
		JMenuItem connection = new JMenuItem("Se connecter");
		connection.addActionListener(listenerMenu);
		JMenuItem unConnection = new JMenuItem("Se déconnecter");
		unConnection.addActionListener(listenerMenu);
		menuPrincipal.add(refresh);
		menuPrincipal.add(unConnection);

		JMenuItem adressServer = new JMenuItem("Adresse serveur");
		adressServer.addActionListener(listenerMenu);
		JMenuItem serverUDPPort = new JMenuItem("Port UDP serveur");
		serverUDPPort.addActionListener(listenerMenu);
		JMenuItem serverTCPPort = new JMenuItem("Port TCP serveur");
		serverTCPPort.addActionListener(listenerMenu);
		JMenuItem UDPPort = new JMenuItem("Port UDP");
		UDPPort.addActionListener(listenerMenu);
		JMenuItem TCPPort = new JMenuItem("Port TCP");
		TCPPort.addActionListener(listenerMenu);

		menuConfiguration.add(adressServer);
		menuConfiguration.add(serverUDPPort);
		menuConfiguration.add(serverTCPPort);
		menuConfiguration.add(UDPPort);
		menuConfiguration.add(TCPPort);

		menuBar.add(menuPrincipal);
		menuBar.add(menuConfiguration);
	}

	private void constructConnectionPanel()
	{
		// Panel principal
		connectionPanel = new JPanel();
		connectionPanel.setLayout(new BorderLayout(0, 0));
		// Box principal
		Box principalBox = Box.createVerticalBox();
		principalBox.setBorder(new EmptyBorder(50, 0, 0, 0));

		JLabel pseudoLabel = new JLabel("Identifiant : ");
		pseudoLabel.setMinimumSize(new Dimension(110, 20));
		pseudoLabel.setMaximumSize(new Dimension(135, 25));
		pseudoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		// Construction du pseudo
		pseudoField = new JTextField(properties.getProperty("alias"));
		pseudoField.setMinimumSize(new Dimension(110, 20));
		pseudoField.setMaximumSize(new Dimension(135, 25));
		pseudoField.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel passwordLabel = new JLabel("Mot de passe : ");
		passwordLabel.setMinimumSize(new Dimension(110, 20));
		passwordLabel.setMaximumSize(new Dimension(135, 25));
		passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		passwordField = new JPasswordField();
		passwordField.setMinimumSize(new Dimension(110, 20));
		passwordField.setMaximumSize(new Dimension(135, 25));
		passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Construction du bouton de connexion
		final JButton connectionButton = new JButton("Se connecter");
		connectionButton.setMinimumSize(new Dimension(110, 20));
		connectionButton.setMaximumSize(new Dimension(135, 25));
		connectionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		connectionButton.addActionListener(listenerMenu);

		passwordField.addKeyListener(new KeyListener()
		{

			@Override
			public void keyTyped(KeyEvent e)
			{

			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					connectionButton.doClick();
				}
			}

			@Override
			public void keyPressed(KeyEvent e)
			{

			}
		});

		// Ajout des elements
		principalBox.add(pseudoLabel);
		principalBox.add(pseudoField);
		principalBox.add(passwordLabel);
		principalBox.add(passwordField);
		principalBox.add(connectionButton);
		connectionPanel.add(principalBox, BorderLayout.CENTER);
	}

	public static void alert(String message)
	{
		JOptionPane.showMessageDialog(ClientServerUI.getMainFrame(), message);
	}

	public static JFrame getMainFrame()
	{
		return mainFrame;
	}

	public static void setMainFrame(JFrame frame)
	{
		ClientServerUI.mainFrame = frame;
	}

	public static JFrame getDialogFrame()
	{
		return dialogFrame;
	}

	public static void setDialogFrame(JFrame dialogFrame)
	{
		ClientServerUI.dialogFrame = dialogFrame;
	}
}
