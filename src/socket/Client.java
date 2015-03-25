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
    public Client(InetAddress address,int port)
    {
        this.port = port;
        this.address = address;
    }
    
    public void start()
    {
        try
        {
            client = new Socket(this.address,this.port);
            while(client.isConnected())
            {    
                this.write(client);
            }
            //client.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void write(Socket socket)
    {
        Scanner reader = new Scanner(System.in);
        PrintWriter writer;
        String message = reader.nextLine();
        if(message.contentEquals("exit"))
        {
            try {
            client.close();
            } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
        writer = new PrintWriter(new PrintWriter(socket.getOutputStream()));
        writer.println(message);
        writer.flush();
        } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);}}
}