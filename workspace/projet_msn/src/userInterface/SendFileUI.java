package userInterface;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

import model.Client;
import model.ClientDialog;

/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class SendFileUI
{
	private static JFrame mainFrame;
	public static Client client;
	public static ClientDialog dialog;

	public static JFileChooser fileChooser;

	/**
	 * Création de la fenêtre principale.
	 */
	public SendFileUI(Client clientRef, ClientDialog dialogRef)
	{
		client = clientRef;
		dialog = dialogRef;
		initialize();
	}

	public static void refreshClient()
	{

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		setMainFrame(new JFrame("Envoie de fichier"));

		JButton searchButton = new JButton("Parcourir");
		searchButton.setPreferredSize(new Dimension(90, 23));
		searchButton.setMaximumSize(new Dimension(90, 23));
		searchButton.setMinimumSize(new Dimension(90, 23));

		final JTextField searchResultField = new JTextField();
		searchResultField.setPreferredSize(new Dimension(190, 23));
		searchResultField.setMaximumSize(new Dimension(190, 23));
		searchResultField.setMinimumSize(new Dimension(190, 23));

		JButton sendButton = new JButton("Envoyer");
		sendButton.setPreferredSize(new Dimension(90, 23));
		sendButton.setMaximumSize(new Dimension(90, 23));
		sendButton.setMinimumSize(new Dimension(90, 23));

		searchButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser dialogue = new JFileChooser(new File("."));
				// PrintWriter sortie;
				File fichier;

				if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					fichier = dialogue.getSelectedFile();
					searchResultField.setText(fichier.getAbsolutePath());
				}
			}
		});

		sendButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				client.sendFileToDialog(searchResultField.getText(), dialog.getIdDialog());
				getMainFrame().dispose();
			}
		});

		getMainFrame().setLayout(new GridLayout(2, 2));
		getMainFrame().add(searchResultField);
		getMainFrame().add(searchButton);
		getMainFrame().add(sendButton);

		getMainFrame().setLocation(400, 300);
		getMainFrame().setResizable(false);
		getMainFrame().setVisible(true);
		getMainFrame().pack();
		getMainFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		refreshClient();
	}

	public static JFrame getMainFrame()
	{
		return mainFrame;
	}

	public static void setMainFrame(JFrame frame)
	{
		SendFileUI.mainFrame = frame;
	}
}
