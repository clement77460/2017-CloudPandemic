package fr.efrei.nouvellonJaworski.controller.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import fr.efrei.nouvellonJaworski.vue.AbstractNetworkGame;
import fr.efrei.paumier.shared.orders.OrderType;

public class SubmitController implements ActionListener{
	
	private JTextField inputOrderType;
	private JComboBox<OrderType> orderTypeComboBox;
	private AbstractNetworkGame partie;
	
	public SubmitController(JTextField inputOrderType,JComboBox<OrderType> orderTypeComboBox,
			AbstractNetworkGame partie) {
		this.inputOrderType=inputOrderType;
		this.orderTypeComboBox=orderTypeComboBox;
		this.partie=partie;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		partie.launchOrder(inputOrderType.getText(), (OrderType)orderTypeComboBox.getSelectedItem());
	}

}
