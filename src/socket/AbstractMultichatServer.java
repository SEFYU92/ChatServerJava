/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static sun.security.krb5.Confounder.bytes;

/**
 *
 * @author Thomas
 */
public class AbstractMultichatServer implements MultichatServer {
     int port;
    InetAddress adress; 
    private Object charset;
    public AbstractMultichatServer(InetAddress adress,int port)
    {
        this.port = port;
        this.adress = adress;
    }
    
    public void start()
    {
        ServerSocketChannel server;
        try
        {
            server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(this.adress, this.port));
            //serversocket = new ServerSocket(this.port, 50, this.adress);
            //Thread thread = new Thread(new MultiClient(serversocket));          //Test multi connexion
            //thread.start();
           // SocketChannel client = server.accept();
   // server.socket().bind(new InetSocketAddress(9000));
    Selector selector = Selector.open();
     server.configureBlocking(false);
    server.register(selector, SelectionKey.OP_ACCEPT);
    
    ByteBuffer bbyte = ByteBuffer.allocate(100);
    byte bytes[] = null;
      byte[] data = new byte[255];
    
    while (true) {
      selector.select();
      Set<SelectionKey> readyKeys = selector.selectedKeys();
      Iterator<SelectionKey> iterator = readyKeys.iterator();
      
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        iterator.remove();
        if (key.isAcceptable()) {
            System.out.println("is_Acceptable : s");
          SocketChannel client = server.accept();
          System.out.println("Accepted connection from " + client);
          client.configureBlocking(false);
          ByteBuffer source = ByteBuffer.wrap(data);
          SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE);
          key2.attach(source);
          System.out.println("is_Acceptable : e");
        } else if (key.isReadable()){
            
            System.out.println("is readable : s");
            ((SocketChannel) key.channel()).read(bbyte);
            bbyte.flip();
            bbyte.get(bytes);
            String v = new String( bytes, Charset.forName("UTF-8") );
            System.out.println(v);
            
            bbyte.compact();
            System.out.println("is readable : e");
        }
        else if (key.isWritable()) {
            
            System.out.println("is_writable : s");
          SocketChannel client = (SocketChannel) key.channel();
          ByteBuffer output = (ByteBuffer) key.attachment();
          output.clear();
          output.put("TEST 1".getBytes());
          output.flip();
          
          if (output.hasRemaining()) {
            client.write(output);
          }
          
          System.out.println("is_writable : e");
        } 
        //key.channel().close();
        //iterator.remove();
        }
        }
       }catch (IOException ex) {
             Logger.getLogger(AbstractMultichatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
