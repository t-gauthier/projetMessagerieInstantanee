package model;

import java.math.BigInteger;
import java.net.InetAddress;
import java.security.SecureRandom;

/**
 * 
 * Classe stockant les informations des clients. {@link AbstractClientServer}.
 * Comme son nom, son IP, son port etc...
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 */
public class ClientServerData
{
	/**
	 * Clé publique unique d'un client.
	 */
	private String id;
	/**
	 * Nom d'un client.
	 */
	private String name;
	/**
	 * Message perso du client.
	 */
	private String personalMessage;
	/**
	 * groupes auquel appartient le client.
	 */
	private String groups;
	/**
	 * Adresse ip du client.
	 */
	private InetAddress ip;
	/**
	 * Port UDP d'écoute du client.
	 */
	private int portUDP;
	/**
	 * Port TCP d'écoute du client.
	 */
	private int portTCP;

	/**
	 * Contructeur qui prend 3 paramètres. Ce constructeur génère une clé
	 * publique unique pour le client.
	 * 
	 * @param name
	 *            nom du client
	 * @param ip
	 *            ip du client
	 * @param portUDP
	 *            port d'écoute UDP du client
	 * @param portTCP
	 *            port d'écoute TCP du client
	 */
	public ClientServerData(String name, InetAddress ip, int portUDP, int portTCP)
	{
		SecureRandom random = new SecureRandom();
		this.id = new BigInteger(130, random).toString(32);
		this.name = name;
		this.ip = ip;
		this.portUDP = portUDP;
		this.portTCP = portTCP;
		this.personalMessage = "";
		this.groups = "";
	}

	/**
	 * Constructeur qui prend 4 paramètres de ClientServerData.
	 * 
	 * @param id
	 *            clé publique du client
	 * @param name
	 *            nom du client
	 * @param ip
	 *            ip du client
	 * @param portUDP
	 *            port d'écoute UDP du client
	 * @param portTCP
	 *            port d'écoute TCP du client
	 */
	public ClientServerData(String id, String name, InetAddress ip, int portUDP, int portTCP)
	{
		this.id = id;
		this.name = name;
		this.ip = ip;
		this.portUDP = portUDP;
		this.portTCP = portTCP;
		this.personalMessage = "";
		this.groups = "";
	}

	/**
	 * Constructeur qui prend 4 paramètres de ClientServerData.
	 * 
	 * @param name
	 *            nom du client
	 * @param ip
	 *            ip du client
	 * @param portUDP
	 *            port d'écoute UDP du client
	 * @param portTCP
	 *            port d'écoute TCP du client
	 * @param groups
	 *            groupes du client
	 */
	public ClientServerData(String name, InetAddress ip, int portUDP, int portTCP, String groups)
	{
		SecureRandom random = new SecureRandom();
		this.id = new BigInteger(130, random).toString(32);
		this.name = name;
		this.ip = ip;
		this.portUDP = portUDP;
		this.portTCP = portTCP;
		this.personalMessage = "";
		this.groups = groups;
	}

	/**
	 * Getter du nom du client
	 * 
	 * @return name, le nom du client
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Setter qui fixe le nom du client
	 * 
	 * @param name
	 *            nom du client
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Getter de l'adresse IP
	 * 
	 * @return ip, l'ip du client
	 */
	public InetAddress getIp()
	{
		return ip;
	}

	/**
	 * Setter qui fixe l'adresse IP du client
	 * 
	 * @param ip
	 *            l'ip du client
	 */
	public void setIp(InetAddress ip)
	{
		this.ip = ip;
	}

	/**
	 * Getter du port d'écoute UDP du client
	 * 
	 * @return port, le port d'écoute UDP
	 */
	public int getPortUDP()
	{
		return portUDP;
	}

	/**
	 * Setter qui fixe le port UDP d'écoute du client
	 * 
	 * @param portUDP
	 *            port UDP
	 */
	public void setPortUDP(int portUDP)
	{
		this.portUDP = portUDP;
	}
	
	/**
	 * Getter du port d'écoute TCP du client
	 * 
	 * @return port, le port d'écoute TCP
	 */
	public int getPortTCP()
	{
		return portTCP;
	}

	/**
	 * Setter qui fixe le port TCP d'écoute du client
	 * 
	 * @param portTCP
	 *            port TCP
	 */
	public void setPortTCP(int portTCP)
	{
		this.portTCP = portTCP;
	}

	/**
	 * Getter de la clé publique du client
	 * 
	 * @return id, la clé publique du client
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Setter qui fixe la clé publique du client
	 * 
	 * @param id
	 *            clé publique unique du client
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * Getter du message perso du client
	 * 
	 * @return personalMessage, le message perso du client
	 */
	public String getPersonalMessage()
	{
		return personalMessage;
	}

	/**
	 * Setter qui fixe le message perso du client
	 * 
	 * @param personalMessage
	 *            message perso du client
	 */
	public void setPersonalMessage(String personalMessage)
	{
		this.personalMessage = personalMessage;
	}

	/**
	 * Getter des groupes du client
	 * 
	 * @return groups, les groupes du client
	 */
	public String getGroups()
	{
		return groups;
	}

	/**
	 * Setter qui fixe les groupes du client
	 * 
	 * @param groups
	 *            groupes du client
	 */
	public void setGroups(String groups)
	{
		this.groups = groups;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ClientServerData))
		{
			return false;
		} else if (obj == this)
		{
			return true;
		} else if (!this.name.equals(((ClientServerData) obj).name))
		{
			return false;
		} else if (this.portUDP != ((ClientServerData) obj).portUDP)
		{
			return false;
		} else if (!this.ip.equals(((ClientServerData) obj).ip))
		{
			return false;
		} else if (!this.id.equals(((ClientServerData) obj).id))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "Client: " + this.name + " " + this.ip + " " + this.portUDP;
	}
}
