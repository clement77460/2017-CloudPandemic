package fr.efrei.nouvellonJaworski.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fr.efrei.nouvellonJaworski.model.abstractmodel.TableModelPandemic;
import fr.efrei.paumier.shared.networking.messaging.ClientMessage;
import fr.efrei.paumier.shared.networking.messaging.Message;
import fr.efrei.paumier.shared.orders.OrderMessage;
import fr.efrei.paumier.shared.orders.OrderType;
import fr.efrei.paumier.shared.simulation.Statistics;

public class ClientTerminal implements Runnable{
	  private String hote;
	  private int port;
	  private Socket socket;
	  private ObjectOutputStream objectOutputStream;
	  private ObjectInputStream objectIntputStream;
	  
	  private TableModelPandemic tableModelPandemic;
	
	public ClientTerminal(String hote, int port) {
	    this.hote = hote;
	    this.port = port;
	  }
	
	public void setTableModelPandemic(TableModelPandemic tableModelPandemic) {
		this.tableModelPandemic=tableModelPandemic;
	}
	
	private void getMessage() {
		
	    try {
	    	Object obj=objectIntputStream.readObject();
	    	//recupération d'un clientMessage
	    	Message clientMessage=((ClientMessage) obj).getMessage();
	    	
	    	this.tableModelPandemic.refreshStats(((Statistics) clientMessage),
	    			((ClientMessage) obj).getName());
	    	
	    } catch (ClassNotFoundException | IOException e) {
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
		while(true) {
			this.getMessage();
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
