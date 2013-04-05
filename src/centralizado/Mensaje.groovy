/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package centralizado
import java.io.Serializable;
/**
 *
 * @author uzielgl
 */
class Mensaje implements Serializable{
    public static final int TIPO_SOLICITUD = 1;
    public static final int TIPO_OTORGAMIENTO = 2;
    public static final int TIPO_LIBERACION = 3;
    
    //Id del proceso solicitante
    public int id_solicitante;
    public int id_recurso;
    
    public Recurso recurso; //Recurso a otorgar o liberar
    
    public int tipo;
    public requestResources = [:];
    
    /***/
    public Mensaje(int tipo, int id_solicitante){
        this.tipo = tipo;
        if( tipo == 1)
            this.id_solicitante = id_solicitante;
        else if( tipo == 3)
            this.id_recurso = id_solicitante
    }
    
    
    Mensaje(int tipo, Recurso r){
        this.tipo = tipo;
        recurso = r;
    }
    
    //Cuando es de tipo solicitud le voy agregando los id de recursos que solicita
    public addRequestResources( int id_resource, int value ){
        requestResources[ id_resource ] = value ;
    }
    
        
    
    
    
}

