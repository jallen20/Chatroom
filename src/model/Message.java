package model;

import java.io.Serializable;

public class Message implements Serializable {
	private String sender;
	private String message;
	
	
	public Message(String sender, String message) {
		if (sender == null) throw new IllegalArgumentException("sender was null");
		if (message == null) throw new IllegalArgumentException("message was null");
		
		this.sender = sender;
		this.message = message;
	}
	
	public String getSender() {
		return this.sender;
	}
	
	public String getMessage() {
		return this.message;
	}
}
