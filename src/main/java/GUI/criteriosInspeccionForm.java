/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Entities.User.userEntity;
import documentData.AQL_Inspector;
import documentData.criteriosInspeccion;
import documentData.dataDocumento;
import documentData.resultadosInspeccion;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author marcelovillalobos
 */
public class criteriosInspeccionForm extends javax.swing.JFrame {

    /**
     * Creates new form userAuthentication
     */
    private String nivelInspeccion, tipoDocumento;
    private int cantidadUnidadesLote, cantidadUnidadesAMuestrear, cantidadAceptación, cantidadRechazo;
    private dataDocumento documento;
    private AQL_Inspector inspector; // Mantener una referencia al inspector
    private criteriosInspeccion criteriosInspeccion;
    private resultadosInspeccion resultadosInspeccion;
    private JTextField[] fields;
    private userEntity user;

    public criteriosInspeccionForm(dataDocumento documento, criteriosInspeccion criteriosInspeccion, resultadosInspeccion resultadosInspeccion, userEntity user) {
        initComponents();
        centerWindowOnScreen();

        btnVaciarCampos.setVisible(false);
        this.user = user;

        this.documento = documento;
        this.criteriosInspeccion = criteriosInspeccion;
        this.resultadosInspeccion = resultadosInspeccion;
        this.cantidadUnidadesLote = documento.getCantidadUnidades();
        tipoDocumento = documento.getTipoDocumento();

        // Deshabilitar edición de campos
        txtCantidadAMuestrear.setEditable(false);
        txtCantidadAceptacion.setEditable(false);
        txtCantidadRechazo.setEditable(false);
        txtCantidadUnidadesLote.setEditable(false);
        txtNivelInspección.setEditable(false);

        // Inicializar inspector
        inspector = new AQL_Inspector(cantidadUnidadesLote);

        // Inicializa el array de JTextField
        fields = new JTextField[]{
            txtCantidadAMuestrear, txtCantidadAceptacion, txtCantidadRechazo, txtCantidadUnidadesLote,
            txtNivelInspección
        };

        agregarListenersATextFields(fields);

        // Calcular criterios de inspección
        calcularCriteriosInspeccion();
    }

    public criteriosInspeccionForm() {
    }

    private void calcularCriteriosInspeccion() {
        // Obtener cantidad de unidades del documento
        cantidadUnidadesLote = documento.getCantidadUnidades();
        AQL_Inspector inspector = new AQL_Inspector(cantidadUnidadesLote);

        // Obtener las cantidades y actualizarlas en los campos de texto
        cantidadUnidadesAMuestrear = inspector.getCantidadUnidadesAMuestrear();
        txtCantidadAMuestrear.setText(String.valueOf(cantidadUnidadesAMuestrear));

        cantidadAceptación = inspector.getCantidadAceptacion();
        txtCantidadAceptacion.setText(String.valueOf(cantidadAceptación));

        cantidadRechazo = inspector.getCantidadRechazo();
        txtCantidadRechazo.setText(String.valueOf(cantidadRechazo));

        txtCantidadUnidadesLote.setText(String.valueOf(cantidadUnidadesLote));

        nivelInspeccion = inspector.getCodigoInspeccion();
        txtNivelInspección.setText(nivelInspeccion);

        // Llenar el ComboBox con los porcentajes permitidos
        populateComboBox(inspector);

        // Llamar al método para actualizar los valores de aceptación y rechazo
        String selectedItem = (String) cmbPorcenajeInspeccion.getSelectedItem();
        if (selectedItem != null) {
            double porcentajeSeleccionado = Double.parseDouble(selectedItem.replace("%", ""));
            inspector.actualizarMuestreoConPorcentaje(porcentajeSeleccionado);
        }

        // Agregar ActionListener al ComboBox
        cmbPorcenajeInspeccion.addActionListener(e -> actualizarValoresPorcentaje());
    }

    private void actualizarValoresPorcentaje() {
        // Obtener el porcentaje seleccionado
        String selectedItem = (String) cmbPorcenajeInspeccion.getSelectedItem();
        if (selectedItem != null) {
            // Extraer el valor numérico del porcentaje
            double porcentajeSeleccionado = Double.parseDouble(selectedItem.replace("%", ""));

            // Actualizar el inspector con el nuevo porcentaje
            inspector.seleccionarPorcentajePermitido(porcentajeSeleccionado);

            // Actualizar los valores de aceptación y rechazo
            cantidadAceptación = inspector.getCantidadAceptacion();
            txtCantidadAceptacion.setText(String.valueOf(cantidadAceptación));

            cantidadRechazo = inspector.getCantidadRechazo();
            txtCantidadRechazo.setText(String.valueOf(cantidadRechazo));
        }
    }

    private void populateComboBox(AQL_Inspector inspector) {
        // Obtener los porcentajes para la letra de inspección
        double[] porcentajes = inspector.obtenerPorcentajesPorLetra(inspector.getCodigoInspeccion());

        // Limpiar el ComboBox
        cmbPorcenajeInspeccion.removeAllItems();

        // Llenar el ComboBox con los porcentajes
        if (porcentajes != null) {
            for (double porcentaje : porcentajes) {
                cmbPorcenajeInspeccion.addItem(porcentaje + "%");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay porcentajes disponibles para la letra " + inspector.getCodigoInspeccion());
        }
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button1 = new java.awt.Button();
        jPanel1 = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnVaciarCampos = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblCantidadUnidadesLote = new javax.swing.JLabel();
        lblCantidadRechazo = new javax.swing.JLabel();
        txtCantidadUnidadesLote = new javax.swing.JTextField();
        txtCantidadRechazo = new javax.swing.JTextField();
        lblCantidadAMuestrear = new javax.swing.JLabel();
        txtCantidadAMuestrear = new javax.swing.JTextField();
        lblNivelInspección = new javax.swing.JLabel();
        txtNivelInspección = new javax.swing.JTextField();
        lblCantidadAceptación = new javax.swing.JLabel();
        txtCantidadAceptacion = new javax.swing.JTextField();
        lblPorcentajeInspeccion = new javax.swing.JLabel();
        cmbPorcenajeInspeccion = new javax.swing.JComboBox<>();

        button1.setLabel("button1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(43, 43, 43));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo.setFont(new java.awt.Font("Helvetica Neue", 0, 5)); // NOI18N
        logo.setIcon(new javax.swing.ImageIcon("/Users/marcelovillalobos/Documents/GitHub/AQL_Generator/src/main/resources/images/ids logo blanco 1.png")); // NOI18N
        jPanel1.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 120, 60));

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

        jLabel3.setFont(new java.awt.Font("Open Sans", 0, 50)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(217, 217, 217));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Datos de Muestreo");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, -1, -1));

        lblCantidadUnidadesLote.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblCantidadUnidadesLote.setForeground(new java.awt.Color(217, 217, 217));
        lblCantidadUnidadesLote.setText("Cantidad de Unidades del Lote");
        jPanel1.add(lblCantidadUnidadesLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 180, -1, -1));

        lblCantidadRechazo.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblCantidadRechazo.setForeground(new java.awt.Color(217, 217, 217));
        lblCantidadRechazo.setText("Cantidad de Rechazo");
        jPanel1.add(lblCantidadRechazo, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 380, -1, -1));

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
        jPanel1.add(txtCantidadUnidadesLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 220, 400, 50));

        txtCantidadRechazo.setBackground(new java.awt.Color(102, 102, 102));
        txtCantidadRechazo.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtCantidadRechazo.setForeground(new java.awt.Color(217, 217, 217));
        txtCantidadRechazo.setToolTipText("");
        txtCantidadRechazo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtCantidadRechazo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadRechazoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadRechazoFocusLost(evt);
            }
        });
        txtCantidadRechazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadRechazoActionPerformed(evt);
            }
        });
        jPanel1.add(txtCantidadRechazo, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 420, 400, 50));

        lblCantidadAMuestrear.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblCantidadAMuestrear.setForeground(new java.awt.Color(217, 217, 217));
        lblCantidadAMuestrear.setText("Cantidad a Muestrear");
        jPanel1.add(lblCantidadAMuestrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, -1, -1));

        txtCantidadAMuestrear.setBackground(new java.awt.Color(102, 102, 102));
        txtCantidadAMuestrear.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtCantidadAMuestrear.setForeground(new java.awt.Color(217, 217, 217));
        txtCantidadAMuestrear.setToolTipText("");
        txtCantidadAMuestrear.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtCantidadAMuestrear.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadAMuestrearFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadAMuestrearFocusLost(evt);
            }
        });
        txtCantidadAMuestrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadAMuestrearActionPerformed(evt);
            }
        });
        jPanel1.add(txtCantidadAMuestrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 400, 50));

        lblNivelInspección.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblNivelInspección.setForeground(new java.awt.Color(217, 217, 217));
        lblNivelInspección.setText("Nivel de Inspección");
        jPanel1.add(lblNivelInspección, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, -1, -1));

        txtNivelInspección.setBackground(new java.awt.Color(102, 102, 102));
        txtNivelInspección.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtNivelInspección.setForeground(new java.awt.Color(217, 217, 217));
        txtNivelInspección.setToolTipText("");
        txtNivelInspección.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtNivelInspección.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNivelInspecciónFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNivelInspecciónFocusLost(evt);
            }
        });
        txtNivelInspección.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNivelInspecciónActionPerformed(evt);
            }
        });
        jPanel1.add(txtNivelInspección, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 400, 50));

        lblCantidadAceptación.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblCantidadAceptación.setForeground(new java.awt.Color(217, 217, 217));
        lblCantidadAceptación.setText("Cantidad de Aceptación");
        jPanel1.add(lblCantidadAceptación, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 380, -1, -1));

        txtCantidadAceptacion.setBackground(new java.awt.Color(102, 102, 102));
        txtCantidadAceptacion.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtCantidadAceptacion.setForeground(new java.awt.Color(217, 217, 217));
        txtCantidadAceptacion.setToolTipText("");
        txtCantidadAceptacion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtCantidadAceptacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadAceptacionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadAceptacionFocusLost(evt);
            }
        });
        txtCantidadAceptacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadAceptacionActionPerformed(evt);
            }
        });
        jPanel1.add(txtCantidadAceptacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 400, 50));

        lblPorcentajeInspeccion.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblPorcentajeInspeccion.setForeground(new java.awt.Color(217, 217, 217));
        lblPorcentajeInspeccion.setText("Cantidad de Unidades del Lote");
        jPanel1.add(lblPorcentajeInspeccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 280, -1, -1));

        cmbPorcenajeInspeccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbPorcenajeInspeccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 320, 400, 50));

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

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed

        if (resultadosInspeccion == null) {
            resultadosInspeccion = new resultadosInspeccion(); // Crear nueva instancia si es null
        }

        criteriosInspeccion = new criteriosInspeccion(nivelInspeccion, cantidadUnidadesAMuestrear, cantidadRechazo, cantidadAceptación);

        cantidadErroresForm cantidadErroresForm = new cantidadErroresForm(documento, criteriosInspeccion, resultadosInspeccion, user);
        this.dispose();
        cantidadErroresForm.setVisible(true);


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

    private void txtCantidadRechazoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadRechazoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadRechazoFocusGained

    private void txtCantidadRechazoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadRechazoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadRechazoFocusLost

    private void txtCantidadRechazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadRechazoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadRechazoActionPerformed

    private void txtCantidadAMuestrearFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadAMuestrearFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadAMuestrearFocusGained

    private void txtCantidadAMuestrearFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadAMuestrearFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadAMuestrearFocusLost

    private void txtCantidadAMuestrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadAMuestrearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadAMuestrearActionPerformed

    private void txtNivelInspecciónFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNivelInspecciónFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNivelInspecciónFocusGained

    private void txtNivelInspecciónFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNivelInspecciónFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNivelInspecciónFocusLost

    private void txtNivelInspecciónActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNivelInspecciónActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNivelInspecciónActionPerformed

    private void txtCantidadAceptacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadAceptacionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadAceptacionFocusGained

    private void txtCantidadAceptacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadAceptacionFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadAceptacionFocusLost

    private void txtCantidadAceptacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadAceptacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadAceptacionActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed

        dataDocumentoForm dataDocForm = new dataDocumentoForm(tipoDocumento, documento, user);
        this.dispose();
        dataDocForm.setVisible(true);

    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnVaciarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaciarCamposActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que quieres limpiar todos los campos de texto?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            for (JTextField field : fields) {
                field.setText("");
            }
        }
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
            java.util.logging.Logger.getLogger(criteriosInspeccionForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(criteriosInspeccionForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(criteriosInspeccionForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(criteriosInspeccionForm.class
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
                new criteriosInspeccionForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnVaciarCampos;
    private javax.swing.JButton btnVolver;
    private java.awt.Button button1;
    private javax.swing.JComboBox<String> cmbPorcenajeInspeccion;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCantidadAMuestrear;
    private javax.swing.JLabel lblCantidadAceptación;
    private javax.swing.JLabel lblCantidadRechazo;
    private javax.swing.JLabel lblCantidadUnidadesLote;
    private javax.swing.JLabel lblNivelInspección;
    private javax.swing.JLabel lblPorcentajeInspeccion;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField txtCantidadAMuestrear;
    private javax.swing.JTextField txtCantidadAceptacion;
    private javax.swing.JTextField txtCantidadRechazo;
    private javax.swing.JTextField txtCantidadUnidadesLote;
    private javax.swing.JTextField txtNivelInspección;
    // End of variables declaration//GEN-END:variables
}
