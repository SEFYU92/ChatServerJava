/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;
import java.io.IOException;
import java.net.*;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Youssef
 */
public class SocketChat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        InetAddress address = null;
        try {
        //address = InetAddress.getByName("www.google.com");
        address = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Socket.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(args[0].equals("s"))
        {
            Server server = new Server(address,Integer.parseInt(args[1]));
            server.start();
        }else if(args[0].equals("c"))
        {
            Client client = new Client(address,Integer.parseInt(args[1]));
            client.start();
        }
    }
}
