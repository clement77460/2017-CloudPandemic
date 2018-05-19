package fr.efrei.nouvellonJaworski.vue;

import fr.efrei.paumier.shared.orders.OrderType;

public class PartieNoeud extends AbstractNetworkGame{
	
	
	public PartieNoeud(int population) {
        super(population);
	}
	
	public void launchOrder(String destinataire,OrderType orderType) {
		this.simulation.executeOrder(orderType);
	}
}
