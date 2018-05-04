package fr.efrei.paumier.shared.simulation;

import fr.efrei.paumier.shared.networking.messaging.Message;

public class HelloMessage implements Message {
	private static final long serialVersionUID = 4343009205967193737L;
	
	private final String simulationName;
	
	public HelloMessage(String simulationName) {
		this.simulationName = simulationName;
	}
	
	public String getName() {
		return this.simulationName;
	}
}
