package jUnitTest;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import model.Client;
import model.ClientDialog;
import model.ClientServerData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class ClientDialogTest 
{
	private Client clientTest;
	private InetAddress inetTest;
	private ClientServerData clientDataTest;
	private ClientDialog clientDialogueTest;

	@Before 
	public void initTest()
	{
		try 
		{
			inetTest = InetAddress.getLocalHost();
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}

		clientTest = new Client("TestUnitaire_C2",3001,3000,"localhost");
		clientDialogueTest = new ClientDialog(clientTest,clientTest.getProtocol());
		clientDataTest = new ClientServerData("JUNIT_C1", inetTest, 3003,3000);
	}
	
	@After
	public void endTest()
	{
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{

		}
		clientTest.getThreadComunicationClient().stopThread();
		clientTest.getThreadListenerUDP().stopThread();
		clientTest.getThreadListenerTCP().stopThread();
		clientTest.getProtocol().close();
		clientTest=null;
		clientDialogueTest=null;
		clientDataTest=null;
	}


	@Test
	public void testAddMessage()
	{
		//ajout du premier message
		clientDialogueTest.addMessage("test junit");
		//test si le dernier message est egale a celui qui est inserré
		assertEquals("test junit", clientDialogueTest.getLastMessage());
		//test si le dialogue message est egale au message la premiere fois
		assertEquals("\ntest junit", clientDialogueTest.getDialogue());
		//ajout du second message
		clientDialogueTest.addMessage("test junit2");
		//test si le dernier message est egale a celui qui est inserré
		assertEquals("test junit2", clientDialogueTest.getLastMessage());
		//test si le dialogue est égale a l'ensemble des messages ajoutés
		assertEquals("\ntest junit\ntest junit2", clientDialogueTest.getDialogue());
	}
	
	@Test
	public void testSendMessage() 
	{
		//ajout du premier message
		clientDialogueTest.sendMessage("test junit");
		//test si le dernier message est egale a celui qui est inserré
		assertEquals("moi>test junit", clientDialogueTest.getLastMessage());
		//test si le dialogue message est egale au message la premiere fois
		assertEquals("\nmoi>test junit", clientDialogueTest.getDialogue());
		//ajout du second message
		clientDialogueTest.sendMessage("test junit2");
		//test si le dernier message est egale a celui qui est inserré
		assertEquals("moi>test junit2", clientDialogueTest.getLastMessage());
		//test si le dialogue est egale a l'ensemble des messages ajoutés
		assertEquals("\nmoi>test junit\nmoi>test junit2", clientDialogueTest.getDialogue());
	}

	@Test
	public void testReceiveMessage() 
	{
		//ajout du premier message
		clientDialogueTest.receiveMessage("test junit");
		//test si le dernier message est egale a celui qui est inserré
		assertEquals("test junit", clientDialogueTest.getLastMessage());
		//test si le dialogue message est egale au message la premiere fois
		assertEquals("\ntest junit", clientDialogueTest.getDialogue());
		//ajout du second message
		clientDialogueTest.receiveMessage("test junit2");
		//test si le dernier message est egale a celui qui est inserré
		assertEquals("test junit2", clientDialogueTest.getLastMessage());
		//test si le dialogue est égale a l'ensemble des messages ajoutés
		assertEquals("\ntest junit\ntest junit2", clientDialogueTest.getDialogue());
	}

	@Test
	public void testAddClient()
	{
		//test si la liste est vide
		assertEquals(clientDialogueTest.getClients().size(),0);
		//test si la liste n'est pas null
		assertNotNull(clientDialogueTest.getClients());
		//ajout d'un client
		clientDialogueTest.addClient(clientDataTest);
		//test si la liste posséde un element
		assertEquals(clientDialogueTest.getClients().size(),1);
	}

	@Test
	public void testRemoveClient()
	{
		//test si la liste est vide
		assertEquals(clientDialogueTest.getClients().size(),0);
		//test si la liste n'est pas null
		assertNotNull(clientDialogueTest.getClients());
		//ajout d'un client
		clientDialogueTest.addClient(clientDataTest);
		//test si la liste de un element
		assertEquals(clientDialogueTest.getClients().size(),1);
		//on supprime un élément existant
		clientDialogueTest.removeClient(clientDataTest);
		//test si la liste est vide
		assertEquals(clientDialogueTest.getClients().size(),0);
		//on ressuprime un élément pour vérifier si ca ne plante pas
		clientDialogueTest.removeClient(clientDataTest);
		//test si la liste est vide
		assertEquals(clientDialogueTest.getClients().size(),0);
	}

}
