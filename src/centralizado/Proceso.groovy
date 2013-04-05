/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package centralizado

/**
 *
 * @author uzielgl
 */
class Proceso implements ComunicadorListener{
    private ArrayList<Mensaje> queueMessages = new ArrayList<Mensaje>(); //Será la cola de los mensajes que no han sido entregados
    private Proceso coordinador; //El proceso "cliente" es decir, el proceso de él mismo
    
    //Peers son todos los procesos involucrados en el SD
    private ArrayList<Proceso> peers = new ArrayList<Proceso>();
    
    private Proceso client; //El proceso actual que contiene la ip y el port para levantar el servidor
    public Comunicador comunicador;
    public boolean esCoordinador = false;
    
    public MainWindow window;
    
    public int id;
    public String ip;
    public int port;
    
    // Una lista de la forma [ [proceso, recurso], [proceso, recurso] ]
    public solicitudesRecurso = [];
    
    ArrayList<Recurso> recursos = new ArrayList<Recurso>(); //Recursos compartidos que tiene el proceso
    
    //Listeners
    public List<ProcesoListener> listeners = new ArrayList<ProcesoListener>();
    
    public Proceso(int id, String ip, int port){
        this.id = id;
        this.ip = ip;
        this.port = port;
        comunicador = new Comunicador( ip, port );
        comunicador.udpServer.listeners.add(this);
    }
    
    public setWindow(MainWindow w){
        window = w;
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
        if( this.id == p.id  ){
            this.esCoordinador = true;
        }
    }

    /* Establece todos los procesos involucrados*/
    public setPeers( ArrayList<Proceso> procesos){
        peers = procesos;
    }
    
    /** Regresa un proceso por id*/
    public getPeerById( int id ){
        for( Proceso p: peers){
            if( p.id == id ) return p;
        }
        return null;
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
        comunicador.udpClient.sendMessage( coordinador.ip,  coordinador.port, m  );
    }
    
    public addResource( Recurso r ){
        recursos.add( r );
    }
    
    public Recurso getResourceById(int id){
        for( Recurso r: recursos ){
            if( r.id == id ) return r;
        }
        return null;
    }
    
    /** Envia un mensaje de liberación de recurso al coordinador*/
    public freeResourceRequest( int id_resource){
        comunicador.udpClient.sendMessage( coordinador.ip,  coordinador.port, new Mensaje(3, id_resource)  );
    }
    
    public void freeResource( int id_resource){
        for( int x = 0; x < this.recursos.size(); x++){ //Recorremos todos los recursos
            if( this.recursos[x].id == id_resource ){
                this.recursos[x].setFree();
                window.addHistory("liberado el recurso " + recursos[x].id );
            }
        }
    }
    
    
    
    
    /* Procesa la solicitud de petición que le llega.
     * Verifica que el recurso no esté ocupado, si no está ocupado, manda un mensaje
     * de otorgamineto con ese recurso.
     * Si está ocupado pone ese recurso en cola. Una cola de recursos en espera
     * que debe de llevar el proceso que lo está solicitado y el id del recurso a solicitar por lo menos*/
    public processRequestResource( Mensaje m ){
       //for( int x = 0; x < m.requestResources.size(); x++){ //Verificamos los recursos que solicita
       for(int x: m.requestResources.keySet() ){
            println "el proceso en uso: " + getResourceById( x ).isUse;
            if( !getResourceById( x ).isBusy() ){ //Si el recurso no está ocupado
                //Lo pasamos a ocupado
                Recurso r = getResourceById( x );
                r.setUse();
                //Le ponemos su valor
                r.value = m.requestResources[x];
                if( x == 1) window.lblRecurso1.setText( Integer.toString( m.requestResources[x] ) )
                if( x == 2) window.lblRecurso2.setText( Integer.toString( m.requestResources[x] ) )
                if( x == 3) window.lblRecurso3.setText( Integer.toString( m.requestResources[x] ) )
                
                //Enviamos mensaje de otorgamiento con el recurso al proceso solicitante
                println this.getPeerById( m.id_solicitante ).port.class;
                Proceso a = this.getPeerById( m.id_solicitante );
                comunicador.udpClient.sendMessage( a.ip, a.port, new Mensaje( Mensaje.TIPO_OTORGAMIENTO, r) );
                window.addHistory("Se otorga acceso de recurso " + r.id + " a proceso " + m.id_solicitante );
            }else{
                //Debo de agregar a una cola de solicitudesRecurso [ [id_recurso, id_proceso_solicitante] ]
                solicitudesRecurso.add( [ x, m.id_solicitante, m.requestResources[x] ] );
            }
            actualizarColaRecursos();
        }
    }
    
    public void actualizarColaRecursos(){
        //Separamos los recursos
        ArrayList cola_recurso1 = new ArrayList();
        ArrayList cola_recurso2 = new ArrayList();
        ArrayList cola_recurso3 = new ArrayList();
        //Recurso1
        for( ArrayList l : solicitudesRecurso ){
            if( l[0] == 1) cola_recurso1.add( [ l[1], l[2]] );
            if( l[0] == 2) cola_recurso2.add( [ l[1], l[2]] );
            if( l[0] == 3) cola_recurso3.add( [ l[1], l[2]] );
        }
        
        window.lblCola1.setText( cola_recurso1.toString() )
        window.lblCola2.setText( cola_recurso2.toString() )
        window.lblCola3.setText( cola_recurso3.toString() )
        
    }
    
    public void otorgarServicio(int id_resource){
        for( int x = 0 ; x < solicitudesRecurso.size() ; x++){
            println solicitudesRecurso[x][0]
            if( solicitudesRecurso[x][0] == id_resource ){
                otorgarServicio( id_resource, solicitudesRecurso[x][1], solicitudesRecurso[x][2] );
                solicitudesRecurso.remove(x);
                break;
            }
        }
        actualizarColaRecursos();
    }
    
    public void otorgarServicio(int id_resource, int id_proceso, int value_to_change){
        //Lo pasamos a ocupado
        Recurso r = getResourceById( id_resource );
        r.setUse();
        r.value = value_to_change;
        
        if( id_resource == 1) window.lblRecurso1.setText( Integer.toString( value_to_change ) )
        if( id_resource == 2) window.lblRecurso2.setText( Integer.toString( value_to_change ) )
        if( id_resource == 3) window.lblRecurso3.setText( Integer.toString( value_to_change ) )
        
        //Enviamos mensaje de otorgamiento con el recurso al proceso solicitante
        println this.getPeerById( id_proceso ).port.class;
        Proceso a = this.getPeerById( id_proceso );
        comunicador.udpClient.sendMessage( a.ip, a.port, new Mensaje( Mensaje.TIPO_OTORGAMIENTO, r) );
        window.addHistory("Se otorga acceso de recurso " + r.id + " a proceso " + id_proceso );
    }
    
    public void receiveMessage( Mensaje m){
        //Para coordinador
        if( m.tipo == Mensaje.TIPO_SOLICITUD ){
            println "Recibo mensaje de solicitud" + this
            processRequestResource( m );
        }
        
        if( m.tipo == Mensaje.TIPO_OTORGAMIENTO ){
            println "Recibo mensaje de otorgamiento" + this
            for (ProcesoListener pl : listeners) pl.recibeRecurso( m.recurso );
        }
        
        //Para coordinador
        if( m.tipo == Mensaje.TIPO_LIBERACION){
            println "Liberando el servicio" + this
            freeResource( m.id_recurso );
            otorgarServicio( m.id_recurso ); //Checa de la cola de servicios, cual es el siguiente que se debe de otorgar
        }
    }
    
    
    
    
}

