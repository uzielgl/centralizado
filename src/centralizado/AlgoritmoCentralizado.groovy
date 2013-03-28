/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package centralizado

/**
 *
 * @author uzielgl
 */
class AlgoritmoCentralizado {
  
    
    
    AlgoritmoCentralizado(  Proceso client, Proceso coordinador, ArrayList<Proceso> procesos ){
        peers = procesos;
        this.coordinador = coordinador;
        this.client = client;
        comunicador = new Comunicador( client.ip, client.port );
        comunicador.udpServer.start();
    }
    
    /** Establecerá el proceso que está corriendo el algoritmo, es decir, el proceso de él mismo*/
    public setCoordinador( Proceso p ){
        coordinador = p
    }

    
    /** Obtiene el primer mensaje encolado*/
    public Mensaje getQueueMessage( ){
        return queueMessages.size() != 0 ? queueMessage.remove(0) : null
    }
    
    /** Agrega a la cola de mensajes el nuevo mensaje*/
    public addQueueMessage( Mensaje m){
        queueMessages.add( m )
        
    }
    
    public requestResource(  ){
        
    }
    
}

