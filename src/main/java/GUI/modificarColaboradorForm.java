/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Controller.collaboratorController;
import Controller.userController;
import Entities.User.userEntity;
import Entities.Collaborator.collaboratorEntity;
import Interfaces.collaboratorsEventsListener;
import Interfaces.usersEventsListener;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author marcelovillalobos
 */
public class modificarColaboradorForm extends javax.swing.JFrame {

    /**
     * Creates new form userAuthentication
     */
    private JTextField[] fields;
    private collaboratorsEventsListener eventsListener;
    private collaboratorEntity selectedCollaborator;

    private String originalName;
    
    private String originalType;

    public modificarColaboradorForm(adminCollaboratorsForm adminCollaboratorsForm ,collaboratorEntity selectedCollaborator) {
        initComponents();
        centerWindowOnScreen();
        populateComboBox();

        this.eventsListener = adminCollaboratorsForm;
        this.selectedCollaborator = selectedCollaborator;

        originalName = selectedCollaborator.getNameCollaborator();
        
        originalType = selectedCollaborator.getTypeCollaborator();

        txtNameCollaborator.setText(originalName);
        
        CmbBoxType.setSelectedItem(originalType);

        fields = new JTextField[]{
           txtNameCollaborator
        };

        agregarListenersATextFields(fields);
        agregarListenerAComboBox(CmbBoxType);
    }

    public modificarColaboradorForm() {
    }

    // Método para establecer los valores originales al cargar el usuario
    private void setOriginalValues(String name, String type) {
        this.originalName = name;
        this.originalType = type;
    }

    // Método para verificar si hubo cambios en los datos del usuario
    private boolean huboCambios(String username,String role) {
        return !username.equals(originalName)
                
                || !role.equals(originalType);
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

    private void populateComboBox() {
        CmbBoxType.addItem("Seleccione un tipo");
        CmbBoxType.addItem("Collaborator");
        CmbBoxType.addItem("Reviewer");
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

// Método para añadir un listener al JComboBox
    private void agregarListenerAComboBox(JComboBox<String> comboBox) {
        comboBox.addActionListener(e -> actualizarBotones());
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

        // Validar el ComboBox (debe tener un valor distinto al índice 0)
        boolean comboBoxSeleccionado = CmbBoxType.getSelectedIndex() != 0;

        // Ajustar las condiciones de validación con el ComboBox
        todosLlenos = todosLlenos && comboBoxSeleccionado;
        hayTexto = hayTexto || comboBoxSeleccionado; // Si el ComboBox es distinto del default, cuenta como texto ingresado

        // Habilitar o deshabilitar los botones según el estado de los campos
        btnVaciarCampos.setEnabled(hayTexto);
        btnModificar.setEnabled(todosLlenos);
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
        lblNameCollaborator = new javax.swing.JLabel();
        txtNameCollaborator = new javax.swing.JTextField();
        lblSelectedUser1 = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnVaciarCampos = new javax.swing.JButton();
        lblTypeCollaborator = new javax.swing.JLabel();
        CmbBoxType = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(43, 43, 43));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 700));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo.setFont(new java.awt.Font("Helvetica Neue", 0, 5)); // NOI18N
        logo.setIcon(new javax.swing.ImageIcon("/Users/marcelovillalobos/Documents/GitHub/AQL_Generator/src/main/resources/images/ids logo blanco 1.png")); // NOI18N
        jPanel1.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 120, 60));

        lblNameCollaborator.setFont(new java.awt.Font("Raleway", 0, 24)); // NOI18N
        lblNameCollaborator.setForeground(new java.awt.Color(255, 255, 255));
        lblNameCollaborator.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNameCollaborator.setText("Nombre del Colaborador");
        jPanel1.add(lblNameCollaborator, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, 440, 40));

        txtNameCollaborator.setBackground(new java.awt.Color(51, 51, 51));
        txtNameCollaborator.setFont(new java.awt.Font("Raleway", 0, 16)); // NOI18N
        txtNameCollaborator.setForeground(new java.awt.Color(153, 153, 153));
        txtNameCollaborator.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtNameCollaborator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameCollaboratorActionPerformed(evt);
            }
        });
        jPanel1.add(txtNameCollaborator, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, 440, 45));

        lblSelectedUser1.setFont(new java.awt.Font("Raleway", 0, 36)); // NOI18N
        lblSelectedUser1.setForeground(new java.awt.Color(255, 255, 255));
        lblSelectedUser1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSelectedUser1.setText("Modificar Colaborador");
        jPanel1.add(lblSelectedUser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 420, 50));

        btnCerrar.setBackground(new java.awt.Color(255, 51, 0));
        btnCerrar.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setText("X");
        btnCerrar.setBorder(null);
        btnCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 30, 40, 40));

        btnModificar.setBackground(new java.awt.Color(93, 186, 71));
        btnModificar.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(43, 43, 43));
        btnModificar.setText("Modificar Colaborador");
        btnModificar.setBorder(null);
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificar.setEnabled(false);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 530, 190, 60));

        btnVaciarCampos.setBackground(new java.awt.Color(204, 204, 204));
        btnVaciarCampos.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnVaciarCampos.setForeground(new java.awt.Color(43, 43, 43));
        btnVaciarCampos.setText("Vaciar Campos");
        btnVaciarCampos.setBorder(null);
        btnVaciarCampos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVaciarCampos.setEnabled(false);
        btnVaciarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVaciarCamposActionPerformed(evt);
            }
        });
        jPanel1.add(btnVaciarCampos, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, 190, 60));

        lblTypeCollaborator.setFont(new java.awt.Font("Raleway", 0, 24)); // NOI18N
        lblTypeCollaborator.setForeground(new java.awt.Color(255, 255, 255));
        lblTypeCollaborator.setText("Rol");
        jPanel1.add(lblTypeCollaborator, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, 440, 40));

        CmbBoxType.setFont(new java.awt.Font("Raleway", 0, 18)); // NOI18N
        CmbBoxType.setToolTipText("");
        CmbBoxType.setBorder(null);
        CmbBoxType.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CmbBoxType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CmbBoxTypeActionPerformed(evt);
            }
        });
        jPanel1.add(CmbBoxType, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 340, 440, 47));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 902, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameCollaboratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameCollaboratorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameCollaboratorActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();

    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        try {
            
            
            int id = selectedCollaborator.getIdCollaborator();
            String name = txtNameCollaborator.getText().trim();
            
            String type = (CmbBoxType.getSelectedItem() != null) ? CmbBoxType.getSelectedItem().toString() : "";

            // Validación básica antes de proceder
            if (name.isEmpty() || type.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si hay cambios
            if (!huboCambios(name, type)) {
                JOptionPane.showMessageDialog(null, "No se realizaron cambios.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Si hubo cambios, actualizar el usuario
            collaboratorController controller = new collaboratorController();
            
            controller.updateCollaborator(id, name, type);
            eventsListener.onCollaboratorAdded();

            JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Error: Uno de los elementos no está inicializado correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado al actualizar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        this.dispose();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnVaciarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaciarCamposActionPerformed

        txtNameCollaborator.setText("");
        
        CmbBoxType.setSelectedIndex(0);

    }//GEN-LAST:event_btnVaciarCamposActionPerformed

    private void CmbBoxTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CmbBoxTypeActionPerformed

    }//GEN-LAST:event_CmbBoxTypeActionPerformed

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
            java.util.logging.Logger.getLogger(modificarColaboradorForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(modificarColaboradorForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(modificarColaboradorForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(modificarColaboradorForm.class
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
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new modificarColaboradorForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CmbBoxType;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnVaciarCampos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblNameCollaborator;
    private javax.swing.JLabel lblSelectedUser1;
    private javax.swing.JLabel lblTypeCollaborator;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField txtNameCollaborator;
    // End of variables declaration//GEN-END:variables
}
