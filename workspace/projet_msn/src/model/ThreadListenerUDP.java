package model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import network.Protocol;

/**
 * 		   Classe représentant le thread d'écoute UDP du serveur qui permet de recevoir les messages des
 *         clients et la liste des clients connectés.
 * @author Dorian, Mickaël, Raphaël, Thibault
 *
 */

public class ThreadListenerUDP extends Thread
{
	/**
	 * Client/Serveur a qui appartient le thread.
	 * 
	 * @see AbstractClientServer
	 */
	private AbstractClientServer clientServer;
	/**
	 * Paramètre permettant d'arrêter le Thread.
	 */
	private boolean running;
	/**
	 * Protocol de communication.
	 */
	private Protocol protocol;
	private static Logger logger = Logger.getLogger(ThreadListenerUDP.class.toString());
	/**
	 * Constructeur de la classe ThreadListenerUDP qui prend 2 paramètres.
	 * 
	 * @param clientServer
	 * 				Client ou Serveur 
	 * @param protocol
	 * 				Protocole utilisé.
	 */
	public ThreadListenerUDP(AbstractClientServer clientServer, Protocol protocol)
	{
		this.protocol = protocol;
		this.clientServer = clientServer;
		logger.setParent(AbstractClientServer.getLogger());
	}

	/**
	 * Ce thread récéptionne les messages et les affiche dans la console.
	 */
	public void run()
	{
		this.running = true;

		try
		{
			logger.info("Lancementu Thread d'ecoute UDP");
			while (this.running)
			{
				String message = protocol.readMessage();
				if (message != null && !message.equals(""))
				{
					this.clientServer.treatIncomeUDP(message);
				}
			}
			logger.info("Arret du Thread d'ecoute UDP");
			this.protocol.close();
		} catch (Exception e)
		{
			if(running)
			{
				logger.severe("Erreur du ThreadListenerUDP, message: " + e.getMessage());
			}else
			{
				logger.info("Arret du Thread d'ecoute TCP");
				this.protocol.close();
			}
			//System.err.println("Erreur du ThreadListenerUDP, message: " + e.getMessage());
			//e.printStackTrace();
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
			//On s'auto envoie un message pour stopper le thread
			protocol.sendMessage("", InetAddress.getByName("localhost"), this.protocol.getLocalPort());
		} catch (UnknownHostException e)
		{
			logger.severe("Erreur du ThreadListenerUDP, stopThread, message: " + e.getMessage());
			//System.err.println("Erreur du ThreadListenerUDP, stopThread, message: " + e.getMessage());
		}
	}
}
