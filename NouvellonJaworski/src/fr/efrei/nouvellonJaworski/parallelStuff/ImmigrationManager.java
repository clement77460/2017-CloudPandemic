package fr.efrei.nouvellonJaworski.parallelStuff;

import fr.efrei.nouvellonJaworski.networking.Client;

public class ImmigrationManager extends Thread{
	
	private Client client;
	
	public ImmigrationManager(Client client) {
		this.client=client;
	}
	
	@Override
	public void run() {
		//while(true) {
			///client.extract();
		//}
		
	}

}
