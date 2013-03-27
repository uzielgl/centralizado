/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package centralizado;

/**
 *
 * @author uzielgl
 */
public interface ComunicadorListener {
    void receiveMessage( Mensaje m );
    
    //Aquí mismo podría agregar cosas como addToHistory que agregaría a un txtde histórico
}
