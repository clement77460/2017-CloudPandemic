package fr.efrei.nouvellonJaworski.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fr.efrei.paumier.shared.domain.CityBorder;
import fr.efrei.paumier.shared.domain.MigrationMessage;
import fr.efrei.paumier.shared.orders.OrderMessage;
import fr.efrei.paumier.shared.simulation.HelloMessage;
import fr.efrei.paumier.shared.simulation.Simulation;
import fr.efrei.paumier.shared.simulation.Statistics;

public class ClientNoeud implements CityBorder,Runnable{
	private String nomGroupe="nouvellonJaworski";
	  private String hote;
	  private int port;
	  private Socket socket;
	  private ObjectOutputStream objectOutputStream;
	  private ObjectInputStream objectIntputStream;
	  
	  private Simulation simulation;
	
	public ClientNoeud(String hote, int port) {
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
			System.out.println("-------------Envoi d'un �mmigrant -------------------");
			objectOutputStream.writeObject(migrationMessage);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private void getMessage() {
		
	    try {
	    	
	    	Object obj=objectIntputStream.readObject();
	    	//recup�ration d'un message de migration
	    	if(obj instanceof MigrationMessage) {
	    		System.out.println("-------------Recption d'un immigrant -------------------");
	    		simulation.startReceivingImmigrant(((MigrationMessage) obj).isInfected());	
	    	}
	    	//r�cup�ration d'un ordreMessage
	    	else {
	    		System.out.println("-------------Recption d'un ordre "+ ((OrderMessage)obj).getOrder()
	    				+"-------------------");
	    		this.simulation.executeOrder(((OrderMessage)obj).getOrder());
	    	}
	    } catch (ClassNotFoundException | IOException e) {
		} 
	    
	}

	private void connecter() {
		try {
			socket = new Socket(hote, port);
			objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
			objectIntputStream=new ObjectInputStream(socket.getInputStream());
			System.out.println("connexion succeed");
		} catch (IOException e) {
			this.deconnecter();
			e.printStackTrace();
		}
	    
	  }
	  
	public void deconnecter() {
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
}
