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

/**
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
            
            MigrationMessage mm=new MigrationMessage(true);
            objectOutputStream.writeObject(mm);
        } catch (IOException ex) {
            
        }
    }
}
