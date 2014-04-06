package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Logger;

import network.FileTransfer;
import network.Protocol;
import network.ProtocolTCP;

/**
 *         Classe représentant le threa de comunication d'un serveur vers un client. Il permet de
 *         gérer les demandes client : connexion, déconnexion, demande de lien
 *         etc...
 *         
 *         @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class ThreadFileTransfer extends Thread
{
	/**
	 * Client à qui appartient le thread de communication.
	 * 
	 * @see Client
	 */
	private Client clientServer;
	/**
	 * Socket de communication.
	 * 
	 * @see Socket
	 */
	private Socket socket;
	/**
	 * Protocol de communication.
	 * 
	 * @see Protocol
	 */
	private Protocol protocol;
	/**
	 * Paramètre permettant d'arrêter le thread.
	 */
	private boolean running;
	private static Logger logger = Logger.getLogger(ThreadFileTransfer.class.toString());

	/**
	 * Constructeur du Thread qui prend 2 paramètres .
	 * 
	 * @param server Serveur de l'application
	 * @param socket Socket du serveur
	 */
	public ThreadFileTransfer(Client clientServer, Socket socket)
	{
		this.clientServer = clientServer;
		this.socket = socket;
		this.protocol = new ProtocolTCP(socket);
		logger.setParent(AbstractClientServer.getLogger());
	}

	@Override
	public void run()
	{
		try
		{
			logger.info("Lancement du Thread de transfert de fichier");
			this.running = true;
			//while (running)
			{
				Thread.sleep(500);
				//String message = protocol.readMessage();
				InputStream is = this.socket.getInputStream();
				try
				{
					FileTransfer.receiveFile(is,clientServer);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			logger.info("Arret du Thread de transfert de fichier");
			this.socket.close();
			this.protocol.close();
		} catch (IOException | InterruptedException e)
		{
			logger.severe("Erreur du ThreadComunicationServer, message: " + e.getMessage());
		}
	}

	/**
	 * Méthode permettant d'arrêter le thread
	 */
	public void stopThread()
	{
		this.running = false;
	}

	/**
	 * Getter du socket du thread
	 * 
	 * @return le socket utilisé
	 */
	public Socket getSocket()
	{
		return socket;
	}

	/**
	 * Setter qui fixe le socket du thread
	 * 
	 * @param socket
	 *            le socket que l'on souhaite utilisé pour le thread
	 */
	public void setSocket(Socket socket)
	{
		this.socket = socket;
	}

	/**
	 * Getter du protocol du thread
	 * 
	 * @return le protocol utilisé
	 */
	public Protocol getProtocol()
	{
		return protocol;
	}

	/**
	 * Setter qui fixe le protocol du thread
	 * 
	 * @param protocol
	 *            protocol que l'on souhaite utiliser
	 */
	public void setProtocol(Protocol protocol)
	{
		this.protocol = protocol;
	}

	/**
	 * Getter pour savoir si le thread est en route ou non
	 * 
	 * @return l'état du thread, vrai pour en marche, faux sinon
	 */
	public boolean isRunning()
	{
		return running;
	}

	/**
	 * Setter qui fixe si le thread est en exécution ou non
	 * 
	 * @param running
	 *            vrai pour le mettre en route faux sinon
	 */
	public void setRunning(boolean running)
	{
		this.running = running;
	}
}
