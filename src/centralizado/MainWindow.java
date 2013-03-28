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
public class MainWindow extends javax.swing.JFrame implements ComunicadorListener {
    
    ArrayList<JButton> btns_procesos = new ArrayList<JButton>();
    String selectedProcess;
    
    ArrayList<JButton> btns_coordinadores = new ArrayList<JButton>();
    String selectedCoordinador;
    
    Proceso cliente; //Proceso que corre en este cliente
    Proceso coordinador;
    
    ArrayList<Integer> resourcesCheckeds = new ArrayList<Integer>();
    
    ArrayList<JButton> resourceButtons;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();   
        loadProcessButtons();
        loadCoordinatesButtons();
        
        resourceButtons = new ArrayList<JButton>();
        resourceButtons.add(btnLiberarR1);
        resourceButtons.add(btnLiberarR2);
        resourceButtons.add(btnLiberarR3);
        
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
                    createProceso();
                }
            });
            pnlSelectCoordinator.add( b );
        }        
    }
    
    /* Instancia el algoritmocentralizado*/
    public void createProceso(){
        Map<String,HashMap> process = new Gson().fromJson( getContentFile( new File("procesos.txt") ), Map.class );
        Map<String,String> confClient = process.get( selectedProcess );
        Map<String,String> confCoordinador = process.get( selectedCoordinador );
        
        cliente = new Proceso( selectedProcess, confClient.get("ip"),  Integer.parseInt( confClient.get("port" ) ) );
        cliente.startServer();
        
        coordinador = new Proceso( selectedCoordinador, confCoordinador.get("ip"),  Integer.parseInt( confCoordinador.get("port" ) ) );
        cliente.setCoordinador( coordinador );
        
        cliente.comunicador.udpServer.listeners.add(this);
        
        System.out.println("Cliente" + cliente);
        System.out.println("Coordinador" + coordinador);
        if( cliente.id.contains( coordinador.id ) ){
            cliente.addResource( new Recurso( 1, "Recurso texto") );
            cliente.addResource( new Recurso( 2, "Recurso bd") );
            cliente.addResource( new Recurso( 3, "Recurso archivo") );
            System.out.println("Agregando recursos al proceso 5 (solo cuando el cliente es igual al coordinador)");
            addHistory(coordinador.id + " Cómo coordinador.");
            addHistory("Agregando recursos al coordinador");
        }else{
            addHistory(cliente.id + " Cómo cliente.");
        }
        
        ArrayList<Proceso> procesos = new ArrayList<Proceso>();
        for( String key : process.keySet() ){
            Map<String,String> conf = process.get( key );
            procesos.add( new Proceso(key, conf.get("ip"), Integer.parseInt( conf.get("port") ) ) );
        }
        System.out.println(procesos);
    }
    
    public void addHistory(String s){
        if( this.txtHistorial.getText().isEmpty() )
            this.txtHistorial.append( s );
        else{
            this.txtHistorial.append( "\n" + s);
        }
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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        checkRecurso3 = new javax.swing.JCheckBox();
        checkRecurso2 = new javax.swing.JCheckBox();
        checkRecurso1 = new javax.swing.JCheckBox();
        btnSolicitar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnLiberarR1 = new javax.swing.JButton();
        btnLiberarR2 = new javax.swing.JButton();
        btnLiberarR3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtHistorial = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlSelectProccess.setLayout(new javax.swing.BoxLayout(pnlSelectProccess, javax.swing.BoxLayout.LINE_AXIS));

        pnlSelectCoordinator.setLayout(new javax.swing.BoxLayout(pnlSelectCoordinator, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Algoritmo Centralizado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Solicitud de Recursos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10))); // NOI18N

        checkRecurso3.setText("Recurso 3");
        checkRecurso3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkRecurso3ActionPerformed(evt);
            }
        });

        checkRecurso2.setText("Recurso 2");

        checkRecurso1.setText("Recurso 1");

        btnSolicitar.setText("Solicitar Recurso(s)");
        btnSolicitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolicitarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnSolicitar)
                .addGap(0, 9, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkRecurso1)
                    .addComponent(checkRecurso2)
                    .addComponent(checkRecurso3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkRecurso1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkRecurso2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkRecurso3)
                .addGap(18, 18, 18)
                .addComponent(btnSolicitar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liberación de Procesos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10))); // NOI18N

        btnLiberarR1.setText("Recurso 1");
        btnLiberarR1.setEnabled(false);
        btnLiberarR1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiberarR1ActionPerformed(evt);
            }
        });

        btnLiberarR2.setText("Recurso 2");
        btnLiberarR2.setEnabled(false);
        btnLiberarR2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiberarR2ActionPerformed(evt);
            }
        });

        btnLiberarR3.setText("Recurso 3");
        btnLiberarR3.setEnabled(false);
        btnLiberarR3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiberarR3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLiberarR3)
                    .addComponent(btnLiberarR2)
                    .addComponent(btnLiberarR1))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnLiberarR1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLiberarR2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLiberarR3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Historial", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10))); // NOI18N

        txtHistorial.setColumns(20);
        txtHistorial.setRows(5);
        jScrollPane1.setViewportView(txtHistorial);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlSelectProccess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlSelectCoordinator, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlSelectProccess, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(pnlSelectCoordinator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkRecurso3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkRecurso3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkRecurso3ActionPerformed

    private void btnSolicitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolicitarActionPerformed
        // TODO add your handling code here:
        resourcesCheckeds = new ArrayList<Integer>();
        Mensaje m = new Mensaje(Mensaje.TIPO_SOLICITUD);
        if( this.checkRecurso1.isSelected() ) {
            m.addRequestResources( 1 );
            resourcesCheckeds.add( 1 );
        }
        if( this.checkRecurso2.isSelected() ) {
            m.addRequestResources( 2 );
            resourcesCheckeds.add( 2 );
        }
        if( this.checkRecurso3.isSelected() ) {
            m.addRequestResources( 3 );
            resourcesCheckeds.add( 3 );
        }
        cliente.requestResource( m );
        addHistory("Enviando mensaje de solicitud al coordinador.");
        
        this.btnSolicitar.setEnabled(false);
        enableAllCheckbox(false);
        enableButtonsResource(true, resourcesCheckeds);
        System.out.println("Recursos" + resourcesCheckeds);
    }//GEN-LAST:event_btnSolicitarActionPerformed

    private void btnLiberarR1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiberarR1ActionPerformed
        // TODO add your handling code here:
        freeCheckedResource( 1 );
    }//GEN-LAST:event_btnLiberarR1ActionPerformed

    private void btnLiberarR2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiberarR2ActionPerformed
        // TODO add your handling code here:
        freeCheckedResource( 2 );
    }//GEN-LAST:event_btnLiberarR2ActionPerformed

    private void btnLiberarR3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiberarR3ActionPerformed
        // TODO add your handling code here:
        freeCheckedResource( 3 );
    }//GEN-LAST:event_btnLiberarR3ActionPerformed

    public void freeCheckedResource(int id_resource){
        System.out.println("recursos checkeados actuales" + resourcesCheckeds);
        for(int x=0; x<resourcesCheckeds.size(); x++){
            if( resourcesCheckeds.get(x) == id_resource){
                resourceButtons.get( resourcesCheckeds.get(x) - 1 ).setText("Liberado " + id_resource);
                resourceButtons.get( resourcesCheckeds.get(x) - 1 ).setEnabled(false);
                resourcesCheckeds.remove(x);
                break;
            }
        }
        if( resourcesCheckeds.isEmpty() ){
            resetForm();
        }
        System.out.println("Recursos checkeados restantes " + resourcesCheckeds );
    }
    
    public void resetForm(){
        enableAllCheckbox( true );
        this.checkRecurso1.setSelected(false);
        this.checkRecurso2.setSelected(false);
        this.checkRecurso3.setSelected(false);
        this.btnSolicitar.setEnabled(true);
        
        for(int x=0; x<this.resourceButtons.size(); x++){
            this.resourceButtons.get(x).setEnabled(false);
            this.resourceButtons.get(x).setText("Recurso " + (x+1) );
        }
    }
    
    public void enableAllCheckbox( boolean enable){
        this.checkRecurso1.setEnabled( enable );
        this.checkRecurso2.setEnabled( enable );
        this.checkRecurso3.setEnabled( enable );
    }
    
    public void enableButtonsResource( boolean block){
        btnLiberarR1.setEnabled(block);
        btnLiberarR2.setEnabled(block);
        btnLiberarR3.setEnabled(block);
    }
    public void enableButtonsResource( boolean block, ArrayList<Integer> id_resources ){
        for( Integer r: id_resources ){
            if( r == 1) btnLiberarR1.setEnabled(block);
            if( r == 2) btnLiberarR2.setEnabled(block);
            if( r == 3) btnLiberarR3.setEnabled(block);
        }
            
    }
    
    public void receiveMessage(Mensaje m){
        if( cliente.esCoordinador ){
            System.out.println("Soy el coordinador");
        }
        System.out.println("He recibido un mensaje");
    }
    
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
    private javax.swing.JButton btnLiberarR1;
    private javax.swing.JButton btnLiberarR2;
    private javax.swing.JButton btnLiberarR3;
    private javax.swing.JButton btnSolicitar;
    private javax.swing.JCheckBox checkRecurso1;
    private javax.swing.JCheckBox checkRecurso2;
    private javax.swing.JCheckBox checkRecurso3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlSelectCoordinator;
    private javax.swing.JPanel pnlSelectProccess;
    public javax.swing.JTextArea txtHistorial;
    // End of variables declaration//GEN-END:variables
}
