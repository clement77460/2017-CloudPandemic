package fr.efrei.nouvellonJaworski.controller.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import fr.efrei.nouvellonJaworski.partie.PartieTerminal;
import fr.efrei.paumier.shared.orders.OrderType;

public class SubmitController implements ActionListener{
	
	private JTextField inputOrderType;
	private JComboBox<OrderType> orderTypeComboBox;
	private PartieTerminal game;
	
	public SubmitController(JTextField inputOrderType,JComboBox<OrderType> orderTypeComboBox,
			PartieTerminal game) {
		this.inputOrderType=inputOrderType;
		this.orderTypeComboBox=orderTypeComboBox;
		this.game=game;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		game.launchOrder(inputOrderType.getText(), (OrderType)orderTypeComboBox.getSelectedItem());
	}

}
