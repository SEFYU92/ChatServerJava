/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.*;

/**
 *
 * @author Youssef
 */
public class CHandler implements Runnable{
    Socket client_socket;
    PrintWriter writer;
    MultiClient multi;
    BufferedReader reader;
    static int numclient = 0;
    
    public CHandler(Socket client_socket, MultiClient multiclient)
    {
        numclient ++;
        multi=multiclient;
        this.client_socket = client_socket;
        try {
        writer = new PrintWriter(new PrintWriter(this.client_socket.getOutputStream()));
        reader=new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
        multiclient.addClient(writer);
        } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);}
    }
    
    public void read()
    {
        String message=null;
        try {
        message=reader.readLine();
        System.out.println("Message :"+message);
        this.multi.broadcast("client"+Integer.toString(numclient)+":"+message);
        } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);}
        if(message.contentEquals("exit"))
        {
            try {
            client_socket.close();
            } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void run()
    {                 
        System.out.println("Client connecté !");
        while(client_socket.isConnected())
        {
            this.read();          
        }
    }
}
