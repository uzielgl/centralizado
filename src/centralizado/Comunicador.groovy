/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package centralizado

/**
 *
 * @author uzielgl
 */
class Comunicador implements Serializable{
    public UDPServer udpServer;
    public UDPClient udpClient;
    
    Comunicador(String ip, int port){
        udpServer = new UDPServer(ip, port); //Siempre levanta el servidor
        udpClient = new UDPClient();
    }
    
    /**
     * Sólo se enviarán objectos "Mensaje".
     **/
    public sendMessage(Proceso p, Mensaje m){
        udpClient.sendMenssage( p.ip, p.port, m );
    }
	
}

