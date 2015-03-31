/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Youssef
 */
public class MCClient {
    int port;
    String msg;
    InetAddress adgroup;
    MulticastSocket socket;
    DatagramPacket pkt;
    byte[] buffer;
    
    public MCClient(int port, InetAddress address)
    {
        buffer = new byte[50];
        adgroup = address;
        this.port = port;
        try {
        socket = new MulticastSocket(this.port);
        socket.joinGroup(adgroup);
        } catch (IOException ex) {
            Logger.getLogger(MCClient.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void start()
    {
        Thread sendthread = new Thread(new MCSend(socket,this.port,adgroup));
        sendthread.start();  
        while(true)
        {
            this.MCReceive();
        }
    }
    
    public void MCReceive()
    {
        pkt = new DatagramPacket(buffer,buffer.length);
        try {
        socket.receive(pkt);
        } catch (IOException ex) {
            Logger.getLogger(MCClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        msg = new String(buffer);
        System.out.println(msg);
    }
}
