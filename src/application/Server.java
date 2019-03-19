package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Server side of chat app
 * 
 * @author William Huynh Justin Alan Marcus Douglas
 * @precondition: None
 *
 */
public class Server {
	static Vector<Object> Sockets;
	static Vector<Object> Names;

	/**
	 * Server side of chat app
	 * 
	 * @throws IOException
	 * @precondition: None
	 */
	public Server() throws IOException {
		ServerSocket server = new ServerSocket(4225);
		Sockets = new Vector<>();
		Names = new Vector<>();

		while (true) {
			Socket client = server.accept();
			AcceptClient acceptClient = new AcceptClient(client);
		}
	}
	
	/**
	 * Starting point for server side.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Server server = new Server();
	}

	class AcceptClient extends Thread {
		Socket ClientSocket;
		DataInputStream intput;
		DataOutputStream output;

		public AcceptClient(Socket client) throws IOException {
			try {
				ClientSocket = client;
				intput = new DataInputStream(ClientSocket.getInputStream());
				output = new DataOutputStream(ClientSocket.getOutputStream());

				String LoginName = intput.readUTF();
				if (Names.contains(LoginName)) {
					throw new IllegalArgumentException("Duplicate name");
				}
				Names.add(LoginName);
				Sockets.add(ClientSocket);

				start();
			} catch (Exception e) {

			}
		}
		
		/**
		 * Runs the thread for the clients
		 * @precondition: none
		 * @postcondition: none
		 */
		public void run() {
			while (true) {
				String msgFromClient;
				try {
					msgFromClient = intput.readUTF();

					StringTokenizer st = new StringTokenizer(msgFromClient);
					String LoginName = st.nextToken();
					String MsgType = st.nextToken();
					String msg = "";
					int lo = -1;

					while (st.hasMoreTokens()) {
						msg = msg + " " + st.nextToken();
					}
					if (MsgType.equals("LOGIN")) {
						for (int i = 0; i < Names.size(); ++i) {
							Socket pSocket = (Socket) Sockets.elementAt(i);
							DataOutputStream pOut;
							try {
								pOut = new DataOutputStream(pSocket.getOutputStream());
								DateFormat format = new SimpleDateFormat("HH:mm:ss");
								var date = new Date();
								pOut.writeUTF(format.format(date) + " " + LoginName + " has Logged in.");
							} catch (IOException e) {

							}
						}
					}

					else if (MsgType.equals("LOGOUT")) {
						for (int i = 0; i < Names.size(); ++i) {
							if (LoginName.equals(Names.elementAt(i)))
								lo = i;
							Socket pSocket = (Socket) Sockets.elementAt(i);
							DataOutputStream pOut;
							try {
								pOut = new DataOutputStream(pSocket.getOutputStream());
								DateFormat format = new SimpleDateFormat("HH:mm:ss");
								var date = new Date();

								pOut.writeUTF(format.format(date) + " " + LoginName + " has Logged out.");
							} catch (IOException e) {
								
							}
						}
						if (lo >= 0) {
							Names.removeElement(lo);
							Sockets.removeElement(lo);
						}
					} else {
						for (int i = 0; i < Names.size(); ++i) {
							Socket pSocket = (Socket) Sockets.elementAt(i);
							DataOutputStream pOut;
							try {
								pOut = new DataOutputStream(pSocket.getOutputStream());
								DateFormat format = new SimpleDateFormat("HH:mm:ss");
								var date = new Date();
								pOut.writeUTF(format.format(date) + " " + LoginName + ":" + msg);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					if (MsgType.equals("LOGOUT"))
						break;
				} catch (IOException e1) {
				
				}
			}
		}
	}

}
