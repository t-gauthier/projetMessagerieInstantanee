package network;

import java.net.InetAddress;

/**
 * Classe abstraite permettant de représenter un protocole de
 *           communication.
 *           
 * @author Dorian, Mickaël, Raphaël, Thibault
 */
public abstract class Protocol
{
	/**
	 * Port de communication du protocole.
	 */
	private int localPort;

	/**
	 * Constructeur qui prend un paramètre.
	 * 
	 * @param localPort
	 */
	public Protocol(int localPort)
	{
		this.localPort = localPort;
	}

	/**
	 * Méthode permettant d'envoyer un message à une adresse via un port.
	 * 
	 * @param message message que l'on souhaite envoyer
	 * @param adress adresse de destination
	 * @param port port de destination
	 */
	public abstract void sendMessage(String message, InetAddress adress, int port);

	/**
	 * Méthode permettant d'envoyer un message à l'adresse par defaut du
	 * protocole.
	 * 
	 * @param message message que l'on souhaite envoyer
	 */
	public abstract void sendMessage(String message);

	/**
	 * Méthode permettant de réceptionner un message.
	 * 
	 * @return Chaine réceptionnée
	 */
	public abstract String readMessage();

	/**
	 * Méthode permettant de fermer le protocole.
	 */
	public abstract void close();

	/**
	 * Getter du port local
	 * 
	 * @return localPort, Port local
	 */
	public int getLocalPort()
	{
		return localPort;
	}

	/**
	 * Setter qui fixe le port local
	 * 
	 * @param localPort port de communication
	 *           
	 */
	public void setLocalPort(int localPort)
	{
		this.localPort = localPort;
	}

}
