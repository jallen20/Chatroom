package view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Client;


/**
 * The Class Login.
 * 
 * @precondition: None
 */

public class Login {

	@FXML
	private TextField txtPassword;

	@FXML
	private TextField txtUsername;

	@FXML
	private Button btnLogn;

	@FXML
	private TextArea areaMessages;

	@FXML
	private TextField txtMessage;

	@FXML
	private Button btnSend;

	@FXML
	private Button btnLogout;

	private DataInputStream inputStream;

	private DataOutputStream outputStream;

	private Socket socket;

	private Client client;

	private String loginName;

	private boolean loggedIn;


	/**
	 * Initialize.
	 * 
	 * @throws UnknownHostException the unknown host exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */

	@FXML
	void initialize() throws UnknownHostException, IOException {
		this.loggedIn = false;
		this.btnSend.setDisable(true);

	}


	/**
	 * Login screen
	 * 
	 * @param event the event
	 * @throws UnknownHostException the unknown host exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */

	@FXML
	void login(ActionEvent event) throws UnknownHostException, IOException {
		this.loginName = this.txtUsername.getText();

		this.socket = new Socket("160.10.217.125", 4225);
		this.inputStream = new DataInputStream(this.socket.getInputStream());
		this.outputStream = new DataOutputStream(this.socket.getOutputStream());
		Runnable messageListener = () -> {
			while (true) {
				try {
					this.areaMessages.appendText(this.inputStream.readUTF() + "\n");
				}
					catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		var thread = new Thread(messageListener);
		thread.start();

		this.client = new Client(this.txtUsername.getText(), this.inputStream, this.outputStream, this.socket);
		if (this.btnSend.isDisabled()) {
			this.btnSend.setDisable(false);
			this.btnLogn.setDisable(true);
		}
	}

	/**
	 * Send message button
	 * 
	 * @param event the event
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */

	@FXML
	void send(ActionEvent event) throws IOException, InterruptedException {
		String text = this.txtMessage.getText();
		this.client.writeMessage(this.loginName + "  : " + text);
		this.txtMessage.clear();
	}


	@FXML
	void logout(ActionEvent event) throws IOException {
		var text = this.areaMessages.getText();
		this.client.leaveChat( text);
		System.exit(1);
	}

}
