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

/**
 *
 * @author Thomas
 */
public class ClientChannel {
    int port;
    InetAddress address; 
    SocketChannel client;
    private Object newData;
    public ClientChannel(InetAddress address,int port)
    {
        this.port = port;
        this.address = address;
    }
    
    
    
    public void start()
    {
        try
        {
            SocketChannel client = SocketChannel.open();
            client.connect(new InetSocketAddress(this.address, this.port));
            client.configureBlocking(false);
            
            //while(client.isConnected())
            if(client.isConnected())
            {
               this.read(client);
                //System.out.println("in while(client.isConnected)");
            //    this.write(client);
            }
            //client.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void write(SocketChannel socket) throws IOException
    {
        Scanner reader = new Scanner(System.in);
        //PrintWriter writer
        ByteBuffer buf = ByteBuffer.allocate(1000);
        
        
        String message = "kikou!!!!!!";//reader.nextLine();
        if(message.contentEquals("exit"))
        {
            try {
            client.close();
            } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
        //writer = new PrintWriter(new PrintWriter(socket.getOutputStream()));
       // writer.println(message);
       //writer.flush();       
            buf.clear();
            buf.put(message.getBytes());
            buf.flip();
            while(buf.hasRemaining()) {
                socket.write(buf);
              //  System.out.println("in while(buf.hasRemaining)");
            }
            buf.compact();
        } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);}
    }
    
    public void read(SocketChannel clientsocket)
    {
        System.out.println("debut de read()");
        byte bytes[] = new byte[255];
        ByteBuffer buf = ByteBuffer.allocate(100);
        CharBuffer message=null;
        try {
        //reader=new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            System.out.println("avant .read()");
            clientsocket.read(buf);
            System.out.println("après .read()");
        //message=reader.readLine();
            buf.flip();
            message = Charset.defaultCharset().decode(buf);
            System.out.println("essaie sans ranger dans un string : "+Charset.defaultCharset().decode(buf));
            //buf.get(bytes); buf.
            //System.out.println("miaou");
            //String v = new String( bytes, Charset.forName("UTF-8") );
            //System.out.println("v : "+v);
            
            System.out.println("message : "+message);
            buf.compact();
        System.out.println("Received :"+message);
            
        } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);}
    }
}
