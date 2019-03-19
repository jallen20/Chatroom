package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client {
	private Socket socket;
	private DataInputStream din;
	private DataOutputStream dout;
	private String LoginName;

	/**
	 * Creates a client
	 * @param login client login
	 * @param din data in
	 * @param dout data out
	 * @param socket 
	 * @throws UnknownHostException
	 * @throws IOException
	 * 
	 * @postcondition creates a client
	 */
	public Client(String login, DataInputStream din, DataOutputStream dout, Socket socket)
			throws UnknownHostException, IOException {
		LoginName = login;
		this.socket = socket;
		this.din = din;
		this.dout = dout;
//		
		this.dout.writeUTF(LoginName);
		this.dout.writeUTF(LoginName + " " + "LOGIN");

	}

	public void writeMessage(String message) throws IOException {
		this.dout.writeUTF(message);
	}

	/**
	 * Save a text file of the clients chat window
	 * @param text input text
	 * @throws IOException
	 * @postcondition saves text to a file
	 */
	public void leaveChat(String text) throws IOException {
		DateFormat format = new SimpleDateFormat("HH-mm-ss");
		var date = new Date();
		this.dout.writeUTF(LoginName + " " + "LOGOUT");
		System.out.println("CLIENT " + text);
		String other = this.LoginName + "_" + format.format(date) + ".txt";
		var file = new File(other);
		var writer = new FileWriter(file);
		writer.write(text);
		writer.close();
	}

}
