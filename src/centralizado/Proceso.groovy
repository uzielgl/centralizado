/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package centralizado

/**
 *
 * @author uzielgl
 */
class Proceso {
    private ArrayList<Mensaje> queueMessages = new ArrayList<Mensaje>(); //Será la cola de los mensajes que no han sido entregados
    private Proceso coordinador; //El proceso "cliente" es decir, el proceso de él mismo
    private ArrayList<Proceso> procesos = new ArrayList<Proceso>();
    private Proceso client; //El proceso actual que contiene la ip y el port para levantar el servidor
    public Comunicador comunicador;
    public boolean esCoordinador = false;
    
    public String id;
    public String ip;
    public String port;
    
    ArrayList<Recurso> recursos = new ArrayList<Recurso>(); //Recursos compartidos que tiene el proceso
    
    public Proceso(String id, String ip, int port){
        this.id = id;
        this.ip = ip;
        this.port = port;
        comunicador = new Comunicador( ip, port );
    }
    
    public startServer(){
        comunicador.udpServer.start();
    }
    
    
    
    public String toString(){
        return "[$id, $ip, $port] "
    }
        
    /** Establecerá el proceso que está corriendo el algoritmo, es decir, el proceso de él mismo*/
    public setCoordinador( Proceso p ){   
        coordinador = p
        if( this.id.contains( p.id ) ){
            this.esCoordinador = true;
        }
    }

    /* Establece todos los procesos involucrados*/
    public setPeers( ArrayList procesos){
        peers = procesos;
    }
    
    /** Obtiene el primer mensaje encolado*/
    public Mensaje getQueueMessage( ){
        return queueMessages.size() != 0 ? queueMessage.remove(0) : null
    }
    
    /** Agrega a la cola de mensajes el nuevo mensaje*/
    public addQueueMessage( Mensaje m){
        queueMessages.add( m )
        
    }
    
    /******************* Recursos **************/
    
    /** Envia un mensaje de solicitud de recurso al coordinador*/
    public requestResource( int id_resource ){
        Mensaje m = new Mensaje( Mensaje.TIPO_SOLICITUD);
    }
    
    /** El mensaje ya formado sólo para hacer la petición*/
    public requestResource( Mensaje m){
        comunicador.udpClient.sendMessage( coordinador.ip, Integer.parseInt( coordinador.port ), m  );
    }
    
    public addResource( Recurso r ){
        recursos.add( r );
    }
    
    
}

