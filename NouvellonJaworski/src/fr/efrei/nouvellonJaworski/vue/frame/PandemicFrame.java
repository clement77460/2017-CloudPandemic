package fr.efrei.nouvellonJaworski.vue.frame;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import fr.efrei.nouvellonJaworski.controller.frame.SubmitController;
import fr.efrei.nouvellonJaworski.model.abstractmodel.TableModelPandemic;
import fr.efrei.nouvellonJaworski.partie.PartieTerminal;
import fr.efrei.paumier.shared.orders.OrderType;

public class PandemicFrame extends JFrame{
	private static final long serialVersionUID = 7030202242408996410L;
	private JTable table;
	private PartieTerminal partie;
	public PandemicFrame(TableModelPandemic tableModel,PartieTerminal partie) {
		super();
		this.table=new JTable(tableModel);
		this.partie=partie;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.initFrame();
		
	}
	
	private void initFrame() {
		this.setSize(1200,700);
		this.setTitle("Pandemic Game");
		this.setLocation(20,20);
		this.getContentPane().add(this.createTablePane(),BorderLayout.CENTER);
		this.getContentPane().add(createComboBoxPane(),BorderLayout.SOUTH);
		
	}
	
	private Component createTablePane() {
		
		this.designTableHeader();
		this.designTableContent();
		
		return new JScrollPane(table);
	}
	
	private void designTableHeader() {
		table.getTableHeader().setBackground(new Color(0,0,0));
		table.getTableHeader().setForeground(new Color(255,0,0));
		table.getTableHeader().setFont(new Font("Arial",Font.BOLD,20));
	}
	
	private void designTableContent(){
		table.setBackground(new Color(0,0,0));
		table.setFont(new Font("Arial",Font.BOLD,20));
		table.setForeground(new Color(255,0,0));
		table.setRowHeight(this.getHeight()/7);
		table.setGridColor(new Color(255,0,0));
	}
	
	private Component createComboBoxPane() {
		JPanel panel = new JPanel(new FlowLayout());
		JTextField inputOrderType=new JTextField();
		JButton submitButton = new JButton("Valider");
		JComboBox<OrderType> orderTypeComboBox = new JComboBox<OrderType>(OrderType.values());
		JLabel labelTo=new JLabel("To");
		inputOrderType.setPreferredSize(new Dimension(150,25));
		
		submitButton.addActionListener(new SubmitController
				(inputOrderType,orderTypeComboBox,partie));
		
		panel.add(orderTypeComboBox);
		panel.add(labelTo);
		panel.add(inputOrderType);
		panel.add(submitButton);
		
		return panel;
	}
	
	public void displayEndGameScreen(String message) {
		this.getContentPane().removeAll();
		JPanel panel = new JPanel(new FlowLayout());
		JLabel labelMessage=new JLabel(message);
		
		labelMessage.setFont(new Font("Arial",Font.BOLD,17));
		labelMessage.setForeground(new Color(255,0,0));
		panel.add(labelMessage);
		this.getContentPane().add(panel,BorderLayout.CENTER);
		
		this.revalidate();
		this.repaint();
	}
}
