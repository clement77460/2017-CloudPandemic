package fr.efrei.paumier.shared.orders;

import fr.efrei.paumier.shared.networking.messaging.Message;

public class OrderMessage implements Message {
	private static final long serialVersionUID = -6249261560871853072L;
	
	private final OrderType order;
	
	public OrderMessage(OrderType order) {
		this.order = order;
	}
	
	public OrderType getOrder() {
		return this.order;
	}
}
