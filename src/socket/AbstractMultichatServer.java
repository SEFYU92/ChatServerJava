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
import java.util.Scanner;
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
           
    Selector selector = Selector.open();
     server.configureBlocking(false);
         server.register(selector, SelectionKey.OP_ACCEPT);
    
    ByteBuffer message = ByteBuffer.allocate(1000);
    int i = 0;
    
    while (true) {
        
      selector.select();
      Set<SelectionKey> readyKeys = selector.selectedKeys();
      Iterator<SelectionKey> iterator = readyKeys.iterator();
      
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        
        if (key.isAcceptable()) {
          
          SocketChannel client = server.accept();
          client.configureBlocking(false);
          ByteBuffer buf = ByteBuffer.allocate(1000);
          i++;
          client.register(selector, SelectionKey.OP_READ, i);
       

        } else if (key.isReadable()){
            
            int read =((SocketChannel) key.channel()).read(message);
            if(read > 0){
            byte[] bytes = new byte[read];
            int indice = 0;
            String v = "", indiceClient = "";
            
            message.flip();
            message.get(bytes);
            v = new String( bytes, Charset.forName("UTF-8") );
            indice = (int)key.attachment();
            if(indice > 0) indiceClient = Integer.toString(indice);
            v = "Client"+indiceClient+" : "+v;
            System.out.println(v);
            message.compact();
    
              for(SelectionKey key2 : selector.keys()){
                    if(key2.isValid() && key2.channel() instanceof SocketChannel) {

                        message.clear();
                        message.put((v).getBytes());
                        message.flip();
                        
                        ((SocketChannel)key2.channel()).write(message);
                        message.rewind();
                    }
                }
         }
        }
        iterator.remove();
        }
    }
        
       }catch (IOException ex) {
             Logger.getLogger(AbstractMultichatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
