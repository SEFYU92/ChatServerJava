/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Youssef
 */
public class Write implements Runnable{
    Socket clientsocket;
    Scanner reader;
    String message;
    PrintWriter writer;
    
    public Write(Socket clientsocket, PrintWriter writer)
    {
        this.clientsocket=clientsocket;       
        this.writer = writer;
    }
    
    public void run()
    {
        while(true){
        reader = new Scanner(System.in);
        message = reader.nextLine();
        if(message.contentEquals("exit"))
        {
            try {
            clientsocket.close();
            } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        writer.println(message);
        writer.flush();}
    }
}
