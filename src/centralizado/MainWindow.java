/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package centralizado;

import javax.swing.BorderFactory;
import java.io.*;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
/**
 *
 * @author uzielgl
 */
public class MainWindow extends javax.swing.JFrame {
    
    ArrayList<JButton> btns_procesos = new ArrayList<JButton>();
    String selectedProcess;
    
    ArrayList<JButton> btns_coordinadores = new ArrayList<JButton>();
    String selectedCoordinador;
    
    AlgoritmoCentralizado algoritmoCentralizado;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();   
        loadProcessButtons();
        loadCoordinatesButtons();
        
    }
    
    /** Carga los procesos disponibles en procesos.txt para seleccionar alguno y interactue como tal*/
    public void loadProcessButtons(){
        pnlSelectProccess.setBorder( BorderFactory.createTitledBorder("Actuar como:"));
        Map<String,HashMap> process = new Gson().fromJson( getContentFile( new File("procesos.txt") ), Map.class );
        for(String key : process.keySet()) {
            JButton b = new JButton( key );
            btns_procesos.add( b );
            b.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    selectedProcess = ( (javax.swing.JButton) e.getSource() ).getText();
                    for( JButton btn: btns_procesos ) btn.setEnabled( false );
                    setTitle( selectedProcess );
                    pnlSelectProccess.setBorder( BorderFactory.createTitledBorder("Actuar como: " + selectedProcess ) );
                }
            });
            pnlSelectProccess.add( b );
        }        
    }
    
     /** Carga los procesos disponibles en procesos.txt para seleccionar alguno e interactue como coordinador (servidor)*/
    public void loadCoordinatesButtons(){
        pnlSelectCoordinator.setBorder( BorderFactory.createTitledBorder("Coordinador:"));
        Map<String,HashMap> process = new Gson().fromJson( getContentFile( new File("procesos.txt") ), Map.class );
        for(String key : process.keySet()) {
            JButton b = new JButton( key );
            btns_coordinadores.add( b );
            b.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String proccess = ( (javax.swing.JButton) e.getSource() ).getText();
                    selectedCoordinador = ( (javax.swing.JButton) e.getSource() ).getText();
                    for( JButton btn: btns_coordinadores ) btn.setEnabled( false );
                    setTitle( selectedCoordinador );
                    pnlSelectCoordinator.setBorder( BorderFactory.createTitledBorder("Coordinador: " + selectedCoordinador ) );
                    createAlgoritmCentralizado();
                }
            });
            pnlSelectCoordinator.add( b );
        }        
    }
    
    /* Instancia el algoritmocentralizado*/
    public void createAlgoritmCentralizado(){
        Map<String,HashMap> process = new Gson().fromJson( getContentFile( new File("procesos.txt") ), Map.class );
        Map<String,String> confClient = process.get( selectedProcess );
        Map<String,String> confCoordinador = process.get( selectedCoordinador );
        Proceso cliente = new Proceso( selectedProcess, confClient.get("ip"),  Integer.parseInt( confClient.get("port" ) ) );
        Proceso coordinador = new Proceso( selectedCoordinador, confClient.get("ip"),  Integer.parseInt( confClient.get("port" ) ) );
        
        ArrayList<Proceso> procesos = new ArrayList<Proceso>();
        for( String key : process.keySet() ){
            Map<String,String> conf = process.get( key );
            procesos.add( new Proceso(key, conf.get("ip"), Integer.parseInt( conf.get("port") ) ) );
        }
        System.out.println(procesos);
    }
    
    /** Obtiene el contenido de un archivo*/
    public String getContentFile( File file ){
        String text = "";
        try{
            Scanner config_scanner = new Scanner( file );
            while( config_scanner.hasNext() ){
                text += config_scanner.next();
            }
        }catch(IOException e){
        }
        return text;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSelectProccess = new javax.swing.JPanel();
        pnlSelectCoordinator = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlSelectProccess.setLayout(new javax.swing.BoxLayout(pnlSelectProccess, javax.swing.BoxLayout.LINE_AXIS));

        pnlSelectCoordinator.setLayout(new javax.swing.BoxLayout(pnlSelectCoordinator, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlSelectProccess, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSelectCoordinator, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlSelectProccess, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(pnlSelectCoordinator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(225, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlSelectCoordinator;
    private javax.swing.JPanel pnlSelectProccess;
    // End of variables declaration//GEN-END:variables
}