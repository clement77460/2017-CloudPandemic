package fr.efrei.paumier.shared.domain;

import fr.efrei.paumier.shared.networking.messaging.Message;

public class MigrationMessage implements Message {
	private static final long serialVersionUID = 4560083501581816566L;

	private final boolean infectedFlag;
	
	public MigrationMessage(boolean isInfected) {
		this.infectedFlag = isInfected;
	}
	
	public boolean isInfected() {
		return this.infectedFlag;
	}
}
