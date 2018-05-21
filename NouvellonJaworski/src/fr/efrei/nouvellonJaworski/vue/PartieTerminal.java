package fr.efrei.nouvellonJaworski.vue;

import fr.efrei.paumier.shared.orders.OrderType;

public class PartieTerminal extends AbstractNetworkGame{

	
	public PartieTerminal(int population) {
		super(population,"178.62.119.191",8081);
	}
	
	
	public void launchOrder(String destinataire, OrderType orderType) {
		//la ville destinatrice qui paye?
		cityBorder.sendClientMessage(destinataire,orderType);
	}

}
