package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/** 
 *         Classe représentant le thread d'écoute TCP du serveur qui permet de recevoir les connexions des
 *  	   clients. Lorsque un client se connecte, un nouveau thread est lancé pour
 *         communiquer.
 *         @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class ThreadListenerTCP extends Thread
{
	/**
	 * Client/Serveur à qui appartient le thread.
	 * 
	 * @see AbstractClientServer
	 */
	private AbstractClientServer clientServer;
	/**
	 * Socket de réception.
	 */
	private ServerSocket socket;
	/**
	 * Paramètre permettant d'arrêter le Thread.
	 */
	private boolean running;
	private static Logger logger = Logger.getLogger(ThreadListenerTCP.class.toString());
	/**
	 * Constructeur du ThreadListener TCP qui prend deux paramètres
	 * 
	 * @param server
	 *            serveur lancant le thread
	 * @param port
	 *            numero de port d'écoute
	 */
	public ThreadListenerTCP(AbstractClientServer server, int port)
	{
		try
		{
			this.clientServer = server;
			this.socket = new ServerSocket(port);
			logger.setParent(AbstractClientServer.getLogger());
			this.running = false;
		} catch (IOException e)
		{
			logger.severe("Erreur initialisation ThreadListenerTCP, message: " + e.getMessage());
			//System.err.println("Erreur initialisation serveur, message: " + e.getMessage());
		}
	}

	public void run()
	{
		try
		{
			logger.info("Lancementu Thread d'ecoute TCP");
			//System.out.println("Lancement du Thread d'ecoute TCP");
			this.running = true;
			while (running)
			{
				// On attend une connection client
				Socket socketClient = this.socket.accept();
				// Si on a une connection avec un client
				// On lance un thread de discutions avec le client
				this.clientServer.treatIncomeTCP(socketClient);
			}
			logger.info("Arret du Thread d'ecoute TCP");
			//System.out.println("Arret du Thread d'ecoute");
			this.socket.close();
		} catch (Exception e)
		{
			if (running)
			{
				//System.err.println("Erreur du ThreadListenerTCP, message: " + e.getMessage());
				logger.severe("Erreur du ThreadListenerTCP, message: " + e.getMessage());
			}else
			{
				logger.info("Arret du Thread d'ecoute TCP");
			}
		}
	}

	/**
	 * Méthode permettant d'arrêter le thread proprement
	 */
	public void stopThread()
	{
		this.running = false;
		try
		{
			this.socket.close();
		} catch (IOException e)
		{
			logger.severe("Erreur ThreadListenerTCP, erreur de fermerture du socket, message : " + e.getMessage());
			//System.err.println("Erreur ThreadListenerTCP, erreur de fermerture du socket, message : " + e.getMessage());
		}
	}

	/**
	 * Getter du socket du thread
	 * 
	 * @return le socket utilisé
	 */
	public ServerSocket getSocket()
	{
		return socket;
	}

	/**
	 * Setter qui fixe le socket du thread
	 * 
	 * @param socket
	 *            le socket que l'on souhaite utilisé pour le thread
	 */
	public void setSocket(ServerSocket socket)
	{
		this.socket = socket;
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
