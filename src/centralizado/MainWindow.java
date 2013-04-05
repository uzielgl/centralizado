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
public class MainWindow extends javax.swing.JFrame implements ProcesoListener{
    
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
        
        pnlCola.setVisible(false);
        pnlSolicitud.setVisible(false);
        pnlLiberacion.setVisible(false);
        pack();
                
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
        
        cliente = new Proceso( Integer.parseInt( selectedProcess ), confClient.get("ip"),  Integer.parseInt( confClient.get("port" ) ) );
        cliente.startServer();
        cliente.listeners.add(this);
        cliente.setWindow(this);

        coordinador = new Proceso( Integer.parseInt( selectedCoordinador), confCoordinador.get("ip"),  Integer.parseInt( confCoordinador.get("port" ) ) );
        cliente.setCoordinador( coordinador );
        
        //cliente.comunicador.udpServer.listeners.add(this);
        
        System.out.println("Cliente" + cliente);
        System.out.println("Coordinador" + coordinador);
        if( cliente.id == coordinador.id  ){
            this.pnlCola.setVisible(true);
            this.pnlCola.setSize(30, 100);
            pack();
            cliente.addResource( new Recurso( 1, "Recurso texto") );
            cliente.addResource( new Recurso( 2, "Recurso bd") );
            cliente.addResource( new Recurso( 3, "Recurso archivo") );
            System.out.println("Agregando recursos al proceso 5 (solo cuando el cliente es igual al coordinador)");
            addHistory(coordinador.id + " Cómo coordinador.");
            addHistory("Agregando recursos al coordinador");
        }else{
            this.pnlLiberacion.setVisible(true);
            this.pnlSolicitud.setVisible(true);
            pack();
            addHistory(cliente.id + " Cómo cliente.");
            
        }
        
        ArrayList<Proceso> procesos = new ArrayList<Proceso>();
        for( String key : process.keySet() ){
            Map<String,String> conf = process.get( key );
            procesos.add( new Proceso( Integer.parseInt(key), conf.get("ip"), Integer.parseInt( conf.get("port") ) ) );
        }
        cliente.setPeers(procesos);
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

        jPanel5 = new javax.swing.JPanel();
        pnlSelectProccess = new javax.swing.JPanel();
        pnlSelectCoordinator = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        pnlCola = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblCola1 = new javax.swing.JLabel();
        lblCola2 = new javax.swing.JLabel();
        lblCola3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblRecurso1 = new javax.swing.JLabel();
        lblRecurso2 = new javax.swing.JLabel();
        lblRecurso3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pnlSolicitud = new javax.swing.JPanel();
        checkRecurso3 = new javax.swing.JCheckBox();
        checkRecurso2 = new javax.swing.JCheckBox();
        checkRecurso1 = new javax.swing.JCheckBox();
        btnSolicitar = new javax.swing.JButton();
        txtRecurso1 = new javax.swing.JTextField();
        txtRecurso2 = new javax.swing.JTextField();
        txtRecurso3 = new javax.swing.JTextField();
        pnlLiberacion = new javax.swing.JPanel();
        btnLiberarR1 = new javax.swing.JButton();
        btnLiberarR2 = new javax.swing.JButton();
        btnLiberarR3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtHistorial = new javax.swing.JTextArea();

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlSelectProccess.setLayout(new javax.swing.BoxLayout(pnlSelectProccess, javax.swing.BoxLayout.LINE_AXIS));

        pnlSelectCoordinator.setLayout(new javax.swing.BoxLayout(pnlSelectCoordinator, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Algoritmo Centralizado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        pnlCola.setBorder(javax.swing.BorderFactory.createTitledBorder("Cola de recursos"));
        pnlCola.setToolTipText("");

        jLabel1.setText("1");

        jLabel2.setText("2");

        jLabel3.setText("3");

        lblCola1.setText(" ");

        lblCola2.setText(" ");

        lblCola3.setText(" ");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Valores"));

        jLabel4.setText("1");

        jLabel5.setText("2");

        jLabel6.setText("3");

        lblRecurso1.setText(" ");

        lblRecurso2.setText(" ");

        lblRecurso3.setText(" ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRecurso1)
                            .addComponent(lblRecurso2)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRecurso3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblRecurso1))
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblRecurso2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblRecurso3))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlColaLayout = new javax.swing.GroupLayout(pnlCola);
        pnlCola.setLayout(pnlColaLayout);
        pnlColaLayout.setHorizontalGroup(
            pnlColaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlColaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlColaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlColaLayout.createSequentialGroup()
                        .addGroup(pnlColaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlColaLayout.createSequentialGroup()
                                .addGroup(pnlColaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlColaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCola1)
                                    .addComponent(lblCola2)))
                            .addGroup(pnlColaLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCola3)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnlColaLayout.setVerticalGroup(
            pnlColaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlColaLayout.createSequentialGroup()
                .addGroup(pnlColaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblCola1))
                .addGap(5, 5, 5)
                .addGroup(pnlColaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblCola2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlColaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblCola3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlSolicitud.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Solicitud de Recursos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10))); // NOI18N

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

        txtRecurso1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRecurso1ActionPerformed(evt);
            }
        });

        txtRecurso2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRecurso2ActionPerformed(evt);
            }
        });

        txtRecurso3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRecurso3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSolicitudLayout = new javax.swing.GroupLayout(pnlSolicitud);
        pnlSolicitud.setLayout(pnlSolicitudLayout);
        pnlSolicitudLayout.setHorizontalGroup(
            pnlSolicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSolicitudLayout.createSequentialGroup()
                .addComponent(btnSolicitar)
                .addGap(0, 35, Short.MAX_VALUE))
            .addGroup(pnlSolicitudLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSolicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSolicitudLayout.createSequentialGroup()
                        .addComponent(checkRecurso3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRecurso3))
                    .addGroup(pnlSolicitudLayout.createSequentialGroup()
                        .addComponent(checkRecurso1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRecurso1))
                    .addGroup(pnlSolicitudLayout.createSequentialGroup()
                        .addComponent(checkRecurso2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRecurso2))))
        );
        pnlSolicitudLayout.setVerticalGroup(
            pnlSolicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSolicitudLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSolicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkRecurso1)
                    .addComponent(txtRecurso1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlSolicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkRecurso2)
                    .addComponent(txtRecurso2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlSolicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkRecurso3)
                    .addComponent(txtRecurso3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSolicitar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlLiberacion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liberación de Recursos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10))); // NOI18N

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

        javax.swing.GroupLayout pnlLiberacionLayout = new javax.swing.GroupLayout(pnlLiberacion);
        pnlLiberacion.setLayout(pnlLiberacionLayout);
        pnlLiberacionLayout.setHorizontalGroup(
            pnlLiberacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLiberacionLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(pnlLiberacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLiberarR3)
                    .addComponent(btnLiberarR2)
                    .addComponent(btnLiberarR1))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        pnlLiberacionLayout.setVerticalGroup(
            pnlLiberacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLiberacionLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnLiberarR1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLiberarR2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLiberarR3)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(pnlSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlLiberacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSolicitud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlLiberacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCola, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(pnlCola, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSolicitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolicitarActionPerformed
        // TODO add your handling code here:
        resourcesCheckeds = new ArrayList<Integer>();
        Mensaje m = new Mensaje(Mensaje.TIPO_SOLICITUD, cliente.id);
        ArrayList r = new ArrayList();
        if( this.checkRecurso1.isSelected() ) {
            m.addRequestResources( 1, Integer.parseInt( this.txtRecurso1.getText() ) );
            resourcesCheckeds.add( 1 );
        }
        if( this.checkRecurso2.isSelected() ) {
            m.addRequestResources( 2, Integer.parseInt( this.txtRecurso2.getText() ) );
            resourcesCheckeds.add( 2 );
        }
        if( this.checkRecurso3.isSelected() ) {
            m.addRequestResources( 3, Integer.parseInt( this.txtRecurso3.getText() ) );
            resourcesCheckeds.add( 3 );
        }
        cliente.requestResource( m );
        addHistory("Enviando mensaje de solicitud al coordinador.");
        addHistory("Solicitando recursos: " + resourcesCheckeds);
        this.btnSolicitar.setEnabled(false);
        enableAllCheckbox(false);
        //enableButtonsResource(true, resourcesCheckeds);
        System.out.println("Recursos" + resourcesCheckeds);

    }//GEN-LAST:event_btnSolicitarActionPerformed

    private void checkRecurso3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkRecurso3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkRecurso3ActionPerformed

    private void btnLiberarR3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiberarR3ActionPerformed
        // TODO add your handling code here:
        addHistory("Envio de liberación de recurso 3");
        freeCheckedResource( 3 );
        cliente.freeResourceRequest( 3 );
    }//GEN-LAST:event_btnLiberarR3ActionPerformed

    private void btnLiberarR2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiberarR2ActionPerformed
        // TODO add your handling code here:
        freeCheckedResource( 2 );
        addHistory("Envio de liberación de recurso 2");
        cliente.freeResourceRequest(2);
    }//GEN-LAST:event_btnLiberarR2ActionPerformed

    private void btnLiberarR1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiberarR1ActionPerformed
        // TODO add your handling code here:
        freeCheckedResource( 1 );
        addHistory("Envio de liberación de recurso 1");
        cliente.freeResourceRequest(1);
    }//GEN-LAST:event_btnLiberarR1ActionPerformed

    private void txtRecurso1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRecurso1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRecurso1ActionPerformed

    private void txtRecurso2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRecurso2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRecurso2ActionPerformed

    private void txtRecurso3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRecurso3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRecurso3ActionPerformed

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
    
    public void enableButtonsResource( boolean block, int id_resource ){
            if( id_resource == 1) btnLiberarR1.setEnabled(block);
            if( id_resource == 2) btnLiberarR2.setEnabled(block);
            if( id_resource == 3) btnLiberarR3.setEnabled(block);
    }
    
    public void recibeRecurso(Recurso r){
        addHistory("Recibiendo recurso " + r.id );
        addHistory("Haciendo cositas con recurso " + r.id);
        enableButtonsResource(true, r.id);
    }
    
    
    
    /*
    public void receiveMessage(Mensaje m){
        if( cliente.esCoordinador ){
            //cliente.processRequestResource( m );
        }
        System.out.println("He recibido un mensaje");
    }*/
    
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblCola1;
    public javax.swing.JLabel lblCola2;
    public javax.swing.JLabel lblCola3;
    public javax.swing.JLabel lblRecurso1;
    public javax.swing.JLabel lblRecurso2;
    public javax.swing.JLabel lblRecurso3;
    private javax.swing.JPanel pnlCola;
    private javax.swing.JPanel pnlLiberacion;
    private javax.swing.JPanel pnlSelectCoordinator;
    private javax.swing.JPanel pnlSelectProccess;
    private javax.swing.JPanel pnlSolicitud;
    public javax.swing.JTextArea txtHistorial;
    private javax.swing.JTextField txtRecurso1;
    private javax.swing.JTextField txtRecurso2;
    private javax.swing.JTextField txtRecurso3;
    // End of variables declaration//GEN-END:variables
}
