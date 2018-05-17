package fr.efrei.nouvellonJaworski.networking;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import fr.efrei.paumier.shared.domain.CityBorder;
import fr.efrei.paumier.shared.domain.MigrationMessage;
import fr.efrei.paumier.shared.orders.OrderMessage;
import fr.efrei.paumier.shared.orders.OrderType;
import fr.efrei.paumier.shared.simulation.HelloMessage;
import fr.efrei.paumier.shared.simulation.Simulation;
import fr.efrei.paumier.shared.simulation.Statistics;

public class Client implements CityBorder,Runnable{
	
	  private String hote;
	  private int port;
	  private Socket skt;
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
		System.out.println("extraction du client");
	    try {
	    	//recupération d'un message de migration
	    	Object obj=objectIntputStream.readObject();
	    	if(obj instanceof MigrationMessage) {
	    		simulation.startReceivingImmigrant(((MigrationMessage) obj).isInfected());	
	    	}
	    	//récupération d'un ordreMessage
	    	else {
	    		this.simulation.executeOrder(((OrderMessage)obj).getOrder());
	    	}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} 
	    System.out.println("find e l'extraction");
	}
        
        
	
	private void connecter() throws IOException {
	    skt = new Socket(hote, port);
	    objectOutputStream=new ObjectOutputStream(skt.getOutputStream());
	    objectIntputStream=new ObjectInputStream(skt.getInputStream());
	    
	  }
	  
	private void deconnecter() {
	    try {
	      skt.close();
	    } catch (IOException e) {
	      System.err.println("Cloture de connexion impossible");
	    }
	  }

	@Override
	public void run() {
		
		try {
			this.connecter();
			this.sendHello();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			this.getMessage();
		}
	}

	@Override
	public void sendStatistics(Statistics statistics) {
		System.out.println("--------------------ENVOI STATS-----------------");
		try {
			objectOutputStream.writeObject(statistics);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void sendHello() {
		System.out.println("--------------------ENVOI HELLO-----------------");
		try {
			objectOutputStream.writeObject(new HelloMessage("nouvellonJaworski"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
}
