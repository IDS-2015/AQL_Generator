/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import documentData.criteriosInspeccion;
import documentData.dataDocumento;
import documentData.resultadosInspeccion;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author marcelovillalobos
 */
public class cantidadErroresForm extends javax.swing.JFrame {

    /**
     * Creates new form userAuthentication
     */
    private dataDocumento documento;
    private criteriosInspeccion criteriosInspeccion;
    private resultadosInspeccion resultadosInspeccion;
    private double porcentajeErrores;
    private int cantidadErrores, cantidadUnidadesLote, cantidadRechazo;
    private String resultadoDocumento;
    private JTextField[] fields;
    
    public cantidadErroresForm(dataDocumento documento, criteriosInspeccion criteriosInspeccion, resultadosInspeccion resultadosInspeccion) {
        initComponents();
        centerWindowOnScreen();
        
        this.documento = documento;
        this.criteriosInspeccion = criteriosInspeccion;
        this.resultadosInspeccion = resultadosInspeccion;
        
        cantidadRechazo = criteriosInspeccion.getCantidadRechazo();
        
        cantidadUnidadesLote = documento.getCantidadUnidades();
        txtCantidadUnidadesLote.setText(String.valueOf(cantidadUnidadesLote));
        txtCantidadUnidadesLote.setEditable(false);
        
        
        txtCantidadErrores.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarPorcentajeErrores();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarPorcentajeErrores();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarPorcentajeErrores();
            }
        });
        
        txtCantidadErrores.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9'))
                        && (caracter != '\b')) {
                    e.consume();
                }
            }
        });
        
        txtPorcentajeErrores.setEditable(false);

        // Inicializa el array de JTextField
        fields = new JTextField[]{
            txtCantidadErrores
        };
        
        agregarListenersATextFields(fields);
        
    }
    
    public cantidadErroresForm() {
    }

    // Método para añadir listeners a los campos de texto
    private void agregarListenersATextFields(JTextField[] fields) {
        for (JTextField field : fields) {
            field.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    actualizarBotones();
                }
                
                @Override
                public void removeUpdate(DocumentEvent e) {
                    actualizarBotones();
                }
                
                @Override
                public void changedUpdate(DocumentEvent e) {
                    actualizarBotones();
                }
            });
        }
    }

// Método para actualizar el estado de los botones
    private void actualizarBotones() {
        boolean hayTexto = false;
        boolean todosLlenos = true;

        // Verificar cada campo de texto
        for (JTextField field : fields) {
            if (field.getText().isEmpty()) {
                todosLlenos = false; // Si algún campo está vacío
            } else {
                hayTexto = true; // Si al menos un campo tiene texto
            }
        }

        // Habilitar o deshabilitar los botones según el estado de los campos
        btnVaciarCampos.setEnabled(hayTexto);
        btnSiguiente.setEnabled(todosLlenos);
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
    
    private void calcularPorcentajeError(int cantidadErrores, int cantidadUnidadesLote) {
        if (cantidadUnidadesLote > 0) { // Verificar que no se divida por cero
            porcentajeErrores = ((double) cantidadErrores / cantidadUnidadesLote) * 100;
        } else {
            porcentajeErrores = 0; // O manejar el caso de manera diferente si es necesario
            System.out.println("La cantidad de unidades no puede ser cero o negativa.");
        }
    }
    
    private void actualizarPorcentajeErrores() {
        try {
            cantidadErrores = Integer.parseInt(txtCantidadErrores.getText());
            // Llama al método para calcular el porcentaje
            calcularPorcentajeError(cantidadErrores, cantidadUnidadesLote);

            // Actualiza el campo de texto o etiqueta donde mostrarás el porcentaje
            txtPorcentajeErrores.setText(String.format("%.2f%%", porcentajeErrores)); // Asegúrate de tener un JTextField o JLabel para mostrar el porcentaje
        } catch (NumberFormatException e) {
            // Manejar el caso en que el texto no es un número válido
            txtPorcentajeErrores.setText("0.00%"); // O cualquier valor por defecto que desees
        }
    }
    
    private String calcularResultadoDocumento() {
        
        if (cantidadErrores < cantidadRechazo) {
            return resultadoDocumento = "Aprobado";
        } else {
            return resultadoDocumento = "Rechazado";
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
        lblCantidadErrores = new javax.swing.JLabel();
        txtCantidadErrores = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPorcentajeErrores = new javax.swing.JTextField();
        lblPorcentajeErrores = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        btnVaciarCampos = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        lblCantidadUnidadesLote = new javax.swing.JLabel();
        txtCantidadUnidadesLote = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(43, 43, 43));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo.setFont(new java.awt.Font("Helvetica Neue", 0, 5)); // NOI18N
        logo.setIcon(new javax.swing.ImageIcon("/Users/marcelovillalobos/Documents/GitHub/AQL_Generator/src/main/resources/images/ids logo blanco 1.png")); // NOI18N
        jPanel1.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 120, 60));

        lblCantidadErrores.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblCantidadErrores.setForeground(new java.awt.Color(217, 217, 217));
        lblCantidadErrores.setText("Cantidad de Errores");
        jPanel1.add(lblCantidadErrores, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, -1, -1));

        txtCantidadErrores.setBackground(new java.awt.Color(102, 102, 102));
        txtCantidadErrores.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtCantidadErrores.setForeground(new java.awt.Color(217, 217, 217));
        txtCantidadErrores.setToolTipText("");
        txtCantidadErrores.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtCantidadErrores.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadErroresFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadErroresFocusLost(evt);
            }
        });
        txtCantidadErrores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadErroresActionPerformed(evt);
            }
        });
        jPanel1.add(txtCantidadErrores, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 320, 680, 50));

        jLabel3.setFont(new java.awt.Font("Open Sans", 0, 50)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(217, 217, 217));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Cantidad de Errores");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, -1, -1));

        txtPorcentajeErrores.setBackground(new java.awt.Color(102, 102, 102));
        txtPorcentajeErrores.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtPorcentajeErrores.setForeground(new java.awt.Color(217, 217, 217));
        txtPorcentajeErrores.setText("0.00%");
        txtPorcentajeErrores.setToolTipText("");
        txtPorcentajeErrores.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtPorcentajeErrores.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPorcentajeErroresFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPorcentajeErroresFocusLost(evt);
            }
        });
        txtPorcentajeErrores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorcentajeErroresActionPerformed(evt);
            }
        });
        jPanel1.add(txtPorcentajeErrores, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, 680, 50));

        lblPorcentajeErrores.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblPorcentajeErrores.setForeground(new java.awt.Color(217, 217, 217));
        lblPorcentajeErrores.setText("Porcentaje de Errores");
        jPanel1.add(lblPorcentajeErrores, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 390, 220, -1));

        btnVolver.setBackground(new java.awt.Color(255, 0, 0));
        btnVolver.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(217, 217, 217));
        btnVolver.setText("Volver");
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });
        jPanel1.add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 590, 150, 60));

        btnVaciarCampos.setBackground(new java.awt.Color(217, 217, 217));
        btnVaciarCampos.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnVaciarCampos.setForeground(new java.awt.Color(43, 43, 43));
        btnVaciarCampos.setText("Vaciar Campos");
        btnVaciarCampos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVaciarCampos.setEnabled(false);
        btnVaciarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVaciarCamposActionPerformed(evt);
            }
        });
        jPanel1.add(btnVaciarCampos, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 590, 220, 60));

        btnSiguiente.setBackground(new java.awt.Color(93, 186, 71));
        btnSiguiente.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnSiguiente.setForeground(new java.awt.Color(43, 43, 43));
        btnSiguiente.setText("Siguiente");
        btnSiguiente.setBorder(null);
        btnSiguiente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSiguiente.setEnabled(false);
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 590, 220, 60));

        lblCantidadUnidadesLote.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblCantidadUnidadesLote.setForeground(new java.awt.Color(217, 217, 217));
        lblCantidadUnidadesLote.setText("Cantidad de Unidades del Lote");
        jPanel1.add(lblCantidadUnidadesLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 180, -1, -1));

        txtCantidadUnidadesLote.setBackground(new java.awt.Color(102, 102, 102));
        txtCantidadUnidadesLote.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtCantidadUnidadesLote.setForeground(new java.awt.Color(217, 217, 217));
        txtCantidadUnidadesLote.setToolTipText("");
        txtCantidadUnidadesLote.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtCantidadUnidadesLote.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadUnidadesLoteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadUnidadesLoteFocusLost(evt);
            }
        });
        txtCantidadUnidadesLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadUnidadesLoteActionPerformed(evt);
            }
        });
        jPanel1.add(txtCantidadUnidadesLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 220, 680, 50));

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

    private void txtCantidadErroresFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadErroresFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadErroresFocusGained

    private void txtCantidadErroresFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadErroresFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadErroresFocusLost

    private void txtCantidadErroresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadErroresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadErroresActionPerformed

    private void txtPorcentajeErroresFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPorcentajeErroresFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPorcentajeErroresFocusGained

    private void txtPorcentajeErroresFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPorcentajeErroresFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPorcentajeErroresFocusLost

    private void txtPorcentajeErroresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPorcentajeErroresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPorcentajeErroresActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        
        criteriosInspeccionForm criteriosInspeccionForm = new criteriosInspeccionForm(documento, criteriosInspeccion, resultadosInspeccion);
        this.dispose();
        criteriosInspeccionForm.setVisible(true);

    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        calcularResultadoDocumento();
        resultadosInspeccion = new resultadosInspeccion(cantidadErrores, porcentajeErrores, resultadoDocumento, null);
        
        resultadoDocumentoForm resultadoDocumentoForm = new resultadoDocumentoForm(documento, criteriosInspeccion, resultadosInspeccion);
        this.dispose();
        resultadoDocumentoForm.setVisible(true);
        
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void txtCantidadUnidadesLoteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadUnidadesLoteFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadUnidadesLoteFocusGained

    private void txtCantidadUnidadesLoteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadUnidadesLoteFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadUnidadesLoteFocusLost

    private void txtCantidadUnidadesLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadUnidadesLoteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadUnidadesLoteActionPerformed

    private void btnVaciarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaciarCamposActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVaciarCamposActionPerformed

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
            java.util.logging.Logger.getLogger(cantidadErroresForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cantidadErroresForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cantidadErroresForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cantidadErroresForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new cantidadErroresForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnVaciarCampos;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCantidadErrores;
    private javax.swing.JLabel lblCantidadUnidadesLote;
    private javax.swing.JLabel lblPorcentajeErrores;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField txtCantidadErrores;
    private javax.swing.JTextField txtCantidadUnidadesLote;
    private javax.swing.JTextField txtPorcentajeErrores;
    // End of variables declaration//GEN-END:variables
}
