/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import clases.*;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Miguel Matul <https://github.com/MigueMat4>
 */
public class frmMain extends javax.swing.JFrame {
    
    Pokedex dexter; // objeto que hará uso de la conexión a la API
    Pokemon miPokemon; // objeto de la clase que hace match con los datos de la API
    Reloj reloj = new Reloj(); // objeto para la hora del sistema. ¡No modificar!
    boolean loop = true;        //Booleano para saber cuando se detiene
    /**
     * Creates new form frmMain
     */
    public frmMain() {
        initComponents();
        reloj.start(); // objeto iniciado para la hora del sistema. ¡No modificar!
        btnDeletrear.setEnabled(false);
    }
    
    // clase que conecta a la API y obtiene los datos del pokémon buscado
    public class Pokedex {
        private static final String POKEMON_API_URL = "https://pokeapi.co/api/v2/pokemon/";
        private final String nombrePokemon;
    
        public Pokedex(String pokemonABuscar){
            nombrePokemon = pokemonABuscar;
        }
        
        public void buscarPokemon() throws IOException, InterruptedException{
            Viewer prueba = new Viewer();
            btnBuscar.setEnabled(false);
            txtNombre.setEnabled(false);
            System.out.println("Conectando a la API...");
            // código para conectarse a la API y descargar los datos.
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("Accept", "application/json")
                    .uri(URI.create(POKEMON_API_URL+nombrePokemon))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("¡Conexión exitosa! Descargando datos...");
            ObjectMapper mapper = new ObjectMapper();
            // obtener los datos del pokémon en el objeto correspondiente
            miPokemon = mapper.readValue(response.body(), Pokemon.class);
            // colocar la información en los label correspondientes
            lblID.setText("#"+miPokemon.getId());
            lblNombre.setText(miPokemon.getName());
            lblHeight.setText(String.valueOf(miPokemon.getHeight()) + " m");
            lblWeight.setText(String.valueOf(miPokemon.getWeight()) + " kg");
            prueba.start();
            System.out.println("¡Datos del Pokémon descargados!");
            btnBuscar.setEnabled(true);
            txtNombre.setEnabled(true);
            btnDeletrear.setEnabled(true);
        }
    }
    
    public void Pausar(){
        loop = false;
    }
    public void Iniciar(){
        loop = true;
    }
    
    // clase que mostrará los 4 sprites del pokémon
    public class Viewer extends Thread{
        ArrayList<Image> list=new ArrayList<Image>();
        int cont = 0;
        public void mostrarSprites() throws MalformedURLException, IOException, InterruptedException{
            // obtengo la url del listado de cada uno de los sprites que me dio la API
            URL url = new URL(miPokemon.getSprites().get("front_default").toString());
            Image img = ImageIO.read(url);
            list.add(img);
            url = new URL(miPokemon.getSprites().get("back_default").toString());
            img = ImageIO.read(url);
            list.add(img);
            url = new URL(miPokemon.getSprites().get("front_shiny").toString());
            img = ImageIO.read(url);
            list.add(img);
            url = new URL(miPokemon.getSprites().get("back_shiny").toString());
            img = ImageIO.read(url);
            list.add(img);
        }
        @Override
        public void run(){
            Iniciar();
            try {
                mostrarSprites();
            } catch (IOException ex) {
                Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            while(loop == true){
                lblSprites.setIcon(new ImageIcon(list.get(cont)));
                cont++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (cont > 3){
                    cont = 0;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    // clase para deletrear el nombre del pokemon
    //<Inserte su código aquí>

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblHH = new javax.swing.JLabel();
        lblMM = new javax.swing.JLabel();
        lblSS = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblSprites = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        lblHeight = new javax.swing.JLabel();
        lblWeight = new javax.swing.JLabel();
        btnDeletrear = new javax.swing.JButton();
        lblLetra = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Pokedex App");

        lblHH.setText("HH:");

        lblMM.setText("MM:");

        lblSS.setText("SS hrs");

        jPanel1.setLayout(null);

        lblSprites.setText("Pokémon no encontrado");
        jPanel1.add(lblSprites);
        lblSprites.setBounds(130, 190, 117, 90);

        lblNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNombre.setText("????");
        jPanel1.add(lblNombre);
        lblNombre.setBounds(530, 180, 140, 30);

        lblID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblID.setText("00");
        jPanel1.add(lblID);
        lblID.setBounds(470, 180, 50, 30);

        lblHeight.setText("?? m");
        jPanel1.add(lblHeight);
        lblHeight.setBounds(480, 240, 90, 14);

        lblWeight.setText("?? kg");
        jPanel1.add(lblWeight);
        lblWeight.setBounds(580, 240, 100, 14);

        btnDeletrear.setText("Deletrear Pokémon");
        btnDeletrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletrearActionPerformed(evt);
            }
        });
        jPanel1.add(btnDeletrear);
        btnDeletrear.setBounds(440, 440, 260, 70);

        lblLetra.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblLetra.setText("?");
        jPanel1.add(lblLetra);
        lblLetra.setBounds(130, 460, 20, 30);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Pokedex.jpg"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(0, 0, 745, 541);

        jLabel3.setText("Ingrese el nombre a buscar:");

        txtNombre.setText("ditto");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addComponent(lblHH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSS)
                .addGap(40, 40, 40))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblHH)
                    .addComponent(lblMM)
                    .addComponent(lblSS)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        Hilo bpokemon = new Hilo(1);
        bpokemon.start();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //
    }//GEN-LAST:event_formWindowOpened

    private void btnDeletrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletrearActionPerformed
        //
    }//GEN-LAST:event_btnDeletrearActionPerformed

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
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new frmMain().setVisible(true);
        });
        System.out.println("LOGS:");
    }
    
    // clase para la hora del sistema. ¡No modificar!
    public class Reloj extends Thread {
        Calendar calendario;
        @Override
        public void run() {
            while (true) {
                calendario= Calendar.getInstance();
                if (calendario.get(Calendar.HOUR_OF_DAY)<10)
                    lblHH.setText(String.valueOf("0"+calendario.get(Calendar.HOUR_OF_DAY)) + " :");
                else
                    lblHH.setText(String.valueOf(calendario.get(Calendar.HOUR_OF_DAY)) + " :");
                if (calendario.get(Calendar.MINUTE)<10)
                    lblMM.setText(String.valueOf("0"+calendario.get(Calendar.MINUTE)) + " :");
                else
                    lblMM.setText(String.valueOf(calendario.get(Calendar.MINUTE)) + " :");
                if (calendario.get(Calendar.SECOND)<10)
                    lblSS.setText(String.valueOf("0"+calendario.get(Calendar.SECOND)) + " hrs");
                else
                    lblSS.setText(String.valueOf(calendario.get(Calendar.SECOND)) + " hrs");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public class Hilo extends Thread{
        int numLabel = 0;
        boolean pokemon = true;
        String np;
        public Hilo(int num){
            this.numLabel = num;
        }
        
    @Override
        public void run(){  
            if (numLabel == 1) {
                dexter = new Pokedex(txtNombre.getText());
                try {
                    dexter.buscarPokemon();
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                lblSprites.setText("");
            }else if(numLabel == 2){

            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnDeletrear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblHH;
    private javax.swing.JLabel lblHeight;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblLetra;
    private javax.swing.JLabel lblMM;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblSS;
    private javax.swing.JLabel lblSprites;
    private javax.swing.JLabel lblWeight;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
