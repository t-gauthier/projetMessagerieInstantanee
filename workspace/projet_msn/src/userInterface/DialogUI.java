package userInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import model.Client;
import model.ClientDialog;
import model.ClientServerData;

/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class DialogUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Client client;

	private Vector<ClientDialog> dialogList;
	private Vector<JListData> simpleDialogtList;
	private JList<JListData> jDialogList;
	private JTextArea textAreaDialog;
	private JTextArea textAreaSaisie;

	/**
	 * Create the frame.
	 */
	public DialogUI(Client clientServer)
	{
		this.client = clientServer;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setBounds(100, 100, 600, 420);
		this.setMinimumSize(new Dimension(600, 420));
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 0));

		Box containerGlobal = Box.createVerticalBox();

		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(10, 2500));
		topPanel.setLayout(new BorderLayout(0, 0));

		Box topBox = Box.createHorizontalBox();

		// JPanel panelListDialog = new JPanel();
		// panelListDialog.setMinimumSize(new Dimension(300, 200));
		// panelListDialog.setMaximumSize(new Dimension(300, 1000));
		// panelListDialog.setLayout(new BorderLayout(0, 0));

		this.simpleDialogtList = new Vector<JListData>();
		this.jDialogList = new JList<JListData>(this.simpleDialogtList);
		this.jDialogList.setPreferredSize(new Dimension(110, 250));
		this.jDialogList.setMinimumSize(new Dimension(110, 250));
		this.jDialogList.setMaximumSize(new Dimension(1000, 3000));
		this.jDialogList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane dialogList = new JScrollPane(this.jDialogList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		dialogList.setMinimumSize(new Dimension(120, 260));
		dialogList.setMaximumSize(new Dimension(120, 3000));

		Component topSpaceBorderVertical = Box.createHorizontalStrut(20);
		topSpaceBorderVertical.setPreferredSize(new Dimension(5, 0));

		this.textAreaDialog = new JTextArea();
		this.textAreaDialog.setLineWrap(true);
		this.textAreaDialog.setWrapStyleWord(true);
		this.textAreaDialog.setEditable(false);
		JScrollPane areaDialog = new JScrollPane(this.textAreaDialog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		areaDialog.setAutoscrolls(true);
		Component spaceBorderHorizontal = Box.createVerticalStrut(5);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout(5, 0));

		Box bottomBox = Box.createHorizontalBox();

		this.textAreaSaisie = new JTextArea();
		this.textAreaSaisie.setLineWrap(true);
		this.textAreaSaisie.setWrapStyleWord(true);
		JScrollPane areaSaisie = new JScrollPane(this.textAreaSaisie, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.textAreaSaisie.addKeyListener(new KeyListener()
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
					sendMessage();
				}
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
			}
		});

		Component bottomSpaceBorderVertical = Box.createHorizontalStrut(20);
		bottomSpaceBorderVertical.setPreferredSize(new Dimension(5, 0));

		Box groupButton = Box.createVerticalBox();

		JButton buttonSend = new JButton("Envoyer");
		buttonSend.setPreferredSize(new Dimension(90, 23));
		buttonSend.setMaximumSize(new Dimension(90, 23));
		buttonSend.setMinimumSize(new Dimension(90, 23));
		buttonSend.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				sendMessage();
			}
		});

		JButton buttonAddClient = new JButton("Ajouter");
		buttonAddClient.setPreferredSize(new Dimension(90, 23));
		buttonAddClient.setMaximumSize(new Dimension(90, 23));
		buttonAddClient.setMinimumSize(new Dimension(90, 23));
		buttonAddClient.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// hideDialog();
				if (jDialogList.getSelectedValue() != null)
				{
					JListData dialogListElement = jDialogList.getSelectedValue();
					String idDialog = dialogListElement.getKey();
					ArrayList<ClientDialog> listDialog = client.getDialogs();
					ClientDialog dialog = null;
					for (ClientDialog clientDialog : listDialog)
					{
						if (clientDialog.getIdDialog().equals(idDialog))
						{
							dialog = clientDialog;
						}
					}
					if (dialog != null)
					{
						AddClientUI addClient = new AddClientUI(client, dialog);
						addClient.toString();
					}
				}
			}
		});
		
		JButton buttonSendFile = new JButton("Fichier");
		buttonSendFile.setPreferredSize(new Dimension(90, 23));
		buttonSendFile.setMaximumSize(new Dimension(90, 23));
		buttonSendFile.setMinimumSize(new Dimension(90, 23));
		buttonSendFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// hideDialog();
				if (jDialogList.getSelectedValue() != null)
				{
					JListData dialogListElement = jDialogList.getSelectedValue();
					String idDialog = dialogListElement.getKey();
					ArrayList<ClientDialog> listDialog = client.getDialogs();
					ClientDialog dialog = null;
					for (ClientDialog clientDialog : listDialog)
					{
						if (clientDialog.getIdDialog().equals(idDialog))
						{
							dialog = clientDialog;
						}
					}
					if (dialog != null)
					{
						SendFileUI addClient = new SendFileUI(client, dialog);
						addClient.toString();
					}
				}
			}
		});

		JButton buttonCloseDialog = new JButton("Fermer");
		buttonCloseDialog.setPreferredSize(new Dimension(90, 23));
		buttonCloseDialog.setMaximumSize(new Dimension(90, 23));
		buttonCloseDialog.setMinimumSize(new Dimension(90, 23));
		buttonCloseDialog.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				hideDialog();
			}
		});

		groupButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		groupButton.add(buttonSend);
		groupButton.add(buttonAddClient);
		groupButton.add(buttonSendFile);
		groupButton.add(buttonCloseDialog);
		buttonSend.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttonAddClient.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttonCloseDialog.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttonSendFile.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// panelListDialog.add(this.jDialogList, BorderLayout.CENTER);
		dialogList.setPreferredSize(new Dimension(120, 280));
		topBox.add(dialogList);
		topBox.add(topSpaceBorderVertical);
		topBox.add(areaDialog);

		bottomBox.add(areaSaisie);
		bottomBox.add(bottomSpaceBorderVertical);
		bottomBox.add(groupButton);

		topPanel.add(topBox, BorderLayout.CENTER);
		bottomPanel.add(bottomBox, BorderLayout.CENTER);

		containerGlobal.add(topPanel);
		containerGlobal.add(spaceBorderHorizontal);
		containerGlobal.add(bottomPanel);

		this.contentPane.add(containerGlobal, BorderLayout.CENTER);
		this.setContentPane(this.contentPane);

		// Thread de mise a jour de la liste de conversation
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				ArrayList<ClientDialog> temp = new ArrayList<ClientDialog>(client.getDialogs());
				while (true)
				{
					if (!temp.equals(client.getDialogs()))
					{
						temp = new ArrayList<ClientDialog>(client.getDialogs());
						refreshList();
					} else
					{
						int cptInUse = 0;
						for (int i = 0; i < client.getDialogs().size(); i++)
						{
							if (client.getDialogs().get(i).isInUse())
							{
								cptInUse++;
							}
						}
						int nbClientToDial = 0;
						for (int i = 0; i < client.getDialogs().size(); i++)
						{
							if (client.getDialogs().get(i).isInUse())
							{
								nbClientToDial += client.getDialogs().get(i).getClients().size();
							}
						}
						if (cptInUse > jDialogList.getModel().getSize())
						{
							refreshList();
						} else
						{
							int nbClientToDialInList = 0;
							for (int j = 0; j < jDialogList.getModel().getSize(); j++)
							{
								nbClientToDialInList += jDialogList.getModel().getElementAt(j).getValue().split(",").length;
							}
							if (nbClientToDial != nbClientToDialInList)
							{
								System.out.println(nbClientToDial + " - " + nbClientToDialInList);
								refreshList();
							}
						}
					}
					try
					{
						Thread.sleep(200);
					} catch (InterruptedException e)
					{
					}
				}

			}
		}).start();
		// Thread de mise a jour de la conversation
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					if (!jDialogList.isSelectionEmpty())
					{
						JListData dialog = jDialogList.getSelectedValue();
						String idDialog = dialog.getKey();
						ArrayList<ClientDialog> listDialog = client.getDialogs();
						String textDialog = "";
						for (ClientDialog clientDialog : listDialog)
						{
							if (clientDialog.getIdDialog().equals(idDialog))
							{
								textDialog = clientDialog.getDialogue();
							}
						}
						textAreaDialog.setText(textDialog);
					}

					try
					{
						Thread.sleep(20);
					} catch (InterruptedException e)
					{

					}
				}

			}
		}).start();
	}

	public void refreshList()
	{
		this.dialogList = new Vector<ClientDialog>(this.client.getDialogs());
		this.simpleDialogtList = new Vector<JListData>();
		JListData sauvDialog = this.jDialogList.getSelectedValue();
		int indiceClient = -1;
		int cpt = 0;
		for (ClientDialog dialog : this.dialogList)
		{
			if (dialog.isInUse())
			{
				String idDialog = dialog.getIdDialog();
				ArrayList<ClientServerData> clients = dialog.getClients();
				String clientstring = "";
				for (int i = 0; i < clients.size(); i++)
				{
					clientstring += ((i == 0) ? "" : ", ") + clients.get(i).getName();
				}
				JListData clientListData = new JListData(idDialog, clientstring);
				this.simpleDialogtList.add(clientListData);
				if (sauvDialog != null && sauvDialog.equals(clientListData))
				{
					indiceClient = cpt;
				}
				cpt++;
			}
		}
		System.out.println("nouvelle list" + this.simpleDialogtList);
		this.jDialogList.setListData(this.simpleDialogtList);
		if (this.textAreaSaisie.getText().trim().equals(""))
		{
			this.jDialogList.setSelectedIndices(new int[] { this.jDialogList.getModel().getSize() - 1 });
		} else if (sauvDialog != null)
		{
			this.jDialogList.setSelectedIndices(new int[] { indiceClient });
		}
		if (this.jDialogList.getSelectedValue() != null)
		{
			this.setTitle("Dialog-" + this.client.getName() + ":" + this.jDialogList.getSelectedValue());
		} else
		{
			this.setTitle("Dialog-" + this.client.getName());
		}
	}

	public void sendMessage()
	{
		if (this.jDialogList.getSelectedValue() != null)
		{
			JListData dialog = this.jDialogList.getSelectedValue();
			String message = this.textAreaSaisie.getText();
			String[] messagesSplit = message.split("[\r\n]");
			message = "";
			for (String messageSplit : messagesSplit)
			{
				message += messageSplit + " ";
			}
			this.client.sendMessageToDialog(message, dialog.getKey());
			this.textAreaSaisie.setText("");
		}
	}

	public void hideDialog()
	{
		if (this.jDialogList.getSelectedValue() != null)
		{
			JListData dialog = this.jDialogList.getSelectedValue();
			this.client.hideDialog(dialog.getKey());
			this.textAreaSaisie.setText("");
			this.textAreaDialog.setText("");
			this.refreshList();
		}
	}

}
