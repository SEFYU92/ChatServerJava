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
            welcome(clientsocket);
            } catch (IOException ex) {
            Logger.getLogger(MultiClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            Thread thread = new Thread(new CHandler(clientsocket));          //Test multi connexion
            thread.start();
            /*try {
                clientsocket = serversocket.accept();
                System.out.println("Client connecté !");
                clientsocket.close();
            } catch (IOException ex) {
                Logger.getLogger(MultiClient.class.getName()).log(Level.SEVERE, null, ex);
            }*/
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
}
