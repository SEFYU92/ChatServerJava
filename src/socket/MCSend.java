/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Youssef
 */
public class MCSend implements Runnable{
    String msg;
    Scanner reader;
    DatagramPacket pkt;
    MulticastSocket socket;
    InetAddress adgroup;
    int port;
    
    public MCSend(MulticastSocket socket,int port, InetAddress adgroup)
    {
        this.socket = socket;
        this.adgroup = adgroup;
        this.port = port;
    }
    
    public void run()
    {
        while(true)
        {
            reader = new Scanner(System.in);
            msg = reader.nextLine();
            pkt = new DatagramPacket(msg.getBytes(),msg.length(),adgroup,port);
            try {
            socket.send(pkt);
            } catch (IOException ex) {
            Logger.getLogger(MCSend.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
