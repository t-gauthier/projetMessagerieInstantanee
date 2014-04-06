package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Classe représentant le protocole UDP.
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class ProtocolUDP extends Protocol
{
	/**
	 * 
	 * {@link DatagramSocket} Socket de communication UDP.
	 */
	private DatagramSocket socket;
	/**
	 * {@link DatagramPacket} permettant l'envoie de paquet.
	 */
	private DatagramPacket writer;
	/**
	 * {@link DatagramPacket} permettant la reception de paquet.
	 */
	private DatagramPacket reader;
	/**
	 * Buffer servant à la reception des paquets.
	 */
	private static byte bufferReader[];
	/**
	 * Taille du buffer.
	 * Pourquoi 60000 ? Limite théorique = 65535, limite en IPv4 = 65507
	 */
	private final static int sizeBufferReader = 60000;
	/**
	 * Dernier port avec lequel le protocol a communiqué
	 * (Port ouvert du dernier client avec lequel il a comuniqué)
	 */
	private int lastPort;
	/**
	 * Dernière adresse avec laquelle le protocol a communiqué
	 * (Adresse Ip du dernier client avec lequel il a comuniqué)
	 */
	private InetAddress lastAdress;

	/**
	 * Constructeur qui prend un paramètre
	 * @param localPort portlocal UDP
	 */
	public ProtocolUDP(int localPort)
	{
		super(localPort);
		try
		{
			this.socket = new DatagramSocket(localPort);
			byte[] buffer = ("").getBytes();
			bufferReader = new byte[sizeBufferReader];
			this.writer = new DatagramPacket(buffer, 0);
			this.reader = new DatagramPacket(bufferReader, sizeBufferReader);
			this.lastPort = 0;
			this.lastAdress = null;
		} catch (SocketException e)
		{
			System.err.println("Erreur d'initialisation de ProtocolUDP, message: " + e.getMessage());
		}
	}
	
	/**
	 * Méthode permettant d'envoyer un message en connaissant une adresse IP et un port
	 * 
	 * @param message message envoyé
	 * @param adress adresse IP du destinataire
	 * @param port port TCP du destinataire
	 */
	public void sendMessage(String message, InetAddress adress, int port)
	{
		byte buffer[];
		try
		{
			buffer = message.getBytes("UTF-8");
			int length = buffer.length;
			this.writer = new DatagramPacket(buffer, length, adress, port);
			this.socket.send(writer);
		} catch (IOException e)
		{
			System.err.println("Erreur d'envoie du message de ProtocolUDP, message:" + e.getMessage());
		}
	}

	/**
	 * Méthode permettant d'envoyer un message en connaissant une adresse IP et un port
	 * 
	 * @param message message envoyé
	 */
	public void sendMessage(String message)
	{
		byte buffer[];
		try
		{
			buffer = message.getBytes("UTF-8");
			int length = buffer.length;
			this.writer = new DatagramPacket(buffer, length, this.socket.getInetAddress(), this.getLocalPort());
			this.socket.send(writer);
		} catch (IOException e)
		{
			System.err.println("Erreur d'envoie du message de ProtocolUDP, message:" + e.getMessage());
		}
	}

	/**
	 * Méthode permettant de lire un message
	 * 
	 * @return le message lu
	 */
	public String readMessage()
	{
		try
		{
			bufferReader = new byte[sizeBufferReader];
			DatagramPacket data = new DatagramPacket(bufferReader, sizeBufferReader);
			socket.receive(data);
			this.lastPort = data.getPort();
			this.lastAdress = data.getAddress();
			return new String(data.getData(), 0, data.getLength());
		} catch (IOException e)
		{
			System.err.println("Erreur de reception de message de ProtocolUDP, message:" + e.getMessage());
		}
		return "";
	}

	/**
	 * Méthode permettant de fermer le protocole
	 * 
	 */
	public void close()
	{
		this.socket.close();
	}

	/**
	 * Getter du DatagramPacket du protocole UDP, qui nous sert de writer
	 * 
	 * @return le writer du protocol {@link DatagramPacket}
	 */
	public DatagramPacket getWriter()
	{
		return writer;
	}

	/**
	 * Setter qui fixe le writer du protocole UDP
	 * 
	 * @param writer
	 *            objet DatagramPacket {@link DatagramPacket}
	 */
	public void setWriter(DatagramPacket writer)
	{
		this.writer = writer;
	}

	/**
	 * Getter du DatagramPacket du protocole UDP, qui nous sert de reader
	 * 
	 * @return le DatagramPacket du protocole UDP{@link DatagramPacket}
	 */
	public DatagramPacket getReader()
	{
		return reader;
	}

	/**
	 * Setter qui fixe le DatagramPacket du protocole UDP, qui nous sert de reader
	 * 
	 * @param reader
	 *            objet DatagramPacket de java {@link DatagramPacket}
	 */
	public void setReader(DatagramPacket reader)
	{
		this.reader = reader;
	}

	/**
	 * Getter du dernier port utilisé
	 * 
	 * @return le dernier port utilisé
	 */
	public int getLastPort()
	{
		return lastPort;
	}

	/**
	 * Setter qui fixe le dernier port utilisé
	 * 
	 * @param lastPort dernier port par lequel on a reçu un message
	 *           
	 */
	public void setLastPort(int lastPort)
	{
		this.lastPort = lastPort;
	}

	/**
	 * Getter de la dernière adresse utilisée
	 * 
	 * @return la dernière adresse utilisée
	 */
	public InetAddress getLastAdress()
	{
		return lastAdress;
	}

	/**
	 * Setter qui fixe la dernière adresse utilisée
	 * 
	 * @param lastAdress dernière adresse utilisée pour l'envoie de message
	 *           
	 */
	public void setLastAdress(InetAddress lastAdress)
	{
		this.lastAdress = lastAdress;
	}

}
