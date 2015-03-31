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
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Youssef
 */
public class MultiClient implements Runnable {
    ServerSocket serversocket;
    Socket clientsocket;
    Vector tabClients = new Vector();
    
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
            this.welcome(clientsocket);
            } catch (IOException ex) {
            Logger.getLogger(MultiClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            Thread thread = new Thread(new CHandler(clientsocket,this));          //Test multi connexion
            thread.start();
        }
    }
    
    public void welcome(Socket client_socket)
    {
        PrintWriter writer;
        try {
        writer = new PrintWriter(new PrintWriter(client_socket.getOutputStream()));
        writer.println("Welcome ! you are connected to the Server !");
        writer.flush();
        } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);}
    }
    
    synchronized public void broadcast(String message)
    {
        PrintWriter out;
        for (int i = 0; i < tabClients.size(); i++)
        {
            out = (PrintWriter) tabClients.elementAt(i);
            if (out != null)
            {
            out.println(message);
            out.flush(); 
            }
        }
    }
    
    synchronized void addClient(PrintWriter out)
    { 
        tabClients.addElement(out);
    }
}
