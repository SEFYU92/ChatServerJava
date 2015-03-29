/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Youssef
 */
public class Client {
    int port;
    InetAddress address; 
    Socket client;
    PrintWriter writer;
    BufferedReader reader;
    public Client(InetAddress address,int port)
    {
        this.port = port;
        this.address = address;
        try {
        client = new Socket(this.address,this.port);
        writer = new PrintWriter(new PrintWriter(client.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException ex) {
        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void start()
    {
        Thread threadwrite = new Thread(new Write(client,writer));
        threadwrite.start();
        while(client.isConnected())
        {
            this.read();
        }     
    }
  
    public void read()
    {    
        String message=null;
        try {
        message=reader.readLine();
        System.out.println(message);
        } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);}
    }
}
