/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Youssef
 */
public class MultiClient implements Runnable {
    ServerSocket serversocket;
    Socket clientsocket;
    public MultiClient(ServerSocket serversocket)
    {
        this.serversocket=serversocket;
    }
    public void run()
    {
        while(true)
        {
            try {
                clientsocket = serversocket.accept();
                System.out.println("Client connecté !");
                clientsocket.close();
            } catch (IOException ex) {
                Logger.getLogger(MultiClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
