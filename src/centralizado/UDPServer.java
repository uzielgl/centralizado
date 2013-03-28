/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package centralizado;
/**
 *
 * @author uzielgl
 */
import java.net.*;
import java.io.*;
import java.util.*;

public class UDPServer extends Thread implements Serializable{
    private String ip;
    private String port;
    public List<ComunicadorListener> listeners = new ArrayList<ComunicadorListener>();
    
    UDPServer( String ip, String port ){
        this.ip = ip;
        this.port = port;
    }
    UDPServer( String ip, int port){
        this.ip = ip;
        this.port = Integer.toString( port );    
    }
    
    public void run(){
        try{
            DatagramSocket aSocket = new DatagramSocket( Integer.parseInt( port ) );
            while(true){
                byte[] buffer = new byte[1000];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                
                //Deserializamos el objeto
                ByteArrayInputStream objIn = new ByteArrayInputStream( request.getData() );
                ObjectInputStream ois = new ObjectInputStream( objIn );
                try{
                    Mensaje mensaje = (Mensaje) ois.readObject();
                    for (ComunicadorListener cl : listeners) cl.receiveMessage( mensaje );
                    System.out.print("Recibiendo mensaje con UDP: ");
                    System.out.println( mensaje );
                }catch( Exception e){
                    e.printStackTrace();
                }
                //System.out.println( new String( request.getData(), "UTF-8" ).trim() );
            } 
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }catch (IOException e) {System.out.println("IO: " + e.getMessage());}
    }
} 
