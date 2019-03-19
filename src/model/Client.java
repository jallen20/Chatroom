package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Client side of application.
 *
 * @author William Huynh Justin Alan Marcus Douglas
 */
public class Client {

	Socket socket;

	DataInputStream din;

	DataOutputStream dout;

	String LoginName;

	/**
	 * Instantiates a new client.
	 *
	 * @param login  the login
	 * @param din    the din
	 * @param dout   the dout
	 * @param socket the socket
	 * @throws UnknownHostException the unknown host exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 *                             
	 * 
	 * @postcondition creates a client
	 * 
	 */
	public Client(String login, DataInputStream din, DataOutputStream dout, Socket socket)
			throws UnknownHostException, IOException {
		LoginName = login;
		this.socket = socket;
		this.din = din;
		this.dout = dout;

		this.dout.writeUTF(LoginName);
		this.dout.writeUTF(LoginName + " " + "LOGIN");

	}

	/**
	 * Write message.
	 *
	 * @param message the message
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void writeMessage(String message) throws IOException {
		this.dout.writeUTF(message);
	}

	/**
	 * 
	 * Leave chat.
	 *
	 * @param text the text
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void leaveChat(String text) throws IOException {
		DateFormat format = new SimpleDateFormat("HH-mm-ss");
		var date = new Date();
		this.dout.writeUTF(LoginName + " " + "LOGOUT");
		var file = new File(this.LoginName + "_" + format.format(date) + ".txt");
		var writer = new FileWriter(file);
		writer.write(text);
		writer.close();
	}

}
