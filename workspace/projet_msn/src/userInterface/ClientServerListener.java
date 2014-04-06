package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JOptionPane;

/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class ClientServerListener implements ActionListener
{
	public static final int TIMEOUT = 50;

	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("action:" + e.getActionCommand());
		switch (e.getActionCommand())
		{
		case "Profil":
			JOptionPane.showMessageDialog(ClientServerUI.getMainFrame(), "Option non implémenté.");
			break;
		case "Rafraichir":
			if (!ClientServerUI.client.getId().equals(""))
			{
				HashMap<String, String[]> list = new HashMap<String, String[]>(ClientServerUI.client.getClientList());
				ClientServerUI.client.askListToServer();
				int ttl = 0;
				while (list.equals(ClientServerUI.client.getClientList()) && ttl < 7)
				{
					try
					{
						Thread.sleep(200);
						ttl++;
					} catch (InterruptedException e1)
					{
					}
				}
				System.out.println(ClientServerUI.client.getClientList());
				System.out.println(ClientServerUI.clientList);

				ClientServerUI.refreshClient();
			}
			break;
		case "Se connecter":
			ClientServerUI.client.setName(ClientServerUI.pseudoField.getText());
			char[] charPassword = ClientServerUI.passwordField.getPassword();
			String password = "";
			for (int i = 0; i < charPassword.length; i++)
			{
				password += charPassword[i];
			}
			ClientServerUI.client.setPassword(password);
			ClientServerUI.client.registerToServer();
			try
			{
				int cpt = 0;
				while ((ClientServerUI.client.getId() == null || ClientServerUI.client.getId().equals("")) && "".equals(ClientServerUI.client.getErrorsMessages()) && cpt < TIMEOUT)
				{
					Thread.sleep(200);
					cpt++;
				}
				if (!"".equals(ClientServerUI.client.getErrorsMessages()))
				{
					ClientServerUI.alert(ClientServerUI.client.getErrorsMessages());
					ClientServerUI.client.setErrorsMessages("");
				} else if (cpt >= TIMEOUT)
				{
					ClientServerUI.alert("Temps de connexion dépassé");
				} else
				{
					ClientServerUI.refreshClient();
				}

			} catch (InterruptedException e1)
			{

			}
			break;
		case "Se déconnecter":
			ClientServerUI.client.unregisterToServer();
			ClientServerUI.jClientList.setVisible(false);
			ClientServerUI.personalMessageField.setVisible(false);
			ClientServerUI.connectionPanel.setVisible(true);
			break;
		case "Adresse serveur":
			if (ClientServerUI.client.getId().equals(""))
			{
				String ipServer = JOptionPane.showInputDialog(ClientServerUI.getMainFrame(), "IP serveur", ClientServerUI.client.getIpServer());
				if (ipServer != null)
				{
					ClientServerUI.client.setIpServer(ipServer);
				}
			} else
			{
				JOptionPane.showMessageDialog(ClientServerUI.getMainFrame(), "Impossible de modifier l'adresse du serveur en étant connecté.");
			}
			break;
		case "Port UDP serveur":
			if (ClientServerUI.client.getId().equals(""))
			{
				String udpServerPort = JOptionPane.showInputDialog(ClientServerUI.getMainFrame(), "Port UDP serveur", ClientServerUI.client.getUdpServerPort());
				if (udpServerPort != null)
				{
					ClientServerUI.client.setUdpServerPort(Integer.parseInt(udpServerPort));
				}
			} else
			{
				JOptionPane.showMessageDialog(ClientServerUI.getMainFrame(), "Impossible de modifier le port UDP serveur en étant connecté.");
			}
			break;
		case "Port TCP serveur":
			if (ClientServerUI.client.getId().equals(""))
			{
				String tcpServerPort = JOptionPane.showInputDialog(ClientServerUI.getMainFrame(), "Port TCP serveur", ClientServerUI.client.getTcpServerPort());
				if (tcpServerPort != null)
				{
					ClientServerUI.client.setTcpServerPort(Integer.parseInt(tcpServerPort));
				}
			} else
			{
				JOptionPane.showMessageDialog(ClientServerUI.getMainFrame(), "Impossible de modifier le port TCP serveur en étant connecté.");
			}
			break;
		case "Port UDP":
			if (ClientServerUI.client.getId().equals(""))
			{
				String port = JOptionPane.showInputDialog(ClientServerUI.getMainFrame(), "Port UDP", ClientServerUI.client.getListeningUDPPort());
				if (port != null)
				{
					ClientServerUI.client.setListeningUDPPort(Integer.parseInt(port));
				}
			} else
			{
				JOptionPane.showMessageDialog(ClientServerUI.getMainFrame(), "Impossible de modifier le port UDP en étant connecté.");
			}
			break;
		case "Port TCP":
			if (ClientServerUI.client.getId().equals(""))
			{
				String port = JOptionPane.showInputDialog(ClientServerUI.getMainFrame(), "Port TCP", ClientServerUI.client.getListeningTCPPort());
				if (port != null)
				{
					ClientServerUI.client.setListeningTCPPort(Integer.parseInt(port));
				}
			} else
			{
				JOptionPane.showMessageDialog(ClientServerUI.getMainFrame(), "Impossible de modifier le port TCP en étant connecté.");
			}
			break;
		default:
			break;
		}
	}
}
