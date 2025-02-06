/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package documentData;

import java.sql.Date;

/**
 *
 * @author marcelovillalobos
 */
public class dataDocumento {
    
    private String nombreProducto;
    private String cliente;
    private String proveedor;
    private String marca;
    private String fechaEvaluacion;
    private String pnIds;
    private String referenciaCliente;
    private String factura;
    private String numeroLote;
    private String tipoDocumento;
    
    private int cantidadUnidades;

    public dataDocumento(String nombreProducto, String cliente, String proveedor, String marca, String fechaEvaluacion, String pnIds, String referenciaCliente, String factura, String numeroLote, String tipoDocumento, int cantidadUnidades) {
        this.nombreProducto = nombreProducto;
        this.cliente = cliente;
        this.proveedor = proveedor;
        this.marca = marca;
        this.fechaEvaluacion = fechaEvaluacion;
        this.pnIds = pnIds;
        this.referenciaCliente = referenciaCliente;
        this.factura = factura;
        this.numeroLote = numeroLote;
        this.tipoDocumento = tipoDocumento;
        this.cantidadUnidades = cantidadUnidades;
    }

    

    

    public dataDocumento() {
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(String fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public String getPnIds() {
        return pnIds;
    }

    public void setPnIds(String pnIds) {
        this.pnIds = pnIds;
    }

    public String getReferenciaCliente() {
        return referenciaCliente;
    }

    public void setReferenciaCliente(String referenciaCliente) {
        this.referenciaCliente = referenciaCliente;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public int getCantidadUnidades() {
        return cantidadUnidades;
    }

    public void setCantidadUnidades(int cantidadUnidades) {
        this.cantidadUnidades = cantidadUnidades;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    
   
    
    
    
    
}
