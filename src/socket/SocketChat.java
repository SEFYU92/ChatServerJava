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
import static javafx.application.Application.launch;

/**
 *
 * @author Youssef
 */
public class SocketChat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        InetAddress address = null;
        InetAddress adgroup = InetAddress.getByName("228.5.6.7");
        try {
        address = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
        Logger.getLogger(Socket.class.getName()).log(Level.SEVERE, null, ex);}
        GUI client1 = new GUI();
        
        if(args.length == 0)
        {
            System.out.println("option missing : <mode:s/c/mcc> <port>");
        }
        else if(args[0].equals("s"))
        {
            Server server = new Server(address,Integer.parseInt(args[1]));
            server.start();
        }else if(args[0].equals("c"))
        {
            Client client = new Client(address,Integer.parseInt(args[1]));
            client1.run(client);
        }else if(args[0].equals("mcc"))
        {
            MCClient client = new MCClient(Integer.parseInt(args[1]),adgroup);
            client.start();
        }
    }
}
