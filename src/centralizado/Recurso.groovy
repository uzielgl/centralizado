/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package centralizado

/**
 *
 * @author uzielgl
 */
class Recurso implements Serializable{
    public boolean isUse; //Determina si el recurso está ocupado o no
    public int id; //ID del algoritmo
    public String descripcion; //Descripción del recurso
    
    Recurso( int id, String descripcion ){
        this.id = id;
        this.descripcion = descripcion;
        this.isUse = false;
    }
    
    /** Regresa si está ocupado o no el recurso*/
    public boolean isBusy(){
        return isUse;
    }
    
    /** Lo pone en uso*/
    public boolean setUse(){
        this.isUse = true;
    }
    
    /** Lo libera*/
    public boolean setFree(){
        this.isUse = false;
    }
    
    public String toString(){
        return "id: " + id + " en uso: " + this.isUse;
    }
    
}

