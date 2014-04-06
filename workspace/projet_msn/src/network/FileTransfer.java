package network;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import model.Client;

/**
 * Classe représentant le transfert de fichier
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 */
public class FileTransfer
{
	/**
	 * Methode permettant d'envoyer un fichier
	 * 
	 * @param os
	 * @param file
	 * @throws Exception
	 */
	public static void send(OutputStream os, String file, String complement) throws Exception
	{
		File myFile = new File(file);
		String[] pathFile = file.split("/");
		String nameFile = pathFile[pathFile.length - 1];
		byte[] mybytearray = new byte[(int) myFile.length()];

		FileInputStream fis = new FileInputStream(myFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		// bis.read(mybytearray, 0, mybytearray.length);

		DataInputStream dis = new DataInputStream(bis);
		dis.readFully(mybytearray, 0, mybytearray.length);

		// Sending file name and file size to the server
		DataOutputStream dos = new DataOutputStream(os);
		dos.writeUTF(complement + "-" + nameFile);
		dos.writeLong(mybytearray.length);
		dos.write(mybytearray, 0, mybytearray.length);
		dos.flush();

		// Closing socket
		dis.close();
		os.close();
	}

	/**
	 * Methode permettant de recevoir un fichier
	 * 
	 * @param is
	 * @param folder
	 * @throws Exception
	 */
	public static void receiveFile(InputStream is, Client client) throws Exception
	{
		int bytesRead;
		DataInputStream clientData = new DataInputStream(is);

		String utfRead = clientData.readUTF();
		String[] element = utfRead.split("-");
		String idDialog = element[0];
		String fichier = element[element.length - 1];

		File file = new File(fichier);
		int i = 0;
		while (file.exists())
		{
			i++;
			String FichierBis = "(" + i + ")" + fichier;
			file = new File(FichierBis);
		}

		OutputStream output = new FileOutputStream(file.getName());
		long size = clientData.readLong();
		byte[] buffer = new byte[1024];
		while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1)
		{
			output.write(buffer, 0, bytesRead);
			size -= bytesRead;
		}
		// On indique dans la fenetre de dialog que l'on a reçu un message
		client.receiveFileToDialog(fichier, idDialog);

		output.close();
	}
}
