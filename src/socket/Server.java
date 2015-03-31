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
public class Server {
    int port;
    InetAddress adress;
    
    public Server(InetAddress adress,int port)
    {
        this.port = port;
        this.adress = adress;
    }
    
    public void start()
    {
        ServerSocket serversocket;
        try
        {
            serversocket = new ServerSocket(this.port, 50, this.adress);
            
            Thread thread = new Thread(new MultiClient(serversocket));          //Test multi connexion
            thread.start();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
