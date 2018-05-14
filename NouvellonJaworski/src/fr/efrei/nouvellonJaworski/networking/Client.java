package fr.efrei.nouvellonJaworski.networking;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import fr.efrei.paumier.shared.domain.CityBorder;
import fr.efrei.paumier.shared.domain.MigrationMessage;
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
		System.out.println("envoi du client");
		MigrationMessage mm=new MigrationMessage(isInfected);
		try {
			//envoi d'un message de migration
			objectOutputStream.writeObject(mm);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	private void getMigrant() {
		System.out.println("extraction du client");
	    try {
	    	//recupération d'un message de migration
			MigrationMessage obj=(MigrationMessage) objectIntputStream.readObject();
			
			simulation.startReceivingImmigrant(obj.isInfected());	
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true) {
			this.getMigrant();
		}
		
	}

	@Override
	public void sendStatistics(Statistics statistics) {
		// TODO Auto-generated method stub
		
	}

	
	
}
