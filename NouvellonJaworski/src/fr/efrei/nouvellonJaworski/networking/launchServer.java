/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.nouvellonJaworski.networking;

/**
 *
 * @author Clément
 */
import java.io.IOException;


public class launchServer {

    public static void main(String[] args) {
        try {
            ServerTemp srv = null;
            try {
                srv = new ServerTemp(11111);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            srv.ecoute();
            srv.sendMSG();
            Thread.sleep(10000);
            srv.sendMSG();
        } catch (InterruptedException ex) {
            
        }
        System.out.println("back");
        while(true);
    }
    
}
