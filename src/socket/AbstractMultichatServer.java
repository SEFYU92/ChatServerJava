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

/**
 *
 * @author Thomas
 */
public class AbstractMultichatServer implements MultichatServer {
     int port;
    InetAddress adress; 
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
    
    ByteBuffer bbyte = ByteBuffer.allocate(1000);
    CharBuffer bchar = null;
    
    while (true) {
      selector.select();
      Set<SelectionKey> readyKeys = selector.selectedKeys();
      Iterator<SelectionKey> iterator = readyKeys.iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        iterator.remove();
        if (key.isAcceptable()) {
          SocketChannel client = server.accept();
          System.out.println("Accepted connection from " + client);
          client.configureBlocking(false);
          
          System.out.println("Client socket channel accepted !");
         // ByteBuffer source = ByteBuffer.wrap("vv");
        //  SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE);
        //  key2.attach(source);
        } else if (key.isReadable()){
            
            ((SocketChannel) key.channel()).read(bbyte);
            bbyte.flip();
            bchar = Charset.defaultCharset().decode(bbyte);
            System.out.println(bchar);
            bbyte.compact();
            
        }else if (key.isWritable()) {
          SocketChannel client = (SocketChannel) key.channel();
          ByteBuffer output = (ByteBuffer) key.attachment();
          if (!output.hasRemaining()) {
            output.rewind();
          }
          client.write(output);
          
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
