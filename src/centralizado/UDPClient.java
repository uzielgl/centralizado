/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package centralizado;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.*;
import java.io.ByteArrayOutputStream;
/**
 *
 * @author uzielgl
 */
public class UDPClient{
      
    public void sendMessage(String ip, int port, Mensaje m){
        //Lo convertimos a bytes
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream os;
        try {
            os = new ObjectOutputStream(bytes);
            os.writeObject( m );  
            os.close();
        } catch (IOException ex) {
            System.out.println("Error de ioexception en UDPClient:sendMessage");
        }
        byte[] messageBytes = bytes.toByteArray();
        
        //Y se enviamos ese arreglo en el datagrama
        try{
            DatagramSocket aSocket = new DatagramSocket();    
            DatagramPacket messageOut = new DatagramPacket(messageBytes, messageBytes.length, 
               InetAddress.getByName(ip), port);
            aSocket.send( messageOut );
            aSocket.close();
            System.out.print("Enviando mensaje con UDP: ");
            System.out.println( m );
        }catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        }catch (IOException e){
            System.out.println("IO: " + e.getMessage());
        }
    }

    
}
