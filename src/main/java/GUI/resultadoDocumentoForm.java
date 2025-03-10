/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Controller.collaboratorController;
import Entities.Collaborator.collaboratorEntity;
import Entities.User.userEntity;
import Generator.Generator;
import documentData.criteriosInspeccion;
import documentData.dataDocumento;
import documentData.resultadosInspeccion;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author marcelovillalobos
 */
public class resultadoDocumentoForm extends javax.swing.JFrame {

    /**
     * Creates new form userAuthentication
     */
    private dataDocumento documento;
    private criteriosInspeccion criteriosInspeccion;
    private resultadosInspeccion resultadosInspeccion;
    private String resultadoInspeccion, observacionesFinales, elaboradoPor, tipoDocumento;
    private int cantidadErrores;
    private Double porcentajeDefectos;
    private collaboratorController collaboratorContoller;
    private userEntity user;

    public resultadoDocumentoForm(dataDocumento documento, criteriosInspeccion criteriosInspeccion, resultadosInspeccion resultadosInspeccion, userEntity user) {
        initComponents();
        centerWindowOnScreen();
        this.user = user;

        this.documento = documento;
        this.criteriosInspeccion = criteriosInspeccion;
        this.resultadosInspeccion = resultadosInspeccion;

        tipoDocumento = documento.getTipoDocumento();

        cantidadErrores = resultadosInspeccion.getNumeroDefectos();
        porcentajeDefectos = resultadosInspeccion.getPorcentajeDefectos();

        resultadoInspeccion = resultadosInspeccion.getResultadoFinal();
        lblResultado.setText(resultadoInspeccion);

        /* Disabled at the moment
            collaboratorContoller = new collaboratorController();
            populateCollaboratorComboBox(cmbPorcenajeInspeccion);
         */
        //Invisible elements for now, this is to not delete all the functionality already given
        lblElaboradoPor.setVisible(false);
        cmbCollaboratorsList.setVisible(false);

        // Cambia el color del JLabel según el resultado
        if ("Aprobado".equalsIgnoreCase(resultadoInspeccion)) {
            lblResultado.setForeground(new Color(93, 186, 71)); // Color verde para aprobado
        } else if ("Rechazado".equalsIgnoreCase(resultadoInspeccion)) {
            lblResultado.setForeground(new Color(255, 0, 0)); // Color rojo para rechazado
        } else {
            lblResultado.setForeground(Color.BLACK); // Color negro por defecto para otros casos
        }

    }

    public resultadoDocumentoForm() {
    }

    //Method to force centering the form
    private void centerWindowOnScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle screenRect = ge.getMaximumWindowBounds();
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        int centerX = (int) (screenRect.getWidth() - windowWidth) / 2;
        int centerY = (int) (screenRect.getHeight() - windowHeight) / 2;
        setLocation(centerX, centerY);
    }

    // Popular un JComboBox con los colaboradores activos de tipo "collaborator"
    public void populateCollaboratorComboBox(JComboBox<String> comboBox) {
        comboBox.removeAllItems(); // Limpiar el combobox
        comboBox.addItem("Seleccione el colaborador encargado");
        List<collaboratorEntity> collaborators = collaboratorContoller.getActiveCollaboratorsByType();
        if (collaborators != null) {
            for (collaboratorEntity collaborator : collaborators) {
                comboBox.addItem(collaborator.getNameCollaborator());
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        lblResultado = new javax.swing.JLabel();
        lblElaboradoPor = new javax.swing.JLabel();
        lblEstadoDocumento1 = new javax.swing.JLabel();
        btnGenerarDocumento = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaObservaciones = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbCollaboratorsList = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(43, 43, 43));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo.setFont(new java.awt.Font("Helvetica Neue", 0, 5)); // NOI18N
        logo.setIcon(new javax.swing.ImageIcon("/Users/marcelovillalobos/Documents/GitHub/AQL_Generator/src/main/resources/images/ids logo blanco 1.png")); // NOI18N
        jPanel1.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 120, 60));

        lblResultado.setFont(new java.awt.Font("Open Sans", 0, 50)); // NOI18N
        lblResultado.setForeground(new java.awt.Color(217, 217, 217));
        lblResultado.setText("Resultado");
        jPanel1.add(lblResultado, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, 380, -1));

        lblElaboradoPor.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblElaboradoPor.setForeground(new java.awt.Color(217, 217, 217));
        lblElaboradoPor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblElaboradoPor.setText("Elaborado por");
        jPanel1.add(lblElaboradoPor, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 470, -1, -1));

        lblEstadoDocumento1.setFont(new java.awt.Font("Open Sans", 0, 50)); // NOI18N
        lblEstadoDocumento1.setForeground(new java.awt.Color(217, 217, 217));
        lblEstadoDocumento1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstadoDocumento1.setText("Estado:");
        jPanel1.add(lblEstadoDocumento1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, 230, -1));

        btnGenerarDocumento.setBackground(new java.awt.Color(93, 186, 71));
        btnGenerarDocumento.setFont(new java.awt.Font("Helvetica Neue", 0, 20)); // NOI18N
        btnGenerarDocumento.setForeground(new java.awt.Color(43, 43, 43));
        btnGenerarDocumento.setText("Generar Documento");
        btnGenerarDocumento.setBorder(null);
        btnGenerarDocumento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGenerarDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarDocumentoActionPerformed(evt);
            }
        });
        jPanel1.add(btnGenerarDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 610, 230, 60));

        btnVolver.setBackground(new java.awt.Color(255, 0, 0));
        btnVolver.setFont(new java.awt.Font("Helvetica Neue", 0, 20)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(217, 217, 217));
        btnVolver.setText("Volver");
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });
        jPanel1.add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 610, 230, 60));

        txtAreaObservaciones.setColumns(20);
        txtAreaObservaciones.setRows(5);
        jScrollPane1.setViewportView(txtAreaObservaciones);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 270, 590, 190));

        jLabel5.setFont(new java.awt.Font("Open Sans", 0, 50)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(217, 217, 217));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Resultado del Documento");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, -1, -1));

        jLabel6.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(217, 217, 217));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Observaciones Finales");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 230, -1, -1));

        cmbCollaboratorsList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbCollaboratorsList, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 510, 590, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarDocumentoActionPerformed
        observacionesFinales = txtAreaObservaciones.getText();
        elaboradoPor = cmbCollaboratorsList.getSelectedItem().toString();

        resultadosInspeccion = new resultadosInspeccion(cantidadErrores, porcentajeDefectos, resultadoInspeccion, observacionesFinales);

        System.out.println("Nombre de Producto: " + documento.getNombreProducto());
        System.out.println("Marca: " + documento.getMarca());
        System.out.println("Cliente: " + documento.getCliente());
        System.out.println("Fecha Evaluacion: " + documento.getFechaEvaluacion());
        System.out.println("P/N IDS: " + documento.getPnIds());
        System.out.println("Numero de Lote: " + documento.getNumeroLote());
        System.out.println("Proveedor: " + documento.getProveedor());
        System.out.println("Referencia Cliente: " + documento.getReferenciaCliente());
        System.out.println("Factura: " + documento.getFactura());
        System.out.println("Cantidad de Unidades: " + documento.getCantidadUnidades());
        System.out.println("Tipo de Documento: " + documento.getTipoDocumento());
        System.out.println("_____________________________\n");

        System.out.println("Nivel de Inspección: " + criteriosInspeccion.getNivelInspeccion());
        System.out.println("AQL Definido: " + criteriosInspeccion.getAQLDefinido());
        System.out.println("Tamaño de la Muestra: " + criteriosInspeccion.getTamanoMuestra());
        System.out.println("Cantidad de Aceptación: " + criteriosInspeccion.getCantidadAceptacion());
        System.out.println("Cantidad de Rechazo: " + criteriosInspeccion.getCantidadRechazo());
        System.out.println("_____________________________\n");

        System.out.println("Numero de Defectos: " + resultadosInspeccion.getNumeroDefectos());
        System.out.println("Porcentaje de Defectos: " + resultadosInspeccion.getPorcentajeDefectos());
        System.out.println("Resultado Final: " + resultadosInspeccion.getResultadoFinal());
        System.out.println("Observaciones Finales: " + resultadosInspeccion.getObservaciones());
        System.out.println("Elaborado por: " + elaboradoPor);

        System.out.println("_____________________________\n");

        Generator generator = new Generator(criteriosInspeccion, documento, resultadosInspeccion, tipoDocumento);
        try {
            generator.generateDocument();
            JOptionPane.showMessageDialog(null, "Documento generado exitosamente!", "Generación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            homeForm homeForm = new homeForm(user);
            this.dispose();
            homeForm.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(resultadoDocumentoForm.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al generar el documento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGenerarDocumentoActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        cantidadErroresForm cantidadErroresForm = new cantidadErroresForm(documento, criteriosInspeccion, resultadosInspeccion, user);
        this.dispose();
        cantidadErroresForm.setVisible(true);
    }//GEN-LAST:event_btnVolverActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* Set the FlatLaf look and feel */
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf"); // or FlatDarkLaf for dark theme

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(resultadoDocumentoForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(resultadoDocumentoForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(resultadoDocumentoForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(resultadoDocumentoForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new resultadoDocumentoForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerarDocumento;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cmbCollaboratorsList;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblElaboradoPor;
    private javax.swing.JLabel lblEstadoDocumento1;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JLabel logo;
    private javax.swing.JTextArea txtAreaObservaciones;
    // End of variables declaration//GEN-END:variables
}
