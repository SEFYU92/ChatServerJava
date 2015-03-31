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
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import socket.Client;
import socket.Server;

/**
 *
 * @author Thomas
 */
public class ClientChannel {
   
    int port;
    InetAddress address; 
    
    public ClientChannel(InetAddress address,int port)
    {
        this.port = port;
        this.address = address;
    }
    
    public class WriteClientChannel implements Runnable {
    
     SocketChannel myClient;
     
     public WriteClientChannel(SocketChannel client) {
       this.myClient = client;
     }
        
    @Override
    public void run()
    {
        
     while(true) {
        Scanner reader = new Scanner(System.in);
        //PrintWriter writer
        ByteBuffer buf = ByteBuffer.allocate(1000);
        
        
        String message = reader.nextLine();
        if(message.contentEquals("exit"))
        {
            try {
            this.myClient.close();
            } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try { 
            buf.clear();
            buf.put(message.getBytes());
            buf.flip();
            while(buf.hasRemaining()) {
                myClient.write(buf);
            }
            buf.compact();
        } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);}
    }
    }//END OF while(true)
    }
    
    public void start()
    {
        try
        {
            SocketChannel client = SocketChannel.open();
            client.connect(new InetSocketAddress(this.address, this.port));
            client.configureBlocking(false);
           
           Thread thread = new Thread(new WriteClientChannel (client));          //Test multi connexion
            thread.start();
            
            while(client.isConnected())
            {
              this.read(client);
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void read(SocketChannel clientsocket) throws IOException
    {
        String stringMessage = null;
        CharBuffer mes=null;
        Charset charset = Charset.defaultCharset();
      
        try {
            ByteBuffer message = ByteBuffer.allocate(1000);
         
               int read = (int)clientsocket.read(message);
           
           byte[] bytes = new byte[read];
            message.flip();
            message.get(bytes);
            stringMessage = new String( bytes, Charset.forName("UTF-8") );
            message.compact();
            
            if(stringMessage != null) if(!stringMessage.contentEquals("")) System.out.println(stringMessage);
            
        } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);}
    }
}
