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
    
    int tipo;
    ArrayList<Integer> requestResources = new ArrayList<Integer>();
    
    /***/
    public Mensaje(int tipo){
        this.tipo = tipo;
    }
    
    public addRequestResources( int id_resource ){
        requestResources.add( id_resource );
    }
    
        
    
    
    
}

