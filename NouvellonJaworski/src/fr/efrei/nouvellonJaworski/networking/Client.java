package fr.efrei.nouvellonJaworski.networking;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fr.efrei.paumier.shared.domain.CityBorder;
import fr.efrei.paumier.shared.domain.MigrationMessage;
import fr.efrei.paumier.shared.networking.messaging.ClientMessage;
import fr.efrei.paumier.shared.networking.messaging.Message;
import fr.efrei.paumier.shared.orders.OrderMessage;
import fr.efrei.paumier.shared.orders.OrderType;
import fr.efrei.paumier.shared.simulation.HelloMessage;
import fr.efrei.paumier.shared.simulation.Simulation;
import fr.efrei.paumier.shared.simulation.Statistics;

public class Client implements CityBorder,Runnable{
	  private String nomGroupe="nouvellonJaworski";
	  private String hote;
	  private int port;
	  private Socket socket;
	  private ObjectOutputStream objectOutputStream;
	  private ObjectInputStream objectIntputStream;
	  
	  private Simulation simulation;
	
	public Client(String hote, int port) {
	    this.hote = hote;
	    this.port = port;
	  }
	
	public void setSimulation(Simulation simulation) {
		this.simulation=simulation;
	}
	
	@Override
	public void sendEmigrant(boolean isInfected) {
		MigrationMessage migrationMessage=new MigrationMessage(isInfected);
		try {
			//envoi d'un message de migration
			objectOutputStream.writeObject(migrationMessage);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private void getMessage() {
		
	    try {
	    	//recupération d'un message de migration
	    	Object obj=objectIntputStream.readObject();
	    	if(obj instanceof MigrationMessage) {
	    		System.out.println("-------------Recption d'un immigrant -------------------");
	    		simulation.startReceivingImmigrant(((MigrationMessage) obj).isInfected());	
	    	}
	    	//récupération d'un ordreMessage
	    	else if(obj instanceof OrderMessage){
	    		System.out.println("-------------Recption d'un ordre "+ ((OrderMessage)obj).getOrder()
	    				+"-------------------");
	    		this.simulation.executeOrder(((OrderMessage)obj).getOrder());
	    	}else {
	    		System.out.println("-------------Recption d'un message client de " +
	    				((ClientMessage)obj).getName()+"-------------------");
	    		Message clientMessage=((ClientMessage) obj).getMessage();
	    		this.simulation.executeOrder(((OrderMessage)clientMessage).getOrder());
	    	}
	    } catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} 
	    
	}
 
	private void connecter() {
		try {
			socket = new Socket(hote, port);
			objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
			objectIntputStream=new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			this.deconnecter();
			e.printStackTrace();
		}
	    
	  }
	  
	private void deconnecter() {
	    try {
	    	socket.close();
	    } catch (IOException e) {
	      System.err.println("Cloture de connexion impossible");
	    }
	  }

	@Override
	public void run() {
		this.connecter();
		this.sendHello();
		while(true) {
			this.getMessage();
		}
	}

	@Override
	public void sendStatistics(Statistics statistics) {
		
		try {
			objectOutputStream.writeObject(statistics);
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}
	
	public void sendHello() {
		System.out.println("--------------------ENVOI HELLO -----------------");
		System.out.println("--------------------ENVOI STATS (rythme 200 ms)-----------------");
		try {
			objectOutputStream.writeObject(new HelloMessage(nomGroupe));
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void sendClientMessage(String target,OrderType orderType) {
		System.out.println("--------------------ENVOI ORDRE VERS "+target+"-----------------");
		try {
			objectOutputStream.writeObject(
					new ClientMessage(target,new OrderMessage(orderType)));
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
