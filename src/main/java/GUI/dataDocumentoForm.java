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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author marcelovillalobos
 */
public class dataDocumentoForm extends javax.swing.JFrame {

    /**
     * Creates new form userAuthentication
     */
    private String nombreProducto, cliente, proveedor, marca, fechaEvaluacion, pnIds, factura, referenciaCliente, numeroLote, tipoDocumento;
    private int cantidadUnidades;
    private criteriosInspeccion criteriosInspeccion;
    private resultadosInspeccion resultadosInspeccion;
    private JTextField[] fields;

    public dataDocumentoForm(String tipoDocumento, dataDocumento documento) {
        initComponents();
        centerWindowOnScreen();

        this.tipoDocumento = tipoDocumento;

        lblTipoDoc.setText(lblTipoDoc.getText() + " " + tipoDocumento);

        txtCantidadUnidades.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9'))
                        && (caracter != '\b')) {
                    e.consume();
                }
            }
        });

        if (tipoDocumento.equals("Despacho")) {
            lblProveedorOCLiente.setText("Cliente");
            lblFacturaORefCliente.setText("Referencia Cliente");

        } else {
            lblProveedorOCLiente.setText("Proveedor");
            lblFacturaORefCliente.setText("Factura");

        }

        // Inicializa el array de JTextField
        fields = new JTextField[]{
            txtNombreProducto, txtProveedorOCLiente, txtMarca, txtFechaEvaluacion,
            txtPNIDS, txtFacturaORefCliente, txtNumeroLote, txtCantidadUnidades
        };

        // Agregar listeners a los campos de texto
        agregarListenersATextFields(fields);

        if (documento != null) {

            nombreProducto = documento.getNombreProducto();
            txtNombreProducto.setText(nombreProducto);

            marca = documento.getMarca();
            txtMarca.setText(marca);

            fechaEvaluacion = documento.getFechaEvaluacion();
            txtFechaEvaluacion.setText(fechaEvaluacion);

            pnIds = documento.getPnIds();
            txtPNIDS.setText(pnIds);

            numeroLote = documento.getNumeroLote();
            txtNumeroLote.setText(numeroLote);

            cantidadUnidades = documento.getCantidadUnidades();
            txtCantidadUnidades.setText(String.valueOf(cantidadUnidades));

            if (tipoDocumento.equals("Despacho")) {

                cliente = documento.getCliente();
                txtProveedorOCLiente.setText(cliente);

                referenciaCliente = documento.getReferenciaCliente();
                txtFacturaORefCliente.setText(referenciaCliente);

            } else {
                proveedor = documento.getProveedor();
                factura = documento.getFactura();

                txtProveedorOCLiente.setText(proveedor);

                txtFacturaORefCliente.setText(factura);

            }

            System.out.println("Nombre de Producto: " + nombreProducto);
            System.out.println("Marca: " + marca);
            System.out.println("Cliente: " + cliente);
            System.out.println("Fecha Evaluacion: " + fechaEvaluacion);
            System.out.println("P/N IDS: " + pnIds);
            System.out.println("Numero de Lote: " + numeroLote);
            System.out.println("Proveedor: " + proveedor);
            System.out.println("Referencia Cliente: " + referenciaCliente);
            System.out.println("Factura: " + factura);
            System.out.println("Cantidad de Unidades: " + cantidadUnidades);
            System.out.println("Tipo de Documento: " + tipoDocumento);
            System.out.println("_____________________________\n");

        } else {
            documento = new dataDocumento();
            documento.setTipoDocumento(tipoDocumento);
        }

    }

    public dataDocumentoForm() {
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
        btnVaciarCampos1.setEnabled(hayTexto);
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
        btnVaciarCampos1 = new javax.swing.JButton();
        lblTipoDoc = new javax.swing.JLabel();
        lblPNIDS = new javax.swing.JLabel();
        lblNumeroLote = new javax.swing.JLabel();
        lblFacturaORefCliente = new javax.swing.JLabel();
        txtCantidadUnidades = new javax.swing.JTextField();
        txtPNIDS = new javax.swing.JTextField();
        lblCantidadUnidades = new javax.swing.JLabel();
        txtFacturaORefCliente = new javax.swing.JTextField();
        txtNumeroLote = new javax.swing.JTextField();
        lblNombreProducto = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        lblProveedorOCLiente = new javax.swing.JLabel();
        txtProveedorOCLiente = new javax.swing.JTextField();
        lblMarca = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        lblFechaEvaluacion = new javax.swing.JLabel();
        txtFechaEvaluacion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

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

        btnVaciarCampos1.setBackground(new java.awt.Color(217, 217, 217));
        btnVaciarCampos1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnVaciarCampos1.setForeground(new java.awt.Color(43, 43, 43));
        btnVaciarCampos1.setText("Vaciar Campos");
        btnVaciarCampos1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVaciarCampos1.setEnabled(false);
        jPanel1.add(btnVaciarCampos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 590, 220, 60));

        lblTipoDoc.setFont(new java.awt.Font("Open Sans", 0, 30)); // NOI18N
        lblTipoDoc.setForeground(new java.awt.Color(217, 217, 217));
        lblTipoDoc.setText("Tipo Documento:");
        jPanel1.add(lblTipoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 60, -1, -1));

        lblPNIDS.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblPNIDS.setForeground(new java.awt.Color(217, 217, 217));
        lblPNIDS.setText("P/N IDS");
        jPanel1.add(lblPNIDS, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 140, -1, -1));

        lblNumeroLote.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblNumeroLote.setForeground(new java.awt.Color(217, 217, 217));
        lblNumeroLote.setText("Numero de Lote");
        jPanel1.add(lblNumeroLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 340, -1, -1));

        lblFacturaORefCliente.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblFacturaORefCliente.setForeground(new java.awt.Color(217, 217, 217));
        lblFacturaORefCliente.setText("Factura o Referencia Cliente");
        jPanel1.add(lblFacturaORefCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 240, -1, -1));

        txtCantidadUnidades.setBackground(new java.awt.Color(102, 102, 102));
        txtCantidadUnidades.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtCantidadUnidades.setForeground(new java.awt.Color(217, 217, 217));
        txtCantidadUnidades.setToolTipText("");
        txtCantidadUnidades.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtCantidadUnidades.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadUnidadesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadUnidadesFocusLost(evt);
            }
        });
        txtCantidadUnidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadUnidadesActionPerformed(evt);
            }
        });
        jPanel1.add(txtCantidadUnidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 480, 400, 50));

        txtPNIDS.setBackground(new java.awt.Color(102, 102, 102));
        txtPNIDS.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtPNIDS.setForeground(new java.awt.Color(217, 217, 217));
        txtPNIDS.setToolTipText("");
        txtPNIDS.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtPNIDS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPNIDSFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPNIDSFocusLost(evt);
            }
        });
        txtPNIDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPNIDSActionPerformed(evt);
            }
        });
        jPanel1.add(txtPNIDS, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 180, 400, 50));

        lblCantidadUnidades.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblCantidadUnidades.setForeground(new java.awt.Color(217, 217, 217));
        lblCantidadUnidades.setText("Cantidad de Unidades");
        jPanel1.add(lblCantidadUnidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 440, -1, -1));

        txtFacturaORefCliente.setBackground(new java.awt.Color(102, 102, 102));
        txtFacturaORefCliente.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtFacturaORefCliente.setForeground(new java.awt.Color(217, 217, 217));
        txtFacturaORefCliente.setToolTipText("");
        txtFacturaORefCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtFacturaORefCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFacturaORefClienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFacturaORefClienteFocusLost(evt);
            }
        });
        txtFacturaORefCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFacturaORefClienteActionPerformed(evt);
            }
        });
        jPanel1.add(txtFacturaORefCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 280, 400, 50));

        txtNumeroLote.setBackground(new java.awt.Color(102, 102, 102));
        txtNumeroLote.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtNumeroLote.setForeground(new java.awt.Color(217, 217, 217));
        txtNumeroLote.setToolTipText("");
        txtNumeroLote.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtNumeroLote.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumeroLoteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumeroLoteFocusLost(evt);
            }
        });
        txtNumeroLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroLoteActionPerformed(evt);
            }
        });
        jPanel1.add(txtNumeroLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 380, 400, 50));

        lblNombreProducto.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblNombreProducto.setForeground(new java.awt.Color(217, 217, 217));
        lblNombreProducto.setText("Nombre del Producto");
        jPanel1.add(lblNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, -1, -1));

        txtNombreProducto.setBackground(new java.awt.Color(102, 102, 102));
        txtNombreProducto.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtNombreProducto.setForeground(new java.awt.Color(217, 217, 217));
        txtNombreProducto.setToolTipText("");
        txtNombreProducto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtNombreProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNombreProductoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreProductoFocusLost(evt);
            }
        });
        txtNombreProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProductoActionPerformed(evt);
            }
        });
        jPanel1.add(txtNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 400, 50));

        lblProveedorOCLiente.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblProveedorOCLiente.setForeground(new java.awt.Color(217, 217, 217));
        lblProveedorOCLiente.setText("Proveedor o Cliente");
        jPanel1.add(lblProveedorOCLiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, -1, -1));

        txtProveedorOCLiente.setBackground(new java.awt.Color(102, 102, 102));
        txtProveedorOCLiente.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtProveedorOCLiente.setForeground(new java.awt.Color(217, 217, 217));
        txtProveedorOCLiente.setToolTipText("");
        txtProveedorOCLiente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtProveedorOCLiente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProveedorOCLienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProveedorOCLienteFocusLost(evt);
            }
        });
        txtProveedorOCLiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProveedorOCLienteActionPerformed(evt);
            }
        });
        jPanel1.add(txtProveedorOCLiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, 400, 50));

        lblMarca.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblMarca.setForeground(new java.awt.Color(217, 217, 217));
        lblMarca.setText("Marca");
        jPanel1.add(lblMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, -1, -1));

        txtMarca.setBackground(new java.awt.Color(102, 102, 102));
        txtMarca.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtMarca.setForeground(new java.awt.Color(217, 217, 217));
        txtMarca.setToolTipText("");
        txtMarca.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtMarca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMarcaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMarcaFocusLost(evt);
            }
        });
        txtMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarcaActionPerformed(evt);
            }
        });
        jPanel1.add(txtMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 380, 400, 50));

        lblFechaEvaluacion.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblFechaEvaluacion.setForeground(new java.awt.Color(217, 217, 217));
        lblFechaEvaluacion.setText("Fecha de Evaluación");
        jPanel1.add(lblFechaEvaluacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 440, -1, -1));

        txtFechaEvaluacion.setBackground(new java.awt.Color(102, 102, 102));
        txtFechaEvaluacion.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        txtFechaEvaluacion.setForeground(new java.awt.Color(217, 217, 217));
        txtFechaEvaluacion.setToolTipText("");
        txtFechaEvaluacion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 217, 217), 2, true));
        txtFechaEvaluacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFechaEvaluacionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFechaEvaluacionFocusLost(evt);
            }
        });
        txtFechaEvaluacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaEvaluacionActionPerformed(evt);
            }
        });
        jPanel1.add(txtFechaEvaluacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 480, 400, 50));

        jLabel4.setFont(new java.awt.Font("Open Sans", 0, 46)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(217, 217, 217));
        jLabel4.setText("Rellene la Información");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, -1));

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
        nombreProducto = txtNombreProducto.getText();

        marca = txtMarca.getText();
        fechaEvaluacion = txtFechaEvaluacion.getText();
        pnIds = txtPNIDS.getText();

        numeroLote = txtNumeroLote.getText();
        cantidadUnidades = Integer.parseInt(txtCantidadUnidades.getText());

        // Validación según el tipo de documento
        if (tipoDocumento.equals("Despacho")) {
            cliente = txtProveedorOCLiente.getText();
            referenciaCliente = txtFacturaORefCliente.getText();
            proveedor = null;
            factura = null;
        } else { // "Recepcion"
            proveedor = txtProveedorOCLiente.getText();
            factura = txtFacturaORefCliente.getText();
            cliente = null;
            referenciaCliente = null;
        }

        System.out.println("Nombre de Producto: " + nombreProducto);
        System.out.println("Marca: " + marca);
        System.out.println("Cliente: " + cliente);
        System.out.println("Fecha Evaluacion: " + fechaEvaluacion);
        System.out.println("P/N IDS: " + pnIds);
        System.out.println("Numero de Lote: " + numeroLote);
        System.out.println("Proveedor: " + proveedor);
        System.out.println("Referencia Cliente: " + referenciaCliente);
        System.out.println("Factura: " + factura);
        System.out.println("Cantidad de Unidades: " + cantidadUnidades);
        System.out.println("Tipo de Documento: " + tipoDocumento);
        System.out.println("_____________________________\n");

        // Creación del objeto con los valores correctos
        dataDocumento documento = new dataDocumento(nombreProducto, cliente, proveedor, marca,
                fechaEvaluacion, pnIds, referenciaCliente,
                factura, numeroLote, tipoDocumento, cantidadUnidades);

        // Suponiendo que criteriosInspeccion es una variable de tipo CriteriosInspeccion
        if (criteriosInspeccion == null) {
            criteriosInspeccion = new criteriosInspeccion(); // Crear nueva instancia si es null
        }

        if (resultadosInspeccion == null) {
            resultadosInspeccion = new resultadosInspeccion(); // Crear nueva instancia si es null
        }

// Ahora, ambos objetos están garantizados de no ser nulos
        criteriosInspeccionForm criteriosInspeccionForm = new criteriosInspeccionForm(documento, criteriosInspeccion, resultadosInspeccion);
        this.dispose();
        criteriosInspeccionForm.setVisible(true);

    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void txtCantidadUnidadesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadUnidadesFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadUnidadesFocusGained

    private void txtCantidadUnidadesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadUnidadesFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadUnidadesFocusLost

    private void txtCantidadUnidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadUnidadesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadUnidadesActionPerformed

    private void txtPNIDSFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPNIDSFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPNIDSFocusGained

    private void txtPNIDSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPNIDSFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPNIDSFocusLost

    private void txtPNIDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPNIDSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPNIDSActionPerformed

    private void txtFacturaORefClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFacturaORefClienteFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFacturaORefClienteFocusGained

    private void txtFacturaORefClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFacturaORefClienteFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFacturaORefClienteFocusLost

    private void txtFacturaORefClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFacturaORefClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFacturaORefClienteActionPerformed

    private void txtNumeroLoteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumeroLoteFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroLoteFocusGained

    private void txtNumeroLoteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumeroLoteFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroLoteFocusLost

    private void txtNumeroLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroLoteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroLoteActionPerformed

    private void txtNombreProductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreProductoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProductoFocusGained

    private void txtNombreProductoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreProductoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProductoFocusLost

    private void txtNombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProductoActionPerformed

    private void txtProveedorOCLienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProveedorOCLienteFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProveedorOCLienteFocusGained

    private void txtProveedorOCLienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProveedorOCLienteFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProveedorOCLienteFocusLost

    private void txtProveedorOCLienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProveedorOCLienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProveedorOCLienteActionPerformed

    private void txtMarcaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMarcaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarcaFocusGained

    private void txtMarcaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMarcaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarcaFocusLost

    private void txtMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarcaActionPerformed

    private void txtFechaEvaluacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaEvaluacionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaEvaluacionFocusGained

    private void txtFechaEvaluacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaEvaluacionFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaEvaluacionFocusLost

    private void txtFechaEvaluacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaEvaluacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaEvaluacionActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        
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
            java.util.logging.Logger.getLogger(dataDocumento.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dataDocumento.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dataDocumento.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dataDocumento.class
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
                new dataDocumentoForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnVaciarCampos1;
    private javax.swing.JButton btnVolver;
    private java.awt.Button button1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCantidadUnidades;
    private javax.swing.JLabel lblFacturaORefCliente;
    private javax.swing.JLabel lblFechaEvaluacion;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblNombreProducto;
    private javax.swing.JLabel lblNumeroLote;
    private javax.swing.JLabel lblPNIDS;
    private javax.swing.JLabel lblProveedorOCLiente;
    private javax.swing.JLabel lblTipoDoc;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField txtCantidadUnidades;
    private javax.swing.JTextField txtFacturaORefCliente;
    private javax.swing.JTextField txtFechaEvaluacion;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtNumeroLote;
    private javax.swing.JTextField txtPNIDS;
    private javax.swing.JTextField txtProveedorOCLiente;
    // End of variables declaration//GEN-END:variables
}
