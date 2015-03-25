/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 *
 * @author Youssef
 */
public class CHandler implements Runnable{
    Socket client_socket;
    public CHandler(Socket client_socket)
    {
        this.client_socket = client_socket;
    }
    public void read(Socket clientsocket)
    {
        BufferedReader reader;
        String message;
        
        try {
        reader=new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
        message=reader.readLine();
        System.out.println("Message :"+message);
        } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);}
    }
    public void run()
    {                 
        System.out.println("Client connecté !");
        this.read(client_socket);
        try {
        client_socket.close();
        } catch (IOException ex) {
        Logger.getLogger(CHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
