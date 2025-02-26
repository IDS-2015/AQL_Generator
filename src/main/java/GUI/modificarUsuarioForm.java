/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Controller.userController;
import Entities.User.userEntity;
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
public class modificarUsuarioForm extends javax.swing.JFrame {

    /**
     * Creates new form userAuthentication
     */
    private JTextField[] fields;
    private usersEventsListener eventsListener;
    private userEntity selectedUser;

    private String originalUsername;
    private String originalPassword;
    private String originalRole;

    public modificarUsuarioForm(adminUsersForm adminUsersForm ,userEntity selectedUser) {
        initComponents();
        centerWindowOnScreen();
        populateComboBox();

        this.eventsListener = adminUsersForm;
        this.selectedUser = selectedUser;

        originalUsername = selectedUser.getNameUser();
        originalPassword = selectedUser.getPasswordUser();
        originalRole = selectedUser.getRoleUser();

        txtUsername.setText(originalUsername);
        txtPassword.setText(originalPassword);
        CmbBoxRol.setSelectedItem(originalRole);

        fields = new JTextField[]{
            txtPassword, txtUsername
        };

        agregarListenersATextFields(fields);
        agregarListenerAComboBox(CmbBoxRol);
    }

    public modificarUsuarioForm() {
    }

    // Método para establecer los valores originales al cargar el usuario
    private void setOriginalValues(String username, String password, String role) {
        this.originalUsername = username;
        this.originalPassword = password;
        this.originalRole = role;
    }

    // Método para verificar si hubo cambios en los datos del usuario
    private boolean huboCambios(String username, String password, String role) {
        return !username.equals(originalUsername)
                || !password.equals(originalPassword)
                || !role.equals(originalRole);
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
        CmbBoxRol.addItem("Seleccione un rol");
        CmbBoxRol.addItem("Admin");
        CmbBoxRol.addItem("Usuario");
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
        boolean comboBoxSeleccionado = CmbBoxRol.getSelectedIndex() != 0;

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
        lblSelectedUser = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblSelectedUser1 = new javax.swing.JLabel();
        lblSelectedUser2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        btnCerrar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnVaciarCampos = new javax.swing.JButton();
        lblClientCode1 = new javax.swing.JLabel();
        CmbBoxRol = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(43, 43, 43));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 700));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo.setFont(new java.awt.Font("Helvetica Neue", 0, 5)); // NOI18N
        logo.setIcon(new javax.swing.ImageIcon("/Users/marcelovillalobos/Documents/GitHub/AQL_Generator/src/main/resources/images/ids logo blanco 1.png")); // NOI18N
        jPanel1.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 120, 60));

        lblSelectedUser.setFont(new java.awt.Font("Raleway", 0, 24)); // NOI18N
        lblSelectedUser.setForeground(new java.awt.Color(255, 255, 255));
        lblSelectedUser.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSelectedUser.setText("Nombre de Usuario");
        jPanel1.add(lblSelectedUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, 440, 40));

        txtUsername.setBackground(new java.awt.Color(51, 51, 51));
        txtUsername.setFont(new java.awt.Font("Raleway", 0, 16)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(153, 153, 153));
        txtUsername.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });
        jPanel1.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, 440, 45));

        lblSelectedUser1.setFont(new java.awt.Font("Raleway", 0, 36)); // NOI18N
        lblSelectedUser1.setForeground(new java.awt.Color(255, 255, 255));
        lblSelectedUser1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSelectedUser1.setText("Modificar Usuario");
        jPanel1.add(lblSelectedUser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 330, 50));

        lblSelectedUser2.setFont(new java.awt.Font("Raleway", 0, 24)); // NOI18N
        lblSelectedUser2.setForeground(new java.awt.Color(255, 255, 255));
        lblSelectedUser2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSelectedUser2.setText("Contraseña");
        jPanel1.add(lblSelectedUser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 290, 440, 40));

        txtPassword.setBackground(new java.awt.Color(51, 51, 51));
        txtPassword.setFont(new java.awt.Font("Raleway", 0, 16)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(153, 153, 153));
        txtPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        jPanel1.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 330, 440, 45));

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
        btnModificar.setText("Modificar Usuario");
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

        lblClientCode1.setFont(new java.awt.Font("Raleway", 0, 24)); // NOI18N
        lblClientCode1.setForeground(new java.awt.Color(255, 255, 255));
        lblClientCode1.setText("Rol");
        jPanel1.add(lblClientCode1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, 440, 40));

        CmbBoxRol.setFont(new java.awt.Font("Raleway", 0, 18)); // NOI18N
        CmbBoxRol.setToolTipText("");
        CmbBoxRol.setBorder(null);
        CmbBoxRol.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CmbBoxRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CmbBoxRolActionPerformed(evt);
            }
        });
        jPanel1.add(CmbBoxRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 430, 440, 47));

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

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();

    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        try {
            
            
            int idUser = selectedUser.getIdUser();
            String username = txtUsername.getText().trim();
            String password = txtPassword.getText().trim();
            String role = (CmbBoxRol.getSelectedItem() != null) ? CmbBoxRol.getSelectedItem().toString() : "";

            // Validación básica antes de proceder
            if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si hay cambios
            if (!huboCambios(username, password, role)) {
                JOptionPane.showMessageDialog(null, "No se realizaron cambios.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Si hubo cambios, actualizar el usuario
            userController controller = new userController();
            controller.updateUser(idUser,username, password, role);
            eventsListener.onUserUpdated();

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

        txtUsername.setText("");
        txtPassword.setText("");
        CmbBoxRol.setSelectedIndex(0);

    }//GEN-LAST:event_btnVaciarCamposActionPerformed

    private void CmbBoxRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CmbBoxRolActionPerformed

    }//GEN-LAST:event_CmbBoxRolActionPerformed

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
            java.util.logging.Logger.getLogger(modificarUsuarioForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(modificarUsuarioForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(modificarUsuarioForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(modificarUsuarioForm.class
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
                new modificarUsuarioForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CmbBoxRol;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnVaciarCampos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblClientCode1;
    private javax.swing.JLabel lblSelectedUser;
    private javax.swing.JLabel lblSelectedUser1;
    private javax.swing.JLabel lblSelectedUser2;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
