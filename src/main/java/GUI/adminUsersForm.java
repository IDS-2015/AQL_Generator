/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Controller.userController;
import Entities.User.userEntity;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import Interfaces.*;
import javax.swing.JOptionPane;

/**
 *
 * @author marcelovillalobos
 */
public class adminUsersForm extends javax.swing.JFrame implements usersEventsListener {

    /**
     * Creates new form userAuthentication
     */
    private userEntity user;

    public adminUsersForm(userEntity user) {
        initComponents();
        centerWindowOnScreen();

        this.user = user;
        usersTable.setModel(new MyTableModel());
        populateTable();
        populateComboBoxes();

        //Listeners to select rows
        jPanel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // Cambiado de mouseClicked a mousePressed
                if (usersTable.getSelectedRow() != -1) {
                    usersTable.clearSelection(); // Deseleccionar la fila
                    txtSelectedUser.setText("");
                    txtSelectedUser.setEnabled(false);

                    btnModificar.setEnabled(false);
                    btnCambiarEstado.setEnabled(false);

                }

            }
        });

    }

    public adminUsersForm() {
    }

    @Override
    public void onUserAdded() {
        MyTableModel tableModel = (MyTableModel) usersTable.getModel();
        tableModel.refreshData();
        usersTable.repaint(); // Force the table to repaint itself
    }

    @Override
    public void onUserUpdated() {
        MyTableModel tableModel = (MyTableModel) usersTable.getModel();
        tableModel.refreshData();
        usersTable.repaint(); // Force the table to repaint itself
    }

    @Override
    public void onUserDeactivated() {
        MyTableModel tableModel = (MyTableModel) usersTable.getModel();
        tableModel.refreshData();
        usersTable.repaint(); // Force the table to repaint itself
    }

    @Override
    public void onUserActivated() {
        MyTableModel tableModel = (MyTableModel) usersTable.getModel();
        tableModel.refreshData();
        usersTable.repaint(); // Force the table to repaint itself
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

    //Method to populate table
    private void populateTable() {
        userController controller = new userController();
        List<userEntity> user = controller.getActiveUsers();

        MyTableModel tableModel = (MyTableModel) usersTable.getModel();
        tableModel.refreshData();

        // Escuchar los cambios en la selección de la tabla
        usersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Asegura que el evento no se dispara dos veces
                    int selectedRow = usersTable.getSelectedRow();
                    if (selectedRow != -1) {

                        btnModificar.setEnabled(true);

                        userEntity selectedUser = getUserFromTable(selectedRow);

                        if (selectedUser.getStatus().equals("Active")) {
                            btnCambiarEstado.setEnabled(true);
                            btnCambiarEstado.setText("Desactivar Usuario");

                        } else if (selectedUser.getStatus().equals("Inactive")) {

                            btnCambiarEstado.setEnabled(true);
                            btnCambiarEstado.setText("Activar Usuario");
                        }

                        // Actualiza los JTextFields en el EDT
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                txtSelectedUser.setText(selectedUser.getNameUser());
                                txtSelectedUser.setEnabled(true);

                            }
                        });
                    } else {
                        btnModificar.setEnabled(false);

                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                txtSelectedUser.setText("");
                                txtSelectedUser.setEnabled(false);

                            }
                        });
                    }
                }
            }
        });
    }

    //Method to populate combo boxes
    private void populateComboBoxes() {
        CmbBoxEstado.removeAllItems();

        // Add status options to the combo box
        CmbBoxEstado.addItem("Active");
        CmbBoxEstado.addItem("Inactive");

        String status;
        status = CmbBoxEstado.getSelectedItem().toString();

        // Add an ActionListener to filter the table based on the selected status
        CmbBoxEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected status
                String selectedStatus = (String) CmbBoxEstado.getSelectedItem();

                // Filter the table based on the selected status
                if (selectedStatus != null) {
                    filterTableByStatus();
                } else {
                    populateTable(); // Populate the table with all clients and products if no status is selected
                }
            }
        });

        CmbBoxEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTableByStatus();
            }
        });
    }

    // Method to filter table by the status selected through the combo box
    private void filterTableByStatus() {
        userController controller = new userController();
        String status = CmbBoxEstado.getSelectedItem().toString();
        System.out.println("Filtering by status: " + status); // Debug line

        List<userEntity> filteredUsers = controller.getUsersByStatus(status); // Llamada corregida

        // Ajuste de botones según el estado seleccionado
        if (status.equals("Active")) {
            btnCambiarEstado.setEnabled(true);
            btnCambiarEstado.setText("Desactivar Usuario");

        } else if (status.equals("Inactive")) {
            btnCambiarEstado.setEnabled(true);
            btnCambiarEstado.setText("Activar Usuario");
        }

        MyTableModel tableModel = (MyTableModel) usersTable.getModel();
        System.out.println("Number of filtered clients: " + filteredUsers.size()); // Debug line
        tableModel.setUsers(filteredUsers); // Update the table model with filtered clients
    }

    //Defining the table model
    class MyTableModel extends AbstractTableModel {

        private List<userEntity> users;

        public MyTableModel() {
            users = new ArrayList<>(); // Inicializar con una lista vacía
            refreshData(); // Cargar los datos desde el controlador
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false; // Hacer que todas las celdas no sean editables
        }

        //Refresh data
        public void refreshData() {
            userController controller = new userController();
            List<userEntity> allUsers = controller.getActiveUsers(); // Fetch all active clients
            this.users = allUsers; // Update the list of clients
            fireTableDataChanged(); // Notify the table model that data has changed
            CmbBoxEstado.setSelectedItem("Active");
            btnCambiarEstado.setEnabled(false);

        }

        //Set the clients
        public void setUsers(List<userEntity> users) {
            this.users = users;
            fireTableDataChanged(); // Notify the table model that data has changed
        }

        @Override
        public int getRowCount() {
            return users.size();
        }

        @Override
        public int getColumnCount() {
            return 4; // Asumiendo que tienes 3 columnas
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "ID";
                case 1:
                    return "Name";
                case 2:
                    return "Password";
                case 3:
                    return "Role";
                case 4:
                    return "Status";
                default:
                    return null;
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            userEntity user = users.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return user.getIdUser();
                case 1:
                    return user.getNameUser();
                case 2:
                    return user.getPasswordUser();
                case 3:
                    return user.getRoleUser();
                case 4:
                    return user.getStatus();
                default:
                    return null;
            }
        }
    }

    //Get client from table selecting the row
    public userEntity getUserFromTable(int selectedRow) {
        MyTableModel tableModel = (MyTableModel) usersTable.getModel();
        return tableModel.users.get(selectedRow);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();
        txtSelectedUser = new javax.swing.JTextField();
        lblSelectedUser = new javax.swing.JLabel();
        lblClientCode1 = new javax.swing.JLabel();
        CmbBoxEstado = new javax.swing.JComboBox<>();
        btnRefrescarTabla = new javax.swing.JButton();
        btnRefrescarTabla1 = new javax.swing.JButton();
        btnCambiarEstado = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(43, 43, 43));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Password", "Role", "Status"
            }
        ));
        jScrollPane1.setViewportView(usersTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 970, 350));

        txtSelectedUser.setEditable(false);
        txtSelectedUser.setBackground(new java.awt.Color(51, 51, 51));
        txtSelectedUser.setFont(new java.awt.Font("Raleway", 0, 16)); // NOI18N
        txtSelectedUser.setForeground(new java.awt.Color(153, 153, 153));
        txtSelectedUser.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtSelectedUser.setEnabled(false);
        txtSelectedUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSelectedUserActionPerformed(evt);
            }
        });
        jPanel1.add(txtSelectedUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 290, 45));

        lblSelectedUser.setFont(new java.awt.Font("Raleway", 0, 24)); // NOI18N
        lblSelectedUser.setForeground(new java.awt.Color(255, 255, 255));
        lblSelectedUser.setText("Usuario seleccionado");
        jPanel1.add(lblSelectedUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 250, -1));

        lblClientCode1.setFont(new java.awt.Font("Raleway", 0, 24)); // NOI18N
        lblClientCode1.setForeground(new java.awt.Color(255, 255, 255));
        lblClientCode1.setText("Estado");
        jPanel1.add(lblClientCode1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 100, -1, -1));

        CmbBoxEstado.setFont(new java.awt.Font("Raleway", 0, 18)); // NOI18N
        CmbBoxEstado.setToolTipText("");
        CmbBoxEstado.setBorder(null);
        CmbBoxEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CmbBoxEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CmbBoxEstadoActionPerformed(evt);
            }
        });
        jPanel1.add(CmbBoxEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 140, 153, 47));

        btnRefrescarTabla.setBackground(new java.awt.Color(204, 204, 204));
        btnRefrescarTabla.setFont(new java.awt.Font("Raleway", 0, 16)); // NOI18N
        btnRefrescarTabla.setForeground(new java.awt.Color(0, 0, 0));
        btnRefrescarTabla.setText("Refrescar Datos");
        btnRefrescarTabla.setBorder(null);
        btnRefrescarTabla.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefrescarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarTablaActionPerformed(evt);
            }
        });
        jPanel1.add(btnRefrescarTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 140, 168, 50));

        btnRefrescarTabla1.setBackground(new java.awt.Color(204, 204, 204));
        btnRefrescarTabla1.setFont(new java.awt.Font("Raleway", 0, 16)); // NOI18N
        btnRefrescarTabla1.setForeground(new java.awt.Color(0, 0, 0));
        btnRefrescarTabla1.setText("Refrescar Datos");
        btnRefrescarTabla1.setBorder(null);
        btnRefrescarTabla1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefrescarTabla1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarTabla1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnRefrescarTabla1, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 140, 168, 50));

        btnCambiarEstado.setBackground(new java.awt.Color(217, 217, 217));
        btnCambiarEstado.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnCambiarEstado.setForeground(new java.awt.Color(43, 43, 43));
        btnCambiarEstado.setText("Desactivar/Activar Usuario");
        btnCambiarEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCambiarEstado.setEnabled(false);
        btnCambiarEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarEstadoActionPerformed(evt);
            }
        });
        jPanel1.add(btnCambiarEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 590, 190, 60));

        btnAgregar.setBackground(new java.awt.Color(93, 186, 71));
        btnAgregar.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(43, 43, 43));
        btnAgregar.setText("Agregar Usuario");
        btnAgregar.setBorder(null);
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 590, 190, 60));

        btnModificar.setBackground(new java.awt.Color(217, 217, 217));
        btnModificar.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(43, 43, 43));
        btnModificar.setText("Modificar Usuario");
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificar.setEnabled(false);
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 590, 190, 60));

        btnVolver.setBackground(new java.awt.Color(255, 0, 0));
        btnVolver.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(217, 217, 217));
        btnVolver.setText("Volver ");
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });
        jPanel1.add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 150, 50));

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

    private void txtSelectedUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSelectedUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSelectedUserActionPerformed

    private void CmbBoxEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CmbBoxEstadoActionPerformed

    }//GEN-LAST:event_CmbBoxEstadoActionPerformed

    private void btnRefrescarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarTablaActionPerformed
        MyTableModel tableModel = (MyTableModel) usersTable.getModel();
        tableModel.refreshData();
        CmbBoxEstado.setSelectedIndex(0);
        usersTable.repaint(); // Force the table to repaint itself
        btnCambiarEstado.setEnabled(false);
    }//GEN-LAST:event_btnRefrescarTablaActionPerformed

    private void btnRefrescarTabla1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarTabla1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRefrescarTabla1ActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed

    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.dispose();
        adminPanelForm adminPanelForm = new adminPanelForm(user);
        adminPanelForm.setVisible(true);
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnCambiarEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarEstadoActionPerformed
        // Verificar que se haya seleccionado un usuario
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un usuario.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

// Obtener la entidad del usuario y su ID
        userEntity selectedUser = getUserFromTable(selectedRow);
        int userID = selectedUser.getIdUser();

// Determinar la acción a realizar (activar o desactivar)
        boolean isDeactivating = btnCambiarEstado.getText().contains("Desactivar");
        String actionText = isDeactivating ? "desactivar" : "activar";

// Mostrar diálogo de confirmación al usuario
        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea " + actionText + " al usuario?",
                "Confirmar acción",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            // Si el usuario cancela, se sale del método
            return;
        }

// Crear el controlador de usuarios
        userController controller = new userController();

        try {
            // Ejecutar la acción según corresponda
            if (isDeactivating) {
                controller.deactivateUser(userID);
                onUserDeactivated();
                JOptionPane.showMessageDialog(null, "Usuario desactivado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                controller.activateUser(userID);
                onUserActivated();
                JOptionPane.showMessageDialog(null, "Usuario activado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            String errorMsg = isDeactivating ? "No se pudo desactivar al usuario!" : "No se pudo activar al usuario!";
            JOptionPane.showMessageDialog(null, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnCambiarEstadoActionPerformed

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
            java.util.logging.Logger.getLogger(adminUsersForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminUsersForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminUsersForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminUsersForm.class
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
                new adminUsersForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CmbBoxEstado;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCambiarEstado;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRefrescarTabla;
    private javax.swing.JButton btnRefrescarTabla1;
    private javax.swing.JButton btnVolver;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClientCode1;
    private javax.swing.JLabel lblSelectedUser;
    private javax.swing.JTextField txtSelectedUser;
    private javax.swing.JTable usersTable;
    // End of variables declaration//GEN-END:variables
}
