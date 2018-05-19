/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.nouvellonJaworski.networking;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import fr.efrei.paumier.shared.domain.MigrationMessage;
import fr.efrei.paumier.shared.networking.messaging.ClientMessage;
import fr.efrei.paumier.shared.orders.OrderMessage;
import fr.efrei.paumier.shared.orders.OrderType;

/**
 * Serveur temporaire qui a permis de tester les fonctionnalités du client
 * 
 * @author Clément
 */
public class ServerTemp {

    ServerSocket srvskt;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectIntputStream;
    Socket skt;

    public ServerTemp(int port) throws IOException {
        srvskt = new ServerSocket(port);
    }

    public void ecoute() {
        System.out.println("lancement du server");
        
        try {
            
            skt = srvskt.accept();
            System.out.println("connection accepted");
            objectOutputStream=new ObjectOutputStream(skt.getOutputStream());
            objectIntputStream=new ObjectInputStream(skt.getInputStream());
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Connexion impossible");
        }
    }
    public void sendMSG(){
        try {
            this.readOutputAndSendMSGAfterHelloOrStats();
            MigrationMessage mm=new MigrationMessage(true);
            objectOutputStream.writeObject(mm);
        } catch (IOException ex) {
            
        }
    }
    public void readOutputAndSendMSGAfterHelloOrStats(){
        try {
            Object obj=objectIntputStream.readObject();
            System.out.println(obj);
        }  catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
	} 
    }
    public void sendOrder(){
        try {
            this.readOutputAndSendMSGAfterHelloOrStats();
            OrderMessage orderMessage=new OrderMessage(OrderType.RESEARCH_IMPROVED_VACCINE);
            objectOutputStream.writeObject(orderMessage);
        } catch (IOException ex) {
            
        }
    }
    public void sendClientMessage(){
        try {
            ClientMessage clientMessage=new ClientMessage("nouvellonJaworski",
                    new OrderMessage(OrderType.RESEARCH_IMPROVED_VACCINE));
            objectOutputStream.writeObject(clientMessage);
        } catch (IOException ex) {
            
        }
    }
}
