package fr.efrei.nouvellonJaworski.vue;

import fr.efrei.paumier.shared.orders.OrderType;

public class PartieNoeud extends AbstractNetworkGame{
	
	
	public PartieNoeud(int population) {
        super(population,"178.62.119.191",4242);
	}
	
	public void launchOrder(String destinataire,OrderType orderType) {
		this.simulation.executeOrder(orderType);
	}
}
