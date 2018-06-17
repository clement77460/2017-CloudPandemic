package fr.efrei.paumier.shared.networking.messaging;

public class ClientMessage implements Message {
	private static final long serialVersionUID = 7598979284709703085L;

	private final String name;
	private final Message message;
	
	public ClientMessage(String name, Message message) {
		this.name = name;
		this.message = message;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Message getMessage() {
		return this.message;
	}
}
