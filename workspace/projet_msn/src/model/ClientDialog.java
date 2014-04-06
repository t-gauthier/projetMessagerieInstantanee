package model;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;

import network.FileTransfer;
import network.Protocol;

/**
 * Classe représentant un dialogue entre clients.
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 */
public class ClientDialog
{
	/**
	 * Client a qui appartient le dialogue
	 * 
	 * @see Client
	 */
	private Client client;
	/**
	 * Identifiant du dialogue. Clé permettant d'identifier le dialogue de façon
	 * unique.
	 */
	private String idDialog;
	/**
	 * Liste des clients avec qui le client propriétaire dialogue
	 * 
	 * @see ClientServerData
	 */
	private ArrayList<ClientServerData> clients;
	/**
	 * Protocol permettant au dialogue de communiquer.
	 * 
	 * @see Protocol
	 */
	private Protocol protocol;
	/**
	 * Ensemble des messages échangés
	 */
	private String dialogue;
	/**
	 * Dernier message échangé
	 */
	private String lastMessage;
	/**
	 * Attribut permettant de savoir si la conversation est encore active.
	 */
	private boolean inUse;

	/**
	 * Constructeur avec 2 paramètres créant un dialogue. Ce constructeur est
	 * appelé par le client lors de la création d'un dialogue. Une clé unique
	 * est générée par le constructeur pour identifier le dialogue.
	 * 
	 * @param client
	 *            client auquel on démarre un dialogue
	 * @param protocol
	 *            protocole utilisé pour ce dialogue
	 */
	public ClientDialog(Client client, Protocol protocol)
	{
		this.client = client;
		SecureRandom random = new SecureRandom();
		this.idDialog = new BigInteger(130, random).toString(32);
		this.clients = new ArrayList<ClientServerData>();
		this.protocol = protocol;
		this.dialogue = "";
		this.inUse = true;
	}

	/**
	 * Constructeur avec 2 paramètres créant un dialogue. Ce constructeur est
	 * appelé par le client lorsqu'il reçoit une notification de dialogue par un
	 * autre client. Le premier paramètre correspond à la clé unique du dialogue
	 * qui a du être reçu.
	 * 
	 * @param idDialog
	 *            clé unique du dialogue
	 * @param client
	 *            client auquel est rataché le dialogue
	 * @param protocol
	 *            protocol utilisé pour ce dialogue
	 */
	public ClientDialog(String idDialog, Client client, Protocol protocol)
	{
		this.client = client;
		this.idDialog = idDialog;
		this.clients = new ArrayList<ClientServerData>();
		this.protocol = protocol;
		this.dialogue = "";
		this.lastMessage = "";
		this.inUse = true;
	}

	/**
	 * Méthode permettant d'ajouter un message au dialogue
	 * 
	 * @param message
	 *            rajoute un message sur la fenêtre du dialogue
	 */
	public void addMessage(String message)
	{
		this.dialogue += "\n" + message;
		this.lastMessage = message;
	}

	/**
	 * Méthode permettant d'envoyer un message à tous les clients du dialogue.
	 * 
	 * @param message
	 *            message que l'on souhaite envoyé à l'autre client
	 *            {@link #addMessage(String)}
	 */
	public void sendMessage(String message)
	{
		this.inUse = true;
		for (ClientServerData client : this.clients)
		{
			this.protocol.sendMessage("dialog:message:" + this.idDialog + ":" + this.client.getName() + ">" + message, client.getIp(), client.getPortUDP());
		}
		this.addMessage("moi>" + message);
	}

	/**
	 * Méthode permettant d'envoyer un fichier à tous les clients du dialogue.
	 * 
	 * @param file
	 *            fichier que l'on souhaite envoyé à l'autre client
	 * 
	 */
	public void sendFile(String file)
	{
		this.inUse = true;
		for (ClientServerData client : this.clients)
		{
			try
			{
				Socket socket = new Socket(client.getIp(), client.getPortTCP());
				System.out.println("Connecting...");
				OutputStream os = socket.getOutputStream();
				// send file
				try
				{
					FileTransfer.send(os, file, this.idDialog);
					this.addMessage( "Fichier (" + file + ") envoyé dans la conversation");
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				socket.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Méthode permettant de gérer la réception d'un message
	 * 
	 * @param message
	 *            message reçu
	 * @return message, message reçu traité {@link #addMessage(String)}
	 */
	public String receiveMessage(String message)
	{
		this.inUse = true;
		this.addMessage(message);
		return message;
	}

	/**
	 * Méthode permettant de gérer la réception d'un fichier
	 * 
	 * @param file
	 *            fichier reçu
	 * @return message, message reçu traité
	 */
	public String receiveFile(String file)
	{
		this.inUse = true;
		this.addMessage( "Fichier (" + file + ") reçu depuis la conversation");
		return file;
	}

	/**
	 * Méthode permettant d'ajouter un client au dialogue
	 * 
	 * @param client
	 *            client que l'on souhaite ajouter au dialogue
	 * @return true si le client est bien ajouté, false sinon.
	 */
	public boolean addClient(ClientServerData client)
	{
		boolean alreadyInDialog = false;
		for (ClientServerData clientL : this.clients)
		{
			if (client.equals(clientL))
			{
				alreadyInDialog = true;
			}
		}
		if (!alreadyInDialog && !this.client.equals(client))
		{
			return this.clients.add(client);
		}
		return false;
	}

	/**
	 * Méthode permettant de supprimer un client du dialogue.
	 * 
	 * @param client
	 *            à supprimer
	 * @return la liste des clients à jour
	 */
	public boolean removeClient(ClientServerData client)
	{
		return this.clients.remove(client);
	}

	/**
	 * Getter de la clé unique du dialogue
	 * 
	 * @return idDialog, la clé unique du dialogue
	 */
	public String getIdDialog()
	{
		return idDialog;
	}

	/**
	 * Setter qui fixe la clé unique du dialogue
	 * 
	 * @param idDialog
	 *            la clé unique du dialogue
	 */
	public void setIdDialog(String idDialog)
	{
		this.idDialog = idDialog;
	}

	/**
	 * Getter de la liste des clients qui le client voit
	 * 
	 * @return clients, la liste des clients qui le client voit
	 */
	public ArrayList<ClientServerData> getClients()
	{
		return clients;
	}

	/**
	 * Setter qui fixe la liste des clients qui le client voit
	 * 
	 * @param clients
	 *            la liste des clients que l'on veut ajouter
	 */
	public void setClients(ArrayList<ClientServerData> clients)
	{
		this.clients = clients;
	}

	/**
	 * Getter du protocole utilisé
	 * 
	 * @return le protocole utilisé
	 */
	public Protocol getProtocol()
	{
		return protocol;
	}

	/**
	 * Setter qui fixe le protocole utilisé
	 * 
	 * @param protocol
	 *            le protocole utilisé
	 */
	public void setProtocol(Protocol protocol)
	{
		this.protocol = protocol;
	}

	/**
	 * Getter du dialogue, c'est à dire l'ensemble de la conversation entre n
	 * clients
	 * 
	 * @return dialogue, le dialogue
	 */
	public String getDialogue()
	{
		return dialogue;
	}

	/**
	 * Setter qui fixe le dialogue, c'est à dire l'ensemble de la conversation
	 * entre n clients
	 * 
	 * @param dialogue
	 *            conversation complète entre n clients
	 */
	public void setDialogue(String dialogue)
	{
		this.dialogue = dialogue;
	}

	/**
	 * Getter du dernier message d'un dialogue
	 * 
	 * @return lastMessage, le dernier message
	 */
	public String getLastMessage()
	{
		return lastMessage;
	}

	/**
	 * Setter qui fixe le dernier message d'un dialogue
	 * 
	 * @param lastMessage
	 *            message que l'on veut ajouter à la conversation
	 */
	public void setLastMessage(String lastMessage)
	{
		this.lastMessage = lastMessage;
	}

	/**
	 * Getter pour savoir si la conversation est active ou non
	 * 
	 * @return inUse true si la conversation est active, false sinon
	 */
	public boolean isInUse()
	{
		return inUse;
	}

	/**
	 * Setter qui fixe l'état de la conversation
	 * 
	 * @param inUse
	 *            état de la conversation
	 */
	public void setInUse(boolean inUse)
	{
		this.inUse = inUse;
	}
}
