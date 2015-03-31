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
        

        int i = 0;
        int port = 2004;
        boolean serv = false;
        boolean multicast = false;
        boolean nio = false;
        boolean error = false;
        boolean help = false;

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

       
        for(i = 0; i < args.length; i++) if(args[i].equals("-a") || args[i].equals("--address")) address = InetAddress.getByName(args[i+1]);
        for(i = 0; i < args.length; i++) if(args[i].equals("-h") || args[i].equals("--help")) help = true;
        for(i = 0; i < args.length; i++) if(args[i].equals("-m") || args[i].equals("--multicast")) multicast = true;
        for(i = 0; i < args.length; i++) if(args[i].equals("-n") || args[i].equals("--nio")) nio = true;
        for(i = 0; i < args.length; i++) if(args[i].equals("-p") || args[i].equals("--port")) port = Integer.parseInt(args[i+1]);
        for(i = 0; i < args.length; i++) if(args[i].equals("-s") || args[i].equals("--server")) serv = true;
      
        if(port < 1 || address == null) System.out.println("Error : Command invalid.");
        
        if(args.length == 0)

        {
            System.out.println("option missing : <mode:s/c/mcc> <port>");
        }
        else if(help == true){
            System.out.println("-a , --address = ADDR set the IP address\n" +
                               "-d , --debug display error messages\n" +
                               "-h , --help display this help and quit\n" +
                               "-m , --multicast start the client en multicast mode\n" +
                               "-n , --nio configure the server/client in NIO mode\n" +
                                "-p , --port = PORT set the port\n" +
                                "-s , --server start the server");
            
            //server
        }else if(serv == true)
        {

            Client client = new Client(address,Integer.parseInt(args[1]));
            client1.run(client);
        }else if(args[0].equals("mcc"))
        {
            MCClient client = new MCClient(Integer.parseInt(args[1]),adgroup);

            if(nio == true){ //server NIO
              MultichatServer server = new AbstractMultichatServer(address,port);
           server.start();
            }else { //server normal
            Server server = new Server(address,port);
            server.start();
            }  
        }else{ //client
            
            if(nio ==true){
            ClientChannel client = new ClientChannel(address,port);
           client.start();
            }else if(multicast == true){ //client mulicast 
            MCClient client = new MCClient(port,adgroup);

            client.start();
            }
            else { //client normal 
                Client client = new Client(address,port);
            client1.run(client); 
            }
        } 
               
    }
}
